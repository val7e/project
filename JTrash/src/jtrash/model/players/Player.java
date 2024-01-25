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
	private int level;
	private int gamesWon;
	private int gamesLost;
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
        this.level = 0;
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
  
	public void incrementLevel(){
		this.level++;
	}

    public int getLevel() {
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
	}
	
	public void addGamesLost() {
		this.gamesLost++;	
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
		return nickname + ", " + avatar + ", " + level + ", " + gamesWon + ", " + gamesLost + ", Bot: " + isBot;
	}
	
}
