package com.rainerschuster.cardgames.client.ui;

/**
 * A widget that implements this interface sources the events defined by the
 * {@link com.rainerschuster.cardgames.client.ui.CardListener} interface.
 * 
 * @author Rainer Schuster
 */
public interface SourcesCardEvents {

	/**
	 * Adds a listener interface to receive card events.
	 * 
	 * @param listener
	 *            the listener interface to add
	 */
	void addCardListener(CardListener listener);

	/**
	 * Removes a previously added listener interface.
	 * 
	 * @param listener
	 *            the listener interface to remove
	 */
	void removeCardListener(CardListener listener);
}
