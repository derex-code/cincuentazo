package org.example.cincuentazo.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Deck class represent the deck that contains 52 cards, each with a rank (A, 2-10, J, Q, K) and a suit (♠, ♥, ♦, ♣).
 * @author Olman Alexander Silva 2343025-2724
 * @version 1.0
 */
public class Deck {
    private final List<Cards> pokerCards;
    /**
     * Constructs a deck of 52 poker cards, initializing each card with the specified width and height.
     *
     * @param cardWidth  the width of each card in the deck.
     * @param cardHeight the height of each card in the deck.
     */
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

    /**
     * Method to verificate if the pokercards is empty
     * @return true or false
     */
    public boolean isEmpty() {
        return pokerCards.isEmpty();
    }

    /**
     * Method to add a card back to the deck.
     * This ensures the deck never runs out of cards.
     *
     * @param card the card to be added back to the deck.
     */
    public void returnCardToDeck(Cards card) {
        pokerCards.add(card); // Adds the card back to the deck
        shuffleDeck(); // Optionally shuffle the deck after returning the card
    }

    /**
     * Method to shuffle the deck.
     * This is used when returning a card to the deck.
     */
    private void shuffleDeck() {
        Collections.shuffle(pokerCards); // Shuffle the deck
    }
}
