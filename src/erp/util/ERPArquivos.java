/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package erp.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author Arthur
 */
public class ERPArquivos {

    private static String nomeArqProp = "ERP.properties";
    private static File arqProp = new File(nomeArqProp);
    private static Properties props = new Properties();

    public static void setPropriedade(String propriedade, String valor) {
        if (props.isEmpty()) {
            leArquivoPropriedades();
        }

        // adiciona a chave, com valor.  Caso já exista um elemento na coleção
        // com esta chave, o valor na coleção é  alterado para este último
        props.setProperty(propriedade, valor);
    }

    public static String getPropriedade(String propriedade) {
        if (props.isEmpty()) {
            leArquivoPropriedades();
        }

        // retorna null caso não exista um objeto na coleção em que a chave
        return props.getProperty(propriedade);
    }

    public static boolean arquivoExiste(String caminho) {
        return new File(caminho).exists();
    }

    public static void gravaArquivoPropriedades() {
        if (!arquivoExiste(nomeArqProp)) {
            try {
                arqProp.createNewFile();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
                ex.printStackTrace();
            }
        }

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(arqProp);
            //grava os dados  no arquivo
            props.store(fileOutputStream, "Configurações da Aplicação");
            fileOutputStream.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void leArquivoPropriedades() {
        if (arquivoExiste(nomeArqProp)) {
            FileInputStream fileInputStream = null;
            try {
                fileInputStream = new FileInputStream(arqProp);
                //lê os dados que estão no arquivo
                props.load(fileInputStream);
                fileInputStream.close();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
                ex.printStackTrace();
            }
        }
    }
}
