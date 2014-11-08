package com.rainerschuster.cardgames.client;

/**
 * @author Rainer Schuster
 */
public abstract class CardValues {

    //private CardGame cardGame;
    private Deck deck;

    public CardValues(/*CardGame cardGame, */Deck deck) {
        //this.cardGame = cardGame;
        this.deck = deck;
    }

    public abstract int getCardValue(Card card);

    /**
     * @return the deck
     */
    public Deck getDeck() {
        return deck;
    }

    /**
     * @param deck the deck to set
     */
    public void setDeck(Deck deck) {
        this.deck = deck;
    }

}
