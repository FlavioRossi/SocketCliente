/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import modelo.Cliente;
import java.time.LocalDateTime;
import java.util.Iterator;
import notificaciones.Notificacion;
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
    public static void responder(JSONObject json) {
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
                    
                    JSONObject correoInterno = (JSONObject) resul.get("" + modulo);
                    Iterator iterador = correoInterno.values().iterator();
                    while (iterador.hasNext()){
                        JSONObject msjs = (JSONObject) iterador.next();

                        String origen = msjs.get("nombreD").toString();
                        String mensaje = msjs.get("mensaje").toString();

                        Notificacion notifica = new Notificacion(modulo, "CORREO INTERNO", mensaje, origen, false);
                        cliente.getNotificacion().add(notifica);
                    }
                }
                cliente.showNotificacion();
                break;
        }
    }
}
