package com.rainerschuster.cardgames.client.ui;

import java.util.ArrayList;

/**
 * A helper class for implementers of the SourcesPileEvents interface. This
 * subclass of {@link ArrayList} assumes that all objects added to it will be of
 * type {@link com.rainerschuster.cardgames.client.ui.PileListener}.
 * 
 * @author Rainer Schuster
 */
public class PileListenerCollection extends ArrayList<PileListener> {

	private static final long serialVersionUID = 1L;

	/**
	 * Fires an add event to all listeners.
	 */
	public void fireAdd() {
		for (PileListener listener : this) {
			listener.onAdd();
		}
	}

	/**
	 * Fires a remove event to all listeners.
	 */
	public void fireRemove() {
		for (PileListener listener : this) {
			listener.onRemove();
		}
	}
}
