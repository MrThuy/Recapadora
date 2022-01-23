
import erp.controles.CConfiguracoes;
import erp.controles.CFrame;
import erp.util.HibernateUtil;
import erp.visoes.VFrame;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import javax.swing.*;
import org.hibernate.Session;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
/**
 *
 * @author Arthur
 */
public class SplashScreen extends JWindow {

    AbsoluteLayout layout;
    AbsoluteConstraints absContainer, absImage, absBarra, absTitulo, absTexto;
    ImageIcon imagem;
    JLabel label, titulo;
    static JLabel carregando;
    JProgressBar barra;
    Container container;

    public SplashScreen() {
        //500x375
        
        int lagura = 500;
        int tamanho = 375;

        layout = new AbsoluteLayout();
        absContainer = new AbsoluteConstraints(0, 0);
        absImage = new AbsoluteConstraints(10, 55);
        absBarra = new AbsoluteConstraints(245, 345);
        absTitulo = new AbsoluteConstraints(265, 100);
        absTexto = new AbsoluteConstraints(250, 330);
        container = new Container();
        container.setPreferredSize(new Dimension(lagura, tamanho));
        label = new JLabel();
        imagem = new ImageIcon(getClass().getResource("/recursos/Splash.png"));        
        label.setIcon(imagem);
        titulo = new JLabel("UNIÃO ERP");
        titulo.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 36));
        carregando = new JLabel("Carregando configurações...");
        barra = new JProgressBar();
        barra.setIndeterminate(true);
        barra.setPreferredSize(new Dimension(258, 28));
        this.getContentPane().setLayout(layout);
        this.getContentPane().add(container, absContainer);
        this.getContentPane().add(label, absImage);
        this.getContentPane().add(barra, absBarra);
        this.getContentPane().add(titulo, absTitulo);
        this.getContentPane().add(carregando, absTexto);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public static void alteraLabelCarregando(String texto) {
        carregando.setText(texto);
    }

    public static void main(String args[]) {

        CConfiguracoes.carregaConfiguracoes();
        SplashScreen splashScreen = new SplashScreen();
        alteraLabelCarregando("Conecectando ao banco de dados....");
        Session session = HibernateUtil.openSession();
        int i;
        alteraLabelCarregando("Testando conexão com o banco de dados....");
        while (!HibernateUtil.isConnected()) {
            i = JOptionPane.showConfirmDialog(null, "Não foi possível conectar ao banco de dados, \n"+
                    "deseja alterar as configurações e reconectar?", "Erro", JOptionPane.YES_NO_OPTION);
            if (i == JOptionPane.NO_OPTION) {
                try {
                    System.exit(0);
                } catch (Throwable ex) {
                    //
                }
            }
            CFrame.abreVisao(CFrame.VISOES.CONFIGURACOES, null);
            alteraLabelCarregando("Conecectando ao banco de dados....");
            HibernateUtil.abreConexao();
            session = HibernateUtil.openSession();
            alteraLabelCarregando("Testando conexão com o banco de dados....");
        }
        alteraLabelCarregando("Banco de dados OK! Abrindo janela principal...");
        VFrame vframe = new VFrame();
        vframe.setLocationRelativeTo(null);
        vframe.setExtendedState(vframe.MAXIMIZED_BOTH);
        splashScreen.dispose();
        vframe.setVisible(true);        
        
//        java.awt.EventQueue.invokeLater(new Runnable() {
//
//            public void run() {
//
//                CConfiguracoes.carregaConfiguracoes();
//
//                Session session = HibernateUtil.openSession();
//
//                int i;
//
//                while (!HibernateUtil.isConnected()) {
//                    i = JOptionPane.showConfirmDialog(null, "Não foi possível conectar ao banco de dados, deseja reconectar?", "Erro", JOptionPane.YES_NO_OPTION);
//                    if (i == JOptionPane.NO_OPTION) {
//                        try {
//                            System.exit(0);
//                        } catch (Throwable ex) {
//                            //
//                        }
//                    }
//                    CFrame.abreVisão(CFrame.VISOES.CONFIGURACOES, null);
//                    HibernateUtil.abreConexao();
//                    session = HibernateUtil.openSession();
//                }
//
//                VFrame vframe = new VFrame();
//                vframe.setLocationRelativeTo(null);
//                vframe.setExtendedState(vframe.MAXIMIZED_BOTH);
//                
//                vframe.setVisible(true);
//            }
//        });
    }
}
