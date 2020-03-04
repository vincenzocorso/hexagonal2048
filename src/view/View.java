package hexagonal2048.view;

import hexagonal2048.util.*;
import java.util.List;
import java.awt.event.*;

public interface View {
	/**
	 * Draw a grid of the type passed to parameter with a size equal to size elements per side.
	 * All tiles are drawn empty.
	 * @param gridType the type of the grid to be initialised.
	 * @param size the size of grid. It represents the number of elements per side.
	 */
	public void initGrid(GridType gridType, int size);

	/**
	 * Performs move animations in the grid using updates informations passed by parameter.
	 * @param updates a list containing updates of tiles.
	 * @param onAnimationFinished will be called when all animations are finished.
	 */
	public void moveTiles(List<TileUpdate> updates, Runnable onAnimationsFinished);

	/**
	 * Update current score with a new value.
	 * @param score the updated current score.
	 */
	public void updateScore(int score);

	/**
	 * Update best score with a new value.
	 * @param bestScore the updated best score.
	 */
	public void updateBestScore(int bestScore);

	/**
	 * Add a new key binding.
	 * When the key corresponding to the keyCode is pressed, a certain action will be performed.
	 * @param commandName a unique string which identifies this command.
	 * @param key a string representing keys pressed.
	 * @param action action to be performed.
	 * @see javax.swing.KeyStroke#getKeyStroke(String s)
	 */
	public void addKeyBinding(String commandName, String key, ActionListener action);

	/**
	 * Show a win dialog.
	 */
	public void showWin();

	/**
	 * Show a lose dialog.
	 */
	public void showGameOver();

	/**
	 * Set the action that will be performed, if the user press "new game" button.
	 * @param action action to be performed.
	 */
	public void setNewGameButtonAction(ActionListener action);

	/**
	 * Set the action that will be performed, if user press "undo" button.
	 * @param action action to be performed.
	 */
	public void setUndoMoveButtonAction(ActionListener action);

	/**
	 * Set the action that will be performed, if the user press "exit" button.
	 * @param action action to be performed.
	 */
	public void setExitButtonAction(ActionListener action);

	/**
	 * Set the action that will be performed, if the user press "Hexagonal grid" button.
	 * @param action action to be performed.
	 */
	public void setHexagonalGridButtonAction(ActionListener action);

	/**
	 * Set the action that will be performed, if the user press "Classic grid" button.
	 * @param action action to be performed.
	 */
	public void setClassicGridButtonAction(ActionListener action);

	/**
	 * Set the action that will be performed, if the user press "Select grid size" button.
	 * @param action action to be performed.
	 */
	public void setGridSizeButtonAction(ActionListener action);

	/**
	 * Get the preferred grid size showing a input dialog.
	 * @return the preferred grid size.
	 */
	public int getGridSizePreference();
}