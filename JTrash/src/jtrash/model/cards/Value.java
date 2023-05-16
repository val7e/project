/**
 * 
 */
package jtrash.model.cards;

/**
 * @author val7e
 *
 */
public enum Value {
	ASSO(1),
	DUE(2), 
	TRE(3), 
	QUATTRO(4), 
	CINQUE(5), 
	SEI(6), 
	SETTE(7), 
	OTTO(8), 
	NOVE(9),
	DIECI(10),
	JACK(11),
	QUEEN(11),
	KING(0),
	JOKER(0);
	
	private final int number;
	
	private Value(int number) {
		this.number = number;
	}
	
	public int getInt() {
		return number;
	}
}

