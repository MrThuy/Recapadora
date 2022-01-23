/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package erp.util;

import erp.controles.CFrame;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Map;
import javax.swing.*;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author usuario
 */
public class ERPRelatorios {

    public static void openReport(
            String titulo,
            InputStream inputStream,
            Map parametros,
            Connection conexao) throws JRException {

        /*
         * Cria um JasperPrint, que é a versão preenchida do relatório, usando
         * uma conexão.
         */
        JasperPrint print = JasperFillManager.fillReport(
                inputStream, parametros, conexao);

        // abre o JasperPrint em um JFrame
        viewReportFrame(titulo, print);

    }

    /**
     * Abre um relatório usando um datasource genérico.
     *
     * @param titulo Título usado na janela do relatório.
     * @param inputStream InputStream que contém o relatório.
     * @param parametros Parâmetros utilizados pelo relatório.
     * @param dataSource Datasource a ser utilizado pelo relatório.
     * @throws JRException Caso ocorra algum problema na execução do relatório
     */
    public static void openReport(
            String titulo,
            InputStream inputStream,
            Map parametros,
            JRDataSource dataSource) throws JRException {

        /*
         * Cria um JasperPrint, que é a versão preenchida do relatório, usando
         * um datasource genérico.
         */
        JasperPrint print = JasperFillManager.fillReport(
                inputStream, parametros, dataSource);

        // abre o JasperPrint em um JFrame
        viewReportFrame(titulo, print);

    }

    /**
     * Cria um JFrame para exibir o relatório representado pelo JasperPrint.
     *
     * @param titulo Título do JFrame.
     * @param print JasperPrint do relatório.
     */
    private static void viewReportFrame(String titulo, JasperPrint print) {

        /*
         * Cria um JRViewer para exibir o relatório. Um JRViewer é uma JPanel.
         */
        JRViewer viewer = new JRViewer(print);

        // cria o JFrame 
        final JFrame frameRelatorio = new JFrame(titulo);

        // adiciona o JRViewer no JFrame 
        frameRelatorio.add(viewer, BorderLayout.CENTER);

        // configura o tamanho padrão do JFrame
        frameRelatorio.setSize(500, 500);

        // maximiza o JFrame para ocupar a tela toda.
        frameRelatorio.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // configura a operação padrão quando o JFrame for fechado.
        frameRelatorio.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Action actionTecla = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                frameRelatorio.dispose();
            }
        };
        
        KeyStroke keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
        String actionName = "acao";
        InputMap inputMap = frameRelatorio.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(keyStroke, actionName);
        ActionMap actionMap = frameRelatorio.getRootPane().getActionMap();
        actionMap.put(actionName, actionTecla);

        // exibe o JFrame 
        frameRelatorio.setVisible(true);

        frameRelatorio.toFront();

        /*
         * JasperViewer jasper = new JasperViewer(print, false); JDialog viewer
         * = new JDialog(new javax.swing.JFrame(), titulo, true);
         *
         * viewer.pack(); viewer.getContentPane().add(jasper.getContentPane());
         * viewer.repaint();
         *
         * Toolkit tk = Toolkit.getDefaultToolkit(); Dimension screenSize =
         * tk.getScreenSize(); viewer.setSize(screenSize.width,
         * screenSize.height); *
         *
         * //ds = viewer.getSize(); //viewer.setLocation(((dx.width - ds.width)
         * / 2), ((dx.height - ds.height) / 2));
         *
         * viewer.setVisible(true); viewer.dispose();
         */
    }
}