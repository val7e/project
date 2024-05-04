package main;


import jtrash.controller.GameController;
import jtrash.model.game.Game;
import jtrash.view.GameView;


public class JTrash {
	
	
    public static void main(String[] args) {
    	
        // Create instances of game model, view, and controller
    	Game model = new Game();
    	GameView view = new GameView();
		GameController controller = new GameController(model, view);
		
        
        view.setVisible(true);
    }
    
    
}
