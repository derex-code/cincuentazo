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
    private final boolean isUser;
    public int maxSum;

    /**
     * Method constructor of the class
     * @param name represent the name of the player
     */
    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
        this.isUser = name.equals("User");
        this.maxSum = 50;
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
    public Cards playCard(int currentSum) {
        for (int i = 0; i < hand.size(); i++) {
            Cards card = hand.get(i);
            if (currentSum + card.getValue() <= maxSum) {
                return hand.remove(i);
            }
        }
        return null;
    }

    /**
     * Method to determinate if the cards of the hand are playable
     * @param currentSum represent the current sum
     * @return boolean to know if it has a playable card
     */
    public boolean hasPlayableCard(int currentSum) {
        for (Cards card : hand) {
            if (currentSum + card.getValue() <= maxSum) {
                return true; //There is at least one card that meets the rule
            }
        }
        return false; //There isn't a valid card
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

    /**
     * Method to return the value of the maxSum
     * @return maxSum
     */
    public int getMaxSum() {
        return maxSum;
    }
}

