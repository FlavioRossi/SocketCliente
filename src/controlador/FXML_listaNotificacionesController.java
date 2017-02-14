/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import com.jfoenix.controls.JFXListView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Control;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import modelo.Cliente;
import notificaciones.Notificacion;

/**
 * FXML Controller class
 *
 * @author FLAVIO
 */
public class FXML_listaNotificacionesController implements Initializable {
    @FXML
    private JFXListView<Notificacion> jfxListView_notificaciones;
    
    private final Cliente cliente;

    public FXML_listaNotificacionesController() {
        this.cliente = Cliente.getInstance();
    }
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        jfxListView_notificaciones.setItems(cliente.getNotificacion());
        
        jfxListView_notificaciones.setExpanded(true);
        jfxListView_notificaciones.setDepthProperty(1);
        
        jfxListView_notificaciones.setCellFactory((ListView<Notificacion> param) -> {
            ListCell<Notificacion> cell;
            try {
                FXMLLoader loadFXML = new FXMLLoader();
                loadFXML.setLocation(getClass().getResource("/fxml/FXML_notificacionItem.fxml"));
                Parent nodeRoot = loadFXML.load();
                FXML_notificacionItemController itemNotificacionControlador = loadFXML.getController();
                cell = new ListCell<Notificacion>() {
                    @Override
                    protected void updateItem(Notificacion item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            itemNotificacionControlador.setLabel_titulo(item.getOrigen());
                            itemNotificacionControlador.setLabel_mensaje(item.getMsj());
                            setGraphic(nodeRoot);
                        }else{
                            setGraphic(null);
                        }
                    }
                };
            } catch (IOException ex) {
                Logger.getLogger(FXML_listaNotificacionesController.class.getName()).log(Level.SEVERE, null, ex);
                cell = null;
            }
            return cell;
        });
    }    
}
