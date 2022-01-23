/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package erp.modelos;

/**
 *
 * @author usuario
 */
public class SelecionarObjeto {

    private boolean selecionado;
    private Object objeto;

    public SelecionarObjeto(boolean selecionado, Object objeto) {
        this.selecionado = selecionado;
        this.objeto = objeto;
    }

    public SelecionarObjeto() {
    }

    public boolean isSelecionado() {
        return selecionado;
    }

    public Object getObjeto() {
        return objeto;
    }

    public void setSelecionado(boolean selecionado) {
        this.selecionado = selecionado;
    }

    public void setObjeto(Object objeto) {
        this.objeto = objeto;
    }
    
}
