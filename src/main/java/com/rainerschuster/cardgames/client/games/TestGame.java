package com.rainerschuster.cardgames.client.games;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import com.rainerschuster.cardgames.client.BuildingByEquivalency;
import com.rainerschuster.cardgames.client.Card;
import com.rainerschuster.cardgames.client.Hand;
import com.rainerschuster.cardgames.client.Pile;
import com.rainerschuster.cardgames.client.Stock;
import com.rainerschuster.cardgames.client.Table;
import com.rainerschuster.cardgames.client.Tableau;
import com.rainerschuster.cardgames.client.ui.CardListener;

public class TestGame extends CardGame {

  private Stock stock;
  private Hand hand;
  private Tableau tableau;

  public TestGame(Table table) {
    super(table);
  }

  @Override
  public void firstDeal() {
    // Load all cards
    List<Card> allCards = deck.newDeck(this);

    // Shuffle cards
    // TODO Collections.shuffle(allCards);
    int rindex;
    for (int i = 0; i < allCards.size(); i++) {
      rindex = Random.nextInt(allCards.size());
      Card temp = allCards.get(rindex);
      allCards.set(rindex, allCards.get(i));
      allCards.set(i, temp);
    }

    // Hand(s)
    List<Card> subList = new ArrayList<Card>();
    for (int i = 0; i < 7; i++) {
      Card card = allCards.get(i);
      subList.add(card);
      card.showFront();
    }
    hand.addAllCards(subList);

    // Tableau
    Card card = allCards.get(7);
    tableau.addCard(card);
    card.showFront();

    // Stock
    // TODO stock.addAllCards(allCards.subList(begin, end));
    subList = new ArrayList<Card>();
    for (int i = 8; i < allCards.size(); i++) {
      subList.add(allCards.get(i));
    }
    stock.addAllCards(subList);
  }

  @Override
  public void init() {
    stock = new Stock(this);
    hand = new Hand(
        this,
        "hand", Pile.CGLayout.CASCADE, Pile.CGVisibility.ALL, Pile.CGEmptyStart.NULL, new BuildingByEquivalency(), Pile.CGRemoveRule.NONE); //$NON-NLS-1$

    stock.addCardListener(new CardListener() {
      @Override
      public void onCardClick(Card sender) {
        stock.moveTo(hand, sender);
        sender.showFront();
      }

      @Override
      public void onCardDoubleClick(Card sender) {
      }
    });

    tableau = new Tableau(
        this,
        "tableau", Pile.CGLayout.CASCADE, Pile.CGVisibility.ALL, Pile.CGEmptyStart.NULL, new BuildingByEquivalency(), Pile.CGRemoveRule.NONE); //$NON-NLS-1$
    tableau.setBuildingDirection(Pile.CGBuildingDirection.RIGHT);

    hand.addCardListener(new CardListener() {
      @Override
      public void onCardClick(Card sender) {
        if (tableau.acceptsAdd(sender)) {
          hand.moveTo(tableau, sender);
          sender.showFront();
        }
      }

      @Override
      public void onCardDoubleClick(Card sender) {
      }
    });
  }

  @Override
  public void layout() {
    VerticalPanel vp = new VerticalPanel();
    HorizontalPanel hp = new HorizontalPanel();
    hp.add(stock);
    hp.add(tableau);
    vp.add(hp);
    vp.add(hand);

    table.add(vp);
  }

}
