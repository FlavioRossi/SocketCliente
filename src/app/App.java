/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import modelo.Cliente;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import javax.swing.SwingUtilities;
import notificaciones.NotificacionTray;
import util.Status;

/**
 *
 * @author FLAVIO
 */
public class App extends Application {
    private static final Logger LOG = Logger.getLogger(App.class.getName());
    
    private Stage stage;
    public static NotificacionTray tray;
    public static Cliente cliente = null;
    
    @Override
    public void start(Stage stage){
        this.stage = stage;
        tray = initializeTrayNotification();
        
        cliente = Cliente.getInstance();
        conectarCliente();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Status.nombre = args[0];
        Status.clave = args[1];
        
        launch(args);
    }

    private NotificacionTray initializeTrayNotification() {
        tray = new NotificacionTray("APOLO");
       
        //Menu POPUP
        PopupMenu menu = new PopupMenu();
        MenuItem itemCerrar = new MenuItem("Cerrar APOLO");
        itemCerrar.addActionListener((i) -> {
            close();
        });
        menu.add(itemCerrar);
        tray.setPopupMenu(menu);
                
        //ACTION LISTENER
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                
                if (SwingUtilities.isLeftMouseButton(e)) {
                    Platform.runLater(() -> {
                        try {
                            Parent fxml = FXMLLoader.load(getClass().getResource("/fxml/FXML_app.fxml"));
                            Scene scene = new Scene(fxml);
                            stage.setTitle("APOLO");
                            stage.setScene(scene);
                            stage.show();

                            stage.setOnCloseRequest((WindowEvent event) -> {
                                stage.hide();
                            });

                        } catch (IOException ex) {
                            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                        }    
                    });
                }
            }
        };
        tray.setClickMouseListener(mouseAdapter);
        
        return tray;
    }
    
    public static void close(){
        if (cliente.isConectado()) {
            cliente.cerrarConexion();
        }
        tray.close();
        Platform.exit();
        System.exit(0);
    }

    public static void conectarCliente() {
        if (!cliente.isConectado()) {
            if (cliente.abrirConexion()) {
                cliente.login();
            }else{
                cliente.cerrarConexion();
            }
        }
    }
}
