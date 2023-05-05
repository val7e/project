package jtrash.model.cards;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;

import jtrash.model.game.Game;
import jtrash.model.players.Player;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ArrayList<Player> players = new ArrayList<Player>();
		
		Scanner inputPlayer = new Scanner(System.in);
		  
	    System.out.print("Enter your nickname please : ");
	    String username = inputPlayer.next();
	    Player user = new Player(username, "IconUser", true);
	    
		Player jim = new Player("Jim", "IconJim", false);
		Player pam = new Player("Pam", "IconPam", false);
		Player dwight = new Player("Dwight", "IconDwight", false);
		
		players.add(user);
		players.add(jim);
		players.add(pam);
		players.add(dwight);
		
		Game game = new Game(players.size(), players);	
		game.start();
		
		
	}
	
}
