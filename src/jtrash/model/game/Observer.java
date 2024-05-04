package jtrash.model.game;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import jtrash.model.cards.Card;
import jtrash.model.players.Player;

/**
 * The Observer class.
 * @author val7e
 *
 */
public interface Observer {
	
	void updateDiscardDeck(Card card);
	void updateDefaultDeck(Card card);
	void updatePlayersMap(LinkedHashMap<Player, ArrayList<Card>> map, Card card);
	
	int onWildcardDrawn();
	void notifyPlayersMap();
	void onWinnerScoreUpdate(Player player);
	
	void showNotification();

}
