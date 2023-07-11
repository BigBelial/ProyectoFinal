package Aplic;

import java.awt.Frame;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class cerrarSesion extends JDialog {

    cerrarSesion(Frame e, boolean modal) {
        super(e, modal);
        JOptionPane.showMessageDialog(null, "Sesion cerrada");
    }
}
