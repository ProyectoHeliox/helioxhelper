/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.proyectoHeliox.helper.persistencia;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Define y crea la conexión a la base de datos con el conector Derby.
 *
 * @author Mariana
 */
public class DerbyDB {

    private static Connection conn = null;

    public static Connection getConexion() throws SQLException, IOException {
//        try {
//            Runtime.getRuntime().exec( "chmod 777 /home/opt/HelioxAsistente/app/HelioxHelper.UI.jar" );
//            System.out.println("chmod 777 /home/opt/HelioxAsistente/app/HelioxHelper.UI.jar" );
//        } catch (IOException ex) {
//            Logger.getLogger(DerbyDB.class.getName()).log(Level.SEVERE, null, ex);
//        }
        System.out.println("Derby Conection ready...");
        String url = "jdbc:derby:helioxasistente;create=true;";
        String driver = "org.apache.derby.jdbc.EmbeddedDriver";

        if (conn == null) {
            try {
                Class.forName(driver);
                conn = DriverManager.getConnection(url);
                System.out.println("Conectado a la base de datos");
            } catch (SQLException ex) {
                throw new SQLException(ex);
            } catch (ClassNotFoundException ex) {
                throw new ClassCastException(ex.getMessage());
            }
        }
        return conn;
    }

    public static void cerrar() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }

}
