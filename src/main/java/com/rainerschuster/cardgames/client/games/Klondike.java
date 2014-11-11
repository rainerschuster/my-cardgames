package com.rainerschuster.cardgames.client.games;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.rainerschuster.cardgames.client.BuildingBySteps;
import com.rainerschuster.cardgames.client.BuildingInSequence;
import com.rainerschuster.cardgames.client.CGSimpleDropController;
import com.rainerschuster.cardgames.client.Card;
import com.rainerschuster.cardgames.client.Foundation;
import com.rainerschuster.cardgames.client.FoundationGroup;
import com.rainerschuster.cardgames.client.Messages;
import com.rainerschuster.cardgames.client.Pile;
import com.rainerschuster.cardgames.client.Stock;
import com.rainerschuster.cardgames.client.Table;
import com.rainerschuster.cardgames.client.Tableau;
import com.rainerschuster.cardgames.client.TableauGroup;
import com.rainerschuster.cardgames.client.Utils;
import com.rainerschuster.cardgames.client.Waste;
import com.rainerschuster.cardgames.client.dnd.DNDManager;
import com.rainerschuster.cardgames.client.ui.CardListener;
import com.rainerschuster.cardgames.client.ui.PileListener;

/**
 * @author Rainer Schuster
 */
public class Klondike extends CardGame {

    private static final Logger LOG = Logger.getLogger(Klondike.class.getName());

    private Messages messages = GWT.create(Messages.class);

    private Stock stock;
    private Waste waste;
    private Foundation foundation1;
    private Foundation foundation2;
    private Foundation foundation3;
    private Foundation foundation4;
    private Tableau tableau1;
    private Tableau tableau2;
    private Tableau tableau3;
    private Tableau tableau4;
    private Tableau tableau5;
    private Tableau tableau6;
    private Tableau tableau7;
    private FoundationGroup foundations;
    private TableauGroup tableaus;

    public Klondike(Table table) {
        super(table);
    }

