/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package erp.util;

import java.util.ArrayList;

/**
 *
 * @author Cliente
 */
class ERPResultadoProcesso {

    public Object retornoPadrao;
    public ArrayList erros;

    public ERPResultadoProcesso() {
        erros = new ArrayList();
    }

    public Object getRetornoPadrao() {
        return retornoPadrao;
    }

    public void setRetornoPadrao(Object retornoPadrao) {
        this.retornoPadrao = retornoPadrao;
    }

    public boolean haErros() {
        return (quantiaErros() > 0);
    }

    public int quantiaErros() {
        return erros.size();
    }

    public ArrayList retornaErros() {
        return erros;
    }

    public void adicionaErro(Exception ex) {
        erros.add(ex);
    }
}
