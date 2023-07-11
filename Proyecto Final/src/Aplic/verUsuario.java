package Aplic;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class verUsuario extends JDialog {

    JLabel eUsuarios, eCodigo;
    JTextField cCodigo;
    JButton bBuscar, bEliminarSeleccionados, bCancelar;
    JComboBox combo;
    JTable tabla;
    DefaultTableModel modelo;
    int cantidad;

    public verUsuario(Frame e, boolean modal, String nombreBD, String nombre) {
        super(e, modal);
        
        JPanel panel = new JPanel();
        panel.setLayout(null);
        this.getContentPane().add(panel);
        
        //labels
        eUsuarios = new JLabel("Usuarios");
        eCodigo = new JLabel("Codigo");

        //textfleid
        cCodigo = new JTextField(15);
        
        //botones
        bBuscar = new JButton("Buscar");
        bEliminarSeleccionados = new JButton("Eliminar seleccionados");
        bCancelar = new JButton("Cancelar");
        
        //combo
        String tipo[] = {"Todos", "Codigo", "Nivel 1", "Nivel 2", "Nivel 3"};
        combo = new JComboBox(tipo);
        //tabla
        modelo = new DefaultTableModel() {

            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 5) { // Índice de la columna "Eliminar"
                    return Boolean.class; // Utilizar JCheckBox
                }
                return super.getColumnClass(columnIndex);
            }
        };

        modelo.addColumn("Codigo");
        modelo.addColumn("Nivel");
        modelo.addColumn("Nombre");
        modelo.addColumn("Correo ");
        modelo.addColumn("Contrasenia");
        modelo.addColumn("Eliminar");

        tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        
        
        //posiciones
        eUsuarios.setBounds(20, 20, 90, 15);
        combo.setBounds(90, 20, 90, 20);
        eCodigo.setBounds(220, 20, 90, 15);
        cCodigo.setBounds(280, 20, 150, 20);
        bBuscar.setBounds(450, 20, 90, 20);
        scroll.setBounds(20, 120, 750, 200);
        bEliminarSeleccionados.setBounds(100, 350, 190, 20);
        bCancelar.setBounds(320, 350, 90, 20);

        //aniadirlos
        panel.add(eUsuarios);
        panel.add(combo);
        panel.add(eCodigo);
        panel.add(cCodigo);
        panel.add(bBuscar);
        panel.add(scroll);
        panel.add(bEliminarSeleccionados);
        panel.add(bCancelar);

        bBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
