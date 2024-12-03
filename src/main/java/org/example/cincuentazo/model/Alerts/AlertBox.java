package org.example.cincuentazo.model.Alerts;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class AlertBox implements AlertBoxInterface {
    @Override
    public void showAlert(String title, String header, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        Image icon = new Image(getClass().getResourceAsStream("/org/example/cincuentazo/Images/icon.png"));
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(icon);
        alert.showAndWait();
    }
}
