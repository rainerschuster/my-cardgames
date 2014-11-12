package com.rainerschuster.cardgames.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Widget;
import com.rainerschuster.cardgames.client.Deck.Rank;
import com.rainerschuster.cardgames.client.dnd.DNDManager;
import com.rainerschuster.cardgames.client.games.CardGame;
import com.rainerschuster.cardgames.client.games.CardGame.GameMode;
import com.rainerschuster.cardgames.client.ui.CardListener;
import com.rainerschuster.cardgames.client.ui.CardListenerCollection;
import com.rainerschuster.cardgames.client.ui.PileListener;
import com.rainerschuster.cardgames.client.ui.PileListenerCollection;
import com.rainerschuster.cardgames.client.ui.SourcesCardEvents;
import com.rainerschuster.cardgames.client.ui.SourcesPileEvents;

/**
 * @author Rainer Schuster
 */
public class Pile extends AbsolutePanel implements SourcesPileEvents, SourcesCardEvents {

    private static final Logger LOG = Logger.getLogger(Pile.class.getName());

    // TODO Move to CSS/GSS
    static final int MARGIN_FRONT = 20;
    static final int MARGIN_BACK = 6;

    // private Vector cards;
    private CGLayout layout;
    /**
     * Maximum number of cards that may be on a stack (-1 means infinite).
     */
    private int limit = -1; // TODO What about other negative numbers? => currently < 0 means infinite
    private CGVisibility cgVisibility;
    private CGEmptyStart cgEmptyStart;
    private CGRemoveRule cgRuleRemove;
    private Building buildingAdd;
    private Building buildingRemove;
    private CGBuildingDirection buildingDirection = CGBuildingDirection.BOTTOM;

    private CardGame cardGame;

    private PileListenerCollection listeners = new PileListenerCollection();
    private CardListenerCollection cardListeners = new CardListenerCollection();

    private List<Card> dealOriginal = new ArrayList<Card>();

    private Pile(CardGame cardGame) {
        super();
        this.cardGame = cardGame;

        // Fix defaults of AbsolutePanel
        getElement().getStyle().setOverflow(Overflow.VISIBLE);

        setStylePrimaryName(MyResources.INSTANCE.css().cgPile());
        this.setPixelSize(88, 125);
    }

    public Pile(CardGame cardGame, String id, CGLayout layout,
            CGVisibility visibility, CGEmptyStart emptyStart,
            Building buildingAdd, CGRemoveRule ruleRemove) {
        this(cardGame);
        getElement().setId(id);
        this.layout = layout;
        this.cgVisibility = visibility;
        this.cgEmptyStart = emptyStart;
        this.buildingAdd = buildingAdd;
        this.cgRuleRemove = ruleRemove;
        // this.buildingRemove = buildingRemove;
    }

    public Pile(CardGame cardGame, String id, CGLayout layout,
            CGVisibility visibility, CGEmptyStart emptyStart,
            Building buildingAdd, Building buildingRemove) {
        this(cardGame, id, layout, visibility, emptyStart, buildingAdd, CGRemoveRule.BUILDING);
        this.buildingRemove = buildingRemove;
    }

    // Precondition: checked with acceptsAdd
    public boolean addCard(final Card card) {
        return addAllCards(Collections.singletonList(card));
    }

    // Precondition: checked with acceptsRemove
    public boolean removeCard(final Card card) {
        return removeAllCards(Collections.singletonList(card));
    }

    public void removeAllFrom(final Card card) {
        final List<Card> cards = new ArrayList<Card>();
        for (int i = getCardIndex(card); i < getCardCount(); i++) {
            cards.add(getCard(i));
        }
        removeAllCards(cards);
    }

    // Precondition: checked with acceptsAdd
    /**
     * Convenience-method to reduce DnD (un)register overhead.
     */
    public boolean addAllCards(final List<Card> widgets) {
        // Empty list means nothing to do
        // TODO Return true or false or move code to acceptsAdd?
        if (widgets.isEmpty()) {
            return false;
        }
        
//        if (!acceptsAdd((Card)widgets.get(0))) return false;

        for (Card card : widgets) {
            LOG.log(Level.INFO, "Card is " + card.getElement().getId() + "."); //$NON-NLS-1$ //$NON-NLS-2$
            add(card);
            card.setPile(this);
            if (cardGame.getGameMode() == GameMode.DEAL) {
                dealOriginal.add(card);
            }
        }

        LOG.log(Level.INFO, "Redraw after add.");
        redraw();

        listeners.fireAdd();

        return true;
    }

