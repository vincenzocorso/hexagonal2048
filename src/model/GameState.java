package hexagonal2048.model;

public class GameState {
	private final Grid grid;
	private final int currentScore;
	private final int bestScore;

	public GameState(Grid grid, int currentScore, int bestScore) throws CloneNotSupportedException {
		this.grid = (Grid)grid.clone();
		this.currentScore = currentScore;
		this.bestScore = bestScore;
	}

	public Grid getGrid() {
		return this.grid;
	}

	public int getScore() {
		return this.currentScore;
	}

	public int getBestScore() {
		return this.bestScore;
	}
}