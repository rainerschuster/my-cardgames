package com.rainerschuster.cardgames.client.ui;

import java.util.EventListener;

import com.google.gwt.user.client.ui.Widget;

/**
 * Event listener interface for doubleclick events.
 * 
 * @author Rainer Schuster
 */
public interface DoubleClickListener extends EventListener {

	/**
	 * Fired when the user doubleclicks on a widget.
	 * 
	 * @param sender
	 *            the widget sending the event.
	 */
	void onDoubleClick(Widget sender);
}
