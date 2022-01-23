package erp.controles.pagar;

import erp.controles.cadastro.CCorrentista;
import erp.modelos.banco.Correntista;
import erp.modelos.banco.TituloPagar;
import erp.util.*;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.*;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * @author Arthur
 */
public class CTitulosPagarPorDia {

    private Correntista fornecedor;

    public void pesquisaFornecedor(JInternalFrame frame) {
        ERPFrames.setFocus(frame, "idtipotitulo");
        fornecedor = CCorrentista.pesquisaCorrentistas(CCorrentista.TIPO.FORNECEDOR);
        if (fornecedor != null) {
            fornecedorParaFrame(frame);
        } 
//        else {
//            ERPFrames.setFocus(frame, "idcorrentista");
//        }
    }

    public void criticaFornecedor(JInternalFrame frame) {
        String idFornecedor = ERPFrames.getValorCampoFrame(frame, "idcorrentista");
        if (idFornecedor.trim().length() > 0) {
            fornecedor = CCorrentista.procuraCorrentista(Integer.parseInt(idFornecedor));
            if (!CCorrentista.erroFornecedor(frame, fornecedor)) {
                fornecedorParaFrame(frame);
            } else {
                ERPFrames.setValorCampoFrame(frame, "idCorrentista", "");
                ERPFrames.setValorCampoFrame(frame, "pesquisa", "");
                SwingUtilities.invokeLater(new ERPFocusGrabber(ERPFrames.getComponent(frame, "idcorrentista")));
            }
        } else {
            ERPFrames.setValorCampoFrame(frame, "pesquisa", "");
        }
    }

    private void fornecedorParaFrame(JInternalFrame frame) {
        ERPFrames.objetoParaFrame(frame, fornecedor);
        ERPFrames.setFocus(frame, "idtipotitulo");
    }

    public void salva(JInternalFrame frame) {

        int idFornecedor = ERPValor.toInt(ERPFrames.getValorCampoFrame(frame, "idcorrentista"));
        int idtipotitulo = ERPValor.toInt(ERPFrames.getValorCampoFrame(frame, "idtipotitulo"));

        int numero_ini = Integer.parseInt(ERPFrames.getValorCampoFrame(frame, "numero_ini"));
        int numero_fim = Integer.parseInt(ERPFrames.getValorCampoFrame(frame, "numero_fim"));

        Date dataEmissao_ini = ERPData.toSqlDate(ERPFrames.getValorCampoFrame(frame, "dataEmissao_ini"));
        Date dataEmissao_fim = ERPData.toSqlDate(ERPFrames.getValorCampoFrame(frame, "dataEmissao_fim"));

        Date dataVencimento_ini = ERPData.toSqlDate(ERPFrames.getValorCampoFrame(frame, "dataVencimento_ini"));
        Date dataVencimento_fim = ERPData.toSqlDate(ERPFrames.getValorCampoFrame(frame, "dataVencimento_fim"));

        BigDecimal valor_ini = ERPValor.toBigDecimal(ERPFrames.getValorCampoFrame(frame, "valor_ini"));
        BigDecimal valor_fim = ERPValor.toBigDecimal(ERPFrames.getValorCampoFrame(frame, "valor_fim"));

        boolean radioTodos = ERPFrames.getValorCampoFrame(frame, "radioTodos").equalsIgnoreCase("true") ? true : false;
        boolean radioAbertos = ERPFrames.getValorCampoFrame(frame, "radioAbertos").equalsIgnoreCase("true") ? true : false;

        CTituloPagar.TIPO tipo;

        if (radioTodos) {
            tipo = CTituloPagar.TIPO.TODOS;
        } else if (radioAbertos) {
            tipo = CTituloPagar.TIPO.ABERTOS;
        } else {
            tipo = CTituloPagar.TIPO.PAGOS;
        }
        
        cancela(frame);

        titulosPorDia(CTituloPagar.listaTitulosPagar(
                tipo,
                idFornecedor,
                idtipotitulo,
                numero_ini, numero_fim,
                null, null,
                dataEmissao_ini, dataEmissao_fim,
                dataVencimento_ini, dataVencimento_fim,
                valor_ini, valor_fim,
                new String[]{"tipoTitulo", "correntista"}));

    }

    public void cancela(JInternalFrame frame) {
        ERPFrames.limparTodosCampos(frame);
        fornecedor = null;
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

    public void titulosPorDia(List<TituloPagar> titulo) {

        InputStream inputStream = getClass().getResourceAsStream("/erp/relatorios/TitutloPagarPorDia.jasper");

        Map parametros = new HashMap();

        // criando os dados que serão passados ao datasource
        List dados = new ArrayList();
        dados.addAll(titulo);

        // criando o datasource com os dados criados
        JRDataSource ds = new JRBeanCollectionDataSource(dados);

        try {
            // passando o datasource para o método de criação e exibição do relatório
            ERPRelatorios.openReport("Títulos por Dia", inputStream, parametros,
                    ds);
        } catch (JRException exc) {
            JOptionPane.showMessageDialog(null, exc.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
