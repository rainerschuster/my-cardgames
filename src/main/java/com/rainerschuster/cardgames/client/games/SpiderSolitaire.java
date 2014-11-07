package com.rainerschuster.cardgames.client.games;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
import com.rainerschuster.cardgames.client.ui.CardListener;
import com.rainerschuster.cardgames.client.ui.PileListener;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Rainer Schuster
 */
public class SpiderSolitaire extends CardGame {

	private Messages messages = GWT.create(Messages.class);

	private Stock stock;
	private Foundation foundation1;
	/*private Foundation foundation2;
	private Foundation foundation3;
	private Foundation foundation4;
	private Foundation foundation5;
	private Foundation foundation6;
	private Foundation foundation7;
	private Foundation foundation8;*/
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
		foundation1 = new Foundation(this, "foundation1");
		// NOTE foundation1 is no DropTarget!
		((BuildingBySteps)foundation1.getBuildingAdd()).setWrap(true);
		foundation1.setBuildingRemove(buildingForbidden);

		/*foundation1.setCgOrderRankAdd(Pile.CGOrderRank.KINGTOACE);
		foundation1.setCgOrderSuitAdd(Pile.CGOrderSuit.SUIT);
		foundation1.setCgRuleRemove(FORBIDDEN);
		foundation1.addDeckListener(dl);
		new CGDropTarget(foundation1);*/

		BuildingBySteps buildingAdd = new BuildingInSequence(BuildingBySteps.Direction.DOWN, BuildingBySteps.Suit.NULL, false);
		/*buildingAdd.setDirection(Building.Direction.DOWN);
		buildingAdd.setSuit(Building.Suit.NULL);
		buildingAdd.setWrap(false);*/

		BuildingBySteps buildingRemove = new BuildingInSequence(BuildingBySteps.Direction.DOWN, BuildingBySteps.Suit.SUIT, false);
		/*buildingRemove.setDirection(Building.Direction.DOWN);
		buildingRemove.setSuit(Building.Suit.SUIT);
		buildingRemove.setWrap(false);*/

