package edu.westga.cs6910.pig.view;

import edu.westga.cs6910.pig.model.ComputerPlayer;
import edu.westga.cs6910.pig.model.Game;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

/**
 * Defines the pane that lets the user either roll or hold during their turn
 * This class was started by CS6910
 * 
 * @author CS6910, Kim Weible
 * @version Summer 2021
 */
public class ComputerPane extends GridPane implements InvalidationListener {
	private Game theGame;
	private Label labelTurnTotal;
	private Label labelDiceValues;
	private ComputerPlayer theComputer;
	private Button buttonTakeTurn;

	/**
	 * Creates a new ComputerPane that observes the specified game.
	 * 
	 * @param theGame
	 *            the model object from which this pane gets its data
	 * 
	 * @requires theGame != null
	 */
	public ComputerPane(Game theGame) {
		if (theGame == null) {
			throw new IllegalArgumentException("Invalid game");
		}
		this.theGame = theGame;

		this.theGame.addListener(this);

		this.theComputer = this.theGame.getComputerPlayer();

		this.buildPane();
	}

	private void buildPane() {
		Label labelTitle = new Label("~~ " + this.theComputer.getName() + " ~~");
		this.add(labelTitle, 0, 0);

		this.buttonTakeTurn = new Button("Take Turn");
		this.buttonTakeTurn.setOnAction(new TakeTurnListener());
		this.add(this.buttonTakeTurn, 0, 1);

		Pane verticalGap = new Pane();
		verticalGap.minHeightProperty().bind(labelTitle.heightProperty());
		this.add(verticalGap, 0, 2);

		this.add(new Label("Turn Total: "), 0, 3);
		this.labelTurnTotal = new Label("0");
		this.add(this.labelTurnTotal, 1, 3);

		Label labelDiceValuesHeader = new Label("Dice Values");
		this.add(labelDiceValuesHeader, 0, 4);
		GridPane.setValignment(labelDiceValuesHeader, VPos.TOP);
		this.labelDiceValues = new Label("");
		this.add(this.labelDiceValues, 1, 4);
	}

	@Override
	public void invalidated(Observable theObservable) {
		boolean myTurn = this.theGame.getCurrentPlayer() == this.theComputer;

		if (!myTurn) {
			this.updateTotalLabels();
		}

		if (myTurn) {
			this.setDisable(false);
		} else {
			this.setDisable(true);
		}

		this.setDisable(!myTurn);

		if (this.theGame.isGameOver()) {
			this.setDisable(true);
			return;
		}
	}

	private void updateTotalLabels() {
		int turnTotal = this.theComputer.getTurnTotal();
		if (this.theComputer.getRolledOne()) {
			turnTotal = 0;
		}
		this.labelTurnTotal.setText("" + turnTotal);

		String result = "";
		for (String current : this.theComputer.getRollsInTurn()) {
			result += current + "\n";
		}
		this.labelDiceValues.setText(result);
	}

	/**
	 * Updates the output labels in preparation for a new game
	 */
	public void clearInformation() {
		this.labelTurnTotal.setText("0");
		this.labelDiceValues.setText("");
	}

	/**
	 * Defines the listener for takeTurnButton.
	 */
	private class TakeTurnListener implements EventHandler<ActionEvent> {

		/**
		 * Tells the Game to have its current player (i.e., the computer player)
		 * take its turn.
		 * 
		 * @see javafx.event.EventHandler handle(T-extends-javafx.event.Event)
		 */
		@Override
		public void handle(ActionEvent arg0) {
			if (!ComputerPane.this.theGame.isGameOver()) {
				ComputerPane.this.theGame.play();
			}
		}
	}
}
