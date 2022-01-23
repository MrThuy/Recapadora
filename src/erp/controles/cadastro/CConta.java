package erp.controles.cadastro;

import erp.controles.CFrame;
import erp.modelos.banco.Conta;
import erp.modelos.tablemodel.ContaTableModel;
import erp.util.ERPDados;
import erp.util.ERPFrames;
import erp.visoes.VPesquisa;
import java.sql.Timestamp;
import java.util.List;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Expression;

/**
 * @author Arthur
 */
public class CConta {

    private Conta objeto;

    public enum TIPO {

        TODOS,
        ATIVOS
    }

    public static Conta procuraConta(int idConta) {
        return (Conta) ERPDados.consultaObjeto(Conta.class,
                (new Criterion[]{Expression.eq("idconta", idConta)}), 1);
    }

    private static List listaTodasContas() {
        return ERPDados.consultaLista(Conta.class, -1);
    }

    private static List listaTodasContasAtivas() {
        return ERPDados.consultaLista(Conta.class, (new Criterion[]{
                    Expression.eq("flagAtivo", true)}), -1);
    }

    public static Conta pesquisaConta(TIPO tipo) {
        Conta resultado;
        VPesquisa pesquisa = null;
        try {
            switch (tipo) {
                case TODOS:
                    pesquisa = new VPesquisa(CFrame.getFrame(), "Conta", true);
                    pesquisa.setTableModel(new ContaTableModel(listaTodasContas()));
                    break;
                case ATIVOS:
                    pesquisa = new VPesquisa(CFrame.getFrame(), "Conta", true, CFrame.VISOES.CONTA);
                    pesquisa.setTableModel(new ContaTableModel(listaTodasContasAtivas()));
                    break;
            }
            pesquisa.setVisible(true);
            resultado = (Conta) pesquisa.getResultado();
            return resultado;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private Integer ultimoId() {
        Integer retorno = (Integer) ERPDados.consultaMaxObjeto(Conta.class,
                new Criterion[]{}, "idconta");
        return retorno == null ? 0 : retorno;
    }

    private void ObjetoParaFrame(JInternalFrame frame, Conta objeto) {
        ERPFrames.objetoParaFrame(frame, objeto);
        ERPFrames.setValorCampoFrame(frame, "padraoE", objeto.getFlagPadrao().equals('E'));
        ERPFrames.setValorCampoFrame(frame, "padraoS", objeto.getFlagPadrao().equals('S'));
    }

    private Conta frameParaObjeto(JInternalFrame frame, Conta objeto) throws Exception {
        ERPFrames.frameParaObjeto(frame, objeto);
        objeto.setUsuario(CFrame.getUsuario());
        objeto.setDataInclusao(new Timestamp(System.currentTimeMillis()));

        boolean padraoE = ERPFrames.getValorCampoFrame(frame, "padraoE").equalsIgnoreCase("true") ? true : false;

        objeto.setFlagPadrao(padraoE ? 'E' : 'S');

        return objeto;
    }

    public void criticaId(JInternalFrame frame) {
        String id = ERPFrames.getValorCampo(ERPFrames.getComponent(frame, "idConta"));
        if (!id.equals("")) {
            objeto = procuraConta(Integer.parseInt(id));
            if (objeto != null) {
                ObjetoParaFrame(frame, objeto);
            }
        } else {
            ERPFrames.setValorCampoFrame(frame, "idConta", ultimoId() + 1);
        }
    }

    public void salva(JInternalFrame frame) {
        String id = ERPFrames.getValorCampo(ERPFrames.getComponent(frame, "idConta"));
        if (!id.equals("")) {
            if (objeto == null) {
                objeto = new Conta();
            }

            try {
                objeto = frameParaObjeto(frame, objeto);

                ERPDados.gravaObjeto(objeto);

                JOptionPane.showMessageDialog(frame, "Registro salvo com sucesso!");

                cancela(frame);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(frame, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Informe um número maior que zero para o ID");
        }

    }

    public void cancela(JInternalFrame frame) {

        ERPFrames.limparTodosCampos(frame);

        objeto = null;

        ERPFrames.setValorCampoFrame(frame, "flagAtivo", true);

        ERPFrames.setFocus(frame, "idConta");

    }

    public void exclui(JInternalFrame frame) {
        String id = ERPFrames.getValorCampo(ERPFrames.getComponent(frame, "idConta"));
        if (!id.equals("")) {
            objeto = procuraConta(Integer.parseInt(id));
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
        objeto = pesquisaConta(TIPO.TODOS);
        if (objeto != null) {
            ObjetoParaFrame(frame, objeto);
        } else {
            ERPFrames.setFocus(frame, "idConta");
        }
    }

    public static boolean erroConta(JInternalFrame frame, Conta erro) {
        if (erro == null) {
            JOptionPane.showMessageDialog(frame, "Conta não encontrada", "Erro", JOptionPane.WARNING_MESSAGE);
            return true;
        } else if (!erro.getFlagAtivo()) {
            JOptionPane.showMessageDialog(frame, "Conta inativo", "Erro", JOptionPane.WARNING_MESSAGE);
            return true;
        } else {
            return false;
        }
    }
}
