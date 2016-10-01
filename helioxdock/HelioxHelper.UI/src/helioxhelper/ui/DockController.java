/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helioxhelper.ui;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import java.awt.AWTException;
import java.awt.Desktop;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.proyectoHeliox.helper.entidades.*;
import org.proyectoHeliox.helper.negocio.NegocioHeliox;

/**
 * Controlador de la vista del dock.
 *
 * @author Mariana García
 */
public class DockController implements Initializable {

    final Audio player = new Audio();

    ScrollPane sp = new ScrollPane();

    @FXML
    private ImageView settingsIcon;

    IOUtil ioUtil;

    private final NegocioHeliox nh = new NegocioHeliox();

    @FXML
    private JFXComboBox<Lenguaje> combobox = new JFXComboBox<Lenguaje>();

    @FXML
    private JFXListView listViewBotones;

    private void obtenerLenguajes() throws SQLException, IOException {
        for (Lenguaje l : nh.listadoLenguajes()) {
            combobox.getItems().add(l);
        }
    }

    public void addIconToTray() throws AWTException {
        if (SystemTray.isSupported()) {
            SystemTray sysTray = SystemTray.getSystemTray();
            PopupMenu trayPopupMenu = new PopupMenu();
            java.awt.MenuItem action = new java.awt.MenuItem("Acerca de");
            java.awt.MenuItem config = new java.awt.MenuItem("Configuración");
            config.addActionListener((java.awt.event.ActionEvent e) -> {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Settings.fxml"));
                    Parent root = (Parent) fxmlLoader.load();
                    Stage stage = new Stage();
                    stage.setTitle("Configuración - Heliox Asistente");
                    stage.initModality(Modality.WINDOW_MODAL);
                    stage.setScene(new Scene(root));
                    Image applicationIcon = new Image("file:icon.png");
                    stage.getIcons().add(applicationIcon);
                    stage.show();
                } catch (Exception ex) {
                    System.out.print(ex.getMessage());
                }
            });
            action.addActionListener((java.awt.event.ActionEvent e) -> {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Acerca.fxml"));
                    Parent root = (Parent) fxmlLoader.load();
                    Stage stage = new Stage();
                    stage.setTitle("Acerca de - Heliox Asistente");
                    stage.initModality(Modality.WINDOW_MODAL);
                    stage.setScene(new Scene(root));
                    Image applicationIcon = new Image("file:/src/icon.png");
                    stage.getIcons().add(applicationIcon);
                    stage.show();
                } catch (Exception ex) {
                    System.out.print(ex.getMessage());
                }
            });
            trayPopupMenu.add(action);
            trayPopupMenu.add(config);
            java.awt.Image image = Toolkit.getDefaultToolkit().getImage(System.getProperty("user.dir") + "src/icon.png");
            trayPopupMenu.addSeparator();
            java.awt.MenuItem salir = new java.awt.MenuItem("Salir");
            trayPopupMenu.add(salir);
            salir.addActionListener((java.awt.event.ActionEvent e) -> {
            });
            TrayIcon trayIcon = new TrayIcon(image, "Heliox Asistente", trayPopupMenu);
            trayIcon.setImageAutoSize(true);
            sysTray.add(trayIcon);
        }
    }

    public void llenarPanelBotones(Lenguaje l) throws SQLException, IOException {

        for (Boton b : nh.listadoBotonesLenguaje(l.getId())) {

            Button button = new Button();
            Image icon;
            icon = new Image("file:///"+ b.getRutaIcono());
            ImageView iconView = new ImageView(icon);
            iconView.setFitHeight(25);
            iconView.setFitWidth(25);
            button.setGraphic(iconView);

            button.setOnMouseEntered(e -> {
                iconView.setScaleX(1.2);
                iconView.setScaleY(1.2);
                System.out.println(b.getRutaAudio());
                player.play(b.getRutaAudio());

            });
            button.setOnMouseExited(e -> {
                iconView.setScaleX(1);
                iconView.setScaleY(1);

            });
            Tooltip tooltip = new Tooltip(b.getDescripcion());
            tooltip.setStyle("-fx-font-size: 14");
            Tooltip.install(button, tooltip);
            listViewBotones.getItems().add(button);
            button.setOnMouseClicked(e -> {
            });

            button.setOnMouseClicked((MouseEvent e) -> {
                System.out.print(b.getRutaEjecutable());
                try {
                    if (b.getRutaEjecutable().endsWith(".pdf") || b.getRutaEjecutable().startsWith("http://")) {

                        if (Desktop.isDesktopSupported()) {
                            Desktop desktop = Desktop.getDesktop();
                            desktop.browse(new URI(b.getRutaEjecutable()));

                        } else {

                            //process = Runtime.getRuntime().exec("xdg-open "+ b.getRutaEjecutable());
                            Alert a = new Alert(Alert.AlertType.ERROR);
                            a.setContentText("Desktop no soportado.");
                        }

                    } else {
                        Process process = Runtime.getRuntime().exec(b.getRutaEjecutable());
                    }
                } catch (URISyntaxException | IOException ex) {
                    Logger.getLogger(DockController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

        }

    }

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            nh.crearTablas();
        } catch (SQLException | IOException ex) {
            Logger.getLogger(DockController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            addIconToTray();
        } catch (AWTException ex) {
            Logger.getLogger(DockController.class.getName()).log(Level.SEVERE, null, ex);
        }
            Image icon = new Image(getClass().getResourceAsStream("/icon.png"));
            settingsIcon.setImage(icon);
            Tooltip tipCombobox = new Tooltip("Cambie el lenguaje actual.");
            Tooltip.install(combobox, tipCombobox);
            tipCombobox.setStyle("-fx-font-size: 14");
            Tooltip tipConfig = new Tooltip("Edite la configuracion.");
            Tooltip.install(settingsIcon, tipConfig);
            tipConfig.setStyle("-fx-font-size: 14");
            try {
                
                try {
                    obtenerLenguajes();
                } catch (IOException ex) {
                    Logger.getLogger(DockController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (SQLException ex) {
                Logger.getLogger(DockController.class.getName()).log(Level.SEVERE, null, ex);
            }
            combobox.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Lenguaje> observable, Lenguaje oldValue, Lenguaje newValue) -> {
                System.out.println("Nuevo " + newValue);
                System.out.println("Viejo " + oldValue);
                listViewBotones.getItems().clear();
                try {
                    llenarPanelBotones(newValue);
                } catch (SQLException | IOException ex) {
                    Logger.getLogger(DockController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            try {
                if (nh.listadoLenguajes().size()>0){
                    combobox.getSelectionModel().select(nh.listadoLenguajes().iterator().next());
                }
            } catch (SQLException | IOException ex) {
                Logger.getLogger(DockController.class.getName()).log(Level.SEVERE, null, ex);
            }
            ContextMenu menu = new ContextMenu();
            MenuItem config = new MenuItem("Configuración");
            MenuItem about  = new MenuItem("Acerca de");
            about.setStyle("-fx-font-size: 14");
            menu.setStyle("-fx-font-size: 14");
            menu.getItems().addAll(config, about);
            about.setOnAction(e->{
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Acerca.fxml"));
                    Parent root = (Parent) fxmlLoader.load();
                    Stage stage = new Stage();
                    stage.setTitle("Configuración");
                    stage.initModality(Modality.WINDOW_MODAL);
                    stage.setScene(new Scene(root));
                    stage.setResizable(false);
                    Image applicationIcon = new Image("file:/src/icon.png");
                    stage.getIcons().add(applicationIcon);
                    stage.show();
                } catch (Exception ex){
                    System.out.print(ex.getMessage());
                }
            });
            config.setOnAction((ActionEvent e)->{
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Settings.fxml"));
                    Parent root = (Parent) fxmlLoader.load();
                    Stage stage = new Stage();
                    stage.setTitle("Configuración");
                    stage.initModality(Modality.WINDOW_MODAL);
                    stage.setScene(new Scene(root));
                    stage.setResizable(false);
                    Image applicationIcon = new Image("file:/src/icon.png");
                    stage.getIcons().add(applicationIcon);
                    stage.show();
                } catch (Exception ex){
                    System.out.print(ex.getMessage());
                    System.out.println(ex.getStackTrace());
                }
            });
            
            settingsIcon.setOnMousePressed(e->{
                menu.show(settingsIcon, e.getScreenX(), e.getScreenY());
            });
            
       
    
    }
        
    }
    
      
     
    

