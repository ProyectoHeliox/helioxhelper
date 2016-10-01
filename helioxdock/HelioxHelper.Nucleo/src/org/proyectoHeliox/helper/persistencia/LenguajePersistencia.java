/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.proyectoHeliox.helper.persistencia;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.proyectoHeliox.helper.negocio.DefaultValues;
import org.proyectoHeliox.helper.negocio.UnizipUtility;

/**
 * Define e implementa los metodos de acceso a datos de la entidad lenguaje.
 *
 * @author Mariana Garcia
 */
public class LenguajePersistencia {

    public void agregarLenguaje(String nombre, String ruta) throws SQLException, IOException {
        try {
            PreparedStatement ps = null;
            Connection conn = DerbyDB.getConexion();
            ps = conn.prepareStatement("INSERT INTO Lenguaje (nombre, icono) VALUES (?,?)");
            ps.setString(1, nombre);
            ps.setString(2, ruta);
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex);
        }
    }

    public void eliminarLenguaje(int id) throws SQLException, IOException {
        try {
            PreparedStatement ps = null;
            Connection conn = DerbyDB.getConexion();
            ps = conn.prepareStatement("DELETE from Lenguaje WHERE id = ?");
            ps.setInt(1, id);
            int res = ps.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex);
        }
    }

    public void editarLenguaje(String nombre, int id) throws SQLException, IOException {
        try {
            PreparedStatement ps = null;
            Connection conn = DerbyDB.getConexion();
            ps = conn.prepareStatement("UPDATE Lenguaje SET nombre = ? WHERE id = ?");
            ps.setString(1, nombre);
            ps.setInt(2, id);
            int res = ps.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex);
        }
    }

    public ResultSet consultarLenguaje(int id) throws SQLException, IOException {
        ResultSet res;
        try {
            PreparedStatement ps = null;
            Connection conn = DerbyDB.getConexion();
            ps = conn.prepareStatement("SELECT * FROM LENGUAJE WHERE id = ?");
            ps.setInt(1, id);
            res = ps.executeQuery();
        } catch (SQLException ex) {
            throw new SQLException(ex);
        }
        return res;
    }

    public ResultSet listadoLenguajes() throws SQLException, IOException {
        ResultSet res;
        try {
            PreparedStatement ps = null;
            Connection conn = DerbyDB.getConexion();
            ps = conn.prepareStatement("SELECT * FROM LENGUAJE");
            res = ps.executeQuery();
        } catch (SQLException ex) {
            throw new SQLException(ex);
        }
        return res;
    }

    public void crearTablas() throws SQLException, IOException {
        //Crea las tablas  si no existen.
        Connection conn = DerbyDB.getConexion();
        ResultSet tables;
        DatabaseMetaData dbmd = conn.getMetaData();
        Statement s = conn.createStatement();
        tables = dbmd.getTables(conn.getCatalog(), "APP", "LENGUAJE", new String[] {"LENGUAJE"});
        System.out.println(tables);

        if (!tables.next()) {

          try {
            s.execute("create table lenguaje ("
                    + "   id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                    + "  nombre varchar(50),"
                    + "  icono varchar(250),"
                    + "    CONSTRAINT pk_lenguaje PRIMARY KEY (id)"
                    + ")");
            System.out.println("Se ha creado la tabla Lenguaje");
            s.execute("create table boton ("
                    + "   id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                    + "   rutaicon varchar(250),"
                    + "   rutaejecutable varchar(250),"
                    + "   rutaaudio varchar(250),"
                    + "   descripcion varchar (250),"
                    + "    CONSTRAINT pk_boton PRIMARY KEY (id),"
                    + "   idLenguaje integer"
                    + ")");
            System.out.println("Se ha creado la tabla boton");
            s.execute("ALTER TABLE boton\n"
                    + "ADD CONSTRAINT fk_lenguaje\n"
                    + "FOREIGN KEY (idlenguaje)\n"
                    + "REFERENCES lenguaje(id)");
            System.out.println("Llave foranea agregada");
          } catch(SQLException ignore){
              if(!ignore.getMessage().contains("already exists"))
              throw ignore;
          }

//            DefaultValues values = new DefaultValues();
//            values.agregarLenguajes();
//            values.agregarBotones();

//            String zipFilePath = System.getProperty("user.dir") + File.separatorChar + "HelioxHelper.UI.jar";
//            String destDirectory = System.getProperty("user.dir");
//            UnizipUtility unzipper = new UnizipUtility();
//            try {
//                unzipper.unzip(zipFilePath, destDirectory);
//            } catch (Exception ex) {
//                // some errors occurred
//                ex.printStackTrace();
//            }
        } else {
            System.out.println("Tablas ya existentes");

        }
        
    }
}
