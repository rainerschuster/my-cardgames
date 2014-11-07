package com.rainerschuster.cardgames.client;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Rainer Schuster
 */
public abstract class Building {

	private final Logger logger = Logger.getLogger(Building.class.getName());

	private boolean checkAll = true;

	public boolean accepts(final List<Card> cards) {
		logger.log(Level.INFO, "Accepts list contains " + cards.size() + " cards.");
		Card prevCard = cards.get(0);
		if (prevCard == null || prevCard.isBackShowing()) {
			if (prevCard == null) {
				logger.log(Level.INFO, "Not accepting because previous card is null.");
			} else if (prevCard.isBackShowing()) {
				logger.log(Level.INFO, "Not accepting because previous card shows the back.");
			}
			return false;
		}
		Card thisCard = null;
		// for (Iterator it = cards.listIterator(1); it.hasNext();) {
		for (int i = 1; i < cards.size(); i++) {
			// thisCard = (Card) it.next();
			thisCard = cards.get(i);
			if (thisCard == null || thisCard.isBackShowing() || !accepts(prevCard, thisCard)) {
				logger.log(Level.INFO, "Checking card #" + i);
				if (thisCard == null) {
					logger.log(Level.INFO, "Not accepting because this card is null.");
				} else if (thisCard.isBackShowing()) {
					logger.log(Level.INFO, "Not accepting because this card shows the back.");
				} else {
					logger.log(Level.INFO, "Not accepting because this card is not accepted.");
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
