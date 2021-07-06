package edu.westga.cs6910.pig.view;

import edu.westga.cs6910.pig.model.Game;
import javafx.scene.layout.BorderPane;

/**
 * Defines a GUI for the Pig game. This class was started by CS6910
 * 
 * @author CS6910, Kim Weible
 * @version Summer 2021
 */
public class PigPane extends BorderPane {
	private Game theGame;
	
	/**
	 * Creates a BorderPane object to provide the view for the specified Game model
	 * object.
	 * 
	 * @param theGame
	 *            the domain model object representing the Pig game
	 * 
	 * @requires theGame != null
	 * @ensures the pane is displayed properly
	 */
	public PigPane(Game theGame) {
		if (theGame == null) {
			throw new IllegalArgumentException("Invalid game");
		}
		this.theGame = theGame;
		
		BorderPane theFullPane = new FullPigPane(this.theGame);
		
		this.setCenter(theFullPane);
	}
}
