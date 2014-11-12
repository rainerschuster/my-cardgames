package com.rainerschuster.cardgames.client;

import com.rainerschuster.cardgames.client.games.CardGame;

/**
 * <blockquote cite="http://en.wikipedia.org/w/index.php?title=Glossary_of_Patience_terms&oldid=626298777">
 * Common to "FreeCell" type games, cells allow only one card to be placed in them. Any card can be put in a cell. These act as maneuvering space.
 * </blockquote>
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
