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

		boolean gameOver = false;

		int playerTurnPointer = 0;
		
		while (!gameOver) {
			int turn = 0;
			Player player = this.players.get(playerTurnPointer);
			ArrayList<Card> hand = this.playersMap.get(player);
			Card cardToSwap = null;
			// player's turn loop
			while (true) {
				if (cardToSwap != null && this.isSwappable(cardToSwap.getIntValue(), hand)) {
					cardToSwap = this.swap(cardToSwap, hand);
					continue;
				} else if (turn != 0) {
					discardDeck.add(cardToSwap);
					break;
				}
				// pick discarded card first and increment turn
				if (discardDeck.size() != 0) {
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
		}
	}
	
	/**
	 * A method that checks if the top card in the discard deck is useful to the player.
	 * @param topCard the top card in the discard deck,
	 * @param hand the hand of cards of the player.
	 * @return  true:  if the card at the index intTopCard-1 of the hand has a face down card (USEFUL CARD),
	 *          false: if not (NOT USEFUL CARD).
	 * 	TO DO: borderline cases to handle:
	 * 		- WILD cards: KING and JOKER -> return ??
	 * 		- BLANK cards: JACK and QUEEN -> return false.
	 * 		at the moment the return false.
	 */
	public boolean isSwappable(int intTopCard, ArrayList<Card> hand) {
		if (intTopCard == 0) return false;
		if (intTopCard == 11) return true;
		else return !hand.get(intTopCard-1).isFaceUp();
	}
	
	/**
	 * 
	 * @param topCard
	 * @param hand
	 * @return
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
		Card card = hand.get(intTopCard);
		hand.remove(intTopCard);
		hand.add(intTopCard, topCard);
		hand.get(intTopCard).setFaceUp(true);
		return card;
	}
	
}
