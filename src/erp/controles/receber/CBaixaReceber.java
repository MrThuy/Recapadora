package erp.controles.receber;

import erp.modelos.banco.BaixaReceber;
import erp.modelos.banco.BaixaReceberId;
import erp.modelos.banco.Correntista;
import erp.modelos.banco.TituloReceber;
import erp.modelos.banco.TipoTitulo;
import erp.controles.CFrame;
import erp.controles.cadastro.CCorrentista;
import erp.controles.cadastro.CTipoPagamento;
import erp.controles.cadastro.CTipoTitulo;
import erp.modelos.tablemodel.BaixaReceberTableModel;
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
import org.mozilla.javascript.edu.emory.mathcs.backport.java.util.Arrays;

/**
 * @author Arthur
 */
public class CBaixaReceber {

    private BaixaReceber objeto;
    private BaixaReceberId id;
    private TituloReceber titulo;
    private List<BaixaReceber> itens;
    private Correntista cliente;
    private TipoTitulo tipoTitulo;
    private BigDecimal valor_antigo;
    private BigDecimal juros_antigo;

    public enum TIPO {

        TODOS,
        PAGOS,
        ABERTOS
    }

    public void pesquisaCliente(JInternalFrame frame) {
        ERPFrames.setFocus(frame, "idtipotitulo");
        cliente = CCorrentista.pesquisaCorrentistas(CCorrentista.TIPO.CLIENTE);
        if (cliente != null) {
            clienteParaFrame(frame);
        }
//        else {
//            ERPFrames.setFocus(frame, "idcorrentista");
//        }
    }

