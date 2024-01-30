package jtrash.controller;

import jtrash.model.cards.Card;
import jtrash.model.game.Game;
import jtrash.model.game.Observer;
import jtrash.view.GameView;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class GameController implements Observer {
    private GameView view;
    private Game model;
    private Scanner scanner;

    public GameController(Game model) {
//        this.view = view;
        this.model = model;
        this.scanner = new Scanner(System.in);
        this.model.addObserver(this);
    }

    public void collectGameSetup() {
    	System.out.println("Enter your nickname please :");
        String username = scanner.nextLine();
    	System.out.println("Welcome " + username + "! " +"How many players to you want to play with? ");
    	int numPlayers = scanner.nextInt();
    	scanner.nextLine();
//        view.displayWelcomeMessage();
//        view.displayNumberOfPlayers();
        model.initialzeGame(numPlayers, username);
//        view.displayGameOverMessage();
    }

	/**
	 * 
	 * @param hand
	 * @return
	 */
	private int getWildcardPlament(ArrayList<Card> hand) {
		System.out.print("Where do you want to put the wild card? ");
	    int wildcardPlace = -1;
	    
	    while (true) {
	    	try {
	    		String str = scanner.next();
	    		wildcardPlace = Integer.parseInt(str)-1;
		    	
		        if (wildcardPlace < 0 || wildcardPlace >= hand.size() || hand.get(wildcardPlace).isFaceUp()) {
		            System.out.println("Input not valid. Give the position of covered card:");
		        } else break; // input is valid so exit the loop
		    } catch (NumberFormatException | InputMismatchException e) {
		    	System.out.println("Input not valid. Give a valid int.");
		    	scanner.nextLine();
		    }
	    }
	    return wildcardPlace;
	}

	@Override
	public int onWildcardDrawn(ArrayList<Card> hand) {
		int wildcardPlace = getWildcardPlament(hand);
		return wildcardPlace;
	}

	@Override
	public void onScoreUpdate(int gamesWon, int gamesLost) {
		// TODO Auto-generated method stub
		
	}
	
	  public void closeScanner() {
	        scanner.close();
	    }
}