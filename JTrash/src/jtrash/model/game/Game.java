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
		/**
		 * - pesco carta:
		 *    - if la prima carta del discardDeck discardDeck.peek() non ce l'ho pesco quella: discardDeck.pop(),
		 *    - else pesco una coperta dal deck: deck.drawCard();
		 * - if la carta in posizone==value pescata è ancora coperta -> swappo pescata con carta in hand
		 *    - continuo while value carta pescata==posizione coperta
		 *    - break value carta pescata == posizione già girata
		 * - if la posizione è già occupata da corretta/wildcard then scarto la carta -> discardDeck.push(carta)
		 * 
		 */
		int turn = 3;
		while(turn!=0) {
			/**
			 *  discardDeck.peek().getValue() == numero della carta in top
			 */
			
			
			for (Player player : this.players) {
				ArrayList<Card> hand = playersMap.get(player);
				
				System.out.println(player.getNickname() + ": " + hand);
				
				Card topCard = discardDeck.peek();
				int intTopCard = topCard.getValue().getInt();
				if (checkCard(intTopCard, hand)) {
					System.out.println("Pesco" + topCard.toString());
					
					// swap the topCard with the card at the index intTopCard
					Card newCard = swap(topCard, intTopCard, hand, discardDeck);
					System.out.println(newCard.toString());
					turn--;
					
				} else {
					topCard = deck.drawCard();
					intTopCard = topCard.getValue().getInt();
					if (checkCard(intTopCard, hand)) {
						System.out.println("Pesco" + topCard.toString());
						
						// swap the topCard with the card at the index intTopCard
						Card newCard = swap(topCard, intTopCard, hand, deck);
						System.out.println(newCard.toString());
						turn--;
					} else {
						System.out.println("Next player: ");
						turn--;
					}

				}

				
			}
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
	public boolean checkCard(int intTopCard, ArrayList<Card> hand) {
		// true if card at the index intTopCard of the hand Card is face down.
		return !hand.get(intTopCard-1).isFaceUp();
	}
	
	public Card swap(Card topCard, int intTopCard, ArrayList<Card> hand, Deck deck) {
		Card card = hand.remove(intTopCard);
		hand.add(intTopCard-1, topCard);
		deck.removeCard(topCard);
		card = hand.get(intTopCard-1);
		return card;
	}
	
}