    // Precondition: checked with acceptsRemove
    /**
     * Convenience-method to reduce DnD (un)register overhead.
     */
    public boolean removeAllCards(final List<Card> widgets) {
        LOG.log(Level.INFO, "Attempting to remove cards from pile " + getElement().getId() + "!"); //$NON-NLS-1$ //$NON-NLS-2$
        // Empty list means nothing to do
        if (widgets.isEmpty()) {
            LOG.log(Level.INFO, "Cannot remove because list is empty!"); //$NON-NLS-1$
            return false;
        }

//        if (!cards.removeAll(widgets)) { return false; }

        for (int i = widgets.size(); i > 0; i--) {
            final Card cardToRemove = widgets.get(i - 1);
            if (!remove(cardToRemove)) {
                LOG.log(Level.INFO, "Could not remove card " + cardToRemove.getElement().getId() + "!"); //$NON-NLS-1$ //$NON-NLS-2$
                // This was removed since gwt-dnd removed the card before!
                if (dealOriginal.contains(cardToRemove)) {
                    LOG.log(Level.INFO, "Remove dealx card " + cardToRemove + "!"); //$NON-NLS-1$ //$NON-NLS-2$
                    dealOriginal.remove(cardToRemove);
                }
                // return false;
            } else {
                if (cardGame.getGameMode() != GameMode.DEAL) {
                    if (dealOriginal.contains(cardToRemove)) {
                        LOG.log(Level.INFO, "Remove deal card " + cardToRemove + "!"); //$NON-NLS-1$ //$NON-NLS-2$
                        dealOriginal.remove(cardToRemove);
                    }
                }
            }
        }
        if (getChildren().size() > 0) {
            final Card lastCard = getLastCard();
            dealOriginal.remove(lastCard);
        }

        LOG.log(Level.INFO, "Redraw after remove.");
        redraw();

        LOG.log(Level.INFO, "Fire remove.");
        listeners.fireRemove();
        return true;
    }

    public boolean moveTo(final Pile newPile, final Card card) {
        LOG.log(Level.INFO, "moveTo: remove card");
        if (!removeCard(card)) {
            LOG.log(Level.INFO, "Could not remove card from old pile in moveTo!");
            return false;
        }
        LOG.log(Level.INFO, "moveTo: add card");
        if (!newPile.addCard(card)) {
            LOG.log(Level.INFO, "Could not add card to new pile in moveTo!");
            return false;
        }
        return true;
    }

    public boolean moveTo(final Pile newPile, final List<Card> cards) {
        if (!removeAllCards(cards)) {
            LOG.log(Level.INFO, "Could not remove all cards from old pile in moveTo!");
            return false;
        }
        if (!newPile.addAllCards(cards)) {
            LOG.log(Level.INFO, "Could not add all cards to new pile in moveTo!");
            return false;
        }
        return true;
    }

//    public boolean acceptsRemoveFrom(final Card card) {
//        // If back side is shown
//        if (card.isBackShowing()) {
//            return false;
//        }
//        // If last card
//        if (getChildren().indexOf(card) + 1 == getChildren().size()) {
//            return true;
//        }
//        Card prevCard = card;
//        for (int i = getChildren().indexOf(card) + 1; i < getChildren().size(); i++) {
//            Card myCard = (Card) getChildren().get(i);
//            if (!correctOrder(prevCard, myCard)) {
//                return false;
//            }
//            prevCard = myCard;
//        }
//        // If the pile is empty and acceptsRemoveFrom is called there may be some error!
//        if (getChildren().size() == 0) {
//            System.err.println("Can't drag from an empty pile!");
//            return false;
//        }
//        return true;
//    }

