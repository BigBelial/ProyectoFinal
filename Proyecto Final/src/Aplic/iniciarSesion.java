package Aplic;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class iniciarSesion extends JDialog {

    String nombre;
    int nivel ,codigo;
    JLabel eCorreo, eContraseña;
    JTextField cCorreo, cContraseña;
    JButton bIngresar;
    boolean logeo = false;
    boolean falla = false;

    public iniciarSesion(Frame e, boolean modal, String nombreBD) {
        super(e, modal);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        this.getContentPane().add(panel);

        //jlabel
        eCorreo = new JLabel("Correo");
        eContraseña = new JLabel("Contrasenia");
        //jtextfield
        cCorreo = new JTextField(10);
        cContraseña = new JTextField(10);
        //boton
        bIngresar = new JButton("Aceptar");
        //

        eCorreo.setBounds(20, 20, 90, 15);
        cCorreo.setBounds(120, 20, 120, 20);
        eContraseña.setBounds(20, 50, 90, 15);
        cContraseña.setBounds(120, 50, 120, 20);
        bIngresar.setBounds(100, 90, 90, 20);

        panel.add(eCorreo);
        panel.add(cCorreo);
        panel.add(eContraseña);
        panel.add(cContraseña);
        panel.add(bIngresar);

        bIngresar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (cCorreo.getText().length() != 0) {
                    if (cContraseña.getText().length() != 0) {
                        if (emailVal(cCorreo.getText())) {

                            String url = "jdbc:mysql://localhost/" + nombreBD;

                            try {
                                Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                                Connection con = DriverManager.getConnection(url, "root", "");

                                PreparedStatement preparedStatement;

                                preparedStatement = con.prepareStatement("SELECT nombre, nivel, codigo FROM usuarios WHERE correo = ?  AND contraseña = ?");
                                preparedStatement.setString(1, cCorreo.getText());
                                preparedStatement.setString(2, cContraseña.getText());

                                ResultSet resultSet = preparedStatement.executeQuery();
                                falla = true;
                                logeo = true;
                                if (resultSet.next()) {
                                    nombre = (resultSet.getString("nombre"));
                                    nivel = Integer.parseInt(resultSet.getString("nivel"));
                                    codigo = Integer.parseInt(resultSet.getString("codigo"));
                                    JOptionPane.showMessageDialog(null, "Sesion iniciada");
                                    dispose();
                                } else {
                                    falla = false;

                                    JOptionPane.showMessageDialog(null, "Las credenciales no existen en la base de datos");
                                    
                                }

                                preparedStatement.close();
                                con.close();

                            } catch (Exception r) {
                                JOptionPane.showMessageDialog(null,
                                        "Error al iniciar sesion: " + r.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            }

                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Campo de contrasenia vacio");
                    };
                } else {
                    JOptionPane.showMessageDialog(null, "Campo de correo vacio");
                };

            }
        });

        setTitle("Logeo");
        setSize(300, 170);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public boolean emailVal(String cadena) {
        boolean validarEmail = false;

        if (cadena.matches(".*[ ].*")) {
            JOptionPane.showMessageDialog(null, "Tu correo no puede tener espacios");
            validarEmail = false;
        } else //    "[-\\w\\.]+@\\w+\\.\\w+" indica que debe haber texto antes del @ , texto despues del @, un punto y texto despues del punto.
        if (cadena.matches("[-\\w\\.]+@\\w+\\.\\w+")) {
            validarEmail = true;
        } else {
            JOptionPane.showMessageDialog(null, "Revisa que tu correo este correcto");
        }
        return validarEmail;

    }
}
