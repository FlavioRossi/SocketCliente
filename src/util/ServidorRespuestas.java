/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import conexion.Cliente;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.NoSuchElementException;
import notificaciones.Notificacion;
import notificaciones.NotificacionMsj;
import org.json.simple.JSONObject;

/**
 *
 * @author FLAVIO
 */
public class ServidorRespuestas {
    /**
     * Recibe un objeto JSON con la respuesta del servidor
     * Formato: "parametro", "resul"
     * Devuelve: un Object el cual debe ser casteado segun lo que esperamos
     * @param json
     */
    public static void responder(JSONObject json){
        Cliente cliente = Cliente.getInstance();
        
        int valor = Integer.parseInt(json.get("parametro").toString());
        JSONObject resul = (JSONObject) json.get("resul");
        
        switch(valor){
            case 1:
                //LOGIN DE USUARIO
                String logueo = (String) resul.get("logueo");
                if (!logueo.equals("ok")) {
                    System.out.println("Clave y contraseña erronea");
                    cliente.cerrarConexion();
                }
                
                //Casteo de datos
                int id = Integer.parseInt(resul.get("id").toString());
                String nombre = (String) resul.get("nombre");
                String ingreso = (String) resul.get("ingreso");
                
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
                    
                    Notificacion notificacion;
                    try {
                        notificacion = cliente.getNotificacion()
                                                .stream()
                                                .filter(n -> n.getTipo() == modulo)
                                                .findFirst()
                                                .get();
                    } catch (NoSuchElementException e) {
                        notificacion = new Notificacion(modulo, "CORREO INTERNO");
                    }
                    JSONObject correoInterno = (JSONObject) resul.get("" + modulo);
                    Iterator iterador = correoInterno.values().iterator();
                    for (int i = 1; i < correoInterno.size() + 1; i++) {
                        JSONObject msjs = (JSONObject) iterador.next();

                        String origen = msjs.get("nombreD").toString();
                        String mensaje = msjs.get("mensaje").toString();

                        NotificacionMsj msj;
                        if (origen == null) {
                            msj = new NotificacionMsj(mensaje);
                        }else{
                            msj = new NotificacionMsj(mensaje, origen);
                        }
                        notificacion.addMensaje(msj);
                    }
                    cliente.getNotificacion().add(notificacion);
                }
                cliente.showNotificacion();
                break;
            case 3:
                // ALGO MAS
                break;
        }
    }
}
