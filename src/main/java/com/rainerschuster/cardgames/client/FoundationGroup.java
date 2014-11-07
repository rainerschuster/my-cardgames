package com.rainerschuster.cardgames.client;

import com.google.gwt.user.client.ui.Widget;

/**
 * @author Rainer Schuster
 */
public class FoundationGroup extends PileGroup {

	@Override
	String getPrefix() {
		return "foundation";
	}

	/** @return the {@link Foundation} where the card belongs to, or the first free pile (if it cannot be determined), or {@code null} if adding is not allowed. */
	public Foundation getFoundation(final Card card) {
		/** The first empty pile. */
		Foundation firstFree = null;

		for (Widget w : this) {
			Foundation foundation = (Foundation) w;
			if (foundation.acceptsAdd(card)) {
				if (foundation.getCardCount() > 0) {
					return foundation;
				} else {
					if (firstFree == null) {
						firstFree = foundation;
					}
				}
			}
		}

		// assert firstFree != null : "Every card belongs to a foundation-pile that must not be null!";
		return firstFree;
	}

	/** @return the {@link Foundation} where the suit belongs to, or the first free pile (if it cannot be determined) */
	/*public Foundation getFoundation(int suit) {
		// Hier wird suit als building-rule angenommen!
		Foundation firstFree = null; // The first empty pile

		for (Iterator it = iterator(); it.hasNext();) {
			Foundation foundation = (Foundation)it.next();
			if (foundation.getCardCount() > 0) {
				if (foundation.getCard(0).getSuit() == suit) {
					return foundation;
				}
			} else {
				if (firstFree == null) {
					firstFree = foundation;
				}
			}
		}

		assert firstFree != null : "Every suit belongs to a foundation-pile that must not be null!";
		return firstFree;
	}*/
}
