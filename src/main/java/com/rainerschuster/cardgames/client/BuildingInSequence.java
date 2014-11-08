package com.rainerschuster.cardgames.client;

/**
 * @author Rainer Schuster
 */
public class BuildingInSequence extends BuildingBySteps {

    public BuildingInSequence(int direction, int suit, boolean wrap) {
        super(direction, suit, wrap);
    }

    @Override
    protected boolean acceptsStep(final int cardRankValue1, final int cardRankValue2) {
        return Math.abs(cardRankValue1 - cardRankValue2) == 1;
    }

}
