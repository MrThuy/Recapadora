package erp.controles.receber;

import erp.controles.cadastro.CCorrentista;
import erp.modelos.banco.BaixaReceber;
import erp.modelos.banco.Correntista;
import erp.modelos.tablemodel.BaixaReceberCompletoTableModel;
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
public class CConsultaBaixaReceber {

    private List<BaixaReceber> itens;
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

    public void salva(JInternalFrame frame) {

        int idCliente = ERPValor.toInt(ERPFrames.getValorCampoFrame(frame, "idcorrentista"));
        int idtipotitulo = ERPValor.toInt(ERPFrames.getValorCampoFrame(frame, "idtipotitulo"));
        int idtipopagamento = ERPValor.toInt(ERPFrames.getValorCampoFrame(frame, "idtipopagamento"));

        int numero_ini = Integer.parseInt(ERPFrames.getValorCampoFrame(frame, "numero_ini"));
        int numero_fim = Integer.parseInt(ERPFrames.getValorCampoFrame(frame, "numero_fim"));

        Date data_ini = ERPData.toSqlDate(ERPFrames.getValorCampoFrame(frame, "data_ini"));
        Date data_fim = ERPData.toSqlDate(ERPFrames.getValorCampoFrame(frame, "data_fim"));

        BigDecimal valor_ini = ERPValor.toBigDecimal(ERPFrames.getValorCampoFrame(frame, "valor_ini"));
        BigDecimal valor_fim = ERPValor.toBigDecimal(ERPFrames.getValorCampoFrame(frame, "valor_fim"));

        itens = CBaixaReceber.listaBaixaReceber(
                idCliente, 
                idtipotitulo, 
                0, 
                numero_ini, numero_fim,
                null,
                idtipopagamento, 
                data_ini, data_fim, 
                valor_ini, valor_fim, new String[]{"idcorrentista","tipoPagamento"});
        try {
            ((BaixaReceberCompletoTableModel) ((JTable) ERPFrames.getComponent(frame, "titulosTable")).getModel()).setLinhas(itens);
        } catch (Exception e) {
        }
    }

    public void cancela(JInternalFrame frame) {
        ERPFrames.limparTodosCampos(frame);
        cliente = null;
        itens = null;
        try {
            ((BaixaReceberCompletoTableModel) ((JTable) ERPFrames.getComponent(frame, "titulosTable")).getModel()).setLinhas(itens);
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
