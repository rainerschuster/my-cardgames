package com.rainerschuster.cardgames.client;

import com.rainerschuster.cardgames.client.games.CardGame;

/**
 * @author Rainer Schuster
 */
public class Tableau extends Pile {

    public Tableau(CardGame cardGame, String id, CGLayout layout,
            CGVisibility visibility, CGEmptyStart emptyStart,
            Building buildingAdd, CGRemoveRule ruleRemove) {
        super(cardGame, id, layout, visibility, emptyStart, buildingAdd, ruleRemove);
        buildingAdd.setCheckAll(false);
    }

    public Tableau(CardGame cardGame, String id, CGLayout layout,
            CGVisibility visibility, CGEmptyStart emptyStart,
            Building buildingAdd, Building buildingRemove) {
        super(cardGame, id, layout, visibility, emptyStart, buildingAdd, buildingRemove);
        buildingAdd.setCheckAll(false);
    }

    public Tableau(CardGame cardGame, String id, Building buildingAdd, Building buildingRemove) {
        super(cardGame, id, CGLayout.CASCADE, CGVisibility.ALL, CGEmptyStart.NULL, buildingAdd, buildingRemove);
        buildingAdd.setCheckAll(false);
    }

}
