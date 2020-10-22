package com.rainerschuster.cardgames.client;

import java.util.ArrayList;
import java.util.List;

import com.rainerschuster.cardgames.client.games.CardGame;

/**
 * @author Rainer Schuster
 */
public class Deck {
    public static class Rank {
        public static final int ACE = 1;
        public static final int DEUCE = 2;
        public static final int THREE = 3;
        public static final int FOUR = 4;
        public static final int FIVE = 5;
        public static final int SIX = 6;
        public static final int SEVEN = 7;
        public static final int EIGHT = 8;
        public static final int NINE = 9;
        public static final int TEN = 10;
        public static final int JACK = 11;
        public static final int QUEEN = 12;
        public static final int KING = 13;

        private static final int[] values = new int[]{ACE, DEUCE, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING};
        public static final int[] values() {
            return values;
        }

        private static final String[] names = new String[]{"workaround", "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        public static final String[] names() {
            return names;
        }
    }

    public static class Suit {
        public static final int CLUBS = 0;
        public static final int DIAMONDS = 1;
        public static final int HEARTS = 2;
        public static final int SPADES = 3;

        private static final int[] values = new int[]{CLUBS, DIAMONDS, HEARTS, SPADES};
        public static final int[] values() {
            return values;
        }
        private static final String[] names = new String[]{"&clubs;", "&diams;", "&hearts;", "&spades;"};
        public static final String[] names() {
            return names;
        }

        private static final String[] shortNames = new String[]{"C", "D", "H", "S"};
        public static final String[] shortNames() {
            return shortNames;
        }
    }

    public List<Card> newDeck(CardGame cardGame) {
        return newDeck(cardGame, Rank.values(), Suit.values());
    }

    public List<Card> newDeck(CardGame cardGame, int[] ranks, int[] suits) {
        final List<Card> protoDeck = new ArrayList<>();
        for (int suit = 0; suit < Suit.values().length; suit++) {
            for (int rank = 0; rank < Rank.values().length; rank++) {
                protoDeck.add(new Card(cardGame, Rank.values()[rank], Suit.values()[suit]));
            }
        }
        return protoDeck;
    }

    /*public enum Rank {
		ACE, DEUCE, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING
	}

	public enum Suit {
		CLUBS, DIAMONDS, HEARTS, SPADES
	}

	public enum Special {
		JOKER
	}

	public ArrayList<Card> newDeck() {
		return newDeck(Rank.values(), Suit.values());
	}

	public ArrayList<Card> newDeck(Rank[] ranks, Suit[] suits) {
		final ArrayList<Card> protoDeck = new ArrayList<Card>();
		for (Suit suit : suits) {
			for (Rank rank : ranks) {
				protoDeck.add(new Card(rank, suit));
			}
		}
		return protoDeck;
	}*/

}
