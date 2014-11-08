package com.rainerschuster.cardgames.client;

import com.google.gwt.user.client.ui.HorizontalPanel;

/**
 * @author Rainer Schuster
 */
public abstract class PileGroup extends HorizontalPanel {
    static final long serialVersionUID = 1L;

    /**
     * @return The prefix of the <code>id</code> argument.
     */
    abstract String getPrefix();

    /**
     * @return The number of empty piles.
     */
    public int getEmptyPileCount() {
        int count = 0;
        for (int i = 0; i < getWidgetCount(); i++) {
            if (((Pile) getWidget(i)).getCardCount() == 0) {
                count++;
            }
        }

        return count;
    }

}
