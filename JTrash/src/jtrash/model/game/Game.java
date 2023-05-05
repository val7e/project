/**
 * 
 */
package jtrash.model.game;

import jtrash.model.players.*;
import jtrash.model.cards.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Observable;
import java.util.Stack;

/**
 * @author val7e
 *
 */
@SuppressWarnings("deprecation")
public class Game extends Observable {
	private Deck deck;
	private Stack<Card> discardDeck;
//	private int numPlayers;
	private ArrayList<Player> players;
	private LinkedHashMap<Player,ArrayList<Card>> playersMap;
	
	private boolean gameOver = false;
	
	
	public Game(int numPlayers, ArrayList<Player> listPlayers) {
		int howManyDecks = calculateDecks(numPlayers);
		this.deck = new Deck();
		while (howManyDecks != 0) {
			ArrayList<Card> toConcat = Deck.buildDeck();
			this.deck.merge(toConcat);
			howManyDecks--;
		}
		this.discardDeck = new Stack<Card>();
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
	
	public static int calculateDecks(int players) {
		if (players >= 5) return 2;
		if (players >= 3) return 1; 
		else return 0; //2 players
	}
	
	public void prepareGame(ArrayList<Player> players) {
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
		while(!this.gameOver) {
			/**
			 *  discardDeck.peek().getValue() == numero della carta in top
			 */
			
			
			for (Player player : this.players) {
				ArrayList<Card> hand = playersMap.get(player);
				
				
				checkCard();
			}
		}
	}
	
	public boolean checkCard(Card topCard, ArrayList<Card> hand) {
		//numero della topCard 
		int numTopCard = discardDeck.peek().getValue().getNum();
		
		// Card all'indice uguale al numero della topCard
		Card card = hand.get(numTopCard);
		int numCard = card.getValue().getNum(); // numero della card
		
		while (numCard == hand.indexOf(card)) {
			System.out.println(numCard + ", "+ card);
			
		System.out.println("Pesco");
		Card deckCard = deck.drawCard();
		int numDeckCard = deckCard.getValue().getNum();
		
		if numCard == hand.indexOf(card)
			
		}
	}
}
