package com.rainerschuster.cardgames.client.games;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.rainerschuster.cardgames.client.BuildingBySteps;
import com.rainerschuster.cardgames.client.BuildingInSequence;
import com.rainerschuster.cardgames.client.CGSimpleDropController;
import com.rainerschuster.cardgames.client.Card;
import com.rainerschuster.cardgames.client.Foundation;
import com.rainerschuster.cardgames.client.FoundationGroup;
import com.rainerschuster.cardgames.client.Messages;
import com.rainerschuster.cardgames.client.Pile.CGEmptyStart;
import com.rainerschuster.cardgames.client.Pile.CGLayout;
import com.rainerschuster.cardgames.client.Pile.CGRemoveRule;
import com.rainerschuster.cardgames.client.Pile.CGVisibility;
import com.rainerschuster.cardgames.client.Stock;
import com.rainerschuster.cardgames.client.Table;
import com.rainerschuster.cardgames.client.Tableau;
import com.rainerschuster.cardgames.client.TableauGroup;
import com.rainerschuster.cardgames.client.Utils;
import com.rainerschuster.cardgames.client.Waste;
import com.rainerschuster.cardgames.client.ui.CardListener;
import com.rainerschuster.cardgames.client.ui.PileListener;

/**
 * @author Rainer Schuster
 */
public class Diplomat extends CardGame {

    private Messages messages = GWT.create(Messages.class);

    private Stock stock;
    private Waste waste;
    private Foundation foundation1;
    private Foundation foundation2;
    private Foundation foundation3;
    private Foundation foundation4;
    private Foundation foundation5;
    private Foundation foundation6;
    private Foundation foundation7;
    private Foundation foundation8;
    private Tableau tableau1;
    private Tableau tableau2;
    private Tableau tableau3;
    private Tableau tableau4;
    private Tableau tableau5;
    private Tableau tableau6;
    private Tableau tableau7;
    private Tableau tableau8;
    private FoundationGroup foundations;
    private TableauGroup tableaus;

    public Diplomat(Table table) {
        super(table);
    }

    @Override
    public void firstDeal() {
        // Load all cards
        //Deck.generatePrototypeDeck(this);
        final List<Card> deck1 = deck.newDeck(this);
        final List<Card> deck2 = deck.newDeck(this);
        final List<Card> allCards = deck1;
        allCards.addAll(deck2);
        //ArrayList allCards = deck.newDeck();

        // Shuffle cards
        // TODO Collections.shuffle(allCards);
        Utils.fisherYates(allCards);

        // Tableau
        dealTableauCards(allCards, 0, tableau1);
        dealTableauCards(allCards, 5, tableau2);
        dealTableauCards(allCards, 10, tableau3);
        dealTableauCards(allCards, 15, tableau4);
        dealTableauCards(allCards, 20, tableau5);
        dealTableauCards(allCards, 25, tableau6);
        dealTableauCards(allCards, 30, tableau7);
        dealTableauCards(allCards, 35, tableau8);

        // Stock
        stock.addAllCards(allCards.subList(40, allCards.size()));
    }

    private void dealTableauCards(List<Card> deck, int begin, Tableau target) {
        final List<Card> subList = deck.subList(begin, begin + 5);
        target.addAllCards(subList);
        for (Card card : subList) {
            card.showFront();
        }
    }

