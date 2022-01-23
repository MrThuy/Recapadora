package erp.controles.pneu;

import erp.controles.CFrame;
import erp.controles.pneu.CPneu.STATUS;
import erp.modelos.banco.Marca;
import erp.modelos.banco.Pneu;
import erp.modelos.banco.TamanhoPneu;
import erp.modelos.tablemodel.PneuTableModel;
import erp.util.ERPDados;
import erp.util.ERPData;
import erp.util.ERPFocusGrabber;
import erp.util.ERPFrames;
import erp.visoes.VPesquisa;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Expression;

/**
 * @author Arthur
 */
public class CPneu {

    private Pneu objeto;
    private TamanhoPneu tamanhoPneu;
    private Marca marca;

    public enum TIPO {

        TODOS,
        VENDIDOS,
        A_VENDER
    }

    public enum STATUS {

        TODOS,
        ORIGINAL,
        RECAPADO
    }

    public static boolean erroPneu(JInternalFrame frame, Pneu erro) {
        if (erro == null) {
            JOptionPane.showMessageDialog(frame, "Pneu não encontrada", "Erro", JOptionPane.WARNING_MESSAGE);
            return true;
        } else {
            return false;
        }
    }

    public static Pneu procuraPneu(int idPneu) {
        return (Pneu) ERPDados.consultaObjeto(Pneu.class, (new Criterion[]{Expression.eq("idpneu", idPneu)}), new String[]{"marca", "tamanhoPneu"}, 1);

    }

