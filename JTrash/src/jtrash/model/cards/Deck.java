/**
 * 
 */
package jtrash.model.cards;

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
	 * A method that allows to draw an object Card from the deck.
	 * @return the drawn Card.
	 */
	Card drawCard();
	
	/**
	 * A method that allows to remove an object Card from the deck.
	 * @return the removed Card.
	 */
	boolean removeCard(Card card);
	
	/**
	 * A method that returns a string version of the deck.
	 * @return a string.
	 */
	String getDeck();
}
