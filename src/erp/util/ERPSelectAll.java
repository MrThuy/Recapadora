/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package erp.util;

import javax.swing.JFormattedTextField;

/**
 *
 * @author usuario
 */
public class ERPSelectAll implements Runnable{
    
    private JFormattedTextField component;

    public ERPSelectAll(JFormattedTextField jFormattedTextField) {
        this.component = jFormattedTextField;
    }

    @Override
    public void run() {
       component.selectAll();
    }
    
}
