package com.rainerschuster.cardgames.client.ui;

import java.util.ArrayList;

import com.rainerschuster.cardgames.client.Card;

/**
 * A helper class for implementers of the SourcesCardEvents interface. This
 * subclass of {@link ArrayList} assumes that all objects added to it will be of
 * type {@link com.rainerschuster.cardgames.client.ui.CardListener}.
 * 
 * @author Rainer Schuster
 */
public class CardListenerCollection extends ArrayList<CardListener> {

	private static final long serialVersionUID = 1L;

	/**
	 * Fires a click event to all listeners.
	 * 
	 * @param sender
	 *            the card sending the event.
	 */
	public void fireCardClick(Card sender) {
		for (CardListener listener : this) {
			listener.onCardClick(sender);
		}
	}

	/**
	 * Fires a doubleclick event to all listeners.
	 * 
	 * @param sender
	 *            the card sending the event.
	 */
	public void fireCardDoubleClick(Card sender) {
		for (CardListener listener : this) {
			listener.onCardDoubleClick(sender);
		}
	}
}
