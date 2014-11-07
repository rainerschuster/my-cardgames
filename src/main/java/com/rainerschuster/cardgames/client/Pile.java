package com.rainerschuster.cardgames.client;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.rainerschuster.cardgames.client.Deck.Rank;
import com.rainerschuster.cardgames.client.dnd.DNDManager;
import com.rainerschuster.cardgames.client.games.CardGame;
import com.rainerschuster.cardgames.client.games.CardGame.GameMode;
import com.rainerschuster.cardgames.client.ui.CardListener;
import com.rainerschuster.cardgames.client.ui.CardListenerCollection;
import com.rainerschuster.cardgames.client.ui.PileListener;
import com.rainerschuster.cardgames.client.ui.PileListenerCollection;
import com.rainerschuster.cardgames.client.ui.SourcesCardEvents;
import com.rainerschuster.cardgames.client.ui.SourcesPileEvents;

/**
 * @author Rainer Schuster
 */
public class Pile extends AbsolutePanel implements SourcesPileEvents, SourcesCardEvents {

	private final Logger logger = Logger.getLogger(Pile.class.getName());

	// TODO Move to CSS/GSS
	static final int MARGIN_FRONT = 20;
	static final int MARGIN_BACK = 6;

	// private Vector cards;
	private CGLayout layout;
	/** maximum number of cards that may be on a stack (-1 means infinite) */
	private int limit = -1; // TODO What about other negative numbers?
							// => currently < 0 means infinite
	private CGVisibility cgVisibility;
	private CGEmptyStart cgEmptyStart;
	private CGRemoveRule cgRuleRemove;
	private Building buildingAdd;
	private Building buildingRemove;
	private CGBuildingDirection buildingDirection = CGBuildingDirection.BOTTOM;

	private CardGame cardGame;

	private PileListenerCollection listeners = new PileListenerCollection();
	private CardListenerCollection cardListeners = new CardListenerCollection();

	private Pile(CardGame cardGame) {
		super();
		this.cardGame = cardGame;

		// Fix defaults of AbsolutePanel
		getElement().getStyle().setOverflow(Overflow.VISIBLE);

		setStylePrimaryName(MyResources.INSTANCE.css().cgPile());
		this.setPixelSize(88, 125);
	}

	public Pile(CardGame cardGame, String id, CGLayout layout,
			CGVisibility visibility, CGEmptyStart emptyStart,
			Building buildingAdd, CGRemoveRule ruleRemove) {
		this(cardGame);
		getElement().setId(id);
		this.layout = layout;
		this.cgVisibility = visibility;
		this.cgEmptyStart = emptyStart;
		this.buildingAdd = buildingAdd;
		this.cgRuleRemove = ruleRemove;
		// this.buildingRemove = buildingRemove;
	}

	public Pile(CardGame cardGame, String id, CGLayout layout,
			CGVisibility visibility, CGEmptyStart emptyStart,
			Building buildingAdd, Building buildingRemove) {
		this(cardGame, id, layout, visibility, emptyStart, buildingAdd, CGRemoveRule.BUILDING);
		this.buildingRemove = buildingRemove;
	}

	// Precondition: checked with acceptsAdd
	public boolean addCard(final Card card) {
		// if (!acceptsAdd(card)) {return false};
		final List<Card> cards = new ArrayList<Card>();
		cards.add(card);
		return addAllCards(cards);
	}

	// Precondition: checked with acceptsRemove
	public boolean removeCard(final Card card) {
		// if (!acceptsRemove(card)) {return false};
		final List<Card> cards = new ArrayList<Card>();
		cards.add(card);
		return removeAllCards(cards);
	}

	public void removeAllFrom(final Card card) {
		final List<Card> cards = new ArrayList<Card>();
		for (int i = getCardIndex(card); i < getCardCount(); i++) {
			cards.add(getCard(i));
		}
		removeAllCards(cards);
	}

