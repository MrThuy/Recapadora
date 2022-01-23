/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package erp.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author usuario
 */
public class ERPComandoExterno {

    public static String executaCmd(String cmd) {
        Process p;
        String stdIn = "", stdErr = "", s;
        try {
            //executar  ls -la no diretório corrente
            p = Runtime.getRuntime().exec(cmd);

            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));

            BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

            // lê o conteudo da saída padrão do comando e guarda em variável
            while ((s = stdInput.readLine()) != null) {
                stdIn += s + "\n";
            }

            // lê o conteudo da saída de erro do comando e guarda em variável
            while ((s = stdError.readLine()) != null) {
                stdErr += s + "\n";
            }

            // Exibir a saída padrão e de erro na tela
            // System.out.println("Saida Padrao: \n" + stdIn);
            if (!stdErr.isEmpty()) {
                System.out.println("Saida Erro: \n" + stdErr);
            }

            return stdIn;

        } catch (IOException ex) {
            return null;
        }
    }
}
