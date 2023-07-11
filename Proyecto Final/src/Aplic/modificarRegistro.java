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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author JUAND
 */
public class modificarRegistro extends JDialog {

    JLabel usuario;
    JTextField caja_codigo;
    JButton buscar, cancelar, modificarSeleccionado;
    JComboBox combo;
    JTable tabla;
    DefaultTableModel modelo;

    public modificarRegistro(Frame e, boolean modal, String nombreBD, String nombre) {
        super(e, modal);
        Container c = getContentPane();
        c.setLayout(null);

        //labels
        usuario = new JLabel("Usuarios:");
        //textfleid
        caja_codigo = new JTextField(30);
        //botone
        buscar = new JButton("Buscar");
        cancelar = new JButton("Cancelar");
        modificarSeleccionado = new JButton("Modificar Seleccionado");
        //combo
        String tipo[] = {"Placa", "Factura", "Todos", "Empleado"};
        combo = new JComboBox(tipo);
        //tabla
        modelo = new DefaultTableModel() {

            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 10) { // Índice de la columna "Modificar"
                    return Boolean.class; // Utilizar JCheckBox
                }
                return super.getColumnClass(columnIndex);
            }

            public boolean isCellEditable(int row, int column) {
                return !(column == 0 || column == 9);
            }

            public void setValueAt(Object aValue, int row, int column) {
                super.setValueAt(aValue, row, column);
                //para total
                if (column == 6 || column == 7) { // Columnas "Horas" y "Valor Hora"
                    int horas = Integer.parseInt(getValueAt(row, 6).toString());
                    int valorHora = Integer.parseInt(getValueAt(row, 7).toString());
                    int total = horas * valorHora;
                    setValueAt(total, row, 8); // Columna "Total"
                }
                //para horas
                if (column == 5 || column == 4) { // Columnas "H Salida" y "H Entrada"
                    
                    int posicionE = (getValueAt(row,4).toString().indexOf(":"));
                    int posicionS = (getValueAt(row,5).toString().indexOf(":"));
                    int tamE = (getValueAt(row,4).toString().length());
                    int tamS = (getValueAt(row,5).toString().length());
                  
                    String horaSalida = getValueAt(row, 5).toString().substring(0,posicionS);
                    String minutoSalida = getValueAt(row, 5).toString().substring(posicionS+1,tamS);
                    String horaEntrada = getValueAt(row, 4).toString().substring(0,posicionE);
                    String minutoEntrada = getValueAt(row, 4).toString().substring(posicionE+1,tamE);
                    
                 //poner el formato correcto 
                    if (horaEntrada.length() == 1) {
                               horaEntrada = "0"+horaEntrada;  
                            }
                    
                     if(minutoEntrada.length() == 1){
                               minutoEntrada = "0"+minutoEntrada;
                            }
                    
                     if(horaSalida.length()==1){
                            horaSalida = "0"+horaSalida;
                            }
                     
                     if(minutoSalida.length()==1){
                            minutoSalida = "0"+minutoSalida;
                            }
                    
                    // Calcular la diferencia de tiempo en horas
                    int horas = 0;
                 
                    int horasSalida = Integer.parseInt(horaSalida);
                    int minutosSalida = Integer.parseInt(minutoSalida);
                    int horasEntrada = Integer.parseInt(horaEntrada);
                    int minutosEntrada = Integer.parseInt(minutoEntrada);
                   
                    if (horasEntrada == horasSalida) {
                        horas = 1;
                    } else if (minutosEntrada >= minutosSalida) {
                        horas = horasSalida - horasEntrada;
                    } else {
                        horas = (horasSalida - horasEntrada) + 1;
                    }
 
                    setValueAt(horas, row, 6); // Columna "Horas"

                
                }
            }
        };

        modelo.addColumn("Factura");
        modelo.addColumn("Vehiculo");
        modelo.addColumn("Placa");
        modelo.addColumn("Cliente");
        modelo.addColumn("H Entrada");
        modelo.addColumn("H Salida");
        modelo.addColumn("Horas");
        modelo.addColumn("Valor Hora");
        modelo.addColumn("Total");
        modelo.addColumn("Empleado");
        modelo.addColumn("Modificar");

        tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        //

        buscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                modelo.setRowCount(0);
                if (combo.getSelectedItem() == "Todos") {
                    Object fila[] = new Object[10];
                    String url = "jdbc:mysql://localhost/" + nombreBD;

                    //tabla                                   
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                        Connection con = DriverManager.getConnection(url, "root", "");

                        PreparedStatement preparedStatement;

                        preparedStatement = con.prepareStatement("SELECT factura.codigoFactura, factura.vehiculo ,factura.nombreCliente,factura.placa,factura.horaEntrada,factura.minutoEntrada,factura.horaSalida,factura.minutoSalida,factura.valorHora,factura.horas,factura.total, usuarios.nombre FROM factura , usuarios WHERE factura.codigoEmpleado = usuarios.codigo");

                        ResultSet resultSet = preparedStatement.executeQuery();

                        while (resultSet.next()) {

                            fila[0] = (resultSet.getString("codigoFactura"));
                            fila[1] = (resultSet.getString("vehiculo"));
                            fila[2] = (resultSet.getString("placa"));
                            fila[3] = (resultSet.getString("nombreCliente"));
                            String horaEntrada = (resultSet.getString("horaEntrada"));
                            if (horaEntrada.length() == 1) {
                               horaEntrada = "0"+horaEntrada;  
                            }
                            String minutoEntrada = (resultSet.getString("minutoEntrada"));
                            if(minutoEntrada.length() == 1){
                               minutoEntrada = "0"+minutoEntrada;
                            }
                            fila[4] = horaEntrada + ":" + minutoEntrada;
                            String horaSalida = (resultSet.getString("horaSalida"));
                            if(horaSalida.length()==1){
                            horaSalida = "0"+horaSalida;
                            }
                            String minutoSalida = (resultSet.getString("minutoSalida"));
                            if(minutoSalida.length()==1){
                            minutoSalida = "0"+minutoSalida;
                            }
                            fila[5] = horaSalida + ":" + minutoSalida;
                            fila[6] = (resultSet.getString("horas"));
                            fila[7] = (resultSet.getString("valorHora"));
                            fila[8] = (resultSet.getString("total"));
                            fila[9] = (resultSet.getString("nombre"));
                            modelo.addRow(fila);
                        }
                        preparedStatement.close();
                        con.close();

                    } catch (Exception r) {
                        JOptionPane.showMessageDialog(null,
                                "Error al buscar todos: " + r.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }

                if (combo.getSelectedItem() == "Placa") {
                    Object fila[] = new Object[10];
                    String url = "jdbc:mysql://localhost/" + nombreBD;

                    //tabla                                   
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                        Connection con = DriverManager.getConnection(url, "root", "");

                        PreparedStatement preparedStatement;

                        preparedStatement = con.prepareStatement("SELECT factura.codigoFactura, factura.vehiculo ,factura.nombreCliente,factura.placa,factura.horaEntrada,factura.minutoEntrada,factura.horaSalida,factura.minutoSalida,factura.valorHora,factura.horas,factura.total, usuarios.nombre FROM factura , usuarios WHERE factura.placa= ? and factura.codigoEmpleado = usuarios.codigo");
                        preparedStatement.setString(1, caja_codigo.getText());

                        ResultSet resultSet = preparedStatement.executeQuery();

                        while (resultSet.next()) {

                            fila[0] = (resultSet.getString("codigoFactura"));
                            fila[1] = (resultSet.getString("vehiculo"));
                            fila[2] = (resultSet.getString("placa"));
                            fila[3] = (resultSet.getString("nombreCliente"));
                            String horaEntrada = (resultSet.getString("horaEntrada"));
                            if (horaEntrada.length() == 1) {
                               horaEntrada = "0"+horaEntrada;  
                            }
                            String minutoEntrada = (resultSet.getString("minutoEntrada"));
                            if(minutoEntrada.length() == 1){
                               minutoEntrada = "0"+minutoEntrada;
                            }
                            fila[4] = horaEntrada + ":" + minutoEntrada;
                            String horaSalida = (resultSet.getString("horaSalida"));
                            if(horaSalida.length()==1){
                            horaSalida = "0"+horaSalida;
                            }
                            String minutoSalida = (resultSet.getString("minutoSalida"));
                            if(minutoSalida.length()==1){
                            minutoSalida = "0"+minutoSalida;
                            }
                            fila[5] = horaSalida + ":" + minutoSalida;
                            fila[6] = (resultSet.getString("horas"));
                            fila[7] = (resultSet.getString("valorHora"));
                            fila[8] = (resultSet.getString("total"));
                            fila[9] = (resultSet.getString("nombre"));
                            modelo.addRow(fila);
                        }
                        preparedStatement.close();
                        con.close();

                    } catch (Exception r) {
                        JOptionPane.showMessageDialog(null,
                                "Error al buscar por placa: " + r.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }

                if (combo.getSelectedItem() == "Factura") {
                    Object fila[] = new Object[10];
                    String url = "jdbc:mysql://localhost/" + nombreBD;

                    //tabla                                   
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                        Connection con = DriverManager.getConnection(url, "root", "");

                        PreparedStatement preparedStatement;

                        preparedStatement = con.prepareStatement("SELECT factura.codigoFactura, factura.vehiculo ,factura.nombreCliente,factura.placa,factura.horaEntrada,factura.minutoEntrada,factura.horaSalida,factura.minutoSalida,factura.valorHora,factura.horas,factura.total, usuarios.nombre FROM factura , usuarios WHERE factura.codigoFactura = ? and factura.codigoEmpleado = usuarios.codigo");
                        preparedStatement.setString(1, caja_codigo.getText());

                        ResultSet resultSet = preparedStatement.executeQuery();

                        while (resultSet.next()) {

                            fila[0] = (resultSet.getString("codigoFactura"));
                            fila[1] = (resultSet.getString("vehiculo"));
                            fila[2] = (resultSet.getString("placa"));
                            fila[3] = (resultSet.getString("nombreCliente"));
                            String horaEntrada = (resultSet.getString("horaEntrada"));
                            if (horaEntrada.length() == 1) {
                               horaEntrada = "0"+horaEntrada;  
                            }
                            String minutoEntrada = (resultSet.getString("minutoEntrada"));
                            if(minutoEntrada.length() == 1){
                               minutoEntrada = "0"+minutoEntrada;
                            }
                            fila[4] = horaEntrada + ":" + minutoEntrada;
                            String horaSalida = (resultSet.getString("horaSalida"));
                            if(horaSalida.length()==1){
                            horaSalida = "0"+horaSalida;
                            }
                            String minutoSalida = (resultSet.getString("minutoSalida"));
                            if(minutoSalida.length()==1){
                            minutoSalida = "0"+minutoSalida;
                            }
                            fila[5] = horaSalida + ":" + minutoSalida;
                            fila[6] = (resultSet.getString("horas"));
                            fila[7] = (resultSet.getString("valorHora"));
                            fila[8] = (resultSet.getString("total"));
                            fila[9] = (resultSet.getString("nombre"));
                            modelo.addRow(fila);
                        }
                        preparedStatement.close();
                        con.close();

                    } catch (Exception r) {
                        JOptionPane.showMessageDialog(null,
                                "Error al buscar por factura: " + r.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }

                if (combo.getSelectedItem() == "Empleado") {
                    Object fila[] = new Object[10];
                    String url = "jdbc:mysql://localhost/" + nombreBD;

                    //tabla                                   
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                        Connection con = DriverManager.getConnection(url, "root", "");

                        PreparedStatement preparedStatement;

                        preparedStatement = con.prepareStatement("SELECT factura.codigoFactura, factura.vehiculo ,factura.nombreCliente,factura.placa,factura.horaEntrada,factura.minutoEntrada,factura.horaSalida,factura.minutoSalida,factura.valorHora,factura.horas,factura.total, usuarios.nombre FROM factura , usuarios WHERE factura.codigoEmpleado = ? and factura.codigoEmpleado = usuarios.codigo");
                        preparedStatement.setString(1, caja_codigo.getText());

                        ResultSet resultSet = preparedStatement.executeQuery();

                        while (resultSet.next()) {

                           fila[0] = (resultSet.getString("codigoFactura"));
                            fila[1] = (resultSet.getString("vehiculo"));
                            fila[2] = (resultSet.getString("placa"));
                            fila[3] = (resultSet.getString("nombreCliente"));
                            String horaEntrada = (resultSet.getString("horaEntrada"));
                            if (horaEntrada.length() == 1) {
                               horaEntrada = "0"+horaEntrada;  
                            }
                            String minutoEntrada = (resultSet.getString("minutoEntrada"));
                            if(minutoEntrada.length() == 1){
                               minutoEntrada = "0"+minutoEntrada;
                            }
                            fila[4] = horaEntrada + ":" + minutoEntrada;
                            String horaSalida = (resultSet.getString("horaSalida"));
                            if(horaSalida.length()==1){
                            horaSalida = "0"+horaSalida;
                            }
                            String minutoSalida = (resultSet.getString("minutoSalida"));
                            if(minutoSalida.length()==1){
                            minutoSalida = "0"+minutoSalida;
                            }
                            fila[5] = horaSalida + ":" + minutoSalida;
                            fila[6] = (resultSet.getString("horas"));
                            fila[7] = (resultSet.getString("valorHora"));
                            fila[8] = (resultSet.getString("total"));
                            fila[9] = (resultSet.getString("nombre"));
                            modelo.addRow(fila);
                        }
                        preparedStatement.close();
                        con.close();

                    } catch (Exception r) {
                        JOptionPane.showMessageDialog(null,
                                "Error al buscar por empleado: " + r.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }

            }
        });

        modificarSeleccionado.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                for (int i = 0; i < modelo.getRowCount(); i++) {
                    if (modelo.getValueAt(i, 10) == null) {

                    } else {

                        if (modelo.getValueAt(i, 1).equals("carro") || modelo.getValueAt(i, 1).equals("moto") || modelo.getValueAt(i, 1).equals("bicicleta")) {
                          if(validarFormato(modelo.getValueAt(i,4).toString(), modelo.getValueAt(i,5).toString())){
                            if (Integer.parseInt(modelo.getValueAt(i, 4).toString().substring(0, 2)) < Integer.parseInt(modelo.getValueAt(i, 5).toString().substring(0, 2)) || (Integer.parseInt(modelo.getValueAt(i, 4).toString().substring(0, 2)) == Integer.parseInt(modelo.getValueAt(i, 5).toString().substring(0, 2)) && Integer.parseInt(modelo.getValueAt(i, 4).toString().substring(3, 5)) < Integer.parseInt(modelo.getValueAt(i, 5).toString().substring(3, 5)))) {

                                String url = "jdbc:mysql://localhost/" + nombreBD;
                                try {
                                    Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                                    Connection con = DriverManager.getConnection(url, "root", "");

                                    PreparedStatement preparedStatement;

                                    preparedStatement = con.prepareStatement("UPDATE factura set vehiculo = ? ,placa = ?, nombreCliente = ? , horaEntrada = ? , minutoEntrada =?,horaSalida = ? , minutoSalida = ? , valorHora = ?, horas = ? , total = ? where codigoFactura = ?");
                                    preparedStatement.setString(1, modelo.getValueAt(i, 1).toString());
                                    preparedStatement.setString(2, modelo.getValueAt(i, 2).toString());
                                    preparedStatement.setString(3, modelo.getValueAt(i, 3).toString());
                                    String horaEntrada = modelo.getValueAt(i, 4).toString();
                                    String horaSalida = modelo.getValueAt(i, 5).toString();
                                    preparedStatement.setInt(4, Integer.parseInt(horaEntrada.substring(0, 2)));
                                    preparedStatement.setInt(5, Integer.parseInt(horaEntrada.substring(3, 5)));
                                    preparedStatement.setInt(6, Integer.parseInt(horaSalida.substring(0, 2)));
                                    preparedStatement.setInt(7, Integer.parseInt(horaSalida.substring(3, 5)));
                                    preparedStatement.setInt(8, Integer.parseInt(modelo.getValueAt(i, 7).toString()));
                                    preparedStatement.setInt(9, Integer.parseInt(modelo.getValueAt(i, 6).toString()));
                                    preparedStatement.setInt(10, Integer.parseInt(modelo.getValueAt(i, 8).toString()));
                                    preparedStatement.setInt(11, Integer.parseInt(modelo.getValueAt(i, 0).toString()));

                                    preparedStatement.executeUpdate();
                                    preparedStatement.close();
                                    con.close();
                                    JOptionPane.showMessageDialog(null, "Se ha modificado el usuario");
                                } catch (Exception r) {
                                    JOptionPane.showMessageDialog(null,
                                            "Error al modificar registro : " + r.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                                }

                            } else {
                                JOptionPane.showMessageDialog(null, "Escribe bien la hora de entrada y salida");
                            }
                            
                        } else {JOptionPane.showMessageDialog(null, "Revisa que el formato de hora este en formato xx:xx");}
                        } else {
                            JOptionPane.showMessageDialog(null, "Escribe el tipo de vehiculo correcto");
                        }

                    }

                }
            }
        });

        cancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                dispose();

            }
        });

        //posiciones
        usuario.setBounds(10, 6, 55, 50);
        combo.setBounds(80, 15, 100, 30);
        caja_codigo.setBounds(220, 15, 170, 30);
        buscar.setBounds(420, 15, 80, 30);
        scroll.setBounds(15, 80, 600, 190);
        modificarSeleccionado.setBounds(80, 300, 180, 30);
        cancelar.setBounds(370, 300, 100, 30);

        c.add(usuario);
        c.add(combo);
        c.add(caja_codigo);
        c.add(buscar);
        c.add(scroll);
        c.add(modificarSeleccionado);
        c.add(cancelar);

        setTitle("Modificar registro, Usuario " + nombre);
        setSize(650, 400);
        setLocationRelativeTo(null);
        setVisible(true);

    }
    public boolean validarFormato(String horaEntrada, String horaSalida){
       boolean validar = false;
       
       int tamE = horaEntrada.length();
       int tamS = horaSalida.length();
       
        if(tamE == 5 && tamS == 5){
        validar = true;
        } 
      
        return validar;
    }

}
