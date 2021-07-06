package edu.westga.cs6910.pig.view;

import edu.westga.cs6910.pig.model.Game;
import edu.westga.cs6910.pig.model.Player;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;

/**
 * Defines the new game pane that creates the player selections and panes This
 * class was started by CS6910
 * 
 * @author CS6910, Kim Weible
 * @version Summer 2021
 */
public class NewGamePane extends GridPane {
	private RadioButton radHumanPlayer;
	private RadioButton radComputerPlayer;
	private RadioButton radRandomPlayer;
	private ComboBox<Integer> cmbGoalScore;

	private Game theGame;
	private PigPane thePane;
	private Player theHuman;
	private Player theComputer;

	/**
	 * Creates a NewGamePane object to handle the player radio buttons and the
	 * player pane selections
	 * 
	 * @param theGame
	 *            the domain model object representing the Pig game
	 * @param thePane
	 *            the pig pane object
	 * 
	 * @requires theGame != null
	 */
	public NewGamePane(Game theGame, PigPane thePane) {
		if (theGame == null || thePane == null) {
			throw new IllegalArgumentException("Invalid game");
		}
		this.theGame = theGame;
		this.thePane = thePane;

		this.theHuman = this.theGame.getHumanPlayer();
		this.theComputer = this.theGame.getComputerPlayer();

		this.buildPane();
	}

	private void buildPane() {
		this.setHgap(20);

		this.createGoalScoreItems();

		this.createFirstPlayerItems();

		this.reset();
	}

	private void createFirstPlayerItems() {
		Label lblFirstPlayer = new Label("Who plays first? ");
		this.add(lblFirstPlayer, 2, 0);

		this.radHumanPlayer = new RadioButton(
				this.theHuman.getName() + " first");
		this.radHumanPlayer.setOnAction(new HumanFirstListener());

		this.radComputerPlayer = new RadioButton(
				this.theComputer.getName() + " first");
		this.radComputerPlayer.setOnAction(new ComputerFirstListener());

		this.radRandomPlayer = new RadioButton("Random Player");
		this.radRandomPlayer.setOnAction(new RandomFirstListener());

		ToggleGroup group = new ToggleGroup();
		this.radHumanPlayer.setToggleGroup(group);
		this.radComputerPlayer.setToggleGroup(group);
		this.radRandomPlayer.setToggleGroup(group);

		this.add(this.radHumanPlayer, 3, 0);
		this.add(this.radComputerPlayer, 4, 0);
		this.add(this.radRandomPlayer, 5, 0);
	}

	private void createGoalScoreItems() {
		Label lblGoalScore = new Label("Initial Goal Score: ");
		this.add(lblGoalScore, 0, 0);

		this.cmbGoalScore = new ComboBox<Integer>();
		this.cmbGoalScore.getItems().addAll(100, 50, 20);
		this.cmbGoalScore.setValue(100);
		this.cmbGoalScore.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				int goalScore = NewGamePane.this.cmbGoalScore.getValue();
				NewGamePane.this.theGame.setGoalScore(goalScore);
				NewGamePane.this.thePane.updateGameInfo();
			}
		});
		this.add(this.cmbGoalScore, 1, 0);
	}

	/**
	 * Resets the radio buttons for new selection
	 */
	public void reset() {
		NewGamePane.this.theGame
				.setGoalScore(NewGamePane.this.cmbGoalScore.getValue());
		this.radHumanPlayer.setSelected(false);
		this.radComputerPlayer.setSelected(false);
		this.radRandomPlayer.setSelected(false);
	}

	/**
	 * Defines the listener for computer player first button.
	 */
	private class RandomFirstListener implements EventHandler<ActionEvent> {
		@Override
		/**
		 * Enables the ComputerPlayerPanel and starts a new game. Event handler
		 * for a click in the computerPlayerButton.
		 */
		public void handle(ActionEvent arg0) {
			int goalScore = NewGamePane.this.cmbGoalScore.getValue();

			NewGamePane.this.thePane.showPlayerPane(true);

			if (Math.random() * 10 <= 4) {
				NewGamePane.this.thePane.showComputerPane(false);
				NewGamePane.this.theGame.startNewGame(NewGamePane.this.theComputer,
						goalScore);
			} else {
				NewGamePane.this.thePane.showHumanPane(false);
				NewGamePane.this.theGame.startNewGame(NewGamePane.this.theHuman,
						goalScore);
			}
		}
	}

	/*
	 * Defines the listener for computer player first button.
	 */
	private class ComputerFirstListener implements EventHandler<ActionEvent> {
		@Override
		/**
		 * Enables the ComputerPlayerPanel and starts a new game. Event handler
		 * for a click in the computerPlayerButton.
		 */
		public void handle(ActionEvent arg0) {
			int goalScore = NewGamePane.this.cmbGoalScore.getValue();

			NewGamePane.this.thePane.showComputerPane(false);
			NewGamePane.this.thePane.showPlayerPane(true);
			NewGamePane.this.theGame.startNewGame(NewGamePane.this.theComputer,
					goalScore);
		}
	}

	/*
	 * Defines the listener for human player first button.
	 */
	private class HumanFirstListener implements EventHandler<ActionEvent> {
		/*
		 * Sets up user interface and starts a new game. Event handler for a
		 * click in the human player button.
		 */
		@Override
		public void handle(ActionEvent event) {
			int goalScore = NewGamePane.this.cmbGoalScore.getValue();

			NewGamePane.this.thePane.showPlayerPane(true);
			NewGamePane.this.thePane.showHumanPane(false);
			NewGamePane.this.theGame.startNewGame(NewGamePane.this.theHuman,
					goalScore);
		}
	}
}
