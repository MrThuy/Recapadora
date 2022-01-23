package erp.controles.caixa;

import erp.modelos.banco.LancamentoCaixa;
import erp.modelos.banco.Conta;
import erp.modelos.banco.LancamentoCaixaId;
import erp.modelos.banco.Correntista;
import erp.modelos.banco.SaldoCaixa;
import erp.modelos.banco.SaldoCaixaId;
import erp.controles.CFrame;
import erp.controles.cadastro.CConta;
import erp.controles.cadastro.CCorrentista;
import erp.modelos.*;
import erp.modelos.tablemodel.LancamentoCaixaTableModel;
import erp.util.ERPDados;
import erp.util.ERPData;
import erp.util.ERPFocusGrabber;
import erp.util.ERPFrames;
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
public class CLancamentoCaixaS {

    private LancamentoCaixa objeto;
    private LancamentoCaixaId id;
    private List<LancamentoCaixa> itens;
    private Correntista empresa;
    private Conta conta;
    private SaldoCaixa saldoInicial;
    private BigDecimal valor_antigo;
    private Character falg_mov_antigo;

    public void criticaEmpresa(JInternalFrame frame) {
        String idEmpresa = ERPFrames.getValorCampoFrame(frame, "idempresa");
        if (idEmpresa != null) {
            empresa = CCorrentista.procuraCorrentista(Integer.parseInt(idEmpresa));
            if (CCorrentista.erroEmpresa(frame, empresa)) {
                ERPFrames.setValorCampoFrame(frame, "idempresa", "");
                ERPFrames.setFocus(frame, "idempresa");
            } else {
                CarregaLancamentos(frame);
                if (ERPFrames.getValorCampoFrame(frame, "dataLancamento").isEmpty()) {
                    ERPFrames.setValorCampoFrame(frame, "dataLancamento", ERPData.agora());
                }
            }
        }
    }

    public void criticaData(JInternalFrame frame) {
        String data = ERPFrames.getValorCampoFrame(frame, "dataLancamento");
        if (!data.isEmpty()) {
            CarregaLancamentos(frame);
        }
    }

    public void enterNumero(JInternalFrame frame) {
        String data = ERPFrames.getValorCampoFrame(frame, "dataLancamento");
        String idlancamento = ERPFrames.getValorCampoFrame(frame, "idlancamento");
        if ((empresa != null) && (!data.isEmpty()) && (idlancamento.isEmpty())) {
            Integer numero = ultimoLancamento(empresa.getIdcorrentista(), ERPData.toSqlDate(data));
            ERPFrames.setValorCampoFrame(frame, "idlancamento", numero + 1);
        }
    }

    public void criticaNumero(JInternalFrame frame) {
        if (empresa != null) {
            String dataLancamento = ERPFrames.getValorCampoFrame(frame, "dataLancamento");
            if (!dataLancamento.isEmpty()) {
                String idlancamento = ERPFrames.getValorCampoFrame(frame, "idlancamento");
                if (!idlancamento.isEmpty()) {
                    objeto = lancamentosCaixa(
                            empresa.getIdcorrentista(),
                            ERPData.toSqlDate(dataLancamento), ERPData.toSqlDate(dataLancamento),
                            Integer.valueOf(idlancamento), null,
                            new String[]{"conta"});
                    objetoParaFrame(frame);
                } else {
                    enterNumero(frame);
                }
            }
        }
    }

    public void pesquisaConta(JInternalFrame frame) {
        //ERPFrames.setFocus(frame, "entrada");
        conta = CConta.pesquisaConta(CConta.TIPO.ATIVOS);
        if (conta != null) {
            contaParaFrame(frame);
        }
    }

    public void criticaConta(JInternalFrame frame) {
        String idconta = ERPFrames.getValorCampoFrame(frame, "idconta");
        if (idconta.trim().length() > 0) {
            conta = CConta.procuraConta(Integer.parseInt(idconta));
            if (!CConta.erroConta(frame, conta)) {
                contaParaFrame(frame);
            } else {
                ERPFrames.setValorCampoFrame(frame, "idconta", "");
                SwingUtilities.invokeLater(new ERPFocusGrabber(ERPFrames.getComponent(frame, "idconta")));
            }
        }
    }

