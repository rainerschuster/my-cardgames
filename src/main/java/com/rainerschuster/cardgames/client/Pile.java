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
 * <blockquote cite="http://en.wikipedia.org/w/index.php?title=Glossary_of_card_game_terms&oldid=613941263">
 * A set of cards placed on a surface so that they partially or completely overlap.
 * </blockquote>
 * 
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
    }

    public Pile(CardGame cardGame, String id, CGLayout layout,
            CGVisibility visibility, CGEmptyStart emptyStart,
            Building buildingAdd, Building buildingRemove) {
        this(cardGame, id, layout, visibility, emptyStart, buildingAdd, CGRemoveRule.BUILDING);
        this.buildingRemove = buildingRemove;
    }

    // Precondition: checked with acceptsAdd
    /**
     * Adds the specified card to the pile. Note that you have to check if adding is allowed with {@link #acceptsAdd} before!
     * @param card The card to add.
     * @return {@code true} if adding the card was successful.
     */
    public boolean addCard(final Card card) {
        return addAllCards(Collections.singletonList(card));
    }

    // Precondition: checked with acceptsRemove
    /**
     * Removes the specified card from the pile. Note that you have to check if removing is allowed with {@link #acceptsRemove} before!
     * @param card The card to remove.
     * @return {@code true} if removing the card was successful.
     */
    public boolean removeCard(final Card card) {
        return removeAllCards(Collections.singletonList(card));
    }

    /**
     * Removes all cards starting with the specified card from the pile. Note that you have to check if removing is allowed with {@link #acceptsRemove} before!
     * @param card The card from which to remove.
     * @return {@code true} if removing the cards was successful.
     */
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

        for (int i = widgets.size(); i > 0; i--) {
            final Card cardToRemove = widgets.get(i - 1);
            // TODO Rethink this because gwt-dnd removed the card before!
            if (!remove(cardToRemove)) {
                LOG.log(Level.INFO, "Could not remove card " + cardToRemove.getElement().getId() + "!"); //$NON-NLS-1$ //$NON-NLS-2$
                if (dealOriginal.contains(cardToRemove)) {
                    LOG.log(Level.INFO, "Remove dealx card " + cardToRemove + "!"); //$NON-NLS-1$ //$NON-NLS-2$
                    dealOriginal.remove(cardToRemove);
                }
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

    /**
     * Remove the card from this pile and add it to the specified new pile.
     * @param newPile The pile on which the card should be moved.
     * @param card The card to move.
     * @return {@code true} if both removing and adding was successful.
     */
    public boolean moveTo(final Pile newPile, final Card card) {
        if (!removeCard(card)) {
            return false;
        }
        if (!newPile.addCard(card)) {
            return false;
        }
        return true;
    }

    /**
     * Remove the cards from this pile and add it to the specified new pile.
     * @param newPile The pile on which the cards should be moved.
     * @param cards The cards to move.
     * @return {@code true} if both removing and adding was successful.
     */
    public boolean moveTo(final Pile newPile, final List<Card> cards) {
        if (!removeAllCards(cards)) {
            return false;
        }
        if (!newPile.addAllCards(cards)) {
            return false;
        }
        return true;
    }

    /**
     * Redraw the pile according to the rules and the current state of the cards.
     * Cards that were not changed since they were dealt remain as they are.
     */
    public void redraw() {
        int topPos = 0;
        int leftPos = 0;
        int zIndex = 1;

        for (Iterator<Widget> it = getChildren().iterator(); it.hasNext();) {
            final Card card = (Card) it.next();
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

    /**
     * Acceptance check if the specified card may be added to the pile.
     * @param card The card to check.
     * @return {@code true} if the acceptance check was successful.
     */
    public boolean acceptsAdd(final Card card) {
        return acceptsAddAll(Collections.singletonList(card));
    }

    /**
     * Acceptance check if the specified cards may be added to the pile.
     * @param cards The cards to check.
     * @return {@code true} if the acceptance check was successful.
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

    /**
     * Acceptance check if the specified card may be removed from the pile.
     * @param card The card to check.
     * @return {@code true} if the acceptance check was successful.
     */
    public boolean acceptsRemove(final Card card) {
        return acceptsRemoveAll(Collections.singletonList(card));
    }

    /**
     * Acceptance check if the specified cards may be removed from the pile.
     * @param cards The cards to check.
     * @return {@code true} if the acceptance check was successful.
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

    /**
     * @return The last (typically the topmost) card on the pile.
     */
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

    /**
     * @return The number of cards on the pile.
     */
    public int getCardCount() {
        return getChildren().size();
    }

    /**
     * @param index Index of the card to get.
     * @return The card on the pile with the specified index.
     */
    public Card getCard(final int index) {
        return (Card) getChildren().get(index);
    }

    /**
     * @param card The card for which to determine the index.
     * @return The index of the specified card.
     */
    public int getCardIndex(final Card card) {
        return getChildren().indexOf(card);
    }

    /**
     * Decides how piled cards are laid out.
     */
    public enum CGLayout {
        /**
         * Cards are drawn on top of each other so that only the topmost card can directly be seen.
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
         * The topmost card on the pile shows the front side, all others show the back side.
         */
        TOP;
    }

    /**
     * Decides which (bottommost) card is allowed on an empty pile.
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
