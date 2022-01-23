/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package erp.controles;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Expression;
import erp.modelos.banco.Usuario;
import erp.util.ERPDados;

/**
 *
 * @author Arthur
 */
public class CUsuario {

    public CUsuario() {
    }

    public Usuario getUsuario(int idusuaio) {
        return (Usuario) ERPDados.consultaObjeto(Usuario.class, (new Criterion[]{Expression.eq("idUsuario", 1)}), 1);
    }
}
