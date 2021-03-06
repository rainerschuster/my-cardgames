package com.rainerschuster.cardgames.client;

/**
 * @author Rainer Schuster
 */
public class BuildingInMultiples extends BuildingBySteps {

    private int step;

    public BuildingInMultiples(int step) {
        super();
        this.step = step;
    }

    @Override
    protected boolean acceptsStep(final int cardRankValue1, final int cardRankValue2) {
        return Math.abs(cardRankValue1 - cardRankValue2) == step;
    }

}
