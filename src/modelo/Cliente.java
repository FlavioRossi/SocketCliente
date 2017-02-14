/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import conexion.ConexionServidor;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import notificaciones.Notificacion;
import notificaciones.Notificaciones;
import org.json.simple.JSONObject;
import util.Status;

/**
 *
 * @author FLAVIO
 */
public class Cliente {
    private static final Logger LOG = Logger.getLogger(Cliente.class.getName());
    
    private static Cliente instanciaCliente = null;
    
    private final ConexionServidor conexion;
    private final Usuario usuario;
    private final ObservableList<Notificacion> notificacion;
    
    private Cliente() {
        this.conexion = ConexionServidor.getInstancia();
        this.usuario = new Usuario();
        notificacion = FXCollections.observableArrayList();
    }

    public static Cliente getInstance(){
        if (instanciaCliente == null) {
            instanciaCliente = new Cliente();
        }
        return instanciaCliente;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }

    public ObservableList<Notificacion> getNotificacion() {
        return notificacion;
    }

    public void showNotificacion(){
        Notificaciones show = new Notificaciones();
        try {
            show.showNotificacion(notificacion.stream().collect(Collectors.toList()));
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean abrirConexion(){
        return conexion.conectar();
    }
    
    public boolean cerrarConexion(){
        JSONObject json = new JSONObject();
        json.put("estado", "baja");
        return conexion.enviar(9999, json);
    }
    
    public boolean isConectado(){
        return conexion.isESTADO_CONEXION();
    }
    
    public void login(){
        JSONObject json = new JSONObject();
        json.put("usuario", Status.nombre);
        json.put("clave", Status.clave);
        
        conexion.enviar(1, json);
    }

}
