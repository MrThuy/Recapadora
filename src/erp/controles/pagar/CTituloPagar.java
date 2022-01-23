package erp.controles.pagar;

import erp.controles.CFrame;
import erp.controles.cadastro.CCorrentista;
import erp.controles.cadastro.CTipoTitulo;
import erp.modelos.banco.Correntista;
import erp.modelos.banco.TipoTitulo;
import erp.modelos.banco.TituloPagar;
import erp.modelos.banco.TituloPagarId;
import erp.modelos.tablemodel.TituloPagarTableModel;
import erp.util.ERPDados;
import erp.util.ERPData;
import erp.util.ERPFocusGrabber;
import erp.util.ERPFrames;
import erp.visoes.VPesquisa;
import java.awt.Container;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Expression;

/**
 * @author Arthur
 */
public class CTituloPagar {

    private TituloPagar objeto;
    private TituloPagarId id;
    private List<TituloPagar> itens;
    private Correntista fornecedor;
    private TipoTitulo tipoTitulo;

    public enum TIPO {

        TODOS,
        PAGOS,
        ABERTOS
    }

    public void pesquisaFornecedor(JInternalFrame frame) {
        ERPFrames.setFocus(frame, "idtipotitulo");
        fornecedor = CCorrentista.pesquisaCorrentistas(CCorrentista.TIPO.FORNECEDOR);
        if (fornecedor != null) {
            fornecedorParaFrame(frame);
        } 
//        else {
//            ERPFrames.setFocus(frame, "idcorrentista");
//        }
    }

    public void criticaFornecedor(JInternalFrame frame) {
        String idCorrentista = ERPFrames.getValorCampoFrame(frame, "idcorrentista");
        if (idCorrentista.trim().length() > 0) {
            fornecedor = CCorrentista.procuraCorrentista(Integer.parseInt(idCorrentista));
            if (!CCorrentista.erroFornecedor(frame, fornecedor)) {
                fornecedorParaFrame(frame);
            } else {
                ERPFrames.setValorCampoFrame(frame, "idCorrentista", "");
                SwingUtilities.invokeLater(new ERPFocusGrabber(ERPFrames.getComponent(frame, "idcorrentista")));
            }
        }
    }

    public void criticaTipoTitulo(JInternalFrame frame) {
        String idTipotitulo = ERPFrames.getValorCampoFrame(frame, "idtipotitulo");
        if (idTipotitulo != null) {
            tipoTitulo = CTipoTitulo.procuraTipoTitulo(Integer.parseInt(idTipotitulo));
            if (tipoTitulo != null) {
                if (tipoTitulo.getFlagGeranumero()) {
                    ERPFrames.setValorCampoFrame(frame, "numero",
                            ultimoNumeroTipoTituloPagar(tipoTitulo.getIdtipotitulo()) + 1);
                }
            }
        }
    }

    public void criticaNumero(JInternalFrame frame) {
        String numero = ERPFrames.getValorCampoFrame(frame, "numero");
        if (numero.trim().length() > 0 && tipoTitulo != null && fornecedor != null) {
            ERPFrames.setValorCampoFrame(frame, "parcela",
                    ultimaParcelaTituloPagar(fornecedor.getIdcorrentista(),
                    tipoTitulo.getIdtipotitulo(),
                    Integer.parseInt(numero)) + 1);
        }
    }

    public void pesquisaNumeroParcela(JInternalFrame frame) {
        if (fornecedor != null) {
            if (tipoTitulo != null) {
                objeto = pesquisaTitulosPagarPorFornecedorETipoTitulo(
                        TIPO.ABERTOS,
                        fornecedor.getIdcorrentista(),
                        tipoTitulo.getIdtipotitulo());
                if (objeto != null) {
                    objetoParaFrame(frame);
                }

            } else {
                objeto = pesquisaTitulosPagarPorFornecedor(
                        TIPO.ABERTOS,
                        fornecedor.getIdcorrentista());
                if (objeto != null) {
                    objetoParaFrame(frame);
                }
            }
        } else {
            ERPFrames.setFocus(frame, "idcorrentista");
        }
    }

