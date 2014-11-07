package com.rainerschuster.cardgames.client;

import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Rainer Schuster
 */
public abstract class Building {

	private static final Logger LOG = Logger.getLogger(Building.class.getName());

	private boolean checkAll = true;

	public boolean accepts(final List<Card> cards) {
		LOG.log(Level.INFO, "Accepts list contains " + cards.size() + " cards."); //$NON-NLS-1$
		Card prevCard = cards.get(0);
		if (prevCard == null || prevCard.isBackShowing()) {
			if (prevCard == null) {
				LOG.log(Level.INFO, "Not accepting because previous card is null."); //$NON-NLS-1$
			} else if (prevCard.isBackShowing()) {
				LOG.log(Level.INFO, "Not accepting because previous card shows the back."); //$NON-NLS-1$
			}
			return false;
		}
		Card thisCard = null;
		 for (Iterator<Card> it = cards.listIterator(1); it.hasNext();) {
			 thisCard = it.next();
			if (thisCard == null || thisCard.isBackShowing() || !accepts(prevCard, thisCard)) {
				if (thisCard == null) {
					LOG.log(Level.INFO, "Not accepting because this card is null."); //$NON-NLS-1$
				} else if (thisCard.isBackShowing()) {
					LOG.log(Level.INFO, "Not accepting because this card shows the back."); //$NON-NLS-1$
				} else {
					LOG.log(Level.INFO, "Not accepting because this card is not accepted."); //$NON-NLS-1$
				}
				return false;
			}
			if (!checkAll) {
				return true;
			}
			prevCard = thisCard;
		}
		return true;
	}

	protected abstract boolean accepts(Card card1, Card card2);

	public boolean isCheckAll() {
		return checkAll;
	}

	public void setCheckAll(boolean checkAll) {
		this.checkAll = checkAll;
	}
}
