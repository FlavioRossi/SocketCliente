/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notificaciones;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Window;
import java.io.IOException;
import java.util.List;
import javafx.animation.FadeTransition;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import javax.swing.JFrame;

/**
 *
 * @author FLAVIO
 */
public class Notificaciones {
    /**
     * Ventana principal de notificaciones
     */
    private final JFrame FRAME;
    /**
     * Contenedor de notificaiciones
     */
    private final JFXPanel CONTENEDOR;
    /**
     * Tiempo que se muestra la notificación en milisegundos
     */
    private final long DURACION = 2000;

    /**
     * Carga notificación 
     */
    public Notificaciones() {
        FRAME = new JFrame();
        
        FRAME.setType(Window.Type.UTILITY);
        FRAME.setUndecorated(true);
        FRAME.getContentPane().setBackground(new java.awt.Color(1.0f,1.0f,1.0f,0.0f));
        FRAME.setBackground(new java.awt.Color(1.0f,1.0f,1.0f,0.0f));
        
        CONTENEDOR = new JFXPanel();
    }
    
    public void showNotificacion(List<Notificacion> notificaciones) throws IOException{
        FRAME.setVisible(false);
        
        StackPane fxmlNotificacion = FXMLLoader.load(getClass().getResource("/fxml/FXML_notificacion.fxml"));

        JFXButton btn_cerrar = (JFXButton) fxmlNotificacion.lookup("#btn_cerrar");
        btn_cerrar.setOnAction(e -> {
            cerrar(fxmlNotificacion);
        });
        
        JFXListView fxmlListaNotificacion = FXMLLoader.load(getClass().getResource("/fxml/FXML_listaNotificaciones.fxml"));
        
        StackPane.setMargin(fxmlListaNotificacion, new Insets(35,0,5,0));
        fxmlListaNotificacion.setExpanded(true);
        fxmlListaNotificacion.setDepthProperty(1);
        
        fxmlNotificacion.getChildren().add(fxmlListaNotificacion);
        
        Scene scene = new Scene(fxmlNotificacion);
        scene.setFill(Color.TRANSPARENT);
        CONTENEDOR.setScene(scene);
        
        FRAME.setSize(new Dimension(400, (int) scene.getHeight()));
        
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
        Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
        int x = (int) rect.getMaxX() - 400 - 5;
        int y = (int) rect.getMaxY() - 45;
        FRAME.setLocation(x, y - (int) scene.getHeight());
        
        FRAME.setAlwaysOnTop(true);
        FRAME.getContentPane().removeAll();
        FRAME.getContentPane().add(CONTENEDOR);
        FRAME.setFocusableWindowState(false);
        FRAME.setVisible(true);
        
        FadeTransition ft = new FadeTransition();
        ft.setNode(fxmlNotificacion);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.setDuration(Duration.seconds(0.5));
        ft.play(); 
        
        final MediaPlayer SONIDO = new MediaPlayer(new Media(getClass().getResource("/music/Clear.mp3").toString()));
        SONIDO.play();
        app.App.tray.showIconoNotificacion();
        
        fxmlNotificacion.hoverProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (!newValue)cerrar(fxmlNotificacion);
        });
    }
    
    private void cerrar(Parent node){
        FadeTransition ft = new FadeTransition();
        ft.onFinishedProperty().set((EventHandler<ActionEvent>) (ActionEvent event) -> {
            FRAME.getContentPane().removeAll();
            FRAME.setVisible(false);
        });
        ft.setNode(node);
        ft.setFromValue(1);
        ft.setToValue(0);
        ft.setDuration(Duration.seconds(0.5));
        ft.play();
    }
}
