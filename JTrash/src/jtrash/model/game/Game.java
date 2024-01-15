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

		// boolean to handle the game (TO HANDLE)
		/**
		 * GAME OVER: the game goes on until one player is only dealt one card. 
		 * They must fill that spot with an Ace or wildcard. 
		 * If they do so and they say “Trash,” this ends the entire game.
		 * 
		 */
		boolean gameOver = false;
		
		// boolean to handle the rounds
		
		/**
		 * ROUND OVER: when at least one player completes his hand.
		 * In the next round winners are dealt one less card.
		 * The others are dealt the same amount of card than the round before.
		 * Max 10 rounds, then the game is over.
		 */
		boolean roundOver = false;
		
		// boolean to handle last turn (handled with method isLastTurn)
		
		/**
		 * LAST TURN: when someone has completed his set and says "TRASH"
		 * each player gets to draw one more card to try and complete their set,
		 * so is thier last turn. Then the round is over.
		 * So lastTurn happens right before roundOver.
		 */
		boolean lastTurn = false;
		
		/*
		 * winners: list that is updated after the end of each round.
		 */
		ArrayList<Player> winners = new ArrayList<Player>();
		
		int playerTurnPointer = 0;
		
		while (!roundOver) {
			int turn = 0;
			
			// player's turn loop
			while (true) {
				// selecting the current player
				Player player = this.players.get(playerTurnPointer);
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
				cardToSwap = swap(cardToSwap, hand);	
				discardDeck.add(cardToSwap);
				System.out.println(player.getNickname() + " scarta " + cardToSwap);
				
				if (isLastTurn(hand)) {
					//check if game is over
					if (hand.size() == 1 && hand.get(0).isFaceUp()) {
						roundOver = true;
					} else winners.add(player);
				}
				turn++;
				playerTurnPointer = (playerTurnPointer + 1) % this.players.size();
			}	
			
//			
//			
//			if (lastTurn == true) {
//				roundOver = true;
//				Player winner = this.players.get(playerTurnPointer);
//			}
//			
//			if (roundOver == true) {
//				Player winner = this.players.get(playerTurnPointer);
//				System.out.println(winner.getNickname() + "è il vincitore del round!");
//			}
//			
		}
		
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
		try {
			System.out.println("Current player ha pescato: " + topCard.getIntValue() + " che ha indice: " + topCard.getIndexValue());
			System.out.println("Nella hand all'indice " + index + " ha la carta: " + hand.get(index));
			if (hand.get(index).isFaceUp()) System.out.println("scoperta");
			else System.out.println("coperta");
		}
		catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
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
	public Card swap(Card topCard, ArrayList<Card> hand) {
		// computes the index of the card from its value.
		int intTopCard = topCard.getIndexValue();
		/*
		 * ACE 1 -> 0
		 * TWO 2 -> 1
		 * THREE 3 -> 2
		 * FOUR 4 -> 3
		 * FIVE 5 -> 4
		 * SIX 6 -> 5
		 * SEVEN 7 -> 6
		 * EIGHT 8 -> 7
		 * NINE 9 -> 8
		 * TEN 10 -> 9
		 * JACK, QUEEN 12 -> 11
		 * KING, JOKER 11 -> 10
		 */
		// if a NOT USEFUL CARD is dealt it return it and it will be discarded.
		if (topCard.getIntValue() == 12) return topCard; // 12 equals Jack and Queen

		// if the player has ONLY one card that is Ace(1)/Wild(11)
		if (hand.size()==1 && (topCard.getIntValue()==1 || topCard.getIntValue()==11)) return topCard;
		
		// if is wild: 11 equals King and Joker
		if (topCard.getIntValue() == 11) {
			// caso base
			if (hand.size() == 1) {
				Card card = hand.get(intTopCard);
				if (!card.isFaceUp()) {
					hand.remove(intTopCard);
					hand.add(intTopCard, topCard);
					hand.get(intTopCard).setFaceUp(true);
					return card;
				}
				else return topCard;
			}
			else {
				Scanner inputIndex = new Scanner(System.in);
			    System.out.print("Where do you want to put the wild card? ");
			    
			    String str = inputIndex.next();
			    try {
			    	intTopCard = Integer.parseInt(str)-1;
			        // check if the input gave by the player corresponds to a face down up and
			    	// returns a error message because it is not valid, keeps asking until the player enters a valid input
			        while (hand.get(intTopCard).isFaceUp()) {
			            System.out.println("Input non valido. Inserisci la posizione di una carta non girata:");    
			            intTopCard = inputIndex.nextInt()-1;
			        }
			    } catch (NumberFormatException e) {
			    	e.printStackTrace();
			    }	
			
			    Card card = hand.get(intTopCard);
			    //gestire lo swap
			    hand.remove(intTopCard);
				hand.add(intTopCard, topCard);
				hand.get(intTopCard).setFaceUp(true);
				
				System.out.println("Posiziona " + topCard + " e si ritrova in mano " + card);
				
			    return swap(card, hand);
			}

		}
			
		
		// saving the card to swap in card variable
		Card card = hand.get(intTopCard);
		if (card.isFaceUp()) {
			System.out.println("La carta da scartare è girata, quindi non potrei.");
		} else System.out.println("La carta da scartare è coperta quindi posso scambiarla.");
		
		
		if (this.isSwappable(topCard, hand)) {
			// removing the card from the hand
			hand.remove(intTopCard);
			// adding the swapped card at the correct index
			hand.add(intTopCard, topCard);
			// setting the isFaceUp value to true in the swapped card
			hand.get(intTopCard).setFaceUp(true);
			System.out.println("Metto " + topCard +  " all'indice " + intTopCard + " cioè alla posizione " + topCard.getIntValue() + " e pesco " + card);
			return swap(card, hand);
		}
		return card;
		
	}
	
	/**
	 * A method that checks if the current player has completed the hand, Ace to 10.
	 * This method needs to be executed after every player has completed his swapping, so after he discards a card.
	 * You can win even if you have some wild cards in place, so we need to check only the isFaceUp field.
	 * @param hand the hand of cards of the player.
	 * @return 	true: if every card of the hand is face up,
	 * 			false: if not.
	 */
	public boolean isLastTurn(ArrayList<Card> hand) {
		boolean check = false;
		int i = 0;
		
		for (Card card : hand) {
			if (card.isFaceUp() == true) i++;
		}
//		System.out.println(i);
		
		if (i==10) check = true;
		
		return check;
	}
	
	public boolean allCardsInPlace(ArrayList<Card> hand) {
		boolean check = false;
		int i = 0;
		
		for (Card card : hand) {
			if (card.getIntValue() == i) 
				i++;
		}
		
		if (i==10) check = true;
		
		return check;
	}

}
