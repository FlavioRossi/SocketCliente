/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import com.jfoenix.controls.JFXListView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import modelo.Cliente;

/**
 * FXML Controller class
 *
 * @author FLAVIO
 */
public class FXML_appController implements Initializable {
    @FXML
    private JFXListView<AdapterNotificacion> jfxListView_notificaciones;
    
    private ObservableList<AdapterNotificacion> notificaciones;
    
    private Cliente cliente;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        notificaciones = FXCollections.observableArrayList();
        jfxListView_notificaciones.setItems(notificaciones);
        
    }    

    public void setCliente(Cliente cliente){
        this.cliente = cliente;
    }
    
    /**
     * Clase adaptador jfxListView_notificaciones
     */
    private static class AdapterNotificacion {
        private SimpleStringProperty titulo;
        private SimpleStringProperty mensaje;
        
        public AdapterNotificacion() {
            titulo = new SimpleStringProperty();
            mensaje = new SimpleStringProperty();
        }

        public SimpleStringProperty getTitulo() {
            return titulo;
        }

        public void setTitulo(SimpleStringProperty titulo) {
            this.titulo = titulo;
        }

        public SimpleStringProperty getMensaje() {
            return mensaje;
        }

        public void setMensaje(SimpleStringProperty mensaje) {
            this.mensaje = mensaje;
        }
    }
}
