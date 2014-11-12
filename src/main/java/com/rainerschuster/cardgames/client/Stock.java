package com.rainerschuster.cardgames.client;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.rainerschuster.cardgames.client.games.CardGame;

/**
 * <blockquote cite="http://en.wikipedia.org/w/index.php?title=Glossary_of_card_game_terms&oldid=613941263">
 * A pile of cards, face down, which are left over after setting up the rest of the game (ie. dealing hands, setting up other layout areas).
 * </blockquote>
 * 
 * @author Rainer Schuster
 */
public class Stock extends Pile implements HasClickHandlers {

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
