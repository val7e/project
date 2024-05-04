/**
 * 
 */
package jtrash.model.cards;

/**
 * @author val7e
 *
 */
public enum Value {
	ACE(1),
	TWO(2), 
	THREE(3), 
	FOUR(4), 
	FIVE(5), 
	SIX(6), 
	SEVEN(7), 
	EIGHT(8), 
	NINE(9),
	TEN(10),
	JACK(12),
	QUEEN(12),
	KING(11);
	
	private final int number;
	
	private Value(int number) {
		this.number = number;
	}
	
	public int getInt() {
		return number;
	}
}

