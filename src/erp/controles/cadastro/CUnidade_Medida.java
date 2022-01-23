package erp.controles.cadastro;

import erp.controles.CFrame;
import erp.modelos.banco.UnidadeMedida;
import erp.modelos.tablemodel.UnidadeMedidaTableModel;
import erp.util.ERPDados;
import erp.util.ERPFrames;
import erp.visoes.VPesquisa;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Expression;

/**
 * @author Arthur
 */
public class CUnidade_Medida {

    UnidadeMedida objeto;

    public void criticaIdUnidade(JInternalFrame frame) {            
        String id = ERPFrames.getValorCampo(ERPFrames.getComponent(frame, "idUnidade"));        
        if (!id.equals("")) {
            objeto = procuraUnidadeMedida(id);
            if (objeto != null) {
                objetoParaFrame(frame, objeto);
            }
        }
    }
    
     public void objetoParaFrame(JInternalFrame frame, UnidadeMedida objeto) {
         ERPFrames.objetoParaFrame(frame, objeto);
     }
     
     public UnidadeMedida frameParaObjeto(JInternalFrame frame, UnidadeMedida objeto) throws Exception {
         ERPFrames.frameParaObjeto(frame, objeto);
         return objeto;
     }     
     
    public void salva(JInternalFrame frame) {
        if (objeto == null) {
            objeto = new UnidadeMedida();
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
        
        ERPFrames.getComponent(frame, "idUnidade").requestFocus();
    }    

    public void pesquisaUnidade(JInternalFrame frame) {
        ERPFrames.getComponent(frame, "descricao").requestFocus();
        objeto = pesquisaTodasUnidades();
        if (objeto != null) {
            objetoParaFrame(frame, objeto);
        } else {
            ERPFrames.getComponent(frame, "idUnidade").requestFocus();
        }   
    }

    public static UnidadeMedida pesquisaTodasUnidades() {
        UnidadeMedida resultado;
        VPesquisa pesquisa = new VPesquisa(CFrame.getFrame(), "Unidade de Medida", true);
        try {
            pesquisa.setTableModel(new UnidadeMedidaTableModel(listaTodasUnidades()));
            pesquisa.setVisible(true);
            resultado = (UnidadeMedida) pesquisa.getResultado();
            return resultado;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }     
    }

    public void exclui(JInternalFrame frame) {
        String id = ERPFrames.getValorCampo(ERPFrames.getComponent(frame, "idUnidade"));
        if (!id.equals("")) {
            objeto = procuraUnidadeMedida(id);
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

    public static List listaTodasUnidades() {
        return ERPDados.consultaLista(UnidadeMedida.class, -1);
    }

    public static UnidadeMedida procuraUnidadeMedida(String idunidade) {
        return (UnidadeMedida) ERPDados.consultaObjeto(UnidadeMedida.class, (new Criterion[]{
            Expression.eq("idUnidade", idunidade)}), 1);
    }

    public static ComboBoxModel carregaUnidadeMedidas() {
        List<UnidadeMedida> lista = listaTodasUnidades();

        ComboBoxModel model;

        List vector = new ArrayList();

        vector.add(null);

        for (UnidadeMedida unidade : lista) {
            vector.add(unidade.getIdUnidade() + " - " + unidade.getDescricao());
        }

        model = new DefaultComboBoxModel(vector.toArray());

        return model;
    }
}
