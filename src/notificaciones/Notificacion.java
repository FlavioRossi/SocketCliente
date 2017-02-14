/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notificaciones;

/**
 *
 * @author FLAVIO
 */
public class Notificacion {
    private final int tipo;
    private String titulo;
    private String msj;
    private String origen;
    private boolean visto;

    public Notificacion(int tipo, String titulo, String msj, String origen, boolean visto) {
        this.tipo = tipo;
        this.titulo = titulo;
        this.msj = msj;
        this.origen = origen;
        this.visto = visto;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMsj() {
        return msj;
    }

    public void setMsj(String msj) {
        this.msj = msj;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public boolean isVisto() {
        return visto;
    }

    public void setVisto(boolean visto) {
        this.visto = visto;
    }

    
}
