/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package erp.controles.cadastro;

import erp.controles.CFrame;
import erp.modelos.banco.TipoPagamento;
import erp.modelos.tablemodel.TipoPagamentoTableModel;
import erp.util.ERPDados;
import erp.util.ERPFrames;
import erp.visoes.VPesquisa;
import java.sql.Timestamp;
import java.util.List;
import java.util.Vector;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Expression;

/**
 *
 * @author Cliente
 */
public class CTipoPagamento {

    private TipoPagamento objeto;

    public enum TIPO {

        TODOS,
        ATIVOS
    }

    public static TipoPagamento procuraTipoPagamento(int idTipoPagamento) {
        return (TipoPagamento) ERPDados.consultaObjeto(TipoPagamento.class, (new Criterion[]{Expression.eq("idtipopagamento", idTipoPagamento)}), 1);
    }

    public static TipoPagamento pesquisaTipoPagamento(TIPO tipo) {
        TipoPagamento resultado;
        VPesquisa pesquisa = new VPesquisa(CFrame.getFrame(), "Tipo de Pagamento", true);
        try {
            switch (tipo) {
                case TODOS:
                    pesquisa.setTableModel(new TipoPagamentoTableModel(listaTodosTiposPagamentos()));
                    break;
                case ATIVOS:
                    pesquisa.setTableModel(new TipoPagamentoTableModel(listaTodosTiposPagamentosAtivos()));
                    break;
                default:
                    pesquisa.setTableModel(new TipoPagamentoTableModel(null));
                    break;
            }
            pesquisa.setVisible(true);
            resultado = (TipoPagamento) pesquisa.getResultado();
            return resultado;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private static List listaTodosTiposPagamentos() {
        return ERPDados.consultaLista(TipoPagamento.class, -1);
    }

    private static List listaTodosTiposPagamentosAtivos() {
        return ERPDados.consultaLista(TipoPagamento.class, (new Criterion[]{
                    Expression.eq("flagAtivo", true)}), -1);
    }

    private void TipoPagamentoParaFrame(JInternalFrame frame, TipoPagamento objeto) {
        ERPFrames.objetoParaFrame(frame, objeto);
    }

    private TipoPagamento frameParaTipoPagamento(JInternalFrame frame, TipoPagamento objeto) throws Exception {
        ERPFrames.frameParaObjeto(frame, objeto);
        objeto.setUsuario(CFrame.getUsuario());
        objeto.setDataInclusao(new Timestamp(System.currentTimeMillis()));
        return objeto;
    }

    public void criticaId(JInternalFrame frame) {
        String id = ERPFrames.getValorCampo(ERPFrames.getComponent(frame, "idTipoPagamento"));
        if (!id.equals("")) {
            objeto = procuraTipoPagamento(Integer.parseInt(id));
            if (objeto != null) {
                TipoPagamentoParaFrame(frame, objeto);
            } else {
                JOptionPane.showMessageDialog(frame, "Tipo de pagamento não encontrado", "Erro", JOptionPane.WARNING_MESSAGE);
                ERPFrames.setValorCampoFrame(frame, "idTipoPagamento", "");
                ERPFrames.setFocus(frame, "idTipoPagamento");
            }
        }
    }

    public void salva(JInternalFrame frame) {
        if (objeto == null) {
            objeto = new TipoPagamento();
        }

        try {
            objeto = frameParaTipoPagamento(frame, objeto);

            ERPDados.gravaObjeto(objeto);

            JOptionPane.showMessageDialog(frame, "Registro salvo com sucesso!");

            cancela(frame);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void cancela(JInternalFrame frame) {

        ERPFrames.limparTodosCampos(frame);

        objeto = null;

        ERPFrames.setValorCampoFrame(frame, "flagAtivo", true);

        ERPFrames.setFocus(frame, "idTipoPagamento");

    }

    public void exclui(JInternalFrame frame) {
        String id = ERPFrames.getValorCampo(ERPFrames.getComponent(frame, "idTipoPagamento"));
        if (!id.equals("")) {
            objeto = procuraTipoPagamento(Integer.parseInt(id));
            if (objeto != null) {
                if (JOptionPane.showConfirmDialog(frame, "Tem certeza que deseja excluir?") == JOptionPane.YES_OPTION) {
                    try {
                        objeto = frameParaTipoPagamento(frame, objeto);

                        ERPDados.apagaObjeto(objeto);

                        JOptionPane.showMessageDialog(frame, "Registro excluido com sucesso!");

                        cancela(frame);

                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(frame, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }

    public void pesquisa(JInternalFrame frame) {
        ERPFrames.getComponent(frame, "descricao").requestFocus();
        objeto = pesquisaTipoPagamento(TIPO.TODOS);
        if (objeto != null) {
            TipoPagamentoParaFrame(frame, objeto);
        } else {
            ERPFrames.setFocus(frame, "idTipoPagamento");
        }
    }

    public static boolean erroTipoPagamento(JInternalFrame frame, TipoPagamento erro) {
        if (erro == null) {
            JOptionPane.showMessageDialog(frame, "Tipo de Título não encontrado", "Erro", JOptionPane.WARNING_MESSAGE);
            return true;
        } else if (!erro.getFlagAtivo()) {
            JOptionPane.showMessageDialog(frame, "Tipo de Título inativo", "Erro", JOptionPane.WARNING_MESSAGE);
            return true;
        } else {
            return false;
        }
    }

    public static ComboBoxModel comboTipoPagamento() {
        List<TipoPagamento> lista = listaTodosTiposPagamentos();

        ComboBoxModel model;

        Vector vector = new Vector();

        vector.add(null);

        if (lista != null) {
            for (TipoPagamento TipoPagamento : lista) {
                vector.add(TipoPagamento.getIdtipopagamento() + " - " + TipoPagamento.getDescricao());
            }
        }

        model = new DefaultComboBoxModel(vector);

        return model;
    }
}
