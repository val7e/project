package jtrash.model.game;

import java.util.ArrayList;

import jtrash.model.cards.Card;
import jtrash.model.players.Player;

public interface Observer {
	
	void update(ArrayList<Card> hand);
	
	void onWildcardDrawn(ArrayList<Card> hand);
	
	void onWinnerScoreUpdate(Player player);
	
	
}
