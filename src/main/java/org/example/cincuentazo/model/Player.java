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
     * constructor de la clase
     * @param name representa el nombre del jugador
     */
    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
        this.canPlay = true;
        this.isUser = name.equals("User");
    }

    /**
     * metodo para adherir cartas
     * @param card representa la carta adherida
     */
    public void addCard(Cards card) {
        hand.add(card);
    }

    /**
     * Metodo para jugar una carta
     * @return representa el primer movimiento
     */
    public Cards playCard() {
        if (!hand.isEmpty() && canPlay) {
            return hand.removeFirst(); // Simula jugar la primera carta de la mano
        }
        return null;
    }

    /**
     * Metodo que retorna el nombre del jugador
     * @return representa el nombre del jugador
     */
    public String getName() {
        return name;
    }

    /**
     * Metodo que retorna la mano del jugador
     * @return representa la mano del jugador
     */
    public List<Cards> getHand() {
        return hand;
    }

    /**
     * Metodo para verificar si es el jugador usuario
     */
    public boolean isUser() {
        return isUser;
    }
}

