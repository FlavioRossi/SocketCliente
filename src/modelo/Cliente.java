/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import notificaciones.Notificaciones;
import util.ServidorRespuestas;
import util.Status;

/**
 *
 * @author FLAVIO
 */
public final class Cliente {
    /**
     * Socket de conexión con el servidor
     */
    private final Socket socket;
    /**
     * Lectura del servidor
     */
    private final DataInputStream input;
    /**
     * Escritura del servidor
     */
    private final DataOutputStream output;
    /**
     * Notificaciones del cliente
     */
    private final Notificaciones notificaciones;
    /**
     * Parametros de respuestas del servidor
     */
    private final ServidorRespuestas servidorRespuestas;
    
    /**
     * Estado de conexión del cliente (conectado/desconectado)
     * Cuando el cliente se desconecta se detiene la recepción del servidor
     * Cuando el cliente se conecta se reanuda la recepción del servidor
     */
    private final BooleanProperty estado;
    /**
     * Hilo de lectura de respuestas del servidor
     */
    private Thread recibeLeeCliente;
    /**
     * Información del usuario recibida por el servidor
     */
    private final Usuario usuario;
    
    /**
     * LOG de la clase 
     */
    private static final Logger LOG = Logger.getLogger(Cliente.class.getName());
    
    public Cliente() throws IOException {
        //Conecto el socket con el servidor
        socket = new Socket(Status.IP_SERVER, Status.PUERTO_SOCKET);
        input = new DataInputStream(socket.getInputStream());
        output = new DataOutputStream(socket.getOutputStream());
        
        usuario = new Usuario();
        notificaciones = new Notificaciones();
        servidorRespuestas = new ServidorRespuestas(this);
        
        //Recibe lo que envia el servidor
        Task tareaLeeCliente = new Task() {
            @Override
            protected Object call() throws Exception {
                while(true){
                    String mensaje = input.readUTF();
                    JSONObject respond = (JSONObject) new JSONParser().parse(mensaje);
                    servidorRespuestas.responder(respond);
                }
            }
        };
        recibeLeeCliente = new Thread(tareaLeeCliente);
        
        estado = new SimpleBooleanProperty();
        estado.addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (!newValue) {
                recibeLeeCliente.interrupt();
            }else{
                recibeLeeCliente.start();
            }
        });
        loginCliente();
    }
    
    /**
     * Devuelve instancia de usuario
     * @return 
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Devuelve etado de conexión del cliente
     * @return 
     */
    public boolean getEstado() {
        return estado.get();
    }

    /**
     * Setea conexión del cliente
     * @param estado 
     */
    public void setEstado(boolean estado) {
        this.estado.set(estado);
    }
    
    /**
     * Verifico usuario y contraseña en el servidor 
     */
    public void loginCliente(){
        JSONObject json = new JSONObject();
        json.put("usuario", Status.nombre);
        json.put("clave", Status.clave);

        servidorRespuestas.responder(enviar(1, json));
    }
    
    /**
     * Devuelvo lista de notificaciones
     * @return 
     */
    public Notificaciones getNotificaciones() {
        return notificaciones;
    }
    
    /**
     * Envio petición al servidor
     * @param parametro ->Tipo de operación que va a realizar el servidor
     * @param resul ->Parametros para la resolución
     * @return ->Retorna la respuesta del servidor
     *         ->Formato de respuesta Json: "parametro", "resul"
     */
    public JSONObject enviar(int parametro, JSONObject resul){
        JSONObject json = new JSONObject();
        json.put("parametro", parametro);
        json.put("resul", resul);
        
        JSONObject respond = new JSONObject();
        try {
            //Envia al servidor
            output.writeUTF(json.toString());
            //Respuesta del servidor
            String mensaje = input.readUTF();
            respond = (JSONObject) new JSONParser().parse(mensaje);
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            LOG.log(Level.SEVERE, "Error al enviar al servidor");
        } catch (ParseException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            LOG.log(Level.SEVERE, "Error al parsear respuesta del servidor");
        }
        return respond;
    }
}