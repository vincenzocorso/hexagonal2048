package hexagonal2048.view;

import javax.swing.*;

public class GameMenu extends JMenuBar {
	private final JMenu gameMenu = new JMenu("Game");
	private final JMenu settingsMenu = new JMenu("Settings");

	private final JMenuItem newGameMenuItem = new JMenuItem("New game");
	private final JMenuItem undoMoveMenuItem = new JMenuItem("Undo move");
	private final JMenuItem creditsMenuItem = new JMenuItem("Credits");
	private final JMenuItem exitMenuItem = new JMenuItem("Exit");

	private final JMenu gridMenu = new JMenu("Select grid");
	private final JMenuItem hexagonalGridMenuItem = new JMenuItem("Hexagonal grid");
	private final JMenuItem classicGridMenuItem = new JMenuItem("Classic grid");
	private final JMenuItem gridSizeMenuItem = new JMenuItem("Select grid size");

	public GameMenu() {
		this.setupGameMenu();
		this.setupSettingsMenu();
	}

	private void setupGameMenu() {
		this.gameMenu.add(this.newGameMenuItem);
		this.gameMenu.add(this.undoMoveMenuItem);
		this.creditsMenuItem.addActionListener((e) -> JOptionPane.showMessageDialog(this.getParent(), "Hexagonal 2048 by Vincenzo Corso.\nCheck also github.com/vincenzocorso"));
		this.gameMenu.add(creditsMenuItem);
		this.gameMenu.add(this.exitMenuItem);

		this.add(this.gameMenu);
	}

	private void setupSettingsMenu() {
		this.gridMenu.add(this.hexagonalGridMenuItem);
		this.gridMenu.add(this.classicGridMenuItem);
		
		this.settingsMenu.add(gridMenu);
		this.settingsMenu.add(gridSizeMenuItem);
		
		this.add(this.settingsMenu);
	}

	public int showGridSizeDialog() {
		String input = JOptionPane.showInputDialog(this.getParent(), "Insert the grid dimension (between 3 and 5)");
		return Integer.parseInt(input);
	}

	public JMenuItem getNewGameMenuItem() {
		return this.newGameMenuItem;
	}

	public JMenuItem getUndoMoveMenuItem() {
		return this.undoMoveMenuItem;
	}

	public JMenuItem getExitMenuItem() {
		return this.exitMenuItem;
	}

	public JMenuItem getHexagonalGridMenuItem() {
		return this.hexagonalGridMenuItem;
	}
	
	public JMenuItem getClassicGridMenuItem() {
		return this.classicGridMenuItem;
	}

	public JMenuItem getGridSizeMenuItem() {
		return this.gridSizeMenuItem;
	}
}