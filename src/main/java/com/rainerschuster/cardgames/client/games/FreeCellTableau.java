package com.rainerschuster.cardgames.client.games;

import java.util.List;

import com.rainerschuster.cardgames.client.Building;
import com.rainerschuster.cardgames.client.Card;
import com.rainerschuster.cardgames.client.Tableau;

/**
 * @author Rainer Schuster
 */
public class FreeCellTableau extends Tableau {

    public FreeCellTableau(CardGame cardGame, String id, Building buildingAdd, Building buildingRemove) {
        super(cardGame, id, buildingAdd, buildingRemove);
    }

    public FreeCellTableau(CardGame cardGame, String id, CGLayout layout,
            CGVisibility visibility, CGEmptyStart emptyStart,
            Building buildingAdd, Building buildingRemove) {
        super(cardGame, id, layout, visibility, emptyStart, buildingAdd,
                buildingRemove);
    }

    @Override
    public boolean acceptsRemoveAll(List<Card> cards) {
        return super.acceptsRemoveAll(cards)
                && ((FreeCell) getCardGame()).getFreeCellCount() >= cards.size();
    }

}