    /**
     * TODO JavaDoc
     */
    public void redraw() {
        int topPos = 0;
        int leftPos = 0;
        int zIndex = 1;

        for (Iterator<Widget> it = getChildren().iterator(); it.hasNext();) {
            final Card card = (Card) it.next();
//        for (Card card : widgets) {
            LOG.log(Level.INFO, "Card is " + card.getElement().getId() + "."); //$NON-NLS-1$ //$NON-NLS-2$

            final Style style = card.getElement().getStyle();
            style.setZIndex(zIndex);
            style.setTop(topPos, Style.Unit.PX);
            style.setLeft(leftPos, Style.Unit.PX);

            LOG.log(Level.INFO, "marginTop: " + topPos + "."); //$NON-NLS-1$ //$NON-NLS-2$

            if (cardGame.getGameMode() != GameMode.DEAL && !dealOriginal.contains(card)) {
                switch (cgVisibility) {
                case ALL:
                    card.showFront();
                    break;
                case TOP:
                case NONE:
                    card.showBack();
                    if (DNDManager.isDropTarget(card)) {
                        DNDManager.unregisterDropController(DNDManager.getDropTarget(card));
                    }
                    break;
                default:
                    assert false;
                    break;
                }
            }

            // HERE WAS ADD

            zIndex++;
            LOG.log(Level.INFO, "Layout: " + layout.name() + "."); //$NON-NLS-1$ //$NON-NLS-2$
            if (layout == CGLayout.CASCADE) {
                // position for next card
                switch (buildingDirection) {
                case LEFT:
                    leftPos -= card.isFrontShowing() ? MARGIN_FRONT : MARGIN_BACK;
                    break;
                case RIGHT:
                    leftPos += card.isFrontShowing() ? MARGIN_FRONT : MARGIN_BACK;
                    break;
                case TOP:
                    topPos -= card.isFrontShowing() ? MARGIN_FRONT : MARGIN_BACK;
                    break;
                case BOTTOM:
                    topPos += card.isFrontShowing() ? MARGIN_FRONT : MARGIN_BACK;
                    break;
                default:
                    // Default value, to prohibit default!
                    break;
                }
            }
        }

        final Card card = getLastCard();
        if (card != null) {

            // Parameters for the last card
            if (cardGame.getGameMode() != GameMode.DEAL && cgVisibility == CGVisibility.TOP) {
                card.showFront();
            }

            if (card.isFrontShowing()) {
                if (!DNDManager.isDropTarget(card)) {
                    final CGSimpleDropController dropController = new CGSimpleDropController(card);
                    DNDManager.registerDropController(dropController);
                }
            }

            if (DNDManager.isDropTarget(this)) {
                LOG.log(Level.INFO, "Made non-empty pile " + getElement().getId() + " no drop target."); //$NON-NLS-1$ //$NON-NLS-2$
                DNDManager.unregisterDropController(DNDManager.getDropTarget(this));
            }
        } else {
            if (!DNDManager.isDropTarget(this)) {
                LOG.log(Level.INFO, "Made empty pile " + getElement().getId() + " drop target."); //$NON-NLS-1$ //$NON-NLS-2$
                final CGSimpleDropController dropController = new CGSimpleDropController(this);
                DNDManager.registerDropController(dropController);
            }
        }
    }

    public boolean acceptsAdd(final Card card) {
        final List<Card> cards = new ArrayList<Card>();
        cards.add(card);
        return acceptsAddAll(cards);
    }

    /**
     * Basic acceptsAdd function.
     */
    public boolean acceptsAddAll(final List<Card> cards) {
        if (cards.isEmpty()) {
            LOG.log(Level.INFO, "List of cards must not be empty!");
            return false;
        }

        if (cardGame.getGameMode() == CardGame.GameMode.DEAL) {
            LOG.log(Level.INFO, "Accepted while in 'DEAL' mode.");
            return true;
        }

        if (limit >= 0 && (getCardCount() + cards.size()) > limit) {
            LOG.log(Level.INFO, "Cannot add more cards than the limit!");
            return false;
        }

        List<Card> list;
        if (getCardCount() > 0) {
            list = new ArrayList<Card>();
            list.add(getLastCard());
            list.addAll(cards);
        } else {
            LOG.log(Level.INFO, "Empty pile.");
            list = cards;
            // Check first card
            switch (cgEmptyStart) {
            case NULL:
                LOG.log(Level.INFO, "Empty start rule 'NULL'.");
                break;
            case NONE:
                LOG.log(Level.INFO, "Empty start rule 'NONE'.");
                return false;
            case FUNC:
                LOG.log(Level.INFO, "Empty start rule 'FUNC'.");
                if (!acceptsFirstCard(cards.get(0))) {
                    return false;
                }
                break;
            case KING:
                LOG.log(Level.INFO, "Empty start rule 'KING'.");
                if (cards.get(0).getRank() != Rank.KING) {
                    return false;
                }
                break;
            case ACE:
                LOG.log(Level.INFO, "Empty start rule 'ACE'.");
                if (cards.get(0).getRank() != Rank.ACE) {
                    return false;
                }
                break;
            default:
                LOG.log(Level.INFO, "Empty start rule invalid or not set!"); //$NON-NLS-1$
                assert false : "cgEmptyStart value invalid or not set"; //$NON-NLS-1$
                break;
            }
        }

        final boolean result = buildingAdd.accepts(list);
        LOG.log(Level.INFO, "Result of acceptsAddAll is " + result + "."); //$NON-NLS-1$ //$NON-NLS-2$
        return result;
    }

