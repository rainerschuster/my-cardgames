package com.rainerschuster.cardgames.client.ui;

/**
 * A widget that implements this interface sources the events defined by the
 * {@link com.rainerschuster.cardgames.client.ui.DoubleClickListener} interface.
 * 
 * @author Rainer Schuster
 */
public interface SourcesDoubleClickEvents {

    /**
     * Adds a listener interface to receive doubleclick events.
     * 
     * @param listener
     *            the listener interface to add
     */
    void addDoubleClickListener(DoubleClickListener listener);

    /**
     * Removes a previously added listener interface.
     * 
     * @param listener
     *            the listener interface to remove
     */
    void removeDoubleClickListener(DoubleClickListener listener);
}