	// Precondition: checked with acceptsAdd
	/** Convenience-method to reduce DnD (un)register overhead */
	public boolean addAllCards(final List<Card> widgets) {
		// Empty list means nothing to do
		// TODO Return true or false or move code to acceptsAdd?
		if (widgets.isEmpty()) {
			return false;
		}
		/*
		 * if (!acceptsAdd((Card)widgets.get(0))) return false;
		 */

		int topPos = 0;
		int leftPos = 0;
		int zIndex = 1;
		if (getChildren().size() > 0) {
			// Card lastCard = (Card)cards.lastElement();
			Card lastCard = getLastCard();

			if (cardGame.getGameMode() != GameMode.DEAL
					&& cgVisibility == CGVisibility.ALL) {
				lastCard.showFront();
			} else {
				lastCard.showBack();
			}

			zIndex = DOM.getIntStyleAttribute(lastCard.getElement(), "zIndex") + 1;

			if (layout == CGLayout.CASCADE) {
				// Margin is dependent on the visibility (front/back) of the
				// previous card
				int tempOffset = (lastCard.isFrontShowing() ? MARGIN_FRONT : MARGIN_BACK);
				switch (buildingDirection) {
				case LEFT:
					leftPos = DOM.getIntStyleAttribute(lastCard.getElement(), "marginLeft") - tempOffset;
					break;
				case RIGHT:
					leftPos = DOM.getIntStyleAttribute(lastCard.getElement(), "marginLeft") + tempOffset;
					break;
				case TOP:
					topPos = DOM.getIntStyleAttribute(lastCard.getElement(), "marginTop") - tempOffset;
					break;
				case BOTTOM:
					topPos = DOM.getIntStyleAttribute(lastCard.getElement(), "marginTop") + tempOffset;
					break;
				default:
					// Default value, to prohibit default!
					break;
				}
			}
			if (DNDManager.isDropTarget(lastCard)) {
				DNDManager.unregisterDropController(DNDManager.getDropTarget(lastCard));
			}
		} else {
			// Pile is empty
			if (DNDManager.isDropTarget(this)) {
				DNDManager.unregisterDropController(DNDManager.getDropTarget(this));
			}
		}

		Card card = null;
		for (int i = 0; i < widgets.size(); i++) {
			card = widgets.get(i);
			logger.log(Level.INFO, "card is " + card.getElement().getId());

			final Style style = card.getElement().getStyle();
			style.setZIndex(zIndex);
		    style.setMarginTop(topPos, Style.Unit.PX);
		    style.setMarginLeft(leftPos, Style.Unit.PX);

			logger.log(Level.INFO, "marginTop: " + topPos);

			if (cardGame.getGameMode() != GameMode.DEAL) {
				switch (cgVisibility) {
				case ALL:
					card.showFront();
					break;
				case TOP:
				case NONE:
					card.showBack();
					if (DNDManager.isDropTarget(card)) {
						DNDManager.unregisterDropController(DNDManager.getDropTarget(card));
					}
					break;
				default:
					assert false;
					break;
				}
			}
			add(card);
			card.setPile(this);

			zIndex++;
			logger.log(Level.INFO, "Layout: " + layout.name());
			if (layout == CGLayout.CASCADE) {
				// position for next card
				switch (buildingDirection) {
				case LEFT:
					leftPos -= card.isFrontShowing() ? MARGIN_FRONT : MARGIN_BACK;
					break;
				case RIGHT:
					leftPos += card.isFrontShowing() ? MARGIN_FRONT : MARGIN_BACK;
					break;
				case TOP:
					topPos -= card.isFrontShowing() ? MARGIN_FRONT : MARGIN_BACK;
					break;
				case BOTTOM:
					topPos += card.isFrontShowing() ? MARGIN_FRONT : MARGIN_BACK;
					break;
				default:
					// Default value, to prohibit default!
					break;
				}
			}
		}

		// parameters for last card
		if (cardGame.getGameMode() != GameMode.DEAL && cgVisibility == CGVisibility.TOP) {
			card.showFront();
		}

		if (card.isFrontShowing()) {
			/*
			 * if (!DNDManager.isDragSource(card)) new CGDragSource(card);
			 */
			if (!DNDManager.isDropTarget(card)) {
				final CGSimpleDropController dropController = new CGSimpleDropController(card);
				DNDManager.registerDropController(dropController);
			}
		}

		/*
		 * if (!cards.addAll(widgets)) { return false; }
		 */

		listeners.fireAdd();

		return true;
	}

