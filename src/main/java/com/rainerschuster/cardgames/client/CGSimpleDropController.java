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

    private static final Logger LOG = Logger.getLogger(CGSimpleDropController.class.getName());

    public CGSimpleDropController(Widget dropTarget) {
        super(dropTarget);
    }

    /* (non-Javadoc)
     * @see com.allen_sauer.gwt.dnd.client.drop.AbstractDropController#onDrop(com.allen_sauer.gwt.dnd.client.DragContext)
     */
    @Override
    public void onDrop(final DragContext context) {
        LOG.log(Level.INFO, "onDrop"); //$NON-NLS-1$
        super.onDrop(context);
    }

    /* (non-Javadoc)
     * @see com.allen_sauer.gwt.dnd.client.drop.AbstractDropController#onPreviewDrop(com.allen_sauer.gwt.dnd.client.DragContext)
     */
    @Override
    public void onPreviewDrop(final DragContext context) throws VetoDragException {
        LOG.log(Level.INFO, "onPreviewDrop"); //$NON-NLS-1$
        final Widget sender = context.draggable;
        if (sender instanceof Card) {
            Pile testDeck = null;
            if (getDropTarget() instanceof Card) {
                // Ensures that a move can be cancelled (drop over original pile)
                if (sender.getParent() == getDropTarget().getParent()) {
                    LOG.log(Level.INFO, "Dropping on original pile is OK."); //$NON-NLS-1$
                    return;
                }
                testDeck = ((Card)getDropTarget()).getPile();
            } else if (getDropTarget() instanceof Pile) {
                // Ensures that the move can be cancelled (drop over same pile later)
                if (sender.getParent() == getDropTarget()) {
                    LOG.log(Level.INFO, "Dropping on same pile is OK."); //$NON-NLS-1$
                    return;
                }
                testDeck = (Pile)getDropTarget();
            }

            if (testDeck != null) {
                final List<Card> cards = new ArrayList<>();
//                cards.add((Card) sender);
//                cards.addAll((Collection<? extends Card>) context.selectedWidgets);
                for (Widget w : context.selectedWidgets) {
                    LOG.log(Level.INFO, "Card widget " + w.getElement().getId() + "."); //$NON-NLS-1$ //$NON-NLS-2$
                    cards.add((Card) w);
                }
                if (testDeck.acceptsDropAll(cards)) {
                    LOG.log(Level.INFO, "onPreviewDrop accepted."); //$NON-NLS-1$
                    return;
                }
                LOG.log(Level.INFO, "onPreviewDrop did not accept drop all!"); //$NON-NLS-1$
            }
        }
        LOG.log(Level.INFO, "Drag source must be a card!"); //$NON-NLS-1$
        throw new VetoDragException();
    }

}
