/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package erp.util;

import java.awt.Component;
import javax.swing.JComponent;

/**
 *
 * @author Arthur
 */
public class ERPFocusGrabber implements Runnable {

    private JComponent component;

    public ERPFocusGrabber(JComponent component) {
        this.component = component;
    }

    public ERPFocusGrabber(Component component) {
        this.component = (JComponent) component;
    }

    @Override
    public void run() {
        component.grabFocus();
    }
}
