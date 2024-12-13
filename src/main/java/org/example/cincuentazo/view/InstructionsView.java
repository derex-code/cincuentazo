package org.example.cincuentazo.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class InstructionsView extends Stage {
    public InstructionsView() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/org/example/cincuentazo/instructions-view.fxml")
        );
        Parent root = loader.load();
        this.setTitle("CINCUENTAZO GAME");
        Scene scene = new Scene(root);
        this.setScene(scene);
        this.getIcons().add(new Image(
                getClass().getResource("/org/example/cincuentazo/Images/icon.png").toString()
        ));
        this.show();
    }

    public static InstructionsView getInstance() throws IOException {
        if (InstructionsView.InstructionsViewHolder.INSTANCE == null) {
            return InstructionsView.InstructionsViewHolder.INSTANCE = new InstructionsView();
        } else {
            return InstructionsView.InstructionsViewHolder.INSTANCE;
        }
    }

    private static class InstructionsViewHolder {
        private static InstructionsView INSTANCE;
    }
}
