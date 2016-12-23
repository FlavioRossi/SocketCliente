/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.Initializable;
import modelo.Cliente;

/**
 * FXML Controller class
 *
 * @author FLAVIO
 */
public class FXML_appController implements Initializable {
    private Cliente cliente;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            cliente = new Cliente();
        } catch (IOException ex) {
            Logger.getLogger(FXML_appController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error al conectar con el servidor");
        }
    }
}
