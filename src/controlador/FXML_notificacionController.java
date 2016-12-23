/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import util.Notificaciones;

/**
 * FXML Controller class
 *
 * @author FLAVIO
 */
public class FXML_notificacionController implements Initializable {
    Notificaciones notificaciones;
    
    @FXML
    private Label lbl_titulo;
    @FXML
    private Label lbl_mensaje;
    @FXML
    private HBox hbox_root;
    @FXML
    private JFXButton btn_cerrar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
    public void setObservableList(Notificaciones notificaciones){
        this.notificaciones = notificaciones;
    }
    
    public void setTitulo(String titulo){
        lbl_titulo.setText(titulo);
    }
    public void setMensaje(String mensaje){
        lbl_mensaje.setText(mensaje);
    }
    
}
