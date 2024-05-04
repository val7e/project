package jtrash.model.game;

import jtrash.model.players.*;
import jtrash.model.cards.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


/**
 * @author val7e
 *
 */
public class Game {
	private DefaultDeck deck;
	private DiscardDeck discardDeck;
	
	private ArrayList<Player> players;
	private int playerTurnPointer;

	private LinkedHashMap<Player,ArrayList<Card>> playersMap;

	private boolean gameOver;
	
	private Player winner;
	
	

	private int roundCounter;
	private ArrayList<Player> winners = new ArrayList<Player>();
	
	
	private List<Observer> observers;
	private int wildcardPlace;
	
	private Card firstCard;
	
	
	/**
	 * Public constructor.
	 */
	public Game() {
		this.observers = new ArrayList<Observer>();
	}
	
	/**
	 * A method that initialize the game, builds the list of players.
	 * It also calls prepareGame(), the final step to set the game.
	 * @param numPlayers the number of players chosen by the user by GUI
	 * @param username of the PlayerUser chosen by ProfileDialog (GUI)
	 * @param avatar of the PlayerUser chosen by ProfileDialog (GUI)
	 */
	public void initializeGame(int numPlayers, String username, String avatar) {
		int howManyDecks = calculateDecks(numPlayers);
		buildDecks(howManyDecks);
		
		//building list of players based on number of players given in the controller
		ArrayList<Player> listPlayers = new ArrayList<Player>();
		PlayerUser user = PlayerUser.getInstance(username, avatar);
	    listPlayers.add(user);
		List<String> botNames = Arrays.asList("Jim", "Pam", "Dwight");
		List<String> botAvatar = Arrays.asList("graphics/iconJim.png", "graphics/iconPam.png", "graphics/iconDwight.png");
		int limit = numPlayers-1;
		for (int i = 0; i < limit; i++) {
			listPlayers.add(new PlayerBot(botNames.get(i), botAvatar.get(i)));
		}
		this.players = listPlayers;
		System.out.println("model dice: initialize game");
		prepareGame(players);
		System.out.println("il game è pronto per iniziare.");
	}
	
	/**
	 * A private method that is called inside the constructor and it calculates how many decks do we need need based on the number of players in the game.
	 * @param players: the number of players in the game.
	 * @return the number of decks that needs to be built.
	 */
	private static int calculateDecks(int players) {
		if (players >= 5) return 2;
		if (players >= 3) return 1; 
		else return 0; //2 players
	}
	
	/**
	 * This method builds the deck by merging how many decks are needed.
	 * Builds the Discard Deck.
	 * @param howManyDecks
	 */
	private void buildDecks(int howManyDecks) {
		this.deck = new DefaultDeck();
		while (howManyDecks != 0) {
			ArrayList<Card> toConcat = DefaultDeck.buildDeck();
			this.deck.merge(toConcat);
			howManyDecks--;
		}
		this.discardDeck = new DiscardDeck();	
	}
	
	/**
	 * A private method that is called inside the method initializeGame and it does everything that it needs to be done to set the game:
	 *  - shuffles the cards
	 *  - creates the LinkedHashMap with each Player and its ArrayList of cards (hand)
	 *  - deals the hand of cards to each Player
	 *  - adds the first discarded card to the discardDeck
	 * 
	 * @param players an ArrayList of objects Player
	 */
	private void prepareGame(ArrayList<Player> players) {
		if (roundCounter > 0) {
			buildDecks(calculateDecks(players.size()));
		}
		deck.shuffleCards();
		this.playersMap = new LinkedHashMap<Player,ArrayList<Card>>();
		for (Player player : players) {
			ArrayList<Card> hand = new ArrayList<Card>();
			int loopLimit = 10 - player.getLevel();
			for (int i = 1; i <= loopLimit; i++) {
				hand.add(deck.drawCard());
			}
			playersMap.put(player, hand);	
		}
		this.firstCard = deck.drawCard();
		discardDeck.add(firstCard); // first card of the discard deck
		
	}
	
