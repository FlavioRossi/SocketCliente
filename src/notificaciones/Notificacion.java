/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notificaciones;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author FLAVIO
 */
public class Notificacion {
    private final int tipo;
    private String titulo;
    private final ObservableList<NotificacionMsj> mensajes;
    
    public Notificacion(int tipo, String titulo) {
        this.tipo = tipo;
        this.titulo = titulo;
        this.mensajes = FXCollections.observableArrayList();
    }

    public int getTipo() {
        return tipo;
    }
    
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<NotificacionMsj> getMensajes() {
        return mensajes;
    }

    public void addMensaje(NotificacionMsj msj){
        mensajes.add(msj);
    }
    
    public void addMensajes(List<NotificacionMsj> msjs){
        mensajes.addAll(msjs);
    }
    
    
}
