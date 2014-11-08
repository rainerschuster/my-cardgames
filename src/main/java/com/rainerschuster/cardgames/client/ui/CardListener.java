package com.rainerschuster.cardgames.client.ui;

import java.util.EventListener;

import com.rainerschuster.cardgames.client.Card;

/**
 * Event listener interface for card events.
 * 
 * @author Rainer Schuster
 */
public interface CardListener extends EventListener {

    /**
     * Fired when the user clicks on a card.
     * 
     * @param sender
     *            the card sending the event.
     */
    void onCardClick(Card sender);

    /**
     * Fired when the user doubleclicks on a card.
     * 
     * @param sender
     *            the card sending the event.
     */
    void onCardDoubleClick(Card sender);
}
