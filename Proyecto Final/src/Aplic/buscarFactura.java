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

public class buscarFactura extends JDialog {

    JLabel eNoFactura, eVehiculo, eNombreCliente, ePlaca, eHoraEntrada,
            eMinEntrada, eHoraSalida, eMinSalida, eValorHora, eHoras, eTotalPagar;
    JTextField cNoFactura, cVehiculo, cNombreCliente, cPlaca, cHoraEntrada, cMinEntrada, cHoraSalida, cMinSalida, cValorHora, cHoras, cTotalPagar;
    JButton bBuscar, bCancelar;

    public buscarFactura(Frame e, boolean modal, String nombreBD, String nombre) {
        super(e, modal);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        this.getContentPane().add(panel);

        //JLabels
        eNoFactura = new JLabel("No. Factura");
        eVehiculo = new JLabel("Vehiculo");
        eNombreCliente = new JLabel("Nombre Cliente");
        ePlaca = new JLabel("Placa");
        eHoraEntrada = new JLabel("Hora entrada");
        eMinEntrada = new JLabel("Minuto entrada");
        eHoraSalida = new JLabel("Hora salida");
        eMinSalida = new JLabel("Min salida");
        eValorHora = new JLabel("Valor hora");
        eHoras = new JLabel("Horas");
        eTotalPagar = new JLabel("Total a pagar");

        //JTextFields
        cNoFactura = new JTextField(10);
        cVehiculo = new JTextField(10);
        cNombreCliente = new JTextField(10);
        cPlaca = new JTextField(10);
        cHoraEntrada = new JTextField(10);
        cMinEntrada = new JTextField(10);
        cHoraSalida = new JTextField(10);
        cMinSalida = new JTextField(10);
        cValorHora = new JTextField(10);
        cHoras = new JTextField(10);
        cTotalPagar = new JTextField(10);
        
        //ponerlos para que las cajas no sean editables
        cVehiculo.setEditable(false);
        cNombreCliente.setEditable(false);
        cPlaca.setEditable(false);
        cHoraEntrada.setEditable(false);
        cMinEntrada.setEditable(false);
        cHoraSalida.setEditable(false);
        cMinSalida.setEditable(false);
        cValorHora.setEditable(false);
        cHoras.setEditable(false);
        cTotalPagar.setEditable(false);

        //JButtons
        bCancelar = new JButton("Cancelar");
        bBuscar = new JButton("Buscar");

        eNoFactura.setBounds(35, 15, 60, 15);
        cNoFactura.setBounds(120, 15, 30, 20);
        bBuscar.setBounds(180, 15, 90, 20);

        eVehiculo.setBounds(20, 50, 60, 15);
        cVehiculo.setBounds(120, 50, 120, 20);

        eNombreCliente.setBounds(20, 80, 90, 15);
        cNombreCliente.setBounds(120, 80, 120, 20);

        ePlaca.setBounds(20, 110, 90, 15);
        cPlaca.setBounds(120, 110, 120, 20);

        eHoraEntrada.setBounds(20, 140, 90, 15);
        cHoraEntrada.setBounds(120, 140, 120, 20);

        eMinEntrada.setBounds(20, 170, 90, 15);
        cMinEntrada.setBounds(120, 170, 120, 20);

        eHoraSalida.setBounds(20, 200, 90, 15);
        cHoraSalida.setBounds(120, 200, 120, 20);

        eMinSalida.setBounds(20, 230, 90, 15);
        cMinSalida.setBounds(120, 230, 120, 20);

        eValorHora.setBounds(20, 260, 90, 15);
        cValorHora.setBounds(120, 260, 120, 20);

        eHoras.setBounds(20, 290, 90, 15);
        cHoras.setBounds(120, 290, 120, 20);

        eTotalPagar.setBounds(20, 320, 90, 15);
        cTotalPagar.setBounds(120, 320, 120, 20);

        bCancelar.setBounds(100, 350, 90, 20);

        panel.add(eNoFactura);
        panel.add(cNoFactura);
        panel.add(bBuscar);

        panel.add(eVehiculo);
        panel.add(cVehiculo);

        panel.add(eNombreCliente);
        panel.add(cNombreCliente);

        panel.add(ePlaca);
        panel.add(cPlaca);

        panel.add(eHoraEntrada);
        panel.add(cHoraEntrada);

        panel.add(eMinEntrada);
        panel.add(cMinEntrada);

        panel.add(eHoraSalida);
        panel.add(cHoraSalida);

        panel.add(eMinSalida);
        panel.add(cMinSalida);

        panel.add(eValorHora);
        panel.add(cValorHora);

        panel.add(eHoras);
        panel.add(cHoras);

        panel.add(eTotalPagar);
        panel.add(cTotalPagar);

        panel.add(bCancelar);

        bBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (cNoFactura.getText().length() != 0) {

                    String url = "jdbc:mysql://localhost/" + nombreBD;

                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                        Connection con = DriverManager.getConnection(url, "root", "");

                        PreparedStatement preparedStatement;

                        preparedStatement = con.prepareStatement("SELECT vehiculo,nombreCliente,placa,horaEntrada,minutoEntrada,horaSalida,minutoSalida,valorHora,horas,total FROM factura WHERE codigoFactura = ?");
                        preparedStatement.setString(1, cNoFactura.getText());
                        ResultSet resultSet = preparedStatement.executeQuery();

                        if (resultSet.next()) {
                            cVehiculo.setText((resultSet.getString("vehiculo")));
                            cNombreCliente.setText((resultSet.getString("nombreCliente")));
                            cPlaca.setText((resultSet.getString("placa")));
                            cHoraEntrada.setText((resultSet.getString("horaEntrada")));
                            cMinEntrada.setText((resultSet.getString("minutoEntrada")));
                            cHoraSalida.setText((resultSet.getString("horaSalida")));
                            cMinSalida.setText((resultSet.getString("minutoSalida")));
                            cValorHora.setText((resultSet.getString("valorHora")));
                            cHoras.setText((resultSet.getString("horas")));
                            cTotalPagar.setText((resultSet.getString("total")));
                        } else {
                            JOptionPane.showMessageDialog(null, "La factura no existe en la base de datos");
                        }

                        preparedStatement.close();
                        con.close();

                    } catch (Exception r) {
                        JOptionPane.showMessageDialog(null,
                                "Error al buscar usuario: " + r.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Escribe el numero de factura");
                };

            }
        });

        bCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                dispose();

            }
        });

        setTitle("Buscar Factura, Usuario " + nombre);
        setSize(300, 420);
        setLocationRelativeTo(null);
        setVisible(true);

    }
}
