/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexion;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modelo.Usuario;
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
    
    public Cliente() {
        this.conexion = new ConexionServidor(Status.IP_SERVER, Status.PUERTO_SOCKET, 3, 5);
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
        System.out.println("lista : " + notificacion.toString());
        Notificaciones show = new Notificaciones();
        try {
            show.showNotificacion(notificacion.stream().collect(Collectors.toList()));
        } catch (IOException ex) {
            System.out.println("Error al mostrar notificaciones");
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean abrirConexion(){
        return conexion.conectar();
    }
    
    public boolean cerrarConexion(){
        return conexion.cerrar();
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
