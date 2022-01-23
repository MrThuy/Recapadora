package erp.controles.receber;

import erp.controles.cadastro.CCorrentista;
import erp.modelos.banco.Correntista;
import erp.modelos.banco.TituloReceber;
import erp.modelos.tablemodel.TituloReceberCompletoTableModel;
import erp.util.*;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.*;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * @author Arthur
 */
public class CConsultaReceber {

    private List<TituloReceber> itens;
    private Correntista cliente;

    public void pesquisaCliente(JInternalFrame frame) {
        ERPFrames.setFocus(frame, "idtipotitulo");
        cliente = CCorrentista.pesquisaCorrentistas(CCorrentista.TIPO.CLIENTE);
        if (cliente != null) {
            clienteParaFrame(frame);
        }
//        else {
//            ERPFrames.setFocus(frame, "idcorrentista");
//        }
    }

    public void criticaCliente(JInternalFrame frame) {
        String idCliente = ERPFrames.getValorCampoFrame(frame, "idcorrentista");
        if (idCliente.trim().length() > 0) {
            cliente = CCorrentista.procuraCorrentista(Integer.parseInt(idCliente));
            if (!CCorrentista.erroCliente(frame, cliente)) {
                clienteParaFrame(frame);
            } else {
                ERPFrames.setValorCampoFrame(frame, "idCorrentista", "");
                ERPFrames.setValorCampoFrame(frame, "pesquisa", "");
                SwingUtilities.invokeLater(new ERPFocusGrabber(ERPFrames.getComponent(frame, "idcorrentista")));
            }
        } else {
            ERPFrames.setValorCampoFrame(frame, "pesquisa", "");
        }
    }

    private void clienteParaFrame(JInternalFrame frame) {
        ERPFrames.objetoParaFrame(frame, cliente);
        ERPFrames.setFocus(frame, "idtipotitulo");
    }

    private List<TituloReceber> carregaItens(JInternalFrame frame) {
        ERPFrames.setValorCampoFrame(frame, "quantidade", 0);
        ERPFrames.setValorCampoFrame(frame, "valor_total", 0);
        ERPFrames.setValorCampoFrame(frame, "valor_pago", 0);
        ERPFrames.setValorCampoFrame(frame, "valor_saldo", 0);

        int idCliente = ERPValor.toInt(ERPFrames.getValorCampoFrame(frame, "idcorrentista"));
        int idtipotitulo = ERPValor.toInt(ERPFrames.getValorCampoFrame(frame, "idtipotitulo"));

        int numero_ini = Integer.parseInt(ERPFrames.getValorCampoFrame(frame, "numero_ini"));
        int numero_fim = Integer.parseInt(ERPFrames.getValorCampoFrame(frame, "numero_fim"));

        Date dataEmissao_ini = ERPData.toSqlDate(ERPFrames.getValorCampoFrame(frame, "dataEmissao_ini"));
        Date dataEmissao_fim = ERPData.toSqlDate(ERPFrames.getValorCampoFrame(frame, "dataEmissao_fim"));
        
        Date dataPagamento_ini = ERPData.toSqlDate(ERPFrames.getValorCampoFrame(frame, "dataPagamento_ini"));
        Date dataPagamento_fim = ERPData.toSqlDate(ERPFrames.getValorCampoFrame(frame, "dataPagamento_fim"));

        Date dataVencimento_ini = ERPData.toSqlDate(ERPFrames.getValorCampoFrame(frame, "dataVencimento_ini"));
        Date dataVencimento_fim = ERPData.toSqlDate(ERPFrames.getValorCampoFrame(frame, "dataVencimento_fim"));

        BigDecimal valor_ini = ERPValor.toBigDecimal(ERPFrames.getValorCampoFrame(frame, "valor_ini"));
        BigDecimal valor_fim = ERPValor.toBigDecimal(ERPFrames.getValorCampoFrame(frame, "valor_fim"));

        int idfinanceira = ERPValor.toInt(ERPFrames.getValorCampoFrame(frame, "idfinanceira"));

        boolean radioTodos = ERPFrames.getValorCampoFrame(frame, "radioTodos").equalsIgnoreCase("true") ? true : false;
        boolean radioAbertos = ERPFrames.getValorCampoFrame(frame, "radioAbertos").equalsIgnoreCase("true") ? true : false;
        boolean radioPagos = ERPFrames.getValorCampoFrame(frame, "radioPagos").equalsIgnoreCase("true") ? true : false;

        CTituloReceber.TIPO tipo;

        if (radioTodos) {
            tipo = CTituloReceber.TIPO.TODOS;
        } else if (radioAbertos) {
            tipo = CTituloReceber.TIPO.ABERTOS;
        } else {
            tipo = CTituloReceber.TIPO.PAGOS;
        }

        return CTituloReceber.listaTitulosReceber(
                tipo,
                idCliente,
                idtipotitulo,
                numero_ini, numero_fim,
                dataEmissao_ini, dataEmissao_fim,
                dataVencimento_ini, dataVencimento_fim,
                dataPagamento_ini, dataPagamento_fim,
                valor_ini, valor_fim,
                idfinanceira,
                new String[]{"tipoTitulo", "correntista", "idfinanceira"});
    }

