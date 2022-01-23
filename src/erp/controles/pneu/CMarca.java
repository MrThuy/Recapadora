/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package erp.controles.pneu;

import erp.controles.CFrame;
import erp.modelos.banco.Marca;
import erp.modelos.tablemodel.MarcaTableModel;
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
public class CMarca {

    private Marca objeto;

    public static Marca procuraMarca(int idmarca) {
        return (Marca) ERPDados.consultaObjeto(Marca.class, (new Criterion[]{Expression.eq("idmarca", idmarca)}), 1);
    }

    public static Marca pesquisaMarca() {
        Marca resultado;
        VPesquisa pesquisa = new VPesquisa(CFrame.getFrame(), "Marca", true);
        try {
            pesquisa.setTableModel(new MarcaTableModel(listaMarcas()));           
            pesquisa.setVisible(true);
            resultado = (Marca) pesquisa.getResultado();
            return resultado;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private static List listaMarcas() {
        return ERPDados.consultaLista(Marca.class, -1);
    }

    private void objetoParaFrame(JInternalFrame frame, Marca objeto) {
        ERPFrames.objetoParaFrame(frame, objeto);
    }

    private Marca frameParaObjeto(JInternalFrame frame, Marca objeto) throws Exception {
        ERPFrames.frameParaObjeto(frame, objeto);
        objeto.setUsuario(CFrame.getUsuario());
        objeto.setDataInclusao(new Timestamp(System.currentTimeMillis()));        
        return objeto;
    }

    public void criticaId(JInternalFrame frame) {        
        String id = ERPFrames.getValorCampo(ERPFrames.getComponent(frame, "idmarca"));
        if (!id.equals("")) {
            objeto = procuraMarca(Integer.parseInt(id));
            if (objeto != null) {
                objetoParaFrame(frame, objeto);
            } else {
                erroMarca(frame, objeto);
                ERPFrames.setValorCampoFrame(frame, "idmarca", "");
                ERPFrames.setFocus(frame, "idmarca");
            }
        }        
    }

    public void salva(JInternalFrame frame) {
        if (objeto == null) {
            objeto = new Marca();
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

        ERPFrames.getComponent(frame, "idmarca").requestFocus();

    }

    public void exclui(JInternalFrame frame) {
        String id = ERPFrames.getValorCampo(ERPFrames.getComponent(frame, "idmarca"));
        if (!id.equals("")) {
            objeto = procuraMarca(Integer.parseInt(id));
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
        objeto = pesquisaMarca();
        if (objeto != null) {
           objetoParaFrame(frame, objeto);
        } else {
            ERPFrames.getComponent(frame, "idmarca").requestFocus();
        }
    }

    public static boolean erroMarca(JInternalFrame frame, Marca erro) {
        if (erro == null) {
            JOptionPane.showMessageDialog(frame, "Marca não encontrada", "Erro", JOptionPane.WARNING_MESSAGE);
            return true;
        } else {
            return false;
        }
    }

    public static ComboBoxModel comboMarca() {
        List<Marca> lista = listaMarcas();

        ComboBoxModel model;

        List vector = new ArrayList();

        vector.add(null);

        if (lista != null) {
            for (Marca objeto : lista) {
                vector.add(objeto.getIdmarca()+ " - " + objeto.getDescricao());
            }
        }

        model = new DefaultComboBoxModel(vector.toArray());

        return model;
    }
    
}
