/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import notificaciones.Notificacion;
import notificaciones.NotificacionMsj;

/**
 * FXML Controller class
 *
 * @author FLAVIO
 */
public class FXML_notificacionController implements Initializable {
    
    ObservableList<Notificacion> notificaciones;
    
    @FXML
    private VBox vbox_mensajes;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("inicializa");
    }    
    
    public void addTitulo(String titulo){
        Label lbl_titulo = new Label(titulo);
        lbl_titulo.getStyleClass().add("title");
        vbox_mensajes.getChildren().add(lbl_titulo);
    }
    
    public void addMsj(NotificacionMsj msj){
        msj.setVisto(true);
        
        HBox hbox = new HBox();
        hbox.getStyleClass().add("contenedor");
        if (msj.getOrigen() != null) {
            Label lbl_remitente = new Label(msj.getOrigen());
            lbl_remitente.getStyleClass().add("remitente");
            hbox.getChildren().add(lbl_remitente);
        }
        Label lbl_msj = new Label(msj.getMsj());
        lbl_msj.getStyleClass().add("responde");
        hbox.getChildren().add(lbl_msj);
        
        vbox_mensajes.getChildren().add(hbox);
    }
    
    @FXML
    private void paginaOI(MouseEvent event) {
        try {
            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + "www.oi.com.ar");
        } catch (IOException ex) {
            System.out.println("Error al abrir pagina de Organización Informática");
            Logger.getLogger(FXML_notificacionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
