package jtrash.model.game;

import java.util.ArrayList;
import java.util.List;

import jtrash.model.cards.Card;
import jtrash.model.players.Player;

public class Observable {
	
	private List<Observer> observers = new ArrayList<Observer>();
	
	public void addObserver(Observer observer) {
		observers.add(observer);
	}
	
	public void removeObserver(Observer observer) {
		observers.remove(observer);
	}
	
	public void notifyObserversOnWildcardDrawn(ArrayList<Card> hand) {
		for (Observer observer : observers) {
			observer.onWildcardDrawn(hand);
		}
	}
	
	public void notifyObserversOnWinnerScoreUpdate(Player winner) {
		for (Observer observer : observers) {
			observer.onWinnerScoreUpdate(winner);
		}
	}
}
