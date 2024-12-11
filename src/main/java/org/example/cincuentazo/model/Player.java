package org.example.cincuentazo.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represent the players of the game, using threads.
 * @author Olman Alexander Silva 2343025-2724
 * @version 1.0
 */

public class Player {
    private final String name;
    private final List<Cards> hand;
    private int score;
    private boolean canPlay;

    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
        this.score = 0;
        this.canPlay = true;
    }

    public void addCard(Cards card) {
        hand.add(card);
    }

    public Cards playCard() {
        if (!hand.isEmpty() && canPlay) {
            return hand.remove(0); // Simula jugar la primera carta de la mano
        }
        return null;
    }

    public boolean canContinue() {
        return canPlay;
    }

    public void updateScore(int cardValue) {
        score += cardValue;
        if (score > 50) {
            canPlay = false;
        }
    }

    public String getName() {
        return name;
    }

    public List<Cards> getHand() {
        return hand;
    }

    public int getScore() {
        return score;
    }
}

