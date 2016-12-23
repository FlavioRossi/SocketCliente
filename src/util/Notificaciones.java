/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import com.jfoenix.controls.JFXButton;
import controlador.FXML_notificacionController;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import javax.swing.JFrame;
import org.json.simple.JSONArray;

/**
 *
 * @author FLAVIO
 */
public class Notificaciones {
    private final int x;
    /**
     * Ventana principal de notificaciones
     */
    private final JFrame frame;
    /**
     * Contenedor de notificaiciones
     */
    private final VBox contenedor;
    /**
     * Lista de notificaciones
     */
    private final ObservableList<JSONArray> notificaciones = FXCollections.observableArrayList();

    /**
     * Carga notificación 
     */
    public Notificaciones() {
        frame = new JFrame();
        
        frame.setType(Window.Type.UTILITY);
        frame.setUndecorated(true);
        frame.getContentPane().setBackground(new java.awt.Color(1.0f,1.0f,1.0f,0.0f));
        frame.setBackground(new java.awt.Color(1.0f,1.0f,1.0f,0.0f));
        
        final JFXPanel mainJFXPanel = new JFXPanel();

        contenedor = new VBox();
        contenedor.setStyle("-fx-background-color: transparent;");
        contenedor.setSpacing(10);
        
        Scene scene = new Scene(contenedor);
        scene.setFill(Color.TRANSPARENT);
        mainJFXPanel.setScene(scene);
        
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
        Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
        x = (int) rect.getMaxX() - 400 - 5;
        int y = (int) rect.getMaxY() - 45;
        frame.setLocation(x, y);
        frame.setAlwaysOnTop(true);
        frame.getContentPane().add(mainJFXPanel);
        frame.pack();
        frame.setVisible(true);
        
        frame.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseEntered(MouseEvent e) {
                System.out.println("Dentro");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                System.out.println("fuera");
            }
        });
    }
    
    /**
     * Agrega notificación a la lista
     * @param titulo -> Titulo de la notificación
     * @param mensaje -> Mensaje de la notificación
     * @param mostrar -> Indica si muestra o no la notificación
     * @throws IOException 
     */
    public void addNotificacion(String titulo, String mensaje, boolean mostrar) throws IOException{
        System.out.println(mostrar);
        if (mostrar) {
            System.out.println("notificaciones");
            muestraNotificaicón(titulo, mensaje);
        }
    }
    
    /**
     * Muestra notificación al cliente
     * @param titulo
     * @param mensaje
     * @throws IOException 
     */
    private void muestraNotificaicón(String titulo, String mensaje) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/FXML_notificacion.fxml"));
        HBox fxml = (HBox) loader.load();
        FXML_notificacionController controlador = (FXML_notificacionController) loader.getController();
        controlador.setMensaje(mensaje);
        controlador.setTitulo(titulo);
        
        fxml.visibleProperty().addListener((obs, ov, nv) -> {
            System.out.println("mostrando elemento");
            if (nv) {
                FadeTransition ft = new FadeTransition();
                ft.onFinishedProperty().set((EventHandler<ActionEvent>) (ActionEvent event) -> {
                    try {
                        Thread.sleep(5000);
                        FadeTransition ft2 = new FadeTransition();
                        ft2.setNode(fxml);
                        ft2.setFromValue(0);
                        ft2.setToValue(1);
                        ft2.setDuration(Duration.seconds(3));
                        ft2.play();
                        
                        ft2.onFinishedProperty().set((EventHandler<ActionEvent>) (ActionEvent event2) -> {
                            contenedor.getChildren().remove(fxml);
                            frame.setSize(new Dimension(400, frame.getBounds().height - (int) fxml.getPrefHeight()));
                            frame.setLocation(x, frame.getLocation().y + (int) fxml.getPrefHeight());
                        });
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Notificaciones.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
                ft.setNode(fxml);
                ft.setFromValue(0);
                ft.setToValue(1);
                ft.setDuration(Duration.seconds(3));
                ft.play();
            }
        });
        JFXButton btn_cerrar = (JFXButton) fxml.lookup("#btn_cerrar");
        btn_cerrar.setOnAction(e -> {
            cerrar(fxml);
        });
        Platform.runLater(()->{
            contenedor.getChildren().add(fxml);
            frame.setSize(new Dimension(400, frame.getBounds().height + (int) fxml.getPrefHeight()));
            frame.setLocation(x, frame.getLocation().y - (int) fxml.getPrefHeight());
        });
        
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException ex) {
//            Logger.getLogger(Notificaciones.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        cerrar(fxml);
    }
    
    private void cerrar(HBox node){
        FadeTransition ft = new FadeTransition();
        ft.onFinishedProperty().set((EventHandler<ActionEvent>) (ActionEvent event) -> {
            contenedor.getChildren().remove(node);
            frame.setSize(new Dimension(400, frame.getBounds().height - (int) node.getPrefHeight()));
            frame.setLocation(x, frame.getLocation().y + (int) node.getPrefHeight());
        });
        ft.setNode(node);
        ft.setFromValue(1);
        ft.setToValue(0);
        ft.setDuration(Duration.seconds(3));
        ft.play();
    }
}
