package org.example.cincuentazo.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to represent the table of the game
 * @author Olman Alexander Silva 2343025-2724
 * @version 1.0
 */

public class GamingTable {
    private final Deck deck;
    private final List<Player> players;
    private int tableScore;

    public GamingTable(Deck deck) {
        this.deck = deck;
        this.players = new ArrayList<>();
        this.tableScore = 0;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void playTurn(Player player) {
        if (player.canContinue()) {
            Cards playedCard = player.playCard();
            if (playedCard != null) {
                int cardValue = Integer.parseInt(playedCard.getRank().replaceAll("[^0-9]", "10")); // Ajustar lógica
                tableScore += cardValue;
                player.updateScore(cardValue);
            }
        }
    }

    public int getTableScore() {
        return tableScore;
    }

    public Player checkWinner() {
        return players.stream().filter(Player::canContinue).findFirst().orElse(null);
    }

    public boolean isGameOver() {
        return players.stream().filter(Player::canContinue).count() == 1;
    }
}
