package com.rainerschuster.cardgames.client;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Rainer Schuster
 */
public abstract class BuildingBySteps extends Building {

    private static final Logger LOG = Logger.getLogger(BuildingBySteps.class.getName());

    private int direction = 0;
    private int suit = 0;
    /**
     * Wrap means "ace on king" (ascending) or "king on ace" (descending).
     */
    private boolean wrap = false;

    public BuildingBySteps(int direction, int suit, boolean wrap) {
        super();
        this.direction = direction;
        this.suit = suit;
        this.wrap = wrap;
    }

    public BuildingBySteps() {
        super();
    }

    // Precondition: card2 is the successor of card1 and both cards must exist
    @Override
    protected boolean accepts(final Card card1, final Card card2) {
        if (card1 == null) {
            LOG.log(Level.INFO, "Card 1 must not be null!"); //$NON-NLS-1$
        }
        if (card2 == null) {
            LOG.log(Level.INFO, "Card 2 must not be null!"); //$NON-NLS-1$
        }
        Objects.requireNonNull(card1);
        Objects.requireNonNull(card2);
        LOG.log(Level.INFO, "Checking suit."); //$NON-NLS-1$
        // Check suit order
        switch (suit) {
        case Suit.NULL:
            LOG.log(Level.INFO, "Checking suit 'NULL'."); //$NON-NLS-1$
            break;
        case Suit.SUIT:
            LOG.log(Level.INFO, "Checking suit 'SUIT'."); //$NON-NLS-1$
            if (!suitOrderSuit(card1, card2)) {
                return false;
            }
            break;
        case Suit.COLOR:
            LOG.log(Level.INFO, "Checking suit 'COLOR'."); //$NON-NLS-1$
            if (!suitOrderColor(card1, card2)) {
                return false;
            }
            break;
        case Suit.ALTERNATING:
            LOG.log(Level.INFO, "Checking suit 'ALTERNATING'."); //$NON-NLS-1$
            if (!suitOrderAlternating(card1, card2)) {
                return false;
            }
            break;
        case Suit.OTHERSUIT:
            LOG.log(Level.INFO, "Checking suit 'OTHERSUIT'."); //$NON-NLS-1$
            if (!suitOrderOtherSuit(card1, card2)) {
                return false;
            }
            break;
        default:
            LOG.log(Level.INFO, "Invalid suit!"); //$NON-NLS-1$
            assert false;
            break;
        }

        LOG.log(Level.INFO, "Checking direction."); //$NON-NLS-1$
        // Check direction
        switch (direction) {
        case Direction.NULL:
            LOG.log(Level.INFO, "Checking direction 'NULL'."); //$NON-NLS-1$
            break;
        case Direction.UP:
            LOG.log(Level.INFO, "Checking direction 'UP'."); //$NON-NLS-1$
            if (card1.getCardValue() > card2.getCardValue()) {
                return false;
            }
            break;
        case Direction.DOWN:
            LOG.log(Level.INFO, "Checking direction 'DOWN'."); //$NON-NLS-1$
            if (card1.getCardValue() < card2.getCardValue()) {
                LOG.log(Level.INFO, "Building in direction 'DOWN' denied."); //$NON-NLS-1$
                return false;
            }
            break;
        default:
            LOG.log(Level.INFO, "Invalid direction!"); //$NON-NLS-1$
            assert false;
            break;
        }

        LOG.log(Level.INFO, "Checking step."); //$NON-NLS-1$
        // Check step (rank order)
        return acceptsStep(card1, card2);
    }

    public static boolean suitOrderSuit(final Card card1, final Card card2) {
        Objects.requireNonNull(card1);
        Objects.requireNonNull(card2);
        return card1.getSuit() == card2.getSuit();
    }

    public static boolean suitOrderColor(final Card card1, final Card card2) {
        Objects.requireNonNull(card1);
        Objects.requireNonNull(card2);
        return card1.isRed() == card2.isRed();
    }

    public static boolean suitOrderAlternating(final Card card1, final Card card2) {
        Objects.requireNonNull(card1);
        Objects.requireNonNull(card2);
        return card1.isRed() == card2.isBlack(); // || (card1.isBlack() == card2.isRed())
    }

    public static boolean suitOrderOtherSuit(final Card card1, final Card card2) {
        Objects.requireNonNull(card1);
        Objects.requireNonNull(card2);
        return card1.getSuit() != card2.getSuit();
    }

    public static class Direction {
        public static final int NULL = 0;
        public static final int UP = 1;
        public static final int DOWN = 2;
    }

    public static class Suit {
        /**
         * any suit allowed
         */
        public static final int NULL = 0;
        /**
         * same suit
         */
        public static final int SUIT = 1;
        /**
         * same color (only black aut red)
         */
        public static final int COLOR = 2;
        /**
         * alternating color (black on red or red on black)
         */
        public static final int ALTERNATING = 3;
        /**
         * other suit
         */
        public static final int OTHERSUIT = 4;
    }

    private boolean acceptsStep(final Card card1, final Card card2) {
        Objects.requireNonNull(card1);
        Objects.requireNonNull(card2);
        return acceptsStep(card1.getCardValue(), card2.getCardValue());
    }

    protected abstract boolean acceptsStep(int cardRankValue1, int cardRankValue2);

    /**
     * @return the direction-rule
     */
    public int getDirection() {
        return direction;
    }

    /**
     * @param direction the direction-rule to set
     */
    public void setDirection(int direction) {
        this.direction = direction;
    }

    /**
     * @return the suit-rule
     */
    public int getSuit() {
        return suit;
    }

    /**
     * @param suit the suit-rule to set
     */
    public void setSuit(int suit) {
        this.suit = suit;
    }

    /**
     * @return {@code true} if "ace on king" (ascending) or "king on ace" (descending) is allowed
     */
    public boolean isWrap() {
        return wrap;
    }

    /**
     * @param wrap {@code true} if "ace on king" (ascending) or "king on ace" (descending) is allowed
     */
    public void setWrap(boolean wrap) {
        this.wrap = wrap;
    }

}
