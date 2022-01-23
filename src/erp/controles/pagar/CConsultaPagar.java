package erp.controles.pagar;

import erp.controles.cadastro.CCorrentista;
import erp.modelos.banco.Correntista;
import erp.modelos.banco.TituloPagar;
import erp.modelos.tablemodel.TituloPagarCompletoTableModel;
import erp.util.ERPData;
import erp.util.ERPFocusGrabber;
import erp.util.ERPFrames;
import erp.util.ERPValor;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.swing.JInternalFrame;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

/**
 * @author Arthur
 */
public class CConsultaPagar {

    private List<TituloPagar> itens;
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
        ERPFrames.setValorCampoFrame(frame, "quantidade", 0);
        ERPFrames.setValorCampoFrame(frame, "valor_total", 0);
        ERPFrames.setValorCampoFrame(frame, "valor_pago", 0);
        ERPFrames.setValorCampoFrame(frame, "valor_saldo", 0);        

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
        boolean radioPagos = ERPFrames.getValorCampoFrame(frame, "radioPagos").equalsIgnoreCase("true") ? true : false;

        CTituloPagar.TIPO tipo;

        if (radioTodos) {
            tipo = CTituloPagar.TIPO.TODOS;
        } else if (radioAbertos) {
            tipo = CTituloPagar.TIPO.ABERTOS;
        } else {
            tipo = CTituloPagar.TIPO.PAGOS;
        }

        itens = CTituloPagar.listaTitulosPagar(
                tipo,
                idFornecedor,
                idtipotitulo,
                numero_ini, numero_fim,
                null, null,
                dataEmissao_ini, dataEmissao_fim,
                dataVencimento_ini, dataVencimento_fim,
                valor_ini, valor_fim,
                new String[]{"tipoTitulo", "correntista"});

        try {
            ((TituloPagarCompletoTableModel) ((JTable) ERPFrames.getComponent(frame, "titulosTable")).getModel()).setLinhas(itens);
        } catch (Exception e) {
        }
        ERPFrames.setValorCampoFrame(frame, "quantidade", itens.size());

        BigDecimal valor_total = ((TituloPagarCompletoTableModel) ((JTable) ERPFrames.getComponent(frame, "titulosTable")).getModel()).getSomaCol(5);

        BigDecimal valor_pago = ((TituloPagarCompletoTableModel) ((JTable) ERPFrames.getComponent(frame, "titulosTable")).getModel()).getSomaCol(6);

        ERPFrames.setValorCampoFrame(frame, "valor_total", valor_total);
        ERPFrames.setValorCampoFrame(frame, "valor_pago", valor_pago);
        ERPFrames.setValorCampoFrame(frame, "valor_saldo", valor_total.subtract(valor_pago));
    }

    public void cancela(JInternalFrame frame) {
        ERPFrames.limparTodosCampos(frame);
        fornecedor = null;
        itens = null;
        try {
            ((TituloPagarCompletoTableModel) ((JTable) ERPFrames.getComponent(frame, "titulosTable")).getModel()).setLinhas(itens);
        } catch (Exception e) {
        }
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
}