//para limpiar la tabla cada vez que cambie de tipo de busqueda
        modelo.setRowCount(0);
                if (combo.getSelectedItem() == "Todos") {
                    Object fila[] = new Object[5];
                    String url = "jdbc:mysql://localhost/" + nombreBD;

                    //tabla                                   
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                        Connection con = DriverManager.getConnection(url, "root", "");

                        PreparedStatement preparedStatement;

                        preparedStatement = con.prepareStatement("SELECT codigo, nivel ,nombre, correo , contraseña FROM usuarios");

                        ResultSet resultSet = preparedStatement.executeQuery();

                        while (resultSet.next()) {

                            fila[0] = (resultSet.getString("codigo"));
                            fila[1] = (resultSet.getString("nivel"));
                            fila[2] = (resultSet.getString("nombre"));
                            fila[3] = (resultSet.getString("correo"));
                            fila[4] = (resultSet.getString("contraseña"));
                            modelo.addRow(fila);

                        }

                        preparedStatement.close();
                        con.close();

                    } catch (Exception r) {
                        JOptionPane.showMessageDialog(null,
                                "Error al iniciar sesion: " + r.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }

                if (combo.getSelectedItem() == "Codigo") {
                    if (cCodigo.getText().length() != 0) {
                        Object fila[] = new Object[5];
                        String url = "jdbc:mysql://localhost/" + nombreBD;

                        try {
                            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                            Connection con = DriverManager.getConnection(url, "root", "");

                            PreparedStatement preparedStatement;

                            preparedStatement = con.prepareStatement("SELECT codigo, nivel ,nombre, correo , contraseña FROM usuarios WHERE codigo = ?");
                            preparedStatement.setString(1, cCodigo.getText());

                            ResultSet resultSet = preparedStatement.executeQuery();

                            if (resultSet.next()) {

                                fila[0] = (resultSet.getString("codigo"));
                                fila[1] = (resultSet.getString("nivel"));
                                fila[2] = (resultSet.getString("nombre"));
                                fila[3] = (resultSet.getString("correo"));
                                fila[4] = (resultSet.getString("contraseña"));
                                modelo.addRow(fila);
                                cCodigo.setText("");

                            } else {
                                JOptionPane.showMessageDialog(null, "Las credenciales no existen en la base de datos");

                            }

                            preparedStatement.close();
                            con.close();

                        } catch (Exception r) {
                            JOptionPane.showMessageDialog(null,
                                    "Error al iniciar sesion: " + r.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }

                    } else {
                        JOptionPane.showMessageDialog(null, "Escribe el codigo");
                    };
                }

                if (combo.getSelectedItem() == "Nivel 1") {
                    Object fila[] = new Object[5];
                    String url = "jdbc:mysql://localhost/" + nombreBD;
                    //tabla                                   
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                        Connection con = DriverManager.getConnection(url, "root", "");
                        PreparedStatement preparedStatement;
                        preparedStatement = con.prepareStatement("SELECT codigo, nivel ,nombre, correo , contraseña FROM usuarios WHERE nivel = 1");
                        ResultSet resultSet = preparedStatement.executeQuery();

                        while (resultSet.next()) {

                            fila[0] = (resultSet.getString("codigo"));
                            fila[1] = (resultSet.getString("nivel"));
                            fila[2] = (resultSet.getString("nombre"));
                            fila[3] = (resultSet.getString("correo"));
                            fila[4] = (resultSet.getString("contraseña"));
                            modelo.addRow(fila);

                        }

                        preparedStatement.close();
                        con.close();

                    } catch (Exception r) {
                        JOptionPane.showMessageDialog(null,
                                "Error al iniciar sesion: " + r.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }

                if (combo.getSelectedItem() == "Nivel 2") {
                    Object fila[] = new Object[5];
                    String url = "jdbc:mysql://localhost/" + nombreBD;

                    //tabla                                   
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                        Connection con = DriverManager.getConnection(url, "root", "");
                        PreparedStatement preparedStatement;
                        preparedStatement = con.prepareStatement("SELECT codigo, nivel ,nombre, correo , contraseña FROM usuarios WHERE nivel = 2");
                        ResultSet resultSet = preparedStatement.executeQuery();

                        while (resultSet.next()) {

                            fila[0] = (resultSet.getString("codigo"));
                            fila[1] = (resultSet.getString("nivel"));
                            fila[2] = (resultSet.getString("nombre"));
                            fila[3] = (resultSet.getString("correo"));
                            fila[4] = (resultSet.getString("contraseña"));
                            modelo.addRow(fila);

                        }
                        preparedStatement.close();
                        con.close();

                    } catch (Exception r) {
                        JOptionPane.showMessageDialog(null,
                                "Error al iniciar sesion: " + r.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                if (combo.getSelectedItem() == "Nivel 3") {
                    Object fila[] = new Object[5];
                    String url = "jdbc:mysql://localhost/" + nombreBD;

                    //tabla                                   
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                        Connection con = DriverManager.getConnection(url, "root", "");

                        PreparedStatement preparedStatement;

                        preparedStatement = con.prepareStatement("SELECT codigo, nivel ,nombre, correo , contraseña FROM usuarios WHERE nivel = 3");

                        ResultSet resultSet = preparedStatement.executeQuery();

                        while (resultSet.next()) {

                            fila[0] = (resultSet.getString("codigo"));
                            fila[1] = (resultSet.getString("nivel"));
                            fila[2] = (resultSet.getString("nombre"));
                            fila[3] = (resultSet.getString("correo"));
                            fila[4] = (resultSet.getString("contraseña"));
                            modelo.addRow(fila);
                        }
                        preparedStatement.close();
                        con.close();

                    } catch (Exception r) {
                        JOptionPane.showMessageDialog(null,
                                "Error al iniciar sesion: " + r.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }

            }
        });

        bEliminarSeleccionados.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                
              for(int i = 0;i<modelo.getRowCount();i++){
                if(modelo.getValueAt(i,5)== null){  
        
               }else{
                         String url = "jdbc:mysql://localhost/"+nombreBD;
                         try {
                                        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                                        Connection con = DriverManager.getConnection(url, "root", "");

                                        PreparedStatement preparedStatement;

                                        preparedStatement = con.prepareStatement("DELETE FROM usuarios where codigo = ?");
                                        preparedStatement.setString(1,modelo.getValueAt(i,0).toString());
                                        
                                        preparedStatement.executeUpdate();
                                        preparedStatement.close();
                                        con.close();
                                        JOptionPane.showMessageDialog(null, "Usuario eliminado de la base de datos");
                                        modelo.removeRow(i);
                                    }catch (Exception r) {
                                        JOptionPane.showMessageDialog(null,
                                                "Error al borrar ingreso : " + r.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                                    }                          
            
        }
  
    } 
                
            
            }
        });

        bCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                dispose();

            }
        });

        setTitle("Ver/Eliminar Usuarios, Usuario " + nombre);
        setSize(800, 500);
        setLocationRelativeTo(null);
        setVisible(true);

    }
}
