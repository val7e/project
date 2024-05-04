package jtrash.model.players;

public class PlayerUser extends Player {

	private static PlayerUser instance;

    // Private constructor to prevent instantiation from outside
    private PlayerUser(String nickname, String avatar) {
        super(nickname, avatar, false); // PlayerUser is not a bot
    }

    // Static method to get the single instance of HumanPlayer
    public static PlayerUser getInstance(String nickname, String avatar) {
        if (instance == null) {
            instance = new PlayerUser(nickname, avatar);
        }
        return instance;
    }
}
