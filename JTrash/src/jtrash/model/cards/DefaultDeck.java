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
public class DefaultDeck implements Deck {
	private final ArrayList<Card> cards;
	
	/**
	 * Deck 1 constructor: builds a deck of 54 cards.
	 */
	public DefaultDeck() {
		this.cards = buildDeck();
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
					cards.add(new Card(suit, value, Type.NOT_WILD, false));
				}
				else cards.add(new Card(suit, value, Type.WILD, false));
			}
		}
		for (int i = 0; i < 1; i++) {
			cards.add(new Joker(Color.BLACK, false));
			cards.add(new Joker(Color.RED, false));
		}
	
		return cards;
	}
	
	/**
	 * A public method that is invoked inside calculateDecks in the Game class.
	 * It takes an ArrayList<Card> and concatenates it to another.
	 * @param toConcat
	 */
	public void merge(ArrayList<Card> toConcat) {
		this.cards.addAll(toConcat);
	}
	
	/**
	 * A public method that is invoked inside prepareGame in the Game class.
	 * It shuffles the cards.
	 */
	public void shuffleCards() {
		Collections.shuffle(cards);
	}
	
	@Override
	public int size() {
		return this.cards.size();
	}
	
	@Override
	public Card drawCard() {
		return this.cards.remove(0);
	}
	
	@Override
	public boolean removeCard(Card card) {
		return this.cards.remove(card);
	}
	
	@Override
	public String getDeck() {
		return this.cards.toString();
	}

}