	/**
	 * The method that is invoked to actually start the game; it contains all the core logic of the card game.
	 * 
	 * GAME OVER: the game goes on until one player is only dealt one card. 
	 * They must fill that spot with an Ace or wildcard. 
	 * If they do so and they say "Trash", this ends the entire game.
	 * 
	 * ROUND OVER: when at least one player completes his hand (says "Trash").
	 * In the next round winners are dealt one less card.
	 * The others are dealt the same amount of card than the round before.
	 * The game is over when the player who is only dealt one card fills it.
	 *  
	 */
	public void start() {

		this.gameOver = false; // boolean to handle the game

		boolean roundOver = false; // boolean to handle the rounds
		
		gameLoop: while (!this.gameOver) {
			prepareGame(players); // to handle
			roundOver = false;
			winners.clear();
			this.playerTurnPointer = 0;
			
			
			roundLoop: while (!roundOver) {
				System.out.println("Round: " + roundCounter);
				// player's turn loop
				while (true) {
					System.out.println("playerTurnPointer " + playerTurnPointer);
					// selecting the current player
					Player player = this.players.get(playerTurnPointer);
					
					if (winners.contains(player)) {
						roundOver = true;
						roundCounter++; // roundCounter 
						break roundLoop;
					}	
					
				
					System.out.println(player.getNickname() + "'s turn.");
					
					//fetching the hand of the current player
					ArrayList<Card> hand = this.playersMap.get(player);
					
					//if deck is empty, reset decks
					if (deck.isEmpty()) {
						deck.getCards().addAll(discardDeck.getCards());
						discardDeck.getCards().removeAll(deck.getCards());
						discardDeck.add(deck.drawCard());
					}
					
					Card cardToSwap = null;
					// if discard deck ha something, pick discarded card first and increment turn
					if (!discardDeck.isEmpty()) {
						cardToSwap = this.discardDeck.peek();
					}
					
					//check if the top card in discard deck is swappable, if it is proceed to draw it:
					if (this.isSwappable(cardToSwap, hand)) {
						cardToSwap = this.discardDeck.drawCard();
						System.out.println(player.getNickname() + " draws from Discard Deck " + cardToSwap.toString());
						
					// else pick a face down card from the default deck and increment turn
					} else {
						cardToSwap = this.deck.drawCard();
						System.out.println(player.getNickname() + " draw from Deck " + cardToSwap.toString());
						
					}
					
					// swap it if you have a face down slot
					cardToSwap = swap(cardToSwap, hand, player);	
					discardDeck.add(cardToSwap);
					System.out.println(player.getNickname() + " discards " + cardToSwap);
					
					if (isLastTurn(hand)) {
						
						// add winner of the round to the list of winners
						winners.add(player);
						System.out.println("=================== " + player.getNickname() + " says Trash!! ===================");
						
						// check if game is over
						if (hand.size() == 1 && hand.get(0).isFaceUp()) {
							roundOver = true;
							this.gameOver = true;
							break gameLoop;
						}
					}	
					playerTurnPointer = (playerTurnPointer + 1) % this.players.size();
				}
			}
			
			winner = winners.get(0);
			System.out.println("ROUND OVER! The winner is " + winner.getNickname() + ". So " + winners.toString() + " will be dealt less a card in the next round (next level).");
			
			for (Player player : winners) {
				player.incrementLevel();
			}
		}
		
		winner = winners.get(0);
		System.out.println("GAME OVER: The winner of the game is " + winner + "!!");
		// setting scores and levels
		for (Player player : players) {
			if (player.equals(winner)) player.addGamesWon();
			else player.addGamesLost();
		}
		
		for (Player player : players) {
			System.out.println(player + " Wins: " + player.getGamesWon() + " Losses: " + player.getGamesLost());
		}
//		notifyObserversOnWinnerScoreUpdate(winner);
	}
	
