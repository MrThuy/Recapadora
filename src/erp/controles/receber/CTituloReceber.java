package erp.controles.receber;

import erp.controles.CFrame;
import erp.controles.cadastro.CCorrentista;
import erp.controles.cadastro.CTipoTitulo;
import erp.modelos.banco.Correntista;
import erp.modelos.banco.TipoTitulo;
import erp.modelos.banco.TituloReceber;
import erp.modelos.banco.TituloReceberId;
import erp.modelos.tablemodel.TituloReceberTableModel;
import erp.util.ERPDados;
import erp.util.ERPData;
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
public class CTituloReceber {

    private TituloReceber objeto;
    private TituloReceberId id;
    private List<TituloReceber> itens;
    private Correntista cliente;
    private TipoTitulo tipoTitulo;

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
                            ultimoNumeroTipoTituloReceber(tipoTitulo.getIdtipotitulo()) + 1);
                }
            }
        }
    }

    public void criticaNumero(JInternalFrame frame) {
        String numero = ERPFrames.getValorCampoFrame(frame, "numero");
        if (numero.trim().length() > 0 && tipoTitulo != null && cliente != null) {
            ERPFrames.setValorCampoFrame(frame, "parcela",
                    ultimaParcelaTituloReceber(cliente.getIdcorrentista(),
                    tipoTitulo.getIdtipotitulo(),
                    Integer.parseInt(numero)) + 1);
        }
    }

    public void pesquisaNumeroParcela(JInternalFrame frame) {
        if (cliente != null) {
            if (tipoTitulo != null) {
                objeto = pesquisaTitulosRecebePorClienteETipoTitulo(
                        TIPO.ABERTOS,
                        cliente.getIdcorrentista(),
                        tipoTitulo.getIdtipotitulo());
                if (objeto != null) {
                    objetoParaFrame(frame);
                }

            } else {
                objeto = pesquisaTitulosRecebePorCliente(
                        TIPO.ABERTOS,
                        cliente.getIdcorrentista());
                if (objeto != null) {
                    objetoParaFrame(frame);
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
                    objeto = procuraTituloReceber(cliente.getIdcorrentista(),
                            Integer.parseInt(numero),
                            Integer.parseInt(parcela),
                            tipoTitulo.getIdtipotitulo());
                    if (objeto != null) {
                        objetoParaFrame(frame);
                    } else {
                        ERPFrames.setValorCampoFrame(frame, "dataEmissao", ERPData.agora());
//                        ERPFrames.setValorCampoFrame(frame, "dataVencimento",
//                                ERPData.somaDias(ERPData.agora(), 30 * Integer.parseInt(parcela)));
                    }
                }
            } else {
                ERPFrames.setFocus(frame, "idtipotitulo");
            }
        } else {
            ERPFrames.setFocus(frame, "idcorrentista");
        }
    }

    public void criticaDataEmissao(JInternalFrame frame) {
        String dataEmissao = ERPFrames.getValorCampoFrame(frame, "dataEmissao");
        String parcela = ERPFrames.getValorCampoFrame(frame, "parcela");
        if ((dataEmissao.trim().length() > 0) && (parcela.trim().length() > 0)) {
            ERPFrames.setValorCampoFrame(frame, "dataVencimento",
                    ERPData.somaDias(ERPData.toSqlDate(dataEmissao), 30 * Integer.parseInt(parcela)));
        }
    }

    public static TituloReceber pesquisaTitulosRecebePorCliente(TIPO tipo, int idcliente) {
        TituloReceber resultado;
        VPesquisa pesquisa = new VPesquisa(CFrame.getFrame(), "Título Receber", true);
        try {
            pesquisa.setTableModel(new TituloReceberTableModel(listaTitulosRecebePorCliente(tipo, idcliente, new String[]{"tipoTitulo", "idfinanceira"})));
            pesquisa.setVisible(true);
            resultado = (TituloReceber) pesquisa.getResultado();
            return resultado;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static TituloReceber pesquisaTitulosRecebePorClienteETipoTitulo(TIPO tipo, int idcliente, int idtipotitulo) {
        TituloReceber resultado;
        VPesquisa pesquisa = new VPesquisa(CFrame.getFrame(), "Título Receber", true);
        try {
            pesquisa.setTableModel(new TituloReceberTableModel(listaTitulosRecebePorCliente(tipo, idcliente, new String[]{"tipoTitulo", "idfinanceira"})));
            pesquisa.setVisible(true);
            resultado = (TituloReceber) pesquisa.getResultado();
            return resultado;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static TituloReceber procuraTituloReceber(int idcliente, int numero, int parcela, int idtipotitulo) {
        return (TituloReceber) ERPDados.consultaObjeto(TituloReceber.class,
                (new Criterion[]{
            Expression.eq("id.idcorrentista", idcliente),
            Expression.eq("id.numero", numero),
            Expression.eq("id.parcela", parcela),
            Expression.eq("id.idtipotitulo", idtipotitulo)}), new String[]{"correntista", "tipoTitulo", "idfinanceira"}, 1);
    }

    private Integer ultimaParcelaTituloReceber(int idcliente, int idtipotitulo, int numero) {
        Integer retorno = (Integer) ERPDados.consultaMaxObjeto(TituloReceber.class,
                new Criterion[]{
            Expression.eq("id.idcorrentista", idcliente),
            Expression.eq("id.numero", numero),
            Expression.eq("id.idtipotitulo", idtipotitulo)}, "id.parcela");
        return retorno == null ? 0 : retorno;
    }

    private Integer ultimoNumeroTipoTituloReceber(int idtipotitulo) {
        Integer retorno = (Integer) ERPDados.consultaMaxObjeto(TituloReceber.class,
                new Criterion[]{
            Expression.eq("id.idtipotitulo", idtipotitulo)}, "id.numero");
        return retorno == null ? 0 : retorno;
    }

    public static List<TituloReceber> listaTitulosRecebePorCliente(TIPO tipo, int idcliente, String[] Join) {
        switch (tipo) {
            case TODOS: {
                return ERPDados.consultaLista(TituloReceber.class, new Criterion[]{
                    Expression.eq("id.idcorrentista", idcliente)}, Join, -1);
            }
            case ABERTOS: {
                return ERPDados.consultaLista(TituloReceber.class, new Criterion[]{
                    Expression.eq("id.idcorrentista", idcliente),
                    Expression.neProperty("valor", "valorPago")}, Join, -1);
            }
            case PAGOS: {
                return ERPDados.consultaLista(TituloReceber.class, new Criterion[]{
                    Expression.eq("id.idcorrentista", idcliente),
                    Expression.eqProperty("valor", "valorPago")}, Join, -1);
            }
            default: {
                return null;
            }
        }
    }

    public static List<TituloReceber> listaTitulosRecebePorClienteETipoTitulo(TIPO tipo, int idcliente, int idtipotitulo, String[] Join) {
        switch (tipo) {
            case TODOS: {
                return ERPDados.consultaLista(TituloReceber.class, new Criterion[]{
                    Expression.eq("id.idcorrentista", idcliente),
                    Expression.eq("id.idtipotitulo", idtipotitulo)}, Join, -1);
            }
            case ABERTOS: {
                return ERPDados.consultaLista(TituloReceber.class, new Criterion[]{
                    Expression.eq("id.idcorrentista", idcliente),
                    Expression.eq("id.idtipotitulo", idtipotitulo),
                    Expression.neProperty("valor", "valorPago")}, Join, -1);
            }
            case PAGOS: {
                return ERPDados.consultaLista(TituloReceber.class, new Criterion[]{
                    Expression.eq("id.idcorrentista", idcliente),
                    Expression.eq("id.idtipotitulo", idtipotitulo),
                    Expression.eqProperty("valor", "valorPago")}, Join, -1);
            }
            default: {
                return null;
            }
        }
    }

    public static List<TituloReceber> listaTitulosReceber(
            TIPO tipo,
            int idCliente,
            int idtipotitulo,
            int numero_ini,
            int numero_fim,
            Date dataEmissao_ini,
            Date dataEmissao_fim,
            Date dataVencimento_ini,
            Date dataVencimento_fim,
            Date dataPagamento_ini,
            Date dataPagamento_fim,
            BigDecimal valor_ini,
            BigDecimal valor_fim,
            int idfinanceira, String[] Join) {

        List<Criterion> criterion = new ArrayList<>();

        if (idCliente > 0) {
            criterion.add(Expression.eq("id.idcorrentista", idCliente));
        }
        if (idtipotitulo > 0) {
            criterion.add(Expression.eq("id.idtipotitulo", idtipotitulo));
        }
        criterion.add(Expression.between("id.numero", numero_ini, numero_fim));
        criterion.add(Expression.between("dataEmissao", dataEmissao_ini, dataEmissao_fim));
        criterion.add(Expression.between("dataVencimento", dataVencimento_ini, dataVencimento_fim));
        criterion.add(Expression.between("valor", valor_ini, valor_fim));

        if ((dataPagamento_ini != null) && (dataPagamento_fim != null)) {
            criterion.add(Expression.between("dataPagamento", dataPagamento_ini, dataPagamento_fim));
        }

        if (idfinanceira > 0) {
            criterion.add(Expression.eq("idfinanceira.idcorrentista", idfinanceira));
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

        return ERPDados.consultaLista(TituloReceber.class, criterions, Join, -1);
    }

    private void clienteParaFrame(JInternalFrame frame) {
        ERPFrames.objetoParaFrame(frame, cliente);
        carregaGrid(frame);
        ERPFrames.setFocus(frame, "idtipotitulo");
    }

    public void objetoParaFrame(JInternalFrame frame) {
        id = objeto.getId();
        //cliente = objeto.getCorrentista();
        tipoTitulo = objeto.getTipoTitulo();
        ERPFrames.setValorCampoFrame(frame, "idtipotitulo", objeto.getTipoTitulo().getIdtipotitulo());
        if (objeto.getIdfinanceira() != null) {
            ERPFrames.setValorCampoFrame(frame, "idfinanceira", objeto.getIdfinanceira().getIdcorrentista());
        } else {
            ERPFrames.limpaCampo(ERPFrames.getComponent(frame, "idfinanceira"));
        }
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

    private TituloReceber frameParaObjeto(JInternalFrame frame, TituloReceber objeto) throws Exception {
        ERPFrames.frameParaObjeto(frame, id);
        ERPFrames.frameParaObjeto(frame, objeto);
        objeto.setId(id);
        objeto.setCorrentista(cliente);
        objeto.setTipoTitulo(CTipoTitulo.procuraTipoTitulo(id.getIdtipotitulo()));
        String idfinanceira = ERPFrames.getValorCampoFrame(frame, "idfinanceira");
        if (idfinanceira != null) {
            objeto.setIdfinanceira(CCorrentista.procuraCorrentista(Integer.parseInt(idfinanceira)));
        }
        objeto.setUsuario(CFrame.getUsuario());
        objeto.setDataInclusao(new Timestamp(System.currentTimeMillis()));
        return objeto;
    }

    public void salva(JInternalFrame frame) {
        if (cliente != null) {
            if (tipoTitulo != null) {
                String numero = ERPFrames.getValorCampoFrame(frame, "numero");
                String parcela = ERPFrames.getValorCampoFrame(frame, "parcela");
                if ((numero.trim().length() > 0) && (parcela.trim().length() > 0)) {
                    try {
                        if (objeto == null) {
                            objeto = new TituloReceber();
                            objeto.setValorPago(BigDecimal.ZERO);
                            objeto.setJuros(BigDecimal.ZERO);
                        }
                        if (id == null) {
                            id = new TituloReceberId();
                        }

                        objeto = frameParaObjeto(frame, objeto);

                        ERPDados.gravaObjeto(objeto);

                        JOptionPane.showMessageDialog(frame, "Registro salvo com sucesso!");

                        TituloReceber titulo = objeto;

                        cancela(frame);

                        if (tipoTitulo.getFlagGeraduplicata().booleanValue()) {
                            new CDuplicataReceber().duplicataReceber(titulo);
                        }

                        if (tipoTitulo.getFlagGerapromissoria().booleanValue()) {
                            if (JOptionPane.showConfirmDialog(frame, "Deseja imprimir o promissória?", null, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                                new CPromissória().promissoria(titulo);
                            }
                        }

                        ERPFrames.setValorCampoFrame(frame, "idtipotitulo", tipoTitulo.getIdtipotitulo());

                        ERPFrames.setValorCampoFrame(frame, "numero", titulo.getId().getNumero());

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
        if (!(ERPFrames.getValorCampoFrame(frame, "parcela").isEmpty()) && (cliente != null)) {
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
            cliente = null;
            itens = null;
            tipoTitulo = null;
            try {
                ((TituloReceberTableModel) ((JTable) ERPFrames.getComponent(frame, "titulosTable")).getModel()).setLinhas(itens);
            } catch (Exception e) {
            }
            ERPFrames.setFocus(frame, "idcorrentista");
        }
    }

    public void exclui(JInternalFrame frame) {
        if (cliente != null) {
            if (tipoTitulo != null) {
                String numero = ERPFrames.getValorCampoFrame(frame, "numero");
                String parcela = ERPFrames.getValorCampoFrame(frame, "parcela");
                if ((numero.trim().length() > 0) && (parcela.trim().length() > 0)) {
                    objeto = procuraTituloReceber(cliente.getIdcorrentista(),
                            Integer.parseInt(numero),
                            Integer.parseInt(parcela),
                            tipoTitulo.getIdtipotitulo());
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

    public static boolean erroTituloReceber(JInternalFrame frame, TituloReceber erro) {
        if (erro == null) {
            JOptionPane.showMessageDialog(frame, "Título do receber não encontrado", "Erro", JOptionPane.WARNING_MESSAGE);
            return true;
        } else {
            return false;
        }
    }

    private void carregaGrid(JInternalFrame frame) {
        itens = listaTitulosRecebePorCliente(TIPO.ABERTOS, cliente.getIdcorrentista(), new String[]{"tipoTitulo", "idfinanceira"});
        try {
            ((TituloReceberTableModel) ((JTable) ERPFrames.getComponent(frame, "titulosTable")).getModel()).setLinhas(itens);
        } catch (Exception e) {
        }
    }

    public void setObjeto(JInternalFrame frame, TituloReceber objeto) {
        this.objeto = objeto;
        objetoParaFrame(frame);
    }
}
