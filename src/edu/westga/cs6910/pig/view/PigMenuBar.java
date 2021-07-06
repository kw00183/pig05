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
 * Defines the class that manages the pig menu bar
 * 
 * @author Kim Weible
 * @version Summer 2021
 * 
 */
public class PigMenuBar {
	private Game theGame;
	private PigPane thePane;
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
	 * @requires theGame != null
	 */
	public PigMenuBar(Game theGame, PigPane thePane, PigHelpDialog helpDialog) {
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
	 * @return vbxMenuHolder the vertical box holding the menu items
	 */
	public VBox createMenu() {
		VBox vbxMenuHolder = new VBox();

		MenuBar mnuMain = new MenuBar();

		Menu mnuFile = this.createGameMenu();

		Menu mnuSettings = this.createStrategyMenu();

		Menu mnuHelp = this.createHelpMenu();

		mnuMain.getMenus().addAll(mnuFile, mnuSettings, mnuHelp);
		vbxMenuHolder.getChildren().addAll(mnuMain);
		return vbxMenuHolder;
	}

	private Menu createStrategyMenu() {
		Menu mnuSettings = new Menu("_Strategy");
		mnuSettings.setMnemonicParsing(true);

		ToggleGroup tglStrategy = new ToggleGroup();

		RadioMenuItem mnuCautious = new RadioMenuItem("_Cautious");
		mnuCautious.setAccelerator(new KeyCodeCombination(KeyCode.C,
				KeyCombination.SHORTCUT_DOWN));
		mnuCautious.setOnAction(new CautiousStrategyListener());
		mnuCautious.setMnemonicParsing(true);
		mnuCautious.setToggleGroup(tglStrategy);

		RadioMenuItem mnuGreedy = new RadioMenuItem("Gr_eedy");
		mnuGreedy.setAccelerator(new KeyCodeCombination(KeyCode.E,
				KeyCombination.SHORTCUT_DOWN));
		mnuGreedy.setOnAction(new GreedyStrategyListener());
		mnuGreedy.setMnemonicParsing(true);
		mnuGreedy.setToggleGroup(tglStrategy);

		RadioMenuItem mnuRandom = new RadioMenuItem("_Random");
		mnuRandom.setAccelerator(new KeyCodeCombination(KeyCode.R,
				KeyCombination.SHORTCUT_DOWN));
		mnuRandom.setOnAction(new RandomStrategyListener());
		mnuRandom.setMnemonicParsing(true);
		mnuRandom.setToggleGroup(tglStrategy);

		PigStrategy currentStrategy = this.theGame.getComputerPlayer()
				.getStrategy();
		if (currentStrategy.getClass() == CautiousStrategy.class) {
			mnuCautious.setSelected(true);
		} else if (currentStrategy.getClass() == RandomStrategy.class) {
			mnuRandom.setSelected(true);
		} else {
			mnuGreedy.setSelected(true);
		}

		mnuSettings.getItems().addAll(mnuCautious, mnuGreedy, mnuRandom);
		return mnuSettings;
	}

	private Menu createGameMenu() {
		Menu mnuGame = new Menu("_Game");
		mnuGame.setMnemonicParsing(true);

		MenuItem mnuNew = new MenuItem("_New");
		mnuNew.setMnemonicParsing(true);
		mnuNew.setAccelerator(new KeyCodeCombination(KeyCode.N,
				KeyCombination.SHORTCUT_DOWN));
		mnuNew.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				PigMenuBar.this.thePane.resetPanes();
			}
		});

		MenuItem mnuExit = new MenuItem("E_xit");
		mnuExit.setMnemonicParsing(true);
		mnuExit.setAccelerator(new KeyCodeCombination(KeyCode.X,
				KeyCombination.SHORTCUT_DOWN));
		mnuExit.setOnAction(event -> System.exit(0));

		mnuGame.getItems().addAll(mnuNew, mnuExit);
		return mnuGame;
	}

	private Menu createHelpMenu() {
		Menu mnuHelp = new Menu("_Help");
		mnuHelp.setMnemonicParsing(true);

		MenuItem mnuHelpContent = new MenuItem("Hel_p Contents");
		mnuHelpContent.setMnemonicParsing(true);
		mnuHelpContent.setAccelerator(new KeyCodeCombination(KeyCode.P,
				KeyCombination.SHORTCUT_DOWN));
		mnuHelpContent.setOnAction(new HelpContentsListener());

		MenuItem mnuAbout = new MenuItem("_About");
		mnuAbout.setMnemonicParsing(true);
		mnuAbout.setAccelerator(new KeyCodeCombination(KeyCode.A,
				KeyCombination.SHORTCUT_DOWN));
		mnuAbout.setOnAction(new HelpAboutListener());

		mnuHelp.getItems().addAll(mnuHelpContent, mnuAbout);
		return mnuHelp;
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
	 * Defines the listener for the help dialog menu
	 */
	private class HelpContentsListener implements EventHandler<ActionEvent> {
		/*
		 * Sets the help dialog menu
		 */
		@Override
		public void handle(ActionEvent event) {
			PigMenuBar.this.helpDialog.setShouldShowHelpDialog(true);
			PigMenuBar.this.helpDialog.showHelpDialog();
		}
	}

	/*
	 * Defines the listener for the help about manu
	 */
	private class HelpAboutListener implements EventHandler<ActionEvent> {
		/*
		 * Sets the help about menu
		 */
		@Override
		public void handle(ActionEvent event) {
			String aboutContent = "Creation Date: July 5, 2021\n"
					+ "Authors: cs6910, Kim Weible";
			Alert aboutMessage = new Alert(AlertType.NONE, aboutContent,
					ButtonType.OK);
			aboutMessage.setTitle("About");
			aboutMessage.show();
		}
	}
}