    public void mudouRadio(JInternalFrame frame) {
        if (conta != null) {
            if (objeto == null) {
                boolean entrada = ERPFrames.getValorCampoFrame(frame, "entrada").equalsIgnoreCase("true") ? true : false;
                if (entrada) {
                    ERPFrames.setValorCampoFrame(frame, "observacao", conta.getObservacaoEntrada());
                } else {
                    ERPFrames.setValorCampoFrame(frame, "observacao", conta.getObservacaoSaida());
                }
            }
            //ERPFrames.setFocus(frame, "valor");
        }
    }

    public static List<SaldoCaixa> listaSaldosCaixaPosteriores(
            int idempresa,
            Date data) {

        List<Criterion> criterion = new ArrayList<>();

        if (idempresa > 0) {
            criterion.add(Expression.eq("id.idempresa", idempresa));
        }
        if (data != null) {
            criterion.add(Expression.gt("id.dataSaldo", data));
        }

        Criterion[] criterions = criterion.toArray(new Criterion[criterion.size()]);

        return ERPDados.consultaLista(SaldoCaixa.class, criterions, -1);
    }

    public static SaldoCaixa saldoInicial(int idempresa, Date dataSaldo) {
        SaldoCaixa retorno = null;
        Date data = (Date) ERPDados.consultaMaxObjeto(SaldoCaixa.class,
                new Criterion[]{
            Expression.eq("id.idempresa", idempresa),
            Expression.le("id.dataSaldo", dataSaldo)},
                "id.dataSaldo");
        if (data != null) {
            retorno = (SaldoCaixa) ERPDados.consultaObjeto(SaldoCaixa.class,
                    new Criterion[]{
                Expression.eq("id.idempresa", idempresa),
                Expression.eq("id.dataSaldo", data)}, 1);
        }
        if ((data == null) || (retorno == null)) {
            retorno = null;
        }

        return retorno;
    }

    public static List<SaldoCaixa> listaSaldosCaixa(
            int idempresa,
            Date data_ini, Date data_fim,
            String[] Join) {

        List<Criterion> criterion = new ArrayList<>();

        if (idempresa > 0) {
            criterion.add(Expression.eq("id.idempresa", idempresa));
        }
        if ((data_ini != null) && (data_fim != null)) {
            criterion.add(Expression.between("id.dataSaldo", data_ini, data_fim));
        }

        Criterion[] criterions = criterion.toArray(new Criterion[criterion.size()]);

        return ERPDados.consultaLista(SaldoCaixa.class, criterions, Join, -1);
    }

    public static List<LancamentoCaixa> listaLancamentosCaixa(
            int idempresa,
            Date data_ini, Date data_fim,
            Integer numero_ini, Integer numero_fim,
            Integer idConta,
            BigDecimal valor_ini, BigDecimal valor_fim,
            String[] Join) {

        List<Criterion> criterion = new ArrayList<>();

        if (idempresa > 0) {
            criterion.add(Expression.eq("id.idempresa", idempresa));
        }
        if ((data_ini != null) && (data_fim != null)) {
            criterion.add(Expression.between("id.dataLancamento", data_ini, data_fim));
        }
        if ((numero_ini != null) && (numero_fim != null)) {
            criterion.add(Expression.between("id.idlancamento", numero_ini.intValue(), numero_fim.intValue()));
        }
        if ((idConta != null) && (idConta.intValue() > 0)) {
            criterion.add(Expression.eq("conta.idconta", idConta.intValue()));
        }
        if ((valor_ini != null) && (valor_fim != null)) {
            criterion.add(Expression.between("valor", valor_ini, valor_fim));
        }
        Criterion[] criterions = criterion.toArray(new Criterion[criterion.size()]);

        return ERPDados.consultaLista(LancamentoCaixa.class, criterions, Join, -1);
    }

