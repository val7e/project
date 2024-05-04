package jtrash.view;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.swing.*;
import javax.swing.border.Border;

import jtrash.model.cards.Card;
import jtrash.model.game.Observer;
import jtrash.model.players.Player;

/**
 * 
 * Actual game frame:
 * it has the game table, divided in panels, one for each player.
 * It plays the background audio.
 * It gets updates from the model (the Observable) to show changes based on the events of the game.
 * @author val7e
 *
 */
public class GameFrame extends JFrame implements Observer {
	
	private Color bg = new Color(63, 115, 85);
	
	private AudioManager audioTheme;
	private JButton defaultDeck;
	private JButton discardDeck;

    private final JLayeredPane tablePanel;
    


	private GridLayout rotated = new GridLayout(5, 2);
    
    private final JPanel[] playerPanels;
    private final JLabel[] playerLabels;
	private ArrayList<Player> players;
	
	private LinkedHashMap<Player, ArrayList<JLabel>> mapLabels;
	private ArrayList<JLabel> arrayCardsLabels;
	private JLabel cardLabel;
	private JLabel drawnCard; 


	/**
	 * Public constructor.
	 * @param numPlayers
	 */
	public GameFrame(int numPlayers) {
        setTitle("JTrash");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setSize(1300,750);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);
        

        tablePanel = new JLayeredPane();
        playerPanels = new JPanel[numPlayers];
        playerLabels = new JLabel[numPlayers];
        mapLabels = new LinkedHashMap<>();
        
        buildTable();
        buildDecksPanel();
        buildPlayerUser();

        switch (numPlayers) {
            case 2 -> build2PlayersGame();
            case 3 -> build3PlayersGame();
            case 4 -> build4PlayersGame();
        }

        // setting background audio
        audioTheme = AudioManager.getInstance();
        audioTheme.play("audioTracks/audioTheme.wav");
        

