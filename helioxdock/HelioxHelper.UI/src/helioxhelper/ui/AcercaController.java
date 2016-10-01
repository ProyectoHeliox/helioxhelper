/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helioxhelper.ui;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;

/**
 * FXML Controller class
 *
 * @author Mariana
 */
public class AcercaController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
     @FXML
    private Hyperlink linkProyecto;

    @FXML
    private Hyperlink linkInali;
    Process process;

    @FXML
    void openLinkInali(ActionEvent event) throws IOException, URISyntaxException {
//       process = Runtime.getRuntime().exec("firefox "+ linkInali.getText());
        if (Desktop.isDesktopSupported()){
            Desktop desktop = Desktop.getDesktop();
            desktop.browse(new URI(linkInali.getText()));
        }
    }

    @FXML
    void openLinkProyecto(ActionEvent event) throws IOException, URISyntaxException {
//       process = Runtime.getRuntime().exec("firefox "+ linkProyecto.getText());
         if (Desktop.isDesktopSupported()){
            Desktop desktop = Desktop.getDesktop();
            desktop.browse(new URI(linkProyecto.getText()));
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
    }    
    
}
