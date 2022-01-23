/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package erp.controles.cadastro;

import erp.controles.CFrame;
import erp.modelos.banco.Produto;
import erp.util.ERPDados;
import erp.util.ERPFrames;
import erp.visoes.VPesquisa;
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
public class CProduto {

    private Produto produto;

    public void criticaProduto(JInternalFrame frame) {
        String idProduto = ERPFrames.getValorCampo(ERPFrames.getComponent(frame, "idProduto"));
        if (!idProduto.equals("")) {
            produto = procuraProduto(Integer.parseInt(idProduto));
            if (produto != null) {
                produtoParaFrame(frame, produto);
            } else {
                JOptionPane.showMessageDialog(frame, "Produto não encontrado", "Erro", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    public void salvaProduto(JInternalFrame frame) {
        if (produto == null) {
            produto = new Produto();
        }

        try {
            produto = frameParaProduto(frame, produto);

            ERPDados.gravaObjeto(produto);

            JOptionPane.showMessageDialog(frame, "Registro salvo com sucesso!");

            ERPFrames.limparTodosCampos(frame);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void cancelaProduto(JInternalFrame frame) {

        ERPFrames.limparTodosCampos(frame);

        produto = null;

        ERPFrames.getComponent(frame, "idProduto").requestFocus();

    }

    public void excluiProduto(JInternalFrame frame) {
        String idProduto = ERPFrames.getValorCampo(ERPFrames.getComponent(frame, "idProduto"));
        if (!idProduto.equals("")) {
            produto = procuraProduto(Integer.parseInt(idProduto));
            if (produto != null) {
                if (JOptionPane.showConfirmDialog(frame, "Tem certeza que deseja exclui?") == JOptionPane.YES_OPTION) {
                    try {
                        produto = frameParaProduto(frame, produto);

                        ERPDados.apagaObjeto(produto);

                        JOptionPane.showMessageDialog(frame, "Registro excluido com sucesso!");

                        cancelaProduto(frame);

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        }
    }

    public void pesquisaTodosProdutos(JInternalFrame frame) {
        produto = pesquisaProdutos();
        if (produto != null) {
            produtoParaFrame(frame, produto);
            ERPFrames.getComponent(frame, "descricao").requestFocus();
        } else {
            ERPFrames.getComponent(frame, "idProduto").requestFocus();
        }
    }

    public static ComboBoxModel carregaClassificaoProduto() {

        ComboBoxModel model;

        Vector vector = new Vector();

        vector.add(null);
        vector.add("MP - Matéria Prima");
        vector.add("PS - Produto Semi-Acabado");
        vector.add("PA - Protudo Acabado");
        vector.add("SE - Serviço");
        vector.add("EM - Embalagem");

        model = new DefaultComboBoxModel(vector);

        return model;
    }

    public static List listaTodosProdutos() {
        return ERPDados.consultaLista(Produto.class, -1);
    }

    public static Produto procuraProduto(int idProduto) {
        return (Produto) ERPDados.consultaObjeto(Produto.class, (new Criterion[]{Expression.eq("idProduto", idProduto)}), 1);
    }

    public static Produto pesquisaProdutos() {
        Produto resultado = null;
        VPesquisa pesquisa = new VPesquisa(CFrame.getFrame(), "Produtos", true);
        //pesquisa.setTableModel(new ProtudoTableModel(listaTodosProdutos()));
        pesquisa.setVisible(true);
        resultado = (Produto) pesquisa.getResultado();
        return resultado;
    }

    public static boolean erroProduto(JInternalFrame frame, Produto erroProduto) {
        if (erroProduto == null) {
            JOptionPane.showMessageDialog(frame, "Produto não encontrado", "Erro", JOptionPane.WARNING_MESSAGE);
            return true;
        } else if (!erroProduto.isFlagAtivo()) {
            JOptionPane.showMessageDialog(frame, "Produto desativado", "Erro", JOptionPane.WARNING_MESSAGE);
            return true;
        } else {
            return false;
        }
    }

    public void produtoParaFrame(JInternalFrame frame, Produto produto) {
        ERPFrames.objetoParaFrame(frame, produto);
        ERPFrames.setValorCampo(ERPFrames.getComponent(frame, "idunidade"), produto.getUnidadeMedida().getIdUnidade());
    }

    public Produto frameParaProduto(JInternalFrame frame, Produto produto) throws Exception {
        ERPFrames.frameParaObjeto(frame, produto);
        produto.setUsuario(CFrame.getUsuario());
        produto.setUnidadeMedida(CUnidade_Medida.procuraUnidadeMedida(ERPFrames.getValorCampo(ERPFrames.getComponent(frame, "idunidade"))));
        return produto;
    }
}
