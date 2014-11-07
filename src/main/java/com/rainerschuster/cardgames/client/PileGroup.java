package com.rainerschuster.cardgames.client;

import com.google.gwt.user.client.ui.HorizontalPanel;

/**
 * @author Rainer Schuster
 */
public abstract class PileGroup extends HorizontalPanel /* Vector */{
	static final long serialVersionUID = 1L;

	/** @return Prefix of the <code>id</code> argument. */
	abstract String getPrefix();

//	// synchronized?
//	@Override
//	public boolean add(Object o) {
//		((Pile) o).getElement().setId(getPrefix() + (size() + 1));
//		return super.add(o);
//	}

	/** @return Number of empty piles. */
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
