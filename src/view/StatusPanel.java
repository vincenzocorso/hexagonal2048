package hexagonal2048.view;

import java.awt.*;
import javax.swing.*;

public class StatusPanel extends JPanel {
	private static final Color buttonBackgroundColor = new Color(245, 193, 125);
	private static final Color buttonTextColor = Color.WHITE;
	private static final Font buttonFont = new Font("Arial", Font.PLAIN, 30);

	private final LogoPanel logoPanel = new LogoPanel();
	private final JPanel scoreboardPanel = new JPanel();
	private final BoardPanel scorePanel = new BoardPanel("Score");
	private final BoardPanel bestScorePanel = new BoardPanel("Best");
	private final JButton newGameButton = new JButton();
	private final JButton undoButton = new JButton();

	public StatusPanel() {
		this.setLayout(new BorderLayout());
		this.scoreboardPanel.setBorder(BorderFactory.createEmptyBorder(10, 2, 10, 10));

		this.setupScoreboardPanel();

		this.add(logoPanel, BorderLayout.WEST);
		this.add(scoreboardPanel, BorderLayout.CENTER);
	}

	private void setupScoreboardPanel() {
		this.setupNewGameButton();
		this.setupUndoButton();
		this.scoreboardPanel.setLayout(new GridLayout(2, 2, 10, 10));
		this.scoreboardPanel.add(scorePanel);
		this.scoreboardPanel.add(bestScorePanel);
		this.scoreboardPanel.add(newGameButton);
		this.scoreboardPanel.add(undoButton);
	}

	private void setupNewGameButton() {
		this.newGameButton.setFocusPainted(false);
		this.newGameButton.setBorderPainted(false);
		this.newGameButton.setBackground(StatusPanel.buttonBackgroundColor);
		this.newGameButton.setForeground(StatusPanel.buttonTextColor);
		this.newGameButton.setFont(StatusPanel.buttonFont);
		this.newGameButton.setText("new game");
	}

	private void setupUndoButton() {
		this.undoButton.setFocusPainted(false);
		this.undoButton.setBorderPainted(false);
		this.undoButton.setBackground(StatusPanel.buttonBackgroundColor);
		this.undoButton.setForeground(StatusPanel.buttonTextColor);
		this.undoButton.setFont(StatusPanel.buttonFont);
		this.undoButton.setText("undo");
	}

	public void updateScore(String text) {
		this.scorePanel.setValueText(text);
	}

	public void updateBestScore(String text) {
		this.bestScorePanel.setValueText(text);
	}

	public JButton getNewGameButton() {
		return this.newGameButton;
	}

	public JButton getUndoButton() {
		return this.undoButton;
	}
}