/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package erp.controles.receber;

import erp.modelos.banco.BaixaReceber;
import erp.modelos.banco.BaixaReceberId;
import erp.modelos.banco.Correntista;
import erp.modelos.banco.TituloReceber;
import erp.modelos.banco.TipoTitulo;
import erp.controles.cadastro.CCorrentista;
import erp.controles.cadastro.CTipoTitulo;
import erp.modelos.*;
import erp.modelos.tablemodel.BaixaReceberTableModel;
import erp.util.ERPFocusGrabber;
import erp.util.ERPFrames;
import erp.util.ERPRelatorios;
import java.awt.Container;
import java.io.InputStream;
import java.util.*;
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
public class CRecibo {

    private BaixaReceber objeto;
    private BaixaReceberId id;
    private TituloReceber titulo;
    private List<BaixaReceber> itens;
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
                titulo = CTituloReceber.pesquisaTitulosRecebePorCliente(CTituloReceber.TIPO.TODOS, cliente.getIdcorrentista());
                if (titulo != null) {
                    tituloParaFrame(frame);
                }
            } else {
                titulo = CTituloReceber.pesquisaTitulosRecebePorCliente(CTituloReceber.TIPO.TODOS,
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

    private void clienteParaFrame(JInternalFrame frame) {
        ERPFrames.objetoParaFrame(frame, cliente);
        ERPFrames.setFocus(frame, "idtipotitulo");
    }

    private void tituloParaFrame(JInternalFrame frame) {
        ERPFrames.objetoParaFrame(frame, titulo.getId());
        ERPFrames.objetoParaFrame(frame, titulo);
        carregaGrid(frame);
    }

    public void salva(JInternalFrame frame) {
        if (objeto != null) {
            try {

                recibo(objeto.getId().getIdcorrentista(),
                        objeto.getId().getIdtipotitulo(),
                        objeto.getId().getNumero(),
                        objeto.getId().getParcela(),
                        objeto.getId().getIdpagamento());

                cancela(frame);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(frame, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }

        } else {
            JOptionPane.showMessageDialog(frame, "Selecione um dos pagamentos primeiro, \n com um duplo clique na linha da tabela.", "Erro", JOptionPane.ERROR_MESSAGE);
            ERPFrames.setFocus(frame, "idcorrentista");
        }
    }

    public void cancela(JInternalFrame frame) {
        objeto = null;
        id = null;
        titulo = null;
        tipoTitulo = null;
        itens = null;
        ((BaixaReceberTableModel) ((JTable) ERPFrames.getComponent(frame, "jTable1")).getModel()).setLinhas(itens);
    }

    private void carregaGrid(JInternalFrame frame) {
        itens = CBaixaReceber.listaBaixasRecebePorTitulo(titulo.getId().getIdcorrentista(),
                titulo.getId().getIdtipotitulo(),
                titulo.getId().getNumero(),
                titulo.getId().getParcela(),
                new String[]{"tipoPagamento"});
        ((BaixaReceberTableModel) ((JTable) ERPFrames.getComponent(frame, "jTable1")).getModel()).setLinhas(itens);
    }

    public void setObjeto(JInternalFrame frame, BaixaReceber objeto) {
        this.objeto = objeto;
    }

    public void recibo(int idCliente, int idtipotitulo, int numero, int parcela, int idpagamento) {
        recibo(Arrays.asList(new BaixaReceber[]{
                    CBaixaReceber.procuraBaixaReceber(idCliente, idtipotitulo, numero, parcela, idpagamento,
                    new String[]{"tituloReceber", "tituloReceber.correntista"})}));
    }

    public void recibo(int idCliente, int idtipotitulo, int numero, int parcela) {
        recibo(CBaixaReceber.listaBaixasRecebePorTitulo(idCliente, idtipotitulo, numero, parcela,
                new String[]{"tituloReceber", "tituloReceber.correntista"}));
    }

    public void recibo(List<BaixaReceber> baixa) {

        InputStream inputStream = getClass().getResourceAsStream("/erp/relatorios/Recibo.jasper");

        Map parametros = new HashMap();

        // criando os dados que serão passados ao datasource
        List dados = new ArrayList();
        dados.addAll(baixa);

        // criando o datasource com os dados criados
        JRDataSource ds = new JRBeanCollectionDataSource(dados);

        try {

            // passando o datasource para o método de criação e exibição do relatório
            ERPRelatorios.openReport("Recibo", inputStream, parametros,
                    ds);

        } catch (JRException exc) {
            JOptionPane.showMessageDialog(null, exc.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
