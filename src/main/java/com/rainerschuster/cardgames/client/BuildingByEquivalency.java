package com.rainerschuster.cardgames.client;

/**
 * @author Rainer Schuster
 */
public class BuildingByEquivalency extends Building {

    public BuildingByEquivalency() {
        super();
    }

    @Override
    protected boolean accepts(Card card1, Card card2) {
        return card1.getSuit() == card2.getSuit()
                || card1.getRank() == card2.getRank();
    }

}
