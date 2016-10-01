/*
 * Copyright (C) 2016 Mariana
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License.
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
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import org.proyectoHeliox.helper.entidades.Boton;
import org.proyectoHeliox.helper.negocio.NegocioHeliox;

/**
 * FXML Controller class
 *
 * @author Mariana
 */
public class BotonListViewCellController extends ListCell<Boton> {

    NegocioHeliox nh = new NegocioHeliox();
    ListView<Boton> listView;
    JFXDialogLayout layout;
    StackPane stackPane;
    JFXDialog dialog;
    File fileIcono;
    @FXML
    private HBox buttonCardBox;

    @FXML
    private ImageView iconoPrograma;

    @FXML
    private Label lblAudio;

    @FXML
    private Label lblPrograma;

    @FXML
    private Label lblDescripcion;

    @FXML
    private JFXButton btnEditar;

    @FXML
    private JFXButton btnBorrar;

    private FXMLLoader loader;

    @FXML
    void audioOnMouseClicked(MouseEvent event) {

    }

    public BotonListViewCellController(ListView<Boton> listView, JFXDialogLayout layout,
            StackPane stackPane, JFXDialog dialog) {
        super();
        this.listView = listView;
        this.layout = layout;
        this.stackPane = stackPane;
        this.dialog = dialog;
    }

    @Override
    protected void updateItem(Boton b, boolean empty) {
        super.updateItem(b, empty);
        if (empty || b == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (loader == null) {
                loader = new FXMLLoader(getClass().getResource("BotonListViewCell.fxml"));
                loader.setController(this);
                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
//            File fileAudio = new File(b.getRutaAudio());
            // lblAudio.setText(fileAudio.getName());
            File fileEjecutable = new File(b.getRutaEjecutable());
            lblPrograma.setText(fileEjecutable.getName());
            lblDescripcion.setText(b.getDescripcion());
            btnBorrar.setOnAction(e -> {
                layout.getActions().clear();
                layout.setHeading(new Label("¿ELIMINAR?"));
                JFXButton btnOk = new JFXButton("ELIMINAR");
                JFXButton btnCancel = new JFXButton("CONSERVAR");
                layout.getActions().addAll(btnCancel, btnOk);
                dialog = new JFXDialog(stackPane, layout, JFXDialog.DialogTransition.CENTER);
                btnCancel.setOnMouseClicked(ev -> {
                    dialog.close();
                });
                btnOk.setOnMouseClicked(ev -> {
                    
                try {

                    nh.eliminarBoton(b);
                    listView.getItems().remove(b);
                } catch (SQLException | IOException ex) {
                    Logger.getLogger(FXMLSettingsController.class.getName()).log(Level.SEVERE, null, ex);
                }
                listView.getItems().stream().forEach((btn) -> {
                    System.out.println("item" + btn.getId());
                }
                );

                });
                dialog.show();
            });
            btnEditar.setOnAction(e->{
                
                layout.getActions().clear();
                layout.setHeading(new Label("INFORMACIÓN"));
                JFXButton btnOk = new JFXButton("GUARDAR");
                JFXButton btnCancel = new JFXButton("DESCARTAR");
                FXMLAgregarBotonController agregarBoton = new FXMLAgregarBotonController();
                agregarBoton.setFileEjecutable(fileEjecutable);
                agregarBoton.getDescripcion().setText(b.getDescripcion());
//                try {
//                    //agregarBoton.setFileAudio(fileAudio);
//                    FileInputStream fis= new FileInputStream(b.getRutaIcono());
//                } catch (FileNotFoundException ex) {
//                    Logger.getLogger(BotonListViewCellController.class.getName()).log(Level.SEVERE, null, ex);
//                }
                
                //agregarBoton.setFileIcono(fileIcono);
                layout.getBody().setAll(agregarBoton);
                layout.getActions().addAll(btnCancel, btnOk);
                dialog = new JFXDialog(stackPane, layout, JFXDialog.DialogTransition.CENTER);
                btnCancel.setOnMouseClicked(ev -> {
                    dialog.close();
                });
                btnOk.setOnMouseClicked(ev -> {
                    
                try {
                    nh.editarBoton(b);
                    listView.getItems().remove(b);
                } catch (SQLException | IOException ex) {
                    Logger.getLogger(FXMLSettingsController.class.getName()).log(Level.SEVERE, null, ex);
                }
                listView.getItems().stream().forEach((btn) -> {
                    System.out.println("item" + btn.getId());
                }
                );

                });
                dialog.show();
            });

           // Image icon = new Image(getClass().getResourceAsStream("file:"+ b.getRutaIcono()));
            // iconoPrograma.setImage(icon);
            setGraphic(buttonCardBox);

        }
    }
    
    
}
