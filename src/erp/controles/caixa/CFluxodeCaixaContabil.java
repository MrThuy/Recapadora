package erp.controles.caixa;

import erp.modelos.banco.Correntista;
import erp.modelos.FluxoCaixa;
import erp.modelos.banco.LancamentoCaixa;
import erp.modelos.banco.SaldoCaixa;
import erp.util.ERPData;
import erp.util.ERPFrames;
import erp.util.ERPRelatorios;
import erp.util.ERPValor;
import java.io.InputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * @author Arthur
 */
public class CFluxodeCaixaContabil {

    private Correntista empresa;

    public void salva(JInternalFrame frame) {

        int idempresa = ERPValor.toInt(ERPFrames.getValorCampoFrame(frame, "idempresa"));

        Date data_ini = ERPData.toSqlDate(ERPFrames.getValorCampoFrame(frame, "data_ini"));
        Date data_fim = ERPData.toSqlDate(ERPFrames.getValorCampoFrame(frame, "data_fim"));

        cancela(frame);

        List<LancamentoCaixa> lanc = CLancamentoCaixaS.listaLancamentosCaixa(idempresa, data_ini, data_fim, null, null, new String[]{"conta"});

        List<SaldoCaixa> saldosCaixa = CLancamentoCaixaS.listaSaldosCaixa(idempresa, data_ini, data_fim, new String[]{"correntista"});

        List<FluxoCaixa> fluxoCaixas = new ArrayList<>();

        SaldoCaixa saldodia = null;

        for (LancamentoCaixa lancamentoCaixa : lanc) {
            if ((saldodia == null)
                    || (lancamentoCaixa.getId().getDataLancamento().compareTo(saldodia.getId().getDataSaldo()) != 0)) {

                for (SaldoCaixa saldoCaixa : saldosCaixa) {
                    if (lancamentoCaixa.getId().getDataLancamento().compareTo(saldoCaixa.getId().getDataSaldo()) == 0) {
                        saldodia = saldoCaixa;
                        //saldosCaixa.remove(saldoCaixa);
                        break;
                    }
                }
            }
            fluxoCaixas.add(new FluxoCaixa(saldodia, lancamentoCaixa));
        }

        fluxodeCaixaContabil(fluxoCaixas);

    }

    public void cancela(JInternalFrame frame) {
        ERPFrames.limparTodosCampos(frame);
        empresa = null;
        ERPFrames.setFocus(frame, "idempresa");
        ERPFrames.setValorCampoFrame(frame, "data_ini", ERPData.primeiroDiaMes(ERPData.somaMes(ERPData.agora(),-1)));
        ERPFrames.setValorCampoFrame(frame, "data_fim", ERPData.ultimoDiaMes(ERPData.somaMes(ERPData.agora(),-1)));
    }

    public void fluxodeCaixaContabil(List<FluxoCaixa> fluxoCaixas) {

        InputStream inputStream = getClass().getResourceAsStream("/erp/relatorios/FluxoCaixaContabil.jasper");

        Map parametros = new HashMap();

        // criando os dados que serão passados ao datasource
        List dados = new ArrayList();
        dados.addAll(fluxoCaixas);

        // criando o datasource com os dados criados
        JRDataSource ds = new JRBeanCollectionDataSource(dados);

        try {
            // passando o datasource para o método de criação e exibição do relatório
            ERPRelatorios.openReport("Fluxo Contábil", inputStream, parametros,
                    ds);
        } catch (JRException exc) {
            JOptionPane.showMessageDialog(null, exc.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