	/**
	 * A method that checks if the pulled out card is useful to the player.
	 * @param topCard the top card i.e. the pulled out card (from deck or discardDeck),
	 * @param hand the hand of cards of the player.
	 * @return  true:  if the card at the index of the topCard of the hand 
	 * 						has a face down card (USEFUL CARD), or
	 * 						has a face up card but the slot is kept by a wild card.
	 *          false: if not (NOT USEFUL CARD).
	 * 	Borderline cases handled:
	 * 		- WILD cards: KING and JOKER -> condition explained previously
	 * 		- BLANK cards: JACK and QUEEN -> return false.
	 * 
	 * try catch block to remove
	 */
	public boolean isSwappable(Card topCard, ArrayList<Card> hand) {
		int index = topCard.getIndexValue();
		if (topCard.getIntValue() == 12) {
			System.out.println(topCard);
			return false; // 12 equals Jack and Queen
		}
		if (topCard.getIntValue() == 11) {
			System.out.println(topCard);
			return true; // 11 equals King and Joker
		}
		if (index >= hand.size()) {
			System.out.println("index out of range");
			return false; // control for the rounds > 0 (where one or more player are dealt less a card.)
		}

		// checking if the card in the hand is face up but the slot in the hand is kept by a wild card
		if (hand.get(index).isFaceUp() && hand.get(index).getType() == Type.WILD) {
			System.out.println(topCard);
			return true;
		}
		else {
			System.out.println(topCard);
			return !hand.get(index).isFaceUp();
		}
	}

	/**
	 * A method that swaps the card at the index intTopCard with the card just pulled out
	 * @param topCard the card just pulled out
	 * @param hand the hand of cards of the player.
	 * @return the card to discard.
	 */
	private Card swap(Card topCard, ArrayList<Card> hand, Player player) {
		// computes the index of the card from its value.
		int intTopCard = topCard.getIndexValue();
		// if a NOT USEFUL CARD is dealt it return it and it will be discarded.
		if (topCard.getIntValue() == 12) return topCard; // 12 equals Jack and Queen
		
		// if is wild: 11 equals King and Joker
		if (topCard.getIntValue() == 11) {
			// base case
			if (hand.size() == 1) {
				// i do not ask where to place the wildcard because there's only one slot
				Card card = hand.get(0);
				if (!card.isFaceUp()) {
					hand.remove(0);
					hand.add(0, topCard);
					hand.get(0).setFaceUp(true);
					return card;
				}
				else return topCard;
			}
			if (this.isLastTurn(hand)) return topCard;
			else if (player.isBot()) {
				intTopCard = findFirstNotFaceUpCard(hand);
				System.out.println(player.getNickname() + " chooses " + intTopCard);
			}
			else {
//				notifyObserversOnWildcardDrawn();
				
					Scanner inputIndex = new Scanner(System.in);
				    System.out.print("Where do you want to put the wild card? ");
				    List<Integer> faceDownCards = IntStream.range(0, hand.size())
				    		.filter(i -> !hand.get(i).isFaceUp())
			                .map(i -> i + 1)
			                .boxed()
			                .collect(Collectors.toList());
				    
				    System.out.println("Your face down cards are: "+ faceDownCards);
				    intTopCard = -1;
				    
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
				
				System.out.println(player.getNickname() + " chooses the position " + (intTopCard+1));

			}
		}
		
		// control for the rounds > 0 (where one or more player are dealt less a card.)
		if (intTopCard >= hand.size()) return topCard; 
		
		// saving the card to swap in card variable
		Card card = hand.get(intTopCard);

		if (this.isSwappable(topCard, hand)) {
			// removing the card from the hand
			hand.remove(intTopCard);
			// adding the swapped card at the correct index
			hand.add(intTopCard, topCard);
			// setting the isFaceUp value to true in the swapped card
			hand.get(intTopCard).setFaceUp(true);
			System.out.println("Puts " + topCard +  " at index " + intTopCard + " and finds " + card);
			return swap(card, hand, player);
		}
		return card;	
	}
	
