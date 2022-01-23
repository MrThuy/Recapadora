package erp.controles.pagar;

import erp.modelos.banco.BaixaPagar;
import erp.modelos.banco.BaixaPagarId;
import erp.modelos.banco.Correntista;
import erp.modelos.banco.TituloPagar;
import erp.modelos.banco.TipoTitulo;
import erp.controles.CFrame;
import erp.controles.cadastro.CCorrentista;
import erp.controles.cadastro.CTipoPagamento;
import erp.controles.cadastro.CTipoTitulo;
import erp.modelos.*;
import erp.modelos.tablemodel.BaixaPagarTableModel;
import erp.util.ERPDados;
import erp.util.ERPFocusGrabber;
import erp.util.ERPFrames;
import erp.visoes.VPesquisa;
import java.awt.Container;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
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
public class CBaixaPagar {

    private BaixaPagar objeto;
    private BaixaPagarId id;
    private TituloPagar titulo;
    private List<BaixaPagar> itens;
    private Correntista fornecedor;
    private TipoTitulo tipoTitulo;
    private BigDecimal valor_antigo;
    private BigDecimal juros_antigo;

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
                ERPFrames.setValorCampoFrame(frame, "idcorrentista", "");
                SwingUtilities.invokeLater(new ERPFocusGrabber(ERPFrames.getComponent(frame, "idcorrentista")));
            }
        }
    }

    public void criticaTipoTitulo(JInternalFrame frame) {
        String idTipotitulo = ERPFrames.getValorCampoFrame(frame, "idtipotitulo");
        if (idTipotitulo != null) {
            tipoTitulo = CTipoTitulo.procuraTipoTitulo(Integer.parseInt(idTipotitulo));
        }
    }

    public void pesquisaNumeroParcela(JInternalFrame frame) {
        if (fornecedor != null) {
            if (tipoTitulo != null) {
                titulo = CTituloPagar.pesquisaTitulosPagarPorFornecedorETipoTitulo(
                        CTituloPagar.TIPO.ABERTOS,
                        fornecedor.getIdcorrentista(),
                        tipoTitulo.getIdtipotitulo());
            } else {
                titulo = CTituloPagar.pesquisaTitulosPagarPorFornecedor(
                        CTituloPagar.TIPO.ABERTOS,
                        fornecedor.getIdcorrentista());

            }
            if (titulo != null) {
                tituloParaFrame(frame);
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
                    titulo = CTituloPagar.procuraTituloPagar(
                            CTituloPagar.TIPO.TODOS,
                            fornecedor.getIdcorrentista(),
                            tipoTitulo.getIdtipotitulo(),
                            new Integer(numero), new Integer(numero),
                            new Integer(parcela), new Integer(parcela),
                            null, null,
                            null, null,
                            null, null,
                            new String[]{});
                    if (titulo != null) {
                        tituloParaFrame(frame);
                    }
                }
            } else {
                ERPFrames.setFocus(frame, "idtipotitulo");
            }
        } else {
            ERPFrames.setFocus(frame, "idcorrentista");
        }
    }

    public static BaixaPagar pesquisaBaixaPagar(int idfornecedor, int idtipotitulo, int numero, int parcela) {
        BaixaPagar resultado;
        VPesquisa pesquisa = new VPesquisa(CFrame.getFrame(), "Baixas do Pagar", true);
        try {
            pesquisa.setTableModel(new BaixaPagarTableModel(listaBaixasPagar(
                    idfornecedor,
                    idtipotitulo,
                    0,
                    numero,
                    numero,
                    parcela,
                    0,
                    null, null,
                    null, null,
                    new String[]{"tipoPagamento"})));
            pesquisa.setVisible(true);
            resultado = (BaixaPagar) pesquisa.getResultado();
            return resultado;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static List<BaixaPagar> listaBaixasPagar(
            int idCorrentista,
            int idTipoTitulo,
            int idpagamento,
            Integer numero_ini,
            Integer numero_fim,
            Integer parcela,
            int tipoPagamento,
            Date dataPagamento_ini,
            Date dataPagamento_fim,
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
        if (idpagamento > 0) {
            criterion.add(Expression.eq("id.idpagamento", idpagamento));
        }
        if ((numero_ini != null) && (numero_fim != null)) {
            criterion.add(Expression.between("id.numero", numero_ini.intValue(), numero_fim.intValue()));
        }
        if (parcela != null) {
            criterion.add(Expression.eq("id.parcela", parcela.intValue()));
        }
        if (tipoPagamento > 0) {
            criterion.add(Expression.eq("tipoPagamento.idtipopagamento", tipoPagamento));
        }
        if ((dataPagamento_ini != null) && (dataPagamento_fim != null)) {
            criterion.add(Expression.between("dataPagamento", dataPagamento_ini, dataPagamento_fim));
        }
        if ((valor_ini != null) && (valor_fim != null)) {
            criterion.add(Expression.between("valor", valor_ini, valor_fim));
        }

        Criterion[] criterions = criterion.toArray(new Criterion[criterion.size()]);

        return ERPDados.consultaLista(BaixaPagar.class, criterions, Join, -1);
    }

    public static BaixaPagar procuraBaixaPagar(
            int idCorrentista,
            int idTipoTitulo,
            int idpagamento,
            Integer numero_ini,
            Integer numero_fim,
            Integer parcela,
            int tipoPagamento,
            Date dataPagamento_ini,
            Date dataPagamento_fim,
            BigDecimal valor_ini,
            BigDecimal valor_fim,
            String[] Join) {
        List list = listaBaixasPagar(
                idCorrentista,
                idTipoTitulo,
                idpagamento,
                numero_ini, numero_fim,
                parcela,
                tipoPagamento,
                dataPagamento_ini, dataPagamento_fim,
                valor_ini, valor_fim,
                Join);
        return (BaixaPagar) ERPDados.proximoObjeto(list);
    }

    private void fornecedorParaFrame(JInternalFrame frame) {
        ERPFrames.objetoParaFrame(frame, fornecedor);
        ERPFrames.setFocus(frame, "idtipotitulo");
    }

    private void tituloParaFrame(JInternalFrame frame) {
        boolean aberto = true;
        if (titulo.getValor().equals(titulo.getValorPago())) {
            if (JOptionPane.showConfirmDialog(frame, "Título já baixado,\n Deseja alterar ou excluir algum pagamento?",
                    null, JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
                cancela(frame);
                aberto = false;
            }
        }
        if (aberto) {
            ERPFrames.objetoParaFrame((Container) ERPFrames.getComponent(frame, "jPanel1"), titulo.getId());
            ERPFrames.setValorCampoFrame(frame, "valortitulo", titulo.getValor());
            ERPFrames.setValorCampoFrame(frame, "valorPago", titulo.getValorPago());
            ERPFrames.setValorCampoFrame(frame, "dataVencimento", titulo.getDataVencimento());
            ERPFrames.setValorCampoFrame(frame, "observacaotitulo", titulo.getObservacao());
            carregaGrid(frame);
            ERPFrames.setFocus(frame, "idtipopagamento");
            valor_antigo = BigDecimal.ZERO;
            juros_antigo = BigDecimal.ZERO;
        }
    }

    private void objetoParaFrame(JInternalFrame frame) {
        id = objeto.getId();
        ERPFrames.objetoParaFrame(frame, objeto);
        if (objeto.getTipoPagamento() != null) {
            ERPFrames.objetoParaFrame(frame, objeto.getTipoPagamento());
        }

        valor_antigo = objeto.getValor();
        juros_antigo = objeto.getJuros();
        ERPFrames.setFocus(frame, "idtipopagamento");
    }

    private BaixaPagar frameParaObjeto(JInternalFrame frame, BaixaPagar objeto) throws Exception {
        ERPFrames.frameParaObjeto(frame, id);
        ERPFrames.frameParaObjeto(frame, objeto);
        objeto.setId(id);
        String idtipopagamento = ERPFrames.getValorCampoFrame(frame, "idtipopagamento");
        if (idtipopagamento != null) {
            objeto.setTipoPagamento(CTipoPagamento.procuraTipoPagamento(Integer.parseInt(idtipopagamento)));
        }
        objeto.setUsuario(CFrame.getUsuario());
        objeto.setDataInclusao(new Timestamp(System.currentTimeMillis()));
        return objeto;
    }

    public void salva(JInternalFrame frame) {
        if (titulo != null) {
            try {
                if (objeto == null) {
                    objeto = new BaixaPagar();
                }
                if (id == null) {
                    id = new BaixaPagarId();
                }

                objeto = frameParaObjeto(frame, objeto);

                titulo.setValorPago(titulo.getValorPago().add(objeto.getValor().subtract(valor_antigo)));
                titulo.setJuros(titulo.getJuros().add(objeto.getJuros().subtract(juros_antigo)));

                Object[] objetos = new Object[]{titulo, objeto};

                ERPDados.gravaObjetos(objetos);

                JOptionPane.showMessageDialog(frame, "Registro salvo com sucesso!");

                cancela(frame);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(frame, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }

        } else {
            JOptionPane.showMessageDialog(frame, "Precisa selecionar um título primeiro.", "Erro", JOptionPane.ERROR_MESSAGE);
            ERPFrames.setFocus(frame, "idcorrentista");
        }
    }

    public void cancela(JInternalFrame frame) {
        if (titulo != null) {
            ERPFrames.limparTodosCampos(frame);
            fornecedorParaFrame(frame);
            ERPFrames.setFocus(frame, "idtipotitulo");
        } else {
            ERPFrames.limparTodosCampos(frame);
            fornecedor = null;
            ERPFrames.setFocus(frame, "idcorrentista");
        }
        valor_antigo = BigDecimal.ZERO;
        juros_antigo = BigDecimal.ZERO;
        objeto = null;
        id = null;
        titulo = null;
        tipoTitulo = null;
        itens = null;
        try {
            ((BaixaPagarTableModel) ((JTable) ERPFrames.getComponent(frame, "jTable1")).getModel()).setLinhas(itens);
        } catch (Exception e) {
        }
    }

    public void exclui(JInternalFrame frame) {
        if (titulo != null) {
            if (objeto != null) {
                if (JOptionPane.showConfirmDialog(frame, "Tem certeza que deseja excluir?", "",
                        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

                    try {

                        titulo.setValorPago(titulo.getValorPago().subtract(objeto.getValor()));
                        titulo.setJuros(titulo.getJuros().subtract(objeto.getJuros()));

                        ERPDados.executaOperacao(new Object[]{objeto, titulo},
                                new ERPDados.OPERACOES[]{ERPDados.OPERACOES.EXCLUIR, ERPDados.OPERACOES.ALTERAR});

                        JOptionPane.showMessageDialog(frame, "Registro excluido com sucesso!");

                        cancela(frame);

                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(frame, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Selecione um pagamento dando um duplo clique na linha da grid antes de excluir");
            }
        } else {
            ERPFrames.setFocus(frame, "idtipotitulo");
        }
    }

    private void carregaGrid(JInternalFrame frame) {
        itens = listaBaixasPagar(
                titulo.getId().getIdcorrentista(),
                titulo.getId().getIdtipotitulo(),
                0,
                titulo.getId().getNumero(),
                titulo.getId().getNumero(),
                titulo.getId().getParcela(),
                0,
                null, null,
                null, null,
                new String[]{"tipoPagamento"});

        try {
            ((BaixaPagarTableModel) ((JTable) ERPFrames.getComponent(frame, "jTable1")).getModel()).setLinhas(itens);
        } catch (Exception e) {
        }
    }

    public void setObjeto(JInternalFrame frame, BaixaPagar objeto) {
        this.objeto = objeto;
        objetoParaFrame(frame);
    }
}
