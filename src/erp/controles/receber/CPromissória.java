/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package erp.controles.receber;

import erp.controles.cadastro.CCorrentista;
import erp.controles.cadastro.CTipoTitulo;
import erp.modelos.banco.Correntista;
import erp.modelos.banco.TipoTitulo;
import erp.modelos.banco.TituloReceber;
import erp.modelos.tablemodel.TituloReceberTableModel;
import erp.util.ERPFocusGrabber;
import erp.util.ERPFrames;
import erp.util.ERPRelatorios;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Cliente
 */
public class CPromissória {

    private TituloReceber objeto;
    private List<TituloReceber> itens;
    private Correntista cliente;
    private TipoTitulo tipoTitulo;

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
        }
    }

    public void pesquisaNumeroParcela(JInternalFrame frame) {
        if (cliente != null) {
            if (tipoTitulo != null) {
                objeto = CTituloReceber.pesquisaTitulosRecebePorCliente(CTituloReceber.TIPO.ABERTOS, cliente.getIdcorrentista());
                if (objeto != null) {
                    objetoParaFrame(frame);
                }
            } else {
                objeto = CTituloReceber.pesquisaTitulosRecebePorCliente(CTituloReceber.TIPO.ABERTOS,
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
                    objeto = CTituloReceber.procuraTituloReceber(cliente.getIdcorrentista(),
                            Integer.parseInt(numero),
                            Integer.parseInt(parcela),
                            tipoTitulo.getIdtipotitulo());
                    if (!CTituloReceber.erroTituloReceber(frame, objeto)) {
                        objetoParaFrame(frame);
                    }
                } else {
                    ERPFrames.setValorCampoFrame(frame, "parcela", "");
                    ERPFrames.setFocus(frame, "parcela");
                }
            }
        } else {
            ERPFrames.setFocus(frame, "idcorrentista");
        }
    }

    private void clienteParaFrame(JInternalFrame frame) {
        ERPFrames.objetoParaFrame(frame, cliente);
        carregaGrid(frame);
        ERPFrames.setFocus(frame, "idtipotitulo");
    }

    public void objetoParaFrame(JInternalFrame frame) {
        tipoTitulo = objeto.getTipoTitulo();
        ERPFrames.setValorCampoFrame(frame, "idtipotitulo", objeto.getTipoTitulo().getIdtipotitulo());
        ERPFrames.objetoParaFrame(frame, objeto.getId());
        ERPFrames.objetoParaFrame(frame, objeto);
    }

    public void gerar(JInternalFrame frame) {
        if (cliente != null) {
            if (objeto != null) {
                promissoria(objeto);
                cancela(frame);
            } else {
                JOptionPane.showMessageDialog(frame, "Os campos cliente, número e parcela \n são obrigatórios seus preenchimentos",
                        "Erro", JOptionPane.ERROR_MESSAGE);
                ERPFrames.setFocus(frame, "parcela");
            }
        } else {
            ERPFrames.setFocus(frame, "idcorrentista");
        }
    }

    public void cancela(JInternalFrame frame) {
        if ((ERPFrames.getValorCampoFrame(frame, "numero").trim().length() > 0) && (cliente != null)) {
            ERPFrames.limparTodosCampos(frame);
            ERPFrames.setValorCampoFrame(frame, "idtipotitulo", tipoTitulo.getIdtipotitulo());
            ERPFrames.setValorCampoFrame(frame, "idcorrentista", cliente.getIdcorrentista());
            ERPFrames.setValorCampoFrame(frame, "pesquisa", cliente.getPesquisa());
            objeto = null;
        } else {
            ERPFrames.limparTodosCampos(frame);
            objeto = null;
            cliente = null;
            itens = null;
            tipoTitulo = null;
            ((TituloReceberTableModel) ((JTable) ERPFrames.getComponent(frame, "jTable1")).getModel()).setLinhas(itens);
            ERPFrames.setFocus(frame, "idcorrentista");
        }
    }

    private void carregaGrid(JInternalFrame frame) {
        itens = CTituloReceber.listaTitulosRecebePorCliente(CTituloReceber.TIPO.TODOS, cliente.getIdcorrentista(), new String[]{"tipoTitulo", "idfinanceira"});
        ((TituloReceberTableModel) ((JTable) ERPFrames.getComponent(frame, "jTable1")).getModel()).setLinhas(itens);
    }

    public void setObjeto(JInternalFrame frame, TituloReceber objeto) {
        this.objeto = CTituloReceber.procuraTituloReceber(objeto.getId().getIdcorrentista(),
                objeto.getId().getNumero(), objeto.getId().getParcela(), objeto.getId().getIdtipotitulo());
        objetoParaFrame(frame);
    }

    public void promissoria(int idcliente, int idtipotitulo, int numero, int parcela) {
        promissoria(CTituloReceber.procuraTituloReceber(idcliente, numero, parcela, idtipotitulo));
    }

    public void promissoria(TituloReceber titulo) {
        InputStream inputStream = getClass().getResourceAsStream("/erp/relatorios/Promissoria.jasper");

        Map parametros = new HashMap();

        // criando os dados que serão passados ao datasource
        List dados = new ArrayList();

        dados.add(titulo);

        // criando o datasource com os dados criados
        JRDataSource ds = new JRBeanCollectionDataSource(dados);

        try {

            // passando o datasource para o método de criação e exibição do relatório
            ERPRelatorios.openReport("Promisoria", inputStream, parametros,
                    ds);

        } catch (JRException exc) {
            JOptionPane.showMessageDialog(null, exc.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
