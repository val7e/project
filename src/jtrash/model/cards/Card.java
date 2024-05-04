package jtrash.model.cards;

public class Card {
	protected Value value;
	private Suit suit;
	protected Type type;
	protected boolean isFaceUp;
	
	/**
	 * Card constructor without parameters.
	 */
	public Card() {
		
	}
	/**
	 * Card constructor.
	 * @param suit
	 * @param value
	 * @param type
	 * @param isFaceUp
	 */
	public Card(Suit suit, Value value, Type type, boolean isFaceUp) {
		this.suit = suit;
		this.value = value;
		this.type = type;
		this.setFaceUp(isFaceUp);
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
	
	/**
	 * Returns true if the card is face up,
	 * false if is face down.
	 */
	public boolean isFaceUp() {
		return isFaceUp;
	}
	/**
	 * @param isFaceUp the isFaceUp to set
	 */
	public void setFaceUp(boolean isFaceUp) {
		this.isFaceUp = isFaceUp;
	}

	
	@Override
	public String toString() {
		return suit + "_" + value;
	}
	
	/**
	 * Returns the value of the card.
	 * e.g. card Quadri 8 has int 8.
	 * @return the value of Card.
	 */
	public int getIntValue() {
		return this.getValue().getInt();
	}
	
	/**
	 * Returns the index of the card, meaning the value of the card less 1. 
	 * e.g. card Quadri 8 has index 7.
	 * @return the index of Card.
	 */
	public int getIndexValue() {
		int x = this.getValue().getInt();
		int y = 1;
		int z = x-y;
		return z;
	}
	
	/**
	 * Method to getting the path of each card.
	 * @return the path of a card.
	 */
	public String getPath() {
		return "cards/" + toString() + ".png";
	}
	
}
