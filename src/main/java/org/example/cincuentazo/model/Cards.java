package org.example.cincuentazo.model;

/**
 * Represent the cards of the game
 * @author Olman Alexander Silva 2343025-2724
 * @version 1.0
 */
public class Cards {
    private int rank; //Rank of the cards
    private int suit; // suit of the cards

    /**
     * Constructor
     * @param rank represent the value of the cards
     * @param suit represent hearts, diamonds, clubs and spades
     */
    public Cards(int rank, int suit) {} //Constructor

    /**
     * Method to get the value of the card
     * @return rank
     */
    public int getRank() {
        return rank;
    }
}
