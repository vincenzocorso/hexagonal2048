package hexagonal2048.view;

import hexagonal2048.util.GridType;

public abstract class GridPanelFactory {
	private GridPanelFactory() {}

	public static GridPanel createGridPanel(GridType gridType, int size) {
		switch(gridType) {
			case CLASSIC: return new ClassicGridPanel(size, 100);
			case HEXAGONAL: return new HexagonalGridPanel(size, 50);
			default: throw new IllegalArgumentException("Invalid grid type");
		}
	}
}