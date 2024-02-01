package jtrash.controller;

import jtrash.model.cards.Card;
import jtrash.model.game.Game;
import jtrash.model.game.Observer;
import jtrash.view.GameView;
import jtrash.view.NewProfileView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class GameController implements Observer {
    private GameView view;
    private Game model;
    private Scanner scanner;

    public GameController(Game model, GameView view) {
        this.model = model;
        this.view = view;
        this.model.addObserver(this);
        
        
     // Add ActionListener to start button
        this.view.addStartButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // When start button is clicked, show new profile button and profile panel
                view.showNewProfileButton();
                view.showProfilePanel();
            }
        });

     // Add ActionListener to new profile button
        this.view.addNewProfileButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // When new profile button is clicked, show NewProfileView
                new NewProfileView();
            }
        });
        
       
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