/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPopup;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import modelo.Cliente;

/**
 * FXML Controller class
 *
 * @author FLAVIO
 */
public class FXML_appController implements Initializable {
    
    private final Cliente cliente;
    private final JFXPopup popup;
    
    @FXML
    private StackPane stackPane_container;
    @FXML
    private JFXButton jfxButton_menu;

    public FXML_appController() {
        this.cliente = Cliente.getInstance();
        this.popup = new JFXPopup();
    }
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Parent node;
        try {
            node = FXMLLoader.load(getClass().getResource("/fxml/FXML_listaNotificaciones.fxml"));
            StackPane.setMargin(node, new Insets(63,0,0,0));
        } catch (IOException ex) {
            Logger.getLogger(FXML_appController.class.getName()).log(Level.SEVERE, null, ex);
            node = null;
        }
        stackPane_container.getChildren().add(node);
        
        inicializaMenuPopup();
    } 

    @FXML
    private void reconectar(ActionEvent event) {
        if (!cliente.isConectado()) {
            if (cliente.abrirConexion()) {
                System.out.println("Cliente conectado");
            }else{
                System.out.println("Cliente no conectado");
            }
        }
    }

    private void inicializaMenuPopup() {
        JFXButton btn_opciones = new JFXButton("Opciones");
        btn_opciones.setOnAction((e) -> {
            popup.close();
            System.out.println("muestra vista opciones");
        });
        btn_opciones.setStyle("-fx-text-fill: #FFFFFF;");
        
        JFXButton btn_cerrarSesion = new JFXButton("Cerrar Sesión");
        btn_cerrarSesion.setOnAction((e) -> {
            popup.close();
            System.out.println("devuelve " + cliente.cerrarConexion());
        });
        btn_cerrarSesion.setStyle("-fx-text-fill: #FFFFFF;");
        
        VBox container = new VBox(btn_opciones, btn_cerrarSesion);
        container.setStyle("-fx-background-color: #424242");
        popup.setContent(container);
        popup.setSource(jfxButton_menu);
    }

    @FXML
    private void opciones(ActionEvent event) {
        popup.show(JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT);
    }
}
