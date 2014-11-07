package com.rainerschuster.cardgames.client;

import com.rainerschuster.cardgames.client.games.CardGame;

/**
 * @author Rainer Schuster
 */
public class Cell extends Pile {

	//static final Building defaultBuildingAdd = new BuildingInSequence(Building.Direction.NULL, Building.Suit.NULL, false);
	//static final Building defaultBuildingRemove = new BuildingInSequence(Building.Direction.NULL, Building.Suit.NULL, false);

	/*static {
		defaultBuildingAdd = new BuildingInSequence();
		defaultBuildingAdd.setDirection(Building.Direction.NULL);
		defaultBuildingAdd.setSuit(Building.Suit.NULL);
		defaultBuildingAdd.setWrap(false);
		
		defaultBuildingRemove = defaultBuildingAdd;
	}*/

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