    public void criticaParcela(JInternalFrame frame) {
        if (fornecedor != null) {
            if (tipoTitulo != null) {
                String numero = ERPFrames.getValorCampoFrame(frame, "numero");
                String parcela = ERPFrames.getValorCampoFrame(frame, "parcela");
                if ((numero.trim().length() > 0)
                        && (parcela.trim().length() > 0)) {
                    objeto = procuraTituloPagar(
                            TIPO.TODOS,
                            fornecedor.getIdcorrentista(),
                            tipoTitulo.getIdtipotitulo(),
                            new Integer(numero), new Integer(numero),
                            new Integer(parcela), new Integer(parcela),
                            null, null,
                            null, null,
                            null, null, new String[]{});
                    if (objeto != null) {
                        objeto.setTipoTitulo(tipoTitulo);
                        objetoParaFrame(frame);
                    } else {
                        ERPFrames.setValorCampoFrame(frame, "dataEmissao", ERPData.agora());
                        ERPFrames.setValorCampoFrame(frame, "dataVencimento",
                                ERPData.somaDias(ERPData.agora(), 30 * Integer.parseInt(parcela)));
                    }
                }
            } else {
                ERPFrames.setFocus(frame, "idtipotitulo");
            }
        } else {
            ERPFrames.setFocus(frame, "idcorrentista");
        }
    }

