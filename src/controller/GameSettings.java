package hexagonal2048.controller;

import hexagonal2048.util.*;

public class GameSettings {
	private GridType gridType;
	private int gridSize;

	public GameSettings() {
		this.setGridType(GridType.HEXAGONAL);
		this.setGridSize(3);
	}

	public GridType getGridType() {
		return this.gridType;
	}

	public void setGridType(GridType gridType) {
		this.gridType = gridType;
	}

	public int getGridSize() {
		return this.gridSize;
	}

	public void setGridSize(int gridSize) {
		if(gridSize <= 2 || gridSize >= 6)
			throw new IllegalArgumentException("Invalid grid size");

		this.gridSize = gridSize;
	}
}