package edu.westga.cs6910.pig.view;

import edu.westga.cs6910.pig.model.Game;
import edu.westga.cs6910.pig.model.strategies.CautiousStrategy;
import edu.westga.cs6910.pig.model.strategies.GreedyStrategy;
import edu.westga.cs6910.pig.model.strategies.PigStrategy;
import edu.westga.cs6910.pig.model.strategies.RandomStrategy;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.VBox;

/**
 * Defines the class that manages the pig game menu bar
 * 
 * @author Kim Weible
 * @version Summer 2021
 * 
 */
public class PigMenuBar {
	private Game theGame;
	private FullPigPane thePane;
	private PigHelpDialog helpDialog;

	/**
	 * Creates a menu bar object
	 * 
	 * @param theGame
	 *            the domain model object representing the Pig game
	 * @param thePane
	 *            the pig pane object
	 * @param helpDialog
	 *            the helpDialog object
	 * 
	 * @requires theGame != null, thePane != null, helpDialog != null
	 */
	public PigMenuBar(Game theGame, FullPigPane thePane,
			PigHelpDialog helpDialog) {
		if (theGame == null || thePane == null || helpDialog == null) {
			throw new IllegalArgumentException("Invalid game");
		}
		this.theGame = theGame;
		this.thePane = thePane;
		this.helpDialog = helpDialog;
	}

	/**
	 * Builds the vertical box that holds the menu items
	 * 
	 * @return vBoxMenuHolder the vertical box holding the menu items
	 */
	public VBox createMenu() {
		VBox vBoxMenuHolder = new VBox();

		MenuBar menuMain = new MenuBar();

		Menu menuFile = this.createGameMenu();

		Menu menuSettings = this.createStrategyMenu();

		Menu menuHelp = this.createHelpMenu();

		menuMain.getMenus().addAll(menuFile, menuSettings, menuHelp);
		vBoxMenuHolder.getChildren().addAll(menuMain);
		return vBoxMenuHolder;
	}

	/**
	 * Defines the computer strategy menu items of cautious, greedy and random
	 * 
	 * @return menuSettings the menu object containing the strategy menu items
	 */
	private Menu createStrategyMenu() {
		Menu menuSettings = new Menu("_Strategy");
		menuSettings.setMnemonicParsing(true);

		ToggleGroup toggleStrategy = new ToggleGroup();

		RadioMenuItem menuCautious = this.addStrategyItem("_Cautious", KeyCode.C,
				"cautious");
		menuCautious.setToggleGroup(toggleStrategy);

		RadioMenuItem menuGreedy = this.addStrategyItem("Gr_eedy", KeyCode.E,
				"greedy");
		menuGreedy.setToggleGroup(toggleStrategy);

		RadioMenuItem menuRandom = this.addStrategyItem("_Random", KeyCode.R,
				"random");
		menuRandom.setToggleGroup(toggleStrategy);

		PigStrategy currentStrategy = this.theGame.getComputerPlayer()
				.getStrategy();
		if (currentStrategy.getClass() == CautiousStrategy.class) {
			menuCautious.setSelected(true);
		} else if (currentStrategy.getClass() == RandomStrategy.class) {
			menuRandom.setSelected(true);
		} else {
			menuGreedy.setSelected(true);
		}

		menuSettings.getItems().addAll(menuCautious, menuGreedy, menuRandom);
		return menuSettings;
	}

	/**
	 * Defines the radio menu items of the computer strategy for cautious,
	 * greedy and random
	 * 
	 * @return menuStrategy the radio menu items for the strategies
	 */
	private RadioMenuItem addStrategyItem(String menuText, KeyCode key,
			String strategy) {
		RadioMenuItem menuStrategy = new RadioMenuItem(menuText);
		menuStrategy.setAccelerator(
				new KeyCodeCombination(key, KeyCombination.SHORTCUT_DOWN));

		if (strategy.equals("cautious")) {
			menuStrategy.setOnAction(new CautiousStrategyListener());
		} else if (strategy.equals("random")) {
			menuStrategy.setOnAction(new RandomStrategyListener());
		} else {
			menuStrategy.setOnAction(new GreedyStrategyListener());
		}
		menuStrategy.setMnemonicParsing(true);
		return menuStrategy;
	}

