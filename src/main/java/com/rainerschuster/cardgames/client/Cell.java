package com.rainerschuster.cardgames.client;

import com.rainerschuster.cardgames.client.games.CardGame;

/**
 * A cell is a pile with maximum 1 card allowed.
 * 
 * @author Rainer Schuster
 */
public class Cell extends Pile {

    public Cell(CardGame cardGame, String id, CGLayout layout, CGVisibility visibility, CGEmptyStart emptyStart, Building buildingAdd, CGRemoveRule ruleRemove) {
        super(cardGame, id, layout, visibility, emptyStart, buildingAdd, ruleRemove);
        // Set max. number of cards to 1 per default
        setLimit(1);
    }

    public Cell(CardGame cardGame, String id, CGLayout layout, CGVisibility visibility, CGEmptyStart emptyStart, Building buildingAdd, BuildingBySteps buildingRemove) {
        super(cardGame, id, layout, visibility, emptyStart, buildingAdd, buildingRemove);
        // Set max. number of cards to 1 per default
        setLimit(1);
    }

    public Cell(CardGame cardGame, String id) {
        this(cardGame, id, CGLayout.STACK, CGVisibility.ALL, CGEmptyStart.NULL, new BuildingInSequence(BuildingBySteps.Direction.NULL, BuildingBySteps.Suit.NULL, false), new BuildingInSequence(BuildingBySteps.Direction.NULL, BuildingBySteps.Suit.NULL, false));
    }

}