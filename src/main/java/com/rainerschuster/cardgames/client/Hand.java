package com.rainerschuster.cardgames.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.user.client.ui.Widget;
import com.rainerschuster.cardgames.client.games.CardGame;

/**
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

    Comparator<Card> cardComparator = new Comparator<Card>() {
        @Override
        public int compare(Card card1, Card card2) {
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
    public boolean addAllCards(List<Card> widgets) {
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
    public boolean removeAllCards(List<Card> widgets) {
        // After removing sorting is not necessary, only the empty spaces have to be filled
        if (super.removeAllCards(widgets)) {
            List<Card> list = new ArrayList<Card>();
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