    public boolean acceptsRemove(final Card card) {
        List<Card> cards = new ArrayList<Card>();
        cards.add(card);
        return acceptsRemoveAll(cards);
    }

    /**
     * Basic acceptsRemove function.
     */
    public boolean acceptsRemoveAll(final List<Card> cards) {
        if (cards.isEmpty()) {
            LOG.log(Level.INFO, "List of cards must not be empty!"); //$NON-NLS-1$
            return false;
        }

        if (cardGame.getGameMode() == CardGame.GameMode.DEAL) {
            LOG.log(Level.INFO, "Accepted while in 'DEAL' mode."); //$NON-NLS-1$
            return true;
        }

        // More cards would be removed than there are on the pile
        if (cards.size() > getCardCount()) {
            LOG.log(Level.INFO, "Cannot remove more cards than there are on the pile!"); //$NON-NLS-1$
            return false;
        }

        switch (cgRuleRemove) {
        case ANY:
            LOG.log(Level.INFO, "Removing allowed because of remove rule 'ANY'."); //$NON-NLS-1$
            return true;
        case TOP:
            final boolean resultTop = cards.size() == 1 && cards.get(0) == getLastCard();
            LOG.log(Level.INFO, "Removing" + (resultTop ? " " : " not ") + "allowed in remove rule 'TOP'.");
            return resultTop;
        case BUILDING:
            final boolean resultBuilding = buildingRemove.accepts(cards);
            LOG.log(Level.INFO, "Removing" + (resultBuilding ? " " : " not ") + "allowed in remove rule 'BUILDING'.");
            return resultBuilding;
        case NONE:
            LOG.log(Level.INFO, "Removing not allowed because of remove rule 'NONE'."); //$NON-NLS-1$
            return false;
        default:
            LOG.log(Level.INFO, "Removing not allowed because no (or invalid) remove rule specified."); //$NON-NLS-1$
            assert false : "No (or invalid) remove rule specified."; //$NON-NLS-1$
            return false;
        }
    }

    /**
     * Basic acceptsDrag function.
     */
    public boolean acceptsDragAll(final List<Card> cards) {
        return acceptsRemoveAll(cards);
    }

    /**
     * Basic acceptsDrop function.
     */
    public boolean acceptsDropAll(final List<Card> cards) {
        return acceptsAddAll(cards);
    }

    public void onClick(final Card sender) {
        cardListeners.fireCardClick(sender);
    }

    public void onDoubleClick(final Card sender) {
        cardListeners.fireCardDoubleClick(sender);
    }

    public Card getLastCard() {
        if (getChildren().size() > 0) {
            final Card lastCard = (Card) getChildren().get(getChildren().size() - 1);
            LOG.log(Level.INFO, "Last card is " + lastCard.getElement().getId() + "."); //$NON-NLS-1$ //$NON-NLS-2$
            return lastCard;
        } else {
            LOG.log(Level.INFO, "Last card is null."); //$NON-NLS-1$
            return null;
        }
    }

//    public void shuffle() {
//        Collections.shuffle(cards);
//    }

    public int getCardCount() {
        return getChildren().size();
    }

    public Card getCard(int index) {
        return (Card) getChildren().get(index);
    }

