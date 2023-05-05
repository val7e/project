/**
 * 
 */
package jtrash.model.players;

/**
 * @author val7e
 *
 */
public class Player {
	private final String nickname;
	private String avatar;
	private String level; //level of the player calculated by expPoints
	private int gamesWon;
	private int gamesLost;
	private int expPoints = 1; //incremented by gamesPlayed
	private int status; //status of the player in each game
	private boolean isBot = false;
	
	public boolean getIsBot() {
		return this.isBot;
	}

	public void setIsBot(boolean value) {
		this.isBot = value;
	}

    public Player(String nickname, String avatar, boolean isBot) {
        this.nickname = nickname;
        this.avatar = avatar;
        this.isBot = isBot;
        this.level = "Beginner";
    }
    
    /**
     * Constructor that takes only a String in input for when the data are taken from a file txt.
     * 
     */
    
    public Player(String data) {
    	String[] d = data.split(",");
    	this.nickname = d[0];
    }

    public String getNickname() {
        return nickname;
    }
    
    /**
	 * @param avatar the avatar to set
	 */
    public void setAvatar(String avatar) {
    	this.avatar = avatar;
    }
    
    public String getAvatar() {
    	return avatar;
    }
    
    /**
	 * This is method is invoked at the end of every game.
	 * ! check the values because this was set for UNO game !
	 * @param level the level to set
	 */
	private void setLevel() {
		if (expPoints >= 64) this.level = "Beginner";
		if (gamesWon > gamesLost) {
			if (expPoints >= 512) this.level = "Intermediate";
			if (expPoints >= 4096) this.level = "Advanced";
		}
	}

    public String getLevel() {
		return this.level;
	}
	
	public int getGamesWon() {
		return this.gamesWon;
	}
	
	public int getGamesLost() {
		return this.gamesLost;
	}
	
	public void addGamesWon() {
		this.gamesWon++;
		this.expPoints *= 2;
		setLevel();
	}
	
	public void addGamesLost() {
		this.gamesLost++;
		this.expPoints--;
		setLevel();
		
	}
	
	public int getExpPoints() {
		return this.expPoints;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return nickname + ", " + avatar + ", " + level + ", " + expPoints + ", " + gamesWon + ", " + gamesLost + ", Bot: " + isBot;
	}
	
}