	/**
	 * 
	 * @param topCard
	 * @param hand
	 * @param player
	 * @return
	 */
	public Card swapCard (Card topCard, ArrayList<Card> hand, Player player) {
		int intTopCard = topCard.getIndexValue();
		Card card = topCard;
		// if is wild: 11 equals King and Joker
		if (topCard.getIntValue() == 11) {
			notifyObserversOnWildcardDrawn();
		} else {
			card = hand.get(intTopCard);
			// removing the card from the hand
			hand.remove(intTopCard);
			// adding the swapped card at the correct index
			hand.add(intTopCard, topCard);
			// setting the isFaceUp value to true in the swapped card
			hand.get(intTopCard).setFaceUp(true);
			discardDeck.add(card);
			System.out.println("Puts " + topCard +  " at index " + intTopCard  + " and finds " + card);
		}
			return card;
	}
	
	/**
	 * A method that checks if the current player has completed the hand.
	 * This method needs to be executed after every player has completed his swapping, so after he discards a card.
	 * You can win even if you have some wild cards in place, so we need to check only the isFaceUp field.
	 * @param hand the hand of cards of the player.
	 * @return 	true: if every card of the hand is face up,
	 * 			false: if not.
	 */
	private boolean isLastTurn(ArrayList<Card> hand) {
	    return hand.stream()
	               .allMatch(Card::isFaceUp);
	}
	
	/**
	 * A method that finds the first not face up card for the PlayerBot.
	 * @param hand of the PlayerBot
	 * @return the int of the first not face up card in the hand.
	 */
	private static int findFirstNotFaceUpCard(ArrayList<Card> hand) {
        return hand.stream()
                   .filter(card -> !card.isFaceUp())
                   .findFirst()
                   .map(hand::indexOf)
                   .orElse(-1);
    }
	
	

	public void notifyObserversOnWildcardDrawn() {
		for (Observer observer : observers) {
			observer.onWildcardDrawn();
		}
	}
//	
//	public void notifyObserversOnWinnerScoreUpdate() {
//		for (Observer observer : observers) {
//			observer.onWinnerScoreUpdate();
//		}
//	}

	
	
	
	// METHODS FOR CONTROLLER
	
	public int getWildcardPlace(int place) {
		this.wildcardPlace = place;
		return place;
	}
	
	public ArrayList<Card> getPlayerHand(Player player) {
		return playersMap.get(player);
	}
	
//	
//	public String drawFromDiscard() {
//		if (discardDeck.isEmpty()) {
//			return null;
//		} else {
//			String path = this.discardDeck.drawCard().getPath();
//			System.out.println("model dice: "+path);
//			return path;
//		}
//		
//	}
//	
//	public Card getTopCard() {
//		return this.discardDeck.peek();
//	}
//	public Card drawFromDeck() {
//		return this.deck.drawCard();
//	}

	
	
	//GETTERS
	public DefaultDeck getDeck() {
		return deck;
	}

	public DiscardDeck getDiscardDeck() {
		return discardDeck;
	}

	public LinkedHashMap<Player, ArrayList<Card>> getPlayersMap() {
		return playersMap;
	}
	
	public int getPlayerTurnPointer() {
		return playerTurnPointer;
	}
	
	
	public boolean isGameOver() {
		return gameOver;
	}
	
	public Player getWinner() {
		return winner;
	}

	public Card getFirstCard() {
		return firstCard;
	}
	
	public ArrayList<Player> getPlayers() {
		return players;
	}
	
	// METHODS FOR OBSERVERS
	/**
	 * 
	 * @param observer
	 */
	public void addObserver(Observer observer) {
		observers.add(observer);
	}
	
	/**
	 * 
	 * @param observer
	 */
	public void removeObvserver(Observer observer) {
		observers.remove(observer);
	}
	
	public void notifyObservers() {
		for (Observer observer : observers) {
			observer.updateDiscardDeck(firstCard);
			observer.updateDefaultDeck(firstCard);
			observer.notifyPlayersMap();
		}
	}
	
	
}
