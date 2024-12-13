package org.example.cincuentazo.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to represent the players of the game
 * @author Olman Alexander Silva 2343025-2724
 * @version 1.0
 */

public class Player {
    private final String name;
    private final List<Cards> hand;
    private boolean canPlay;
    private final boolean isUser;

    /**
     * Method constructor of the class
     * @param name represent the name of the player
     */
    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
        this.canPlay = true;
        this.isUser = name.equals("User");
    }

    /**
     * Method to add cards
     * @param card represent the added card
     */
    public void addCard(Cards card) {
        hand.add(card);
    }

    /**
     * Method to play a card
     * @return represent the first movement
     */
    public Cards playCard() {
        if (!hand.isEmpty() && canPlay) {
            return hand.removeFirst();
        }
        return null;
    }

    /**
     * Method to return the name of the player
     * @return returns player's name
     */
    public String getName() {
        return name;
    }

    /**
     * Method to return the hand of the player
     * @return returns hand's player
     */
    public List<Cards> getHand() {
        return hand;
    }

    /**
     * Method to check if the player is a user
     * @return boolean is user or not.
     */
    public boolean isUser() {
        return isUser;
    }
}

