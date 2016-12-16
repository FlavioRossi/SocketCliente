/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.time.LocalDateTime;

/**
 *
 * @author FLAVIO
 */
public class Usuario {
    /**
     * Numero de identificación del servidor
     */
    private int id;
    /**
     * Nombre completo del usuario
     */
    private String nombre;
    /**
     * Fecha y hora en que ingreso al sistema
     * Formato de la clase Status 
     */
    private LocalDateTime ingreso;
    /**
     * Fecha y hora en que se desconecto del sistema
     */
    private LocalDateTime salida;
    /**
     * Mostrar notificaciones o no
     */
    private boolean notificaciones;
    
    public Usuario() {
        notificaciones = true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDateTime getIngreso() {
        return ingreso;
    }

    public void setIngreso(LocalDateTime ingreso) {
        this.ingreso = ingreso;
    }

    public LocalDateTime getSalida() {
        return salida;
    }

    public void setSalida(LocalDateTime salida) {
        this.salida = salida;
    }

    public boolean isNotificaciones() {
        return notificaciones;
    }

    public void setNotificaciones(boolean notificaciones) {
        this.notificaciones = notificaciones;
    }
    
    
}
