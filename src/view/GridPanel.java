package hexagonal2048.view;

import hexagonal2048.util.*;
import hexagonal2048.util.Point;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.function.Predicate;
import java.awt.*;
import javax.swing.*;
import javax.swing.Timer;

public abstract class GridPanel extends JPanel {
	protected final int size;
	protected final int tileSideLength;
	protected Tile[][] gridTiles;
	protected Tile[][] gameTiles;
	protected boolean[][] animatingTiles;

	public GridPanel(int size, int tileSideLength) {
		this.size = size;
		this.tileSideLength = tileSideLength;
	}

	public abstract Dimension getPreferredSize();

	public void moveTiles(List<TileUpdate> updates, Runnable onAnimationsFinished) {
		List<TileUpdate> movements = this.filterUpdates(updates, u -> u instanceof MovementTileUpdate);
		List<TileUpdate> additions = this.filterUpdates(updates, u -> !(u instanceof MovementTileUpdate));

		MovingTileAnimation[] animations = prepareAnimations(movements);
		ParallelTransition transition = new ParallelTransition(animations);
		transition.invokeWhenFinished(() -> {
			handleAdditionUpdates(additions);
			onAnimationsFinished.run();
		});
		transition.start();
		
		this.repaint();
	}

	private static List<TileUpdate> filterUpdates(List<TileUpdate> updates, Predicate<TileUpdate> predicate) {
		return updates.stream().filter(predicate).collect(Collectors.toCollection(ArrayList::new));
	}

	// TODO: scaling animation
	private void handleAdditionUpdates(List<TileUpdate> additions) {
		for(TileUpdate update : additions) {
			int x = update.getCurrentPosition().getX();
			int y = update.getCurrentPosition().getY();
			if(update.getCurrentValue() == 0)
				this.gameTiles[y][x] = null;
			else
				this.gameTiles[y][x] = TileFactory.createTile(this, this.tileSideLength, update.getCurrentValue());
		}
	}

	// TODO: refactor
	private MovingTileAnimation[] prepareAnimations(List<TileUpdate> movements) {
		MovingTileAnimation[] animations = new MovingTileAnimation[movements.size()];

		for(int i = 0; i < movements.size(); i++) {
			MovementTileUpdate movementUpdate = (MovementTileUpdate)movements.get(i);
			int startX = movementUpdate.getCurrentPosition().getX();
			int startY = movementUpdate.getCurrentPosition().getY();
			int finalX = movementUpdate.getFinalPosition().getX();
			int finalY = movementUpdate.getFinalPosition().getY();
			Tile source = this.gameTiles[startY][startX];
			Tile target = this.gridTiles[finalY][finalX];

			this.animatingTiles[startY][startX] = true;

			MovingTileAnimation animation = new MovingTileAnimation(source, target, 15);
			animation.invokeWhenFinished(() -> {
				this.gameTiles[finalY][finalX] = this.gameTiles[startY][startX];
				this.gameTiles[startY][startX] = null;

				if(movementUpdate instanceof MergingTileUpdate) {
					MergingTileUpdate mergingUpdate = (MergingTileUpdate)movementUpdate;
					this.gameTiles[finalY][finalX].setupValue(mergingUpdate.getNewValue());
				}

				this.animatingTiles[startY][startX] = false;
			});
			animations[i] = animation;
		}

		return animations;
	}

	class MovingTileAnimation extends Animation {
		private final Tile source;
		private final Tile target;
		private final int speed;

		private Timer timer;

		public MovingTileAnimation(Tile source, Tile target, int speed) {
			this.source = source;
			this.target = target;
			this.speed = speed;
		}

		@Override
		public Animation start() {
			this.timer = new Timer(10, (e) -> this.animate());
			timer.start();
			return this;
		}

		private void end() {
			this.timer.stop();
			for(Runnable runnable : this.runnables)
				runnable.run();
		}

		@Override
		public void invokeWhenFinished(Runnable runnable) {
			if(runnable != null)
				this.runnables.add(runnable);
		}

		private void animate() {
			Point movementVector = this.getMovementVector();
			Point distanceVector = this.source.getDistanceFromTile(this.target);
			this.source.translate(movementVector.getX(), movementVector.getY());
			repaint();
			if(distanceVector.getX() * distanceVector.getX() + distanceVector.getY() * distanceVector.getY() <= 0)
				this.end();
		}

		// TODO: refactor
		private Point getMovementVector() {
			Rectangle sourceBounds = this.source.getBounds();
			Rectangle targetBounds = this.target.getBounds();
			Point distanceVector = this.source.getDistanceFromTile(this.target);

			double gradient = (targetBounds.getCenterY() - sourceBounds.getCenterY()) / (targetBounds.getCenterX() - sourceBounds.getCenterX());
			int dx, dy;
			if(Double.isInfinite(gradient)) {
				dx = 0;
				dy = (distanceVector.getY() >= 0) ? this.speed : -this.speed;
			}
			else {
				dx = (distanceVector.getX() >= 0) ? this.speed : -this.speed;
				dy = (int)(gradient * dx);
			}

			if(Math.abs(distanceVector.getX()) <= Math.abs(dx))
				dx = (int)distanceVector.getX();
			
			if(Math.abs(distanceVector.getY()) <= Math.abs(dy))
				dy = (int)distanceVector.getY();

			return new Point(dx, dy);
		}
	}
}