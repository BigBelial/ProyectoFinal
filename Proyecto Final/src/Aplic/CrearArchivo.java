/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Aplic;

import java.io.FileWriter;
import java.io.PrintWriter;

/**
 *
 * @author Root
 */
public class CrearArchivo {
    
    public CrearArchivo(Base base){
    try
    { 
        FileWriter f = new FileWriter("InfoBase.txt");
        PrintWriter pw = new PrintWriter(f);
        pw.print(base.getNombre()+"/");
        pw.print(base.getUsuario() +"/");
        pw.print(base.getContra());
        pw.close();
        
    }
    catch(Exception e)
    {    
    }
    
    
    }
   
}