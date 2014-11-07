package com.rainerschuster.cardgames.client.ui;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.Widget;

/**
 * A helper class for implementers of the SourcesDoubleClickEvents interface.
 * This subclass of {@link ArrayList} assumes that all objects added to it will
 * be of type {@link com.rainerschuster.cardgames.client.ui.DoubleClickListener}
 * .
 * 
 * @author Rainer Schuster
 */
public class DoubleClickListenerCollection extends
		ArrayList<DoubleClickListener> {

	private static final long serialVersionUID = 1L;

	/**
	 * Fires a doubleclick event to all listeners.
	 * 
	 * @param sender
	 *            the widget sending the event.
	 */
	public void fireDoubleClick(Widget sender) {
		for (DoubleClickListener listener : this) {
			listener.onDoubleClick(sender);
		}
	}
}
