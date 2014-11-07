package com.rainerschuster.cardgames.client.games;

import java.util.List;

import com.rainerschuster.cardgames.client.Building;
import com.rainerschuster.cardgames.client.Card;
import com.rainerschuster.cardgames.client.CardValues;
import com.rainerschuster.cardgames.client.Deck;
import com.rainerschuster.cardgames.client.DefaultCardValues;
import com.rainerschuster.cardgames.client.Table;
import com.rainerschuster.cardgames.client.dnd.DNDManager;

/**
 * @author Rainer Schuster
 */
public abstract class CardGame {
	private int gameMode;
	Table table;
	Deck deck = new Deck();
	CardValues cardValues = new DefaultCardValues(deck);
	//CellGroup cells = new CellGroup();
	//FoundationGroup foundations = new FoundationGroup();
	//TableauGroup tableaus = new TableauGroup();

	public static class GameMode {
		public static final int DEAL = 0; // cards are added as they come
		public static final int PLAY = 1;
	}

	public CardGame(Table table) {
		this.table = table;
	}

	/** Initializes the game. */
	public void start() {
		DNDManager.unregisterDropControllers();

		table.clear();
		init();
		layout();
		
		setGameMode(GameMode.DEAL);
		firstDeal();
		
		// Add it to the root panel
		//RootPanel.get().add(myPanel);
		/*Widget slot_table = RootPanel.get("slot_table");
		RootPanel.get().remove(slot_table);
		table.add(slot_table);
		RootPanel.get().add(table);*/
		setGameMode(GameMode.PLAY);
	}

	/** Initializes the game-specific components. */
	public abstract void init();

	/** Sets the areas on the table. */
	public abstract void layout();

	public abstract void firstDeal();

	/** @return {@code true} if the player has won the game */
	public boolean isWon() {
		return false;
	}

	public int getGameMode() {
		return gameMode;
	}

	public void setGameMode(int gameMode) {
		this.gameMode = gameMode;
	}

	public Table getTable() {
		return table;
	}

	public CardValues getCardValues() {
		return cardValues;
	}

	/** Convenience-constant to deny building. */
	public static final Building buildingForbidden = new Building(){
		/* (non-Javadoc)
		 * @see com.rainerschuster.cardgames.client.Building#accepts(java.util.List)
		 */
		@Override
		public boolean accepts(List<Card> cards) {
			return false;
		}

		@Override
		protected boolean accepts(Card card1, Card card2) {
			return false;
		}
	};
	
	/** Convenience-constant to allow building (in nearly any case). */
	public static final Building buildingAllowed = new Building(){
		@Override
		protected boolean accepts(Card card1, Card card2) {
			return true;
		}
	};
	
}
