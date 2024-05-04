package jtrash.controller;

import jtrash.model.cards.Card;
import jtrash.model.cards.DefaultDeck;
import jtrash.model.cards.DiscardDeck;
import jtrash.model.game.Game;
import jtrash.model.players.Player;
import jtrash.view.GameFrame;
import jtrash.view.GameOver;
import jtrash.view.GameView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.swing.JOptionPane;


/**
 * The controller class.
 * @author val7e
 *
 */
public class GameController {
    private GameView view;
    private Game model;
    private GameFrame frame;
    
    private Card drawnCard;
   
    public GameController(Game model, GameView view) {
        this.model = model;
        this.view = view;
        
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
			String username = GameView.getUsername();
            if (username.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter a username and choose a profile picture.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
            	String avatar = GameView.getAvatar();
                saveData(username, avatar);
                System.out.println("New data entered: " + username + " " + avatar);
                view.gameMenu();
            }
		}
    }


    
    class Game2Listener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Starting game with 2 players...");
			collectGameSetup(2, GameView.getUsername(), GameView.getAvatar());
		}
    	
    }
    
    class Game3Listener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Starting game with 3 players...");
			collectGameSetup(3, GameView.getUsername(), GameView.getAvatar());
		}
    	
    }
    
    class Game4Listener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Starting game with 4 players...");
	    	collectGameSetup(4, GameView.getUsername(), GameView.getAvatar());
		}
    	
    }
    
    class DiscardDeckListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("pesco carta dal discard deck. . .");
	    	showNotificationOnSwap();
		}
    	
    }
    
    class DefaultDeckListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("pesco carta dal deck. . .");
			notifyDrawnLabel();
			showNotificationOnSwap();
		}
    	
    }

    private void saveData(String username, String avatar) {
        try {
            String filename = "profile.txt";
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            writer.write("Username: " + username + "\n");
            writer.write("Profile Picture: " + avatar + "\n");
            writer.close();
            JOptionPane.showMessageDialog(null, "Profile saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            view.getNewProfileDialog().dispose();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error saving profile.", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    
    /**
     * This methods starts the actual game frame. 
     * @param numPlayers number of the players of the game chosen by the user
     * @param username inserted by the user (from the new profile dialog)
     * @param avatar chosen from a FileChooser by the user (from the new profile dialog)
     */
    public void collectGameSetup(int numPlayers, String username, String avatar) {
    	view.dispose();
    	System.out.println("collecting game setup ...");
    	model = new Game();
    	model.initializeGame(numPlayers, username, avatar);
    	
    	// adding the frame to the observers list of the model
    	model.addObserver(frame);
    	
    	// creating new game frame
    	frame = new GameFrame(numPlayers);
		
		// to show first card on discardDeck
    	frame.initDiscardDeck(model.getFirstCard());
    	
    	// getting players hand
    	notifyPlayersMap();
    	
    	// here will be written all the action listener relative to the game frame
    	frame.addDiscardDeckListener(new DiscardDeckListener());
    	frame.addDefaultDeckListener(new DefaultDeckListener());
    	// update the hands
    	
    	// starting the game
    	model.start();
    	notifyPlayersMap();
    	// update the winner
    	onWinnerScoreUpdate(model.getWinner());
    }


	public int onWildcardDrawn() {
		ArrayList<Card> hand = model.getPlayerHand(model.getPlayers().get(model.getPlayerTurnPointer()));
		Scanner inputIndex = new Scanner(System.in);
	    System.out.print("Where do you want to put the wild card? ");
	    List<Integer> faceDownCards = IntStream.range(0, hand.size())
	    		.filter(i -> !hand.get(i).isFaceUp())
                .map(i -> i + 1)
                .boxed()
                .collect(Collectors.toList());
	    
	    System.out.println("Your face down cards are: "+ faceDownCards);
	    int intTopCard  = -1;
	    
	    while (true) {
	    	try {
	    		String str = inputIndex.next();
		    	intTopCard = Integer.parseInt(str)-1;
		    	
		        if (intTopCard < 0 || intTopCard >= hand.size() || hand.get(intTopCard).isFaceUp()) {
		            System.out.println("Input not valid. Give the position of covered card:");
		        } else break; // input is valid so exit the loop
		    } catch (NumberFormatException | InputMismatchException e) {
		    	System.out.println("Input not valid. Give a valid int.");
		    	inputIndex.nextLine();
		    }
	    	faceDownCards = IntStream.range(0, hand.size())
	                .filter(i -> !hand.get(i).isFaceUp())
	                .mapToObj(i -> hand.get(i).getIntValue())
	                .collect(Collectors.toList());
	    }
	    inputIndex.close();
	    return intTopCard;
	}
	
	
	/**
	 * 
	 * @param player
	 */
	public void onWinnerScoreUpdate(Player player) {
		frame.stop();
		GameOver over = new GameOver(player.isBot());
		
		
	}
	
	/**
	 * A method that updates the DrawnLabel with the card drawn by the PlayerUser in the GUI.
	 */
	public void notifyDrawnLabel() {
		frame.updateDrawnLabel(notifyDefaultDeck());
	}
	
	/**
	 * A method that allows the PlayerUser to draw a card from the deck from the GUI.
	 * @return the card drawn.
	 */
	public Card notifyDefaultDeck() {
		DefaultDeck defaultDeck = model.getDeck();
		this.drawnCard = defaultDeck.drawCard();
		frame.updateDefaultDeck(drawnCard);
		return drawnCard;
		
	}

	/**
	 * A method that builds and updates the hands of the players in the GUI.
	 */
	public void notifyPlayersMap() {
		LinkedHashMap<Player, ArrayList<Card>> map = model.getPlayersMap();
		frame.updatePlayersMap(map, null);
	}

	/**
	 * A method that swaps the card drawn with the card in the hand and checks before if it is swappable by calling isSwappable from the model.
	 */
	public void showNotificationOnSwap() {
		System.out.println("mando notifica al frame");
		ArrayList<Card> hand = model.getPlayerHand(model.getPlayers().get(model.getPlayerTurnPointer()));
		Player currentPlayer = model.getPlayers().get(model.getPlayerTurnPointer());
		DiscardDeck discardDeck = model.getDiscardDeck();
		this.drawnCard = discardDeck.drawCard();	
		if (model.isSwappable(this.drawnCard, hand)) {
			System.out.println("swappable");
			drawnCard = model.swapCard(this.drawnCard, hand, currentPlayer);
			System.out.println(model.getPlayerHand(model.getPlayers().get(model.getPlayerTurnPointer())));
			frame.updatePlayersMap(model.getPlayersMap(), drawnCard);
			frame.showNotification();
		} else {
			System.out.println("not swappable");
			frame.showNotification();
		}
		
	}

//	@Override
//	public void update(Game model) {
//		model.notifyObserversOnTopCard(model.getTopCard());
//		model.notifyObserversOnHiddenCard(model.drawFromDeck());
//		ArrayList<Player> players = model.getPlayers();
//		for (Player player : players) {
//			ArrayList<Card> hand = model.getPlayerHand(player);
//		}
//	}
	
}