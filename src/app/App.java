/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.Status;

/**
 *
 * @author FLAVIO
 */
public class App extends Application {
    
    @Override
    public void start(Stage stage) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/fxml/FXML_app.fxml"));
        Scene scene = new Scene(fxml);
        stage.setTitle("Socket cliente - Organización Informática");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Status.nombre = args[0];
        Status.clave = args[1];
        launch(args);
    }
    
}
