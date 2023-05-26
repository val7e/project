/**
 * 
 */
package jtrash.model.cards;

import java.util.ArrayList;

/**
 * @author val7e
 *
 */
public interface Deck {
	/**
	 * A method that calculate the size of the deck.
	 * @return an integer of the size of the deck.
	 */
	int size();
	
	/**
	 * A method that checks if the deck is empty.
	 * @return  true if the deck is empty,
	 * 			false otherwise.
	 */
	boolean isEmpty();
	
	/**
	 * A method that allows to draw an object Card from the deck.
	 * @return the pulled out Card.
	 */
	Card drawCard();
	
	/**
	 * A method that allows to remove an object Card from the deck.
	 * @return the removed Card.
	 */
	boolean removeCard(Card card);
	
	/**
	 * A method that allows to add a card to a deck.
	 * @param card
	 */
	void add(Card card);
	
	/**
	 * A method that returns a string version of the deck.
	 * @return a string.
	 */
	String getDeck();
	
	/**
	 * Getter method for cards array.
	 * @return
	 */
	public ArrayList<Card> getCards();
}
