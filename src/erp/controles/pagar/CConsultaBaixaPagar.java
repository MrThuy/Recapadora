package erp.controles.pagar;

import erp.controles.cadastro.CCorrentista;
import erp.modelos.banco.BaixaPagar;
import erp.modelos.banco.Correntista;
import erp.modelos.tablemodel.BaixaPagarCompletoTableModel;
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
public class CConsultaBaixaPagar {

    private List<BaixaPagar> itens;
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
        int idtipopagamento = ERPValor.toInt(ERPFrames.getValorCampoFrame(frame, "idtipopagamento"));

        int numero_ini = Integer.parseInt(ERPFrames.getValorCampoFrame(frame, "numero_ini"));
        int numero_fim = Integer.parseInt(ERPFrames.getValorCampoFrame(frame, "numero_fim"));

        Date data_ini = ERPData.toSqlDate(ERPFrames.getValorCampoFrame(frame, "data_ini"));
        Date data_fim = ERPData.toSqlDate(ERPFrames.getValorCampoFrame(frame, "data_fim"));

        BigDecimal valor_ini = ERPValor.toBigDecimal(ERPFrames.getValorCampoFrame(frame, "valor_ini"));
        BigDecimal valor_fim = ERPValor.toBigDecimal(ERPFrames.getValorCampoFrame(frame, "valor_fim"));

        itens = CBaixaPagar.listaBaixasPagar(
                idFornecedor, 
                idtipotitulo, 
                0, 
                numero_ini, numero_fim,
                null,
                idtipopagamento, 
                data_ini, data_fim, 
                valor_ini, valor_fim, new String[]{"idcorrentista","tipoPagamento"});
        try {
            ((BaixaPagarCompletoTableModel) ((JTable) ERPFrames.getComponent(frame, "titulosTable")).getModel()).setLinhas(itens);
        } catch (Exception e) {
        }
    }

    public void cancela(JInternalFrame frame) {
        ERPFrames.limparTodosCampos(frame);
        fornecedor = null;
        itens = null;
        try {
            ((BaixaPagarCompletoTableModel) ((JTable) ERPFrames.getComponent(frame, "titulosTable")).getModel()).setLinhas(itens);
        } catch (Exception e) {
        }
        ERPFrames.setFocus(frame, "idcorrentista");
        ERPFrames.setValorCampoFrame(frame, "numero_ini", 0);
        ERPFrames.setValorCampoFrame(frame, "numero_fim", 9999999);
        Calendar calendar = new GregorianCalendar();
        Date hj = new Date(System.currentTimeMillis());
        calendar.setTime(hj);
        calendar.add(Calendar.YEAR, -1);
        ERPFrames.setValorCampoFrame(frame, "data_ini", calendar.getTime());
        ERPFrames.setValorCampoFrame(frame, "data_fim", hj);
        ERPFrames.setValorCampoFrame(frame, "valor_ini", 0);
        ERPFrames.setValorCampoFrame(frame, "valor_fim", "999.999,99");
    }
}
