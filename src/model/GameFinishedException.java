package hexagonal2048.model;

public class GameFinishedException extends RuntimeException {
	public GameFinishedException() {
		super();
	}

	public GameFinishedException(String message) {
		super(message);
	}
}