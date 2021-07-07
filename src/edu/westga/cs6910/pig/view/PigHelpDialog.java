package edu.westga.cs6910.pig.view;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

/**
 * Defines the class that manages the pig help dialog instructions
 * 
 * @author Kim Weible
 * @version Summer 2021
 * 
 */
public class PigHelpDialog {
	private boolean shouldShowHelpDialog;

	/**
	 * Creates a new PigHelpDialog that sets the initial show of the game
	 * instructions at the beginning of the game
	 */
	public PigHelpDialog() {
		this.shouldShowHelpDialog = true;
	}

	/**
	 * Getter for the shouldShowHelpDialog instance variable
	 * 
	 * @return shouldShowHelpDialog determines whether the help dialog should
	 *         show as true/false
	 */
	public boolean getShouldShowHelpDialog() {
		return this.shouldShowHelpDialog;
	}

	/**
	 * Setter for the shouldShowHelpDialog instance variable
	 * 
	 * @param shouldShowHelpDialog
	 *            determines whether the help dialog should show as true/false
	 */
	public void setShouldShowHelpDialog(boolean shouldShowHelpDialog) {
		this.shouldShowHelpDialog = shouldShowHelpDialog;
	}

	/**
	 * Determines if the Help Dialog should show or not, based on the explicit
	 * button selection
	 * 
	 * @return boolean should the help dialog show on new game
	 */
	public boolean showHelpDialog() {
		if (!this.getShouldShowHelpDialog()) {
			this.setShouldShowHelpDialog(false);
			return false;
		}

		Alert message = new Alert(AlertType.CONFIRMATION);
		message.setTitle("CS6910 - Better Pig");

		String helpMessage = "Pig rules: \n  Play against the computer.\n"
				+ "  Alternate taking turns, rolling the dice.\n"
				+ "  If the player does not roll a 1 on either die, the points are added for the turn.\n"
				+ "  If the player chooses to hold, their turn total "
				+ "  is added to their score and the turn is over.\n"
				+ "  You may set the goal score at the start of each game and\n    switch what "
				+ "strategy the computer uses at any time.";

		message.setHeaderText(helpMessage);
		message.setContentText(
				"Would you like to see this dialog at the start of the next game?");

		ButtonType buttonYes = new ButtonType("Yes");
		ButtonType buttonNo = new ButtonType("No");
		message.getButtonTypes().setAll(buttonYes, buttonNo);

		Optional<ButtonType> result = message.showAndWait();

		if (result.get() == buttonYes) {
			this.setShouldShowHelpDialog(true);
		} else {
			this.setShouldShowHelpDialog(false);
		}
		return this.getShouldShowHelpDialog();
	}
}