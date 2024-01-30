package jtrash.model.game;

import java.util.ArrayList;

import jtrash.model.cards.Card;

public interface Observer {
	
	int onWildcardDrawn(ArrayList<Card> hand);
	
	void onScoreUpdate(int gamesWon, int gamesLost);
	
	
}