    @Override
    public void init() {
        foundations = new FoundationGroup();
        tableaus = new TableauGroup();

        stock = new Stock(this);
        stock.addCardListener(new CardListener() {
            @Override
            public void onCardClick(final Card sender) {
                LOG.log(Level.INFO, "Stock: onCardClick."); //$NON-NLS-1$
                stock.moveTo(waste, sender);
                sender.showFront();
            }

            @Override
            public void onCardDoubleClick(final Card sender) {
                // Not used
            }
        });

        stock.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(final ClickEvent event) {
                LOG.log(Level.INFO, "Click on stock."); //$NON-NLS-1$
                if (waste.getCardCount() > 0) {
                    // Get back all cards from moveTarget
                    final List<Card> allCards = new ArrayList<Card>();
                    for (int i = 0; i < waste.getCardCount(); i++) {
                        allCards.add(waste.getCard(i));
//                        allCards.add(moveTarget.getCards().get(i));
                    }
                    waste.removeAllCards(allCards);
                    Collections.reverse(allCards);
                    stock.addAllCards(allCards);
                    // HINT moveTo cannot be used here because of the reversed order
//                    moveTo(this, allCards);
                }
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
                    final Foundation foundation = foundations.getFoundation(sender/*.getSuit()*/);
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
        final CGSimpleDropController foundation1DropController = new CGSimpleDropController(foundation1);
        DNDManager.registerDropController(foundation1DropController);
        foundations.add(foundation1);
        foundation2 = new Foundation(this, "foundation2"); //$NON-NLS-1$
        foundation2.addPileListener(pl);
        final CGSimpleDropController foundation2DropController = new CGSimpleDropController(foundation2);
        DNDManager.registerDropController(foundation2DropController);
        foundations.add(foundation2);
        foundation3 = new Foundation(this, "foundation3"); //$NON-NLS-1$
        foundation3.addPileListener(pl);
        final CGSimpleDropController foundation3DropController = new CGSimpleDropController(foundation3);
        DNDManager.registerDropController(foundation3DropController);
        foundations.add(foundation3);
        foundation4 = new Foundation(this, "foundation4"); //$NON-NLS-1$
        foundation4.addPileListener(pl);
        final CGSimpleDropController foundation4DropController = new CGSimpleDropController(foundation4);
        DNDManager.registerDropController(foundation4DropController);
        foundations.add(foundation4);

        final BuildingBySteps buildingAdd = new BuildingInSequence(BuildingBySteps.Direction.DOWN, BuildingBySteps.Suit.ALTERNATING, false);
        final BuildingBySteps buildingRemove = new BuildingInSequence(BuildingBySteps.Direction.DOWN, BuildingBySteps.Suit.ALTERNATING, false);

        tableau1 = new Tableau(this, "tableau1", buildingAdd, buildingRemove); //$NON-NLS-1$
        tableau1.setCgEmptyStart(Pile.CGEmptyStart.KING);
        tableau1.addCardListener(cardListener);
        tableaus.add(tableau1);
        tableau2 = new Tableau(this, "tableau2", buildingAdd, buildingRemove); //$NON-NLS-1$
        tableau2.setCgEmptyStart(Pile.CGEmptyStart.KING);
        tableau2.addCardListener(cardListener);
        tableaus.add(tableau2);
        tableau3 = new Tableau(this, "tableau3", buildingAdd, buildingRemove); //$NON-NLS-1$
        tableau3.setCgEmptyStart(Pile.CGEmptyStart.KING);
        tableau3.addCardListener(cardListener);
        tableaus.add(tableau3);
        tableau4 = new Tableau(this, "tableau4", buildingAdd, buildingRemove); //$NON-NLS-1$
        tableau4.setCgEmptyStart(Pile.CGEmptyStart.KING);
        tableau4.addCardListener(cardListener);
        tableaus.add(tableau4);
        tableau5 = new Tableau(this, "tableau5", buildingAdd, buildingRemove); //$NON-NLS-1$
        tableau5.setCgEmptyStart(Pile.CGEmptyStart.KING);
        tableau5.addCardListener(cardListener);
        tableaus.add(tableau5);
        tableau6 = new Tableau(this, "tableau6", buildingAdd, buildingRemove); //$NON-NLS-1$
        tableau6.setCgEmptyStart(Pile.CGEmptyStart.KING);
        tableau6.addCardListener(cardListener);
        tableaus.add(tableau6);
        tableau7 = new Tableau(this, "tableau7", buildingAdd, buildingRemove); //$NON-NLS-1$
        tableau7.setCgEmptyStart(Pile.CGEmptyStart.KING);
        tableau7.addCardListener(cardListener);
        tableaus.add(tableau7);
    }

    @Override
    public void layout() {
        final VerticalPanel vp = new VerticalPanel();
        final HorizontalPanel hp = new HorizontalPanel();
        hp.setWidth("100%"); //$NON-NLS-1$
        hp.add(stock);
        hp.add(waste);
        // Placeholder
        final HTML filler = new HTML("&nbsp;"); //$NON-NLS-1$
        hp.add(filler);
        hp.setCellWidth(filler, "100%"); //$NON-NLS-1$
        hp.add(foundations);
        hp.setCellHorizontalAlignment(foundations, HasHorizontalAlignment.ALIGN_RIGHT);
        vp.add(hp);
        vp.add(tableaus);

        table.add(vp);
    }

    @Override
    public void firstDeal() {
        // Load all cards
//        Deck.generatePrototypeDeck(this);
        final List<Card> allCards = deck.newDeck(this);

        // Shuffle cards
        // TODO Collections.shuffle(allCards);
        Utils.fisherYates(allCards);

        // Tableau
        dealTableauCards(allCards, 0, 0, tableau1);
        dealTableauCards(allCards, 1, 2, tableau2);
        dealTableauCards(allCards, 3, 5, tableau3);
        dealTableauCards(allCards, 6, 9, tableau4);
        dealTableauCards(allCards, 10, 14, tableau5);
        dealTableauCards(allCards, 15, 20, tableau6);
        dealTableauCards(allCards, 21, 27, tableau7);

        // Stock
        stock.addAllCards(allCards.subList(28, allCards.size()));
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
    }

    private void dealTableauCards(final List<Card> deck, final int begin, final int end, final Tableau target) {
        target.addAllCards(deck.subList(begin, end));

        final Card card = deck.get(end);
        card.showFront();
        target.addCard(card);
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
                && (foundation4.getCardCount() == 13);
    }

    /**
     * @return How many cards could be "cleaned up" in one take (loops until nothing can be cleaned up anymore).
     */
    public int cleanUpTrick() {
        int count = 0;
        int temp;
        while ((temp = cleanUpRoundTrick()) > 0) {
            count += temp;
        }
        LOG.log(Level.INFO, count + " cards cleaned up"); //$NON-NLS-1$
        return count;
    }

    /**
     * @return How many cards could be "cleaned up" in one round/loop.
     */
    private int cleanUpRoundTrick() {
        int count = 0;
        count += cleanUpTrickStack(waste);
        count += cleanUpTrickStack(tableau1);
        count += cleanUpTrickStack(tableau2);
        count += cleanUpTrickStack(tableau3);
        count += cleanUpTrickStack(tableau4);
        count += cleanUpTrickStack(tableau5);
        count += cleanUpTrickStack(tableau6);
        count += cleanUpTrickStack(tableau7);
        return count;
    }

    /**
     * @return How many cards could be "cleaned up" at a pile.
     */
    private int cleanUpTrickStack(final Pile pile) {
        int count = 0;
        boolean flag = true;
        while (pile.getCardCount() > 0 && flag) {
            final Foundation fd = foundations.getFoundation(pile.getLastCard()/*.getSuit()*/);
            if (fd.acceptsAdd(pile.getLastCard())) {
                pile.moveTo(fd, pile.getLastCard());
                // TODO Show last card?
//                if (deck.getCardCount() > 0) deck.getLastCard().showFront();
                count++;
            } else {
                flag = false;
            }
        }
        return count;
    }

}
