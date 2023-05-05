package jtrash.model.cards;

public class Card {
	protected Value value;
	private Suit suit;
	protected Type type;
	
	public Card() {
		
	}
	public Card(Suit suit, Value value, Type type) {
		this.suit = suit;
		this.value = value;
		this.type = type;
	}
	
	
	/**
	 * @return the value
	 */
	public Value getValue() {
		return value;
	}
	/**
	 * @return the suit
	 */
	public Suit getSuit() {
		return suit;
	}
	
	/**
	 * @return the type
	 */
	public Type getType() {
		return type;
	}
	
	@Override
	public String toString() {
		return suit + "_" + value + "_" + type;
	}
	
}
