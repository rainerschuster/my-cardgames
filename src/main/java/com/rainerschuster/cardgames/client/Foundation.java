package com.rainerschuster.cardgames.client;

import com.rainerschuster.cardgames.client.games.CardGame;

/**
 * @author Rainer Schuster
 */
public class Foundation extends Pile {

    //static final Building defaultBuildingAdd = new BuildingInSequence(Building.Direction.UP, Building.Suit.NULL, false);
    //static final Building defaultBuildingRemove = new BuildingInSequence(Building.Direction.UP, Building.Suit.NULL, false);

    /*static {
		defaultBuildingAdd = new BuildingInSequence();
		defaultBuildingAdd.setDirection(Building.Direction.UP);
		defaultBuildingAdd.setSuit(Building.Suit.SUIT);
		defaultBuildingAdd.setWrap(false);

		defaultBuildingRemove = defaultBuildingAdd;
	}*/

    public Foundation(CardGame cardGame, String id, CGLayout layout, CGVisibility visibility, CGEmptyStart emptyStart, Building buildingAdd, CGRemoveRule ruleRemove) {
        super(cardGame, id, layout, visibility, emptyStart, buildingAdd, ruleRemove);
    }

    public Foundation(CardGame cardGame, String id, CGLayout layout, CGVisibility visibility, CGEmptyStart emptyStart, Building buildingAdd, Building buildingRemove) {
        super(cardGame, id, layout, visibility, emptyStart, buildingAdd, buildingRemove);
    }

    public Foundation(CardGame cardGame, String id) {
        this(cardGame, id, CGLayout.STACK, CGVisibility.ALL, CGEmptyStart.ACE, new BuildingInSequence(BuildingBySteps.Direction.UP, BuildingBySteps.Suit.SUIT, false), new BuildingInSequence(BuildingBySteps.Direction.UP, BuildingBySteps.Suit.SUIT, false));
    }

    //@Override
    /*public boolean correctOrder(final Card card1, final Card card2) {
		return suitOrderEqual(card1, card2) && rankOrderAceToKing(card1, card2);
	}*/

}
