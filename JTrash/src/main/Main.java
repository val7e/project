package main;

import javax.swing.SwingUtilities;

import jtrash.controller.GameController;
import jtrash.model.game.Game;
import jtrash.view.GameView;

public class Main {
    public static void main(String[] args) {
        // Create instances of game model, view, and controller
    	Game model = new Game();
    	GameView view = new GameView();
    	
        GameController controller = new GameController(model, view);
        
        SwingUtilities.invokeLater(() -> view.setVisible(true));
        // Start the game
//        controller.collectGameSetup(); // Collect initial setup information

        // Close any resources if needed
        controller.closeScanner(); // Close the scanner used for input
    }
}