    public void criticaCliente(JInternalFrame frame) {
        String idCliente = ERPFrames.getValorCampoFrame(frame, "idcorrentista");
        if (idCliente.trim().length() > 0) {
            cliente = CCorrentista.procuraCorrentista(Integer.parseInt(idCliente));
            if (!CCorrentista.erroCliente(frame, cliente)) {
                clienteParaFrame(frame);
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
        if (cliente != null) {
            if (tipoTitulo != null) {
                titulo = CTituloReceber.pesquisaTitulosRecebePorClienteETipoTitulo(
                        CTituloReceber.TIPO.ABERTOS,
                        cliente.getIdcorrentista(),
                        tipoTitulo.getIdtipotitulo());
                if (titulo != null) {
                    tituloParaFrame(frame);
                }
            } else {
                titulo = CTituloReceber.pesquisaTitulosRecebePorCliente(CTituloReceber.TIPO.ABERTOS,
                        cliente.getIdcorrentista());
                if (titulo != null) {
                    tituloParaFrame(frame);
                }
            }
        } else {
            ERPFrames.setFocus(frame, "idcorrentista");
        }
    }

    public void criticaParcela(JInternalFrame frame) {
        if (cliente != null) {
            if (tipoTitulo != null) {
                String numero = ERPFrames.getValorCampoFrame(frame, "numero");
                String parcela = ERPFrames.getValorCampoFrame(frame, "parcela");
                if ((numero.trim().length() > 0)
                        && (parcela.trim().length() > 0)) {
                    titulo = CTituloReceber.procuraTituloReceber(cliente.getIdcorrentista(),
                            Integer.parseInt(numero),
                            Integer.parseInt(parcela),
                            tipoTitulo.getIdtipotitulo());
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

    public static BaixaReceber pesquisaBaixaReceber(int idcliente, int idtipotitulo, int numero, int parcela) {
        BaixaReceber resultado = null;
        VPesquisa pesquisa = new VPesquisa(CFrame.getFrame(), "Baixas do Receber", true);
        try {
            pesquisa.setTableModel(new BaixaReceberTableModel(listaBaixasRecebePorTitulo(idcliente, idtipotitulo, numero, parcela, new String[]{"tipoPagamento"})));
            pesquisa.setVisible(true);
            resultado = (BaixaReceber) pesquisa.getResultado();
            return resultado;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static BaixaReceber procuraBaixaReceber(int idcliente, int idtipotitulo, int numero, int parcela,
            int idpagamento, String[] Join) {
        return (BaixaReceber) ERPDados.consultaObjeto(BaixaReceber.class,
                (new Criterion[]{
            Expression.eq("id.idcorrentista", idcliente),
            Expression.eq("id.idtipotitulo", idtipotitulo),
            Expression.eq("id.numero", numero),
            Expression.eq("id.parcela", parcela),
            Expression.eq("id.idpagamento", idpagamento)}), Join, 1);
    }

    public static List<BaixaReceber> listaBaixasRecebePorTitulo(int idcliente, int idtipotitulo, int numero, int parcela, String[] Join) {
        return ERPDados.consultaLista(BaixaReceber.class, new Criterion[]{
            Expression.eq("id.idcorrentista", idcliente),
            Expression.eq("id.idtipotitulo", idtipotitulo),
            Expression.eq("id.numero", numero),
            Expression.eq("id.parcela", parcela)}, Join, -1);

    }

    private void clienteParaFrame(JInternalFrame frame) {
        ERPFrames.objetoParaFrame(frame, cliente);
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

    private BaixaReceber frameParaObjeto(JInternalFrame frame, BaixaReceber objeto) throws Exception {
        ERPFrames.frameParaObjeto(frame, id);
        ERPFrames.frameParaObjeto(frame, objeto);
        objeto.setId(id);
        String idtipopagamento = ERPFrames.getValorCampoFrame(frame, "idtipopagamento");
        if (idtipopagamento != null) {
            objeto.setTipoPagamento(CTipoPagamento.procuraTipoPagamento(Integer.parseInt(idtipopagamento)));
        }
        objeto.setIdusuario(CFrame.getUsuario().getIdUsuario());
        objeto.setDataInclusao(new Timestamp(System.currentTimeMillis()));
        return objeto;
    }

    public void salva(JInternalFrame frame) {
        if (titulo != null) {
            try {
                if (objeto == null) {
                    objeto = new BaixaReceber();
                }
                if (id == null) {
                    id = new BaixaReceberId();
                }

                objeto = frameParaObjeto(frame, objeto);

                if (objeto.getDataPagamento() != null) {

                    titulo.setValorPago(titulo.getValorPago().add(objeto.getValor().subtract(valor_antigo)));
                    titulo.setJuros(titulo.getJuros().add(objeto.getJuros().subtract(juros_antigo)));

                    if (titulo.getValor().compareTo(titulo.getValorPago()) == 0) {
                        titulo.setDataPagamento(objeto.getDataPagamento());
                    } else {
                        titulo.setDataPagamento(null);
                    }


                    Object[] objetos = new Object[]{titulo, objeto};

                    ERPDados.gravaObjetos(objetos);

                    JOptionPane.showMessageDialog(frame, "Registro salvo com sucesso!");

                    titulo.setCorrentista(cliente);
                    objeto.setTituloReceber(titulo);
                    BaixaReceber baixa = objeto;

                    cancela(frame);

                    if (baixa.getTipoPagamento() != null) {
                        if (baixa.getTipoPagamento().getFlagGerarecibo().booleanValue()) {
                            if (JOptionPane.showConfirmDialog(frame, "Deseja imprimir o recibo?", null, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                                new CRecibo().recibo(Arrays.asList(new BaixaReceber[]{baixa}));
                            }
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "É preciso informar a data do pagamento", "Erro", JOptionPane.ERROR_MESSAGE);
                    ERPFrames.setFocus(frame, "DataPagamento");
                }

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
            clienteParaFrame(frame);
            ERPFrames.setFocus(frame, "idtipotitulo");
        } else {
            ERPFrames.limparTodosCampos(frame);
            cliente = null;
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
            ((BaixaReceberTableModel) ((JTable) ERPFrames.getComponent(frame, "jTable1")).getModel()).setLinhas(itens);
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
                        if (titulo.getValor().compareTo(titulo.getValorPago()) == 0) {
                            titulo.setDataPagamento(objeto.getDataPagamento());
                        } else {
                            titulo.setDataPagamento(null);
                        }

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
        itens = listaBaixasRecebePorTitulo(titulo.getId().getIdcorrentista(),
                titulo.getId().getIdtipotitulo(),
                titulo.getId().getNumero(),
                titulo.getId().getParcela(),
                new String[]{"tipoPagamento"});

        try {
            ((BaixaReceberTableModel) ((JTable) ERPFrames.getComponent(frame, "jTable1")).getModel()).setLinhas(itens);
        } catch (Exception e) {
        }
    }

    public void setObjeto(JInternalFrame frame, BaixaReceber objeto) {
        this.objeto = objeto;
        objetoParaFrame(frame);
    }

    public static List<BaixaReceber> listaBaixaReceber(
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

        return ERPDados.consultaLista(BaixaReceber.class, criterions, Join, -1);
    }
}
