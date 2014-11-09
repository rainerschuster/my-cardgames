package com.rainerschuster.cardgames.client.games;

import java.util.Iterator;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.rainerschuster.cardgames.client.BuildingBySteps;
import com.rainerschuster.cardgames.client.BuildingInSequence;
import com.rainerschuster.cardgames.client.Card;
import com.rainerschuster.cardgames.client.Foundation;
import com.rainerschuster.cardgames.client.Messages;
import com.rainerschuster.cardgames.client.Pile;
import com.rainerschuster.cardgames.client.Stock;
import com.rainerschuster.cardgames.client.Table;
import com.rainerschuster.cardgames.client.Tableau;
import com.rainerschuster.cardgames.client.TableauGroup;
import com.rainerschuster.cardgames.client.Utils;
import com.rainerschuster.cardgames.client.ui.CardListener;
import com.rainerschuster.cardgames.client.ui.PileListener;

/**
 * @author Rainer Schuster
 */
public class SpiderSolitaire extends CardGame {

    private Messages messages = GWT.create(Messages.class);

    private Stock stock;
    private Foundation foundation1;
    private Tableau tableau1;
    private Tableau tableau2;
    private Tableau tableau3;
    private Tableau tableau4;
    private Tableau tableau5;
    private Tableau tableau6;
    private Tableau tableau7;
    private Tableau tableau8;
    private Tableau tableau9;
    private Tableau tableau10;
    private TableauGroup tableaus = new TableauGroup();

    public SpiderSolitaire(Table table) {
        super(table);
    }

    @Override
    public void init() {
        stock = new Stock(this);
        stock.addCardListener(new CardListener(){
            @Override
            public void onCardClick(final Card sender) {
                gameDeal();
            }

            @Override
            public void onCardDoubleClick(final Card sender) {
                // Not used
            }
        });
        foundation1 = new Foundation(this, "foundation1"); //$NON-NLS-1$
        // NOTE foundation1 is no DropTarget!
        ((BuildingBySteps)foundation1.getBuildingAdd()).setWrap(true);
        foundation1.setBuildingRemove(buildingForbidden);

        /*foundation1.setCgOrderRankAdd(Pile.CGOrderRank.KINGTOACE);
		foundation1.setCgOrderSuitAdd(Pile.CGOrderSuit.SUIT);
		foundation1.setCgRuleRemove(FORBIDDEN);
		foundation1.addDeckListener(dl);
		new CGDropTarget(foundation1);*/

        final BuildingBySteps buildingAdd = new BuildingInSequence(BuildingBySteps.Direction.DOWN, BuildingBySteps.Suit.NULL, false);

        final BuildingBySteps buildingRemove = new BuildingInSequence(BuildingBySteps.Direction.DOWN, BuildingBySteps.Suit.SUIT, false);

        tableau1 = new Tableau(this, "tableau1", buildingAdd, buildingRemove); //$NON-NLS-1$
        tableau1.setCgEmptyStart(Pile.CGEmptyStart.KING);
        tableaus.add(tableau1);
        tableau2 = new Tableau(this, "tableau2", buildingAdd, buildingRemove); //$NON-NLS-1$
        tableau2.setCgEmptyStart(Pile.CGEmptyStart.KING);
        tableaus.add(tableau2);
        tableau3 = new Tableau(this, "tableau3", buildingAdd, buildingRemove); //$NON-NLS-1$
        tableau3.setCgEmptyStart(Pile.CGEmptyStart.KING);
        tableaus.add(tableau3);
        tableau4 = new Tableau(this, "tableau4", buildingAdd, buildingRemove); //$NON-NLS-1$
        tableau4.setCgEmptyStart(Pile.CGEmptyStart.KING);
        tableaus.add(tableau4);
        tableau5 = new Tableau(this, "tableau5", buildingAdd, buildingRemove); //$NON-NLS-1$
        tableau5.setCgEmptyStart(Pile.CGEmptyStart.KING);
        tableaus.add(tableau5);
        tableau6 = new Tableau(this, "tableau6", buildingAdd, buildingRemove); //$NON-NLS-1$
        tableau6.setCgEmptyStart(Pile.CGEmptyStart.KING);
        tableaus.add(tableau6);
        tableau7 = new Tableau(this, "tableau7", buildingAdd, buildingRemove); //$NON-NLS-1$
        tableau7.setCgEmptyStart(Pile.CGEmptyStart.KING);
        tableaus.add(tableau7);
        tableau8 = new Tableau(this, "tableau8", buildingAdd, buildingRemove); //$NON-NLS-1$
        tableau8.setCgEmptyStart(Pile.CGEmptyStart.KING);
        tableaus.add(tableau8);
        tableau9 = new Tableau(this, "tableau9", buildingAdd, buildingRemove); //$NON-NLS-1$
        tableau9.setCgEmptyStart(Pile.CGEmptyStart.KING);
        tableaus.add(tableau9);
        tableau10 = new Tableau(this, "tableau10", buildingAdd, buildingRemove); //$NON-NLS-1$
        tableau10.setCgEmptyStart(Pile.CGEmptyStart.KING);
        tableaus.add(tableau10);
    }

