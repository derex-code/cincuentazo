package org.example.cincuentazo.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.example.cincuentazo.model.Alerts.AlertBox;

import java.util.Optional;

public class GameController {
    public GridPane userGame;
    public GridPane playerMachine3;
    public GridPane playerMachine2;
    public GridPane playerMachine1;
    public TextField textFieldSuma;
    public ColumnConstraints deckDown;
    public GridPane gridPaneTable;

    @FXML
    private Button starGameButton;

    @FXML
    public void onActionStarGameButton(ActionEvent event){
        System.out.println("ActionEvent");
//        new AlertBox().showAlert(
//                "Cincuentazo Game",
//                "If you are the last one",
//                "You'll win"
//        );
        //Carta boca abajo para el mazo
        ImageView imageViewTable = new ImageView(new Image(getClass().getResource("/org/example/cincuentazo/Images/cardBack.png").toExternalForm()));
        imageViewTable.setFitHeight(100); //alto
        imageViewTable.setFitWidth(90); //ancho
        gridPaneTable.add(imageViewTable, 1, 0);

        //Cuadro de dialogo para selecionar numero de jugadores
        TextInputDialog dialog = new TextInputDialog("1"); // Valor predeterminado: 1
        Image icon = new Image(getClass().getResourceAsStream("/org/example/cincuentazo/Images/icon.png"));
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(icon);
        dialog.setTitle("Cincuentazo Game");
        dialog.setHeaderText("Number of players");
        dialog.setContentText("Please, enter the number of players (maximum 3):");

        // Mostrar el cuadro de diálogo y esperar entrada del usuario
        Optional<String> result = dialog.showAndWait();

        // Procesar la entrada del usuario
        result.ifPresent(input -> {
            try {
                int numberOfPlayers = Integer.parseInt(input);
                if (numberOfPlayers >= 1 && numberOfPlayers <= 3) {
                    System.out.println("Número de jugadores seleccionado: " + numberOfPlayers);
                } else {
                    System.out.println("Por favor, ingrese un número válido entre 1 y 3.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor ingrese un número.");
            }
        });

        //Posicionar imagen de cartas boca abajo en cada gridpane de los jugadores maquina
        ImageView imageView = new ImageView(new Image(getClass().getResource("/org/example/cincuentazo/Images/cardsBack.png").toExternalForm()));
        imageView.setFitHeight(85); //alto
        imageView.setFitWidth(140); //ancho
        playerMachine1.add(imageView, 0, 0);

        ImageView imageView2 = new ImageView(new Image(getClass().getResource("/org/example/cincuentazo/Images/cardsBack.png").toExternalForm()));
        imageView2.setFitHeight(85); //alto
        imageView2.setFitWidth(153); //ancho
        playerMachine2.add(imageView2, 0, 0);
        ImageView imageView3 = new ImageView(new Image(getClass().getResource("/org/example/cincuentazo/Images/cardsBack.png").toExternalForm()));
        imageView3.setFitHeight(97); //alto
        imageView3.setFitWidth(153); //ancho
        playerMachine3.add(imageView3, 0, 0);


    }


}
