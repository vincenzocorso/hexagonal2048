package hexagonal2048.model;

import hexagonal2048.util.*;
import java.util.*;

public interface Model {
	/**
	 * Start a new game, resetting the current score to zero.
	 * Initializes the game grid of the type passed to parameter with a size equal to size elements per side.
	 * Finally two tiles with value 2 or 4 are added in distinct and random positions of the grid.
	 * @param gridType the type of the grid to be initialised.
	 * @param size the size of grid. It represents the number of elements per side.
	 * @return a list containing updates of tiles that were added.
	 * @see hexagonal2048.util.TileUpdate
	 */
	public List<TileUpdate> newGame(GridType gridType, int size);

	/**
	 * Performs a move in the grid in the direction passed by parameter.
	 * @param direction the move direction.
	 * @return a list containing updates of tiles.
	 * @throws IllegalMoveException if the direction is not valid.
	 * @throws GameFinishedException if the player has already lost and the games is finished.
	 */
	public List<TileUpdate> moveTiles(Direction direction);

	/**
	 * Returns the game to the state before the last move.
	 * @return a list containing informations about tiles' position after this undo move.
	 * @throws IllegalMoveException if undo can't be executed.
	 * @throws GameFinishedException if the player has already lost and the games is finished.
	 */
	public List<TileUpdate> undoMove();

	/**
	 * Check if the player has created a tile with a value high enough to win.
	 * @return true if the player has won. False otherwise.
	 */
	public boolean hasPlayerWon();

	/**
	 * Check if the player has no more moves available.
	 * @return true if the player has no more moves available. False otherwise.
	 */
	public boolean isGameFinished();

	/**
	 * Get the player's current score.
	 * @return the player's current score.
	 */
	public int getScore();

	/**
	 * Get the player's best score.
	 * @return the player's best score.
	 */
	public int getBestScore();
}