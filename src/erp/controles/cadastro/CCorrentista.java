/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package erp.controles.cadastro;

import erp.controles.CFrame;
import erp.modelos.banco.Correntista;
import erp.modelos.tablemodel.CorrentistaTableModel;
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
public class CCorrentista {

    private Correntista correntista;

    public enum TIPO {

        TODOS,
        CLIENTE,
        FORNECEDOR,
        FUNCIONARIO,
        TRANPORTADOR,
        FINANCEIRA,
        EMPRESA,
        ATIVOS
    }

    public static Correntista procuraCorrentista(int idCorrentista) {
        return (Correntista) ERPDados.consultaObjeto(Correntista.class, (new Criterion[]{Expression.eq("idcorrentista", idCorrentista)}), 1);

    }

    public static Correntista pesquisaCorrentistas(TIPO tipo) {
        Correntista resultado;
        VPesquisa pesquisa;        
        try {
            if (tipo.equals(TIPO.TODOS)) {
                pesquisa = new VPesquisa(CFrame.getFrame(), "Correntista", true);
                pesquisa.setTableModel(new CorrentistaTableModel(listaTodosCorrentistas()));
            } else {
                pesquisa = new VPesquisa(CFrame.getFrame(), "Correntista", true, CFrame.VISOES.CORRENTISTA);

                if (tipo.equals(TIPO.CLIENTE)) {
                    pesquisa.setTableModel(new CorrentistaTableModel(listaTodosClientes()));
                } else if (tipo.equals(TIPO.FORNECEDOR)) {
                    pesquisa.setTableModel(new CorrentistaTableModel(listaTodosFornecedores()));
                } else if (tipo.equals(TIPO.FUNCIONARIO)) {
                    pesquisa.setTableModel(new CorrentistaTableModel(listaTodosFuncionarios()));
                } else if (tipo.equals(TIPO.TRANPORTADOR)) {
                    pesquisa.setTableModel(new CorrentistaTableModel(listaTodosTransportadores()));
                } else if (tipo.equals(TIPO.FINANCEIRA)) {
                    pesquisa.setTableModel(new CorrentistaTableModel(listaTodasFinanceiras()));
                } else if (tipo.equals(TIPO.EMPRESA)) {
                    pesquisa.setTableModel(new CorrentistaTableModel(listaTodasEmpresas()));
                } else if (tipo.equals(TIPO.ATIVOS)) {
                    pesquisa.setTableModel(new CorrentistaTableModel(listaTodosCorrentistasAtivos()));
                }
            }
            pesquisa.setVisible(true);
            resultado = (Correntista) pesquisa.getResultado();
            return resultado;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private static List listaTodosCorrentistas() {
        return ERPDados.consultaLista(Correntista.class, -1);
    }

    private static List listaTodosClientes() {
        return ERPDados.consultaLista(Correntista.class, (new Criterion[]{
                    Expression.eq("flagCliente", true),
                    Expression.eq("flagAtivo", true)}), -1);
    }

    private static List listaTodosFornecedores() {
        return ERPDados.consultaLista(Correntista.class, (new Criterion[]{
                    Expression.eq("flagFornecedor", true),
                    Expression.eq("flagAtivo", true)}), -1);
    }

    private static List listaTodosFuncionarios() {
        return ERPDados.consultaLista(Correntista.class, (new Criterion[]{
                    Expression.eq("flagFuncionario", true),
                    Expression.eq("flagAtivo", true)}), -1);
    }

    private static List listaTodosTransportadores() {
        return ERPDados.consultaLista(Correntista.class, (new Criterion[]{
                    Expression.eq("flagTransportador", true),
                    Expression.eq("flagAtivo", true)}), -1);
    }

    private static List listaTodasFinanceiras() {
        return ERPDados.consultaLista(Correntista.class, (new Criterion[]{
                    Expression.eq("flagFinanceira", true),
                    Expression.eq("flagAtivo", true)}), -1);
    }

    private static List listaTodasEmpresas() {
        return ERPDados.consultaLista(Correntista.class, (new Criterion[]{
                    Expression.eq("flagEmpresa", true),
                    Expression.eq("flagAtivo", true)}), -1);
    }

    private static List listaTodosCorrentistasAtivos() {
        return ERPDados.consultaLista(Correntista.class, (new Criterion[]{
                    Expression.eq("flagAtivo", true)}), -1);
    }

    private void correntistaParaFrame(JInternalFrame frame, Correntista correntista) {
        ERPFrames.objetoParaFrame(frame, correntista);
    }

    private Correntista frameParaCorrentista(JInternalFrame frame, Correntista correntista) throws Exception {
        ERPFrames.frameParaObjeto(frame, correntista);
        correntista.setUsuario(CFrame.getUsuario());
        correntista.setDataInclusao(new Timestamp(System.currentTimeMillis()));
        return correntista;
    }

    public void criticaId(JInternalFrame frame) {
        String idCorrentista = ERPFrames.getValorCampo(ERPFrames.getComponent(frame, "idcorrentista"));
        if (!idCorrentista.equals("")) {
            correntista = procuraCorrentista(Integer.parseInt(idCorrentista));
            if (correntista != null) {
                correntistaParaFrame(frame, correntista);
            } else {
                JOptionPane.showMessageDialog(frame, "Correntista não encontrado", "Erro", JOptionPane.WARNING_MESSAGE);
                ERPFrames.setValorCampoFrame(frame, "idCorrentista", "");
                ERPFrames.setFocus(frame, "idCorrentista");
            }
        }
    }

    public void salva(JInternalFrame frame) {
        if (correntista == null) {
            correntista = new Correntista();
        }

        try {
            correntista = frameParaCorrentista(frame, correntista);

            ERPDados.gravaObjeto(correntista);

            JOptionPane.showMessageDialog(frame, "Registro salvo com sucesso!");

            cancela(frame);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void cancela(JInternalFrame frame) {

        ERPFrames.limparTodosCampos(frame);

        correntista = null;

        ERPFrames.setValorCampoFrame(frame, "flagAtivo", true);

        ERPFrames.setFocus(frame, "idCorrentista");

    }

    public void exclui(JInternalFrame frame) {
        String id = ERPFrames.getValorCampo(ERPFrames.getComponent(frame, "idCorrentista"));
        if (!id.equals("")) {
            correntista = procuraCorrentista(Integer.parseInt(id));
            if (correntista != null) {
                if (JOptionPane.showConfirmDialog(frame, "Tem certeza que deseja excluir?") == JOptionPane.YES_OPTION) {
                    try {
                        correntista = frameParaCorrentista(frame, correntista);

                        ERPDados.apagaObjeto(correntista);

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
        ERPFrames.setFocus(frame, "nome");
        correntista = pesquisaCorrentistas(TIPO.TODOS);
        if (correntista != null) {
            correntistaParaFrame(frame, correntista);
        } else {
            ERPFrames.setFocus(frame, "idCorrentista");
        }
    }

    public static boolean erroCorrentista(JInternalFrame frame, Correntista erro) {
        if (erro == null) {
            JOptionPane.showMessageDialog(frame, "Correntista não encontrado", "Erro", JOptionPane.WARNING_MESSAGE);
            return true;
        } else if (!erro.isFlagAtivo()) {
            JOptionPane.showMessageDialog(frame, "Correntista inativo", "Erro", JOptionPane.WARNING_MESSAGE);
            return true;
        } else {
            return false;
        }
    }

    public static boolean erroCliente(JInternalFrame frame, Correntista erro) {
        if (erro == null) {
            JOptionPane.showMessageDialog(frame, "Cliente não encontrado", "Erro", JOptionPane.WARNING_MESSAGE);
            return true;
        } else if (!erro.isFlagAtivo()) {
            JOptionPane.showMessageDialog(frame, "Cliente inativo", "Erro", JOptionPane.WARNING_MESSAGE);
            return true;
        } else if (!erro.isFlagCliente()) {
            JOptionPane.showMessageDialog(frame, "Correntista não é um cliente", "Erro", JOptionPane.WARNING_MESSAGE);
            return true;
        } else {
            return false;
        }
    }

    public static boolean erroFornecedor(JInternalFrame frame, Correntista erro) {
        if (erro == null) {
            JOptionPane.showMessageDialog(frame, "Fornecedor não encontrado", "Erro", JOptionPane.WARNING_MESSAGE);
            return true;
        } else if (!erro.isFlagAtivo()) {
            JOptionPane.showMessageDialog(frame, "Fornecedor inativo", "Erro", JOptionPane.WARNING_MESSAGE);
            return true;
        } else if (!erro.isFlagFornecedor()) {
            JOptionPane.showMessageDialog(frame, "Correntista não é um fornecedor", "Erro", JOptionPane.WARNING_MESSAGE);
            return true;
        } else {
            return false;
        }
    }

    public static boolean erroFuncionario(JInternalFrame frame, Correntista erro) {
        if (erro == null) {
            JOptionPane.showMessageDialog(frame, "Funcionario não encontrado", "Erro", JOptionPane.WARNING_MESSAGE);
            return true;
        } else if (!erro.isFlagAtivo()) {
            JOptionPane.showMessageDialog(frame, "Funcionario inativo", "Erro", JOptionPane.WARNING_MESSAGE);
            return true;
        } else {
            return false;
        }
    }

    public static boolean erroTransportador(JInternalFrame frame, Correntista erro) {
        if (erro == null) {
            JOptionPane.showMessageDialog(frame, "Transportador não encontrado", "Erro", JOptionPane.WARNING_MESSAGE);
            return true;
        } else if (!erro.isFlagAtivo()) {
            JOptionPane.showMessageDialog(frame, "Transportador inativo", "Erro", JOptionPane.WARNING_MESSAGE);
            return true;
        } else {
            return false;
        }
    }

    public static boolean erroFinanceira(JInternalFrame frame, Correntista erro) {
        if (erro == null) {
            JOptionPane.showMessageDialog(frame, "Financeira não encontrada", "Erro", JOptionPane.WARNING_MESSAGE);
            return true;
        } else if (!erro.isFlagAtivo()) {
            JOptionPane.showMessageDialog(frame, "Financeira inativa", "Erro", JOptionPane.WARNING_MESSAGE);
            return true;
        } else {
            return false;
        }
    }

    public static boolean erroEmpresa(JInternalFrame frame, Correntista erro) {
        if (erro == null) {
            JOptionPane.showMessageDialog(frame, "Empresa não encontrada", "Erro", JOptionPane.WARNING_MESSAGE);
            return true;
        } else if (!erro.isFlagAtivo()) {
            JOptionPane.showMessageDialog(frame, "Empresa inativa", "Erro", JOptionPane.WARNING_MESSAGE);
            return true;
        } else {
            return false;
        }
    }

    public static ComboBoxModel comboFinanceira() {
        List<Correntista> lista = listaTodasFinanceiras();

        ComboBoxModel model;

        Vector vector = new Vector();

        vector.add(null);
        if (lista != null) {
            for (Correntista correntista : lista) {
                vector.add(correntista.getIdcorrentista() + " - " + correntista.getPesquisa());
            }
        }
        model = new DefaultComboBoxModel(vector);

        return model;
    }

    public static ComboBoxModel comboEmpresa() {
        List<Correntista> lista = listaTodasEmpresas();

        ComboBoxModel model;

        List vetorList = new ArrayList();

        vetorList.add(null);
        if (lista != null) {
            for (Correntista correntista : lista) {
                vetorList.add(correntista.getIdcorrentista() + " - " + correntista.getPesquisa());
            }
        }
        model = new DefaultComboBoxModel(vetorList.toArray());

        return model;
    }
}