	// Precondition: checked with acceptsRemove
	/** Convenience-method to reduce DnD (un)register overhead */
	public boolean removeAllCards(final List<Card> widgets) {
		// Empty list means nothing to do
		if (widgets.isEmpty()) {
			logger.log(Level.INFO, "Cannot remove because list is empty!");
			return false;
		}

		/*
		 * if (!cards.removeAll(widgets)) { return false; }
		 */

		for (int i = widgets.size(); i > 0; i--) {
			final Card cardToRemove = widgets.get(i - 1);
			if (!remove(cardToRemove)) {
				logger.log(Level.INFO, "Could not remove card " + cardToRemove.getElement().getId() + "!");
				// This was removed since gwt-dnd removed the card before!
				// return false;
			}
		}

		if (getChildren().size() > 0) {
			logger.log(Level.INFO, "Pile is not empty.");
			// Card lastCard = (Card)cards.lastElement();
			final Card lastCard = getLastCard();
			logger.log(Level.INFO, "cgVisibility: " + cgVisibility.name());
			switch (cgVisibility) {
			case ALL:
			case TOP:
				lastCard.showFront();
				if (!DNDManager.isDropTarget(lastCard)) {
					final CGSimpleDropController dropController = new CGSimpleDropController(lastCard);
					DNDManager.registerDropController(dropController);
				}
				break;
			case NONE:
				lastCard.showBack();
				break;
			default:
				assert false;
				break;
			}
		} else {
			logger.log(Level.INFO, "Pile is empty => declare pile as drop target.");
			// Pile is empty => declare pile as drop target
			if (!DNDManager.isDropTarget(this)) {
				final CGSimpleDropController dropController = new CGSimpleDropController(
						this);
				DNDManager.registerDropController(dropController);
			}
		}

		logger.log(Level.INFO, "Fire remove.");
		listeners.fireRemove();
		return true;
	}

	public boolean moveTo(final Pile newPile, final Card card) {
		logger.log(Level.INFO, "moveTo");
		logger.log(Level.INFO, "moveTo: remove card");
		if (!removeCard(card)) {
			logger.log(Level.INFO, "Could not remove card from old pile in moveTo!");
			return false;
		}
		logger.log(Level.INFO, "moveTo: add card");
		if (!newPile.addCard(card)) {
			logger.log(Level.INFO, "Could not add card to new pile in moveTo!");
			return false;
		}
		return true;
	}

	public boolean moveTo(final Pile newPile, final List<Card> cards) {
		if (!removeAllCards(cards)) {
			logger.log(Level.INFO, "Could not remove all cards from old pile in moveTo!");
			return false;
		}
		if (!newPile.addAllCards(cards)) {
			logger.log(Level.INFO, "Could not add all cards to new pile in moveTo!");
			return false;
		}
		return true;
	}

	/*
	 * public boolean acceptsRemoveFrom(Card card) { // Wenn R�ckseite gezeigt
	 * wird if (card.isBackShowing()) { return false; } // Wenn letzte Karte if
	 * (getChildren().indexOf(card) + 1 == getChildren().size()) { return true;
	 * } Card prevCard = card; for (int i = getChildren().indexOf(card) + 1; i <
	 * getChildren().size(); i++) { Card myCard = (Card)getChildren().get(i); if
	 * (!correctOrder(prevCard, myCard)) { return false; } prevCard = myCard; }
	 * // Wenn Stapel leer und es wird das abgefragt ist ein Fehler aufgetreten
	 * if (getChildren().size() == 0) {
	 * System.err.println("Can't drag from an empty pile!"); return false; }
	 * return true; }
	 */

