package hexagonal2048.view;

import hexagonal2048.util.Point;
import java.awt.*;
import javax.swing.*;

public class HexagonalGridPanel extends GridPanel {
	public HexagonalGridPanel(int size, int tileSideLength) {
		super(size, tileSideLength);
		int gridSize = this.size * 2 - 1;
		this.gridTiles = new Tile[gridSize][gridSize];
		this.gameTiles = new Tile[gridSize][gridSize];
		this.animatingTiles = new boolean[gridSize][gridSize];
		this.initGrid();
	}

	private void initGrid() {
		int spacing = 9;
		int side = this.tileSideLength + spacing;

		Point startPoint = Point.ORIGIN;
		for(int y = 0; y < this.gridTiles.length; y++) {
			Point currentPoint = startPoint.getLocation();
			for(int x = 0; x < this.gridTiles.length; x++) {
				if(y > x - this.size && y < x + this.size) {
					this.gridTiles[y][x] = new HexagonalTile(currentPoint, this.tileSideLength);
					currentPoint = currentPoint.getTranslatedPoint((int)(1.5 * side), (int)(-side * Math.sqrt(3) / 2));
				}
			}

			if(y < this.size - 1)
				startPoint = startPoint.getTranslatedPoint(0, (int)(side * Math.sqrt(3)));
			else
				startPoint = startPoint.getTranslatedPoint((int)(1.5 * side), (int)(side * Math.sqrt(3) / 2));
		}
	}

	@Override
	public Dimension getPreferredSize() {
		int minX = this.getGridMinX();
		int maxX = this.getGridMaxX();
		int minY = this.getGridMinY();
		int maxY = this.getGridMaxY();
		return new Dimension(maxX + Math.abs(minX) + 50, maxY + Math.abs(minY) + 50);
	}

	private int getGridMinX() {
		Tile leftmostTile = this.gridTiles[0][0];
		return (leftmostTile == null) ? 0 : (int)leftmostTile.getBounds().getMinX();
	}

	private int getGridMaxX() {
		int gridSize = this.gridTiles.length;
		Tile rightmostTile = this.gridTiles[gridSize - 1][gridSize - 1];
		return (rightmostTile == null) ? 0 : (int)rightmostTile.getBounds().getMaxX();
	}

	private int getGridMinY() {
		Tile uppermostTile = this.gridTiles[0][this.size - 1];
		return (uppermostTile == null) ? 0 : (int)uppermostTile.getBounds().getMinY();
	}

	private int getGridMaxY() {
		int gridSize = this.gridTiles.length;
		Tile lowermostTile = this.gridTiles[gridSize - 1][this.size - 1];
		return (lowermostTile == null) ? 0: (int)lowermostTile.getBounds().getMaxY();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;

		this.setupGridAtCenter();
		for(int y = 0; y < this.gridTiles.length; y++)
			for(int x = 0; x < this.gridTiles.length; x++)
				if(this.gridTiles[y][x] != null)
					this.gridTiles[y][x].draw(g2d);

		this.alignTilesToGrid();
		for(int y = 0; y < this.gameTiles.length; y++)
			for(int x = 0; x < this.gameTiles.length; x++)
				if(this.gameTiles[y][x] != null)
					this.gameTiles[y][x].draw(g2d);
	}

	private void setupGridAtCenter() {
		Point distanceVector = this.getGridDistanceFromCenter();
		for(int y = 0; y < this.gridTiles.length; y++)
			for(int x = 0; x < this.gridTiles.length; x++)
				if(this.gridTiles[y][x] != null)
					this.gridTiles[y][x].translate(distanceVector.getX(), distanceVector.getY());
	}

	private Point getGridDistanceFromCenter() {
		Tile centerGridTile = this.getCenterGridTile();
		double dx = this.getWidth() / 2 - centerGridTile.getBounds().getCenterX();
		double dy = this.getHeight() / 2 - centerGridTile.getBounds().getCenterY();
		return new Point((int)dx, (int)dy);
	}

	private Tile getCenterGridTile() {
		return this.gridTiles[this.size - 1][this.size - 1];
	}

	private void alignTilesToGrid() {
		for(int y = 0; y < this.gameTiles.length; y++) {
			for(int x = 0; x < this.gameTiles.length; x++) {
				if(this.gameTiles[y][x] != null && !this.animatingTiles[y][x]) {
					Point distanceVector = this.gameTiles[y][x].getDistanceFromTile(this.gridTiles[y][x]);
					this.gameTiles[y][x].translate(distanceVector.getX(), distanceVector.getY());
				}
			}
		}
	}
}