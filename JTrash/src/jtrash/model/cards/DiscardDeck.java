/**
 * 
 */
package jtrash.model.cards;

import java.util.ArrayList;

/**
 * @author val7e
 *
 */
public class DiscardDeck implements Deck {
	private final ArrayList<Card> cards = new ArrayList<Card>();
	
	/**
	 * A public method that appends an object Card to the end of the list cards.
	 * @param card to append.
	 */
	public void add(Card card) {
		cards.add(card);
	}
	
	public Card peek() {
		return cards.get(size() - 1);
	}

	@Override
	public int size() {
		return this.cards.size();
	}
	
	@Override
	public boolean isEmpty() {
		if (size() == 0) return true;
		else return false;
	}

	/**
	 * Removes and returns the last element from the Discard Deck.
	 */
	@Override
	public Card drawCard() {
		return this.cards.remove(size() - 1);
	}
	// TO REMOVE
	@Override
	public boolean removeCard(Card card) {
		return this.cards.remove(card);
	}
	
	// TO REMOVE
	@Override
	public String getDeck() {
		return this.cards.toString();
	}
	
	@Override
	public ArrayList<Card> getCards(){
		return cards;
	}
	
}
