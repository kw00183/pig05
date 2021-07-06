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
	private BorderPane pnContent;

	private HumanPane pnHumanPlayer;
	private ComputerPane pnComputerPlayer;
	private StatusPane pnGameInfo;
	private NewGamePane pnChooseFirstPlayer;
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
		this.pnContent = new BorderPane();

		this.menuBar = new PigMenuBar(this.theGame, this, this.helpDialog);
		VBox menuBox = this.menuBar.createMenu();

		this.pnChooseFirstPlayer = new NewGamePane(this.theGame, this);
		HBox playerBox = this.createHBoxHolder(this.pnChooseFirstPlayer, false);

		VBox topBox = new VBox();
		topBox.getChildren().addAll(menuBox, playerBox);
		this.pnContent.setTop(topBox);

		this.pnHumanPlayer = new HumanPane(this.theGame);
		HBox leftBox = this.createHBoxHolder(this.pnHumanPlayer, true);
		this.pnContent.setLeft(leftBox);

		this.pnComputerPlayer = new ComputerPane(this.theGame);
		HBox centerBox = this.createHBoxHolder(this.pnComputerPlayer, true);
		this.pnContent.setCenter(centerBox);

		this.pnGameInfo = new StatusPane(this.theGame);
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
		this.pnChooseFirstPlayer.reset();
		this.pnChooseFirstPlayer.setDisable(false);
		this.pnHumanPlayer.setDisable(true);
		this.pnComputerPlayer.setDisable(true);

		if (this.helpDialog.getShouldShowHelpDialog()) {
			this.helpDialog.showHelpDialog();
		}

		this.theGame.resetGame();
		this.pnGameInfo.clearInformation();
		this.pnHumanPlayer.clearInformation();
		this.pnComputerPlayer.clearInformation();
	}

	/**
	 * Updates the status game pane
	 */
	public void updateGameInfo() {
		this.pnGameInfo.update();
	}

	/**
	 * Enables or disables the player pane
	 * 
	 * @param isDisabled
	 *            is the pane disabled
	 */
	public void showPlayerPane(boolean isDisabled) {
		this.pnChooseFirstPlayer.setDisable(isDisabled);
	}

	/**
	 * Enables or disables the computer pane
	 * 
	 * @param isDisabled
	 *            is the pane disabled
	 */
	public void showComputerPane(boolean isDisabled) {
		this.pnComputerPlayer.setDisable(isDisabled);
	}

	/**
	 * Enables or disables the human pane
	 * 
	 * @param isDisabled
	 *            is the pane disabled
	 */
	public void showHumanPane(boolean isDisabled) {
		this.pnHumanPlayer.setDisable(isDisabled);
	}
}