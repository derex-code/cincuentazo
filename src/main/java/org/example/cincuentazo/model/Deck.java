package org.example.cincuentazo.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Deck class represent the deck that contein the cards
 * @author Olman Alexander Silva 2343025-2724
 * @version 1.0
 */
public class Deck {
    private final List<Cards> pokerCards;

    public Deck(double cardWidth, double cardHeight) {
        pokerCards = new ArrayList<>();
        String[] ranks = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        String[] suits = {"♠", "♥", "♦", "♣"};

        //Create each card and put into deck
        for (String suit : suits) {
            for (String rank : ranks) {
                pokerCards.add(new Cards(rank, suit, cardWidth, cardHeight));
            }
        }
    }

    /**
     * Method to shuffle the cards of the deck
     */
    public void suffle(){
        Collections.shuffle(pokerCards);
    }

    /**
     * Method to return the first card of the deck and deleted
     * of the list
     * @return The first card of the deck
     */
    public Cards drawCard(){
        if (!pokerCards.isEmpty()) {
            return pokerCards.removeFirst();
        }
        return null;
    }

//    /**
//     * Metodo para obtener u
//     */
//    public Cards getInitialCard() {
//        return pokerCards.getFirst(); // Retorna la primera carta sin removerla
//    }

    /**
     * Method to verificate if the pokercards is empty
     * @return true or false
     */

    public boolean isEmpty() {
        return pokerCards.isEmpty();
    }

//    /**
//     * Return the cards in the deck
//     * @return The actual number of the card in the deck
//     */
//    public int getSize(){
//        return pokerCards.size();
//    }

}