	/**
	 * Defines the game menu with options to start new game or exit application
	 * 
	 * @return menuGame the game menu
	 */
	private Menu createGameMenu() {
		Menu menuGame = new Menu("_Game");
		menuGame.setMnemonicParsing(true);

		MenuItem menuNew = new MenuItem("_New");
		menuNew.setMnemonicParsing(true);
		menuNew.setAccelerator(new KeyCodeCombination(KeyCode.N,
				KeyCombination.SHORTCUT_DOWN));
		menuNew.setOnAction(new NewGameListener());

		MenuItem menuExit = new MenuItem("E_xit");
		menuExit.setMnemonicParsing(true);
		menuExit.setAccelerator(new KeyCodeCombination(KeyCode.X,
				KeyCombination.SHORTCUT_DOWN));
		menuExit.setOnAction(event -> System.exit(0));

		menuGame.getItems().addAll(menuNew, menuExit);
		return menuGame;
	}

	/**
	 * Defines the help menu with options to show the help content or about
	 * information
	 * 
	 * @return menuHelp the help menu
	 */
	private Menu createHelpMenu() {
		Menu menuHelp = new Menu("_Help");
		menuHelp.setMnemonicParsing(true);

		MenuItem menuHelpContent = new MenuItem("Hel_p Contents");
		menuHelpContent.setMnemonicParsing(true);
		menuHelpContent.setAccelerator(new KeyCodeCombination(KeyCode.P,
				KeyCombination.SHORTCUT_DOWN));
		menuHelpContent.setOnAction(new HelpContentsListener());

		MenuItem menuAbout = new MenuItem("_About");
		menuAbout.setMnemonicParsing(true);
		menuAbout.setAccelerator(new KeyCodeCombination(KeyCode.A,
				KeyCombination.SHORTCUT_DOWN));
		menuAbout.setOnAction(new HelpAboutListener());

		menuHelp.getItems().addAll(menuHelpContent, menuAbout);
		return menuHelp;
	}

	/*
	 * Defines the listener for the computer's cautious strategy
	 */
	private class CautiousStrategyListener
			implements
				EventHandler<ActionEvent> {
		/*
		 * Sets the cautious strategy for the computer
		 */
		@Override
		public void handle(ActionEvent event) {
			PigMenuBar.this.theGame.getComputerPlayer()
					.setStrategy(new CautiousStrategy());
		}
	}

	/*
	 * Defines the listener for the computer's greedy strategy
	 */
	private class GreedyStrategyListener implements EventHandler<ActionEvent> {
		/*
		 * Sets the greedy strategy for the computer
		 */
		@Override
		public void handle(ActionEvent event) {
			PigMenuBar.this.theGame.getComputerPlayer()
					.setStrategy(new GreedyStrategy());
		}
	}

	/*
	 * Defines the listener for the computer's random strategy
	 */
	private class RandomStrategyListener implements EventHandler<ActionEvent> {
		/*
		 * Sets the random strategy for the computer
		 */
		@Override
		public void handle(ActionEvent event) {
			PigMenuBar.this.theGame.getComputerPlayer()
					.setStrategy(new RandomStrategy());
		}
	}

	/*
	 * Defines the listener for starting a new game
	 */
	private class NewGameListener implements EventHandler<ActionEvent> {
		/*
		 * Sets the actions needed to start the new game
		 */
		@Override
		public void handle(ActionEvent event) {
			PigMenuBar.this.thePane.resetPanes();
		}
	}

	/*
	 * Defines the listener for the help dialog menu
	 */
	private class HelpContentsListener implements EventHandler<ActionEvent> {
		/*
		 * Sets the actions needed to show the help dialog alert
		 */
		@Override
		public void handle(ActionEvent event) {
			PigMenuBar.this.helpDialog.setShouldShowHelpDialog(true);
			PigMenuBar.this.helpDialog.showHelpDialog();
		}
	}

	/*
	 * Defines the listener for the help about menu
	 */
	private class HelpAboutListener implements EventHandler<ActionEvent> {
		/*
		 * Sets the actions needed to show the about information alert
		 */
		@Override
		public void handle(ActionEvent event) {
			String aboutContent = "Creation Date: July 5, 2021\n"
					+ "Authors: cs6910, Kim Weible";
			Alert aboutAlert = new Alert(AlertType.NONE, aboutContent,
					ButtonType.OK);
			aboutAlert.setTitle("About");
			aboutAlert.show();
		}
	}
}