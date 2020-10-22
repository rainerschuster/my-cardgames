package com.rainerschuster.cardgames.client;

/**
 * Interface to represent the messages contained in resource bundle:
 * 	C:/my-cardgames/src/main/resources/com/rainerschuster/cardgames/client/Messages.properties'.
 */
public interface Messages extends com.google.gwt.i18n.client.Messages {
  
  /**
   * Translated "© 2006-2014 by Rainer Schuster".
   * 
   * @return translated "© 2006-2014 by Rainer Schuster"
   */
  @DefaultMessage("© 2006-2014 by Rainer Schuster")
  @Key("copyright")
  String copyright();

  /**
   * Translated "Game over :-(".
   * 
   * @return translated "Game over :-("
   */
  @DefaultMessage("Game over :-(")
  @Key("gameOver")
  String gameOver();

  /**
   * Translated "Game won :-)".
   * 
   * @return translated "Game won :-)"
   */
  @DefaultMessage("Game won :-)")
  @Key("gameWon")
  String gameWon();

  /**
   * Translated "Help".
   * 
   * @return translated "Help"
   */
  @DefaultMessage("Help")
  @Key("menuHelp")
  String menuHelp();

  /**
   * Translated "About".
   * 
   * @return translated "About"
   */
  @DefaultMessage("About")
  @Key("menuHelpAbout")
  String menuHelpAbout();

  /**
   * Translated "Select Game".
   * 
   * @return translated "Select Game"
   */
  @DefaultMessage("Select Game")
  @Key("menuSelectGame")
  String menuSelectGame();

  /**
   * Translated "Solitaire".
   * 
   * @return translated "Solitaire"
   */
  @DefaultMessage("Solitaire")
  @Key("menuSelectGameSolitaire")
  String menuSelectGameSolitaire();

  /**
   * Translated "View".
   * 
   * @return translated "View"
   */
  @DefaultMessage("View")
  @Key("menuView")
  String menuView();

  /**
   * Translated "Full Screen".
   * 
   * @return translated "Full Screen"
   */
  @DefaultMessage("Full Screen")
  @Key("menuViewFullScreen")
  String menuViewFullScreen();
}
