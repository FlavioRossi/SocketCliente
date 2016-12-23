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
                    String modulo = key.toString();
                    switch(modulo){
                        case "1":
                            JSONObject correoInterno = (JSONObject) resul.get(modulo);
                            for (int i = 0; i < correoInterno.size(); i++) {
                                JSONObject msj = (JSONObject) correoInterno.get(i);
                                cliente.getNotificaciones().addMsj
                                
                            }
                    }
                }
                
                String titulo = resul.get("nombreD").toString();
                String mensaje = resul.get("mensaje").toString();
                try {
                    cliente.getNotificaciones().addNotificacion("CORREO INTERNO - " + titulo, mensaje, cliente.getUsuario().isNotificaciones());
                } catch (IOException ex) {
                    Logger.getLogger(ServidorRespuestas.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
        }
    }
}
