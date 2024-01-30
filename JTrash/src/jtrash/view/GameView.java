package jtrash.view;

import javax.swing.JFrame;

import jtrash.model.players.Player;

public class GameView {
    private final GameFrame gameFrame;

    public GameView() {
        gameFrame = new GameFrame();
    }

    public void display() {
        gameFrame.setVisible(true);
    }

    public JFrame getFrame() {
        return gameFrame.getFrame();
    }
    
    public void displayWelcomeMessage() {
        System.out.println("Welcome to the Card Game!");
    }

    public void displayNumberOfPlayers() {
        System.out.println("Please enter the number of players: ");
    }

    public void displayGameOverMessage() {
        System.out.println("Congratulations! The game is over!");
    }

    public void displayPlayerHand(Player player) {
        System.out.println(player);
    }
    
    
}