    public static List<LancamentoCaixa> listaLancamentosCaixa(
            int idempresa,
            Date data_ini, Date data_fim,
            Integer numero,
            Integer idConta,
            String[] Join) {

        List<Criterion> criterion = new ArrayList<>();

        if (idempresa > 0) {
            criterion.add(Expression.eq("id.idempresa", idempresa));
        }
        if ((data_ini != null) && (data_fim != null)) {
            criterion.add(Expression.between("id.dataLancamento", data_ini, data_fim));
        }
        if (numero != null) {
            criterion.add(Expression.eq("id.idlancamento", numero.intValue()));
        }
        if (idConta != null) {
            criterion.add(Expression.eq("conta.idconta", idConta.intValue()));
        }

        Criterion[] criterions = criterion.toArray(new Criterion[criterion.size()]);

        return ERPDados.consultaLista(LancamentoCaixa.class, criterions, Join, -1);
    }

    public static LancamentoCaixa lancamentosCaixa(
            int idempresa,
            Date data_ini, Date data_fim,
            Integer numero,
            Integer idConta,
            String[] Join) {
        List list = listaLancamentosCaixa(idempresa, data_ini, data_fim, numero, idConta, Join);
        return (LancamentoCaixa) ERPDados.proximoObjeto(list);
    }

    public static Integer ultimoLancamento(int idempresa, Date dataLancamento) {
        Integer retorno = (Integer) ERPDados.consultaMaxObjeto(LancamentoCaixa.class,
                new Criterion[]{
            Expression.eq("id.idempresa", idempresa),
            Expression.eq("id.dataLancamento", dataLancamento)},
                "id.idlancamento");
        return retorno == null ? 0 : retorno;
    }

    private void contaParaFrame(JInternalFrame frame) {
        ERPFrames.objetoParaFrame(frame, conta);
        if (objeto == null) {
            if (conta.getFlagPadrao() == 'E') {
                ERPFrames.setValorCampoFrame(frame, "entrada", true);
            } else {
                ERPFrames.setValorCampoFrame(frame, "saida", true);
            }
        }
        mudouRadio(frame);
    }

    public void objetoParaFrame(JInternalFrame frame) {
        if (objeto != null) {
            valor_antigo = objeto.getValor();
            falg_mov_antigo = objeto.getFlagMovimento();
            id = objeto.getId();
            conta = objeto.getConta();
            if (objeto.getFlagMovimento() == 'E') {
                ERPFrames.setValorCampoFrame(frame, "entrada", true);
            } else {
                ERPFrames.setValorCampoFrame(frame, "saida", true);
            }
            contaParaFrame(frame);
            ERPFrames.objetoParaFrame(frame, id);
            ERPFrames.objetoParaFrame(frame, objeto);
            ERPFrames.setFocus(frame, "idconta");
        }
    }

    private LancamentoCaixa frameParaObjeto(JInternalFrame frame, LancamentoCaixa objeto) throws Exception {
        ERPFrames.frameParaObjeto(frame, id);
        ERPFrames.frameParaObjeto(frame, objeto);
        objeto.setId(id);
        objeto.setConta(conta);
        objeto.setFlagMovimento(ERPFrames.getValorCampoFrame(frame, "entrada").equalsIgnoreCase("true") ? 'E' : 'S');
        objeto.setUsuario(CFrame.getUsuario());
        objeto.setDataInclusao(ERPData.agora());
        return objeto;
    }

