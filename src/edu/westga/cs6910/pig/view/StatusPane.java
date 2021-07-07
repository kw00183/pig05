package edu.westga.cs6910.pig.view;

import edu.westga.cs6910.pig.model.Game;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 * Defines the pane that lets the user tell the computer player to
 * take its turn and that displays the die values the computer
 * player took on its turn.
 * 
 * @author CS6910
 * @version Summer 2021
 */
public class StatusPane extends GridPane implements InvalidationListener {
	private Game theGame;
	private Label labelStatus;
	
	/**
	 * Creates a new status pane that observes the specified game. 
	 * 
	 * @param theGame	the model object from which this pane gets its data
	 * 
	 * @requires 	theGame != null
	 */
	public StatusPane(Game theGame) {
		if (theGame == null) {
			throw new IllegalArgumentException("Invalid game");
		}
		this.theGame = theGame;
		
		this.theGame.addListener(this);
		
		this.buildPane();
	}

	private void buildPane() {
		this.add(new Label("~~ Game Info ~~"), 0, 0);
		
		this.labelStatus = new Label(this.theGame.toString());
		this.add(this.labelStatus, 0, 1);
	}

	@Override
	public void invalidated(Observable observable) {
		this.update();
	}
	
	/**
	 * Signals the pane to update its information
	 */
	public void update() {
		this.labelStatus.setText(this.theGame.toString());
	}

	/**
	 * Updates the output labels in preparation for a new game
	 */
	public void clearInformation() {		
		this.labelStatus.setText(this.theGame.toString());
	}
}
