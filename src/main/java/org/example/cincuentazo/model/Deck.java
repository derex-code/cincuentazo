package org.example.cincuentazo.model;

import javax.smartcardio.Card;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represent all the cards in the deck
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
     * Shuffle the cards of the deck
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
            return pokerCards.remove(0);
        }
        return null;
    }

//    /**
//     * Crea una representación visual del mazo usando un TilePane.
//     *
//     * @return TilePane con las cartas distribuidas.
//     */
//    public TilePane renderDeck() {
//        TilePane tilePane = new TilePane();
//        tilePane.setHgap(10);
//        tilePane.setVgap(10);
//
//        for (PokerCard card : cards) {
//            tilePane.getChildren().add(card);
//        }
//
//        return tilePane;
//    }

    /**
     * Return the cards in the deck
     * @return The actual number of the card in the deck
     */
    public int getSize(){
        return pokerCards.size();
    }

}
