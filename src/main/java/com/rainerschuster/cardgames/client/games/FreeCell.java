package com.rainerschuster.cardgames.client.games;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.rainerschuster.cardgames.client.BuildingBySteps;
import com.rainerschuster.cardgames.client.BuildingInSequence;
import com.rainerschuster.cardgames.client.CGSimpleDropController;
import com.rainerschuster.cardgames.client.Card;
import com.rainerschuster.cardgames.client.Cell;
import com.rainerschuster.cardgames.client.CellGroup;
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
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Rainer Schuster
 */
public class FreeCell extends CardGame {

	private Messages messages = GWT.create(Messages.class);

	// HINT "Free" is a keyword that usually means Cell or Tableau (so xxxFreeCell doesn't only mean piles of type Cell)

	private Cell cell1;
	private Cell cell2;
	private Cell cell3;
	private Cell cell4;
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
	private Tableau tableau8;
	private CellGroup cells;
	private FoundationGroup foundations;
	private TableauGroup tableaus;

	public FreeCell(Table table) {
		super(table);
	}

	@Override
	public void init() {
		cells = new CellGroup();
		foundations = new FoundationGroup();
		tableaus = new TableauGroup();

		foundation1 = new Foundation(this, "foundation1"); //$NON-NLS-1$
		foundation1.setBuildingRemove(buildingForbidden);
		foundation1.addPileListener(dl);
		new CGSimpleDropController(foundation1);
		foundations.add(foundation1);
		foundation2 = new Foundation(this, "foundation2"); //$NON-NLS-1$
		foundation2.setBuildingRemove(buildingForbidden);
		foundation2.addPileListener(dl);
		new CGSimpleDropController(foundation2);
		foundations.add(foundation2);
		foundation3 = new Foundation(this, "foundation3"); //$NON-NLS-1$
		foundation3.setBuildingRemove(buildingForbidden);
		foundation3.addPileListener(dl);
		new CGSimpleDropController(foundation3);
		foundations.add(foundation3);
		foundation4 = new Foundation(this, "foundation4"); //$NON-NLS-1$
		foundation4.setBuildingRemove(buildingForbidden);
		foundation4.addPileListener(dl);
		new CGSimpleDropController(foundation4);
		foundations.add(foundation4);

		final CardListener cellCardListener = new CardListener(){
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

		cell1 = new Cell(this, "cell1"); //$NON-NLS-1$
		new CGSimpleDropController(cell1);
		cell1.addCardListener(cellCardListener);
		cells.add(cell1);
		cell2 = new Cell(this, "cell2"); //$NON-NLS-1$
		new CGSimpleDropController(cell2);
		cell2.addCardListener(cellCardListener);
		cells.add(cell2);
		cell3 = new Cell(this, "cell3"); //$NON-NLS-1$
		new CGSimpleDropController(cell3);
		cell3.addCardListener(cellCardListener);
		cells.add(cell3);
		cell4 = new Cell(this, "cell4"); //$NON-NLS-1$
		new CGSimpleDropController(cell4);
		cell4.addCardListener(cellCardListener);
		cells.add(cell4);

		final BuildingBySteps buildingAdd = new BuildingInSequence(BuildingBySteps.Direction.DOWN, BuildingBySteps.Suit.ALTERNATING, false);

		final BuildingBySteps buildingRemove = buildingAdd;

		final CardListener tableauCardListener = new CardListener(){
			@Override
			public void onCardClick(final Card sender) {
				// Not used
			}

			// Move to foundation or to cell if allowed
			@Override
			public void onCardDoubleClick(final Card sender) {
				if (sender.getPile().acceptsRemove(sender)) {
					final Foundation foundation = foundations.getFoundation(sender/*.getSuit()*/);
					if (foundation != null) {
						if (foundation.acceptsAdd(sender)) {
							sender.getPile().moveTo(foundation, sender);
						}
					} else {
						final Pile pile = getNextFreeCell();
						if (pile != null) {
							sender.getPile().moveTo(pile, sender);
						}
					}
				}
			}
		};

		tableau1 = new FreeCellTableau(this, "tableau1", buildingAdd, buildingRemove); //$NON-NLS-1$
		tableau1.setCgEmptyStart(Pile.CGEmptyStart.KING);
		tableau1.addCardListener(tableauCardListener);
		tableaus.add(tableau1);
		tableau2 = new FreeCellTableau(this, "tableau2", buildingAdd, buildingRemove); //$NON-NLS-1$
		tableau2.setCgEmptyStart(Pile.CGEmptyStart.KING);
		tableau2.addCardListener(tableauCardListener);
		tableaus.add(tableau2);
		tableau3 = new FreeCellTableau(this, "tableau3", buildingAdd, buildingRemove); //$NON-NLS-1$
		tableau3.setCgEmptyStart(Pile.CGEmptyStart.KING);
		tableau3.addCardListener(tableauCardListener);
		tableaus.add(tableau3);
		tableau4 = new FreeCellTableau(this, "tableau4", buildingAdd, buildingRemove); //$NON-NLS-1$
		tableau4.setCgEmptyStart(Pile.CGEmptyStart.KING);
		tableau4.addCardListener(tableauCardListener);
		tableaus.add(tableau4);
		tableau5 = new FreeCellTableau(this, "tableau5", buildingAdd, buildingRemove); //$NON-NLS-1$
		tableau5.setCgEmptyStart(Pile.CGEmptyStart.KING);
		tableau5.addCardListener(tableauCardListener);
		tableaus.add(tableau5);
		tableau6 = new FreeCellTableau(this, "tableau6", buildingAdd, buildingRemove); //$NON-NLS-1$
		tableau6.setCgEmptyStart(Pile.CGEmptyStart.KING);
		tableau6.addCardListener(tableauCardListener);
		tableaus.add(tableau6);
		tableau7 = new FreeCellTableau(this, "tableau7", buildingAdd, buildingRemove); //$NON-NLS-1$
		tableau7.setCgEmptyStart(Pile.CGEmptyStart.KING);
		tableau7.addCardListener(tableauCardListener);
		tableaus.add(tableau7);
		tableau8 = new FreeCellTableau(this, "tableau8", buildingAdd, buildingRemove); //$NON-NLS-1$
		tableau8.setCgEmptyStart(Pile.CGEmptyStart.KING);
		tableau8.addCardListener(tableauCardListener);
		tableaus.add(tableau8);
	}

	@Override
	public void layout() {
		final VerticalPanel vp = new VerticalPanel();
		final HorizontalPanel hp = new HorizontalPanel();
		hp.add(cells);
		// TODO Add spacing
		hp.add(foundations);
		vp.add(hp);
		vp.add(tableaus);

		table.add(vp);
	}

	@Override
	public void firstDeal() {
		// Load all cards
		//Deck.generatePrototypeDeck(this);
		final List<Card> allCards = deck.newDeck(this);

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
		dealTableauCards(allCards, 0, 6, tableau1);
		dealTableauCards(allCards, 7, 13, tableau2);
		dealTableauCards(allCards, 14, 20, tableau3);
		dealTableauCards(allCards, 21, 27, tableau4);
		dealTableauCards(allCards, 28, 33, tableau5);
		dealTableauCards(allCards, 34, 39, tableau6);
		dealTableauCards(allCards, 40, 45, tableau7);
		dealTableauCards(allCards, 46, 51, tableau8);

		// No stock left
	}

	private void dealTableauCards(List<Card> deck, int begin, int end, Tableau target) {
		// TODO target.addAllCards(deck.subList(begin, end));
		final List<Card> subList = new ArrayList<Card>();
		for (int i = begin; i <= end; i++) {
			deck.get(i).showFront();
			subList.add(deck.get(i));
		}
		target.addAllCards(subList);
		//target.setCgVisibility(Deck.CGVisibility.ALL);
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
		return (foundation1.getCardCount() == 13)
				&& (foundation2.getCardCount() == 13)
				&& (foundation3.getCardCount() == 13)
				&& (foundation4.getCardCount() == 13);
	}

	/** @return how many cards could be "cleaned up" in one take (loopes until nothing can be cleaned up anymore) */
	/*public int cleanUpTrick() {
		int count = 0;
		int temp;
		while ((temp = cleanUpRoundTrick()) > 0) {
			count += temp;
		}
		System.out.println(count + " cards cleaned up");
		return count;
	}*/

	/** @return how many cards could be "cleaned up" in one round/loop */
	/*private int cleanUpRoundTrick() {
		int count = 0;
		count += cleanUpTrickPile(tableau1);
		count += cleanUpTrickPile(tableau2);
		count += cleanUpTrickPile(tableau3);
		count += cleanUpTrickPile(tableau4);
		count += cleanUpTrickPile(tableau5);
		count += cleanUpTrickPile(tableau6);
		count += cleanUpTrickPile(tableau7);
		count += cleanUpTrickPile(tableau8);
		return count;
	}*/

	/** @return how many cards could be "cleaned up" at a pile */
	/*private int cleanUpTrickPile(Pile pile) {
		int count = 0;
		boolean flag = true;
		while (pile.getCardCount() > 0 && flag) {
			FoundationDeck fd = getFoundation(pile.getLastCard().getSuit());
			if (fd.acceptsAdd(pile.getLastCard())) {
				pile.moveTo(fd, pile.getLastCard());
				// TODO Show last card?
				//if (pile.getCardCount() > 0) pile.getLastCard().showFront();
				count++;
			} else
				flag = false;
		}
		return count;
	}*/

	/** @return next available "free cell" (checks cell and tableau piles) */
	public Pile getNextFreeCell() {
		// Search in cells
		for (Iterator<Widget> it = cells.iterator(); it.hasNext();) {
			final Pile pile = (Pile) it.next();
			if (pile.getCardCount() == 0) {
				return pile;
			}
		}

		// Search in tableaus
		for (Iterator<Widget> it = tableaus.iterator(); it.hasNext();) {
			final Pile pile = (Pile) it.next();
			if (pile.getCardCount() == 0) {
				return pile;
			}
		}

		return null;
	}

	/** @return number of "free cells" (checks cell and tableau piles) */
	public int getFreeCellCount() {
		return cells.getEmptyPileCount() + tableaus.getEmptyPileCount();
	}

}
