/**
 * 
 */
package jtrash.model.cards;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author val7e
 *
 */
public class Deck {
	private final ArrayList<Card> cards;
	
	/**
	 * Deck 1 constructor: builds a deck of 54 cards.
	 */
	public Deck() {
		this.cards = buildDeck();
	}
	
	
	public void shuffleCards() {
		Collections.shuffle(cards);
	}
	
	public int size() {
		return this.cards.size();
	}
	
	public Card drawCard() {
		return cards.remove(0);
	}
	
	/**
	 * This method is invoked in the default constructor.
	 * @return the deck for the game.
	 */
	public static ArrayList<Card> buildDeck() {
		ArrayList<Card> cards = new ArrayList<Card>();
		for (Suit suit : Suit.values()) {
			for (Value value : Value.values()) {
				if (value != Value.KING) {
					cards.add(new Card(suit, value, Type.NOT_WILD));
				}
				else cards.add(new Card(suit, value, Type.WILD));
			}
		}
		for (int i = 0; i < 1; i++) {
			cards.add(new Joker(Color.BLACK));
			cards.add(new Joker(Color.RED));
		}
	
		return cards;
	}
	
	/**
	 * This method takes an ArrayList<Card> and concats it to another.
	 * @param toConcat
	 */
	public void merge(ArrayList<Card> toConcat) {
		cards.addAll(toConcat);
	}
	
	/**
	 * toString method
	 * @return a string version of a deck
	 */
	public String getDeck() {
		return cards.toString();
	}
}
