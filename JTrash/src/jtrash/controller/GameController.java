package jtrash.controller;

import jtrash.model.cards.Card;
import jtrash.model.game.Game;
import jtrash.model.game.Observer;
import jtrash.model.players.Player;
import jtrash.view.GameView;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;



public class GameController implements Observer {
    private GameView view;
    private Game model;
    private Scanner scanner;

    public GameController(Game model, GameView view) {
        this.model = model;
        this.view = view;
        this.model.addObserver(this);
        
        
        // Add ActionListener to start button
        this.view.addStartButtonListener(new StartListener());
        
        // Add ActionListener to newProfile button
        this.view.addProfileListener(new ProfileListener());
        
        // Add ActionListener to new game buttons
        this.view.addGame2Listener(new Game2Listener());
        this.view.addGame3Listener(new Game3Listener());
        this.view.addGame4Listener(new Game4Listener());
        
    }
    
    // Defining ActionListener for start button
    class StartListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			view.showMenuPanel();
		}
    	
    }
    
    class ProfileListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("dialog profilo aperto");
			view.openNewProfileDialog();
			view.addSaveButtonListener(new SaveListener());
//        	String[] data = view.openNewProfileDialog();
//        	System.out.println(data);
//        	if (data != null && data.length == 2) {
//        		newProfileDataEntered(data[0], data[1]);
        	}
		}
    	
    class SaveListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String username = view.getUsername();
            if (username.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter a username and choose a profile picture.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
            	String avatar = view.getAvatar();
                saveData(username, avatar);
                System.out.println("New data entered.");
                view.closeNewProfileDialog();
                
            }
			
		}
    	
    }


    
    class Game2Listener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Starting game with 2 players...");
			collectGameSetup(2, view.getUsername(), view.getAvatar());
			
			view.displayGameOverMessage();
		}
    	
    }
    
    class Game3Listener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Starting game with 2 players...");
			collectGameSetup(3, view.getUsername(), view.getAvatar());
		}
    	
    }
    
    class Game4Listener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Starting game with 2 players...");
	    	collectGameSetup(4, view.getUsername(), view.getAvatar());
		}
    	
    }
    
    private void saveData(String username, String avatar) {
        try {
            String filename = "profile.txt";
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            writer.write("Username: " + username + "\n");
            writer.write("Profile Picture: " + avatar+ "\n");
            writer.close();
            JOptionPane.showMessageDialog(null, "Profile saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error saving profile.", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    
    public void collectGameSetup(int numPlayers, String username, String avatar) {
    	System.out.println("Setting...");
        model.initialzeGame(numPlayers, username, avatar);
//    	model.prova(numPlayers, username, avatar);
       
    }


	@Override
	public void onWildcardDrawn(ArrayList<Card> hand) {
		System.out.print("Where do you want to put the wild card? ");
		scanner = new Scanner(System.in);
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
	    model.getWildcardPlace(wildcardPlace);
		
	}

	@Override
	public void onWinnerScoreUpdate(Player player) {
		if (player.getIsBot()) {
			// display which bot won in the game panel and then show the gif
			view.displayGameOverMessage();
		} else {
			// display that the user has won in the game panel and then show the gif
			view.displayWinMessage();
		}
		
	}
	
	@Override
	public void update(ArrayList<Card> hand) {
		// TODO Auto-generated method stub
		
		
	}
	public void closeScanner() {
		scanner.close();
	}


}