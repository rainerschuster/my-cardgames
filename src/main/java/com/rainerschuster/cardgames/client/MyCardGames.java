package com.rainerschuster.cardgames.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.UmbrellaException;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.RootPanel;
import com.rainerschuster.cardgames.client.dnd.DNDManager;
import com.rainerschuster.cardgames.client.games.Diplomat;
import com.rainerschuster.cardgames.client.games.FreeCell;
import com.rainerschuster.cardgames.client.games.Klondike;
import com.rainerschuster.cardgames.client.games.SpiderSolitaire;
import com.rainerschuster.cardgames.client.games.Yukon;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 * @author Rainer Schuster
 */
public class MyCardGames implements EntryPoint {

	private static final Logger LOG = Logger.getLogger(MyCardGames.class.getName());

	private Table table;
	private PickupDragController dragController;

	private Messages messages = GWT.create(Messages.class);

	/**
	 * This is the entry point method.
	 */
	@Override
	public void onModuleLoad() {
		GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
			@Override
			public void onUncaughtException(Throwable e) {
				Throwable unwrapped = unwrap(e);
				LOG.log(Level.SEVERE, "Ex caught!", unwrapped);
			}

			public Throwable unwrap(Throwable e) {
				if (e instanceof UmbrellaException) {
					UmbrellaException ue = (UmbrellaException) e;
					if (ue.getCauses().size() == 1) {
						return unwrap(ue.getCauses().iterator().next());
					}
				}
				return e;
			}
		});

		killContextMenu();

		// Inject the contents of the CSS file
		MyResources.INSTANCE.css().ensureInjected();

//	    ContactsServiceAsync rpcService = GWT.create(ContactsService.class);
//	    HandlerManager eventBus = new HandlerManager(null);
//	    AppController appViewer = new AppController(rpcService, eventBus);
//	    appViewer.go(RootPanel.get());

		this.table = new Table();
		final DockPanel body = new DockPanel();
		body.setWidth("100%");

		// Menu
		final MenuBar menu = new MenuBar();
//		// Game
//		final MenuBar gameMenu = new MenuBar(true);
//		//gameMenu.addItem("New Game", newGameCmd);
//		menu.addItem("Game", gameMenu);

		// Select Game
		final MenuBar gamesMenu = new MenuBar(true);
		final MenuBar solitaireGCMenu = new MenuBar(true);
		solitaireGCMenu.addItem("Diplomat", new CardGameCommand(new Diplomat(table)));
		solitaireGCMenu.addItem("FreeCell", new CardGameCommand(new FreeCell(table)));
		solitaireGCMenu.addItem("Klondike", new CardGameCommand(new Klondike(table)));
		solitaireGCMenu.addItem("Spider Solitaire", new CardGameCommand(new SpiderSolitaire(table)));
		solitaireGCMenu.addItem("Yukon", new CardGameCommand(new Yukon(table)));
		gamesMenu.addItem(messages.menuSelectGameSolitaire(), solitaireGCMenu);
//		gamesMenu.addItem(messages.menuSelectGameTest(), new CardGameCommand(new TestGame(table)));
		menu.addItem(messages.menuSelectGame(), gamesMenu);

		// View
		final MenuBar viewMenu = new MenuBar(true);
		final Command fullPageCmd = new Command() {
			@Override
			public void execute() {
				requestFullscreen();
			}
		};
		viewMenu.addItem(messages.menuViewFullScreen(), fullPageCmd);
		menu.addItem(messages.menuView(), viewMenu);

		// Help
		final MenuBar helpMenu = new MenuBar(true);
		helpMenu.addItem(messages.menuHelpAbout(), new Command(){
			@Override
			public void execute() {
				Window.alert("\u00A9 2006-2014 by Rainer Schuster");
			}
		});
		menu.addItem(messages.menuHelp(), helpMenu);

		body.add(menu, DockPanel.NORTH);

		// Left Menu (Top 10, Personal Favorites, ...) => Quickstart
		final HTML menuPlaceholder = new HTML("Placeholder for Menu");
		menuPlaceholder.addStyleName(MyResources.INSTANCE.css().cgSidePlaceholder());
		body.add(menuPlaceholder, DockPanel.WEST);

		final HTML adsPlaceholder = new HTML("Placeholder for Ads");
		adsPlaceholder.addStyleName(MyResources.INSTANCE.css().cgSidePlaceholder());
		body.add(adsPlaceholder, DockPanel.EAST);
		body.setCellHorizontalAlignment(adsPlaceholder, HasHorizontalAlignment.ALIGN_RIGHT);
		/*body.add(new HTMLPanel("<script type=\"text/javascript\"><!--\n" +
				"google_ad_client = \"pub-3271125680235410\";\n" +
				"google_alternate_color = \"008000\";\n" +
				"google_ad_width = 160;\n" +
				"google_ad_height = 600;\n" +
				"google_ad_format = \"160x600_as\";\n" +
				"google_ad_type = \"text_image\";\n" +
				"google_ad_channel =\"4965460994\";\n" +
				"//--></script>\n" +
				"<script type=\"text/javascript\"\n" +
				"  src=\"http://pagead2.googlesyndication.com/pagead/show_ads.js\">\n" +
				"</script>"), DockPanel.EAST);*/

		body.add(table, DockPanel.CENTER);
		body.setCellWidth(table, "100%");
		body.setCellHorizontalAlignment(table, HasHorizontalAlignment.ALIGN_CENTER);
		table.setWidth("100%");

		RootPanel.get().add(body);

//		final PickupDragController dragController = new PickupDragController(RootPanel.get(), false);
		this.dragController = new PickupDragController(table, false);
		// https://code.google.com/p/gwt-dnd/issues/detail?id=52
		dragController.setBehaviorDragStartSensitivity(3);
//		dragController.setBehaviorMultipleSelection(true);
		DNDManager.setDragController(dragController);

		final CGDragHandler dragHandler = new CGDragHandler();
		DNDManager.addDragHandler(dragHandler);
	}

	private static native void killContextMenu() /*-{
		$doc.oncontextmenu = function() {
			return false;
		};
	}-*/;

	/** http://stackoverflow.com/questions/9571557/gwt-fullscreen */
	private native void requestFullscreen() /*-{
		var element = $doc.documentElement;
		if (element.requestFullscreen) {
			element.requestFullscreen();
		} else if (element.mozRequestFullScreen) {
			element.mozRequestFullScreen();
		} else if (element.webkitRequestFullscreen) {
			element.webkitRequestFullscreen();
		} else if (element.msRequestFullscreen) {
			element.msRequestFullscreen();
		}
	}-*/;
}
