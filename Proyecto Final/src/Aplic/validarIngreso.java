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

public class validarIngreso extends JDialog {

    JLabel eVehiculo, ePlaca, eHoraEntrada, eMinEntrada, eNombreCliente;
    JTextField cVehiculo, cPlaca, cHoraEntrada, cMinEntrada, cNombreCliente;
    JButton bGuardar, bCancelar;
    String informacion;
    String puesto;

    public validarIngreso(Frame e, boolean modal, String nombre, String formato, String nombreBD) {
        super(e, modal);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        this.getContentPane().add(panel);

        //JLabels
        eVehiculo = new JLabel("Vehiculo");
        ePlaca = new JLabel("Placa");
        eHoraEntrada = new JLabel("Hora entrada");
        eMinEntrada = new JLabel("Min entrada");
        eNombreCliente = new JLabel("Nombre cliente ");

        //JTextFields
        cVehiculo = new JTextField(10);
        cPlaca = new JTextField(10);
        cHoraEntrada = new JTextField(10);
        cMinEntrada = new JTextField(10);
        cNombreCliente = new JTextField(10);

        //JButtons
        bGuardar = new JButton("Guardar");
        bCancelar = new JButton("Cancelar");

        eVehiculo.setBounds(20, 20, 90, 15);
        cVehiculo.setBounds(130, 20, 130, 20);
        ePlaca.setBounds(20, 60, 90, 15);
        cPlaca.setBounds(130, 60, 130, 20);
        eHoraEntrada.setBounds(20, 100, 130, 15);
        cHoraEntrada.setBounds(130, 100, 130, 20);
        eMinEntrada.setBounds(20, 140, 130, 15);
        cMinEntrada.setBounds(130, 140, 130, 20);
        eNombreCliente.setBounds(20, 180, 90, 15);
        cNombreCliente.setBounds(130, 180, 130, 20);
        bGuardar.setBounds(40, 220, 90, 20);
        bCancelar.setBounds(150, 220, 90, 20);

        panel.add(eVehiculo);
        panel.add(cVehiculo);
        panel.add(ePlaca);
        panel.add(cPlaca);
        panel.add(eHoraEntrada);
        panel.add(cHoraEntrada);
        panel.add(eMinEntrada);
        panel.add(cMinEntrada);
        panel.add(eNombreCliente);
        panel.add(cNombreCliente);
        panel.add(bGuardar);
        panel.add(bCancelar);

        cVehiculo.setEditable(false);
        cPlaca.setEditable(false);
        cHoraEntrada.setEditable(false);
        cMinEntrada.setEditable(false);

        informacion = formato;
        if (formato.length() == 16) {
            puesto = informacion.substring(2, 3);
        } else if (formato.length() == 17) {
            puesto = informacion.substring(2, 4);
        }

        if (informacion.substring(0, 1).equals("c")) {
            cVehiculo.setText("carro");
        } else if (informacion.substring(0, 1).equals("m")) {
            cVehiculo.setText("moto");
        } else if (informacion.substring(0, 1).equals("b")) {
            cVehiculo.setText("bicicleta");
        }

        if (informacion.substring(0, 1).equals("c")) {
            if (formato.length() == 16) {
                cPlaca.setText(informacion.substring(4, 10));
            } else if (formato.length() == 17) {
                cPlaca.setText(informacion.substring(5, 11));
            }

        } else if (informacion.substring(0, 1).equals("m")) {

            cPlaca.setText(informacion.substring(5, 11));

        } else if (informacion.substring(0, 1).equals("b")) {

            cPlaca.setText(informacion.substring(5, 9));

        }

        if (informacion.substring(0, 1).equals("c")) {
            if (formato.length() == 16) {
                cHoraEntrada.setText(informacion.substring(11, 13));
            } else if (formato.length() == 17) {
                cHoraEntrada.setText(informacion.substring(12, 14));
            }

        } else if (informacion.substring(0, 1).equals("m")) {
            cHoraEntrada.setText(informacion.substring(12, 14));
        } else if (informacion.substring(0, 1).equals("b")) {
            cHoraEntrada.setText(informacion.substring(10, 12));
        }

        if (informacion.substring(0, 1).equals("c")) {
            if (formato.length() == 16) {
                cMinEntrada.setText(informacion.substring(14, 16));
            } else if (formato.length() == 17) {
                cMinEntrada.setText(informacion.substring(15, 17));
            }
        } else if (informacion.substring(0, 1).equals("m")) {
            cMinEntrada.setText(informacion.substring(15, 17));
        } else if (informacion.substring(0, 1).equals("b")) {
            cMinEntrada.setText(informacion.substring(13, 15));
        }

        bGuardar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String url = "jdbc:mysql://localhost/" + nombreBD;

                try {
                    Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                    Connection con = DriverManager.getConnection(url, "root", "");

                    PreparedStatement preparedStatement;

                    preparedStatement = con.prepareStatement("INSERT into ingreso (vehiculo,puesto,placa,horaEntrada,minutoEntrada,nombreCliente) values(?,?,?,?,?,?)");
                    preparedStatement.setString(1, cVehiculo.getText());
                    preparedStatement.setString(2, puesto);
                    preparedStatement.setString(3, cPlaca.getText());
                    preparedStatement.setString(4, cHoraEntrada.getText());
                    preparedStatement.setString(5, cMinEntrada.getText());
                    preparedStatement.setString(6, cNombreCliente.getText());
                    preparedStatement.executeUpdate();
                    preparedStatement.close();
                    con.close();
                    JOptionPane.showMessageDialog(null, "Vehiculo ingresado con exito");
                    
                } catch (Exception r) {
                    JOptionPane.showMessageDialog(null,
                            "Error al insertar el ingreso: " + r.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }

                dispose();

            }
        });

        bCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                dispose();

            }
        });

        setTitle("Validar ingreso, Usuario " + nombre);
        setSize(300, 300);
        setLocationRelativeTo(null);
        setVisible(true);

    }
}
