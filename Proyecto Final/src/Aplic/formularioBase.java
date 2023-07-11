package Aplic;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Frame;
import javax.swing.JDialog;
import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class formularioBase extends JDialog {
    
    public static Connection connection;
    public static Statement statement;
    public final String SERVER = "jdbc:mysql://localhost:3306";

    JButton crear, cancelar;
    JLabel eNombreBD, enombreUsuario, eContraseña, etiquetabd;
    JTextField cNombreBD, cnombreUsuario, cContraseña;
    String nom;
    boolean conectado = false;
    boolean cancelado = false;
     Base base = new Base();

    public formularioBase(Frame e, boolean modal) {
        super(e, modal);

        //Etiquetas y cajas de texto
        etiquetabd = new JLabel("Credenciales de la base de datos");
        crear = new JButton("Crear base de datos");
        eNombreBD = new JLabel("Nombre DB");
        enombreUsuario = new JLabel("Nombre usuario");
        eContraseña = new JLabel("Contraseña");
        cnombreUsuario = new JTextField(10);
        cContraseña = new JTextField(10);
        cNombreBD = new JTextField(10);
        //botones
        crear = new JButton("Crear");
        cancelar = new JButton("Cancelar");

        JPanel panel = new JPanel();
        panel.setLayout(null);
        this.getContentPane().add(panel);

        etiquetabd.setBounds(40, 20, 200, 15);
        eNombreBD.setBounds(20, 60, 90, 15);
        cNombreBD.setBounds(120, 60, 120, 20);
        enombreUsuario.setBounds(20, 90, 90, 15);
        cnombreUsuario.setBounds(120, 90, 120, 20);
        eContraseña.setBounds(20, 120, 90, 15);
        cContraseña.setBounds(120, 120, 120, 20);
        crear.setBounds(30, 170, 100, 20);
        cancelar.setBounds(150,170, 100,20);
        
        cnombreUsuario.setText("root");
        cnombreUsuario.setEditable(false);
       
        cContraseña.setText("");
        cContraseña.setEditable(false);
        
        panel.add(etiquetabd);
        panel.add(crear);
        panel.add(cancelar);
        panel.add(eNombreBD);
        panel.add(cNombreBD);
        panel.add(enombreUsuario);
        panel.add(cnombreUsuario);
        panel.add(eContraseña);
        panel.add(cContraseña);

     
        crear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
               if(cNombreBD.getText().length() != 0){
               if(cnombreUsuario.getText().length()!=0){
            
                
                String url = "jdbc:mysql://localhost:3306/";
                String nombreBD = cNombreBD.getText();
                String usuario = "root";
                String contra = "";
                nom = cNombreBD.getText();
           
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection(SERVER+"/"+nombreBD,usuario,contra);
            statement =connection.createStatement();
            System.out.println("Conectado");
            conectado = true;
            
            dispose();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException r) {
            System.out.println("Error de conexion a la base de datos " + r.getMessage());
        }
        
        
        if(!conectado){
        try {
            
            // Conexión a la base de datos
            Connection connection = DriverManager.getConnection(url, usuario, contra);

            // Crear la base de datos
            String sqlCrearBD = "CREATE DATABASE " + nombreBD;
            Statement statementBD = connection.createStatement();
            statementBD.executeUpdate(sqlCrearBD);
            System.out.println("Base de datos creada correctamente.");

            // Conexión a la base de datos creada
            String urlBD = url + nombreBD;
            Connection connectionBD = DriverManager.getConnection(urlBD, usuario, contra);

            // Crear la tabla "usuarios"
            String sqlCrearTablaUsuario = "CREATE TABLE usuarios "
                    + "("
                    + "codigo INT AUTO_INCREMENT PRIMARY KEY,"
                    + "nombre VARCHAR(255),"
                    + "correo VARCHAR(255) UNIQUE,"
                    + "contraseña VARCHAR(255),"
                    + "nivel varchar(255))";
            
            String sqlCrearTablaIngreso = "CREATE TABLE ingreso(\n" +
                    "vehiculo VARCHAR (50) NOT NULL,\n" +
                    "puesto INT NOT NULL UNIQUE,\n" +
                    "placa VARCHAR (50) NOT NULL ,\n" +
                    "horaEntrada INT NOT NULL,\n" +
                    "minutoEntrada INT NOT NULL,\n" +
                    "nombreCliente VARCHAR(50) NOT NULL,\n" +
                    "PRIMARY KEY (placa)\n" +
                    ");";
            
            String sqlCrearTablaSalida = "CREATE TABLE salida(\n" +
                    "vehiculoPlaca VARCHAR (50) UNIQUE NOT NULL,\n" +
                    "horaSalida INT NOT NULL,\n" +
                    "minutoSalida INT NOT NULL,\n" +
                    "FOREIGN KEY (vehiculoplaca)REFERENCES ingreso (placa)\n" +
                    ");";
            
            String sqlCrearTablaValores = "CREATE TABLE valores(\n" +
                    "tipo VARCHAR (50) NOT NULL,\n" +
                    "valor int NOT NULL ,\n" +
                    "PRIMARY KEY (tipo)\n" +
                    ");";
            
            String sqlCrearTablaFactura = "CREATE TABLE factura(\n" +
                "codigoFactura INT AUTO_INCREMENT NOT NULL, \n" +
                "vehiculo VARCHAR (50) NOT NULL, \n" +
                "nombreCliente VARCHAR(50) NOT NULL, \n" +
                "placa VARCHAR (50) NOT NULL , \n" +
                "horaEntrada INT NOT NULL, \n" +
                "minutoEntrada INT NOT NULL, \n" +
                "horaSalida INT NOT NULL, \n" +
                "minutoSalida INT NOT NULL,\n" +
                "valorHora INT NOT NULL,\n" +
                "horas INT NOT NULL,\n" +
                "total INT NOT NULL,\n" +
                "codigoEmpleado INT NOT NULL,\n" +
                "FOREIGN KEY (codigoEmpleado)REFERENCES usuarios (codigo) ,\n" +
                "PRIMARY KEY (codigoFactura)\n" +
                "\n" +
                ");";
            
            String sqlInsert = "INSERT INTO usuarios VALUES(0,'admin','admin@gmail.com','admin',1)";
            Statement statementTabla = connectionBD.createStatement();
            statementTabla.executeUpdate(sqlCrearTablaUsuario);
            statementTabla.executeUpdate(sqlCrearTablaIngreso);
            statementTabla.executeUpdate(sqlCrearTablaSalida);
            statementTabla.executeUpdate(sqlCrearTablaValores);
            statementTabla.executeUpdate(sqlCrearTablaFactura);
            statementTabla.executeUpdate(sqlInsert);
            System.out.println("Tablas creadas correctamente.");

            // Cerrar conexiones
            statementTabla.close();
            connectionBD.close();
            statementBD.close();
            connection.close();
               
                base.setNombre(cNombreBD.getText());
                base.setUsuario(cnombreUsuario.getText());
                base.setContra(cContraseña.getText());
                new CrearArchivo(base);
            
        } catch (Exception a) {
            a.printStackTrace();
        }
        
        dispose();
        }
        
       
        } else { JOptionPane.showMessageDialog(null, "Escribe el usuario");}  
        }else {JOptionPane.showMessageDialog(null, "Escribe el nombre de la base de datos");}    
        

            }});
        

        cancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancelado = true;
                dispose();
            }          
        });

        setTitle("Crear base de datos");
        setSize(300, 300);
        setLocationRelativeTo(null);
        setVisible(true);

    }
}


