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
public class NotificacionMsj {
    private String msj;
    private String origen;
    private boolean visto;

    public NotificacionMsj(String msj, String origen) {
        this.msj = msj;
        this.origen = origen;
    }

    public NotificacionMsj(String msj) {
        this.msj = msj;
        this.origen = null;
    }

    public boolean isVisto() {
        return visto;
    }

    public void setVisto(boolean visto) {
        this.visto = visto;
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
    
    
}
