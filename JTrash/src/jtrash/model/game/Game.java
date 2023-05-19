/**
 * 
 */
package jtrash.model.game;

import jtrash.model.players.*;
import jtrash.model.cards.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Observable;


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
	 *  - adds the first card to the discardDeck
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
	    	System.out.println(player.getNickname() + playersMap.get(player));
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
			var player = this.players.get(playerTurnPointer);
			var hand = this.playersMap.get(player);
			Card cardToSwap = null;
			do {
				// leggi questa if condition per ultima, prima parti dalla riga 114
				if (cardToSwap != null && this.isSwappable(cardToSwap.getIntValue(), hand)) {
					int index = cardToSwap.getIntValue()-1;
					Card temp = hand.get(index);
					hand.add(index, cardToSwap);
					cardToSwap = temp;
					continue;
				}
				// pick discarded first 
				cardToSwap = this.discardDeck.peek();
				// swap it if you have a face down slot
				if (this.isSwappable(cardToSwap.getIntValue(), hand)) {
					cardToSwap = this.swap(cardToSwap, hand, discardDeck);
				} else {
					// else pick a facedown card from the default deck
					cardToSwap = this.deck.drawCard();
					// swap again if possible
					if (this.isSwappable(cardToSwap.getIntValue(), hand)) {
						cardToSwap = this.swap(cardToSwap, hand, deck);
					} else {
						// nothing else to do
						// interrupt the player's turn
						break;
					}
				}
			} while(cardToSwap != null);
			playerTurnPointer = (playerTurnPointer + 1) % this.players.size();
		}
	}
	
	/**
	 * A method that checks if the top card in the discard deck is useful to the player.
	 * @param topCard the top card in the discard deck,
	 * @param hand the hand of cards of the player.
	 * @return 
	 * 		true if the index equals to the integer of the topCard in the hand has a face down card (USEFUL CARD),
	 * 		false if not (NOT USEFUL CARD).
	 */
	public boolean isSwappable(int intTopCard, ArrayList<Card> hand) {
		// true if card at the index intTopCard of the hand Card is face down.
		return !hand.get(intTopCard-1).isFaceUp();
	}
	
	public Card swap(Card topCard, ArrayList<Card> hand, Deck deck) {
		var intTopCard = topCard.getIntValue();
		Card card = hand.remove(intTopCard);
		hand.add(intTopCard-1, topCard);
		deck.removeCard(topCard);
		card = hand.get(intTopCard-1);
		return card;
	}
	
}
