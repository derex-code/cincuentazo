package org.example.cincuentazo.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import org.example.cincuentazo.model.Alerts.AlertBox;

public class GameController {
    public GridPane userGame;
    public GridPane playerMachine3;
    public GridPane playerMachine2;
    public GridPane playerMachine1;
    public TextField textFieldSuma;
    public ColumnConstraints deckDown;
    @FXML
    private Button starGameButton;

    @FXML
    public void onActionStarGameButton(ActionEvent event){
        System.out.println("ActionEvent");
        new AlertBox().showAlert(
                "Cincuentazo Game",
                "If you are the last one",
                "You'll win"
        );


    }


}
