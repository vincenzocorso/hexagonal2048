package hexagonal2048.view;

import hexagonal2048.util.*;
import hexagonal2048.util.Point;
import java.util.*;
import java.util.List;
import java.util.Timer;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ViewManager extends JFrame implements View{
	private final GameMenu gameMenu = new GameMenu();
	private final StatusPanel statusPanel = new StatusPanel();
	private GridPanel gridPanel;

	public ViewManager(String windowTitle) {
		this.getContentPane().add(statusPanel, BorderLayout.NORTH);
		this.setupFrame(windowTitle);
	}

	private void setupFrame(String windowTitle) {
		this.setTitle(windowTitle);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int)((screenSize.getWidth() - this.getWidth()) / 2);
		this.setLocation(x, 150);	
		this.setJMenuBar(this.gameMenu);
		this.setVisible(true);
	}

	@Override
	public void initGrid(GridType gridType, int size) {
		if(this.gridPanel != null)
			this.getContentPane().remove(this.gridPanel);

		this.gridPanel = GridPanelFactory.createGridPanel(gridType, size);
		this.getContentPane().add(gridPanel, BorderLayout.CENTER);
		this.pack();
	}

	@Override
	public void moveTiles(List<TileUpdate> updates, Runnable onAnimationsFinished) {
		this.gridPanel.moveTiles(updates, onAnimationsFinished);
	}

	@Override
	public void updateScore(int score) {
		this.statusPanel.updateScore("" + score);
	}

	@Override
	public void updateBestScore(int bestScore) {
		this.statusPanel.updateBestScore("" + bestScore);
	}

	@Override
	public void addKeyBinding(String commandName, String key, ActionListener action) {
		JPanel contentPane = (JPanel)this.getContentPane();
		contentPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(key), commandName);
		contentPane.getActionMap().put(commandName, new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e) {
				action.actionPerformed(e);
			}
		});
	}

	@Override
	public void showWin() {
		this.showMessagePanel(new Color(252, 186, 3, 144), "You win!");
	}

	@Override
	public void showGameOver() {
		this.showMessagePanel(new Color(95, 95, 95, 144), "Game over!");
	}

	private void showMessagePanel(Color color, String text) {
		JPanel glassPane = new MessagePanel(Point.ORIGIN, this.getSize(), color, text);
		this.setGlassPane(glassPane);
		this.getGlassPane().setVisible(true);

		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				getGlassPane().setVisible(false);
				setGlassPane(new JPanel());
			}
		};
		timer.schedule(task, 2000);
	}

	@Override
	public void setNewGameButtonAction(ActionListener action) {
		this.statusPanel.getNewGameButton().addActionListener(action);
		this.gameMenu.getNewGameMenuItem().addActionListener(action);
	}

	@Override
	public void setUndoMoveButtonAction(ActionListener action) {
		this.statusPanel.getUndoButton().addActionListener(action);
		this.gameMenu.getUndoMoveMenuItem().addActionListener(action);
	}

	@Override
	public void setExitButtonAction(ActionListener action) {
		this.gameMenu.getExitMenuItem().addActionListener(action);
	}

	@Override
	public void setHexagonalGridButtonAction(ActionListener action) {
		this.gameMenu.getHexagonalGridMenuItem().addActionListener(action);
	}

	@Override
	public void setClassicGridButtonAction(ActionListener action) {
		this.gameMenu.getClassicGridMenuItem().addActionListener(action);
	}

	@Override
	public void setGridSizeButtonAction(ActionListener action) {
		this.gameMenu.getGridSizeMenuItem().addActionListener(action);
	}

	@Override
	public int getGridSizePreference() {
		return this.gameMenu.showGridSizeDialog();
	}
}