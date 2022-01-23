/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package erp.controles.cadastro;

import erp.modelos.banco.Correntista;
import erp.util.ERPFocusGrabber;
import erp.util.ERPFrames;
import erp.util.ERPRelatorios;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Cliente
 */
public class CEnvelope {

    private Correntista objeto;

    public void pesquisaDestinatario(JInternalFrame frame) {        
        objeto = CCorrentista.pesquisaCorrentistas(CCorrentista.TIPO.ATIVOS);
        if (objeto != null) {
            objetoParaFrame(frame);
        } 
//        else {
//            ERPFrames.setFocus(frame, "idcorrentista");
//        }
    }

    public void criticaDestinatario(JInternalFrame frame) {
        String idCliente = ERPFrames.getValorCampoFrame(frame, "idcorrentista");
        if (idCliente.trim().length() > 0) {
            objeto = CCorrentista.procuraCorrentista(Integer.parseInt(idCliente));
            if (!CCorrentista.erroCorrentista(frame, objeto)) {
                objetoParaFrame(frame);
            } else {
                ERPFrames.setValorCampoFrame(frame, "idCorrentista", "");
                SwingUtilities.invokeLater(new ERPFocusGrabber(ERPFrames.getComponent(frame, "idcorrentista")));
            }
        }
    }

    public void objetoParaFrame(JInternalFrame frame) {
        ERPFrames.objetoParaFrame(frame, objeto);
    }

    public void gerar(JInternalFrame frame) {
        if (objeto != null) {
            envelope(objeto);
            cancela(frame);
        } else {
            JOptionPane.showMessageDialog(frame, "Selecione um destinatário", "Erro", JOptionPane.ERROR_MESSAGE);
            ERPFrames.setFocus(frame, "parcela");
        }
    }

    public void cancela(JInternalFrame frame) {
            ERPFrames.limparTodosCampos(frame);
            objeto = null;
        }
    

    public void envelope(int iddestinatario) {
        envelope(CCorrentista.procuraCorrentista(iddestinatario));
    }

    public void envelope(Correntista destinatario) {
        InputStream inputStream = getClass().getResourceAsStream("/erp/relatorios/Envelope.jasper");

        Map parametros = new HashMap();

        // criando os dados que serão passados ao datasource
        List dados = new ArrayList();

        dados.add(destinatario);

        // criando o datasource com os dados criados
        JRDataSource ds = new JRBeanCollectionDataSource(dados);

        
        ImageIcon gto = new ImageIcon(getClass().getResource("/recursos/Pneu94.jpg"));
        parametros.put("logo", gto.getImage());
        
        try {

            // passando o datasource para o método de criação e exibição do relatório
            ERPRelatorios.openReport("Envelope", inputStream, parametros,
                    ds);

        } catch (JRException exc) {
            JOptionPane.showMessageDialog(null, exc.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
