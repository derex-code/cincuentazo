package org.example.cincuentazo;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.cincuentazo.view.GameView;

import java.io.IOException;

/**
 * Main Class represent the inicializacion of the app.
 * @author Olman Alexander Silva 2343025-2724
 * @version 1.0
 */

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        GameView.getInstance();
    }
}