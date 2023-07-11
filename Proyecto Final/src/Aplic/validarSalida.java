/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author JUAND
 */
public class validarSalida extends JDialog{
    JLabel vehiculo, nombre, placa, he, me,hs,ms,valor,horas,total;
    JTextField caja_vehiculo, caja_nombre, caja_placa, caja_he, caja_me, caja_hs, caja_ms, caja_valor, caja_horas, caja_total;
    JButton guardar, cancelar;
    String informacion;
    String vehiculoPlaca;
    int horaEntrada, minutoEntrada,valorHora;
    String nombreCliente;
    public validarSalida(Frame e, boolean modal, String nombreBD, String nombre, String formato, int codigo){
    super(e,modal);
    Container c = getContentPane();
    c.setLayout(null);
   
    //Jlaberl
    vehiculo = new JLabel("Vehiculo");
    this.nombre = new JLabel("Nombre Cliente");
    placa = new JLabel("Placa");
    he  = new JLabel("Hora entrada");
    me = new JLabel("Minutos entrada");
    hs = new JLabel("Hora salida");
    ms = new JLabel("Minutos salida");
    valor = new JLabel("Valor hora");
    horas = new JLabel("Horas");
    total = new JLabel("Total a pagar");
    //jtextfield
    caja_vehiculo = new JTextField(15);
    caja_nombre = new JTextField(15);
    caja_placa = new JTextField(16);
    caja_he = new JTextField(15);
    caja_me = new JTextField(15);
    caja_hs = new JTextField(15);
    caja_ms = new JTextField(15);
    caja_valor = new JTextField(16);
    caja_horas = new JTextField(16);
    caja_total = new JTextField(16);
    //pooner que no sean editables
    caja_vehiculo.setEditable(false);
    caja_nombre.setEditable(false);;
    caja_placa.setEditable(false);
    caja_he.setEditable(false);
    caja_me.setEditable(false);
    caja_hs.setEditable(false);
    caja_ms.setEditable(false);
    caja_valor.setEditable(false);
    caja_horas.setEditable(false);
    caja_total.setEditable(false);
    
    informacion = formato;
    
    //botones
    guardar = new JButton("Guardar");
    cancelar = new JButton ("Cancelar");
    //setear cajas
    
     if(informacion.substring(0,1).equals("c")){
    caja_vehiculo.setText("carro");
    } else if(informacion.substring(0,1).equals("m")){
      caja_vehiculo.setText("moto");
    } else if(informacion.substring(0,1).equals("b")){
     caja_vehiculo.setText("bicicleta");
    } 
     
     
     if(informacion.substring(0,1).equals("c")){
    caja_placa.setText(informacion.substring(2,8));
     vehiculoPlaca = informacion.substring(2,8);
    } else if(informacion.substring(0,1).equals("m")){
     caja_placa.setText(informacion.substring(2,8));
               vehiculoPlaca = informacion.substring(2,8);
    } else if(informacion.substring(0,1).equals("b")){
     caja_placa.setText(informacion.substring(2,6));
     vehiculoPlaca = informacion.substring(2,6);
    }
     
    if(informacion.substring(0,1).equals("c")){
    caja_hs.setText(informacion.substring(9,11));
    } else if(informacion.substring(0,1).equals("m")){
     caja_hs.setText(informacion.substring(9,11));
    } else if(informacion.substring(0,1).equals("b")){
     caja_hs.setText(informacion.substring(7,9));
    } 
    
      if(informacion.substring(0,1).equals("c")){
    caja_ms.setText(informacion.substring(12,14));
    } else if(informacion.substring(0,1).equals("m")){
     caja_ms.setText(informacion.substring(12,14));
    } else if(informacion.substring(0,1).equals("b")){
     caja_ms.setText(informacion.substring(10,12));
    } 
     
    //conexion para sacar nombreCliente y setearlo 
     String url = "jdbc:mysql://localhost/"+nombreBD;
            
                                   try {
                                        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                                        Connection con = DriverManager.getConnection(url, "root", "");

                                        PreparedStatement preparedStatement;

                                        preparedStatement = con.prepareStatement("SELECT horaEntrada,minutoEntrada,nombreCliente FROM ingreso WHERE placa = ?");
                                        preparedStatement.setString(1,vehiculoPlaca);
                                        ResultSet resultSet = preparedStatement.executeQuery();
                                       
                                         if(resultSet.next())
                                          {
                                          horaEntrada = Integer.parseInt(resultSet.getString("horaEntrada"));
                                          minutoEntrada = Integer.parseInt(resultSet.getString("minutoEntrada"));
                                          nombreCliente = (resultSet.getString("nombreCliente"));
                                           }else {
                                             JOptionPane.showMessageDialog(null, "Las credenciales no existen en la base de datos");   
                                                 dispose();
                                                 }
                                         
                                         
                                        preparedStatement.close();
                                        con.close();
                                       caja_he.setText(""+horaEntrada);
                                       caja_me.setText(""+minutoEntrada);
                                       caja_nombre.setText(nombreCliente);
                                    }catch (Exception r) {
                                        JOptionPane.showMessageDialog(null,
                                                "Error al buscar la placa: " + r.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                                    } 
                                   
                                   //conexion para setear el valor hora dependiendo el tipo
                                 
                                   try {
                                        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                                        Connection con = DriverManager.getConnection(url, "root", "");

                                        PreparedStatement preparedStatement;

                                        preparedStatement = con.prepareStatement("SELECT valor FROM valores WHERE tipo = ?");
                                        preparedStatement.setString(1,caja_vehiculo.getText());
                                        ResultSet resultSet = preparedStatement.executeQuery();
                                       
                                         if(resultSet.next())
                                          {
                                          valorHora = Integer.parseInt(resultSet.getString("valor"));
                                           }else {
                                             JOptionPane.showMessageDialog(null, "Las credenciales no existen en la base de datos");   
                                                 dispose();
                                                 }
                                         
                                         
                                        preparedStatement.close();
                                        con.close();
                                       caja_valor.setText(""+valorHora);
                                    }catch (Exception r) {
                                        JOptionPane.showMessageDialog(null,
                                                "Error al buscar valor hora: " + r.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                                    } 
                                   
    // setear horas y valortotal                               
       if(horaEntrada == Integer.parseInt(caja_hs.getText())){
             caja_horas.setText("1");
       } else if (minutoEntrada >= Integer.parseInt(caja_ms.getText())){
       int horaSalida = Integer.parseInt(caja_hs.getText());
       caja_horas.setText(""+(horaSalida - horaEntrada));
       } else {
       int horaSalida = Integer.parseInt(caja_hs.getText());
       caja_horas.setText(""+((horaSalida - horaEntrada) + 1));
       
       }
       
       int totalpagar = Integer.parseInt(caja_horas.getText())* Integer.parseInt(caja_valor.getText());
       caja_total.setText(""+totalpagar);                            
                                                     
    
    guardar.addActionListener(new ActionListener(){
    public void actionPerformed(ActionEvent e){
    int contador=0;
    //conexion para contar cuantas filas ha en factura y asi sacar el codigo usando count + 1
        String url = "jdbc:mysql://localhost/"+nombreBD;
            
                                   try {
                                        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                                        Connection con = DriverManager.getConnection(url, "root", "");

                                        PreparedStatement preparedStatement;

                                        preparedStatement = con.prepareStatement("SELECT COUNT(*) FROM factura"); 
                                        ResultSet resultSet = preparedStatement.executeQuery();
                                       
                                         if(resultSet.next())
                                          {
                                         contador = resultSet.getInt(1);   
                                           }
                                    }catch (Exception r) {
                                        JOptionPane.showMessageDialog(null,
                                                "Error al contar: " + r.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                                    } 
        
     //conexion para insertar en factura
     
                                      try {
                                        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                                        Connection con = DriverManager.getConnection(url, "root", "");

                                        PreparedStatement preparedStatement;

                                        preparedStatement = con.prepareStatement("INSERT into factura (codigoFactura,vehiculo,nombreCliente,placa,horaEntrada,minutoEntrada,horaSalida,minutoSalida,valorHora,horas,total, codigoEmpleado) values(?,?,?,?,?,?,?,?,?,?,?,?)");
                                        preparedStatement.setInt(1,contador + 1);
                                        preparedStatement.setString(2, caja_vehiculo.getText());
                                        preparedStatement.setString(3, caja_nombre.getText());
                                        preparedStatement.setString(4, caja_placa.getText());
                                        preparedStatement.setInt(5, Integer.parseInt(caja_he.getText()));
                                        preparedStatement.setInt(6, Integer.parseInt(caja_me.getText()));
                                        preparedStatement.setInt(7, Integer.parseInt(caja_hs.getText()));
                                        preparedStatement.setInt(8, Integer.parseInt(caja_ms.getText()));
                                        preparedStatement.setInt(9, Integer.parseInt(caja_valor.getText()));
                                        preparedStatement.setInt(10,Integer.parseInt(caja_horas.getText()));
                                        preparedStatement.setInt(11,Integer.parseInt(caja_total.getText()));
                                        preparedStatement.setInt(12,codigo);
                                        preparedStatement.executeUpdate();
                                        preparedStatement.close();
                                        con.close();
                                       
                                    }catch (Exception r) {
                                        JOptionPane.showMessageDialog(null,
                                                "Error al insertar factura: " + r.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                                    } 
                                    int numFactura = contador + 1;  
                                   JOptionPane.showMessageDialog(null, "Tu numero de factura es: " + numFactura);   
                                     //conexion para borrar ingreso y salida
                                    
                                    try {
                                        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                                        Connection con = DriverManager.getConnection(url, "root", "");

                                        PreparedStatement preparedStatement;

                                        preparedStatement = con.prepareStatement("DELETE FROM salida where vehiculoPlaca = ?");
                                        preparedStatement.setString(1,caja_placa.getText());
                                        
                                        preparedStatement.executeUpdate();
                                        preparedStatement.close();
                                        con.close();
   
                                    }catch (Exception r) {
                                        JOptionPane.showMessageDialog(null,
                                                "Error al borrar ingreso : " + r.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                                    }
                                    //borrar salida
                                    try {
                                        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                                        Connection con = DriverManager.getConnection(url, "root", "");

                                        PreparedStatement preparedStatement;

                                        preparedStatement = con.prepareStatement("DELETE FROM ingreso where placa = ?");
                                        preparedStatement.setString(1,caja_placa.getText());
                                        
                                        preparedStatement.executeUpdate();
                                        preparedStatement.close();
                                        con.close();
   
                                    }catch (Exception r) {
                                        JOptionPane.showMessageDialog(null,
                                                "Error al borrar ingreso : " + r.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                                    }
                                    
                                    dispose();


    }
    });
   
    cancelar.addActionListener(new ActionListener(){
    public void actionPerformed(ActionEvent e){
           //conexion para borrar la salida si no guardo la factura y le dio cancelar 
           
           String url = "jdbc:mysql://localhost/"+nombreBD;
                                   try {
                                        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                                        Connection con = DriverManager.getConnection(url, "root", "");

                                        PreparedStatement preparedStatement;

                                        preparedStatement = con.prepareStatement("DELETE FROM salida WHERE salida.vehiculoPlaca = ?");
                                        preparedStatement.setString(1,caja_placa.getText());
                                        preparedStatement.executeUpdate();
                                        preparedStatement.close();
                                        con.close();
                                      
                                    }catch (Exception r) {
                                        JOptionPane.showMessageDialog(null,
                                                "Error al eliminar: " + r.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                                    }   
                       dispose();
    
    }
    });
    
    //posiciones
    
        vehiculo.setBounds(20, 20, 60, 15);
        caja_vehiculo.setBounds(120, 20, 120, 20);

        this.nombre.setBounds(20, 50, 90, 15);
        caja_nombre.setBounds(120, 50, 120, 20);

        placa.setBounds(20, 80, 90, 15);
        caja_placa.setBounds(120, 80, 120, 20);

        he.setBounds(20, 110, 90, 15);
        caja_he.setBounds(120, 110, 120, 20);

        me.setBounds(20, 140, 90, 15);
        caja_me.setBounds(120, 140, 120, 20);

        hs.setBounds(20, 170, 90, 15);
        caja_hs.setBounds(120, 170, 120, 20);

        ms.setBounds(20, 200, 90, 15);
        caja_ms.setBounds(120, 200, 120, 20);

        valor.setBounds(20, 230, 90, 15);
        caja_valor.setBounds(120, 230, 120, 20);

        horas.setBounds(20, 260, 90, 15);
        caja_horas.setBounds(120, 260, 120, 20);

        total.setBounds(20, 290, 90, 15);
        caja_total.setBounds(120, 290, 120, 20);

        guardar.setBounds(45, 320, 90, 20);
        cancelar.setBounds(155, 320, 90, 20);
    
    c.add(vehiculo); c.add(caja_vehiculo);
    c.add(this.nombre); c.add(caja_nombre);
    c.add(placa); c.add(caja_placa);
    c.add(he); c.add(caja_he);
    c.add(me); c.add(caja_me);
    c.add(hs); c.add(caja_hs);
    c.add(ms); c.add(caja_ms);
    c.add(valor); c.add(caja_valor);
    c.add(horas); c.add(caja_horas);
    c.add(total); c.add(caja_total);
    c.add(guardar);
    c.add(cancelar);
    
    setTitle("Validar Salida, Usuario " + nombre);
    setSize(300,400);
    setLocationRelativeTo(null);
    setVisible(true);
    
  
    }
}
