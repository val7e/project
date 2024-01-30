package main;

import jtrash.controller.GameController;
import jtrash.model.game.Game;

public class Main {
    public static void main(String[] args) {
        // Create the game model, view, and controller
    	Game model = new Game();
        GameController controller = new GameController(model);
        // Create the view if needed
        
        // Start the game
        controller.collectGameSetup(); // Collect initial setup information
//        controller.performGameActions(); // Perform game actions
        
        // Close any resources if needed
        controller.closeScanner(); // Close the scanner used for input
    }
}
