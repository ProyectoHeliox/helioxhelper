/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helioxhelper.ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXListView;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import org.proyectoHeliox.helper.entidades.*;
import org.proyectoHeliox.helper.negocio.NegocioHeliox;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.ImageView;
/**
 * Controlador de la vista Settings.
 *
 * @author Mariana G<arcía
 */
public class FXMLSettingsController implements Initializable {
    NegocioHeliox nh = new NegocioHeliox();
    JFXDialogLayout layout = new JFXDialogLayout();
    JFXDialog dialog;
    private static FXMLSettingsController instance = null;
    
    @FXML
    private JFXListView<Lenguaje> listaLenguajes;

    @FXML
    private JFXListView listaBotones;
    
    private ObservableList<Boton> botonObservableList;
    private ObservableList<Lenguaje> lenguajeObservableList;
    
    @FXML
    private JFXButton btnAgregarBoton;

    @FXML
    private JFXButton agregarLenguaje;

    @FXML
    private JFXButton eliminarLenguaje;

    @FXML
    private StackPane stackPane;
    
    @FXML
    private JFXButton editarLenguaje;
   
    void setStyle( JFXButton b) {
        b.setStyle("-fx-text-color: #212121");
        b.setStyle("-fx-background-color: white");
        b.setOnMouseEntered(e->{
            b.setStyle("-fx-background-color: #BDBDBD");
        });        
        b.setOnMouseExited(e->{
            b.setStyle("-fx-background-color: white");
        });        
    }
    

    @FXML
    void onActionEliminarLenguaje(ActionEvent event) throws SQLException, IOException {
        layout.getActions().clear();
        layout.getBody().clear();
        layout.getHeading().clear();
       
        if (!listaLenguajes.getSelectionModel().isEmpty()) {
            layout.getActions().clear();
            layout.setHeading(new Label("ELIMINAR LENGUA " +  listaLenguajes.getSelectionModel().getSelectedItem().getNombre().toUpperCase()));
            JFXButton btnOk = new JFXButton("ELIMINAR");
            JFXButton btnCancel = new JFXButton("CONSERVAR");
            setStyle(btnCancel);
            setStyle(btnOk);
            layout.getActions().addAll(btnCancel, btnOk);
            JFXDialog dialog = new JFXDialog(stackPane, layout, JFXDialog.DialogTransition.CENTER);
            btnCancel.setOnMouseClicked(e -> {
                dialog.close();
            });
            btnOk.setOnMouseClicked((MouseEvent e) -> {
                try {
                    nh.eliminarLenguaje(listaLenguajes.getSelectionModel().getSelectedItem());
                    listaLenguajes.getItems().clear();
                    llenarLista();
                    listaLenguajes.refresh();
                    
                } catch (SQLException | IOException ex) {
                    Logger.getLogger(FXMLSettingsController.class.getName()).log(Level.SEVERE, null, ex);
                }
                dialog.close();
            });
            dialog.show();

        } else {
            layout.setHeading(new Label("NO HAS SELECCIONADO NINGUN LENGUAJE"));
            JFXButton btnOk = new JFXButton("ENTENDIDO");
            setStyle(btnOk);
            layout.getActions().clear();
            layout.setActions(btnOk);
            JFXDialog dialog = new JFXDialog(stackPane, layout, JFXDialog.DialogTransition.CENTER);
            btnOk.setOnMouseClicked(e -> {
                dialog.close();
            });
            dialog.show();
        }

    }
    
    @FXML
    void onActionEditarLenguaje(ActionEvent event) {
        layout.getActions().clear();
        layout.getBody().clear();
        layout.getHeading().clear();
        if (listaLenguajes.getSelectionModel().isEmpty()){
        
        }
        else {
            Lenguaje l = listaLenguajes.getSelectionModel().getSelectedItem();
            layout.setHeading(new Label("Información de Lengua"));
            AgregarLenguajeDialogController editarLenguajeBox = new AgregarLenguajeDialogController();
            editarLenguajeBox.getNombre().setText(listaLenguajes.getSelectionModel().getSelectedItem().getNombre());
            File icon = new File("file:" + listaLenguajes.getSelectionModel().getSelectedItem().getIcono());
            editarLenguajeBox.getIcono().setText(icon.getName());
            JFXButton btnOk = new JFXButton("GUARDAR");
            JFXButton btnCancel = new JFXButton("DESCARTAR");
            setStyle(btnOk);
            setStyle(btnCancel);
            layout.setActions(btnCancel, btnOk);
            layout.setBody(editarLenguajeBox);
            dialog = new JFXDialog(stackPane, layout, JFXDialog.DialogTransition.CENTER);
            btnOk.setOnMouseClicked(e -> {

                l.setNombre(editarLenguajeBox.getNombre().getText());
                l.setIcono(editarLenguajeBox.getFileIcono().toString());
                try {
                    nh.editarLenguaje(l);
                } catch (SQLException | IOException ex) {
                    Logger.getLogger(FXMLSettingsController.class.getName()).log(Level.SEVERE, null, ex);
                }
                listaLenguajes.getItems().clear();
                lenguajeObservableList.clear();
                try {
                    llenarLista();
                } catch (SQLException | IOException ex) {
                    Logger.getLogger(FXMLSettingsController.class.getName()).log(Level.SEVERE, null, ex);
                }
                dialog.close();
            });
            btnCancel.setOnMouseClicked(e -> {
                dialog.close();
            });
            dialog.show();
        }

    }