		tableau1 = new Tableau(this, "tableau1", buildingAdd, buildingRemove);
		tableau1.setCgEmptyStart(Pile.CGEmptyStart.KING);
		tableaus.add(tableau1);
		tableau2 = new Tableau(this, "tableau2", buildingAdd, buildingRemove);
		tableau2.setCgEmptyStart(Pile.CGEmptyStart.KING);
		tableaus.add(tableau2);
		tableau3 = new Tableau(this, "tableau3", buildingAdd, buildingRemove);
		tableau3.setCgEmptyStart(Pile.CGEmptyStart.KING);
		tableaus.add(tableau3);
		tableau4 = new Tableau(this, "tableau4", buildingAdd, buildingRemove);
		tableau4.setCgEmptyStart(Pile.CGEmptyStart.KING);
		tableaus.add(tableau4);
		tableau5 = new Tableau(this, "tableau5", buildingAdd, buildingRemove);
		tableau5.setCgEmptyStart(Pile.CGEmptyStart.KING);
		tableaus.add(tableau5);
		tableau6 = new Tableau(this, "tableau6", buildingAdd, buildingRemove);
		tableau6.setCgEmptyStart(Pile.CGEmptyStart.KING);
		tableaus.add(tableau6);
		tableau7 = new Tableau(this, "tableau7", buildingAdd, buildingRemove);
		tableau7.setCgEmptyStart(Pile.CGEmptyStart.KING);
		tableaus.add(tableau7);
		tableau8 = new Tableau(this, "tableau8", buildingAdd, buildingRemove);
		tableau8.setCgEmptyStart(Pile.CGEmptyStart.KING);
		tableaus.add(tableau8);
		tableau9 = new Tableau(this, "tableau9", buildingAdd, buildingRemove);
		tableau9.setCgEmptyStart(Pile.CGEmptyStart.KING);
		tableaus.add(tableau9);
		tableau10 = new Tableau(this, "tableau10", buildingAdd, buildingRemove);
		tableau10.setCgEmptyStart(Pile.CGEmptyStart.KING);
		tableaus.add(tableau10);
	}

	public void gameDeal() {
		setGameMode(GameMode.DEAL);
		for (Iterator<Widget> iter = tableaus.iterator(); iter.hasNext();) {
			if (stock.getCardCount() > 0) {
				Card lastCard = stock.getLastCard();
				stock.moveTo((Tableau) iter.next(), lastCard);
				lastCard.showFront();
			}
		}
		setGameMode(GameMode.PLAY);
	}

	@Override
	public void layout() {
		VerticalPanel vp = new VerticalPanel();
		tableaus.setHeight("480px");
		vp.add(tableaus);
		HorizontalPanel hp = new HorizontalPanel();
		hp.add(foundation1);
		hp.add(stock);
		vp.add(hp);
		table.add(vp);

		/*RootPanel.get("slot_stock").add(stock);
		RootPanel.get("slot_foundation1").add(foundation1);
		//RootPanel.get("slot_foundation2").add(foundation2);
		//RootPanel.get("slot_foundation3").add(foundation3);
		//RootPanel.get("slot_foundation4").add(foundation4);
		//RootPanel.get("slot_foundation5").add(foundation5);
		//RootPanel.get("slot_foundation6").add(foundation6);
		//RootPanel.get("slot_foundation7").add(foundation7);
		//RootPanel.get("slot_foundation8").add(foundation8);
		RootPanel.get("slot_tableau1").add(tableau1);
		RootPanel.get("slot_tableau2").add(tableau2);
		RootPanel.get("slot_tableau3").add(tableau3);
		RootPanel.get("slot_tableau4").add(tableau4);
		RootPanel.get("slot_tableau5").add(tableau5);
		RootPanel.get("slot_tableau6").add(tableau6);
		RootPanel.get("slot_tableau7").add(tableau7);
		RootPanel.get("slot_tableau8").add(tableau8);
		RootPanel.get("slot_tableau9").add(tableau9);
		RootPanel.get("slot_tableau10").add(tableau10);*/
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
		int rindex;
		for (int i = 0; i < allCards.size(); i++) {
			rindex = Random.nextInt(allCards.size());
			final Card temp = allCards.get(rindex);
			allCards.set(rindex, allCards.get(i));
			allCards.set(i, temp);
		}

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
		// TODO stock.addAllCards(allCards.subList(begin, end));
		final List<Card> subList = new ArrayList<Card>();
		for (int i = 54; i < allCards.size(); i++) {
			subList.add(allCards.get(i));
		}
		stock.addAllCards(subList);
	}

	//J5private void dealTableauCards(List<Card> deck, int begin, int end, TableauDeck target) {
	private void dealTableauCards(List<Card> deck, int begin, int end, Tableau target) {
		Card card;
		// TODO target.addAllCards(deck.subList(begin, end));
		final List<Card> subList = new ArrayList<Card>();
		for (int i = begin; i < end; i++) {
			subList.add(deck.get(i));
		}
		target.addAllCards(subList);
		
		card = deck.get(end);
		card.showFront();
		target.addCard(card);
		target.setCgVisibility(Pile.CGVisibility.ALL);
	}

	private final PileListener dl = new PileListener() {
		@Override
		public void onAdd() {
			if (isWon()) {
				Window.alert(messages.gameWon());
				/*DialogBox db = new DialogBox();
				db.setText(messages.gameWon());
				db.show();*/
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
		/*return (foundation1.getCardCount() == 13)
				&& (foundation2.getCardCount() == 13)
				&& (foundation3.getCardCount() == 13)
				&& (foundation4.getCardCount() == 13)
				&& (foundation5.getCardCount() == 13)
				&& (foundation6.getCardCount() == 13)
				&& (foundation7.getCardCount() == 13)
				&& (foundation8.getCardCount() == 13);*/
	}
}
