/**
 * 
 */
package jtrash.model.players;

/**
 * @author val7e
 *
 */
public abstract class Player {
	private String nickname;
	private String avatar;
	private int level;
	private int gamesWon;
	private int gamesLost;
	private boolean isBot;
	
    
    public Player(String nickname, String avatar, boolean isBot) {
        this.nickname = nickname;
        this.avatar = avatar;
        this.level = 0;
        this.isBot = isBot;
    }

    // Common behavior for all players
    public String getNickname() {
        return nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public int getGamesWon() {
        return gamesWon;
    }

    public int getGamesLost() {
        return gamesLost;
    }

    public int getLevel() {
        return level;
    }

    public boolean isBot() {
        return isBot;
    }

	public void incrementLevel(){
		this.level++;
	}
	
	public void addGamesWon() {
		this.gamesWon++;
		incrementLevel();
	}
	
	public void addGamesLost() {
		this.gamesLost++;	
	}
	
	public int getGamesPlayed() {
		return gamesLost+gamesWon;
	}
	
	@Override
	public String toString() {
		return nickname + ", " + avatar + ", " + level + ", " + gamesWon + ", " + gamesLost + ", Bot: " + isBot;
	}
	
}
