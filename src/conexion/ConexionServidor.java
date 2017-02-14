/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexion;

import java.awt.TrayIcon;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.channels.IllegalBlockingModeException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import org.json.simple.JSONObject;
import util.Status;

/**
 *
 * @author FLAVIO
 */
public class ConexionServidor {
    /**
     * LOG de la clase 
     */
    private static final Logger LOG = Logger.getLogger(ConexionServidor.class.getName());
    
    private final String ip = Status.IP_SERVER;
    private final int puerto = Status.PUERTO_SOCKET;
    private final int intentos = 3;
    private final int tiempoReconexion = 5;

    private final BooleanProperty ESTADO_CONEXION;
    
    private Thread verificaConexionT;
    private Thread escuchaServidorT;
    
    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;
    
    private static ConexionServidor instanciaConexionServidor = null;
    
    private ConexionServidor(){
        ESTADO_CONEXION = new SimpleBooleanProperty(false);
        ESTADO_CONEXION.addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            System.out.println("valor del estado de conexion: " + newValue + " oldvalue: " + oldValue);
            if (!newValue) {
                cerrar();
            }
        });
    }
    
    public static ConexionServidor getInstancia(){
        if (instanciaConexionServidor == null) {
            instanciaConexionServidor = new ConexionServidor();
        }
        return instanciaConexionServidor;
    }
    
    public BooleanProperty getESTADO_CONEXION() {
        return ESTADO_CONEXION;
    }
    public boolean isESTADO_CONEXION() {
        return ESTADO_CONEXION.get();
    }
    public void setESTADO_CONEXION(boolean estadoConexion) {
        this.ESTADO_CONEXION.set(estadoConexion);
    }
    
    public boolean conectar(){
        SocketAddress sockaddr = new InetSocketAddress(ip, puerto);
        socket = new Socket();
        
        app.App.tray.showIconoConectando();
        for (int i = 0; i < intentos; i++) {
            try {
                //intenta conectar con el servidor durante <tiempoReconexion>
                socket.connect(sockaddr);
                input = new DataInputStream(socket.getInputStream());
                output = new DataOutputStream(socket.getOutputStream());
                
                verificaConexionT = new Thread(new VerificaConexion());
                escuchaServidorT = new Thread(new EscuchaServidor());
                
                escuchaServidorT.start();
                verificaConexionT.start();
                
                break;
            } catch (IOException | IllegalArgumentException | IllegalBlockingModeException ex) {
                //LOG.log(Level.SEVERE, ex.toString(), ex);
                app.App.tray.showMensaje("Conexión no establecida", "Intento de conexión..." + (i + 1), TrayIcon.MessageType.INFO);
                
                try {
                    Thread.sleep(tiempoReconexion * 1000);
                } catch (InterruptedException ex1) {
                    Logger.getLogger(ConexionServidor.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
        }
        
        if (!socket.isConnected()) {
            app.App.tray.showMensaje("No se encuentra servidor", "Para volver a intentar presione sobre el globo", TrayIcon.MessageType.ERROR);
            app.App.tray.showIconoSinConexion();
            ESTADO_CONEXION.set(false);
            return false;
        }
        ESTADO_CONEXION.set(true);
        app.App.tray.showIconoDefecto();
        return true;
    }

    public boolean enviar(int parametro, JSONObject mensaje) {
        JSONObject json = new JSONObject();
        json.put("parametro", parametro);
        json.put("resul", mensaje);
        
        boolean respuesta = false;
        if (isESTADO_CONEXION()) {
            try {
                output.writeUTF(json.toString());
                System.out.println("Se envio al servidor: " + json.toString());
                respuesta = true;
            } catch (IOException e) {
                System.out.println("Servidor desconectado");
            }
        }
        return respuesta;
    }

    public String recibir(){
        try {
            String recibe = input.readUTF();
            return recibe;
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, ex.toString(), ex);
            return null;
        }
    }
    
    private boolean cerrar() {
        try {
            if (!verificaConexionT.isInterrupted()) 
                verificaConexionT.interrupt();
            
            if (!escuchaServidorT.isInterrupted()) 
                escuchaServidorT.interrupt();
            
            output.close();
            input.close();
            socket.close();
            
            return true;
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, ex.toString(), ex);
            return false;
        } 
    }
}
