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

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import org.proyectoHeliox.helper.entidades.Lenguaje;

/**
 * FXML Controller class
 *
 * @author Mariana
 */
public class LenguajeListViewCellController extends ListCell<Lenguaje> {
    
    @FXML
    private HBox hbox;

    @FXML
    private ImageView icon;

    @FXML
    private Label lblLenguaje;

    
     private FXMLLoader loader;
     
       void setStyle(JFXButton b) {

        b.setStyle("-fx-background-color: transparent");
        b.setOnMouseEntered(e -> {
            b.setStyle("-fx-background-color: #BDBDBD");
        });

        b.setOnMouseExited(e -> {
            b.setStyle("-fx-background-color: transparent");
        });
    }
     @Override
    protected void updateItem(Lenguaje l, boolean empty){
         super.updateItem(l, empty);
         if (empty || l == null) {
             setText(null);
             setGraphic(null);
         } else {
             if (loader == null) {
                 loader = new FXMLLoader(getClass().getResource("LenguajeListViewCell.fxml"));
                 loader.setController(this);
                 try {
                     loader.load();
                 } catch (IOException e) {
                     e.printStackTrace();
                 }
             }
             lblLenguaje.setText(l.getNombre());
//            Image icono = new Image(getClass().getResourceAsStream("file:"+ l.getIcono()));
//            icon.setImage(icono);
             setGraphic(hbox);
        }
    }
    
    
}
