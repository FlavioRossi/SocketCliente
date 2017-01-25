/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.time.format.DateTimeFormatter;
import javax.swing.ImageIcon;

/**
 *
 * @author FLAVIO
 */
public class Status {
    /**
     * IP del servidor donde se aloja la conexión del socket
     */
    public static final String IP_SERVER = "192.168.1.106";
    /**
     * PUERTO de la conexión del socket
     */
    public static final int PUERTO_SOCKET = 2222;
    
    /**
     * NOMBRE del usuario
     */
    public static String nombre;
    /**
     * CLAVE del usuario
     */
    public static String clave;
    
    /**
     * FORMATO fecha y hora del sistema
     */
    public static final DateTimeFormatter FORMATO_FECHA_HORA = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    /**
     * FORMATO fecha del sistema
     */
    public static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    /**
     * FORMATO hora del sistema
     */
    public static final DateTimeFormatter HORA = DateTimeFormatter.ofPattern("HH:mm:ss");
    
}
