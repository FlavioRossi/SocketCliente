/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notificaciones;

import java.awt.Image;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.MouseAdapter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javax.swing.ImageIcon;

/**
 *
 * @author FLAVIO
 */
public class NotificacionTray {
    private final SystemTray SYSTEM_TRAY;
    private final TrayIcon TRAY_ICON;
    
    /**
     * Imagenes por defecto
     */
    private final ImageIcon ICON;
    private final ImageIcon ICON_NOTIFICACION;
    private final ImageIcon ICON_SIN_CONEXION;
    private final ImageIcon ICON_CONECTANDO;
    
    private static final Logger LOG = Logger.getLogger(NotificacionTray.class.getName());
    
    public NotificacionTray(String titulo) {
        Platform.setImplicitExit(false);
        
        SYSTEM_TRAY = SystemTray.getSystemTray();
        
        //Imagenes por defecto
        ICON = new ImageIcon(getClass().getResource("/img/logo.png"));
        ICON_NOTIFICACION = new ImageIcon(getClass().getResource("/img/iconoNotifica.png"));
        ICON_SIN_CONEXION = new ImageIcon(getClass().getResource("/img/iconoSinConexion.png"));
        ICON_CONECTANDO = new ImageIcon(getClass().getResource("/img/iconoConectando.png"));
        
        TRAY_ICON = new TrayIcon(ICON.getImage(), titulo);
        TRAY_ICON.setImageAutoSize(true);
        
        try {
            SYSTEM_TRAY.add(TRAY_ICON);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, ex.toString(), ex);
        }
    }
    
    public void setClickMouseListener(MouseAdapter mouseAdapter){
        TRAY_ICON.addMouseListener(mouseAdapter);
    }
    public void setPopupMenu(PopupMenu popup){
        TRAY_ICON.setPopupMenu(popup);
    }
    public void setIcono(Image imagen){
        TRAY_ICON.setImage(imagen);
    }
    
    public void showIconoNotificacion(){
        TRAY_ICON.setImage(ICON_NOTIFICACION.getImage());
    }
    public void showIconoDefecto(){
        TRAY_ICON.setImage(ICON.getImage());
    }
    public void showIconoSinConexion(){
        TRAY_ICON.setImage(ICON_SIN_CONEXION.getImage());
    }
    public void showIconoConectando(){
        TRAY_ICON.setImage(ICON_CONECTANDO.getImage());
    }
    
    public void showMensaje(String titulo, String mensaje, TrayIcon.MessageType tipo){
        TRAY_ICON.displayMessage(titulo, mensaje, tipo);
    }

    public void close() {
        SYSTEM_TRAY.remove(TRAY_ICON);
    }
}
