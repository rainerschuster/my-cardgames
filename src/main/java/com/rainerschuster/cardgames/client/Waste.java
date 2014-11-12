package com.rainerschuster.cardgames.client;

import java.util.List;

import com.rainerschuster.cardgames.client.games.CardGame;

/**
 * <blockquote cite="http://en.wikipedia.org/w/index.php?title=Glossary_of_Patience_terms&oldid=626298777">
 * The area where the cards from the stock go when they are brought into play.
 * </blockquote>
 * 
 * @author Rainer Schuster
 */
public class Waste extends Pile {

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
        this(cardGame, "waste"); //$NON-NLS-1$
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