    public void salva(JInternalFrame frame) {

        itens = carregaItens(frame);

        ((TituloReceberCompletoTableModel) ((JTable) ERPFrames.getComponent(frame, "titulosTable")).getModel()).setLinhas(itens);

        ERPFrames.setValorCampoFrame(frame, "quantidade", itens.size());

        BigDecimal valor_total = ((TituloReceberCompletoTableModel) ((JTable) ERPFrames.getComponent(frame, "titulosTable")).getModel()).getSomaCol(5);

        BigDecimal valor_pago = ((TituloReceberCompletoTableModel) ((JTable) ERPFrames.getComponent(frame, "titulosTable")).getModel()).getSomaCol(6);

        ERPFrames.setValorCampoFrame(frame, "valor_total", valor_total);
        ERPFrames.setValorCampoFrame(frame, "valor_pago", valor_pago);
        ERPFrames.setValorCampoFrame(frame, "valor_saldo", valor_total.subtract(valor_pago));
    }

    public void cancela(JInternalFrame frame) {
        ERPFrames.limparTodosCampos(frame);
        cliente = null;
        itens = null;
        ((TituloReceberCompletoTableModel) ((JTable) ERPFrames.getComponent(frame, "titulosTable")).getModel()).setLinhas(itens);
        ERPFrames.setFocus(frame, "idcorrentista");
        ERPFrames.setValorCampoFrame(frame, "numero_ini", 0);
        ERPFrames.setValorCampoFrame(frame, "numero_fim", 9999999);
        Calendar calendar = new GregorianCalendar();
        Date hj = new Date(System.currentTimeMillis());
        calendar.setTime(hj);
        calendar.add(Calendar.YEAR, -1);
        ERPFrames.setValorCampoFrame(frame, "dataEmissao_ini", calendar.getTime());
        ERPFrames.setValorCampoFrame(frame, "dataEmissao_fim", hj);
        ERPFrames.setValorCampoFrame(frame, "dataVencimento_ini", calendar.getTime());
        calendar.add(Calendar.YEAR, 2);
        ERPFrames.setValorCampoFrame(frame, "dataVencimento_fim", calendar.getTime());
        ERPFrames.setValorCampoFrame(frame, "valor_ini", 0);
        ERPFrames.setValorCampoFrame(frame, "valor_fim", "999.999,99");
    }

    public void geraRelatorio(JInternalFrame frame) {
        itens = carregaItens(frame);

        String parametros_info;

        parametros_info = cliente == null ? "Todos os cliente" : "Cliente: " + cliente.getNome();

        String idtipotitulo = ERPFrames.getValorCampoFrame(frame, "idtipotitulo");
        parametros_info += "; " + (idtipotitulo == null || idtipotitulo.equals("") ? "Todos os tipo de títulos" : "Tipo de Titulo: " + idtipotitulo);

        String numero_ini = ERPFrames.getValorCampoFrame(frame, "numero_ini");
        String numero_fim = ERPFrames.getValorCampoFrame(frame, "numero_fim");

        parametros_info += "; Número :" + numero_ini + " a " + numero_fim;

        String dataEmissao_ini = ERPFrames.getValorCampoFrame(frame, "dataEmissao_ini");
        String dataEmissao_fim = ERPFrames.getValorCampoFrame(frame, "dataEmissao_fim");

        parametros_info += "; Emissão de: " + dataEmissao_ini + " a " + dataEmissao_fim;

        String dataVencimento_ini = ERPFrames.getValorCampoFrame(frame, "dataVencimento_ini");
        String dataVencimento_fim = ERPFrames.getValorCampoFrame(frame, "dataVencimento_fim");

        parametros_info += "; Vencimento de: " + dataVencimento_ini + " a " + dataVencimento_fim;

        String valor_ini = ERPFrames.getValorCampoFrame(frame, "valor_ini");
        String valor_fim = ERPFrames.getValorCampoFrame(frame, "valor_fim");

        parametros_info += "; Valor de: " + valor_ini + " a " + valor_fim;

        String idfinanceira = ERPFrames.getValorCampoFrame(frame, "idfinanceira");

        parametros_info += "; " + (idfinanceira == null || idfinanceira.equals("") ? "Todos as financeiras" : "Financeira: " + idfinanceira);

        boolean radioTodos = ERPFrames.getValorCampoFrame(frame, "radioTodos").equalsIgnoreCase("true") ? true : false;
        boolean radioAbertos = ERPFrames.getValorCampoFrame(frame, "radioAbertos").equalsIgnoreCase("true") ? true : false;

        parametros_info += "; " + (radioTodos ? "Todos os títulos" : (radioAbertos ? "Títulos pagos" : "Títulos em aberto"));

        tituloReceber(itens, parametros_info);
    }

    public void tituloReceber(List<TituloReceber> titulo, String parametros_info) {

        InputStream inputStream = getClass().getResourceAsStream("/erp/relatorios/TitulosReceber.jasper");

        Map parametros = new HashMap();

        // criando os dados que serão passados ao datasource
        List dados = new ArrayList();
        dados.addAll(titulo);
        parametros.put("Parametros", parametros_info);

        // criando o datasource com os dados criados
        JRDataSource ds = new JRBeanCollectionDataSource(dados);

        try {
            // passando o datasource para o método de criação e exibição do relatório
            ERPRelatorios.openReport("Títulos a Receber", inputStream, parametros,
                    ds);
        } catch (JRException exc) {
            JOptionPane.showMessageDialog(null, exc.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
