package com.rainerschuster.cardgames.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Image;
import com.rainerschuster.cardgames.client.Deck.Rank;
import com.rainerschuster.cardgames.client.Deck.Suit;
import com.rainerschuster.cardgames.client.dnd.DNDManager;
import com.rainerschuster.cardgames.client.games.CardGame;

/**
 * @author Rainer Schuster
 */
public class Card extends FocusPanel {

	private final Logger logger = Logger.getLogger(Card.class.getName());
	
	/** Per default the back of the card is shown. */
	private boolean backShowing = true;
	
	private Image frontImage;
	private CardGame cardGame;
	private Pile pile;

	private final int rank;

	private final int suit;

	public Card(CardGame cardGame, int rank, int suit) {
		super();
		this.cardGame = cardGame;
		this.rank = rank;
		this.suit = suit;
		this.frontImage = new Image("./img/" + Suit.shortNames()[suit] + Rank.names()[rank] + ".png");
		frontImage.setPixelSize(88, 125);
		frontImage.getElement().getStyle().setWidth(88, Unit.PX);
		frontImage.getElement().getStyle().setHeight(125, Unit.PX);
		getElement().setId(Suit.values()[suit] + "_" + Rank.names()[rank]);

		setStylePrimaryName(MyResources.INSTANCE.css().cgCard());
		setPixelSize(88, 125);
		getElement().getStyle().setOverflow(Overflow.VISIBLE);

		// This is required for FireFox. Otherwise on dragging a click event is additionally fired
		// see also https://code.google.com/p/gwt-dnd/wiki/GettingStarted
		// Quote: "In Firefox the handler will always fire when the mouse is clicked, even at the the end of drag operation"
		Event.addNativePreviewHandler(new Event.NativePreviewHandler() {

			@Override
			public void onPreviewNativeEvent(NativePreviewEvent event) {
				switch (event.getTypeInt()) {
				case Event.ONMOUSEDOWN:
				case Event.ONMOUSEMOVE:
				case Event.ONMOUSEUP:
					event.getNativeEvent().preventDefault();
					break;
				default:
					break;
				}
			}
		});

		DNDManager.makeDraggable(this/*, frontImage*/);

		addDomHandler(new DoubleClickHandler() {
			@Override
			public void onDoubleClick(final DoubleClickEvent event) {
				logger.log(Level.INFO, "DoubleClickEvent");
				pile.onDoubleClick(Card.this);
			}
		}, DoubleClickEvent.getType());

		addDomHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				logger.log(Level.INFO, "ClickEvent");
				event.stopPropagation();
				pile.onClick(Card.this);
			}
		}, ClickEvent.getType());
	}

	/** Shows the front of the card. */
	public void showFront() {
		setWidget(frontImage);
		backShowing = false;
	}

	/** Shows the back of the card. */
	public void showBack() {
		setWidget(null);
		backShowing = true;
	}

	/** Turns around the card. */
	public void toggle() {
		if (isFrontShowing()) {
			showBack();
		} else {
			showFront();
		}
	}

	/** @return {@code true} if the front of the card is currently shown. */
	public boolean isFrontShowing() {
		return !backShowing;
	}

	/** @return {@code true} if the back of the card is currently shown. */
	public boolean isBackShowing() {
		return backShowing;
	}

	public int getRank() {
		return rank;
	}

	public int getSuit() {
		return suit;
	}
	
	public Pile getPile() {
		return pile;
	}

	public void setPile(Pile pile) {
		this.pile = pile;
	}

	public int getCardValue() {
		return cardGame.getCardValues().getCardValue(this);
	}

	public boolean isBlack() {
		return (suit == Suit.CLUBS) || (suit == Suit.SPADES);
	}

	public boolean isRed() {
		return (suit == Suit.DIAMONDS) || (suit == Suit.HEARTS);
	}

}
