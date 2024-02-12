package main;

import jtrash.controller.GameController;
import jtrash.model.game.Game;
import jtrash.view.GameView;
import jtrash.view.MenuFrame;

public class JTrash {
	Game model;
	GameController controller;
	GameView view;
	
    public static void main(String[] args) {
    	
        // Create instances of game model, view, and controller
    	Game model = new Game();
    	GameView view = new GameView();
        GameController controller = new GameController(model, view);   
//        MenuFrame menu = view.getMenuFrame();
        
//        menu.setNewProfileListener(controller);
        // Start the game
//        controller.collectGameSetup(); // Collect initial setup information

        // Close any resources if needed
//        controller.closeScanner(); // Close the scanner used for input
    }
    
    
}
