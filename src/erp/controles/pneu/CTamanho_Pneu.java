/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package erp.controles.pneu;

import erp.controles.CFrame;
import erp.modelos.banco.TamanhoPneu;
import erp.modelos.tablemodel.TamanhoPneuTableModel;
import erp.util.ERPDados;
import erp.util.ERPFrames;
import erp.visoes.VPesquisa;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
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
public class CTamanho_Pneu {

    private TamanhoPneu objeto;

    public static TamanhoPneu procuraTamanhoPneu(int idtamanhopneu) {
        return (TamanhoPneu) ERPDados.consultaObjeto(TamanhoPneu.class, (new Criterion[]{Expression.eq("idtamanhopneu", idtamanhopneu)}), 1);
    }

    public static TamanhoPneu pesquisaTamanhoPneu() {
        TamanhoPneu resultado;
        VPesquisa pesquisa = new VPesquisa(CFrame.getFrame(), "Tamanho de Pneu", true);
        try {
            pesquisa.setTableModel(new TamanhoPneuTableModel(listaTamanhosPneu()));           
            pesquisa.setVisible(true);
            resultado = (TamanhoPneu) pesquisa.getResultado();
            return resultado;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private static List listaTamanhosPneu() {
        return ERPDados.consultaLista(TamanhoPneu.class, -1);
    }

    private void objetoParaFrame(JInternalFrame frame, TamanhoPneu objeto) {
        ERPFrames.objetoParaFrame(frame, objeto);
    }

    private TamanhoPneu frameParaObjeto(JInternalFrame frame, TamanhoPneu objeto) throws Exception {
        ERPFrames.frameParaObjeto(frame, objeto);
        objeto.setUsuario(CFrame.getUsuario());
        objeto.setDataInclusao(new Timestamp(System.currentTimeMillis()));        
        return objeto;
    }

    public void criticaId(JInternalFrame frame) {        
        String id = ERPFrames.getValorCampo(ERPFrames.getComponent(frame, "idtamanhopneu"));
        if (!id.equals("")) {
            objeto = procuraTamanhoPneu(Integer.parseInt(id));
            if (objeto != null) {
                objetoParaFrame(frame, objeto);
            } else {
                erroTamanhoPneu(frame, objeto);
                ERPFrames.setValorCampoFrame(frame, "idtamanhopneu", "");
                ERPFrames.setFocus(frame, "idtamanhopneu");
            }
        }        
    }

    public void salva(JInternalFrame frame) {
        if (objeto == null) {
            objeto = new TamanhoPneu();
        }

        try {
            objeto = frameParaObjeto(frame, objeto);

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

        ERPFrames.getComponent(frame, "idtamanhopneu").requestFocus();

    }

    public void exclui(JInternalFrame frame) {
        String id = ERPFrames.getValorCampo(ERPFrames.getComponent(frame, "idtamanhopneu"));
        if (!id.equals("")) {
            objeto = procuraTamanhoPneu(Integer.parseInt(id));
            if (objeto != null) {
                if (JOptionPane.showConfirmDialog(frame, "Tem certeza que deseja excluir?") == JOptionPane.YES_OPTION) {
                    try {
                        objeto = frameParaObjeto(frame, objeto);

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
        objeto = pesquisaTamanhoPneu();
        if (objeto != null) {
           objetoParaFrame(frame, objeto);
        } else {
            ERPFrames.getComponent(frame, "idtamanhopneu").requestFocus();
        }
    }

    public static boolean erroTamanhoPneu(JInternalFrame frame, TamanhoPneu erro) {
        if (erro == null) {
            JOptionPane.showMessageDialog(frame, "Tamanho de pneu não encontrada", "Erro", JOptionPane.WARNING_MESSAGE);
            return true;
        } else {
            return false;
        }
    }

    public static ComboBoxModel comboTamanhoPneu() {
        List<TamanhoPneu> lista = listaTamanhosPneu();

        ComboBoxModel model;

        List vector = new ArrayList();

        vector.add(null);

        if (lista != null) {
            for (TamanhoPneu objeto : lista) {
                vector.add(objeto.getIdtamanhopneu()+ " - " + objeto.getDescricao());
            }
        }

        model = new DefaultComboBoxModel(vector.toArray());

        return model;
    }
    
}
