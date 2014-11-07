package com.rainerschuster.cardgames.client.games;

import java.util.ArrayList;
import java.util.List;

import com.rainerschuster.cardgames.client.Building;
import com.rainerschuster.cardgames.client.BuildingBySteps;
import com.rainerschuster.cardgames.client.BuildingInSequence;
import com.rainerschuster.cardgames.client.CGSimpleDropController;
import com.rainerschuster.cardgames.client.Card;
import com.rainerschuster.cardgames.client.Foundation;
import com.rainerschuster.cardgames.client.FoundationGroup;
import com.rainerschuster.cardgames.client.Messages;
import com.rainerschuster.cardgames.client.Pile;
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

/**
 * @author Rainer Schuster
 */
public class Yukon extends CardGame {

	private Messages messages = GWT.create(Messages.class);

//	private Stock stock;
//	private Waste waste;
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

	public Yukon(Table table) {
		super(table);
	}

	/*@Override
	public void onModuleLoad() {
		super.onModuleLoad();
		cleanUpTrick();
	}*/

	@Override
	public void init() {
		foundations = new FoundationGroup();
		tableaus = new TableauGroup();

		/*stock = new Stock(this);
		stock.addCardListener(new CardListener(){
			public void onCardClick(Card sender) {
				stock.moveTo(waste, sender);
				sender.showFront();
			}

			public void onCardDoubleClick(Card sender) {
			}
		});
		stock.addClickListener(new ClickListener(){
			public void onClick(Widget sender) {
				if (waste.getCardCount() > 0) {
					// Get back all cards from moveTarget
					ArrayList allCards = new ArrayList();
					for(int i = 0; i < waste.getCardCount(); i++) {
						allCards.add(waste.getCard(i));
						//allCards.add(moveTarget.getCards().get(i));
					}
					waste.removeAllCards(allCards);
					Collections.reverse(allCards);
					stock.addAllCards(allCards);
					// HINT moveTo ist hier nicht anwendbar aufgrund der umgekehrten Reihenfolge
					// moveTo(this, allCards);
				}
			}
		});*/

		CardListener cardListener = new CardListener(){
			@Override
			public void onCardClick(Card sender) {
			}

			// Move to foundation if allowed
			@Override
			public void onCardDoubleClick(Card sender) {
				if ((sender.getPile()).acceptsRemove(sender)) {
					Foundation foundation = foundations.getFoundation(sender/*.getSuit()*/);
					if (foundation != null) {
						if (foundation.acceptsAdd(sender)) {
							(sender.getPile()).moveTo(foundation, sender);
						}
					}
				}
			}
		};

		/*waste = new Waste(this);
		waste.addCardListener(cardListener);
		stock.setMoveTarget(waste);*/

		foundation1 = new Foundation(this, "foundation1"); //$NON-NLS-1$
		foundation1.addPileListener(dl);
		new CGSimpleDropController(foundation1);
		foundations.add(foundation1);
		foundation2 = new Foundation(this, "foundation2"); //$NON-NLS-1$
		foundation2.addPileListener(dl);
		new CGSimpleDropController(foundation2);
		foundations.add(foundation2);
		foundation3 = new Foundation(this, "foundation3"); //$NON-NLS-1$
		foundation3.addPileListener(dl);
		new CGSimpleDropController(foundation3);
		foundations.add(foundation3);
		foundation4 = new Foundation(this, "foundation4"); //$NON-NLS-1$
		foundation4.addPileListener(dl);
		new CGSimpleDropController(foundation4);
		foundations.add(foundation4);

		Building buildingAdd = new BuildingInSequence(BuildingBySteps.Direction.DOWN, BuildingBySteps.Suit.ALTERNATING, false);
		Building buildingRemove = buildingAllowed;

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
		VerticalPanel vp = new VerticalPanel();
		HorizontalPanel hp = new HorizontalPanel();
		//hp.add(stock);
		//hp.add(waste);
		// Placeholder
		//hp.add(new HTML("<div style=\"width: 88px;\">&nbsp;</div>"));
		hp.add(foundations);
		vp.add(hp);
		vp.add(tableaus);

		table.add(vp);
	}

	@Override
	public void firstDeal() {
		// Load all cards
		//Deck.generatePrototypeDeck(this);
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

		// Tableau
		dealTableauCards(allCards, 0, 0, tableau1);
		dealTableauCards(allCards, 1, 2, tableau2);
		dealTableauCards(allCards, 3, 5, tableau3);
		dealTableauCards(allCards, 6, 9, tableau4);
		dealTableauCards(allCards, 10, 14, tableau5);
		dealTableauCards(allCards, 15, 20, tableau6);
		dealTableauCards(allCards, 21, 27, tableau7);

		dealTableauCards2(allCards, 28, tableau2);
		dealTableauCards2(allCards, 32, tableau3);
		dealTableauCards2(allCards, 36, tableau4);
		dealTableauCards2(allCards, 40, tableau5);
		dealTableauCards2(allCards, 44, tableau6);
		dealTableauCards2(allCards, 48, tableau7);
	}

	private void dealTableauCards2(List<Card> deck, int begin, Tableau target) {
	  List<Card> cardList = new ArrayList<Card>();
		for (int i = 0; i < 4 ; i++) {
			Card card = deck.get(begin + i);
			card.showFront();
			cardList.add(card);
		}
		target.addAllCards(cardList);
		/*Card card;
		for (int i = 0; i < 4 ; i++) {
			card = (Card)deck.get(begin + i);
			card.showFront();
			target.addCard(card);
		}*/
	}

	private void dealTableauCards(List<Card> deck, int begin, int end, Tableau target) {
		Card card;
		// TODO target.addAllCards(deck.subList(begin, end));
		List<Card> subList = new ArrayList<Card>();
		for (int i = begin; i < end; i++) {
			subList.add(deck.get(i));
		}
		target.addAllCards(subList);

		card = deck.get(end);
		card.showFront();
		target.addCard(card);
		//target.setCgVisibility(Pile.CGVisibility.ALL);
	}

	PileListener dl = new PileListener() {
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
		}
	};

	@Override
	public boolean isWon() {
		return (foundation1.getCardCount() == 13)
				&& (foundation2.getCardCount() == 13)
				&& (foundation3.getCardCount() == 13)
				&& (foundation4.getCardCount() == 13);
	}
}