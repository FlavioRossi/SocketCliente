/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import com.jfoenix.controls.JFXRippler;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author FLAVIO
 */
public class FXML_notificacionItemController implements Initializable {

    @FXML
    private ImageView imageView_foto;
    @FXML
    private Label label_titulo;
    @FXML
    private Label label_mensaje;
    @FXML
    private HBox hBox_container;
    @FXML
    private AnchorPane anchorPane_root;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        JFXRippler rippler = new JFXRippler(hBox_container);
        anchorPane_root.getChildren().add(rippler);
        hBox_container.prefWidthProperty().bind(anchorPane_root.widthProperty());
    }    

    public ImageView getImageView_foto() {
        return imageView_foto;
    }

    public void setImageView_foto(ImageView imageView_foto) {
        this.imageView_foto = imageView_foto;
    }

    public String getLabel_titulo() {
        return label_titulo.getText();
    }

    public void setLabel_titulo(String label_titulo) {
        this.label_titulo.setText(label_titulo);
    }

    public String getLabel_mensaje() {
        return label_mensaje.getText();
    }

    public void setLabel_mensaje(String label_mensaje) {
        this.label_mensaje.setText(label_mensaje);
    }
    
    
}