    public void gameDeal() {
        setGameMode(GameMode.DEAL);
        for (Iterator<Widget> iter = tableaus.iterator(); iter.hasNext();) {
            if (stock.getCardCount() > 0) {
                final Card lastCard = stock.getLastCard();
                stock.moveTo((Tableau) iter.next(), lastCard);
                lastCard.showFront();
            }
        }
        setGameMode(GameMode.PLAY);
    }

    @Override
    public void layout() {
        final VerticalPanel vp = new VerticalPanel();
        tableaus.setHeight("480px"); //$NON-NLS-1$
        vp.add(tableaus);
        final HorizontalPanel hp = new HorizontalPanel();
        hp.add(foundation1);
        hp.add(stock);
        vp.add(hp);
        table.add(vp);
    }

    @Override
    public void firstDeal() {
        // Load all cards
        //Deck.generatePrototypeDeck(this);
        // 1 suit
        //deck.newDeck(Deck.Rank.values(), new int[]{Deck.Suit.SPADES});
        // 2 suits
        //deck.newDeck(Deck.Rank.values(), new int[]{Deck.Suit.SPADES, Deck.Suit.HEARTS});
        // 4 suits
        final List<Card> deck1 = deck.newDeck(this);
        final List<Card> deck2 = deck.newDeck(this);
        final List<Card> allCards = deck1;
        allCards.addAll(deck2);
        //ArrayList allCards = deck.newDeck();

        // Shuffle cards
        // TODO Collections.shuffle(allCards);
        Utils.fisherYates(allCards);

        // Tableau
        dealTableauCards(allCards, 0, 5, tableau1);
        dealTableauCards(allCards, 6, 11, tableau2);
        dealTableauCards(allCards, 12, 17, tableau3);
        dealTableauCards(allCards, 18, 23, tableau4);
        dealTableauCards(allCards, 24, 28, tableau5);
        dealTableauCards(allCards, 29, 33, tableau6);
        dealTableauCards(allCards, 34, 38, tableau7);
        dealTableauCards(allCards, 39, 43, tableau8);
        dealTableauCards(allCards, 44, 48, tableau9);
        dealTableauCards(allCards, 49, 53, tableau10);

        // Stock
        stock.addAllCards(allCards.subList(54, allCards.size()));
    }

    @Override
    public void start() {
        super.start();
        tableau1.redraw();
        tableau2.redraw();
        tableau3.redraw();
        tableau4.redraw();
        tableau5.redraw();
        tableau6.redraw();
        tableau7.redraw();
        tableau8.redraw();
        tableau9.redraw();
        tableau10.redraw();
    }

    private void dealTableauCards(final List<Card> deck, final int begin, final int end, final Tableau target) {
        target.addAllCards(deck.subList(begin, end));

        final Card card = deck.get(end);
        card.showFront();
        target.addCard(card);
        target.setCgVisibility(Pile.CGVisibility.ALL);
    }

    private final PileListener dl = new PileListener() {
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
        return foundation1.getCardCount() == 104;
    }

}
