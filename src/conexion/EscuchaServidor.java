/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexion;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import util.ServidorRespuestas;

/**
 *
 * @author FLAVIO
 */
class EscuchaServidor implements Runnable {

    private static final Logger LOG = Logger.getLogger(EscuchaServidor.class.getName());
    
    private final ConexionServidor conexion;
    public EscuchaServidor() {
        this.conexion = ConexionServidor.getInstancia();
    }

    @Override
    public void run() {
        while (conexion.isESTADO_CONEXION()) {
            String mensaje = conexion.recibir();
            if (mensaje != null) {
                JSONObject respond;
                try {
                    respond = (JSONObject) new JSONParser().parse(mensaje);
                    System.out.println("llego del servidor " + mensaje);
                    
                    //verifico si es condicion de salida
                    int valor = Integer.parseInt(respond.get("parametro").toString());
                    if (valor == 9999) {
                        conexion.setESTADO_CONEXION(false);
                    }else{
                        ServidorRespuestas.responder(respond);
                    }
                } catch (ParseException ex) {
                    LOG.log(Level.SEVERE, ex.toString(), ex);
                    System.out.println("El mensaje recibido del servidor no es un formato valido de JSON, msj: " + mensaje);
                }
            }
        }
    }
    
}
