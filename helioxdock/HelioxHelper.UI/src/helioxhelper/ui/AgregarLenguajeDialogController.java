/*
 * Copyright (C) 2016 Mariana
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package helioxhelper.ui;

/**
 *
 * @author Mariana
 */
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

public class AgregarLenguajeDialogController extends VBox {
    File fileIcono;
    FileChooser fileChooser = new FileChooser();
    
    @FXML
    private VBox vBox;

    @FXML
    private JFXTextField nombre;

    @FXML
    private JFXTextField icono;

    @FXML
    private JFXButton btnIcono;

    @FXML
    void handleSeleccionarIconoAction(ActionEvent event) {
        
        fileIcono = fileChooser.showOpenDialog(icono.getScene().getWindow());
        System.out.println(fileIcono);
        icono.setText(fileIcono.getName());
    }

    public AgregarLenguajeDialogController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AgregarLenguajeDialog.fxml"));
       fxmlLoader.setController(this);
       fxmlLoader.setRoot(this);
        try {
            
            fxmlLoader.load();

        } catch (IOException ex) {
            Logger.getLogger(FXMLAgregarBotonController.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Loaded!");
    }
    

    public VBox getvBox() {
        return vBox;
    }

    public void setvBox(VBox vBox) {
        this.vBox = vBox;
    }

    public JFXTextField getNombre() {
        return nombre;
    }

    public void setNombre(JFXTextField nombre) {
        this.nombre = nombre;
    }

    public JFXTextField getIcono() {
        return icono;
    }

    public void setIcono(JFXTextField icono) {
        this.icono = icono;
    }

    public JFXButton getBtnIcono() {
        return btnIcono;
    }

    public void setBtnIcono(JFXButton btnIcono) {
        this.btnIcono = btnIcono;
    }
 
        public File getFileIcono() {
        return fileIcono;
    }

    public void setFileIcono(File fileIcono) {
        this.fileIcono = fileIcono;
    }

    public FileChooser getFileChooser() {
        return fileChooser;
    }

    public void setFileChooser(FileChooser fileChooser) {
        this.fileChooser = fileChooser;
    }
    

}

