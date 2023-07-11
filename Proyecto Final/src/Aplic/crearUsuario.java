package Aplic;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class crearUsuario extends JDialog {
    JLabel eNombre,eCorreo,eContraseña,eNivel;
    JTextField cNombre,cCorreo,Ccontraseña;
    JComboBox comboNivel;
    JButton bCrear,bCancelar;
    boolean logeo= false;

    public crearUsuario(Frame e, boolean modal, String nombreBD, String nombre) {
        super(e, modal);
        
        JPanel panel = new JPanel();
        panel.setLayout(null);
        this.getContentPane().add(panel);
        
        //JLabel
        eNombre = new JLabel("Nombre");
        eCorreo = new JLabel("Correo");
        eContraseña = new JLabel("Contraseña");
        eNivel = new JLabel("Nivel");
        //JTextField
        cNombre = new JTextField(10);
        cCorreo = new JTextField(10);
        Ccontraseña = new JTextField(10);
        //Combobox
        String niveles[] = {"1", "2", "3"};
        comboNivel = new JComboBox(niveles);
        //botones
        bCrear = new JButton("Crear");
        bCancelar = new JButton("Cancelar");
        //
        
        eNombre.setBounds(30, 30, 60, 15);
        cNombre.setBounds(110, 30, 150, 20);
        eCorreo.setBounds(30, 80, 60, 15);
        cCorreo.setBounds(110, 80, 150, 20);
        eContraseña.setBounds(30, 130, 70, 15);
        Ccontraseña.setBounds(110, 130, 150, 20);
        eNivel.setBounds(30, 180, 60, 15);
        comboNivel.setBounds(110, 180, 60, 20);
        bCrear.setBounds(50, 220, 80,25);
        bCancelar.setBounds(150, 220, 90, 25);
        
        panel.add(eNombre);
        panel.add(eCorreo);
        panel.add(eContraseña);
        panel.add(eNivel);
        panel.add(cNombre);
        panel.add(cCorreo);
        panel.add(Ccontraseña);
        panel.add(comboNivel);
        panel.add(bCrear);
        panel.add(bCancelar);

        bCrear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (cNombre.getText().length() != 0) {
                    if (cCorreo.getText().length() != 0) {
                        if (Ccontraseña.getText().length() != 0) {
                            if (nomVal(cNombre.getText())) {
                                if (emailVal(cCorreo.getText())) {

                                    String url = "jdbc:mysql://localhost/"+nombreBD;

                                    try {
                                        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                                        Connection con = DriverManager.getConnection(url, "root", "");

                                        PreparedStatement preparedStatement;

                                        preparedStatement = con.prepareStatement("INSERT into usuarios (nombre,correo,contraseña,nivel) values(?,?,?,?)");
                                        preparedStatement.setString(1, cNombre.getText());
                                        preparedStatement.setString(2, cCorreo.getText());
                                        preparedStatement.setString(3, Ccontraseña.getText());
                                        preparedStatement.setString(4, comboNivel.getSelectedItem().toString());
                                        preparedStatement.executeUpdate();
                                        preparedStatement.close();
                                        con.close();
                                        
                                        JOptionPane.showMessageDialog(null,"Se ha agregado el usuario a la base de datos");
                                       
                                    }catch (Exception r) {
                                        JOptionPane.showMessageDialog(null,
                                                "Error al insertar usuario: " + r.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                                    }       

                                    dispose();
                                }
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Campo de contrasenia vacio");
                        };
                    } else {
                        JOptionPane.showMessageDialog(null, "Campo de correo vacio");
                    };
                } else {
                    JOptionPane.showMessageDialog(null, "Campo de nombre vacio");
                };

            }
        });

        bCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                dispose();

            }
        });

        setTitle("Crear Usuario, Usuario " + nombre);
        setSize(300, 300);
        setLocationRelativeTo(null);
        setVisible(true);

    }

    //validar que el nombre no tenga numeros
    public boolean nomVal(String cadena) {
        if (cadena.matches(".*[0-9].*")) {
            JOptionPane.showMessageDialog(null, "El nombre no puede tener caracteres numericos");
            return false;
        } else {
            return true;
        }
    }
    //validar que el mail tenga al menos un x@x.x

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
