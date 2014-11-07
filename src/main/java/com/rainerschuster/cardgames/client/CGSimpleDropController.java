package com.rainerschuster.cardgames.client;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.allen_sauer.gwt.dnd.client.DragContext;
import com.allen_sauer.gwt.dnd.client.VetoDragException;
import com.allen_sauer.gwt.dnd.client.drop.SimpleDropController;
import com.google.gwt.user.client.ui.Widget;

public class CGSimpleDropController extends SimpleDropController {

	private final Logger logger = Logger.getLogger(CGSimpleDropController.class.getName());

	public CGSimpleDropController(Widget dropTarget) {
		super(dropTarget);
	}

	@Override
	public void onDrop(DragContext context) {
		logger.log(Level.INFO, "onDrop");
		super.onDrop(context);
	}

	@Override
	public void onPreviewDrop(DragContext context) throws VetoDragException {
		logger.log(Level.INFO, "onPreviewDrop");
		final Widget sender = context.draggable;
		if (sender instanceof Card) {
			Pile testDeck = null;
			if (getDropTarget() instanceof Card) {
				// Ensures that a move can be cancelled (drop over original pile)
				if (sender.getParent() == getDropTarget().getParent()) {
					logger.log(Level.INFO, "Dropping on original pile is OK.");
					return;
				}
				testDeck = ((Card)getDropTarget()).getPile();
			} else if (getDropTarget() instanceof Pile) {
				// Ensures that the move can be cancelled (drop over same pile later)
				if (sender.getParent() == getDropTarget()) {
					logger.log(Level.INFO, "Dropping on same pile is OK.");
					return;
				}
				testDeck = (Pile)getDropTarget();
			}
			
			if (testDeck != null) {
				final List<Card> cards = new ArrayList<Card>();
//				cards.add((Card) sender);
				//cards.addAll((Collection<? extends Card>) context.selectedWidgets);
				for (Widget w : context.selectedWidgets) {
					logger.log(Level.INFO, "Card widget " + w.getElement().getId());
					cards.add((Card) w);
				}
				if (testDeck.acceptsDropAll(cards)) {
					logger.log(Level.INFO, "onPreviewDrop accepted.");
					return;
				}
				logger.log(Level.INFO, "onPreviewDrop did not accept drop all!");
			}
		}
		logger.log(Level.INFO, "Drag source must be a card!");
		throw new VetoDragException();
	}

}
