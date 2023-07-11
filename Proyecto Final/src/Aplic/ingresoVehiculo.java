package Aplic;


import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JDialog;
import javax.swing.*;

public class ingresoVehiculo extends JDialog {

    //creacion de las variables graficas
    JLabel eVehiculo, eFormatoPlaca;
    JButton bMostrar, bMostrarTodos, bValidar, bCancelar;
    JScrollPane scroll;
    JTextArea areaVehiculos;
    JTextField cFormatoPlaca;
    JComboBox comboTipoVehiculo;
    String formato;
    int contadorCarro = 0;
    int contadorMoto = 0;
    int contadorBicicleta = 0;

    public ingresoVehiculo(Frame e, boolean modal, String nombreBD, String nombre) {
        super(e, modal);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        this.getContentPane().add(panel);

        //instancia de las variables graficas
        //JLabels
        eVehiculo = new JLabel("Vehiculo");
        eFormatoPlaca = new JLabel("V-P-PLACA-HE-ME");

        //JTextfields
        cFormatoPlaca = new JTextField(15);
        
        //JButton
        bMostrar = new JButton("Mostrar");
        bMostrarTodos = new JButton("Mostrar todos");
        bValidar = new JButton("Validar");
        bCancelar = new JButton("Cancelar");

        //JCombobox
        String tipoVehiculo[] = {"Carro", "Moto", "Bicicleta"};
        comboTipoVehiculo = new JComboBox(tipoVehiculo);

        //JTextArea
        
        areaVehiculos = new JTextArea(1, 30);
        scroll = new JScrollPane(areaVehiculos, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        eVehiculo.setBounds(20, 20, 60, 15);
        comboTipoVehiculo.setBounds(90, 20, 90, 20);
        bMostrar.setBounds(200, 20, 110, 20);
        bMostrarTodos.setBounds(330, 20, 130, 20);
        scroll.setBounds(80, 60, 380, 100);
        eFormatoPlaca.setBounds(20, 180, 130 , 20);
        cFormatoPlaca.setBounds(140, 180, 120, 20);
        bValidar.setBounds(290, 180, 90, 20);
        bCancelar.setBounds(200, 220, 90, 20);

        panel.add(eVehiculo);
        panel.add(comboTipoVehiculo);
        panel.add(bMostrar);
        panel.add(bMostrarTodos);
        panel.add(scroll);
        panel.add(eFormatoPlaca);
        panel.add(cFormatoPlaca);
        panel.add(bValidar);
        panel.add(bCancelar);
        
       
        StringBuilder sb = new StringBuilder();
        
        
        bMostrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                 
                
                if(comboTipoVehiculo.getSelectedItem().toString() == "Carro"){
                    sb.setLength(0);
                    areaVehiculos.setText(sb.toString());
                           
                    String url = "jdbc:mysql://localhost/" + nombreBD;
                    // conexion para sacar la cantidad de puestos de carros ocupados
                      try {
                                        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                                        Connection con = DriverManager.getConnection(url, "root", "");

                                        PreparedStatement preparedStatement;

                                        preparedStatement = con.prepareStatement("SELECT COUNT(*) FROM ingreso where puesto > 0 and puesto < 11"); 
                                        ResultSet resultSet = preparedStatement.executeQuery();
                                       
                                         if(resultSet.next())
                                          {
                                         contadorCarro = resultSet.getInt(1);   
                                           }
                                    }catch (Exception r) {
                                        JOptionPane.showMessageDialog(null,
                                                "Error al contar: " + r.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                                    } 
                        Object [] puestos = new Object [contadorCarro]; 
                        
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                        Connection con = DriverManager.getConnection(url, "root", "");
                        
                        PreparedStatement preparedStatement;
                        
                        preparedStatement = con.prepareStatement("SELECT puesto FROM ingreso WHERE puesto > 0 and puesto < 11");
                        ResultSet resultSet = preparedStatement.executeQuery();
                        int i = 0;
                                
                       while(resultSet.next()) {
                         puestos[i] = resultSet.getString("puesto");
                         i++;
                       }
                      
                        preparedStatement.close();
                        con.close();
                        
                    } catch (Exception r) {
                        JOptionPane.showMessageDialog(null,
                                "Error al buscar los puestos del carro: " + r.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    
                      for (int i = 1; i <= 10; i++) {
                        boolean encontrado = false;
                     for (int j = 0; j < contadorCarro; j++) {
                        if ( Integer.parseInt(puestos[j].toString()) == i) {
                    sb.append("c").append("    ");
                    encontrado = true;
                    break;
                                }
                            }if (!encontrado) {
                sb.append(i).append("    ");
                   }
           
                   }
                   areaVehiculos.setText(sb.toString());     
                    
                }
                
                
                
                 if(comboTipoVehiculo.getSelectedItem().toString() == "Moto"){
                       sb.setLength(0);
                       areaVehiculos.setText(sb.toString());
                     
                       sb.append("\n");
                       String url = "jdbc:mysql://localhost/" + nombreBD;
                    // conexion para sacar la cantidad de puestos de carros ocupados
                      try {
                                        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                                        Connection con = DriverManager.getConnection(url, "root", "");

                                        PreparedStatement preparedStatement;

                                        preparedStatement = con.prepareStatement("SELECT COUNT(*) FROM ingreso where puesto > 10 and puesto < 21"); 
                                        ResultSet resultSet = preparedStatement.executeQuery();
                                       
                                         if(resultSet.next())
                                          {
                                         contadorMoto = resultSet.getInt(1);   
                                           }
                                    }catch (Exception r) {
                                        JOptionPane.showMessageDialog(null,
                                                "Error al contar: " + r.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                                    } 
                        Object [] puestos = new Object [contadorMoto]; 
                        
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                        Connection con = DriverManager.getConnection(url, "root", "");
                        
                        PreparedStatement preparedStatement;
                        
                        preparedStatement = con.prepareStatement("SELECT puesto FROM ingreso WHERE puesto > 10 and puesto < 21");
                        ResultSet resultSet = preparedStatement.executeQuery();
                        int i = 0;
                                
                       while(resultSet.next()) {
                         puestos[i] = resultSet.getString("puesto");
                         i++;
                       }
                      
                        preparedStatement.close();
                        con.close();
                        
                    } catch (Exception r) {
                        JOptionPane.showMessageDialog(null,
                                "Error al buscar los puestos de las moto: " + r.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    
                      for (int i = 11; i <= 20; i++) {
                        boolean encontrado = false;
                     for (int j = 0; j < contadorMoto; j++) {
                        if ( Integer.parseInt(puestos[j].toString()) == i) {
                    sb.append("m").append("    ");
                    encontrado = true;
                    break;
                                }
                            }if (!encontrado) {
                sb.append(i).append("    ");
                   }
           
                   }
                   areaVehiculos.setText(sb.toString());  
                     
                }
                
                 if(comboTipoVehiculo.getSelectedItem().toString() == "Bicicleta"){
                        sb.setLength(0);
                        areaVehiculos.setText(sb.toString());
                        sb.append("\n");
                         sb.append("\n");
                        String url = "jdbc:mysql://localhost/" + nombreBD;
                    // conexion para sacar la cantidad de puestos de carros ocupados
                     
                      try {
                                        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                                        Connection con = DriverManager.getConnection(url, "root", "");

                                        PreparedStatement preparedStatement;

                                        preparedStatement = con.prepareStatement("SELECT COUNT(*) FROM ingreso where puesto > 20 and puesto < 26"); 
                                        ResultSet resultSet = preparedStatement.executeQuery();
                                       
                                         if(resultSet.next())
                                          {
                                         contadorBicicleta = resultSet.getInt(1);   
                                           }
                                    }catch (Exception r) {
                                        JOptionPane.showMessageDialog(null,
                                                "Error al contar: " + r.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                                    } 
                        Object [] puestos = new Object [contadorBicicleta]; 
                        
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                        Connection con = DriverManager.getConnection(url, "root", "");
                        
                        PreparedStatement preparedStatement;
                        
                        preparedStatement = con.prepareStatement("SELECT puesto FROM ingreso WHERE puesto > 20 and puesto < 26");
                        ResultSet resultSet = preparedStatement.executeQuery();
                        int i = 0;
                                
                       while(resultSet.next()) {
                         puestos[i] = resultSet.getString("puesto");
                         i++;
                       }
                      
                        preparedStatement.close();
                        con.close();
                        
                    } catch (Exception r) {
                        JOptionPane.showMessageDialog(null,
                                "Error al buscar los puestos de la bicicleta: " + r.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    } 
                     for (int i = 20; i <= 25; i++) {
                     boolean encontrado = false;
                     for (int j = 0; j < contadorBicicleta; j++) {
                     if ( Integer.parseInt(puestos[j].toString()) == i) {
                     sb.append("b").append("    ");
                     encontrado = true;
                       break;                                          }
                     }if (!encontrado) {
                     sb.append(i).append("    ");
                     }        
                     }
                     areaVehiculos.setText(sb.toString()); 
                  
                                                                                  }
 
            }
        });

        bMostrarTodos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              sb.setLength(0);
              areaVehiculos.setText(sb.toString());

              String url = "jdbc:mysql://localhost/" + nombreBD;
                  //carros
                  try {
                                        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                                        Connection con = DriverManager.getConnection(url, "root", "");

                                        PreparedStatement preparedStatement;

                                        preparedStatement = con.prepareStatement("SELECT COUNT(*) FROM ingreso where puesto > 0 and puesto < 11"); 
                                        ResultSet resultSet = preparedStatement.executeQuery();
                                       
                                         if(resultSet.next())
                                          {
                                         contadorCarro = resultSet.getInt(1);   
                                           }
                                    }catch (Exception r) {
                                        JOptionPane.showMessageDialog(null,
                                                "Error al contar: " + r.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                                    } 
                        Object [] puestosCarro = new Object [contadorCarro]; 
                        
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                        Connection con = DriverManager.getConnection(url, "root", "");
                        
                        PreparedStatement preparedStatement;
                        
                        preparedStatement = con.prepareStatement("SELECT puesto FROM ingreso WHERE puesto > 0 and puesto < 11");
                        ResultSet resultSet = preparedStatement.executeQuery();
                        int i = 0;
                                
                       while(resultSet.next()) {
                         puestosCarro[i] = resultSet.getString("puesto");
                         i++;
                       }
                      
                        preparedStatement.close();
                        con.close();
                        
                    } catch (Exception r) {
                        JOptionPane.showMessageDialog(null,
                                "Error al buscar los puestos del carro: " + r.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    
                      for (int i = 1; i <= 10; i++) {
                        boolean encontrado = false;
                     for (int j = 0; j < contadorCarro; j++) {
                        if ( Integer.parseInt(puestosCarro[j].toString()) == i) {
                    sb.append("c").append("    ");
                    encontrado = true;
                    break;
                                }
                            }if (!encontrado) {
                sb.append(i).append("    ");
                   }
           
                   }
                   areaVehiculos.setText(sb.toString()); 
                   
                   //motos
                 sb.append("\n");
                 try {
                                        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                                        Connection con = DriverManager.getConnection(url, "root", "");

                                        PreparedStatement preparedStatement;

                                        preparedStatement = con.prepareStatement("SELECT COUNT(*) FROM ingreso where puesto > 10 and puesto < 21"); 
                                        ResultSet resultSet = preparedStatement.executeQuery();
                                       
                                         if(resultSet.next())
                                          {
                                         contadorMoto = resultSet.getInt(1);   
                                           }
                                    }catch (Exception r) {
                                        JOptionPane.showMessageDialog(null,
                                                "Error al contar: " + r.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                                    } 
                        Object [] puestosMoto = new Object [contadorMoto]; 
                        
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                        Connection con = DriverManager.getConnection(url, "root", "");
                        
                        PreparedStatement preparedStatement;
                        
                        preparedStatement = con.prepareStatement("SELECT puesto FROM ingreso WHERE puesto > 10 and puesto < 21");
                        ResultSet resultSet = preparedStatement.executeQuery();
                        int i = 0;
                                
                       while(resultSet.next()) {
                         puestosMoto[i] = resultSet.getString("puesto");
                         i++;
                       }
                      
                        preparedStatement.close();
                        con.close();
                        
                    } catch (Exception r) {
                        JOptionPane.showMessageDialog(null,
                                "Error al buscar los puestos de la moto: " + r.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                     
                      for (int i = 11; i <= 20; i++) {
                        boolean encontrado = false;
                     for (int j = 0; j < contadorMoto; j++) {
                        if ( Integer.parseInt(puestosMoto[j].toString()) == i) {
                    sb.append("m").append("    ");
                    encontrado = true;
                    break;
                                }
                            }if (!encontrado) {
                sb.append(i).append("    ");
                   }
           
                   }
                   areaVehiculos.setText(sb.toString()); 
                
                //bicicletas        
                    sb.append("\n");
                      try {
                                        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                                        Connection con = DriverManager.getConnection(url, "root", "");

                                        PreparedStatement preparedStatement;

                                        preparedStatement = con.prepareStatement("SELECT COUNT(*) FROM ingreso where puesto > 20 and puesto < 26"); 
                                        ResultSet resultSet = preparedStatement.executeQuery();
                                       
                                         if(resultSet.next())
                                          {
                                         contadorBicicleta = resultSet.getInt(1);   
                                           }
                                    }catch (Exception r) {
                                        JOptionPane.showMessageDialog(null,
                                                "Error al contar: " + r.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                                    } 
                        Object [] puestosBicicleta = new Object [contadorBicicleta]; 
                        
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                        Connection con = DriverManager.getConnection(url, "root", "");
                        
                        PreparedStatement preparedStatement;
                        
                        preparedStatement = con.prepareStatement("SELECT puesto FROM ingreso WHERE puesto > 20 and puesto < 26");
                        ResultSet resultSet = preparedStatement.executeQuery();
                        int i = 0;
                                
                       while(resultSet.next()) {
                         puestosBicicleta[i] = resultSet.getString("puesto");
                         i++;
                       }
                      
                        preparedStatement.close();
                        con.close();
                        
                    } catch (Exception r) {
                        JOptionPane.showMessageDialog(null,
                                "Error al buscar los puestos de la bicicleta: " + r.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    } 
                     for (int i = 20; i <= 25; i++) {
                     boolean encontrado = false;
                     for (int j = 0; j < contadorBicicleta; j++) {
                     if ( Integer.parseInt(puestosBicicleta[j].toString()) == i) {
                     sb.append("b").append("    ");
                     encontrado = true;
                       break;                                          }
                     }if (!encontrado) {
                     sb.append(i).append("    ");
                     }        
                     }
                     areaVehiculos.setText(sb.toString());   
                   
                   
                
                
            }
        });

        bValidar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                formato = cFormatoPlaca.getText();

                if (cFormatoPlaca.getText().length() != 0) {
                    if (!formato.substring(0, 1).equals("c") && !formato.substring(0, 1).equals("m") && !formato.substring(0, 1).equals("b")) {

                        JOptionPane.showMessageDialog(null, "El tipo de vehiculo no coincide");

                    } else if (formato.substring(0, 1).equals("c")) {
                        //formato de tamanio 16 para carro por si el puesto solo es de un digito     
                        if (formato.length() == 16) {
                            if (formato.substring(0, 1).equals("c")) {
                                if (Character.isDigit(formato.charAt(2))) {
                                    if (Integer.parseInt(formato.substring(2, 3)) > 0 && Integer.parseInt(formato.substring(2, 3)) < 11) {
                                        if (validarCarro(formato)) {

                                            validarIngreso vali = new validarIngreso(null, true, nombre, formato, nombreBD);
                                            cFormatoPlaca.setText("");
                                        }
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Los puestos para los carros son del numero 1 al 10");
                                    };
                                } else {
                                    JOptionPane.showMessageDialog(null, "Verifica que hayas escrito el formato correctamente");
                                };
                            } else {
                                JOptionPane.showMessageDialog(null, "El tipo de vehiculo no coincide");
                            };
                        } else if (formato.length() == 17) {
                            //por si el puesto de carro es de 2 digitos    
                            if (Character.isDigit(formato.charAt(2)) && Character.isDigit(formato.charAt(3))) {
                                if (Integer.parseInt(formato.substring(2, 4)) > 0 && Integer.parseInt(formato.substring(2, 4)) < 11) {
                                    if (validarCarro(formato)) {

                                        validarIngreso vali = new validarIngreso(null, true, nombre, formato, nombreBD);
                                        cFormatoPlaca.setText("");
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(null, "Los puestos para los carros son del numero 1 al 10");
                                };
                            } else {
                                JOptionPane.showMessageDialog(null, "Verifica que hayas escrito el formato correctamente");
                            };
                        } else {
                            JOptionPane.showMessageDialog(null, "Escribe el formato de manera correcta");
                        };

                    } else if (formato.substring(0, 1).equals("m")) {

                        if (formato.length() == 17) {
                            if (formato.substring(0, 1).equals("m")) {
                                if (Character.isDigit(formato.charAt(2)) && Character.isDigit(formato.charAt(3))) {
                                    if (Integer.parseInt(formato.substring(2, 4)) > 10 && Integer.parseInt(formato.substring(2, 4)) < 21) {
                                        if (validarMoto(formato)) {

                                            validarIngreso vali = new validarIngreso(null, true, nombre, formato, nombreBD);
                                            cFormatoPlaca.setText("");
                                        }
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Los puestos para las motos son del numero 11 al 20");
                                    };
                                } else {
                                    JOptionPane.showMessageDialog(null, "Verifica que hayas escrito el formato correctamente");
                                };
                            } else {
                                JOptionPane.showMessageDialog(null, "El tipo de vehiculo no coincide");
                            };
                        } else {
                            JOptionPane.showMessageDialog(null, "Escribe el formato de manera correcta");
                        };

                    } else if (formato.substring(0, 1).equals("b")) {

                        if (formato.length() == 15) {
                            if (formato.substring(0, 1).equals("b")) {
                                if (Character.isDigit(formato.charAt(2)) && Character.isDigit(formato.charAt(3))) {
                                    if (Integer.parseInt(formato.substring(2, 4)) > 20 && Integer.parseInt(formato.substring(2, 4)) < 26) {
                                        if (validarBicicleta(formato)) {

                                            validarIngreso vali = new validarIngreso(null, true, nombre, formato, nombreBD);
                                            cFormatoPlaca.setText("");
                                        }
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Los puestos para las bicicletas son del numero 21 al 25");
                                    };
                                } else {
                                    JOptionPane.showMessageDialog(null, "Verifica que hayas escrito el formato correctamente");
                                };
                            } else {
                                JOptionPane.showMessageDialog(null, "El tipo de vehiculo no coincide");
                            };
                        } else {
                            JOptionPane.showMessageDialog(null, "Escribe el formato de manera correcta");
                        };
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Escribe la placa");
                };

            }
        });

        bCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                dispose();

            }
        });

       

        setTitle("Ingreso Vehiculo, Usuario " + nombre);
        setSize(500, 300);
        setLocationRelativeTo(null);
        setVisible(true);

    }

    public boolean validarCarro(String formato) {
        boolean vali = false;

        if (formato.length() == 16) {
            String letras = formato.substring(4, 7);
            String numeros = formato.substring(7, 10);
            String hora = formato.substring(11, 13);
            String minuto = formato.substring(14, 16);

            if (formato.matches(".*[ ].*")) {
                JOptionPane.showMessageDialog(null, "El formato no puede tener espacios");
                return false;
            }

            if (letras.matches(".*[0-9].*")) {
                JOptionPane.showMessageDialog(null, "Los 3 primero caracteres de la placa deben ser letras");
                return false;
            }

            if (!numeros.matches("^[^a-zA-Z]*$")) {
                JOptionPane.showMessageDialog(null, "Los ultimos 3 caracteres de la placa deben ser numeros");
                return false;
            }

            if (Integer.parseInt(hora) >= 0 && 23 >= Integer.parseInt(hora)) {
                if (Integer.parseInt(minuto) >= 0 && Integer.parseInt(minuto) < 60) {
                    vali = true;
                } else {
                    JOptionPane.showMessageDialog(null, "Formato de hora incorrecto");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Formato de hora incorrecto");
            }

            //validar el formato con carro de puesto 2 caracteres
        } else if (formato.length() == 17) {

            String letras = formato.substring(5, 8);
            String numeros = formato.substring(8, 11);
            String hora = formato.substring(12, 14);
            String minuto = formato.substring(15, 17);

            if (formato.matches(".*[ ].*")) {
                JOptionPane.showMessageDialog(null, "El formato no puede tener espacios");
                return false;
            }

            if (letras.matches(".*[0-9].*")) {
                JOptionPane.showMessageDialog(null, "Los 3 primero caracteres de la placa deben ser letras");
                return false;
            }

            if (!numeros.matches("^[^a-zA-Z]*$")) {
                JOptionPane.showMessageDialog(null, "Los ultimos 3 caracteres de la placa deben ser numeros");
                return false;
            }

            if (Integer.parseInt(hora) >= 0 && 23 >= Integer.parseInt(hora)) {
                if (Integer.parseInt(minuto) >= 0 && Integer.parseInt(minuto) < 60) {
                    vali = true;
                } else {
                    JOptionPane.showMessageDialog(null, "Formato de hora incorrecto");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Formato de hora incorrecto");
            }

        }

        return vali;
    }

    public boolean validarMoto(String formato) {
        boolean vali = false;
        String letras = formato.substring(5, 8);
        String numeros = formato.substring(8, 10);
        String ultimo = formato.substring(10, 11);
        String hora = formato.substring(12, 14);
        String minuto = formato.substring(15, 17);

        if (formato.matches(".*[ ].*")) {
            JOptionPane.showMessageDialog(null, "El formato no puede tener espacios");
            return false;
        }

        if (letras.matches(".*[0-9].*")) {
            JOptionPane.showMessageDialog(null, "Los 3 primero caracteres de la placa deben ser letras");
            return false;
        }

        if (!numeros.matches("^[^a-zA-Z]*$")) {
            JOptionPane.showMessageDialog(null, "Revisa que tu placa este escrita en formato de moto: xxx12x");
            return false;
        }
        if (ultimo.matches(".*[0-9].*")) {
            JOptionPane.showMessageDialog(null, "Revisa que tu placa este escrita en formato de moto: xxx12x");
            return false;
        }

        if (Integer.parseInt(hora) >= 0 && 23 >= Integer.parseInt(hora)) {
            if (Integer.parseInt(minuto) >= 0 && Integer.parseInt(minuto) < 60) {
                vali = true;
            } else {
                JOptionPane.showMessageDialog(null, "Formato de hora incorrecto");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Formato de hora incorrecto");
        }

        return vali;
    }

    public boolean validarBicicleta(String formato) {
        boolean vali = false;
        String numeros = formato.substring(5, 9);
        String hora = formato.substring(10, 12);
        String minuto = formato.substring(13, 15);

        if (formato.matches(".*[ ].*")) {
            JOptionPane.showMessageDialog(null, "El formato no puede tener espacios");
            return false;
        }

        if (!numeros.matches("^[^a-zA-Z]*$")) {
            JOptionPane.showMessageDialog(null, "Revisa que tu placa este escrita en formato bicicleta 0000");
            return false;
        }

        if (Integer.parseInt(hora) >= 0 && 23 >= Integer.parseInt(hora)) {
            if (Integer.parseInt(minuto) >= 0 && Integer.parseInt(minuto) < 60) {
                vali = true;
            } else {
                JOptionPane.showMessageDialog(null, "Formato de hora incorrecto");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Formato de hora incorrecto");
        }

        return vali;
    }
    
 
}
