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
import javax.swing.JDialog;
import javax.swing.*;

/**
 *
 * @author JUAND
 */
public class salidaVehiculo extends JDialog{
    JLabel placa;
    JTextField caja_placa;
    JButton validar, cancelar;
    String formato;
    int horaEntrada, minutoEntrada;
    String nombre;
    
    public salidaVehiculo(Frame e, boolean modal, String nombreBD ,String nombre, int codigo){  
    super(e,modal);
    Container c = getContentPane();
    c.setLayout(new FlowLayout());
    placa = new JLabel("V-PLACA-HS-MS");
    caja_placa = new JTextField(10);
    validar = new JButton("Validar");
    cancelar = new JButton ("Cancelar");
    this.nombre = nombre;
       
    validar.addActionListener(new ActionListener(){
    public void actionPerformed(ActionEvent e){
    formato = caja_placa.getText();
    
    if(caja_placa.getText().length()!=0){
        if(!formato.substring(0,1).equals("c") && !formato.substring(0,1).equals("m") && !formato.substring(0,1).equals("b")){    
          
           JOptionPane.showMessageDialog(null, "El tipo de vehiculo no coincide");
  
        } else if(formato.substring(0,1).equals("c")){
        if(formato.length()==14){
        if(validarCarro(formato)){
             String url = "jdbc:mysql://localhost/"+nombreBD;
            
                                   try {
                                        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                                        Connection con = DriverManager.getConnection(url, "root", "");

                                        PreparedStatement preparedStatement;

                                        preparedStatement = con.prepareStatement("SELECT horaEntrada,minutoEntrada FROM ingreso WHERE placa = ?");
                                        preparedStatement.setString(1,formato.substring(2,8));
                                        ResultSet resultSet = preparedStatement.executeQuery();
                                       
                                         if(resultSet.next())
                                          {
                                          horaEntrada = Integer.parseInt(resultSet.getString("horaEntrada"));
                                          minutoEntrada = Integer.parseInt(resultSet.getString("minutoEntrada"));
                                          
                                          
                                           }else {
                                             JOptionPane.showMessageDialog(null, "Las credenciales no existen en la base de datos");   
                                                 dispose();
                                                 }
                                         
                                         
                                        preparedStatement.close();
                                        con.close();
                                       
                                    }catch (Exception r) {
                                        JOptionPane.showMessageDialog(null,
                                                "Error al buscar la placa: " + r.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                                    } 
             
             
        
                                    if(validarHoraCarro(formato)){
                                    try {
                                        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                                        Connection con = DriverManager.getConnection(url, "root", "");

                                        PreparedStatement preparedStatement;

                                        preparedStatement = con.prepareStatement("INSERT into salida (vehiculoPlaca,horaSalida,minutoSalida) values(?,?,?)");
                                        preparedStatement.setString(1, formato.substring(2,8));
                                        preparedStatement.setString(2, formato.substring(9,11));
                                        preparedStatement.setString(3, formato.substring(12,14));  
                                        preparedStatement.executeUpdate();
                                        preparedStatement.close();
                                        con.close();
                                        validarSalida valS = new validarSalida (null,true,nombreBD,nombre,formato,codigo);      
                                    }catch (Exception r) {
                                        JOptionPane.showMessageDialog(null,
                                                "Error al validar salida: " + r.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                                    }       

                                    dispose();
                                    }
           
        }    
        }else {JOptionPane.showMessageDialog(null, "Revisa que el formato este escrito correctamente");}
        
        
        }else if(formato.substring(0,1).equals("m")){
        if(formato.length()==14){
        if(validarMoto(formato)){    
           String url = "jdbc:mysql://localhost/"+nombreBD;
            try {
                                        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                                        Connection con = DriverManager.getConnection(url, "root", "");

                                        PreparedStatement preparedStatement;

                                        preparedStatement = con.prepareStatement("SELECT horaEntrada,minutoEntrada FROM ingreso WHERE placa = ?");
                                        preparedStatement.setString(1,formato.substring(2,8));
                                        ResultSet resultSet = preparedStatement.executeQuery();
                                       
                                         if(resultSet.next())
                                          {
                                          horaEntrada = Integer.parseInt(resultSet.getString("horaEntrada"));
                                          minutoEntrada = Integer.parseInt(resultSet.getString("minutoEntrada"));

                                           }else {
                                             JOptionPane.showMessageDialog(null, "Las credenciales no existen en la base de datos");   
                                                 dispose();
                                                 }
                                         
                                         
                                        preparedStatement.close();
                                        con.close();
                                       
                                    }catch (Exception r) {
                                        JOptionPane.showMessageDialog(null,
                                                "Error al buscar la placa: " + r.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                                    } 
             
             
        
                                    if(validarHoraMoto(formato)){
                                    try {
                                        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                                        Connection con = DriverManager.getConnection(url, "root", "");

                                        PreparedStatement preparedStatement;

                                        preparedStatement = con.prepareStatement("INSERT into salida (vehiculoPlaca,horaSalida,minutoSalida) values(?,?,?)");
                                        preparedStatement.setString(1, formato.substring(2,8));
                                        preparedStatement.setString(2, formato.substring(9,11));
                                        preparedStatement.setString(3, formato.substring(12,14));  
                                        preparedStatement.executeUpdate();
                                        preparedStatement.close();
                                        con.close();
                                       validarSalida valS = new validarSalida (null,true,nombreBD,nombre,formato,codigo);      
                                    }catch (Exception r) {
                                        JOptionPane.showMessageDialog(null,
                                                "Error al validar salida: " + r.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                                    }       

                                    dispose();
                                    }
                              
            
        }    
        }else {JOptionPane.showMessageDialog(null, "Revisa que el formato este escrito correctamente");}
           
        }else if(formato.substring(0,1).equals("b")){
        if(formato.length()==12){
        if(validarBicicleta(formato)){
           String url = "jdbc:mysql://localhost/"+nombreBD;

             try {
                                        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                                        Connection con = DriverManager.getConnection(url, "root", "");

                                        PreparedStatement preparedStatement;

                                        preparedStatement = con.prepareStatement("SELECT horaEntrada,minutoEntrada FROM ingreso WHERE placa = ?");
                                        preparedStatement.setString(1,formato.substring(2,6));
                                        ResultSet resultSet = preparedStatement.executeQuery();
                                       
                                         if(resultSet.next())
                                          {
                                          horaEntrada = Integer.parseInt(resultSet.getString("horaEntrada"));
                                          minutoEntrada = Integer.parseInt(resultSet.getString("minutoEntrada"));
                                           }else {
                                             JOptionPane.showMessageDialog(null, "Las credenciales no existen en la base de datos");   
                                                 dispose();
                                                 }
                                         
                                         
                                        preparedStatement.close();
                                        con.close();
                                       
                                    }catch (Exception r) {
                                        JOptionPane.showMessageDialog(null,
                                                "Error al buscar la placa: " + r.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                                    } 
             
             
        
                                    if(validarHoraBicicleta(formato)){
                                    try {
                                        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                                        Connection con = DriverManager.getConnection(url, "root", "");

                                        PreparedStatement preparedStatement;

                                        preparedStatement = con.prepareStatement("INSERT into salida (vehiculoPlaca,horaSalida,minutoSalida) values(?,?,?)");
                                        preparedStatement.setString(1, formato.substring(2,6));
                                        preparedStatement.setString(2, formato.substring(7,9));
                                        preparedStatement.setString(3, formato.substring(10,12));  
                                        preparedStatement.executeUpdate();
                                        preparedStatement.close();
                                        con.close();
                                       validarSalida valS = new validarSalida (null,true,nombreBD,nombre,formato,codigo);      
                                    }catch (Exception r) {
                                        JOptionPane.showMessageDialog(null,
                                                "Error al validar salida: " + r.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                                    }       

                                    dispose();
                                    }
           
           
            
        }    
        }else {JOptionPane.showMessageDialog(null, "Revisa que el formato este escrito correctamente");}
        
        }
    
    
    } else { JOptionPane.showMessageDialog(null, "Escribe la placa");};
    
    }
    });
    
    
    
    cancelar.addActionListener(new ActionListener(){
    public void actionPerformed(ActionEvent e){
    
    dispose();
    
    }
    });
    
    
    c.add(placa);
    c.add(caja_placa);
    c.add(validar);
    c.add(cancelar);
    
    setTitle("Salida vehiculo, Usuario " + nombre);
    setSize(300,140);
    setLocationRelativeTo(null);
    setVisible(true);
    
    }
    
     public boolean validarCarro(String formato){
    boolean vali = false;
    String letras = formato.substring(2,5);
    String numeros = formato.substring(5,8);
    String hora = formato.substring(9,11);
    String minuto = formato.substring(12,14);

    if (formato.matches(".*[ ].*")) {
            JOptionPane.showMessageDialog(null, "El formato no puede tener espacios");
            return false;
        }
    
    
    if(letras.matches(".*[0-9].*")){
    JOptionPane.showMessageDialog(null, "Los 3 primero caracteres de la placa deben ser letras");
            return false;
        } 
    
    if(!numeros.matches("^[^a-zA-Z]*$")){
                JOptionPane.showMessageDialog(null, "Los ultimos 3 caracteres de la placa deben ser numeros");
            return false;
            }
    
    if(Integer.parseInt(hora) >=0 && 23 >=Integer.parseInt(hora)){
      if(Integer.parseInt(minuto) >=0 && Integer.parseInt(minuto) < 60){  
              vali = true;
      } else JOptionPane.showMessageDialog(null, "Formato de hora incorrecto");
    } else JOptionPane.showMessageDialog(null, "Formato de hora incorrecto");
    
    
    return vali;
    }
    
    
    public boolean validarMoto(String formato){
    boolean vali = false;
    String letras = formato.substring(2,5);
    String numeros = formato.substring(5,7);
    String ultimo = formato.substring(7,8);
    String hora = formato.substring(9,11);
    String minuto = formato.substring(12,14);
    
    if (formato.matches(".*[ ].*")) {
            JOptionPane.showMessageDialog(null, "El formato no puede tener espacios");
            return false;
        }
    
    
    if(letras.matches(".*[0-9].*")){
    JOptionPane.showMessageDialog(null, "Los 3 primero caracteres de la placa deben ser letras");
            return false;
        } 
    
    if(!numeros.matches("^[^a-zA-Z]*$")){
                JOptionPane.showMessageDialog(null, "Revisa que tu placa este escrita en formato de moto: xxx12x");
            return false;
            }
    if(ultimo.matches(".*[0-9].*")){
    JOptionPane.showMessageDialog(null, "Revisa que tu placa este escrita en formato de moto: xxx12x");
            return false;
        } 
    
    
    
    if(Integer.parseInt(hora) >=0 && 23 >=Integer.parseInt(hora)){
      if(Integer.parseInt(minuto) >=0 && Integer.parseInt(minuto) < 60){  
              vali = true;
      } else JOptionPane.showMessageDialog(null, "Formato de hora incorrecto");
    } else JOptionPane.showMessageDialog(null, "Formato de hora incorrecto");
    
    
    return vali;
    }
    
    public boolean validarBicicleta(String formato){
    boolean vali = false;
    String numeros = formato.substring(2,6);
    String hora = formato.substring(7,9);
    String minuto = formato.substring(10,12);

    
    if (formato.matches(".*[ ].*")) {
            JOptionPane.showMessageDialog(null, "El formato no puede tener espacios");
            return false;
        }
 
    if(!numeros.matches("^[^a-zA-Z]*$")){
                JOptionPane.showMessageDialog(null, "Revisa que tu placa este escrita en formato bicicleta 0000");
            return false;
            }

    if(Integer.parseInt(hora) >=0 && 23 >=Integer.parseInt(hora)){
      if(Integer.parseInt(minuto) >=0 && Integer.parseInt(minuto) < 60){  
              vali = true;
      } else JOptionPane.showMessageDialog(null, "Formato de hora incorrecto");
    } else JOptionPane.showMessageDialog(null, "Formato de hora incorrecto");
    
    
    return vali;
    }
    
    public boolean validarHoraCarro(String formato){
        boolean validar =false;
       int horaSalida = Integer.parseInt(formato.substring(9,11));
       int minutoSalida = Integer.parseInt(formato.substring(12,14));
        
       if(horaSalida > horaEntrada){
           validar = true;
       } else
       
       if(horaSalida == horaEntrada){
       if(minutoSalida > minutoEntrada){
           
        validar = true;
       
       } else {JOptionPane.showMessageDialog(null, "Revisa que la fecha este escrita correctamente ");}
       }else {JOptionPane.showMessageDialog(null, "Revisa que la fecha este escrita correctamente ");}
       
       
       
      return validar;
    }
    public boolean validarHoraMoto(String formato){
        boolean validar = false;
        int horaSalida = Integer.parseInt(formato.substring(9,11));
       int minutoSalida = Integer.parseInt(formato.substring(12,14));
        if(horaSalida > horaEntrada){
           validar = true;
       } else 
       
       if(horaSalida == horaEntrada){
       if(minutoSalida > minutoEntrada){
           
        validar = true;
       
       } else {JOptionPane.showMessageDialog(null, "Revisa que la fecha este escrita correctamente ");}
       }else {JOptionPane.showMessageDialog(null, "Revisa que la fecha este escrita correctamente ");}
       
       
     
        return validar;
    }
    public boolean validarHoraBicicleta(String formato){
       boolean validar = false;
       int horaSalida = Integer.parseInt(formato.substring(7,9));
       int minutoSalida = Integer.parseInt(formato.substring(10,12));
        if(horaSalida > horaEntrada){
           validar = true;
       } else 
       
       if(horaSalida == horaEntrada){
       if(minutoSalida > minutoEntrada){
           
        validar = true;
       
       }else {JOptionPane.showMessageDialog(null, "Revisa que la fecha este escrita correctamente ");}
       }else {JOptionPane.showMessageDialog(null, "Revisa que la fecha este escrita correctamente ");}
     
        return validar;
    }
    
}
