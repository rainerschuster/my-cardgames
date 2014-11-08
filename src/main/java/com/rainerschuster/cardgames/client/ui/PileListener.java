package com.rainerschuster.cardgames.client.ui;

import java.util.EventListener;

/**
 * Event listener interface for pile events.
 * 
 * @author Rainer Schuster
 */
public interface PileListener extends EventListener {

    /**
     * Fired when one or more cards are added on the pile.
     */
    void onAdd();

    /**
     * Fired when one or more cards are removed from the pile.
     */
    void onRemove();
}
