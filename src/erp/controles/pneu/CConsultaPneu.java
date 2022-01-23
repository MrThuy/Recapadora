package erp.controles.pneu;

import erp.modelos.SelecionarObjeto;
import erp.modelos.banco.Marca;
import erp.modelos.banco.Pneu;
import erp.modelos.banco.TamanhoPneu;
import erp.modelos.tablemodel.PneuSelecionavelTableModel;
import erp.util.*;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * @author Arthur
 */
public class CConsultaPneu {

    private List<Pneu> itens;
    private List<SelecionarObjeto> itensSelecionaveis;
    private Marca marca;
    private TamanhoPneu tamanhoPneu;
    private Pneu pneu;

    public void pesquisaPneu(JInternalFrame frame) {
        pneu = CPneu.pesquisaPneus(CPneu.TIPO.TODOS);
        if (pneu != null) {
            carregaItens(frame);
        }
    }

    public void criticaPneu(JInternalFrame frame) {
        String idPneu = ERPFrames.getValorCampoFrame(frame, "idpneu");
        if (idPneu.trim().length() > 0) {
            pneu = CPneu.procuraPneu(Integer.parseInt(idPneu));
            if (CPneu.erroPneu(frame, pneu)) {
                ERPFrames.setValorCampoFrame(frame, "idpneu", "");
                SwingUtilities.invokeLater(new ERPFocusGrabber(ERPFrames.getComponent(frame, "idpneu")));
            } else {
                carregaItens(frame);
            }
        }
    }

    public void pesquisaTamanhoPneu(JInternalFrame frame) {
        tamanhoPneu = CTamanho_Pneu.pesquisaTamanhoPneu();
        if (tamanhoPneu != null) {
            tamanhoParaFrame(frame);
            ERPFrames.setFocus(frame, "lona_min");
        }
    }

    public void criticaTamanhoPneu(JInternalFrame frame) {
        String idTamanho = ERPFrames.getValorCampoFrame(frame, "idtamanhopneu");
        if (idTamanho.trim().length() > 0) {
            tamanhoPneu = CTamanho_Pneu.procuraTamanhoPneu(Integer.parseInt(idTamanho));
            if (CTamanho_Pneu.erroTamanhoPneu(frame, tamanhoPneu)) {
                ERPFrames.setValorCampoFrame(frame, "idtamanhopneu", "");
                ERPFrames.setValorCampoFrame(frame, "descricao", "");
                SwingUtilities.invokeLater(new ERPFocusGrabber(ERPFrames.getComponent(frame, "idtamanhopneu")));
            } else {
                tamanhoParaFrame(frame);
            }
        }
    }

    private void tamanhoParaFrame(JInternalFrame frame) {
        ERPFrames.objetoParaFrame(frame, tamanhoPneu);
    }

    private void carregaItens(JInternalFrame frame) {
        ERPFrames.setValorCampoFrame(frame, "quantidade", 0);

        int idPneu = ERPValor.toInt(ERPFrames.getValorCampoFrame(frame, "idPneu"));
        int idmarca = ERPValor.toInt(ERPFrames.getValorCampoFrame(frame, "idmarca"));
        int idtamanhopneu = ERPValor.toInt(ERPFrames.getValorCampoFrame(frame, "idtamanhopneu"));

        Integer lona_min = Integer.valueOf(ERPFrames.getValorCampoFrame(frame, "lona_min"));
        Integer lona_max = Integer.valueOf(ERPFrames.getValorCampoFrame(frame, "lona_max"));

        BigDecimal valorPreco_min = ERPValor.toBigDecimal(ERPFrames.getValorCampoFrame(frame, "valorPreco_min"));
        BigDecimal valorPreco_max = ERPValor.toBigDecimal(ERPFrames.getValorCampoFrame(frame, "valorPreco_max"));

        String codigo = ERPFrames.getValorCampoFrame(frame, "codigo");

        boolean idtodos = ERPFrames.getValorCampoFrame(frame, "idtodos").equalsIgnoreCase("true") ? true : false;
        boolean idoriginal = ERPFrames.getValorCampoFrame(frame, "idoriginal").equalsIgnoreCase("true") ? true : false;

        CPneu.STATUS status;

        if (idtodos) {
            status = CPneu.STATUS.TODOS;
        } else if (idoriginal) {
            status = CPneu.STATUS.ORIGINAL;
        } else {
            status = CPneu.STATUS.RECAPADO;
        }

        itens = CPneu.listaPneus(
                idPneu,
                idmarca,
                idtamanhopneu,
                lona_min, lona_max,
                null, null,
                null, null,
                valorPreco_min, valorPreco_max,
                null, null,
                null, null,
                codigo,
                status, new String[]{"marca", "tamanhoPneu"});

        itensSelecionaveis = new ArrayList<>();

        for (Pneu item : itens) {
            itensSelecionaveis.add(new SelecionarObjeto(false, item));
        }
        ((PneuSelecionavelTableModel) ((JTable) ERPFrames.getComponent(frame, "jTable1")).getModel()).setLinhas(itensSelecionaveis);
    }

    public void salva(JInternalFrame frame) {
        carregaItens(frame);
        ERPFrames.setValorCampoFrame(frame, "quantidade", "0/" + String.valueOf(itens.size()));
    }

    public void cancela(JInternalFrame frame) {
        ERPFrames.limparTodosCampos(frame);
        pneu = null;
        itens = null;
        itensSelecionaveis = null;
        ((PneuSelecionavelTableModel) ((JTable) ERPFrames.getComponent(frame, "jTable1")).getModel()).setLinhas(null);

        ERPFrames.setFocus(frame, "idpneu");

        ERPFrames.setValorCampoFrame(frame, "lona_min", 0);
        ERPFrames.setValorCampoFrame(frame, "lona_max", 9999999);
        Calendar calendar = new GregorianCalendar();

        ERPFrames.setValorCampoFrame(frame, "valorPreco_min", 0);
        ERPFrames.setValorCampoFrame(frame, "valorPreco_max", "999.999,99");
    }

    public void cliqueGrid(JInternalFrame frame) {
        int cont = 0;
        for (SelecionarObjeto objeto : itensSelecionaveis) {
            if (objeto.isSelecionado()) {
                cont++;
            }
        }
        ERPFrames.setValorCampoFrame(frame, "quantidade", String.valueOf(cont) + "/" + String.valueOf(itens.size()));
    }

    public void geraRelatorio(JInternalFrame frame) {

        List<Pneu> pneusSelecionados = new ArrayList<>();
        for (SelecionarObjeto objeto : itensSelecionaveis) {
            if (objeto.isSelecionado()) {
                pneusSelecionados.add((Pneu) objeto.getObjeto());
            }
        }
        etiquetaPneu(pneusSelecionados);
    }

    public void etiquetaPneu(List<Pneu> pneus) {

        InputStream inputStream = getClass().getResourceAsStream("/erp/relatorios/EtiquetaPneu.jasper");

        Map parametros = new HashMap();

        // criando os dados que serão passados ao datasource
        List dados = new ArrayList();
        dados.addAll(pneus);

        // criando o datasource com os dados criados
        JRDataSource ds = new JRBeanCollectionDataSource(dados);

        try {
            // passando o datasource para o método de criação e exibição do relatório
            ERPRelatorios.openReport("Etiqueta de Pneus", inputStream, parametros,
                    ds);
        } catch (JRException exc) {
            JOptionPane.showMessageDialog(null, exc.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
