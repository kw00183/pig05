package edu.westga.cs6910.pig.view;

import edu.westga.cs6910.pig.model.Game;
import edu.westga.cs6910.pig.model.HumanPlayer;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

/**
 * Defines the panel that lets the user indicate whether they want to roll or
 * hold on their turn
 * 
 * @author CS6910
 * @version Summer 2021
 */
public class HumanPane extends GridPane implements InvalidationListener {
	private Button buttonRoll;
	private Button buttonHold;
	private Label labelTurnTotal;
	private Label labelDiceValues;

	private HumanPlayer theHuman;
	private Game theGame;

	/**
	 * Creates a new HumanPane that observes the specified game.
	 * 
	 * @param theGame
	 *            the model object from which this pane gets its data
	 * 
	 * @requires theGame != null
	 */
	public HumanPane(Game theGame) {
		if (theGame == null) {
			throw new IllegalArgumentException("Invalid game");
		}
		this.theGame = theGame;

		this.theGame.addListener(this);

		this.theHuman = this.theGame.getHumanPlayer();

		this.buildPane();
	}

	private void buildPane() {
		Label labelTitle = new Label("~~ " + this.theHuman.getName() + " ~~");
		this.add(labelTitle, 0, 0);

		this.add(new Label("Turn Total: "), 0, 1);
		this.labelTurnTotal = new Label("0");
		this.add(this.labelTurnTotal, 1, 1);

		this.add(new Label("Dice Values: "), 0, 2);
		this.labelDiceValues = new Label("");
		this.add(this.labelDiceValues, 1, 2);

		Pane verticalGap = new Pane();
		verticalGap.minHeightProperty().bind(labelTitle.heightProperty());
		this.add(verticalGap, 0, 3);

		this.buttonRoll = new Button("Roll");
		this.buttonRoll.setOnAction(new TakeTurnListener());
		this.add(this.buttonRoll, 0, 4);

		this.buttonHold = new Button("Hold");
		this.buttonHold.setOnAction(new HoldListener());
		this.add(this.buttonHold, 1, 4);
	}

	@Override
	public void invalidated(Observable observable) {
		boolean myTurn = this.theGame.getCurrentPlayer() == this.theHuman;

		int turnTotal = this.theHuman.getTurnTotal();
		String result = this.theHuman.getDiceValues();
		this.labelDiceValues.setText(result);
		this.labelTurnTotal.setText("" + turnTotal);

		this.setDisable(!myTurn);

		if (this.theGame.isGameOver()) {
			this.setDisable(true);
			return;
		}
	}

	/**
	 * Updates the output labels in preparation for a new game
	 */
	public void clearInformation() {
		this.labelDiceValues.setText("");
		this.labelTurnTotal
				.setText(Integer.toString(this.theHuman.getTurnTotal()));
	}

	private class TakeTurnListener implements EventHandler<ActionEvent> {
		/**
		 * Tells the Game to have its current player (i.e., the human Player)
		 * take its turn.
		 * 
		 */
		@Override
		public void handle(ActionEvent event) {
			if (!HumanPane.this.theGame.isGameOver()) {
				HumanPane.this.theGame.play();
			}
		}
	}

	private class HoldListener implements EventHandler<ActionEvent> {
		/**
		 * Tells the Game that its current player (i.e., the human Player) will
		 * be holding
		 * 
		 */
		@Override
		public void handle(ActionEvent event) {
			if (!HumanPane.this.theGame.isGameOver()) {
				HumanPane.this.theGame.hold();
			}
		}
	}
}