    public int getCardIndex(final Card card) {
        return getChildren().indexOf(card);
    }

    /**
     * Decides how piled cards are laid out.
     */
    public enum CGLayout {
        /**
         * Cards are drawn on top of each other so that only the top-most card can directly be seen.
         * Note that this does not necessarily mean that it shows the front side.
         */
        STACK,
        /**
         * Cards have a margin so that each card on the stack can be seen.
         */
        CASCADE;
    }

    /**
     * Decides if the font or the back each card on the pile is visible.
     */
    public enum CGVisibility {
        /**
         * All cards on the pile show the front side.
         */
        ALL,
        /**
         * No card on the pile shows the front side (i.e., all cards show the back side). This is the default visibility of {@link Stock}.
         */
        NONE,
        /**
         * The top-most card on the pile shows the front side, all others show the back side.
         */
        TOP;
    }

    /**
     * Decides which (bottom-most) card is allowed on an empty pile.
     */
    public enum CGEmptyStart {
        /**
         * Every card is allowed.
         */
        NULL,
        /**
         * No card is allowed.
         */
        NONE,
        /**
         * Check function to decide. This means that the method {@link Pile#acceptsFirstCard(Card)} must be overwritten.
         */
        FUNC,
        /**
         * An "ace" must be the first card on an empty pile. This is a convenience value (for solitaire).
         */
        ACE,
        /**
         * A "king" must be the first card on an empty pile. This is a convenience value (for solitaire).
         */
        KING;
    }

    /**
     * Decides if removal of cards is allowed.
     */
    public enum CGRemoveRule {
        /**
         * Removal is always allowed.
         */
        ANY,
        /**
         * Top card may be removed only.
         */
        TOP,
        /**
         * Check buildingRemove to decide.
         */
        BUILDING,
        /**
         * Removal is never allowed.
         */
        NONE;
    }

    /**
     * If the layout value {@link CGLayout#CASCADE} is set, this decides in which direction the layout is built.
     */
    public enum CGBuildingDirection {
        /**
         * Cascade cards from right to left.
         */
        LEFT,
        /**
         * Cascade cards from left to right.
         */
        RIGHT,
        /**
         * Cascade cards from bottom to top (i.e., up).
         */
        TOP,
        /**
         * Cascade cards from top to bottom (i.e., down).
         */
        BOTTOM;
    }

    /**
     * To be overwritten if {@link CGEmptyStart#FUNC} is set!
     */
    public boolean acceptsFirstCard(final Card card) {
        return false;
    }

    public void setCgVisibility(CGVisibility cgVisibility) {
        this.cgVisibility = cgVisibility;
    }

    public void setCgEmptyStart(CGEmptyStart cgEmptyStart) {
        this.cgEmptyStart = cgEmptyStart;
    }

    /**
     * Sets the maximum number of cards that may be on a stack (-1 means infinite).
     */
    public void setLimit(int limit) {
        this.limit = limit;
    }

    @Override
    public void addPileListener(PileListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removePileListener(PileListener listener) {
        listeners.remove(listener);
    }

    @Override
    public void addCardListener(CardListener listener) {
        cardListeners.add(listener);
    }

    @Override
    public void removeCardListener(CardListener listener) {
        cardListeners.remove(listener);
    }

    public CardGame getCardGame() {
        return cardGame;
    }

    /**
     * @return the building-rule for adding cards
     */
    public Building getBuildingAdd() {
        return buildingAdd;
    }

    /**
     * @param buildingAdd
     *            the building-rule for adding cards to set
     */
    public void setBuildingAdd(Building buildingAdd) {
        this.buildingAdd = buildingAdd;
    }

    /**
     * @return the building-rule for removing cards
     */
    public Building getBuildingRemove() {
        return buildingRemove;
    }

    /**
     * @param buildingRemove
     *            the building-rule for removing cards to set
     */
    public void setBuildingRemove(Building buildingRemove) {
        this.buildingRemove = buildingRemove;
    }

    /**
     * @return the building-direction
     */
    public CGBuildingDirection getBuildingDirection() {
        return buildingDirection;
    }

    /**
     * @param buildingDirection
     *            the building-direction to set
     */
    public void setBuildingDirection(CGBuildingDirection buildingDirection) {
        this.buildingDirection = buildingDirection;
    }

}
