package Aplic;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Main extends JFrame {

    String nombre, nombreBD;
    JMenu base, login, usuario, vehiculo;
    JMenuItem crearBase, borrarBase; // items de bases
    JMenuItem iniciar, cerrar; // items de login
    JMenuItem crear, modificar, visualizar;  //items de usuario
    JMenuItem ingreso, salida, buscarRegistro, modificarRegistro, buscarFactura, valorHora; // items de vehiculo
    //sesion
    boolean logeo = false;
    int nivel, codigo;

    public Main() {
        Container c = getContentPane();
        c.setLayout(new FlowLayout());

        JMenuBar barra = new JMenuBar();
        setJMenuBar(barra);
        //JMenus
        base = new JMenu("Bases de datos");
        login = new JMenu("Login");
        usuario = new JMenu("Usuario");
        vehiculo = new JMenu("Vehiculos");
        //
        crearBase = new JMenuItem("Crear Base de Datos");
        borrarBase = new JMenuItem("Eliminar base de datos");
        //
        iniciar = new JMenuItem("Iniciar Sesion");
        cerrar = new JMenuItem("Cerrar Sesion");
        //
        crear = new JMenuItem("Crear");
        modificar = new JMenuItem("Modificar");
        visualizar = new JMenuItem("Visualizar/Eliminar");
        //
        ingreso = new JMenuItem("Ingresar");
        salida = new JMenuItem("Salida");
        buscarRegistro = new JMenuItem("Buscar Registros");
        modificarRegistro = new JMenuItem("Modificar Registros");
        buscarFactura = new JMenuItem("Buscar Factura");
        valorHora = new JMenuItem("Valor Hora");
        // barra          
        barra.add(base);
        barra.add(login);
        barra.add(usuario);
        barra.add(vehiculo);
        // items de base
        base.add(crearBase);
        base.add(borrarBase);
        // items de login
        login.add(iniciar);
        login.add(cerrar);
        //items de usuario
        usuario.add(crear);
        usuario.add(modificar);
        usuario.add(visualizar);
        //items de vehiculo
        vehiculo.add(ingreso);
        vehiculo.add(salida);
        vehiculo.add(buscarRegistro);
        vehiculo.add(modificarRegistro);
        vehiculo.add(buscarFactura);
        vehiculo.add(valorHora);
        //

        //botones
        // boton bd
        crearBase.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                formularioBase fb = new formularioBase(null, true);
                if(fb.cancelado){
                }
                else{
                nombreBD = fb.nom;
                }

            }
        });
        borrarBase.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                eliminarBase eli = new eliminarBase(null, true, nombreBD);

            }
        });

        //botones login
        iniciar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(logeo == false){
                iniciarSesion ini = new iniciarSesion(null, true, nombreBD);
                
                    if (ini.logeo) {
                    logeo = ini.logeo;
                    nivel = ini.nivel;
                    codigo = ini.codigo;
                }
                if (ini.falla == false) {
                    logeo = false;
                }

                nombre = ini.nombre;
                
                } else { JOptionPane.showMessageDialog(null, "Primero cierra sesion");
                }
              }
        });

        cerrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                cerrarSesion cer = new cerrarSesion(null, true);

                logeo = false;

            }
        });

        // botones usuario
        crear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (logeo == true) {
                    if (nivel == 1) {
                        crearUsuario creo = new crearUsuario(null, true, nombreBD, nombre);
                    } else {
                        JOptionPane.showMessageDialog(null, "No eres administrador");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Debes iniciar sesion");
                }
            }
        });

        modificar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (logeo == true) {
                    if (nivel == 1) {
                        modificarUsuario modi = new modificarUsuario(null, true, nombreBD, nombre);
                    } else {
                        JOptionPane.showMessageDialog(null, "No eres administrador");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Debes iniciar sesion");
                }

            }
        });

        visualizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (logeo == true) {
                    if (nivel == 1) {
                        verUsuario ver = new verUsuario(null, true, nombreBD, nombre);
                    } else {
                        JOptionPane.showMessageDialog(null, "No eres administrador");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Debes iniciar sesion");
                }

            }
        });
        //botones vehiculo

        ingreso.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

               if(logeo == true){
               if(nivel ==1 || nivel ==2){ 
                ingresoVehiculo ingre = new ingresoVehiculo(null, true, nombreBD, nombre);
                } else  { JOptionPane.showMessageDialog(null, "No eres empleado");}
                } else  { JOptionPane.showMessageDialog(null, "Debes iniciar sesion");}

            }
        });

        salida.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if(logeo == true){
                if(nivel ==1 || nivel ==2){ 
                salidaVehiculo sal = new salidaVehiculo(null, true, nombreBD, nombre,codigo);
                } else  { JOptionPane.showMessageDialog(null, "No eres empleado");}
                } else  { JOptionPane.showMessageDialog(null, "Debes iniciar sesion");}

            }
        });

        buscarRegistro.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (logeo == true) {
                    if (nivel == 1) {
                        buscarRegistro bus = new buscarRegistro(null, true, nombreBD,nombre);
                    } else {
                        JOptionPane.showMessageDialog(null, "No eres administrador");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Debes iniciar sesion");
                }

            }
        });

        modificarRegistro.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (logeo == true) {
                   if (nivel == 1) {
                        modificarRegistro modR = new modificarRegistro(null, true, nombreBD,nombre);
                   } else {
                       JOptionPane.showMessageDialog(null, "No eres administrador");
                    }
               } else {
                    JOptionPane.showMessageDialog(null, "Debes iniciar sesion");
                }

            }
        });

        buscarFactura.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (logeo == true) {
                    if (nivel == 1 || nivel == 2 ||nivel == 3) {
                        buscarFactura factura = new buscarFactura(null, true, nombreBD, nombre);
                    } else {
                        JOptionPane.showMessageDialog(null, "No eres cliente");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Debes iniciar sesion");
                }

            }
        });

        valorHora.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (logeo == true) {
                    if (nivel == 1) {
                        valorHora valor = new valorHora(null, true, nombreBD, nombre);
                    } else {
                        JOptionPane.showMessageDialog(null, "No eres administrador");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Debes iniciar sesion");
                }

            }
        });

        //
        //Atributos para hacer visible la ventana y sus propiedades
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Parqueadero");
        setSize(300, 300);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }

}
