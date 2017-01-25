/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexion;

import org.json.simple.JSONObject;

/**
 *
 * @author FLAVIO
 */
public class ClienteRespuestas {
    public static JSONObject login(String usuario, String clave){
        JSONObject json = new JSONObject();
        json.put("usuario", usuario);
        json.put("clave", clave);
        
        return json;
    }
}
