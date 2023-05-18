package jtrash.model.cards;

public class Card {
	protected Value value;
	private Suit suit;
	protected Type type;
	protected boolean isFaceUp;
	
	/**
	 * Card costructor without parameters.
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
	 * @return the isFaceUp
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
		return suit + "_" + value + "_" + type;
	}

	public int getIntValue() {
		return this.getValue().getInt();
	}
	
}
