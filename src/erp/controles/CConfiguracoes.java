/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package erp.controles;

import erp.util.ERPArquivos;
import erp.util.ERPFrames;
import java.io.File;
import java.io.IOException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JDialog;
import javax.swing.UIManager;

/**
 *
 * @author Arthur
 */
public class CConfiguracoes {

    public static ComboBoxModel carregaLookAndFeel() {
        UIManager.LookAndFeelInfo[] instalados = UIManager.getInstalledLookAndFeels();
        
        ComboBoxModel model;

        Vector vector = new Vector();
        int atual = 0;

        for (int i = 0; i < instalados.length; i++) {
            vector.add(i + " - " + instalados[i].getName());
            if(instalados[i].getName().equals(UIManager.getLookAndFeel().getName())) 
                atual = i;
        }
        model = new DefaultComboBoxModel(vector);
        model.setSelectedItem(vector.get(atual));
        return model;
    }

    public void salvaConfiguracoes(JDialog frame) {
        int valor = Integer.parseInt(ERPFrames.getValorCampoFrame(frame, "lookAndFeel"));
        carregaConfiguracoes();
        //SwingUtilities.updateComponentTreeUI(CFrame.getFrame());
        ERPArquivos.setPropriedade("configuracao.LookAndFeel", UIManager.getInstalledLookAndFeels()[valor].getClassName());
        ERPArquivos.setPropriedade("banco.ip", ERPFrames.getValorCampoFrame(frame, "ip"));
        ERPArquivos.setPropriedade("banco.porta", ERPFrames.getValorCampoFrame(frame, "porta"));
        ERPArquivos.setPropriedade("banco.base", ERPFrames.getValorCampoFrame(frame, "base"));
        ERPArquivos.setPropriedade("bkp.dir", ERPFrames.getValorCampoFrame(frame, "diretorio"));
        ERPArquivos.gravaArquivoPropriedades();

        //JOptionPane.showMessageDialog(frame, "Para aplicar a configurção é necessário reiniciar o sistema");
    }

    public static void carregaConfiguracoes() {
        
        String lookAndFell = ERPArquivos.getPropriedade("configuracao.LookAndFeel");
        
        try {
            UIManager.setLookAndFeel((lookAndFell==null||lookAndFell.equals(""))?"com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel":lookAndFell);
        } catch (Exception err) {
            System.out.print(err.getMessage());
        }
    }

    public static String carregaIP_Banco() {
        String ip = ERPArquivos.getPropriedade("banco.ip");
        return ( ip == null || ip.toString().equals("") ) ? "localhost" : ip;
    }

    public static String carregaPorta_Banco() {
        String porta = ERPArquivos.getPropriedade("banco.porta");
        return ( porta == null ||porta.equals("") ) ? "3306" : porta;
    }
    
    public static String carregaBase_Banco() {
        String base = ERPArquivos.getPropriedade("banco.base");
        return ( base == null ||base.equals("") ) ? "uniao" : base;
    }
    
    public static String carregaDiretorio_Backup() {
        String dir = ERPArquivos.getPropriedade("bkp.dir");
        String retorno = null;
        try {
            retorno = ( dir == null ||dir.equals("") ) ? (new File(".")).getCanonicalPath() : dir;
        } catch (IOException ex) {
            Logger.getLogger(CConfiguracoes.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }    
}
