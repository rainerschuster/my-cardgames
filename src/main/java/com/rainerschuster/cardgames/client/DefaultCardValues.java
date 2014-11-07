package com.rainerschuster.cardgames.client;

/**
 * @author Rainer Schuster
 */
public class DefaultCardValues extends CardValues {

	public DefaultCardValues(Deck deck) {
		super(deck);
	}

	@Override
	public int getCardValue(Card card) {
		return card.getRank();
	}

	/*private Map<Deck.Rank, Integer> defaultCardValues;

	public DefaultCardValues(Deck deck) {
		super(deck);
		Deck.Rank[] values = Deck.Rank.values();
		defaultCardValues = new HashMap<Deck.Rank, Integer>(values.length);
		for (int i = 0; i < values.length; i++) {
			defaultCardValues.put(values[i], i + 1);
		}
	}

	@Override
	public int getCardValue(Card card) {
		return defaultCardValues.get(card.getRank());
	}*/

}