    public static TituloPagar pesquisaTitulosPagarPorFornecedor(TIPO tipo, int idfornecedor) {
        TituloPagar resultado;
        VPesquisa pesquisa = new VPesquisa(CFrame.getFrame(), "Título Pagar", true);
        try {
            pesquisa.setTableModel(new TituloPagarTableModel(
                    listaTitulosPagar(tipo, idfornecedor, 0, null, null, null, null, null, null, null, null, null, null, new String[]{"tipoTitulo"})));
            pesquisa.setVisible(true);
            resultado = (TituloPagar) pesquisa.getResultado();
            return resultado;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static TituloPagar pesquisaTitulosPagarPorFornecedorETipoTitulo(TIPO tipo, int idfornecedor, int idtipotitulo) {
        TituloPagar resultado;
        VPesquisa pesquisa = new VPesquisa(CFrame.getFrame(), "Título Pagar", true);
        try {
            pesquisa.setTableModel(new TituloPagarTableModel(
                    listaTitulosPagar(tipo, idfornecedor, idtipotitulo, null, null, null, null, null, null, null, null, null, null, new String[]{"tipoTitulo"})));
            pesquisa.setVisible(true);
            resultado = (TituloPagar) pesquisa.getResultado();
            return resultado;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private Integer ultimaParcelaTituloPagar(int idcliente, int idtipotitulo, int numero) {
        Integer retorno = (Integer) ERPDados.consultaMaxObjeto(TituloPagar.class,
                new Criterion[]{
                    Expression.eq("id.idcorrentista", idcliente),
                    Expression.eq("id.numero", numero),
                    Expression.eq("id.idtipotitulo", idtipotitulo)}, "id.parcela");
        return retorno == null ? 0 : retorno;
    }

    private Integer ultimoNumeroTipoTituloPagar(int idtipotitulo) {
        Integer retorno = (Integer) ERPDados.consultaMaxObjeto(TituloPagar.class,
                new Criterion[]{
                    Expression.eq("id.idtipotitulo", idtipotitulo)}, "id.numero");
        return retorno == null ? 0 : retorno;
    }

    public static List<TituloPagar> listaTitulosPagar(
            TIPO tipo,
            int idCorrentista,
            int idTipoTitulo,
            Integer numero_ini,
            Integer numero_fim,
            Integer parcela_ini,
            Integer parcela_fim,
            Date dataEmissao_ini,
            Date dataEmissao_fim,
            Date dataVencimento_ini,
            Date dataVencimento_fim,
            BigDecimal valor_ini,
            BigDecimal valor_fim,
            String[] Join) {

        List<Criterion> criterion = new ArrayList<>();

        if (idCorrentista > 0) {
            criterion.add(Expression.eq("id.idcorrentista", idCorrentista));
        }
        if (idTipoTitulo > 0) {
            criterion.add(Expression.eq("id.idtipotitulo", idTipoTitulo));
        }
        if ((numero_ini != null) && (numero_fim != null)) {
            criterion.add(Expression.between("id.numero", numero_ini.intValue(), numero_fim.intValue()));
        }
        if ((parcela_ini != null) && (parcela_fim != null)) {
            criterion.add(Expression.between("id.parcela", parcela_ini.intValue(), parcela_fim.intValue()));
        }
        if ((dataEmissao_ini != null) && (dataEmissao_fim != null)) {
            criterion.add(Expression.between("dataEmissao", dataEmissao_ini, dataEmissao_fim));
        }
        if ((dataVencimento_ini != null) && (dataVencimento_fim != null)) {
            criterion.add(Expression.between("dataVencimento", dataVencimento_ini, dataVencimento_fim));
        }
        if ((valor_ini != null) && (valor_fim != null)) {
            criterion.add(Expression.between("valor", valor_ini, valor_fim));
        }
        switch (tipo) {
            case ABERTOS: {
                criterion.add(Expression.neProperty("valor", "valorPago"));
                break;
            }
            case PAGOS: {
                criterion.add(Expression.eqProperty("valor", "valorPago"));
                break;
            }
        }

        Criterion[] criterions = criterion.toArray(new Criterion[criterion.size()]);

        return ERPDados.consultaLista(TituloPagar.class, criterions, Join, -1);
    }

    public static TituloPagar procuraTituloPagar(
            TIPO tipo,
            int idCliente,
            int idtipotitulo,
            Integer numero_ini,
            Integer numero_fim,
            Integer parcela_ini,
            Integer parcela_fim,
            Date dataEmissao_ini,
            Date dataEmissao_fim,
            Date dataVencimento_ini,
            Date dataVencimento_fim,
            BigDecimal valor_ini,
            BigDecimal valor_fim,
            String[] Join) {
        List list = listaTitulosPagar(tipo, idCliente, idtipotitulo, numero_ini, numero_fim, parcela_ini, parcela_fim, dataEmissao_ini, dataEmissao_fim, dataVencimento_ini, dataVencimento_fim, valor_ini, valor_fim, Join);
        return (TituloPagar) ERPDados.proximoObjeto(list);
    }

    private void fornecedorParaFrame(JInternalFrame frame) {
        ERPFrames.objetoParaFrame(frame, fornecedor);
        carregaGrid(frame);
        ERPFrames.setFocus(frame, "idtipotitulo");
    }

    public void objetoParaFrame(JInternalFrame frame) {
        id = objeto.getId();
        tipoTitulo = objeto.getTipoTitulo();
        ERPFrames.setValorCampoFrame(frame, "idtipotitulo", tipoTitulo.getIdtipotitulo());
        ERPFrames.objetoParaFrame(frame, id);
        ERPFrames.objetoParaFrame(frame, objeto);
        ERPFrames.setFocus(frame, "dataEmissao");
        if ((objeto.getValorPago() == null ? 0 : objeto.getValorPago()).intValue() > 0) {
            if (JOptionPane.showConfirmDialog(frame, "O título já possui pagamentos \n" + ""
                    + "deseja alterar o título mesmo assim?", "", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
                cancela(frame);
            }
        }
    }

    private TituloPagar frameParaObjeto(JInternalFrame frame, TituloPagar objeto) throws Exception {
        ERPFrames.frameParaObjeto(frame, id);
        ERPFrames.frameParaObjeto(frame, objeto);
        objeto.setId(id);
        objeto.setCorrentista(fornecedor);
        objeto.setTipoTitulo(tipoTitulo);
        objeto.setUsuario(CFrame.getUsuario());
        objeto.setDataInclusao(ERPData.agora());
        return objeto;
    }

    public void salva(JInternalFrame frame) {
        if (fornecedor != null) {
            if (tipoTitulo != null) {
                String numero = ERPFrames.getValorCampoFrame(frame, "numero");
                String parcela = ERPFrames.getValorCampoFrame(frame, "parcela");
                if ((numero.trim().length() > 0) && (parcela.trim().length() > 0)) {
                    try {
                        if (objeto == null) {
                            objeto = new TituloPagar();
                            objeto.setValorPago(BigDecimal.ZERO);
                            objeto.setJuros(BigDecimal.ZERO);
                        }
                        if (id == null) {
                            id = new TituloPagarId();
                        }

                        objeto = frameParaObjeto(frame, objeto);

                        ERPDados.gravaObjeto(objeto);

                        JOptionPane.showMessageDialog(frame, "Registro salvo com sucesso!");

                        ERPFrames.setValorCampoFrame(frame, "idtipotitulo", tipoTitulo.getIdtipotitulo());

                        ERPFrames.setValorCampoFrame(frame, "numero", objeto.getId().getNumero());

                        cancela(frame);

                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(frame, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    }

                } else {
                    JOptionPane.showMessageDialog(frame, "Os campos cliente, número e parcela \n são obrigatórios seus preenchimentos", "Erro", JOptionPane.ERROR_MESSAGE);
                    ERPFrames.setFocus(frame, "parcela");
                }
            } else {
                ERPFrames.setFocus(frame, "idtipotitulo");
            }
        } else {
            ERPFrames.setFocus(frame, "idcorrentista");
        }
    }

    public void cancela(JInternalFrame frame) {
        if (!(ERPFrames.getValorCampoFrame(frame, "parcela").isEmpty())
                && (tipoTitulo != null)
                && (fornecedor != null)) {
            carregaGrid(frame);
            ERPFrames.limparTodosCampos((Container) ERPFrames.getComponent(frame, "jPanel1"));
            ERPFrames.setValorCampoFrame(frame, "idtipotitulo", tipoTitulo.getIdtipotitulo());
            objeto = null;
            id = null;
            ERPFrames.setFocus(frame, "numero");
        } else {
            ERPFrames.limparTodosCampos(frame);
            objeto = null;
            id = null;
            fornecedor = null;
            itens = null;
            tipoTitulo = null;
            try {
                ((TituloPagarTableModel) ((JTable) ERPFrames.getComponent(frame, "titulosTable")).getModel()).setLinhas(itens);
            } catch (Exception e) {
            }
            ERPFrames.setFocus(frame, "idcorrentista");
        }
    }

    public void exclui(JInternalFrame frame) {
        if (fornecedor != null) {
            if (tipoTitulo != null) {
                String numero = ERPFrames.getValorCampoFrame(frame, "numero");
                String parcela = ERPFrames.getValorCampoFrame(frame, "parcela");
                if ((numero.trim().length() > 0) && (parcela.trim().length() > 0)) {
                    objeto = procuraTituloPagar(
                            TIPO.TODOS,
                            fornecedor.getIdcorrentista(),
                            tipoTitulo.getIdtipotitulo(),
                            new Integer(numero), new Integer(numero),
                            new Integer(parcela), new Integer(parcela),
                            null, null,
                            null, null,
                            null, null, new String[]{});
                    if (objeto != null) {
                        boolean confirma = false;
                        if ((objeto.getValorPago() == null ? 0 : objeto.getValorPago()).intValue() > 0) {
                            if (JOptionPane.showConfirmDialog(frame, "O título já possui pagamentos \n" + ""
                                    + "deseja excluir mesmo assim?", "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                                confirma = true;
                            }
                        } else if (JOptionPane.showConfirmDialog(frame, "Tem certeza que deseja excluir?", "",
                                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                            confirma = true;
                        }
                        if (confirma) {
                            try {
                                ERPDados.apagaObjeto(objeto);

                                JOptionPane.showMessageDialog(frame, "Registro excluido com sucesso!");

                                cancela(frame);

                            } catch (Exception e) {
                                JOptionPane.showMessageDialog(frame, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Os campos cliente, número e parcela \n são obrigatórios seus preenchimentos", "Erro", JOptionPane.ERROR_MESSAGE);
                    ERPFrames.setFocus(frame, "parcela");
                }
            } else {
                ERPFrames.setFocus(frame, "idtipotitulo");
            }
        } else {
            ERPFrames.setFocus(frame, "idcorrentista");
        }
    }

    public static boolean erroTituloPagar(JInternalFrame frame, TituloPagar erro) {
        if (erro == null) {
            JOptionPane.showMessageDialog(frame, "Título do pagar não encontrado", "Erro", JOptionPane.WARNING_MESSAGE);
            return true;
        } else {
            return false;
        }
    }

    private void carregaGrid(JInternalFrame frame) {
        itens = listaTitulosPagar(
                TIPO.ABERTOS,
                fornecedor.getIdcorrentista(),
                0,
                null, null,
                null, null,
                null, null,
                null, null,
                null, null,
                new String[]{"tipoTitulo"});
        try {
            ((TituloPagarTableModel) ((JTable) ERPFrames.getComponent(frame, "titulosTable")).getModel()).setLinhas(itens);
        } catch (Exception e) {
        }
    }

    public void setObjeto(JInternalFrame frame, TituloPagar objeto) {
        this.objeto = objeto;
        objetoParaFrame(frame);
    }
}
