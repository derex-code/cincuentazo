package org.example.cincuentazo.model;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * Represent the cards of the game
 * @author Olman Alexander Silva 2343025-2724
 * @version 1.0
 */
public class Cards extends Group {
    private final String rank; //Rank of the cards
    private final String suit; // suit of the cards

    /**
     * Constructor
     * @param rank represent the value of the cards
     * @param suit represent hearts, diamonds, clubs and spades
     */
    public Cards(String rank, String suit, double width, double height ) {
        this.rank = rank;
        this.suit = suit;

        //Shape of the card
        Rectangle cardBackground = new Rectangle(width, height);
        cardBackground.setArcWidth(10);
        cardBackground.setArcHeight(10);
        cardBackground.setFill(Color.WHITE);
        cardBackground.setStroke(Color.BLACK);

        //Text for the rank and suit
        Text rankText = new Text(rank);
        //Red for hearts and diamonds
        rankText.setFill(suit.equals("♥") || suit.equals("♦")? Color.RED : Color.BLACK);
        //rankText.setStyle("-fx-font-size: 18px;");
        rankText.setStyle("-fx-font-size: " + (width / 5) + "px;");
//        rankText.setX(10);
//        rankText.setY(20);
        rankText.setX(width * 0.1); // Relative position
        rankText.setY(height * 0.3);

        Text suitText = new Text(suit);
        suitText.setFill(rankText.getFill());
       // suitText.setStyle("-fx-font-size: 18px;");
        suitText.setStyle("-fx-font-size: " + (width / 5) + "px;");

//        suitText.setX(10);
//        suitText.setY(40);
        suitText.setX(width * 0.1); // Relative position
        suitText.setY(height * 0.6);

        //To group elements
        this.getChildren().addAll(cardBackground, rankText, suitText);
    }

    /**
     * Method to get the value of the card
     * @return rank
     */
    public String getRank() {
        return rank;
    }

    /**
     * Metodo para copnvertir el rango de la carta a int
     */
    public int getValue(){
        return Integer.parseInt(rank);
    }

    public String getSuit() {
        return suit;
    }
}
