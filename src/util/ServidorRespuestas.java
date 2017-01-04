/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Cliente;
import notificaciones.Notificacion;
import notificaciones.NotificacionMsj;
import org.json.simple.JSONObject;

/**
 *
 * @author FLAVIO
 */
public class ServidorRespuestas {
    /**
     * Instancia del cliente
     */
    private final Cliente cliente;

    
    
    public ServidorRespuestas(Cliente cliente) {
        this.cliente = cliente;
    }

    /**
     * Recibe un objeto JSON con la respuesta del servidor
     * Formato: "parametro", "resul"
     * Devuelve: un Object el cual debe ser casteado segun lo que esperamos
     * @param json 
     */
    public void responder(JSONObject json){
        int valor = Integer.parseInt(json.get("parametro").toString());
        JSONObject resul = (JSONObject) json.get("resul");
        
        switch(valor){
            case 1:
                //LOGIN DE USUARIO
                String logueo = (String) resul.get("logueo");
                int id = Integer.parseInt(resul.get("id").toString());
                String nombre = (String) resul.get("nombre");
                String ingreso = (String) resul.get("ingreso");
                
                //Seteo estado de conexión del cliente
                cliente.setEstado(logueo.equals("ok"));
                
                //Seteo información del usuario
                cliente.getUsuario().setId(id);
                cliente.getUsuario().setNombre(nombre);
                cliente.getUsuario().setIngreso(LocalDateTime.parse(ingreso, Status.FORMATO_FECHA_HORA));
                cliente.getUsuario().setSalida(LocalDateTime.MIN);
                break;
            case 2:
                //NOTIFICACIONES
                for (Object key : resul.keySet()) {
                    int modulo = Integer.parseInt(key.toString());
                    switch(modulo){
                        case 1:
                            Notificacion notificacion = new Notificacion(1, "CORREO INTERNO");
                            
                            JSONObject correoInterno = (JSONObject) resul.get("" + modulo);
                            for (int i = 1; i < correoInterno.size() + 1; i++) {
                                JSONObject msjs = (JSONObject) correoInterno.get("" + i);
                                
                                String origen = msjs.get("nombreD").toString();
                                String mensaje = msjs.get("mensaje").toString();
                                
                                NotificacionMsj msj;
                                if (origen == null) {
                                    msj = new NotificacionMsj(mensaje);
                                }else{
                                    msj = new NotificacionMsj(mensaje, origen);
                                }
                                msj.setVisto(false);
                                notificacion.addMensaje(msj);
                            }
                            {
                                try {
                                    cliente.getNotificaciones().addNotificacion(notificacion);
                                } catch (IOException ex) {
                                    System.out.println("Error al crear notificación");
                                    Logger.getLogger(ServidorRespuestas.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                            break;
                    }
                }
                {
                    try {
                        cliente.getNotificaciones().showNotificacion();
                    } catch (IOException ex) {
                        System.out.println("Error al mostrar notificaciones");
                        Logger.getLogger(ServidorRespuestas.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            case 3:
                // ALGO MAS
                break;
        }
    }
}
