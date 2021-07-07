package edu.westga.cs6910.pig.view;

import edu.westga.cs6910.pig.model.Game;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * Defines a FullPigPane boject for the Pig game.
 * 
 * @author CS6910, Kim Weible
 * @version Summer 2021
 */
public class FullPigPane extends BorderPane {
	private Game theGame;
	private BorderPane paneContent;

	private HumanPane paneHumanPlayer;
	private ComputerPane paneComputerPlayer;
	private StatusPane paneGameInfo;
	private NewGamePane paneChooseFirstPlayer;
	private PigHelpDialog helpDialog;
	private PigMenuBar menuBar;

	/**
	 * Creates a FullPigPane object to build all the panes for the specified
	 * Game model object.
	 * 
	 * @param theGame
	 *            the domain model object representing the Pig game
	 * 
	 * @requires theGame != null, thePane != null
	 */
	public FullPigPane(Game theGame) {
		if (theGame == null) {
			throw new IllegalArgumentException("Invalid game");
		}
		this.theGame = theGame;

		this.buildPane();
	}

	private void buildPane() {
		this.helpDialog = new PigHelpDialog();
		this.helpDialog.showHelpDialog();
		this.paneContent = new BorderPane();

		this.menuBar = new PigMenuBar(this.theGame, this, this.helpDialog);
		VBox menuBox = this.menuBar.createMenu();

		this.paneChooseFirstPlayer = new NewGamePane(this.theGame, this);
		HBox playerBox = this.createHBoxHolder(this.paneChooseFirstPlayer, false);

		VBox topBox = new VBox();
		topBox.getChildren().addAll(menuBox, playerBox);
		this.paneContent.setTop(topBox);

		this.paneHumanPlayer = new HumanPane(this.theGame);
		HBox leftBox = this.createHBoxHolder(this.paneHumanPlayer, true);
		this.paneContent.setLeft(leftBox);

		this.paneComputerPlayer = new ComputerPane(this.theGame);
		HBox centerBox = this.createHBoxHolder(this.paneComputerPlayer, true);
		this.paneContent.setCenter(centerBox);

		this.paneGameInfo = new StatusPane(this.theGame);
		HBox bottomBox = this.createHBoxHolder(this.paneGameInfo, false);
		this.paneContent.setBottom(bottomBox);

		this.setCenter(this.paneContent);
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
		this.paneChooseFirstPlayer.reset();
		this.paneChooseFirstPlayer.setDisable(false);
		this.paneHumanPlayer.setDisable(true);
		this.paneComputerPlayer.setDisable(true);

		if (this.helpDialog.getShouldShowHelpDialog()) {
			this.helpDialog.showHelpDialog();
		}

		this.theGame.resetGame();
		this.paneGameInfo.clearInformation();
		this.paneHumanPlayer.clearInformation();
		this.paneComputerPlayer.clearInformation();
	}

	/**
	 * Updates the status game pane
	 */
	public void updateGameInfo() {
		this.paneGameInfo.update();
	}

	/**
	 * Enables or disables the player pane
	 * 
	 * @param isPaneDisabled
	 *            is the pane disabled
	 */
	public void showPlayerPane(boolean isPaneDisabled) {
		this.paneChooseFirstPlayer.setDisable(isPaneDisabled);
	}

	/**
	 * Enables or disables the computer pane
	 * 
	 * @param isPaneDisabled
	 *            is the pane disabled
	 */
	public void showComputerPane(boolean isPaneDisabled) {
		this.paneComputerPlayer.setDisable(isPaneDisabled);
	}

	/**
	 * Enables or disables the human pane
	 * 
	 * @param isPaneDisabled
	 *            is the pane disabled
	 */
	public void showHumanPane(boolean isPaneDisabled) {
		this.paneHumanPlayer.setDisable(isPaneDisabled);
	}
}