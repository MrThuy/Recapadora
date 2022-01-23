/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package erp.controles.cadastro;

import erp.controles.CFrame;
import erp.modelos.banco.TipoTitulo;
import erp.modelos.tablemodel.TipoTituloTableModel;
import erp.util.ERPDados;
import erp.util.ERPFrames;
import erp.visoes.VPesquisa;
import java.sql.Timestamp;
import java.util.ArrayList;
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
public class CTipoTitulo {

    private TipoTitulo objeto;

    public enum TIPO {

        TODOS,
        RECEBER,
        PAGAR,
        ATIVOS
    }

    public static TipoTitulo procuraTipoTitulo(int idtipotitulo) {
        return (TipoTitulo) ERPDados.consultaObjeto(TipoTitulo.class, (new Criterion[]{Expression.eq("idtipotitulo", idtipotitulo)}), 1);
    }

    public static TipoTitulo pesquisaTipoTitulo(TIPO tipo) {
        TipoTitulo resultado;
        VPesquisa pesquisa = new VPesquisa(CFrame.getFrame(), "Tipo de Título", true);
        try {
            if (tipo.equals(TIPO.TODOS)) {
                pesquisa.setTableModel(new TipoTituloTableModel(listaTodosTiposTitulos()));
            } else if (tipo.equals(TIPO.RECEBER)) {
                pesquisa.setTableModel(new TipoTituloTableModel(listaTodosTiposTitulosReceber()));
            } else if (tipo.equals(TIPO.PAGAR)) {
                pesquisa.setTableModel(new TipoTituloTableModel(listaTodosTiposTitulosPagar()));
            } else if (tipo.equals(TIPO.ATIVOS)) {
                pesquisa.setTableModel(new TipoTituloTableModel(listaTodosTiposTitulosAtivos()));
            }
            pesquisa.setVisible(true);
            resultado = (TipoTitulo) pesquisa.getResultado();
            return resultado;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private static List listaTodosTiposTitulos() {
        return ERPDados.consultaLista(TipoTitulo.class, -1);
    }

    private static List listaTodosTiposTitulosReceber() {
        return ERPDados.consultaLista(TipoTitulo.class, (new Criterion[]{
                    Expression.eq("flagReceber", true),
                    Expression.eq("flagAtivo", true)}), -1);
    }

    private static List listaTodosTiposTitulosPagar() {
        return ERPDados.consultaLista(TipoTitulo.class, (new Criterion[]{
                    Expression.eq("flagPagar", true),
                    Expression.eq("flagAtivo", true)}), -1);
    }

    private static List listaTodosTiposTitulosAtivos() {
        return ERPDados.consultaLista(TipoTitulo.class, (new Criterion[]{
                    Expression.eq("flagAtivo", true)}), -1);
    }

    private void tipotituloParaFrame(JInternalFrame frame, TipoTitulo objeto) {
        ERPFrames.objetoParaFrame(frame, objeto);
    }

    private TipoTitulo frameParaTipoTitulo(JInternalFrame frame, TipoTitulo objeto) throws Exception {
        ERPFrames.frameParaObjeto(frame, objeto);
        objeto.setUsuario(CFrame.getUsuario());
        objeto.setDataInclusao(new Timestamp(System.currentTimeMillis()));        
        return objeto;
    }

    public void criticaId(JInternalFrame frame) {
        String id = ERPFrames.getValorCampo(ERPFrames.getComponent(frame, "idtipotitulo"));
        if (!id.equals("")) {
            objeto = procuraTipoTitulo(Integer.parseInt(id));
            if (objeto != null) {
                tipotituloParaFrame(frame, objeto);
            } else {
                JOptionPane.showMessageDialog(frame, "Tipo de título não encontrado", "Erro", JOptionPane.WARNING_MESSAGE);
                ERPFrames.setValorCampoFrame(frame, "idtipotitulo", "");
                ERPFrames.getComponent(frame, "idtipotitulo").requestFocus();
            }
        }
    }

    public void salva(JInternalFrame frame) {
        if (objeto == null) {
            objeto = new TipoTitulo();
        }

        try {
            objeto = frameParaTipoTitulo(frame, objeto);

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

        ERPFrames.getComponent(frame, "idtipotitulo").requestFocus();

    }

    public void exclui(JInternalFrame frame) {
        String id = ERPFrames.getValorCampo(ERPFrames.getComponent(frame, "idtipotitulo"));
        if (!id.equals("")) {
            objeto = procuraTipoTitulo(Integer.parseInt(id));
            if (objeto != null) {
                if (JOptionPane.showConfirmDialog(frame, "Tem certeza que deseja excluir?") == JOptionPane.YES_OPTION) {
                    try {
                        objeto = frameParaTipoTitulo(frame, objeto);

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
        objeto = pesquisaTipoTitulo(TIPO.TODOS);
        if (objeto != null) {
            tipotituloParaFrame(frame, objeto);
        } else {
            ERPFrames.getComponent(frame, "idtipotitulo").requestFocus();
        }
    }

    public static boolean erroTipoTitulo(JInternalFrame frame, TipoTitulo erro) {
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

    public static boolean erroTipoTituloReceber(JInternalFrame frame, TipoTitulo erro) {
        if (erro == null) {
            JOptionPane.showMessageDialog(frame, "Tipo de Título não encontrado", "Erro", JOptionPane.WARNING_MESSAGE);
            return true;
        } else if (!erro.getFlagReceber()) {
            JOptionPane.showMessageDialog(frame, "Tipo de Título não do receber", "Erro", JOptionPane.WARNING_MESSAGE);
            return true;
        } else if (!erro.getFlagAtivo()) {
            JOptionPane.showMessageDialog(frame, "Tipo de Título inativo", "Erro", JOptionPane.WARNING_MESSAGE);
            return true;
        } else {
            return false;
        }
    }

    public static boolean erroTipoTituloPagar(JInternalFrame frame, TipoTitulo erro) {
        if (erro == null) {
            JOptionPane.showMessageDialog(frame, "Tipo de Título não encontrado", "Erro", JOptionPane.WARNING_MESSAGE);
            return true;
        } else if (!erro.getFlagPagar()) {
            JOptionPane.showMessageDialog(frame, "Tipo de Título não é do pagar", "Erro", JOptionPane.WARNING_MESSAGE);
            return true;
        } else if (!erro.getFlagAtivo()) {
            JOptionPane.showMessageDialog(frame, "Tipo de Título inativo", "Erro", JOptionPane.WARNING_MESSAGE);
            return true;
        } else {
            return false;
        }
    }

    public static ComboBoxModel comboTipoTituloReceber() {
        List<TipoTitulo> lista = listaTodosTiposTitulosReceber();

        ComboBoxModel model;

        Vector vector = new Vector();

        vector.add(null);

        if (lista != null) {
            for (TipoTitulo tipoTitulo : lista) {
                vector.add(tipoTitulo.getIdtipotitulo() + " - " + tipoTitulo.getDescricao());
            }
        }

        model = new DefaultComboBoxModel(vector);

        return model;
    }

    public static ComboBoxModel comboTipoTituloPagar() {
        List<TipoTitulo> lista = listaTodosTiposTitulosPagar();

        ComboBoxModel model;

        //Vector vector = new Vector();
        List vector = new ArrayList();

        vector.add(null);

        if (lista != null) {
            for (TipoTitulo tipoTitulo : lista) {
                //itens.add(tipoTitulo.getIdtipotitulo() + " - " + tipoTitulo.getDescricao());
                vector.add(tipoTitulo.getIdtipotitulo() + " - " + tipoTitulo.getDescricao());
            }
        }

        model = new DefaultComboBoxModel(vector.toArray());

        return model;
    }
}
