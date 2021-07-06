package edu.westga.cs6910.pig.view;

import edu.westga.cs6910.pig.model.Game;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * Defines a GUI for the Pig game. This class was started by CS6910
 * 
 * @author CS6910, Kim Weible
 * @version Summer 2021
 */
public class PigPane extends BorderPane {
	private Game theGame;
	private BorderPane pnContent;
	private HumanPane pnHumanPlayer;
	private ComputerPane pnComputerPlayer;
	private StatusPane pnGameInfo;
	private NewGamePane pnChooseFirstPlayer;
	private PigHelpDialog helpDialog;
	private PigMenuBar menuBar;

	/**
	 * Creates a pane object to provide the view for the specified Game model
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

		this.helpDialog = new PigHelpDialog();
		this.helpDialog.showHelpDialog();
		this.pnContent = new BorderPane();

		this.menuBar = new PigMenuBar(this.theGame, this, this.helpDialog);
		VBox menuBox = this.menuBar.createMenu();

		this.pnChooseFirstPlayer = new NewGamePane(this.theGame, this);
		HBox playerBox = this.createHBoxHolder(this.pnChooseFirstPlayer, false);

		VBox topBox = new VBox();
		topBox.getChildren().addAll(menuBox, playerBox);
		this.pnContent.setTop(topBox);

		this.pnHumanPlayer = new HumanPane(theGame);
		HBox leftBox = this.createHBoxHolder(this.pnHumanPlayer, true);
		this.pnContent.setLeft(leftBox);

		this.pnComputerPlayer = new ComputerPane(theGame);
		HBox centerBox = this.createHBoxHolder(this.pnComputerPlayer, true);
		this.pnContent.setCenter(centerBox);

		this.pnGameInfo = new StatusPane(theGame);
		HBox bottomBox = this.createHBoxHolder(this.pnGameInfo, false);
		this.pnContent.setBottom(bottomBox);

		this.setCenter(this.pnContent);
	}

	private HBox createHBoxHolder(Pane newPane, boolean disable) {
		newPane.setDisable(disable);
		HBox leftBox = new HBox();
		leftBox.setMinWidth(350);
		leftBox.getStyleClass().add("pane-border");
		leftBox.getChildren().add(newPane);
		return leftBox;
	}

	/**
	 * Defines the functions needed to reset the panes
	 */
	public void resetPanes() {
		PigPane.this.pnChooseFirstPlayer.reset();
		PigPane.this.pnChooseFirstPlayer.setDisable(false);
		PigPane.this.pnHumanPlayer.setDisable(true);
		PigPane.this.pnComputerPlayer.setDisable(true);

		if (PigPane.this.helpDialog.getShouldShowHelpDialog()) {
			PigPane.this.helpDialog.showHelpDialog();
		}

		PigPane.this.theGame.resetGame();
		PigPane.this.pnGameInfo.clearInformation();
		PigPane.this.pnHumanPlayer.clearInformation();
		PigPane.this.pnComputerPlayer.clearInformation();
	}

	/**
	 * Updates the status game pane
	 */
	public void updateGameInfo() {
		PigPane.this.pnGameInfo.update();
	}

	/**
	 * Enables or disables the player pane
	 * 
	 * @param isDisabled
	 *            is the pane disabled
	 */
	public void showPlayerPane(boolean isDisabled) {
		PigPane.this.pnChooseFirstPlayer.setDisable(isDisabled);
	}

	/**
	 * Enables or disables the computer pane
	 * 
	 * @param isDisabled
	 *            is the pane disabled
	 */
	public void showComputerPane(boolean isDisabled) {
		PigPane.this.pnComputerPlayer.setDisable(isDisabled);
	}

	/**
	 * Enables or disables the human pane
	 * 
	 * @param isDisabled
	 *            is the pane disabled
	 */
	public void showHumanPane(boolean isDisabled) {
		PigPane.this.pnHumanPlayer.setDisable(isDisabled);
	}
}
