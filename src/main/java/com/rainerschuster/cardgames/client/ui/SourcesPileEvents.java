package com.rainerschuster.cardgames.client.ui;

/**
 * A widget that implements this interface sources the events defined by the
 * {@link com.rainerschuster.cardgames.client.ui.PileListener} interface.
 * 
 * @author Rainer Schuster
 */
public interface SourcesPileEvents {

    /**
     * Adds a listener interface to receive pile events.
     * 
     * @param listener
     *            the listener interface to add
     */
    void addPileListener(PileListener listener);

    /**
     * Removes a previously added listener interface.
     * 
     * @param listener
     *            the listener interface to remove
     */
    void removePileListener(PileListener listener);
}
