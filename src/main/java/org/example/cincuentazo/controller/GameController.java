package org.example.cincuentazo.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.example.cincuentazo.model.Alerts.AlertBox;

import java.util.Optional;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import org.example.cincuentazo.model.Deck;
import org.example.cincuentazo.model.Player;

import java.util.ArrayList;
import java.util.List;


public class GameController {
    public GridPane userGame;
    public GridPane playerMachine3;
    public GridPane playerMachine2;
    public GridPane playerMachine1;
    public GridPane gridPaneTable;

    private List<Player> players = new ArrayList<>();
    private Player currentPlayer;
    private Deck deck;
    private int numberOfMachinePlayers;

    @FXML
    public void onActionStarGameButton(ActionEvent event) {
        System.out.println("ActionEvent");

        // Mostrar la carta boca abajo en la mesa (en gridPaneTable)
        ImageView imageViewTable = new ImageView(new Image(getClass().getResource("/org/example/cincuentazo/Images/cardBack.png").toExternalForm()));
        imageViewTable.setFitHeight(100);
        imageViewTable.setFitWidth(90);
        gridPaneTable.add(imageViewTable, 1, 0);

        // Cuadro de diálogo para seleccionar el número de jugadores máquina
        TextInputDialog dialog = new TextInputDialog("1");
        Image icon = new Image(getClass().getResourceAsStream("/org/example/cincuentazo/Images/icon.png"));
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(icon);
        dialog.setTitle("Cincuentazo Game");
        dialog.setHeaderText("Number of players");
        dialog.setContentText("How many machine players do you want to play (maximum 3):");

        Optional<String> result = dialog.showAndWait();

        // Procesar entrada del usuario
        result.ifPresent(input -> {
            try {
                int players = Integer.parseInt(input);
                if (players >= 1 && players <= 3) {
                    numberOfMachinePlayers = players;
                    System.out.println("Número de jugadores seleccionado: " + numberOfMachinePlayers);
                } else {
                    showErrorDialog("Please enter a valid number between 1 and 3.");
                }
            } catch (NumberFormatException e) {
                showErrorDialog("Invalid input. Please enter a number.");
            }
        });

        // Inicializar el juego
        initializeGame();

        // Mostrar cartas boca abajo para cada máquina
        displayMachineCards();

        // Iniciar el ciclo de turnos
        new Thread(this::startGameLoop).start();
    }

    private void initializeGame() {
        deck = new Deck(90, 140);
        deck.suffle();

        // Crear jugador usuario
        players.add(new Player("User"));

        // Crear jugadores máquina según la selección
        for (int i = 1; i <= numberOfMachinePlayers; i++) {
            players.add(new Player("Machine " + i));
        }

        // Dar 4 cartas a cada jugador
        for (Player player : players) {
            for (int i = 0; i < 4; i++) {
                player.addCard(deck.drawCard());
            }
        }

        currentPlayer = players.get(0); // Usuario empieza
    }

    private void displayMachineCards() {
        ImageView imageView1 = new ImageView(new Image(getClass().getResource("/org/example/cincuentazo/Images/cardsBack.png").toExternalForm()));
        imageView1.setFitHeight(85);
        imageView1.setFitWidth(140);
        playerMachine1.add(imageView1, 0, 0);

        if (numberOfMachinePlayers > 1) {
            ImageView imageView2 = new ImageView(new Image(getClass().getResource("/org/example/cincuentazo/Images/cardsBack.png").toExternalForm()));
            imageView2.setFitHeight(85);
            imageView2.setFitWidth(153);
            playerMachine2.add(imageView2, 0, 0);
        }

        if (numberOfMachinePlayers > 2) {
            ImageView imageView3 = new ImageView(new Image(getClass().getResource("/org/example/cincuentazo/Images/cardsBack.png").toExternalForm()));
            imageView3.setFitHeight(97);
            imageView3.setFitWidth(153);
            playerMachine3.add(imageView3, 0, 0);
        }
    }

    private void startGameLoop() {
        while (true) {
            if (currentPlayer.getName().equals("User")) {
                waitForUserTurn();
            } else {
                playMachineTurn(currentPlayer);
            }

            // Cambiar al siguiente jugador
            nextPlayer();
        }
    }

    private void waitForUserTurn() {
        // Lógica de turno del usuario en la interfaz gráfica
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Your Turn");
            alert.setHeaderText(null);
            alert.setContentText("It's your turn! Play a card and draw another.");
            alert.showAndWait();
        });

        // Pendiente colocar una acción del usuario (botón, drag & drop, etc.)
    }

    private void playMachineTurn(Player machine) {
        try {
            Thread.sleep((long) (2000 + Math.random() * 2000)); // Esperar entre 2-4 segundos
            Platform.runLater(() -> {
                System.out.println(machine.getName() + " played a card.");
                // Mostrar carta en gridPaneTable
                ImageView cardImage = new ImageView(new Image(getClass().getResource("/org/example/cincuentazo/Images/cardFront.png").toExternalForm()));
                cardImage.setFitHeight(100);
                cardImage.setFitWidth(90);
                gridPaneTable.add(cardImage, 0, 0);
            });

            machine.addCard(deck.drawCard()); // Tomar nueva carta
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void nextPlayer() {
        int currentIndex = players.indexOf(currentPlayer);
        currentPlayer = players.get((currentIndex + 1) % players.size());
    }

    private void showErrorDialog(String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }
}