	public boolean acceptsAdd(final Card card) {
		final List<Card> cards = new ArrayList<Card>();
		cards.add(card);
		return acceptsAddAll(cards);
	}

	/** Basic acceptsAdd function. */
	public boolean acceptsAddAll(final List<Card> cards) {
		if (cards.isEmpty()) {
			logger.log(Level.INFO, "List of cards must not be empty!");
			return false;
		}

		if (cardGame.getGameMode() == CardGame.GameMode.DEAL) {
			logger.log(Level.INFO, "Accepted while in 'DEAL' mode.");
			return true;
		}

		if (limit >= 0 && (getCardCount() + cards.size()) > limit) {
			logger.log(Level.INFO, "Cannot add more cards than the limit!");
			return false;
		}

		List<Card> list;
		if (getCardCount() > 0) {
			list = new ArrayList<Card>();
			list.add(getLastCard());
			list.addAll(cards);
		} else {
			logger.log(Level.INFO, "Empty pile.");
			list = cards;
			// Check first card
			switch (cgEmptyStart) {
			case NULL:
				logger.log(Level.INFO, "Empty start rule 'NULL'.");
				break;
			case NONE:
				logger.log(Level.INFO, "Empty start rule 'NONE'.");
				return false;
			case FUNC:
				logger.log(Level.INFO, "Empty start rule 'FUNC'.");
				if (!acceptsFirstCard(cards.get(0))) {
					return false;
				}
				break;
			case KING:
				logger.log(Level.INFO, "Empty start rule 'KING'.");
				if (cards.get(0).getRank() != Rank.KING) {
					return false;
				}
				break;
			case ACE:
				logger.log(Level.INFO, "Empty start rule 'ACE'.");
				if (cards.get(0).getRank() != Rank.ACE) {
					return false;
				}
				break;
			default:
				logger.log(Level.INFO, "Empty start rule invalid or not set!");
				assert false : "cgEmptyStart value invalid or not set";
				break;
			}
		}

		final boolean result = buildingAdd.accepts(list);
		logger.log(Level.INFO, "Result of acceptsAddAll is " + result);
		return result;
	}

	public boolean acceptsRemove(final Card card) {
		List<Card> cards = new ArrayList<Card>();
		cards.add(card);
		return acceptsRemoveAll(cards);
	}

	/** Basic acceptsRemove function. */
	public boolean acceptsRemoveAll(final List<Card> cards) {
		if (cards.isEmpty()) {
			logger.log(Level.INFO, "List of cards must not be empty!");
			return false;
		}

		if (cardGame.getGameMode() == CardGame.GameMode.DEAL) {
			logger.log(Level.INFO, "Accepted while in 'DEAL' mode.");
			return true;
		}

		// More cards would be removed than there are on the pile
		if (cards.size() > getCardCount()) {
			logger.log(Level.INFO, "Cannot remove more cards than there are on the pile!");
			return false;
		}

		switch (cgRuleRemove) {
		case ANY:
			logger.log(Level.INFO, "Removing allowed because of remove rule 'ANY'.");
			return true;
		case TOP:
			final boolean resultTop = cards.size() == 1 && cards.get(0) == getLastCard();
			logger.log(Level.INFO, "Removing" + (resultTop ? " " : " not ") + "allowed in remove rule 'TOP'.");
			return resultTop;
		case BUILDING:
			final boolean resultBuilding = buildingRemove.accepts(cards);
			logger.log(Level.INFO, "Removing" + (resultBuilding ? " " : " not ") + "allowed in remove rule 'BUILDING'.");
			return resultBuilding;
		case NONE:
			logger.log(Level.INFO, "Removing not allowed because of remove rule 'NONE'.");
			return false;
		default:
			logger.log(Level.INFO, "Removing not allowed because no (or invalid) remove rule specified.");
			assert false : "No (or invalid) remove rule specified.";
			return false;
		}
	}

