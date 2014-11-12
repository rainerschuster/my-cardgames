package com.rainerschuster.cardgames.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.user.client.ui.Widget;
import com.rainerschuster.cardgames.client.games.CardGame;

/**
 * <blockquote cite="http://en.wikipedia.org/w/index.php?title=Glossary_of_card_game_terms&oldid=613941263">
 * The cards held by one player.
 * </blockquote>
 * 
 * @author Rainer Schuster
 */
public class Hand extends Pile {

    public Hand(CardGame cardGame, String id, CGLayout layout,
            CGVisibility visibility, CGEmptyStart emptyStart,
            Building buildingAdd, CGRemoveRule ruleRemove) {
        super(cardGame, id, layout, visibility, emptyStart, buildingAdd, ruleRemove);
        setBuildingDirection(CGBuildingDirection.RIGHT);
    }

    public Hand(CardGame cardGame, String id, CGLayout layout,
            CGVisibility visibility, CGEmptyStart emptyStart,
            Building buildingAdd, Building buildingRemove) {
        super(cardGame, id, layout, visibility, emptyStart, buildingAdd, buildingRemove);
        setBuildingDirection(CGBuildingDirection.RIGHT);
    }

    private final Comparator<Card> cardComparator = new Comparator<Card>() {
        @Override
        public int compare(final Card card1, final Card card2) {
            if (card1.getSuit() < card2.getSuit()) {
                return -1;
            }
            if (card1.getSuit() > card2.getSuit()) {
                return 1;
            }
            if (card1.getRank() < card2.getRank()) {
                return -1;
            }
            if (card1.getRank() > card2.getRank()) {
                return 1;
            }
            return 0;
        }
    };

    @Override
    public boolean addAllCards(final List<Card> widgets) {
        super.addAllCards(widgets);
        final List<Card> list = new ArrayList<Card>();
        for (Iterator<Widget> iter = getChildren().iterator(); iter.hasNext();) {
            list.add((Card) iter.next());
            iter.remove();
        }
        Collections.sort(list, cardComparator);
        return super.addAllCards(list);
    }

    @Override
    public boolean removeAllCards(final List<Card> widgets) {
        // After removing sorting is not necessary, only the empty spaces have to be filled
        if (super.removeAllCards(widgets)) {
            final List<Card> list = new ArrayList<Card>();
            for (Iterator<Widget> iter = getChildren().iterator(); iter.hasNext();) {
                list.add((Card) iter.next());
                iter.remove();
            }
            if (!super.addAllCards(list)) {
                return false;
            }
            return true;
        }
        return false;
    }

}
