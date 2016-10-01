/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.proyectoHeliox.helper.negocio;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.proyectoHeliox.helper.entidades.Boton;
import org.proyectoHeliox.helper.entidades.Lenguaje;
import org.proyectoHeliox.helper.persistencia.BotonPersistencia;
import org.proyectoHeliox.helper.persistencia.LenguajePersistencia;

/**
 * Operaciones del CRUD para botones y lenguaje.
 * @author Mariana García
 */
public class NegocioHeliox {
    
     public void agregarLenguaje(Lenguaje l) throws SQLException, IOException{
        LenguajePersistencia lp = new LenguajePersistencia();
        lp.agregarLenguaje(l.getNombre(), l.getIcono());
    }
     
    public void eliminarLenguaje(Lenguaje l) throws SQLException, IOException{
        LenguajePersistencia lp = new LenguajePersistencia();
        lp.eliminarLenguaje(l.getId());
    }
    
    public void editarLenguaje(Lenguaje l) throws SQLException, IOException{
        LenguajePersistencia lp = new LenguajePersistencia();
        lp.editarLenguaje(l.getNombre(), l.getId());
    }
    
    public void agregarBoton(Boton b, int id) throws SQLException, IOException{
        BotonPersistencia bp = new BotonPersistencia();
        bp.agregarBoton(b.getRutaIcono(), b.getDescripcion(), b.getRutaEjecutable(), b.getRutaAudio(), id);
    }
    
    public void eliminarBoton(Boton b) throws SQLException, IOException{
        BotonPersistencia bp = new BotonPersistencia();
        bp.eliminarBoton(b.getId());
    }
    
    public void editarBoton(Boton b) throws SQLException, IOException{
        BotonPersistencia bp = new BotonPersistencia();
        bp.editarBoton(b.getRutaIcono(), b.getDescripcion(), b.getRutaEjecutable(), b.getRutaAudio(), b.getId());
    }
    
    public ArrayList<Lenguaje> listadoLenguajes() throws SQLException, IOException{
        ArrayList<Lenguaje> lenguaje = new ArrayList<>();
        LenguajePersistencia lp = new LenguajePersistencia();
        ResultSet rs = lp.listadoLenguajes();
        try{
            while(rs.next()){
                Lenguaje l = new Lenguaje();
                l.setId(rs.getInt("id"));
                l.setNombre(rs.getString("nombre"));
                lenguaje.add(l);
            }
        }catch(Exception ex){
            System.out.println("Error: "+ex);
        }
        return lenguaje;
    }
    
    public ArrayList<Boton> listadoBotones() throws SQLException, IOException{
        ArrayList<Boton> botones = new ArrayList<>();
       BotonPersistencia bp = new BotonPersistencia();
        ResultSet rs = bp.listadoBotones();
        try{
            while(rs.next()){
                Boton b = new Boton();
                b.setId(rs.getInt("id"));
                b.setRutaAudio(rs.getString("rutaaudio"));
                b.setDescripcion(rs.getString("descripcion"));
                b.setRutaEjecutable(rs.getString("rutaejecutable"));
                b.setRutaIcono(rs.getString("rutaicon"));
                botones.add(b);
            }
        }catch(Exception ex){
            System.out.println("Error: "+ex);
        }
        return botones;
    }
    
    public ArrayList<Boton> listadoBotonesLenguaje(int id) throws SQLException, IOException{
        ArrayList<Boton> botones = new ArrayList<>();
       BotonPersistencia bp = new BotonPersistencia();
        ResultSet rs = bp.listadoBotonesLenguaje(id);
        try{
            while(rs.next()){
                Boton b = new Boton();
                b.setId(rs.getInt("id"));
                b.setRutaAudio(rs.getString("rutaaudio"));
                b.setDescripcion(rs.getString("descripcion"));
                b.setRutaEjecutable(rs.getString("rutaejecutable"));
                b.setRutaIcono(rs.getString("rutaicon"));
                botones.add(b);
            }
        }catch(Exception ex){
            System.out.println("Error: "+ex);
        }
        return botones;
    }
    
    public void crearTablas() throws SQLException, IOException{
        LenguajePersistencia lp = new LenguajePersistencia();
        lp.crearTablas();
    }
    
}
