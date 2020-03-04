package hexagonal2048.controller;

import hexagonal2048.util.*;
import hexagonal2048.model.*;
import hexagonal2048.view.*;
import java.util.*;

public class Controller {
	private Model model;
	private View view;
	
	private GameSettings gameSettings;

	private boolean isMoving;
	private boolean winShowed;

	public Controller(Model model, View view) {
		this.model = model;
		this.view = view;
		this.gameSettings = new GameSettings();
		this.initController();
	}

	private void initController() {
		this.initGUIActions();
		this.setupKeyBinding();
		this.newGame();
	}

	private void initGUIActions() {
		this.view.setNewGameButtonAction((e) -> this.newGame());
		this.view.setUndoMoveButtonAction((e) -> this.tryUndo());
		this.view.setExitButtonAction((e) -> System.exit(0));
		this.view.setHexagonalGridButtonAction((e) -> this.gameSettings.setGridType(GridType.HEXAGONAL));
		this.view.setClassicGridButtonAction((e) -> this.gameSettings.setGridType(GridType.CLASSIC));
		this.view.setGridSizeButtonAction((e) -> setGridSizeSetting());
	}

	private void setGridSizeSetting() {
		try {
			int sizePreference = this.view.getGridSizePreference();
			this.gameSettings.setGridSize(sizePreference);
		}
		catch(IllegalArgumentException ex) {
			System.out.println("Invalid grid size");
		}
	}

	private void setupKeyBinding() {
		this.view.addKeyBinding("moveUpperLeft", "Q", (e) -> this.tryMove(Direction.UPPER_LEFT));
		this.view.addKeyBinding("moveUp", "W", (e) -> this.tryMove(Direction.UP));
		this.view.addKeyBinding("moveUpperRight", "E", (e) -> this.tryMove(Direction.UPPER_RIGHT));
		this.view.addKeyBinding("moveLeft", "A", (e) -> this.tryMove(Direction.LEFT));
		this.view.addKeyBinding("moveRight", "D", (e) -> this.tryMove(Direction.RIGHT));
		this.view.addKeyBinding("moveLowerLeft", "Z", (e) -> this.tryMove(Direction.LOWER_LEFT));
		this.view.addKeyBinding("moveDown", "X", (e) -> this.tryMove(Direction.DOWN));
		this.view.addKeyBinding("moveLowerRight", "C", (e) -> this.tryMove(Direction.LOWER_RIGHT));
	}

	private void newGame() {
		GridType gridTypeSetting = this.gameSettings.getGridType();
		int gridSize = this.gameSettings.getGridSize();
		List<TileUpdate> updates = this.model.newGame(gridTypeSetting, gridSize);
		this.view.initGrid(gridTypeSetting, gridSize);
		this.isMoving = true;
		this.view.moveTiles(updates, () -> this.isMoving = false);
		this.updateScoreboard();
		this.winShowed = false;
	}

	private void tryMove(Direction direction) {
		try {
			this.makeMove(direction);
		}
		catch(IllegalMoveException ex) {
			System.out.println("Mossa non valida.");
		}
	}

	private void makeMove(Direction direction) {
		if(this.isMoving || this.model.isGameFinished())
			return;

		List<TileUpdate> updates = this.model.moveTiles(direction);
		this.isMoving = true;
		this.view.moveTiles(updates, () -> this.isMoving = false);
		this.updateScoreboard();

		if(!this.winShowed && this.model.hasPlayerWon()) {
			this.winShowed = true;
			this.view.showWin();
		}
	
		if(this.model.isGameFinished())
			this.view.showGameOver();
	}

	private void tryUndo() {
		try {
			this.makeUndo();
		}
		catch(IllegalMoveException ex) {
			System.out.println("Mossa non valida.");
		}
	}
	
	private void makeUndo() {
		if(this.isMoving || this.model.isGameFinished())
			return;

		List<TileUpdate> updates = this.model.undoMove();
		this.isMoving = true;
		this.view.moveTiles(updates, () -> this.isMoving = false);
		this.updateScoreboard();
	}

	private void updateScoreboard() {
		this.view.updateScore(this.model.getScore());
		this.view.updateBestScore(this.model.getBestScore());
	}
}