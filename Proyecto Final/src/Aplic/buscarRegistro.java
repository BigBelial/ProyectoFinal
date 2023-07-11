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
public class buscarRegistro extends JDialog {
    JLabel usuario,total;
    JTextField caja_codigo, caja_total;
    JButton buscar, cancelar;
    JComboBox combo;
    JTable tabla;
    DefaultTableModel modelo;
    public buscarRegistro(Frame e , boolean modal, String nombreBD ,String nombre){
    super(e,modal);
    Container c = getContentPane();
    c.setLayout(null);
    
      //labels
    usuario = new JLabel ("Usuarios:");
    total = new JLabel("Total:");
    //textfleid
    caja_codigo = new JTextField(30);
    caja_total = new JTextField(30);
    //botones
    buscar = new JButton("Buscar");
    cancelar = new JButton("Cancelar");       
    //combo
    String tipo [] ={"Placa","Factura","Todos","Empleado"}; 
    combo = new JComboBox(tipo);
    //tabla
    modelo = new DefaultTableModel();
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
    
    
    tabla = new JTable (modelo);
    JScrollPane scroll = new JScrollPane (tabla,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS ,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    //
    
    buscar.addActionListener(new ActionListener(){
    public void actionPerformed(ActionEvent e){
    
    modelo.setRowCount(0);
         if(combo.getSelectedItem() == "Todos"){
             Object fila[] = new Object[10];
            String url = "jdbc:mysql://localhost/"+nombreBD;
                                        
   
                                    //tabla                                   
                                    try {
                                        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                                        Connection con = DriverManager.getConnection(url, "root", "");

                                        PreparedStatement preparedStatement;
                                        
                                        preparedStatement = con.prepareStatement("SELECT factura.codigoFactura, factura.vehiculo ,factura.nombreCliente,factura.placa,factura.horaEntrada,factura.minutoEntrada,factura.horaSalida,factura.minutoSalida,factura.valorHora,factura.horas,factura.total, usuarios.nombre FROM factura , usuarios WHERE factura.codigoEmpleado = usuarios.codigo");
                                        
                                       
                                        ResultSet resultSet = preparedStatement.executeQuery();
                                     
                                        while(resultSet.next())
                                          {
                                          
                                          fila[0] = (resultSet.getString("codigoFactura"));    
                                          fila[1] = (resultSet.getString("vehiculo"));
                                          fila[2] = (resultSet.getString("placa"));
                                          fila[3] = (resultSet.getString("nombreCliente"));
                                          String horaEntrada = (resultSet.getString("horaEntrada"));
                                          String minutoEntrada = (resultSet.getString("minutoEntrada"));
                                          fila[4] = horaEntrada+":"+minutoEntrada;
                                          String horaSalida = (resultSet.getString("horaSalida"));
                                          String minutoSalida = (resultSet.getString("minutoSalida"));
                                          fila[5] = horaSalida+":"+minutoSalida;  
                                          fila[6] = (resultSet.getString("horas"));
                                          fila[7] = (resultSet.getString("valorHora"));
                                          fila[8] = (resultSet.getString("total"));
                                          fila[9] =(resultSet.getString("nombre"));
                                          modelo.addRow(fila);
                                          }
                                        int cantidad = 0;
                                        for(int i = 0 ; i < modelo.getRowCount(); i++)
                                        {
                                            cantidad += Integer.parseInt(modelo.getValueAt(i,8).toString());
                                        }
                                        caja_total.setText(""+cantidad);
                                        
                                        preparedStatement.close();
                                        con.close();
                                       
                                    }catch (Exception r) {
                                        JOptionPane.showMessageDialog(null,
                                                "Error al buscar todos: " + r.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                                    }  
                                    }
         
          if(combo.getSelectedItem() == "Placa"){
             Object fila[] = new Object[10];
            String url = "jdbc:mysql://localhost/"+nombreBD;
                                        
   
                                    //tabla                                   
                                    try {
                                        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                                        Connection con = DriverManager.getConnection(url, "root", "");

                                        PreparedStatement preparedStatement;
                                        
                                        preparedStatement = con.prepareStatement("SELECT factura.codigoFactura, factura.vehiculo ,factura.nombreCliente,factura.placa,factura.horaEntrada,factura.minutoEntrada,factura.horaSalida,factura.minutoSalida,factura.valorHora,factura.horas,factura.total, usuarios.nombre FROM factura , usuarios WHERE factura.placa= ? and factura.codigoEmpleado = usuarios.codigo");
                                         preparedStatement.setString(1,caja_codigo.getText());
                                       
                                        ResultSet resultSet = preparedStatement.executeQuery();
                                     
                                        while(resultSet.next())
                                          {
                                          
                                          fila[0] = (resultSet.getString("codigoFactura"));    
                                          fila[1] = (resultSet.getString("vehiculo"));
                                          fila[2] = (resultSet.getString("placa"));
                                          fila[3] = (resultSet.getString("nombreCliente"));
                                          String horaEntrada = (resultSet.getString("horaEntrada"));
                                          String minutoEntrada = (resultSet.getString("minutoEntrada"));
                                          fila[4] = horaEntrada+":"+minutoEntrada;
                                          String horaSalida = (resultSet.getString("horaSalida"));
                                          String minutoSalida = (resultSet.getString("minutoSalida"));
                                          fila[5] = horaSalida+":"+minutoSalida;  
                                          fila[6] = (resultSet.getString("horas"));
                                          fila[7] = (resultSet.getString("valorHora"));
                                          fila[8] = (resultSet.getString("total"));
                                          fila[9] =(resultSet.getString("nombre"));
                                          modelo.addRow(fila);
                                          } 
                                        int cantidad = 0;
                                        for(int i = 0 ; i < modelo.getRowCount(); i++)
                                        {
                                            cantidad += Integer.parseInt(modelo.getValueAt(i,8).toString());
                                        }
                                        caja_total.setText(""+cantidad);
                                        preparedStatement.close();
                                        con.close();
                                       
                                    }catch (Exception r) {
                                        JOptionPane.showMessageDialog(null,
                                                "Error al buscar por placa: " + r.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                                    }  
                                    }
       
           if(combo.getSelectedItem() == "Factura"){
             Object fila[] = new Object[10];
            String url = "jdbc:mysql://localhost/"+nombreBD;
                                        
   
                                    //tabla                                   
                                    try {
                                        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                                        Connection con = DriverManager.getConnection(url, "root", "");

                                        PreparedStatement preparedStatement;
                                        
                                        preparedStatement = con.prepareStatement("SELECT factura.codigoFactura, factura.vehiculo ,factura.nombreCliente,factura.placa,factura.horaEntrada,factura.minutoEntrada,factura.horaSalida,factura.minutoSalida,factura.valorHora,factura.horas,factura.total, usuarios.nombre FROM factura , usuarios WHERE factura.codigoFactura = ? and factura.codigoEmpleado = usuarios.codigo");
                                        preparedStatement.setString(1,caja_codigo.getText());
                                       
                                        ResultSet resultSet = preparedStatement.executeQuery();
                                     
                                        while(resultSet.next())
                                          {
                                          
                                          fila[0] = (resultSet.getString("codigoFactura"));    
                                          fila[1] = (resultSet.getString("vehiculo"));
                                          fila[2] = (resultSet.getString("placa"));
                                          fila[3] = (resultSet.getString("nombreCliente"));
                                          String horaEntrada = (resultSet.getString("horaEntrada"));
                                          String minutoEntrada = (resultSet.getString("minutoEntrada"));
                                          fila[4] = horaEntrada+":"+minutoEntrada;
                                          String horaSalida = (resultSet.getString("horaSalida"));
                                          String minutoSalida = (resultSet.getString("minutoSalida"));
                                          fila[5] = horaSalida+":"+minutoSalida;  
                                          fila[6] = (resultSet.getString("horas"));
                                          fila[7] = (resultSet.getString("valorHora"));
                                          fila[8] = (resultSet.getString("total"));
                                          fila[9] =(resultSet.getString("nombre"));
                                          modelo.addRow(fila);
                                          }
                                        int cantidad = 0;
                                        for(int i = 0 ; i < modelo.getRowCount(); i++)
                                        {
                                            cantidad += Integer.parseInt(modelo.getValueAt(i,8).toString());
                                        }
                                        caja_total.setText(""+cantidad);
                                        preparedStatement.close();
                                        con.close();
                                       
                                    }catch (Exception r) {
                                        JOptionPane.showMessageDialog(null,
                                                "Error al buscar por factura : " + r.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                                    }  
                                    }
           
            if(combo.getSelectedItem() == "Empleado"){
             Object fila[] = new Object[10];
            String url = "jdbc:mysql://localhost/"+nombreBD;
                                        
   
                                    //tabla                                   
                                    try {
                                        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                                        Connection con = DriverManager.getConnection(url, "root", "");

                                        PreparedStatement preparedStatement;
                                        
                                        preparedStatement = con.prepareStatement("SELECT factura.codigoFactura, factura.vehiculo ,factura.nombreCliente,factura.placa,factura.horaEntrada,factura.minutoEntrada,factura.horaSalida,factura.minutoSalida,factura.valorHora,factura.horas,factura.total, usuarios.nombre FROM factura , usuarios WHERE factura.codigoEmpleado = ? and factura.codigoEmpleado = usuarios.codigo");
                                        preparedStatement.setString(1,caja_codigo.getText());
                                       
                                        ResultSet resultSet = preparedStatement.executeQuery();
                                     
                                        while(resultSet.next())
                                          {
                                          
                                          fila[0] = (resultSet.getString("codigoFactura"));    
                                          fila[1] = (resultSet.getString("vehiculo"));
                                          fila[2] = (resultSet.getString("placa"));
                                          fila[3] = (resultSet.getString("nombreCliente"));
                                          String horaEntrada = (resultSet.getString("horaEntrada"));
                                          String minutoEntrada = (resultSet.getString("minutoEntrada"));
                                          fila[4] = horaEntrada+":"+minutoEntrada;
                                          String horaSalida = (resultSet.getString("horaSalida"));
                                          String minutoSalida = (resultSet.getString("minutoSalida"));
                                          fila[5] = horaSalida+":"+minutoSalida;  
                                          fila[6] = (resultSet.getString("horas"));
                                          fila[7] = (resultSet.getString("valorHora"));
                                          fila[8] = (resultSet.getString("total"));
                                          fila[9] =(resultSet.getString("nombre"));
                                          modelo.addRow(fila);
                                          }
                                        int cantidad = 0;
                                        for(int i = 0 ; i < modelo.getRowCount(); i++)
                                        {
                                            cantidad += Integer.parseInt(modelo.getValueAt(i,8).toString());
                                        }
                                        caja_total.setText(""+cantidad);
                                        preparedStatement.close();
                                        con.close();
                                       
                                    }catch (Exception r) {
                                        JOptionPane.showMessageDialog(null,
                                                "Error al buscar por empleado: " + r.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                                    }  
                                    }
          
    }
    });
    
    
    cancelar.addActionListener(new ActionListener(){
    public void actionPerformed(ActionEvent e){
    
    dispose();
    
    }
    });
    
    
    //posiciones
    usuario.setBounds(10,6,55,50);
    combo.setBounds(80,15,100,30);
    caja_codigo.setBounds(220, 15, 170,30);
    buscar.setBounds(420,15,80,30);
    scroll.setBounds(15,80,600,190);
    total.setBounds(350,280,55,50);
    caja_total.setBounds(420, 290,130,30);
    cancelar.setBounds(220,320,100,30);
    
    c.add(usuario);
    c.add(combo);
    c.add(caja_codigo);
    c.add(buscar);
    c.add(total);
    c.add(caja_total);
    c.add(scroll);
    c.add(cancelar);
    
    setTitle("Buscar registro, Usuario " + nombre);
    setSize(650,400);
    setLocationRelativeTo(null);
    setVisible(true);
    
    }
}
