package com.rainerschuster.cardgames.client;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.rainerschuster.cardgames.client.games.CardGame;

/**
 * @author Rainer Schuster
 */
public class Stock extends Pile implements HasClickHandlers {

    //static final Building defaultBuildingAdd = new BuildingInSequence(Building.Direction.NULL, Building.Suit.NULL, false);
    //static final Building defaultBuildingRemove = new BuildingInSequence(Building.Direction.NULL, Building.Suit.NULL, false);

    /*static {
		defaultBuildingAdd = new BuildingInSequence();
		defaultBuildingAdd.setDirection(Building.Direction.NULL);
		defaultBuildingAdd.setSuit(Building.Suit.NULL);
		defaultBuildingAdd.setWrap(true);

		defaultBuildingRemove = defaultBuildingAdd;
	}*/

    private Pile moveTarget = null;

    public Stock(CardGame cardGame, String id, CGLayout layout, CGVisibility visibility, CGEmptyStart emptyStart, Building buildingAdd, CGRemoveRule ruleRemove) {
        super(cardGame, id, layout, visibility, emptyStart, buildingAdd, ruleRemove);
    }

    public Stock(CardGame cardGame, String id, CGLayout layout, CGVisibility visibility, CGEmptyStart emptyStart, Building buildingAdd, Building buildingRemove) {
        super(cardGame, id, layout, visibility, emptyStart, buildingAdd, buildingRemove);
    }

    public Stock(CardGame cardGame, String id) {
        this(cardGame, id, CGLayout.STACK, CGVisibility.NONE, CGEmptyStart.NULL, new BuildingInSequence(BuildingBySteps.Direction.NULL, BuildingBySteps.Suit.NULL, false), new BuildingInSequence(BuildingBySteps.Direction.NULL, BuildingBySteps.Suit.NULL, false));
    }

    public Stock(CardGame cardGame) {
        this(cardGame, "stock"); //$NON-NLS-1$
    }

    public boolean correctOrder(final Card card1, final Card card2) {
        return true;
    }

    @Override
    public boolean acceptsDragAll(final List<Card> cards) {
        // It just doesn't look nice if the user drags a card from the talon
        return false;
    }

    public Pile getMoveTarget() {
        return moveTarget;
    }

    public void setMoveTarget(Pile moveTarget) {
        this.moveTarget = moveTarget;
    }

    @Override
    public HandlerRegistration addClickHandler(final ClickHandler handler) {
        return addDomHandler(handler, ClickEvent.getType());
    }

}
