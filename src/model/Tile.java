package hexagonal2048.model;

import hexagonal2048.util.*;

public class Tile {
	private final Point position;
	private final int value;

	public Tile(Point position, int value) {
		this.position = position;
		this.value = value;
	}

	public Tile(Point position) {
		this(position, 0);
	}

	public Point getPosition() {
		return this.position;
	}

	public int getValue() {
		return this.value;
	}
}