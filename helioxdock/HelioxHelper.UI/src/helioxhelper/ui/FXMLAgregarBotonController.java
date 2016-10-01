/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helioxhelper.ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.proyectoHeliox.helper.entidades.*;
import org.proyectoHeliox.helper.negocio.NegocioHeliox;

/**
 * Controla la vista para agregar un nuevo botón a un lenguaje. 
 * 
 *
 * @author Mariana García
 */
public class FXMLAgregarBotonController extends VBox {

  
    File fileAudio, fileEjecutable, fileIcono;
    FileChooser fileChooser = new FileChooser();
    Lenguaje l = new Lenguaje();
    Boton b = new Boton();
    NegocioHeliox nh = new NegocioHeliox();
    
    @FXML
    private VBox vBox;

    @FXML
    private StackPane stackPane;

    @FXML
    private JFXDialog guardarDialog;

    @FXML
    private JFXTextField audio;

    @FXML
    private JFXButton btnAudio;

    @FXML
    private JFXTextField ejecutable;

    @FXML
    private JFXButton btbEjecutable;

    @FXML
    private JFXTextField icono;

    @FXML
    private JFXButton btnIcono;

    @FXML
    private JFXTextArea descripcion;

    @FXML
    private JFXButton guardar;

    @FXML
    private JFXButton cancelar;
    
     Alert alert = new Alert(Alert.AlertType.INFORMATION);
     
     

    @FXML
    void handleCancelarAction(ActionEvent event) {
        alert.setAlertType(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Descartar");
        alert.setContentText("Desea descartar la operacion?");
         Optional<ButtonType> result = alert.showAndWait();
         
         if(result.get() == ButtonType.OK){
          cancelar.getScene().getWindow().hide();
         }     
    }
    
   

    @FXML
    void handleGuardarAction(ActionEvent event) throws SQLException, IOException {
        System.out.print(l.getNombre());
        b.setDescripcion(descripcion.getText());
        nh.agregarBoton(b, l.getId());
        alert.setTitle("Informacion");
        alert.setContentText("Se ha agregado el boton a la lengua.");
        alert.show();

    }

    @FXML
    void handleSeleccionarEjecutableAction(ActionEvent event) throws IOException {
       
        fileEjecutable = fileChooser.showOpenDialog(ejecutable.getScene().getWindow());
        if (fileEjecutable!= null){
              ejecutable.setText(fileEjecutable.toString());
        }      
        b.setRutaEjecutable(ejecutable.getText());
        System.out.println(b.getRutaEjecutable());

    }

    @FXML
    void handleSeleccionarIconoAction(ActionEvent event) throws IOException {
        fileIcono = fileChooser.showOpenDialog(icono.getScene().getWindow());
        System.out.println(fileIcono);
        icono.setText(fileIcono.getName());
        b.setRutaIcono(fileIcono.toString());

    }

    @FXML
    void handleSelecionarAudioAction(ActionEvent event) {
        fileAudio = fileChooser.showOpenDialog(audio.getScene().getWindow());
        System.out.println(fileAudio);
        audio.setText(fileAudio.getName());
        b.setRutaAudio(fileAudio.toString());
    }

    public void initData(Lenguaje l) {
        this.l = l;
        
    }
    public FXMLAgregarBotonController() {
       FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AgregarBotonDialog.fxml"));
       fxmlLoader.setController(this);
       fxmlLoader.setRoot(this);
        try {
            
            fxmlLoader.load();

        } catch (IOException ex) {
            Logger.getLogger(FXMLAgregarBotonController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("Loaded!");
        
    }

    public File getFileAudio() {
        return fileAudio;
    }

    public void setFileAudio(File fileAudio) {
        this.fileAudio = fileAudio;
    }

    public File getFileEjecutable() {
        return fileEjecutable;
    }

    public void setFileEjecutable(File fileEjecutable) {
        this.fileEjecutable = fileEjecutable;
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

    public Lenguaje getL() {
        return l;
    }

    public void setL(Lenguaje l) {
        this.l = l;
    }

    public Boton getB() {
        return b;
    }

    public void setB(Boton b) {
        this.b = b;
    }

    public NegocioHeliox getNh() {
        return nh;
    }

    public void setNh(NegocioHeliox nh) {
        this.nh = nh;
    }

    public VBox getvBox() {
        return vBox;
    }

    public void setvBox(VBox vBox) {
        this.vBox = vBox;
    }

    public StackPane getStackPane() {
        return stackPane;
    }

    public void setStackPane(StackPane stackPane) {
        this.stackPane = stackPane;
    }

    public JFXDialog getGuardarDialog() {
        return guardarDialog;
    }

    public void setGuardarDialog(JFXDialog guardarDialog) {
        this.guardarDialog = guardarDialog;
    }

    public JFXTextField getAudio() {
        return audio;
    }

    public void setAudio(JFXTextField audio) {
        this.audio = audio;
    }

    public JFXButton getBtnAudio() {
        return btnAudio;
    }

    public void setBtnAudio(JFXButton btnAudio) {
        this.btnAudio = btnAudio;
    }

    public JFXTextField getEjecutable() {
        return ejecutable;
    }

    public void setEjecutable(JFXTextField ejecutable) {
        this.ejecutable = ejecutable;
    }

    public JFXButton getBtbEjecutable() {
        return btbEjecutable;
    }

    public void setBtbEjecutable(JFXButton btbEjecutable) {
        this.btbEjecutable = btbEjecutable;
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

    public JFXTextArea getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(JFXTextArea descripcion) {
        this.descripcion = descripcion;
    }

    public JFXButton getGuardar() {
        return guardar;
    }

    public void setGuardar(JFXButton guardar) {
        this.guardar = guardar;
    }

    public JFXButton getCancelar() {
        return cancelar;
    }

    public void setCancelar(JFXButton cancelar) {
        this.cancelar = cancelar;
    }

    public Alert getAlert() {
        return alert;
    }

    public void setAlert(Alert alert) {
        this.alert = alert;
    }



}
