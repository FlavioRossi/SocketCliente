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
import javafx.beans.property.SimpleBooleanProperty;
import org.json.simple.JSONObject;

/**
 *
 * @author FLAVIO
 */
public class ConexionServidor {
    /**
     * LOG de la clase 
     */
    private static final Logger LOG = Logger.getLogger(ConexionServidor.class.getName());
    
    private final String ip;
    private final int puerto;
    private final int intentos;
    private final int tiempoReconexion;
    
    private SimpleBooleanProperty ESTADO_CONEXION;
    
    private final Thread verificaConexionT;
    private final Thread escuchaServidorT;
    
    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;
    
    public ConexionServidor(String ip, int puerto, int intentos, int tiempoReconexion){
        this.ip = ip;
        this.puerto = puerto;
        this.intentos = intentos;
        this.tiempoReconexion = tiempoReconexion;
        
        ESTADO_CONEXION = new SimpleBooleanProperty(false);
        
        verificaConexionT = new Thread(new VerificaConexion(this));
        escuchaServidorT = new Thread(new EscuchaServidor(this));
    }

    public SimpleBooleanProperty getESTADO_CONEXION() {
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
        
        for (int i = 0; i < intentos; i++) {
            try {
                //intenta conectar con el servidor durante <tiempoReconexion>
                socket.connect(sockaddr, 0);
                input = new DataInputStream(socket.getInputStream());
                output = new DataOutputStream(socket.getOutputStream());
                
                verificaConexionT.start();
                escuchaServidorT.start();
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
            ESTADO_CONEXION.set(false);
            return false;
        }
        
        ESTADO_CONEXION.set(true);
        return true;
    }

    public boolean enviar(int parametro, JSONObject mensaje) {
        JSONObject json = new JSONObject();
        json.put("parametro", parametro);
        json.put("resul", mensaje);
        
        try {
            output.writeUTF(json.toString());
            return true;
        } catch (IOException e) {
            System.out.println("Servidor desconectado");
            return false;
        }
    }

    public String recibir(){
        String recibe = "";
        try {
            recibe = input.readUTF();
            return recibe;
        } catch (IOException ex) {
            System.out.println("Recibio " + recibe);
            LOG.log(Level.SEVERE, ex.toString(), ex);
            return null;
        }
    }
    
    public boolean cerrar() {
        setESTADO_CONEXION(false);
        try {
            if (!socket.isClosed()) 
                socket.close();
                
            if (!verificaConexionT.isInterrupted()) 
                verificaConexionT.interrupt();
            
            if (!escuchaServidorT.isInterrupted()) 
                escuchaServidorT.interrupt();
            
            return true;
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, ex.toString(), ex);
            return false;
        }
    }
}