    @FXML
    void onActionAgregarLenguaje(ActionEvent event) throws SQLException {
        layout.getActions().clear();
        layout.getBody().clear();
        layout.getHeading().clear();
        layout.setHeading(new Label("Información de lenguaje"));
        AgregarLenguajeDialogController agregarLenguajeDialog = new  AgregarLenguajeDialogController();
        layout.getBody().setAll(agregarLenguajeDialog);
        dialog = new JFXDialog(stackPane, layout,JFXDialog.DialogTransition.CENTER );
        JFXButton ok = new JFXButton("GUARDAR");
        JFXButton cancel = new JFXButton("DESCARTAR");
        layout.setActions(ok, cancel);
        Lenguaje l = new Lenguaje();
        ok.setOnAction(e->{
            l.setNombre(agregarLenguajeDialog.getNombre().getText());
            l.setIcono(agregarLenguajeDialog.getFileIcono().toString());
            try {
                nh.agregarLenguaje(l);
                listaLenguajes.getItems().clear();
                llenarLista();
            } catch (SQLException | IOException ex) {
                Logger.getLogger(FXMLSettingsController.class.getName()).log(Level.SEVERE, null, ex);
            }
                
                listaLenguajes.refresh();
                dialog.close();
        });
        cancel.setOnAction(e->{
        dialog.close();
        });
        dialog.show();
        
    } 
    
    public void llenarLista() throws SQLException, IOException {
        listaBotones.getItems().clear();
        if (nh.listadoLenguajes().size()>= 1){
        for (Lenguaje l : nh.listadoLenguajes()) {
            lenguajeObservableList.add(l);
        }
        listaLenguajes.setItems(lenguajeObservableList);
        }
    }
    
    public void llenarListaBotones(Lenguaje l) throws SQLException, IOException {

        if (nh.listadoBotonesLenguaje(l.getId()).size() >= 1) {
            for (Boton b : nh.listadoBotonesLenguaje(l.getId())) {
                botonObservableList.add(b);
            }
            listaBotones.setItems(botonObservableList);
        }

    }

    @FXML
    void agregarBoton(ActionEvent event) throws IOException {
        layout.getActions().clear();
        layout.getBody().clear();
        layout.setHeading(new Label("AGREGAR BOTON"));
        FXMLAgregarBotonController agregarBoton = new FXMLAgregarBotonController();
        layout.getBody().setAll(agregarBoton);
        JFXButton btnOk = new JFXButton("GUARDAR");
        JFXButton btnCancel = new JFXButton("DESCARTAR");
        setStyle(btnCancel);
        setStyle(btnOk);
        layout.getActions().addAll(btnCancel, btnOk);
        dialog = new JFXDialog(stackPane, layout, JFXDialog.DialogTransition.CENTER);
           btnOk.setOnAction(e->{
               Boton b = new Boton();
               b.setRutaIcono(agregarBoton.getFileIcono().toString());
               b.setRutaAudio(agregarBoton.getFileAudio().toString());
               b.setDescripcion(agregarBoton.getDescripcion().getText());
               b.setRutaEjecutable(agregarBoton.getEjecutable().getText());
            try {
                nh.agregarBoton(b, listaLenguajes.getSelectionModel().getSelectedItem().getId());
            } catch (SQLException | IOException ex) {
                Logger.getLogger(FXMLSettingsController.class.getName()).log(Level.SEVERE, null, ex);
            }
            dialog.close();
            
        });
        btnCancel.setOnAction(e->{
        dialog.close();
        });
        dialog.show();
    }
    

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listaLenguajes.setExpanded(Boolean.TRUE);
        listaLenguajes.setDepthProperty(1);
        setStyle(btnAgregarBoton);
        setStyle(agregarLenguaje);
        setStyle(editarLenguaje);
        setStyle(eliminarLenguaje);       
        listaBotones.setCellFactory(botonListView -> new BotonListViewCellController(listaBotones, layout, stackPane, dialog));
        listaLenguajes.setCellFactory(lenguajeListView -> new LenguajeListViewCellController());
        instance = this;
        botonObservableList =FXCollections.observableArrayList();
        lenguajeObservableList = FXCollections.observableArrayList();
        Image iconoEliminar = new Image(getClass().getResourceAsStream("/icons/delete.png"));
        ImageView iconView = new ImageView(iconoEliminar);
        iconView.setFitWidth(15);
        iconView.setFitHeight(15);
        eliminarLenguaje.setGraphic(iconView);
        eliminarLenguaje.setText(null);
        Image iconoEditar = new Image(getClass().getResourceAsStream("/icons/edit.png"));
        ImageView iconViewEditar = new ImageView(iconoEditar);
        iconViewEditar.setFitWidth(15);
        iconViewEditar.setFitHeight(15);
        editarLenguaje.setGraphic(iconViewEditar);
        editarLenguaje.setText(null);
        Image iconoAgregar = new Image(getClass().getResourceAsStream("/icons/add_black.png"));
             ImageView iconViewAgregar = new ImageView(iconoAgregar);
             iconViewAgregar.setFitWidth(15);
             iconViewAgregar.setFitHeight(15);
             agregarLenguaje.setGraphic(iconViewAgregar);
             agregarLenguaje.setText(null);
        
        try {
            try {
                // TODO
                llenarLista();
            } catch (IOException ex) {
                Logger.getLogger(FXMLSettingsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLSettingsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        btnAgregarBoton.setVisible(false);
                listaLenguajes.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Lenguaje> observable, Lenguaje oldValue, Lenguaje newValue) -> {
                    System.out.println("Selected item: " + newValue);
                    btnAgregarBoton.setVisible(true);
                    listaBotones.getItems().clear();
             try {
                 llenarListaBotones(newValue);
             } catch (SQLException | IOException ex) {
                 Logger.getLogger(FXMLSettingsController.class.getName()).log(Level.SEVERE, null, ex);
             }
         });
    }
    
    public static FXMLSettingsController getInstance() {
        return instance;
    }
    
}
