/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helioxhelper.ui;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


/**
 * Clase de utlidad para el manejo de archivos.
 *
 * @author Mariana García
 */
public class IOUtil {   
   private void unzipFile(){
       
       
   }  
    

    public void abrirPDF(String path) throws URISyntaxException, IOException {
        File file = new File(path);
        if (Desktop.isDesktopSupported()) {
            URI uri;
            uri = file.toURI();
            Desktop.getDesktop().open(file);
        } else {
            System.out.print("Desktop no esta soportado.");
        }
    }

    public void abrirAplicacion(String path) throws IOException {
        Process p = Runtime.getRuntime().exec(path);
    }

}
