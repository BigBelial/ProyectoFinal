package Aplic;

import java.awt.Container;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class eliminarBase extends JDialog {

    JButton eliminar, cancelar;
    String nombre;

    public eliminarBase(Frame e, boolean modal, String nombreBD) {
        super(e, modal);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        this.getContentPane().add(panel);

        nombre = nombreBD;
        eliminar = new JButton("Eliminar");
        cancelar = new JButton("Cancelar");

        eliminar.setBounds(30, 60, 90, 20);
        cancelar.setBounds(160, 60, 90, 20);

        panel.add(eliminar);
        panel.add(cancelar);

        eliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String url = "jdbc:mysql://localhost/" + nombreBD;

                try {
                    Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                    Connection con = DriverManager.getConnection(url, "root", "");

                    PreparedStatement preparedStatement;

                    preparedStatement = con.prepareStatement("DROP DATABASE " + nombre);
                    preparedStatement.executeUpdate();
                    preparedStatement.close();
                    con.close();
                    JOptionPane.showMessageDialog(null, "La base de datos ha sido eliminada");

                } catch (Exception r) {
                    JOptionPane.showMessageDialog(null,
                            "Error al eliminar base de datos: " + r.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }

                dispose();

            }
        });

        cancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                dispose();

            }
        });

        setTitle("Eliminar base de datos: " + nombre);
        setSize(300, 200);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
