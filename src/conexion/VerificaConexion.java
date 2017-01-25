/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexion;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;

/**
 *
 * @author FLAVIO
 */
class VerificaConexion implements Runnable {

    
    private final int TIEMPO_PULSO = 5000;
    private final ConexionServidor conexion;
    
    private static final Logger LOG = Logger.getLogger(VerificaConexion.class.getName());
    
    public VerificaConexion(ConexionServidor conexion) {
        this.conexion = conexion;
    }

    @Override
    public void run() {
        JSONObject pulso = new JSONObject();
        pulso.put("estado", 0);
        
        while(conexion.isESTADO_CONEXION()){
            try {
                Thread.sleep(TIEMPO_PULSO);
                conexion.setESTADO_CONEXION(conexion.enviar(0, pulso));
            } catch (InterruptedException ex) {
                LOG.log(Level.SEVERE, null, ex);
            }
        }
        conexion.cerrar();
    }
}
