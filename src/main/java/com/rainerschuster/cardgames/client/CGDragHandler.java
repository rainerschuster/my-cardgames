package com.rainerschuster.cardgames.client;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.allen_sauer.gwt.dnd.client.DragContext;
import com.allen_sauer.gwt.dnd.client.DragEndEvent;
import com.allen_sauer.gwt.dnd.client.DragHandlerAdapter;
import com.allen_sauer.gwt.dnd.client.DragStartEvent;
import com.allen_sauer.gwt.dnd.client.VetoDragException;
import com.allen_sauer.gwt.dnd.client.util.DragClientBundle;
import com.google.gwt.user.client.ui.Widget;

public class CGDragHandler extends DragHandlerAdapter {

	private static final Logger LOG = Logger.getLogger(CGDragHandler.class.getName());

	@Override
	public void onPreviewDragStart(DragStartEvent event) throws VetoDragException {
		LOG.log(Level.INFO, "onPreviewDragStart"); //$NON-NLS-1$

		final Widget sender = event.getContext().draggable;
		LOG.log(Level.INFO, "Sender is " + sender.getElement().getId() + "."); //$NON-NLS-1$ //$NON-NLS-2$

		if (sender instanceof Card) {
			final Card card = (Card) sender;

			final Pile pile = card.getPile();
			for (int i = pile.getCardIndex(card) + 1; i < pile.getCardCount(); i++) {
				// add drag object
				LOG.log(Level.INFO, "Toggle card #" + i + "."); //$NON-NLS-1$ //$NON-NLS-2$
				event.getContext().selectedWidgets.add(pile.getCard(i));
//				event.getContext().dragController.toggleSelection(pile.getCard(i));
			}

			final List<Card> cards = new ArrayList<Card>();
//			cards.add(card);
			// cards.addAll((Collection<? extends Card>)getDragObjects());
			for (Widget w : event.getContext().selectedWidgets) {
				LOG.log(Level.INFO, "Additional selected card widget " + w.getElement().getId() + "."); //$NON-NLS-1$ //$NON-NLS-2$
				cards.add((Card) w);
			}
			if (!pile.acceptsDragAll(cards)) {
				LOG.log(Level.INFO, "Pile does not accept to drag all selected cards!"); //$NON-NLS-1$
				throw new VetoDragException();
			}
		} else {
			LOG.log(Level.INFO, "Drag source must be a card!"); //$NON-NLS-1$
			throw new VetoDragException();
		}
	}

	@Override
	public void onDragEnd(DragEndEvent event) {
		LOG.log(Level.INFO, "onDragEnd"); //$NON-NLS-1$

		final DragContext context = event.getContext();
		if (context.vetoException == null) {
			final Card sender = (Card)context.selectedWidgets.get(0);

			LOG.log(Level.INFO, "Getting previous pile"); //$NON-NLS-1$
			final Pile oldPile = sender.getPile();
			LOG.log(Level.INFO, "Previous pile " + oldPile.getElement().getId() + "."); //$NON-NLS-1$ //$NON-NLS-2$
			Pile newPile;
			final Widget dropTarget = context.finalDropController.getDropTarget();
			if (dropTarget instanceof Card) {
				newPile = ((Card)dropTarget).getPile();
			} else {
				newPile = (Pile)dropTarget;
			}
			// TODO if possible get rid of this dirty cast hack
			final List<Card> cards = new ArrayList<Card>(context.selectedWidgets.size());
//			cards.addAll((Collection<? extends Card>) widgets);
			for (Widget w : context.selectedWidgets) {
				LOG.log(Level.INFO, "Card widget " + w.getElement().getId() + "."); //$NON-NLS-1$ //$NON-NLS-2$
				cards.add((Card) w);
			}
			oldPile.moveTo(newPile, cards);
		}

		// clear drag objects
		final List<Widget> selected = new ArrayList<Widget>(context.selectedWidgets);
		for (Widget w : selected) {
			LOG.log(Level.INFO, "Unselecting widget " + w.getElement().getId() + "."); //$NON-NLS-1$ //$NON-NLS-2$
//			event.getContext().dragController.toggleSelection(w);
			w.removeStyleName(DragClientBundle.INSTANCE.css().selected());
		}
		context.selectedWidgets.clear();
	}
}
