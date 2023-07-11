package Aplic;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class modificarUsuario extends JDialog {
    
    JLabel eCodigo, eNombre, eCorreo, eContraseña, eNivel;
    JTextField cCodigo, cNombre, cCorreo, cContraseña;
    JComboBox nivel;
    JButton bBuscar, bModificar, bCancelar;
    
    public modificarUsuario(Frame e, boolean modal, String nombreBD, String nombre) {
        super(e, modal);
        
        JPanel panel = new JPanel();
        panel.setLayout(null);
        this.getContentPane().add(panel);

        //JLabels
        eCodigo = new JLabel("Codigo");
        eNombre = new JLabel("Nombre");
        eCorreo = new JLabel("Correo");
        eContraseña = new JLabel("Contraseña");
        eNivel = new JLabel("Nivel");

        //JTextFields
        cCodigo = new JTextField(10);
        cNombre = new JTextField(10);
        cCorreo = new JTextField(10);
        cContraseña = new JTextField(10);

        //Combobox
        String niveles[] = {"1", "2", "3"};
        nivel = new JComboBox(niveles);

        //JButton
        bBuscar = new JButton("Buscar");
        bCancelar = new JButton("Cancelar");
        bModificar = new JButton("Modificar");

        //posiciones
        //codigo
        eCodigo.setBounds(30, 20, 60, 15);
        cCodigo.setBounds(110, 20, 30, 20);
        bBuscar.setBounds(160, 20, 80, 20);
        
        panel.add(eCodigo);
        panel.add(cCodigo);
        panel.add(bBuscar);

        //nombre
        eNombre.setBounds(30, 50, 60, 15);
        cNombre.setBounds(110, 50, 150, 20);
        
        panel.add(eNombre);
        panel.add(cNombre);

        //Correo
        eCorreo.setBounds(30, 80, 60, 15);
        cCorreo.setBounds(110, 80, 150, 20);
        
        panel.add(eCorreo);
        panel.add(cCorreo);

        //contraseña
        eContraseña.setBounds(30, 110, 70, 15);
        cContraseña.setBounds(110, 110, 150, 20);
        
        panel.add(eContraseña);
        panel.add(cContraseña);

        //ComboBox
        eNivel.setBounds(30, 140, 60, 15);
        nivel.setBounds(110, 140, 60, 20);
        
        panel.add(eNivel);
        panel.add(nivel);

        //Botones 
        bCancelar.setBounds(160, 190, 90, 20);
        bModificar.setBounds(30, 190, 100, 20);
        
        panel.add(bCancelar);
        panel.add(bModificar);
        
        bBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                if (cCodigo.getText().length() != 0) {
                    
                    String url = "jdbc:mysql://localhost/" + nombreBD;
                    
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                        Connection con = DriverManager.getConnection(url, "root", "");
                        
                        PreparedStatement preparedStatement;
                        
                        preparedStatement = con.prepareStatement("SELECT nombre, correo, contraseña, nivel FROM usuarios WHERE codigo = ?");
                        preparedStatement.setString(1, cCodigo.getText());
                        ResultSet resultSet = preparedStatement.executeQuery();
                        
                        if (resultSet.next()) {
                            cNombre.setText((resultSet.getString("nombre")));
                            cCorreo.setText((resultSet.getString("correo")));
                            cContraseña.setText((resultSet.getString("contraseña")));
                            nivel.setSelectedItem((resultSet.getString("nivel")));
                            
                        } else {
                            JOptionPane.showMessageDialog(null, "Las credenciales no existen en la base de datos");
                        }
                        
                        preparedStatement.close();
                        con.close();
                        
                    } catch (Exception r) {
                        JOptionPane.showMessageDialog(null,
                                "Error al buscar usuario: " + r.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    
                } else {
                    JOptionPane.showMessageDialog(null, "Escribe el codigo");
                }
                
            }
        });
        
        bModificar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                if (cNombre.getText().length() != 0) {
                    if (cCorreo.getText().length() != 0) {
                        if (cContraseña.getText().length() != 0) {
                            if (nomVal(cNombre.getText())) {
                                if (emailVal(cCorreo.getText())) {
                                    
                                    String url = "jdbc:mysql://localhost/" + nombreBD;
                                    
                                    try {
                                        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                                        Connection con = DriverManager.getConnection(url, "root", "");
                                        
                                        PreparedStatement preparedStatement;
                                        
                                        preparedStatement = con.prepareStatement("UPDATE usuarios SET nombre = ?, correo = ?, contraseña = ?, nivel = ?  WHERE codigo =?;");
                                        preparedStatement.setString(1, cNombre.getText());
                                        preparedStatement.setString(2, cCorreo.getText());
                                        preparedStatement.setString(3, cContraseña.getText());
                                        preparedStatement.setString(4, nivel.getSelectedItem().toString());
                                        preparedStatement.setString(5, cCodigo.getText());
                                        preparedStatement.executeUpdate();
                                        preparedStatement.close();
                                        con.close();
                                        
                                        JOptionPane.showMessageDialog(null, "Se ha modificado el usuario con exito");
                                        
                                    } catch (Exception r) {
                                        JOptionPane.showMessageDialog(null,
                                                "Error al modificar usuario: " + r.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
        
        setTitle("Modificar Usuario, Usuario " + nombre);
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
