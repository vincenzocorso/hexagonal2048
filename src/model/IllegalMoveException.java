package hexagonal2048.model;

public class IllegalMoveException extends RuntimeException {
	public IllegalMoveException() {
		super();
	}

	public IllegalMoveException(String message) {
		super(message);
	}
}