    @Override
    public void init() {
        foundations = new FoundationGroup();
        tableaus = new TableauGroup();

        stock = new Stock(this);
        stock.addCardListener(new CardListener(){
            @Override
            public void onCardClick(final Card sender) {
                stock.moveTo(waste, sender);
                sender.showFront();
            }

            @Override
            public void onCardDoubleClick(final Card sender) {
                // Not used
            }
        });

        final CardListener cardListener = new CardListener(){
            @Override
            public void onCardClick(final Card sender) {
                // Not used
            }

            // Move to foundation if allowed
            @Override
            public void onCardDoubleClick(final Card sender) {
                if (sender.getPile().acceptsRemove(sender)) {
                    Foundation foundation = foundations.getFoundation(sender/*.getSuit()*/);
                    if (foundation != null) {
                        if (foundation.acceptsAdd(sender)) {
                            sender.getPile().moveTo(foundation, sender);
                        }
                    }
                }
            }
        };

        waste = new Waste(this);
        waste.addCardListener(cardListener);
        stock.setMoveTarget(waste);

        foundation1 = new Foundation(this, "foundation1"); //$NON-NLS-1$
        foundation1.addPileListener(pl);
        new CGSimpleDropController(foundation1);
        foundations.add(foundation1);
        foundation2 = new Foundation(this, "foundation2"); //$NON-NLS-1$
        foundation2.addPileListener(pl);
        new CGSimpleDropController(foundation2);
        foundations.add(foundation2);
        foundation3 = new Foundation(this, "foundation3"); //$NON-NLS-1$
        foundation3.addPileListener(pl);
        new CGSimpleDropController(foundation3);
        foundations.add(foundation3);
        foundation4 = new Foundation(this, "foundation4"); //$NON-NLS-1$
        foundation4.addPileListener(pl);
        new CGSimpleDropController(foundation4);
        foundations.add(foundation4);
        foundation5 = new Foundation(this, "foundation5"); //$NON-NLS-1$
        foundation5.addPileListener(pl);
        new CGSimpleDropController(foundation5);
        foundations.add(foundation5);
        foundation6 = new Foundation(this, "foundation6"); //$NON-NLS-1$
        foundation6.addPileListener(pl);
        new CGSimpleDropController(foundation6);
        foundations.add(foundation6);
        foundation7 = new Foundation(this, "foundation7"); //$NON-NLS-1$
        foundation7.addPileListener(pl);
        new CGSimpleDropController(foundation7);
        foundations.add(foundation7);
        foundation8 = new Foundation(this, "foundation8"); //$NON-NLS-1$
        foundation8.addPileListener(pl);
        new CGSimpleDropController(foundation8);
        foundations.add(foundation8);

        final BuildingBySteps buildingAdd = new BuildingInSequence(BuildingBySteps.Direction.DOWN, BuildingBySteps.Suit.NULL, false);

        tableau1 = new Tableau(this, "tableau1", CGLayout.CASCADE, CGVisibility.ALL, CGEmptyStart.NULL, buildingAdd, CGRemoveRule.TOP); //$NON-NLS-1$
        tableau1.addCardListener(cardListener);
        tableaus.add(tableau1);
        tableau2 = new Tableau(this, "tableau2", CGLayout.CASCADE, CGVisibility.ALL, CGEmptyStart.NULL, buildingAdd, CGRemoveRule.TOP); //$NON-NLS-1$
        tableau2.addCardListener(cardListener);
        tableaus.add(tableau2);
        tableau3 = new Tableau(this, "tableau3", CGLayout.CASCADE, CGVisibility.ALL, CGEmptyStart.NULL, buildingAdd, CGRemoveRule.TOP); //$NON-NLS-1$
        tableau3.addCardListener(cardListener);
        tableaus.add(tableau3);
        tableau4 = new Tableau(this, "tableau4", CGLayout.CASCADE, CGVisibility.ALL, CGEmptyStart.NULL, buildingAdd, CGRemoveRule.TOP); //$NON-NLS-1$
        tableau4.addCardListener(cardListener);
        tableaus.add(tableau4);
        tableau5 = new Tableau(this, "tableau5", CGLayout.CASCADE, CGVisibility.ALL, CGEmptyStart.NULL, buildingAdd, CGRemoveRule.TOP); //$NON-NLS-1$
        tableau5.addCardListener(cardListener);
        tableaus.add(tableau5);
        tableau6 = new Tableau(this, "tableau6", CGLayout.CASCADE, CGVisibility.ALL, CGEmptyStart.NULL, buildingAdd, CGRemoveRule.TOP); //$NON-NLS-1$
        tableau6.addCardListener(cardListener);
        tableaus.add(tableau6);
        tableau7 = new Tableau(this, "tableau7", CGLayout.CASCADE, CGVisibility.ALL, CGEmptyStart.NULL, buildingAdd, CGRemoveRule.TOP); //$NON-NLS-1$
        tableau7.addCardListener(cardListener);
        tableaus.add(tableau7);
        tableau8 = new Tableau(this, "tableau8", CGLayout.CASCADE, CGVisibility.ALL, CGEmptyStart.NULL, buildingAdd, CGRemoveRule.TOP); //$NON-NLS-1$
        tableau8.addCardListener(cardListener);
        tableaus.add(tableau8);
    }

    @Override
    public void layout() {
        final VerticalPanel vp = new VerticalPanel();
        tableaus.setHeight("360px"); //$NON-NLS-1$
        vp.add(foundations);
        vp.add(tableaus);
        final HorizontalPanel hp = new HorizontalPanel();
        hp.add(stock);
        hp.add(waste);
        vp.add(hp);
        table.add(vp);
    }

    private final PileListener pl = new PileListener() {
        @Override
        public void onAdd() {
            if (isWon()) {
                Window.alert(messages.gameWon());
            }
        }

        @Override
        public void onRemove() {
            // Not used
        }
    };

    @Override
    public boolean isWon() {
        return (foundation1.getCardCount() == 13)
                && (foundation2.getCardCount() == 13)
                && (foundation3.getCardCount() == 13)
                && (foundation4.getCardCount() == 13)
                && (foundation5.getCardCount() == 13)
                && (foundation6.getCardCount() == 13)
                && (foundation7.getCardCount() == 13)
                && (foundation8.getCardCount() == 13);
    }

}