        getContentPane().add(tablePanel, BorderLayout.CENTER);
        setVisible(true);
    }

    private void build2PlayersGame() {
        buildNorthPlayerBot(1, "Jim");
    }

    private void build3PlayersGame() {
    	buildWestPlayerBot();
        buildNorthPlayerBot(2, "Pam");    
    }

    private void build4PlayersGame() {
        buildWestPlayerBot();
        buildNorthPlayerBot(2, "Pam");
        buildEastPlayerBot();
    }

    private void buildTable() {
        // Table Panel initialization
        tablePanel.setBounds(0,0,1286,750);
        tablePanel.setBackground(bg);
        tablePanel.setOpaque(true);
    }

    private void buildDecksPanel() {
    	discardDeck = new JButton();
    	discardDeck.setBounds(557, 300, 78, 114);
        tablePanel.add(discardDeck);

        defaultDeck = new JButton();
        defaultDeck.setIcon(new ImageIcon("cards/BACK.png"));
        defaultDeck.setBounds(645, 300, 78, 114);
        tablePanel.add(defaultDeck);
    }

    
    /**
     * Builds the player panel in which are going to be displayed the cards.
     * @param straight boolean, true: the panel has to be displayed straight, false the panel has to be rotated of 90 degrees.
     * @param username String, is added to the border of the panel.
     * @return the built playerPanel
     */
    private JPanel createPlayerPanel(boolean straight, String username) {
        JPanel playerPanel;
        if (straight)
            playerPanel = new JPanel(new GridLayout(2, 5));
        else
            playerPanel  = new JPanel(rotated);

        playerPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        playerPanel.setBackground(bg);
        
        Border border = BorderFactory.createTitledBorder(username);
        playerPanel.setBorder(border);
        return playerPanel;
    }
    
    /**
     * Builds the player label, giving each player the correct avatar.
     * user -> TO HANDLE
     * @param name of the player
     * @return the player label
     */
    private JLabel createPlayerLabel(int name) {
    	JLabel playerLabel = new JLabel();
    	ImageIcon jim = new ImageIcon("graphics/iconJim.png");
        ImageIcon pam = new ImageIcon("graphics/iconPam.png");
        ImageIcon dwight = new ImageIcon("graphics/iconDwight.png");
        ImageIcon user = new ImageIcon(GameView.getAvatar());
    	switch (name) {
    	case 0 -> playerLabel = new JLabel(user);
    	case 1 -> playerLabel = new JLabel(jim);
		case 2 -> playerLabel = new JLabel(pam);
		case 3 -> playerLabel = new JLabel(dwight);
    		}
		return playerLabel;
    }

    /**
     * Builds the playerUser panel.
     */
    private void buildPlayerUser() {
        playerPanels[0] = createPlayerPanel(true, GameView.getUsername());
        playerPanels[0].setBounds(420,430,440,250);
        this.tablePanel.add(playerPanels[0]);
        
        playerLabels[0] = createPlayerLabel(0);
        playerLabels[0].setBounds(344, 608, 70, 70);
        this.tablePanel.add(playerLabels[0]);
    }

    /**
     * Builds the panel for botPlayer1 (Jim) in a 2 players game.
     * Builds the panel for botPlayer2 (Pam) in a 3 players game.
     * @param index the index of the player in the playerPanels array.
     */
    private void buildNorthPlayerBot(int index, String botName) {
        playerPanels[index] = createPlayerPanel(true, botName);
        playerPanels[index].setBounds(420,30,440,250);
        this.tablePanel.add(playerPanels[index]);
        
        playerLabels[index] = createPlayerLabel(index);
        playerLabels[index].setBounds(344, 38, 70, 70);
        this.tablePanel.add(playerLabels[index]); 
    }

    /**
     * Builds the panel for botPlayer1 (Jim).
     */
    private void buildWestPlayerBot() {
        playerPanels[1] = createPlayerPanel(false, "Jim");
        playerPanels[1].setBounds(30,150,250,440);
        this.tablePanel.add(playerPanels[1]);
        
        playerLabels[1] = createPlayerLabel(1);
        playerLabels[1].setBounds(30, 79, 70, 70);
        this.tablePanel.add(playerLabels[1]);   
    }

    /**
     * Builds the panel for botPlayer3 (Dwight).
     */
    private void buildEastPlayerBot() {
        playerPanels[3] = createPlayerPanel(false, "Dwight");
        playerPanels[3].setBounds(1010,150,250,440);
        this.tablePanel.add(playerPanels[3]);
        
        playerLabels[3] = createPlayerLabel(3);
        playerLabels[3].setBounds(1186, 79, 70, 70);
        this.tablePanel.add(playerLabels[3]);   
    }

    
    
    
    public JPanel getPlayerPanel(int index) {
    	return this.playerPanels[index];
    }
    
    public void setTopCard(Card card) {
    	String path = card.getPath();
    	ImageIcon img = getScaledImage(path, false);
		discardDeck.setIcon(img);
    }
    
    /**
     * This method is invoked for scaling the card so it can be displayed correctly throughout the game.
     * @param path is a String in which is stored the card png path.
     * @param zoom is a boolean, it can be:
     * 		- true: to have a zoomed card, usually to be displayed in the drawnLabel
     * 		- false: to have a normal card, the standard size which is 78 x 114
     * 			
     * @return an ImageIcon of the card png
     */
    private ImageIcon getScaledImage(String path, boolean zoom) {
    	ImageIcon imageIcon = new ImageIcon(path); // load the image to a imageIcon
    	Image image = imageIcon.getImage(); // transform it
    	Image newimg;
    	if (zoom) newimg = image.getScaledInstance(144, 192, Image.SCALE_SMOOTH); // scale it the smooth way but zoomed 
    	else newimg = image.getScaledInstance(78, 114, Image.SCALE_SMOOTH); // scale it the smooth way  
    	imageIcon = new ImageIcon(newimg);  // transform it back
    	return imageIcon;
    }
    // ActionListener
    
    public void addDiscardDeckListener(ActionListener discardDeckListener) {
    	discardDeck.addActionListener(discardDeckListener);
    }
    
    public void addDefaultDeckListener(ActionListener defaultDeckListener) {
    	defaultDeck.addActionListener(defaultDeckListener);
    }
    
    
    public void initDiscardDeck(Card card) {
    	String path = card.getPath();
		ImageIcon img = getScaledImage(path, false);
		discardDeck.setIcon(img);
    }
    
    public void stop() {
    	audioTheme.stopAll();
    	// create a label with winner name!
    }
    
    public void updateDrawnLabel (Card card) {
    	String path = card.getPath();
		ImageIcon img = getScaledImage(path, true);
		this.drawnCard = new JLabel(img);
		this.drawnCard.setBounds(350,265,144,192);
		this.drawnCard.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		this.drawnCard.setBackground(Color.WHITE);
		this.drawnCard.setVisible(true);
        tablePanel.add(this.drawnCard, 1);
    }
    
    @Override
	public void updateDiscardDeck(Card card) {
    	ImageIcon img = getScaledImage("/JTrash/cards/BLANK.png", false);
    	if (card != null) img = getScaledImage(card.getPath(), false);
    	discardDeck.setIcon(img);
	}

	@Override
	public void updateDefaultDeck(Card card) {
		String path = card.getPath();
		ImageIcon img = getScaledImage(path, true);
		card.setFaceUp(true);
		this.drawnCard = new JLabel(img);
		this.drawnCard.setBounds(350,265,144,192);
		this.drawnCard.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		this.drawnCard.setBackground(Color.WHITE);
		this.drawnCard.setVisible(true);
        tablePanel.add(this.drawnCard, 1);
	}

	@Override
	public void updatePlayersMap(LinkedHashMap<Player, ArrayList<Card>> map, Card discardCard) {
		if (discardCard!=null) {
			updateDiscardDeck(discardCard);
		}
		int i = 0;
		players = new ArrayList<Player>();
		for (Player player : map.keySet()) {
			players.add(player);
			System.out.println(player +" creo mappa");
			ArrayList<Card> hand = map.get(player);
			this.arrayCardsLabels = new ArrayList<>();
			for (Card card : hand) {
				this.cardLabel = new JLabel();
				if (card.isFaceUp()) {
					System.out.println(card + "" + card.isFaceUp() + card.getPath());
					this.cardLabel.setIcon(getScaledImage(card.getPath(), false));
				} else this.cardLabel.setIcon(getScaledImage("cards/BACK.png", false));
				this.arrayCardsLabels.add(this.cardLabel);
				mapLabels.put(player, arrayCardsLabels);
			}
			System.out.println(player + " sto per darti le carte");
			playerPanels[i].removeAll();
			for (JLabel card : arrayCardsLabels) {
				playerPanels[i].add(card);
			}
			i++;
		}
	}
	
	public void updateAfterDraw(LinkedHashMap<Player, ArrayList<Card>> map, Card discardCard) {
		if (discardCard!=null) {
			updateDiscardDeck(discardCard);
		}
		int i = 0;
		players = new ArrayList<Player>();
		for (Player player : map.keySet()) {
			players.add(player);
			System.out.println(player +" creo mappa");
			ArrayList<Card> hand = map.get(player);
			this.arrayCardsLabels = new ArrayList<>();
			for (Card card : hand) {
				this.cardLabel = new JLabel();
				if (card.isFaceUp()) {
					System.out.println(card + "" + card.isFaceUp() + card.getPath());
					this.cardLabel.setIcon(getScaledImage(card.getPath(), false));
				} else this.cardLabel.setIcon(getScaledImage("cards/BACK.png", false));
				this.arrayCardsLabels.add(this.cardLabel);
				mapLabels.put(player, arrayCardsLabels);
			}
			System.out.println(player + " sto per darti le carte");
			playerPanels[i].removeAll();
			for (JLabel card : arrayCardsLabels) {
				playerPanels[i].add(card);
			}
			i++;
		}
	}

	public JLabel getDrawnCard() {
		return drawnCard;
	}
	
	@Override
	public int onWildcardDrawn() {
		return 0;
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onWinnerScoreUpdate(Player player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showNotification() {
		// TODO Auto-generated method stub
		System.out.println("frame riceve notifica");
		
	}

	@Override
	public void notifyPlayersMap() {
		// TODO Auto-generated method stub
		
	}

}