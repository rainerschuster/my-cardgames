package com.rainerschuster.cardgames.client;

import java.util.List;

import com.rainerschuster.cardgames.client.games.CardGame;

/**
 * @author Rainer Schuster
 */
public class Waste extends Pile {

	//static final Building defaultBuildingAdd = new BuildingInSequence(Building.Direction.NULL, Building.Suit.NULL, false);
	//static final Building defaultBuildingRemove = new BuildingInSequence(Building.Direction.NULL, Building.Suit.NULL, false);

	/*static {
		defaultBuildingAdd = new BuildingInSequence();
		defaultBuildingAdd.setDirection(Building.Direction.NULL);
		defaultBuildingAdd.setSuit(Building.Suit.NULL);
		defaultBuildingAdd.setWrap(true);
		
		defaultBuildingRemove = defaultBuildingAdd;
	}*/

	public Waste(CardGame cardGame, String id, CGLayout layout, CGVisibility visibility, CGEmptyStart emptyStart, Building buildingAdd, CGRemoveRule ruleRemove) {
		super(cardGame, id, layout, visibility, emptyStart, buildingAdd, ruleRemove);
	}

	public Waste(CardGame cardGame, String id, CGLayout layout, CGVisibility visibility, CGEmptyStart emptyStart, Building buildingAdd, Building buildingRemove) {
		super(cardGame, id, layout, visibility, emptyStart, buildingAdd, buildingRemove);
	}

	public Waste(CardGame cardGame, String id) {
		this(cardGame, id, CGLayout.STACK, CGVisibility.ALL, CGEmptyStart.FUNC, new BuildingInSequence(BuildingBySteps.Direction.NULL, BuildingBySteps.Suit.NULL, false), new BuildingInSequence(BuildingBySteps.Direction.NULL, BuildingBySteps.Suit.NULL, false));
	}

	public Waste(CardGame cardGame) {
		this(cardGame, "waste");
	}

	public boolean correctOrder(final Card card1, final Card card2) {
		return true;
	}

	@Override
	public boolean acceptsFirstCard(final Card card) {
		return card.getPile() instanceof Stock;
	}
	
	@Override
	public boolean acceptsDropAll(final List<Card> cards) {
		return false;
	}

}
