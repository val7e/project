/**
 * 
 */
package jtrash.model.cards;

/**
 * @author val7e
 *
 */
public class Joker extends Card {
	private Color color;
	
	/**
	 * Default constructor: it creates a Joker card with the color in input and type WILD.
	 * @param color
	 */
	public Joker (Color color, boolean isFaceUp) {
		this.color = color;
		this.type = Type.WILD;
		this.value = Value.KING; // because it has the same value then KING 
		this.isFaceUp = isFaceUp;
	}

	/**
	 * @return the color of the Joker card.
	 */
	public Color getColor() {
		return color;
	}
	
	@Override
	public String toString() {
		return color + "_JOKER";
	}
}
