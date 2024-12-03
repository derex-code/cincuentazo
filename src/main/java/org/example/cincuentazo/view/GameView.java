package org.example.cincuentazo.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * GameView class represent the view of the game.
 * @author Olman Alexander Silva 2343025-2724
 * @version 1.0
 */

public class GameView extends Stage {
    public GameView() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/org/example/cincuentazo/game-view.fxml")
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

    public static GameView getInstance() throws IOException {
        if (GameView.GameViewHolder.INSTANCE == null) {
            return GameView.GameViewHolder.INSTANCE = new GameView();
        } else {
            return GameView.GameViewHolder.INSTANCE;
        }
    }

    private static class GameViewHolder {
        private static GameView INSTANCE;
    }
}
