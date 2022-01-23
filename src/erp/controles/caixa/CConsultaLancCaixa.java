package erp.controles.caixa;

import erp.controles.cadastro.CConta;
import erp.controles.cadastro.CCorrentista;
import erp.modelos.banco.Conta;
import erp.modelos.banco.Correntista;
import erp.modelos.banco.LancamentoCaixa;
import erp.modelos.tablemodel.LancamentoCaixaCompletoTableModel;
import erp.util.ERPData;
import erp.util.ERPFocusGrabber;
import erp.util.ERPFrames;
import erp.util.ERPValor;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import javax.swing.JInternalFrame;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

/**
 * @author Arthur
 */
public class CConsultaLancCaixa {

    private List<LancamentoCaixa> itens;
    private Correntista empresa;
    private Conta conta;
    
    public void criticaEmpresa(JInternalFrame frame) {
        String idEmpresa = ERPFrames.getValorCampoFrame(frame, "idempresa");
        if (idEmpresa != null) {
            empresa = CCorrentista.procuraCorrentista(Integer.parseInt(idEmpresa));
            if (CCorrentista.erroEmpresa(frame, empresa)) {
                ERPFrames.setValorCampoFrame(frame, "idempresa", "");
                ERPFrames.setFocus(frame, "idempresa");
            }
        } else {
            ERPFrames.setValorCampoFrame(frame, "idempresa", "");
            ERPFrames.setFocus(frame, "idempresa");
        }
    }    

    public void pesquisaConta(JInternalFrame frame) {
        ERPFrames.setFocus(frame, "numero_ini");
        conta = CConta.pesquisaConta(CConta.TIPO.ATIVOS);
        if (conta != null) {
            contaParaFrame(frame);
        } 
//        else {
//            ERPFrames.setFocus(frame, "idconta");
//        }
    }

    public void criticaConta(JInternalFrame frame) {
        String idconta = ERPFrames.getValorCampoFrame(frame, "idconta");
        if (idconta.trim().length() > 0) {
            conta = CConta.procuraConta(Integer.parseInt(idconta));
            if (!CConta.erroConta(frame, conta)) {
                contaParaFrame(frame);
            } else {
                ERPFrames.setValorCampoFrame(frame, "idconta", "");
                SwingUtilities.invokeLater(new ERPFocusGrabber(ERPFrames.getComponent(frame, "idconta")));
            }
        }
    }

    private void contaParaFrame(JInternalFrame frame) {
        ERPFrames.objetoParaFrame(frame, conta);
        ERPFrames.setFocus(frame, "numero_ini");
    }

    public void salva(JInternalFrame frame) {
        ERPFrames.setValorCampoFrame(frame, "quantidade", 0);
        ERPFrames.setValorCampoFrame(frame, "total_entrada", 0);
        ERPFrames.setValorCampoFrame(frame, "total_saida", 0);
        ERPFrames.setValorCampoFrame(frame, "valor_saldo", 0);        

        int idempresa = ERPValor.toInt(ERPFrames.getValorCampoFrame(frame, "idempresa"));
        Integer idconta = ERPValor.toInt(ERPFrames.getValorCampoFrame(frame, "idconta"));

        int numero_ini = Integer.parseInt(ERPFrames.getValorCampoFrame(frame, "numero_ini"));
        int numero_fim = Integer.parseInt(ERPFrames.getValorCampoFrame(frame, "numero_fim"));

        Date data_ini = ERPData.toSqlDate(ERPFrames.getValorCampoFrame(frame, "data_ini"));
        Date data_fim = ERPData.toSqlDate(ERPFrames.getValorCampoFrame(frame, "data_fim"));

        BigDecimal valor_ini = ERPValor.toBigDecimal(ERPFrames.getValorCampoFrame(frame, "valor_ini"));
        BigDecimal valor_fim = ERPValor.toBigDecimal(ERPFrames.getValorCampoFrame(frame, "valor_fim"));

        itens = CLancamentoCaixaS.listaLancamentosCaixa(                
                idempresa, 
                data_ini, data_fim, 
                numero_ini, numero_fim, 
                idconta, 
                valor_ini, valor_fim, 
                new String[]{"conta"});

        try {
            ((LancamentoCaixaCompletoTableModel) ((JTable) ERPFrames.getComponent(frame, "jTable")).getModel()).setLinhas(itens);
        } catch (Exception e) {
        }
        ERPFrames.setValorCampoFrame(frame, "quantidade", itens.size());

        BigDecimal total_entrada = ((LancamentoCaixaCompletoTableModel) ((JTable) ERPFrames.getComponent(frame, "jTable")).getModel()).getSomaCol(5);

        BigDecimal total_saida = ((LancamentoCaixaCompletoTableModel) ((JTable) ERPFrames.getComponent(frame, "jTable")).getModel()).getSomaCol(6);

        ERPFrames.setValorCampoFrame(frame, "total_entrada", total_entrada);
        ERPFrames.setValorCampoFrame(frame, "total_saida", total_saida);
        ERPFrames.setValorCampoFrame(frame, "valor_saldo", total_entrada.subtract(total_saida));
    }

    public void cancela(JInternalFrame frame) {
        ERPFrames.limparTodosCampos(frame);
        empresa = null;
        conta = null;
        itens = null;
        try {
            ((LancamentoCaixaCompletoTableModel) ((JTable) ERPFrames.getComponent(frame, "jTable")).getModel()).limpar();
        } catch (Exception e) {
        }
        ERPFrames.setFocus(frame, "idempresa");
        ERPFrames.setValorCampoFrame(frame, "numero_ini", 0);
        ERPFrames.setValorCampoFrame(frame, "numero_fim", 9999999);
        ERPFrames.setValorCampoFrame(frame, "data_ini",  ERPData.somaMes(ERPData.agora(), -1));
        ERPFrames.setValorCampoFrame(frame, "data_fim", ERPData.agora());        
        ERPFrames.setValorCampoFrame(frame, "valor_ini", 0);
        ERPFrames.setValorCampoFrame(frame, "valor_fim", "999.999,99");
    }
}