	/** Basic acceptsDrag function. */
	public boolean acceptsDragAll(final List<Card> cards) {
		return acceptsRemoveAll(cards);
	}

	/** Basic acceptsDrop function. */
	public boolean acceptsDropAll(final List<Card> cards) {
		return acceptsAddAll(cards);
	}

	public void onClick(final Card sender) {
		cardListeners.fireCardClick(sender);
	}

	public void onDoubleClick(final Card sender) {
		cardListeners.fireCardDoubleClick(sender);
	}

	public Card getLastCard() {
		if (getChildren().size() > 0) {
			final Card lastCard = (Card) getChildren().get(
					getChildren().size() - 1);
			logger.log(Level.INFO, "Last card is " + lastCard.getElement().getId() + ".");
			return lastCard;
		} else {
			logger.log(Level.INFO, "Last card is null.");
			return null;
		}
	}

//	public void shuffle() {
//		Collections.shuffle(cards);
//	}

	public int getCardCount() {
		return getChildren().size();
	}

	public Card getCard(int index) {
		return (Card) getChildren().get(index);
	}

	public int getCardIndex(final Card card) {
		return getChildren().indexOf(card);
	}

	public enum CGLayout {
		STACK, CASCADE;
	}

	public enum CGVisibility {
		ALL, NONE, TOP;
	}

	public enum CGEmptyStart {
		NULL, // every card allowed
		NONE, // no card allowed
		FUNC, // check function to decide
		ACE, // convenience value (for sotaire)
		KING; // convenience value (for sotaire)
	}

	/** Decides when removal of cards is allowed. */
	public enum CGRemoveRule {
		ANY, // always
		TOP, // only top card
		BUILDING, // check function to decide
		NONE; // never
	}

	public enum CGBuildingDirection {
		LEFT, RIGHT, TOP, BOTTOM;
	}

	/** to be overwritten if CGEmptyStart.FUNC is set */
	public boolean acceptsFirstCard(final Card card) {
		return false;
	}

	public void setCgVisibility(CGVisibility cgVisibility) {
		this.cgVisibility = cgVisibility;
	}

	public void setCgEmptyStart(CGEmptyStart cgEmptyStart) {
		this.cgEmptyStart = cgEmptyStart;
	}

	/**
	 * sets the maximum number of cards that may be on a stack (-1 means
	 * infinite)
	 */
	public void setLimit(int limit) {
		this.limit = limit;
	}

	@Override
	public void addPileListener(PileListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removePileListener(PileListener listener) {
		listeners.remove(listener);
	}

	@Override
	public void addCardListener(CardListener listener) {
		cardListeners.add(listener);
	}

	@Override
	public void removeCardListener(CardListener listener) {
		cardListeners.remove(listener);
	}

	public CardGame getCardGame() {
		return cardGame;
	}

	/**
	 * @return the building-rule for adding cards
	 */
	public Building getBuildingAdd() {
		return buildingAdd;
	}

	/**
	 * @param buildingAdd
	 *            the building-rule for adding cards to set
	 */
	public void setBuildingAdd(Building buildingAdd) {
		this.buildingAdd = buildingAdd;
	}

	/**
	 * @return the building-rule for removing cards
	 */
	public Building getBuildingRemove() {
		return buildingRemove;
	}

	/**
	 * @param buildingRemove
	 *            the building-rule for removing cards to set
	 */
	public void setBuildingRemove(Building buildingRemove) {
		this.buildingRemove = buildingRemove;
	}

	/**
	 * @return the building-direction
	 */
	public CGBuildingDirection getBuildingDirection() {
		return buildingDirection;
	}

	/**
	 * @param buildingDirection
	 *            the building-direction to set
	 */
	public void setBuildingDirection(CGBuildingDirection buildingDirection) {
		this.buildingDirection = buildingDirection;
	}
}