    public void salva(JInternalFrame frame) {
        if (empresa != null) {
            if (conta != null) {
                String data = ERPFrames.getValorCampoFrame(frame, "dataLancamento");
                String numero = ERPFrames.getValorCampoFrame(frame, "idlancamento");
                if ((!numero.isEmpty()) && (!data.isEmpty())) {
                    try {
                        if (objeto == null) {
                            objeto = new LancamentoCaixa();
                            id = new LancamentoCaixaId();
                        }

                        objeto = frameParaObjeto(frame, objeto);

                        BigDecimal ajuste = new BigDecimal(0);

                        if (objeto.getFlagMovimento() == 'E') {
                            ajuste = ajuste.add(objeto.getValor());
                        } else {
                            ajuste = ajuste.subtract(objeto.getValor());
                        }

                        if (valor_antigo != null) {
                            if (falg_mov_antigo == 'E') {
                                ajuste = ajuste.subtract(valor_antigo);
                            } else {
                                ajuste = ajuste.add(valor_antigo);
                            }
                        }

                        ArrayList<Object> objetos = new ArrayList<>();
                        objetos.add(objeto);

                        saldoInicial = saldoInicial(empresa.getIdcorrentista(),
                                ERPData.toSqlDate(objeto.getId().getDataLancamento()));

                        if (saldoInicial == null) {
                            saldoInicial = new SaldoCaixa(
                                    new SaldoCaixaId(empresa.getIdcorrentista(), ERPData.toSqlDate(data)),
                                    empresa, BigDecimal.ZERO);
                            objetos.add(saldoInicial);
                        } else if (saldoInicial.getId().getDataSaldo().compareTo(objeto.getId().getDataLancamento()) != 0) {
                            objetos.add(new SaldoCaixa(
                                    new SaldoCaixaId(empresa.getIdcorrentista(), ERPData.toSqlDate(data)),
                                    empresa, saldoInicial.getValor()));
                        }

                        List<SaldoCaixa> saldos_post = listaSaldosCaixaPosteriores(
                                empresa.getIdcorrentista(), ERPData.toSqlDate(data));

                        if ((saldos_post != null) && (!saldos_post.isEmpty())) {
                            for (SaldoCaixa saldoCaixa : saldos_post) {
                                saldoCaixa.setValor(saldoCaixa.getValor().add(ajuste));
                                objetos.add(saldoCaixa);
                            }
                        }

                        SaldoCaixa prox_saldo = saldoInicial(empresa.getIdcorrentista(),
                                ERPData.toSqlDate(ERPData.somaDias(data, 1)));
                        if ((prox_saldo == null)
                                || (prox_saldo.getId().getDataSaldo().compareTo(ERPData.toSqlDate(ERPData.somaDias(data, 1))) != 0)) {
                            //else {
                            prox_saldo = new SaldoCaixa(
                                    new SaldoCaixaId(empresa.getIdcorrentista(), ERPData.toSqlDate(ERPData.somaDias(data, 1))),
                                    empresa,
                                    saldoInicial.getValor().add(ajuste));
                            objetos.add(prox_saldo);
                            //}
                        }

                        ERPDados.gravaObjetos(objetos.toArray());

                        JOptionPane.showMessageDialog(frame, "Registro salvo com sucesso!");

                        cancela(frame);

                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(frame, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    }

                } else {
                    JOptionPane.showMessageDialog(frame, "Os campos empresa, data e número \n são obrigatórios seus preenchimentos", "Erro", JOptionPane.ERROR_MESSAGE);
                    ERPFrames.setFocus(frame, "data");
                }
            } else {
                ERPFrames.setFocus(frame, "conta");
            }
        } else {
            ERPFrames.setFocus(frame, "idempresa");
        }
    }

    public void cancela(JInternalFrame frame) {
        if ((conta != null) && (empresa != null)) {
            String data = ERPFrames.getValorCampoFrame(frame, "dataLancamento");
            ERPFrames.limparTodosCampos(frame);
            ERPFrames.setValorCampoFrame(frame, "idempresa", empresa.getIdcorrentista());
            ERPFrames.setValorCampoFrame(frame, "dataLancamento", data);
            CarregaLancamentos(frame);
            ERPFrames.setFocus(frame, "idlancamento");
        } else {
            ERPFrames.limparTodosCampos(frame);
            empresa = null;
            itens = null;
            try {
                ((LancamentoCaixaTableModel) ((JTable) ERPFrames.getComponent(frame, "JTable")).getModel()).limpar();
            } catch (Exception e) {
            }
            ERPFrames.setFocus(frame, "idempresa");
        }
        objeto = null;
        id = null;
        conta = null;
        valor_antigo = null;
        falg_mov_antigo = null;
    }

    public void exclui(JInternalFrame frame) {
        if (empresa != null) {
            String data = ERPFrames.getValorCampoFrame(frame, "dataLancamento");
            String numero = ERPFrames.getValorCampoFrame(frame, "idlancamento");
            if ((!numero.isEmpty()) && (!data.isEmpty())) {
                objeto = lancamentosCaixa(
                        empresa.getIdcorrentista(),
                        ERPData.toSqlDate(data), ERPData.toSqlDate(data),
                        Integer.valueOf(numero),
                        null,
                        new String[]{});
                if (objeto != null) {

                    BigDecimal ajuste = new BigDecimal(0);

                    if (objeto.getFlagMovimento() == 'E') {
                        ajuste = ajuste.subtract(objeto.getValor());
                    } else {
                        ajuste = ajuste.add(objeto.getValor());
                    }

                    ArrayList<Object> objetos = new ArrayList<>();
                    ArrayList<ERPDados.OPERACOES> operacoes = new ArrayList<>();
                    objetos.add(objeto);
                    operacoes.add(ERPDados.OPERACOES.EXCLUIR);

                    List<SaldoCaixa> saldos_post = listaSaldosCaixaPosteriores(
                            empresa.getIdcorrentista(), ERPData.toSqlDate(data));

                    for (SaldoCaixa saldoCaixa : saldos_post) {
                        saldoCaixa.setValor(saldoCaixa.getValor().add(ajuste));
                        objetos.add(saldoCaixa);
                        operacoes.add(ERPDados.OPERACOES.ALTERAR);
                    }

                    try {
                        ERPDados.executaOperacao(
                                objetos.toArray(),
                                operacoes.toArray(new ERPDados.OPERACOES[operacoes.size()]));

                        JOptionPane.showMessageDialog(frame, "Registro excluido com sucesso!");

                        cancela(frame);

                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(frame, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    }

                }
            } else {
                JOptionPane.showMessageDialog(frame, "Os campos cliente, número e parcela \n são obrigatórios seus preenchimentos", "Erro", JOptionPane.ERROR_MESSAGE);
                ERPFrames.setFocus(frame, "parcela");
            }
        } else {
            ERPFrames.setFocus(frame, "idcorrentista");
        }
    }

    public void setObjeto(JInternalFrame frame, Object objeto) {
        this.objeto = (LancamentoCaixa) objeto;
        objetoParaFrame(frame);
    }

    private void CarregaLancamentos(JInternalFrame frame) {
        if (empresa != null) {
            String dataLancamento = ERPFrames.getValorCampoFrame(frame, "dataLancamento");
            if (!dataLancamento.isEmpty()) {
                itens = listaLancamentosCaixa(empresa.getIdcorrentista(),
                        ERPData.toSqlDate(dataLancamento), ERPData.toSqlDate(dataLancamento),
                        null, null,
                        new String[]{"conta"});
                try {
                    ((LancamentoCaixaTableModel) ((JTable) ERPFrames.getComponent(frame, "JTable")).getModel()).setLinhas(itens);
                } catch (Exception e) {
                }
                Integer numero = ultimoLancamento(empresa.getIdcorrentista(), ERPData.toSqlDate(dataLancamento));
                ERPFrames.setValorCampoFrame(frame, "idlancamento", numero + 1);
                ERPFrames.setFocus(frame, "idlancamento");
                saldoInicial = saldoInicial(empresa.getIdcorrentista(),
                        ERPData.toSqlDate(dataLancamento));

                saldoInicial = saldoInicial == null ? new SaldoCaixa(new SaldoCaixaId(empresa.getIdcorrentista(), ERPData.toSqlDate(dataLancamento)),
                        empresa, BigDecimal.ZERO) : saldoInicial;

                BigDecimal total_entrada = ((LancamentoCaixaTableModel) ((JTable) ERPFrames.getComponent(frame, "JTable")).getModel()).getSomaCol(4);
                BigDecimal total_saida = ((LancamentoCaixaTableModel) ((JTable) ERPFrames.getComponent(frame, "JTable")).getModel()).getSomaCol(5);

                ERPFrames.setValorCampoFrame(frame, "saldo_ini", saldoInicial.getValor());
                ERPFrames.setValorCampoFrame(frame, "total_entrada", total_entrada);
                ERPFrames.setValorCampoFrame(frame, "total_saida", total_saida);
                ERPFrames.setValorCampoFrame(frame, "saldo_final", saldoInicial.getValor().add(total_entrada).subtract(total_saida));
            }
        } else {
            ERPFrames.setValorCampoFrame(frame, "idempresa", "");
            ERPFrames.setFocus(frame, "idempresa");
        }
    }
}
