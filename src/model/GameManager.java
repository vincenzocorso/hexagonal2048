package hexagonal2048.model;

import hexagonal2048.util.*;
import java.util.*;

public class GameManager implements Model {
	private Grid gameGrid;
	private int currentScore;
	private int bestScore;
	private GameState previousGameState;

	private boolean hasPlayerWon;
	private boolean isGameFinished;

	@Override
	public List<TileUpdate> newGame(GridType gridType, int size) {
		this.gameGrid = GridFactory.createGrid(gridType, size);
		this.currentScore = 0;
		this.previousGameState = null;
		this.isGameFinished = false;
		this.hasPlayerWon = false;
		return this.gameGrid.getTiles();
	}

	@Override
	public List<TileUpdate> moveTiles(Direction direction) {
		if(this.isGameFinished())
			throw new GameFinishedException("Game is finished. Start a new game!");

		GameState gameState = this.getCurrentGameState();
		List<TileUpdate> updates = this.gameGrid.moveTiles(direction);

		if(!updates.isEmpty())
			this.previousGameState = gameState;

		this.updateScore(updates);
		this.updateBestScore();

		if(!this.hasPlayerWon() && this.gameGrid.hasWinTile())
			this.hasPlayerWon = true;
		
		if(!this.gameGrid.canMove())
			this.isGameFinished = true;

		return updates;
	}

	private void updateScore(List<TileUpdate> updates) {
		for(TileUpdate update : updates) {
			if(update instanceof MergingTileUpdate) {
				MergingTileUpdate mergingTileUpdate = (MergingTileUpdate)update;
				this.currentScore += mergingTileUpdate.getNewValue();
			}
		}
	}

	private void updateBestScore() {
		if(this.currentScore > this.bestScore)
			this.bestScore = this.currentScore;
	}

	@Override
	public List<TileUpdate> undoMove() {
		if(this.isGameFinished())
			throw new GameFinishedException("Game is finished. Start a new game!");

		if(this.previousGameState == null)
			throw new IllegalMoveException("Can't undo now.");

		this.loadGameState(this.previousGameState);
		this.previousGameState = null;

		return this.gameGrid.getTiles();
	}

	private GameState getCurrentGameState() {
		try {
			return new GameState(this.gameGrid, this.currentScore, this.bestScore);
		}
		catch(CloneNotSupportedException ex) {
			throw new RuntimeException("Can't create a copy of current game state.");
		}
	}

	private void loadGameState(GameState gameState) {
		this.gameGrid = gameState.getGrid();
		this.currentScore = gameState.getScore();
		this.bestScore = gameState.getBestScore();
	}

	@Override
	public boolean hasPlayerWon() {
		return this.hasPlayerWon;
	}

	@Override
	public boolean isGameFinished() {
		return this.isGameFinished;
	}

	@Override
	public int getScore() {
		return this.currentScore;
	}

	@Override
	public int getBestScore() {
		return this.bestScore;
	}
}