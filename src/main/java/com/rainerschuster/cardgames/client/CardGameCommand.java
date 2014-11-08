package com.rainerschuster.cardgames.client;

import com.rainerschuster.cardgames.client.games.CardGame;

import com.google.gwt.user.client.Command;

/**
 * @author Rainer Schuster
 */
public class CardGameCommand implements Command {

    private final CardGame cardGame;

    public CardGameCommand(CardGame cardGame) {
        super();
        this.cardGame = cardGame;
    }

    @Override
    public void execute() {
        cardGame.start();
    }

}
