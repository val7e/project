/**
 * 
 */
package jtrash.model.game;

import jtrash.model.players.*;
import jtrash.model.cards.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Observable;
import java.util.Scanner;


/**
 * @author val7e
 *
 */
@SuppressWarnings("deprecation")
public class Game extends Observable {
	private DefaultDeck deck;
	private DiscardDeck discardDeck;
//	private int numPlayers;
	private ArrayList<Player> players;
	private LinkedHashMap<Player,ArrayList<Card>> playersMap;
	
	private boolean gameOver = false;
	
	
	/**
	 * Game constructor: it takes
	 * @param numPlayers an int that stands for the number of players useful to calculate how many decks do we need,
	 * @param listPlayers an ArrayList of objects Player.
	 */
	public Game(int numPlayers, ArrayList<Player> listPlayers) {
		int howManyDecks = calculateDecks(numPlayers);
		this.deck = new DefaultDeck();
		while (howManyDecks != 0) {
			ArrayList<Card> toConcat = DefaultDeck.buildDeck();
			this.deck.merge(toConcat);
			howManyDecks--;
		}
		
		this.discardDeck = new DiscardDeck();
		this.players = listPlayers;
		prepareGame(players);
		
		
//		NON POSSO CREARE LISTA PLAYERS DENTRO GAME
//		this.players = new ArrayList<Player>();
//		Player user = new Player("Vale");
//		
//		while (numPlayers != 0) {
//			Player jim = new Player("Jim");
//			jim.setIsBot(true);
//		}
		
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
	 * A private method that is called inside the constructor and it does everything that it needs to be done to set the game:
	 *  - shuffles the cards
	 *  - creates the LinkedHashMap with each Player and its ArrayList of cards (hand)
	 *  - deals the hand of cards to each Player
	 *  - adds the first discarded card to the discardDeck
	 * 
	 * @param players an ArrayList of objects Player
	 */
	private void prepareGame(ArrayList<Player> players) {
		deck.shuffleCards();
		this.playersMap = new LinkedHashMap<Player,ArrayList<Card>>();
		for (Player player : players) {
			ArrayList<Card> hand = new ArrayList<Card>();
			for (int i = 1; i <= 10; i++) {
				hand.add(deck.drawCard());
			}
			playersMap.put(player, hand);	
		}
		discardDeck.add(deck.drawCard());
		
	}
	
	/**
	 * The method that is invoked to actually start the game; it contains all the core logic of the card game.
	 */
	public void start() {

		// boolean to handle the game
		boolean gameOver = false;
		
		// boolean to handle the rounds
		boolean roundOver = false;
		
		// boolean to handle last turn
		boolean lastTurn = false;

		int playerTurnPointer = 0;
		
		while (!roundOver) {
			int turn = 0;
			Player player = this.players.get(playerTurnPointer);
			System.out.println(player.getNickname());
			ArrayList<Card> hand = this.playersMap.get(player);
			Card cardToSwap = null;
			// player's turn loop
			while (true) {
				//if deck is empty, need to restart it
				if (deck.isEmpty()) {
					deck.getCards().addAll(discardDeck.getCards());
					discardDeck.getCards().removeAll(deck.getCards());
					discardDeck.add(deck.drawCard());
				}
				if (cardToSwap != null && this.isSwappable(cardToSwap.getIntValue(), hand)) {
					cardToSwap = this.swap(cardToSwap, hand);
					continue;
				} else if (turn != 0) {
					discardDeck.add(cardToSwap);
					break;
				}
				// pick discarded card first and increment turn
				if (!discardDeck.isEmpty()) {
					cardToSwap = this.discardDeck.peek();
					turn++;
				}
				// swap it if you have a face down slot
				if (this.isSwappable(cardToSwap.getIntValue(), hand)) {
					discardDeck.removeCard(cardToSwap);
					cardToSwap = this.swap(cardToSwap, hand);
				// else pick a face down card from the default deck and increment turn
				} else {
					cardToSwap = this.deck.drawCard();
					turn++;
					// swap again if possible
					if (this.isSwappable(cardToSwap.getIntValue(), hand)) {
						deck.removeCard(cardToSwap);
						cardToSwap = this.swap(cardToSwap, hand);
					} else {
						discardDeck.add(cardToSwap);
						// interrupt the player's turn
						break;
					}
				}
			} 
			playerTurnPointer = (playerTurnPointer + 1) % this.players.size();
			if (playerTurnPointer+1 == this.players.size() && isLastTurn(hand)) {
				roundOver = true;
			}
		}
		
	}
	
	/**
	 * A method that checks if the pulled out card is useful to the player.
	 * @param topCard the top card i.e. the pulled out card (from deck or discardDeck),
	 * @param hand the hand of cards of the player.
	 * @return  true:  if the card at the index intTopCard-1 of the hand 
	 * 						has a face down card (USEFUL CARD), or
	 * 						has a face up card but the slot is kept by a wild card.
	 *          false: if not (NOT USEFUL CARD).
	 * 	Borderline cases handled:
	 * 		- WILD cards: KING and JOKER -> condition explained previously
	 * 		- BLANK cards: JACK and QUEEN -> return false.
	 * 
	 * try catch block to remove
	 */
	public boolean isSwappable(int intTopCard, ArrayList<Card> hand) {
		if (intTopCard == 0) return false; // 0 equals Jack and Queen
		if (intTopCard == 11) return true; // 11 equals King and Joker
		try {
			System.out.println("valore carta da pescata: " + intTopCard);
			System.out.println("Carta alla posizione " + intTopCard + ": " + hand.get(intTopCard-1) +" è face up: " + hand.get(intTopCard-1).isFaceUp()+  " "
					+ "ed è di tipo " + hand.get(intTopCard-1).getType());
		}
		catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
		// checking if the card in the hand is face up and the slot in the hand is kept by a wild card
		if (hand.get(intTopCard-1).isFaceUp() && hand.get(intTopCard-1).getType() == Type.WILD) return true;
		else return !hand.get(intTopCard-1).isFaceUp();
	}
	
	/**
	 * A method that swaps the card at the index intTopCard with the card just pulled out
	 * @param topCard the card just pulled out
	 * @param hand the hand of cards of the player.
	 * @return the card that was in place of the one just placed.
	 */
	public Card swap(Card topCard, ArrayList<Card> hand) {
		int intTopCard = topCard.getIntValue() - 1;
		if (topCard.getType() == Type.WILD) {
			// il player deve decidere quale carta swappare con la wild quindi devo registrare input da tastiera
			Scanner inputIndex = new Scanner(System.in);
		    System.out.print("Where do you want to put the wild card? ");
		    String str = inputIndex.next();
		    try {
		    	intTopCard = Integer.parseInt(str)-1;
		    } catch (NumberFormatException e) {
		    	e.printStackTrace();
		    }
		} 
		// saving the card to swap in card variable
		Card card = hand.get(intTopCard);
		// removing the card from the hand
		hand.remove(intTopCard);
		// adding the swapped card at the correct index
		hand.add(intTopCard, topCard);
		// setting the isFaceUp value to true in the swapped card
		hand.get(intTopCard).setFaceUp(true);
		System.out.println("Metto " + topCard +  " alla posizione " + topCard.getIntValue() + " e pesco " + card);
		return card;
	}
	
	/**
	 * A method that checks if the current player has completed the hand, Ace to 10.
	 * You can win even if you have some wild cards in place, so we need to check only the isFaceUp field.
	 * @param hand the hand of cards of the player.
	 * @return 	true: if every card of the hand is face up,
	 * 			false: if not.
	 */
	public boolean isLastTurn(ArrayList<Card> hand) {
		boolean check = false;
		int i = 0;
		
		for (Card card : hand) {
			if (card.isFaceUp() == false) {
				System.out.println("Next player.");
				check = false;
			}
		}
		
		
		while (i < 10) {
			for (Card card : hand) {
				if (card.isFaceUp() == true) {
					i++;
				}
				else break;
			}
		}
	
		if (i == 9) {
			System.out.println("Trash!");
			System.out.println(hand);
			check = true;
		}
		
			
			
		
		
		return check;
	}

}
