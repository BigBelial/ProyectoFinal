package Aplic;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.swing.JDialog;
import javax.swing.*;

public class valorHora extends JDialog {

    JLabel eHoraCarro, eHoraMoto, eHoraBicicleta;
    JTextField cHoraCarro, cHoraMoto, cHoraBicicleta;
    JButton bGuardar, bCancelar;

    public valorHora(Frame e, boolean modal, String nombreBD, String nombre) {
        super(e, modal);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        this.getContentPane().add(panel);

        eHoraCarro = new JLabel("Hora carro");
        eHoraMoto = new JLabel("Hora moto");
        eHoraBicicleta = new JLabel("Hora Bicicleta");

        cHoraCarro = new JTextField(10);
        cHoraMoto = new JTextField(10);
        cHoraBicicleta = new JTextField(10);

        bGuardar = new JButton("Guardar");
        bCancelar = new JButton("Cancelar");

        eHoraCarro.setBounds(20, 20, 90, 15);
        cHoraCarro.setBounds(120, 20, 120, 20);

        eHoraMoto.setBounds(20, 50, 90, 15);
        cHoraMoto.setBounds(120, 50, 120, 20);

        eHoraBicicleta.setBounds(20, 80, 90, 15);
        cHoraBicicleta.setBounds(120, 80, 120, 20);

        bGuardar.setBounds(40, 110, 90, 20);
        bCancelar.setBounds(150, 110, 90, 20);

        panel.add(eHoraCarro);
        panel.add(cHoraCarro);

        panel.add(eHoraMoto);
        panel.add(cHoraMoto);

        panel.add(eHoraBicicleta);
        panel.add(cHoraBicicleta);

        panel.add(bGuardar);
        panel.add(bCancelar);

        bGuardar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (cHoraCarro.getText().length() != 0) {
                    if (cHoraMoto.getText().length() != 0) {
                        if (cHoraBicicleta.getText().length() != 0) {
                            if (validarNum(cHoraCarro.getText())) {
                                if (validarNum(cHoraMoto.getText())) {
                                    if (validarNum(cHoraBicicleta.getText())) {

                                        String url = "jdbc:mysql://localhost/" + nombreBD;

                                        try {
                                            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                                            Connection con = DriverManager.getConnection(url, "root", "");

                                            PreparedStatement preparedStatement;

                                            preparedStatement = con.prepareStatement("INSERT into valores (tipo,valor) values(?,?)");
                                            preparedStatement.setString(1, "carro");
                                            preparedStatement.setString(2, cHoraCarro.getText());
                                            preparedStatement.executeUpdate();
                                            preparedStatement.close();

                                            preparedStatement = con.prepareStatement("INSERT into valores (tipo,valor) values(?,?)");
                                            preparedStatement.setString(1, "moto");
                                            preparedStatement.setString(2, cHoraMoto.getText());
                                            preparedStatement.executeUpdate();
                                            preparedStatement.close();

                                            preparedStatement = con.prepareStatement("INSERT into valores (tipo,valor) values(?,?)");
                                            preparedStatement.setString(1, "bicicleta");
                                            preparedStatement.setString(2, cHoraBicicleta.getText());
                                            preparedStatement.executeUpdate();
                                            preparedStatement.close();
                                            con.close();

                                            JOptionPane.showMessageDialog(null, "Se han agregado los valores a la base de datos");

                                        } catch (Exception r) {
                                            JOptionPane.showMessageDialog(null,
                                                    "Error al insertar usuario: " + r.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                                        }

                                        dispose();

                                    } else {
                                        JOptionPane.showMessageDialog(null, "El valor de la hora de la bicicleta debe ser caracter numerico");
                                    };
                                } else {
                                    JOptionPane.showMessageDialog(null, "El valor de la hora de la moto debe ser caracter numerico");
                                };
                            } else {
                                JOptionPane.showMessageDialog(null, "El valor de la hora del carro debe ser caracter numerico");
                            };
                        } else {
                            JOptionPane.showMessageDialog(null, "Escribe el valor de hora de la bicicleta");
                        };
                    } else {
                        JOptionPane.showMessageDialog(null, "Escribe el valor de hora de la moto");
                    };
                } else {
                    JOptionPane.showMessageDialog(null, "Escribe el valor de hora del carro");
                };

            }
        });

        bCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                dispose();

            }
        });

        setTitle("Valor Hora, Usuario " + nombre);
        setSize(300, 200);
        setLocationRelativeTo(null);
        setVisible(true);

    }

    public boolean validarNum(String cadena) {
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
}