    public void objetoParaFrame(JInternalFrame frame) {
        tamanhoPneu = objeto.getTamanhoPneu();
        tamanhoParaFrame(frame);

        marca = objeto.getMarca();
        ERPFrames.setValorCampoFrame(frame, "idmarca", marca.getIdmarca());

        ERPFrames.objetoParaFrame(frame, objeto);
        if (objeto.getStatus() == 'O') {
            ERPFrames.setValorCampoFrame(frame, "idoriginal", true);
        } else {
            ERPFrames.setValorCampoFrame(frame, "idrecapado", true);
        }

        if ((objeto.getValorVendido() == null ? 0 : objeto.getValorVendido()).intValue() > 0) {
            if (JOptionPane.showConfirmDialog(frame, "O pneu já foi vendido! \n"
                    + "Deseja alterar os dados do pneu mesmo assim?", "", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
                cancela(frame);
            }
        }
    }

    private Pneu frameParaObjeto(JInternalFrame frame, Pneu objeto) throws Exception {
        ERPFrames.frameParaObjeto(frame, objeto);
        objeto.setTamanhoPneu(tamanhoPneu);
        objeto.setMarca(marca);
        objeto.setStatus(ERPFrames.getValorCampoFrame(frame, "idoriginal").equalsIgnoreCase("true") ? 'O' : 'R');
        objeto.setUsuario(CFrame.getUsuario());
        objeto.setDataInclusao(ERPData.agora());
        return objeto;
    }

    public void criticaId(JInternalFrame frame) {
        String idPneu = ERPFrames.getValorCampo(ERPFrames.getComponent(frame, "idpneu"));
        if (!idPneu.equals("")) {
            objeto = procuraPneu(Integer.parseInt(idPneu));
            if (objeto != null) {
                objetoParaFrame(frame);
            } else {
                erroPneu(frame, objeto);
                ERPFrames.setValorCampoFrame(frame, "idpneu", "");
                ERPFrames.setFocus(frame, "idpneu");
            }
        }
    }

    public void criticaMarca(JInternalFrame frame) {
        String idMarca = ERPFrames.getValorCampoFrame(frame, "idmarca");
        if (idMarca != null) {
            marca = CMarca.procuraMarca(Integer.parseInt(idMarca));
        }
    }

    private void tamanhoParaFrame(JInternalFrame frame) {
        ERPFrames.objetoParaFrame(frame, tamanhoPneu);
    }

    public void criticaTamanho(JInternalFrame frame) {
        String idTamanho = ERPFrames.getValorCampoFrame(frame, "idtamanhopneu");
        if (idTamanho.trim().length() > 0) {
            tamanhoPneu = CTamanho_Pneu.procuraTamanhoPneu(Integer.parseInt(idTamanho));
            if (!CTamanho_Pneu.erroTamanhoPneu(frame, tamanhoPneu)) {
                tamanhoParaFrame(frame);
            } else {
                ERPFrames.setValorCampoFrame(frame, "idtamanhopneu", "");
                SwingUtilities.invokeLater(new ERPFocusGrabber(ERPFrames.getComponent(frame, "idtamanhopneu")));
            }
        }

    }

    public void pesquisaTamanhoPneu(JInternalFrame frame) {
        tamanhoPneu = CTamanho_Pneu.pesquisaTamanhoPneu();
        if (tamanhoPneu != null) {
            tamanhoParaFrame(frame);
            SwingUtilities.invokeLater(new ERPFocusGrabber(ERPFrames.getComponent(frame, "lona")));
        }
    }

    public static List<Pneu> listaPneus(
            int idPneu,
            int idMarca,
            int idTamanho_Pneu,
            Integer lona_min, Integer lona_max,
            Date dataCompra_ini, Date dataCompra_fim,
            Date dataVenda_ini, Date dataVenda_fim,
            BigDecimal valorComprado_ini, BigDecimal valorComprado_fim,
            BigDecimal valorPreco_ini, BigDecimal valorPreco_fim,
            BigDecimal valorVendido_ini, BigDecimal valorVendido_fim,
            String codigo,
            STATUS status,
            String[] Join) {

        List<Criterion> criterion = new ArrayList<>();

        if (idPneu > 0) {
            criterion.add(Expression.eq("idpneu", idPneu));
        }
        if (idMarca > 0) {
            criterion.add(Expression.eq("marca.idmarca", idMarca));
        }
        if (idTamanho_Pneu > 0) {
            criterion.add(Expression.eq("tamanhoPneu.idtamanhopneu", idTamanho_Pneu));
        }
        if ((lona_min != null) && (lona_max != null)) {
            criterion.add(Expression.between("lona", lona_min, lona_max));
        }
        if ((dataCompra_ini != null) && (dataCompra_fim != null)) {
            criterion.add(Expression.between("dataCompra", dataCompra_ini, dataCompra_fim));
        }
        if ((dataVenda_ini != null) && (dataVenda_fim != null)) {
            criterion.add(Expression.between("dataVenda", dataVenda_ini, dataVenda_fim));
        }
        if ((valorComprado_ini != null) && (valorComprado_fim != null)) {
            criterion.add(Expression.between("valorComprado", valorComprado_ini, valorComprado_fim));
        }
        if ((valorVendido_ini != null) && (valorVendido_fim != null)) {
            criterion.add(Expression.between("valorVendido", valorVendido_ini, valorVendido_fim));
        }
        if ((valorPreco_ini != null) && (valorPreco_fim != null)) {
            criterion.add(Expression.between("valorPreco", valorPreco_ini, valorPreco_fim));
        }
        if (codigo != null && codigo.trim().length() > 0) {
            criterion.add(Expression.like("codigo", "%" + codigo + "%"));
        }
        switch (status) {
            case ORIGINAL: {
                criterion.add(Expression.eq("status", 'O'));
                break;
            }
            case RECAPADO: {
                criterion.add(Expression.eq("status", 'R'));
                break;
            }
            case TODOS: {
                break;
            }
        }

        Criterion[] criterions = criterion.toArray(new Criterion[criterion.size()]);

        return ERPDados.consultaLista(Pneu.class, criterions, Join, -1);
    }

    public static Pneu pesquisaPneus(TIPO tipo) {
        Pneu resultado;
        VPesquisa pesquisa;
        try {
            if (tipo.equals(TIPO.TODOS)) {
                pesquisa = new VPesquisa(CFrame.getFrame(), "Pneu", true);
                pesquisa.setTableModel(new PneuTableModel(listaPneus(0, 0, 0, 
                        null, null,
                        null, null,
                        null, null,
                        null, null,
                        null, null,
                        null, null,
                        null,
                        STATUS.TODOS,
                        new String[]{"marca", "tamanhoPneu"})));
            } else {
                pesquisa = new VPesquisa(CFrame.getFrame(), "Pneu", true, CFrame.VISOES.PNEU);

                if (tipo.equals(TIPO.A_VENDER)) {
                    pesquisa.setTableModel(new PneuTableModel(listaPneus(0, 0, 0, 
                            null, null,
                            null, null,
                            null, null,
                            null, null,
                            null, null,
                            BigDecimal.ZERO, BigDecimal.ZERO,
                            null,
                            STATUS.TODOS,
                            new String[]{"marca", "tamanhoPneu"})));
                } else if (tipo.equals(TIPO.VENDIDOS)) {

                    if (tipo.equals(TIPO.A_VENDER)) {
                        pesquisa.setTableModel(new PneuTableModel(listaPneus(0, 0, 0, 
                                null, null,
                                null, null,
                                null, null,
                                null, null,
                                null, null,
                                BigDecimal.ZERO, new BigDecimal(999999999),
                                null,
                                STATUS.TODOS,
                                new String[]{"marca", "tamanhoPneu"})));
                    }
                }
            }
            pesquisa.setVisible(true);
            resultado = (Pneu) pesquisa.getResultado();
            return resultado;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void pesquisa(JInternalFrame frame) {
        ERPFrames.setFocus(frame, "idmarca");
        objeto = pesquisaPneus(TIPO.TODOS);
        if (objeto != null) {
            objetoParaFrame(frame);
        } else {
            ERPFrames.setFocus(frame, "idpneu");
        }
    }

    public void salva(JInternalFrame frame) {
        if (marca != null && tamanhoPneu != null) {
            try {
                if (objeto == null) {
                    objeto = new Pneu();
                }
                objeto = frameParaObjeto(frame, objeto);

                ERPDados.gravaObjeto(objeto);

                JOptionPane.showMessageDialog(frame, "Registro salvo com sucesso!");

                cancela(frame);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(frame, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }

        } else {
            JOptionPane.showMessageDialog(frame, "Preencha os campos de tamanho e marca", "Erro", JOptionPane.ERROR_MESSAGE);
            ERPFrames.setFocus(frame, "parcela");
        }
    }

    public void cancela(JInternalFrame frame) {
        objeto = null;
        marca = null;
        tamanhoPneu = null;
        ERPFrames.limparTodosCampos(frame);
        ERPFrames.setFocus(frame, "idpneu");
    }

    public void exclui(JInternalFrame frame) {
        String id = ERPFrames.getValorCampo(ERPFrames.getComponent(frame, "idPneu"));
        if (!id.equals("")) {
            objeto = procuraPneu(Integer.parseInt(id));
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

    public void setObjeto(JInternalFrame frame, Pneu objeto) {
        this.objeto = objeto;
        objetoParaFrame(frame);
    }
}
