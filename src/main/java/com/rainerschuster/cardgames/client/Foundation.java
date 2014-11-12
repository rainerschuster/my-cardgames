package com.rainerschuster.cardgames.client;

import com.rainerschuster.cardgames.client.games.CardGame;

/**
 * <blockquote cite="http://en.wikipedia.org/w/index.php?title=Glossary_of_Patience_terms&oldid=626298777">
 *  Typically squared and face-up. Most solitaire games feature foundation piles (often referred to as foundations) built on foundation cards (usually the Ace). The aim of these games is to clear the tableaux and move all the cards to the foundation piles. Foundation piles are typically built from the foundation card by suit until the card at the other end of the sequence is reached (if the foundation card is the Ace, this is the King); however, some games have different rules. Usually only thirteen cards are allowed in each foundation. The number of foundations can usually be found by multiplying the number of suits by the number of decks involved in the game.
 * </blockquote>
 * 
 * @author Rainer Schuster
 */
public class Foundation extends Pile {

    public Foundation(CardGame cardGame, String id, CGLayout layout, CGVisibility visibility, CGEmptyStart emptyStart, Building buildingAdd, CGRemoveRule ruleRemove) {
        super(cardGame, id, layout, visibility, emptyStart, buildingAdd, ruleRemove);
    }

    public Foundation(CardGame cardGame, String id, CGLayout layout, CGVisibility visibility, CGEmptyStart emptyStart, Building buildingAdd, Building buildingRemove) {
        super(cardGame, id, layout, visibility, emptyStart, buildingAdd, buildingRemove);
    }

    public Foundation(CardGame cardGame, String id) {
        this(cardGame, id, CGLayout.STACK, CGVisibility.ALL, CGEmptyStart.ACE, new BuildingInSequence(BuildingBySteps.Direction.UP, BuildingBySteps.Suit.SUIT, false), new BuildingInSequence(BuildingBySteps.Direction.UP, BuildingBySteps.Suit.SUIT, false));
    }

}
