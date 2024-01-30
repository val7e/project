/**
 * 
 */
package jtrash.model.game;

import jtrash.model.players.*;
import jtrash.model.cards.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;


/**
 * @author val7e
 *
 */
public class Game {
	private DefaultDeck deck;
	private DiscardDeck discardDeck;
	private int numPlayers;
	private String username;
	private ArrayList<Player> players;
	private LinkedHashMap<Player,ArrayList<Card>> playersMap;
	private int roundCounter;
	private ArrayList<Player> winners = new ArrayList<Player>();
	private List<Observer> observers;
	
	
	/**
	 * Game constructor: it takes
	 * @param numPlayers an int that stands for the number of players useful to calculate how many decks do we need,
	 * @param listPlayers an ArrayList of objects Player.
	 */
	public Game() {
		this.numPlayers = 0;
		this.username = "";
		this.observers = new ArrayList<>();
	}
	
	/**
	 * 
	 * @param numPlayers
	 * @param username
	 */
	public void initialzeGame(int numPlayers, String username) {
		int howManyDecks = calculateDecks(numPlayers);
		buildDecks(howManyDecks);
		ArrayList<Player> listPlayers = new ArrayList<Player>();
		//building list of players based on number of players given in main
	    listPlayers.add(new PlayerBot(username, "IconUser", false));
		List<String> botNames = Arrays.asList("Jim", "Pam", "Dwight");
		List<String> botAvatar = Arrays.asList("IconJim", "IconPam", "IconDwight");
		int limit = numPlayers-1;
		for (int i = 0; i < limit; i++) {
			listPlayers.add(new Player(botNames.get(i), botAvatar.get(i), true));
		}
		this.players = listPlayers;
		prepareGame(players);
		start();
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
	 * A private method that is called inside the constructor and it does everything that it needs to be done to set the game:
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
		discardDeck.add(deck.drawCard());
		
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
	 * Max 10 rounds, then the game is over. (Not implemented, TO MODIFY).
	 *  
	 */
	public void start() {

		boolean gameOver = false; // boolean to handle the game

		boolean roundOver = false; // boolean to handle the rounds
		
		gameLoop: while (!gameOver) {
			prepareGame(players); // to handle
			roundOver = false;
			winners.clear();
			int playerTurnPointer = 0;
			
			
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
					
					System.out.println("Turno di: " + player.getNickname());
					
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
						System.out.println(player.getNickname() + " pesca dal DISCARD DECK " + cardToSwap.toString() + " numero: " + cardToSwap.getIntValue() + " indice: " + cardToSwap.getIndexValue());
						
					// else pick a face down card from the default deck and increment turn
					} else {
						cardToSwap = this.deck.drawCard();
						System.out.println(player.getNickname() + " pesca dal DECK " + cardToSwap.toString() + " numero: " + cardToSwap.getIntValue() + " indice: " + cardToSwap.getIndexValue());
						
					}
					
					// swap it if you have a face down slot
					cardToSwap = swap(cardToSwap, hand, player);	
					discardDeck.add(cardToSwap);
					System.out.println(player.getNickname() + " scarta " + cardToSwap);
					
					if (isLastTurn(hand)) {
						
						// add winner of the round to the list of winners
						winners.add(player);
						System.out.println("=================== " + player.getNickname() + " says Trash!! ===================");
						
						// check if game is over
						if (hand.size() == 1 && hand.get(0).isFaceUp()) {
							roundOver = true;
							gameOver = true;
							break gameLoop;
						}
					}	
					playerTurnPointer = (playerTurnPointer + 1) % this.players.size();
				}
			}
			
			Player winner = winners.get(0);
			System.out.println("ROUND OVER! The winner is " + winner.getNickname() + ". So " + winners.toString() + " will be dealt less a card in the next round (next level).");
			
			for (Player player : winners) {
				player.incrementLevel();
			}
		}
		
		for (Player player : players) {
			if (winners.contains(player)) {
				player.addGamesWon();
			} else player.addGamesLost();
		}
		
		Player winner = winners.get(0);
		System.out.println("GAME OVER: The winner of the game is " + winner + "!!");
		
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
		if (topCard.getIntValue() == 12) return false; // 12 equals Jack and Queen
		if (topCard.getIntValue() == 11) return true; // 11 equals King and Joker
		if (index >= hand.size()) return false; // control for the rounds > 0 (where one or more player are dealt less a card.)
		System.out.println(index + " < " + hand.size());
//		// TRY CATCH TO REMOVE
//		try {
//			System.out.println("La carta nel discard deck e': " + topCard.getIntValue());
//			System.out.print("Nella hand ha la carta: " + hand.get(index));
//			if (hand.get(index).isFaceUp()) System.out.println(" che e' scoperta.");
//			else System.out.println(" che e' coperta.");
//		}
//		catch (IndexOutOfBoundsException e) {
//			e.printStackTrace();
//		}
		// checking if the card in the hand is face up but the slot in the hand is kept by a wild card
		if (hand.get(index).isFaceUp() && hand.get(index).getType() == Type.WILD) return true;
		else return !hand.get(index).isFaceUp();
	}

	/**
	 * A method that swaps the card at the index intTopCard with the card just pulled out
	 * @param topCard the card just pulled out
	 * @param hand the hand of cards of the player.
	 * @return the card to discard.
	 */
	public Card swap(Card topCard, ArrayList<Card> hand, Player player) {
		// computes the index of the card from its value.
		int intTopCard = topCard.getIndexValue();
		// if a NOT USEFUL CARD is dealt it return it and it will be discarded.
		if (topCard.getIntValue() == 12) return topCard; // 12 equals Jack and Queen
		
		// if is wild: 11 equals King and Joker
		if (topCard.getIntValue() == 11) {
			// base case
			if (hand.size() == 1) {
				// i do not ask where to place the wildcard because there's only one slot, is it correct?
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
			else if (player.getIsBot()) {
				intTopCard = findFirstNotFaceUpCard(hand);
				System.out.println(player.getNickname() + " sceglie " + intTopCard);
			}
			else {
				intTopCard = notifyObserversOnWildcardDrawn(hand);
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
			System.out.println("Metto " + topCard +  " all'indice " + intTopCard + " cioè alla posizione " + topCard.getIntValue() + " e pesco " + card);
			return swap(card, hand, player);
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
	public boolean isLastTurn(ArrayList<Card> hand) {
	    return hand.stream()
	               .allMatch(Card::isFaceUp);
	}
	
	public static int findFirstNotFaceUpCard(ArrayList<Card> hand) {
        return hand.stream()
                   .filter(card -> !card.isFaceUp())
                   .findFirst()
                   .map(hand::indexOf)
                   .orElse(-1);
    }
	
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
	
	/**
	 * 
	 * @param hand
	 * @return
	 */
	private int notifyObserversOnWildcardDrawn(ArrayList<Card> hand) {
		int index = 0;
		for (Observer observer : observers) {
			index = observer.onWildcardDrawn(hand);
		}
		return index;
	}
}
