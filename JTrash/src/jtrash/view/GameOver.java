package jtrash.view;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JWindow;

public class GameOver extends JWindow {
	private JWindow over; // window for gameover gif
	private AudioManager audioWin;
	private AudioManager audioLost;

	
	public GameOver(boolean isBot) {
		if (isBot) {
			displayGameOverMessage();
		} else displayWinMessage();
	}
	public void displayWinMessage() {
		System.out.println("You won! :)");
    	over = new JWindow();
        over.setSize(480,400);
        over.setLocationRelativeTo(null);
        ImageIcon img = new ImageIcon("graphics/win1.gif");
        JLabel gameOver = new JLabel();
        gameOver.setIcon(img);
        audioWin = AudioManager.getInstance();
        audioWin.play("audioTracks/audioWin.wav");
        over.getContentPane().add(gameOver);
        over.setVisible(true);
    }
    
    public void displayGameOverMessage() {
    	System.out.println("You lose! :(");
    	over = new JWindow();
        over.setSize(480,400);
        over.setLocationRelativeTo(null);
        ImageIcon img = new ImageIcon("graphics/lose1.gif");
        JLabel gameOver = new JLabel();
        gameOver.setIcon(img);
        audioLost = AudioManager.getInstance();
        audioLost.play("audioTracks/audioLost.wav");
        over.getContentPane().add(gameOver);
        over.setVisible(true);
    }
}
