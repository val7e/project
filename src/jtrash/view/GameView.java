package jtrash.view;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * The class of the view.
 * @author val7e
 *
 */
public class GameView extends JFrame {
	
	private JWindow win; // window for the welcome gif
	
	private Color bg = new Color(63, 115, 85);
	
	
	private JPanel welcomePanel;
	private JLabel welcomeLabel;
	
	private JPanel homePanel;
	private JLabel startGameGif;
	
	private JPanel buttonPanel; 
	private JButton startButton;
    
    private JPanel menuPanel;
    private JLabel hiLabel, gameMode, hiGif;
	private JButton profileButton, player2Button, player3Button, player4Button;
	
    private JDialog newProfileDialog;

	private JLabel usernameLabel, avatarLabel;
    private static JTextField usernameTextField;
    private static ImageIcon imageIcon;
    private JButton chooseAvatarButton, saveButton;
    
    
    /**
     * Public constructor.
     */
    public GameView() {
    	
    	displayWelcomeGif();
    	displayStartFrame();
    	displayMenuPanel();
    	
    }

    
    /**
     * A method that shows a JWindow with a welcome animation to the game.
     */
    private void displayWelcomeGif() {
    	// welcome gif, has a timeout
        win = new JWindow();
        win.setSize(540,450);
        win.setLocationRelativeTo(null);
        try {
            ImageIcon img = new ImageIcon("graphics/welcome.gif");
            JLabel welcomeGif = new JLabel();
            welcomeGif.setIcon(img);
            win.getContentPane().add(welcomeGif);
            win.setVisible(true);
            Thread.sleep(1590);
            win.setVisible(false);
        } catch (InterruptedException e) {
            System.out.println("Timeout.");
            e.printStackTrace();
        }
    }
     
    /**
     * A method that builds the start frame of the GUI.
     */
    private void displayStartFrame() {
    	setTitle("JTrash Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout(100,10));
        
        welcomePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20)); // works only if the ints are equals
        welcomeLabel = new JLabel("Welcome to JTrash!", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 30));
        welcomeLabel.setForeground(Color.WHITE);
        welcomePanel.setBackground(bg);
        welcomePanel.add(welcomeLabel);
        add(welcomePanel, BorderLayout.NORTH);
        
        homePanel = new JPanel(new FlowLayout(FlowLayout.CENTER,20, 20));
        homePanel.setBackground(bg);
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,20, 20));
        buttonPanel.setBackground(bg);
        
        ImageIcon img = new ImageIcon("graphics/game.gif");
        startGameGif = new JLabel();
        startGameGif.setIcon(img);
        startGameGif.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        startGameGif.addMouseListener(new MouseAdapter() {
        	 
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI("https://www.wikihow.com/Play-Trash"));
                } catch (IOException | URISyntaxException e1) {
                    e1.printStackTrace();
                }
            }
        });
 
        startButton = buttonBuilder(startButton, "START");
        
        homePanel.add(startGameGif);
        buttonPanel.add(startButton);
        
        getContentPane().setBackground(bg);
        add(homePanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
    }

    /**
     * A method that builds the menu panel that will be added to the start frame.
     */
    private void displayMenuPanel() {
    	menuPanel = new JPanel(new GridBagLayout());
    	GridBagConstraints c = new GridBagConstraints();
     	profileButton = buttonBuilder(profileButton, "Create a new profile");
     	hiLabel = new JLabel();
     	hiLabel.setFont(new Font("Arial", Font.BOLD, 20));
     	hiLabel.setForeground(Color.PINK);
     	ImageIcon img = new ImageIcon("graphics/hiuser.gif");
     	hiGif = new JLabel();
     	hiGif.setIcon(img);
     	gameMode = new JLabel("Now choose game mode:");
        gameMode.setFont(new Font("Arial", Font.BOLD, 20));
        gameMode.setForeground(Color.WHITE);
     	player2Button = buttonBuilder(player2Button, "2 players");
     	player3Button = buttonBuilder(player3Button, "3 players");
     	player4Button = buttonBuilder(player4Button, "4 players");
     	
     	c.anchor = GridBagConstraints.CENTER;
     	c.insets = new Insets(0, 0, 20, 0);
     	c.gridx = 1;
     	c.gridy = 0;
     	menuPanel.add(hiLabel, c);
     	hiLabel.setVisible(false);
     	c.gridy = 1;
     	menuPanel.add(hiGif, c);
     	hiGif.setVisible(false);
     	menuPanel.add(profileButton, c);
     	c.gridy = 2;
     	menuPanel.add(gameMode, c);
     	gameMode.setVisible(false);
     	c.gridx = 0;
     	c.gridy = 3;
     	menuPanel.add(player2Button, c);
     	player2Button.setVisible(false);
     	c.gridx = 1;
     	c.gridy = 3;
     	menuPanel.add(player3Button, c);
     	player3Button.setVisible(false);
     	c.gridx = 2;
     	c.gridy = 3;
     	menuPanel.add(player4Button, c);
     	player4Button.setVisible(false);
     	menuPanel.setBackground(bg);
     	getContentPane().setBackground(bg);
    }
    
    
    /**
     * A method that is invoked after the user has entered the information asked by the new profile dialog.
     */
	public void showMenuPanel() {
    	this.setTitle("JTrash Menu");
    	buttonPanel.remove(startButton);
    	homePanel.setVisible(false);
    	this.add(menuPanel);
    }
	
    /**
     * A method that shows 
     */
	public void openNewProfileDialog() {
		newProfileDialog = dialogBuilder(newProfileDialog,"New Profile");
    	newProfileDialog.setVisible(true);
	}
	
	public void gameMenu() {
		hiLabel.setText("Hi " + getUsername() + "!");
		hiLabel.setVisible(true);
		profileButton.setVisible(false);
		hiGif.setVisible(true);
		gameMode.setVisible(true);
		player2Button.setVisible(true);
		player3Button.setVisible(true);
		player4Button.setVisible(true);
	}
    
    /**
     * This method builds a button whenever is called.
     * @param button the object button
     * @param text the text of the button
     * @return the built button.
     */
    private JButton buttonBuilder(JButton button, String text) {
    	button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 18));
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false); // Remove the button border
        button.setPreferredSize(new Dimension(200, 50));
        return button;
    }
    
    /**
     * This method builds a new profile dialog.
     * @param dialog
     * @param title
     * @return the dialog.
     */
    private JDialog dialogBuilder(JDialog dialog, String title) {
    	dialog = new JDialog();
    	dialog.setTitle("Create a new profile");
    	dialog.setSize(600, 400);
    	dialog.setLocationRelativeTo(null);
    	dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    	dialog.setResizable(false);
        dialog.getRootPane().setOpaque(false);
        dialog.getContentPane().setBackground(bg);
        
        usernameLabel = new JLabel("Enter your username:");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        usernameLabel.setForeground(Color.WHITE);
        usernameTextField = new JTextField(15);
        usernameTextField.setMargin(new Insets(5,10,5,10));
        imageIcon = new ImageIcon("graphics/placeholder.png");
        
        avatarLabel = new JLabel(imageIcon);
        chooseAvatarButton = new JButton();
        chooseAvatarButton = buttonBuilder(chooseAvatarButton, "Choose avatar");
        saveButton = new JButton();
        saveButton = buttonBuilder(saveButton, "SAVE");

        dialog.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        
        // Set constraints for usernameLabel
        
        c.gridx = 0; // Column 0
        c.gridy = 1; // Row 1
        c.insets = new Insets(0, 0, 10, 10);
        dialog.add(usernameLabel, c);
        
        // Set constraints for usernameTextField
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2; // Column 2
        dialog.add(usernameTextField, c);

        // Set constraints for imageLabel
        c.gridx = 0; // Column 0
        c.gridy = 2; // Row 2
        dialog.add(avatarLabel, c);
        
        // Set constraints for chooseImageButton
        c.gridx = 2; // Column 2
        dialog.add(chooseAvatarButton, c);
        
        // Set constraints for saveButton
        c.gridx = 1; // Column 1
        c.gridy = 4; // Row 3
        c.anchor = GridBagConstraints.CENTER; // Center horizontally
        c.insets = new Insets(30, 0, 0, 0); // Add space at the top
        dialog.add(saveButton, c);
        
        // ActionListener for chooseAvatarButton
        chooseAvatarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser("C:\\Users\\val7e\\git\\project\\JTrash\\avatar");
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Images", "jpg", "jpeg", "png", "gif");
                fileChooser.setFileFilter(filter);
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    imageIcon = new ImageIcon(selectedFile.getAbsolutePath());
                    Image image = imageIcon.getImage();
                    Image scaledImage = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                    ImageIcon scaledImageIcon = new ImageIcon(scaledImage);
                    avatarLabel.setIcon(scaledImageIcon);
                }
            }
        });
        
        getContentPane().setBackground(bg);
        return dialog; 
        
    }

    // ActionListeners
    
    public void addStartButtonListener(ActionListener listenerForStartButton) {
        startButton.addActionListener(listenerForStartButton);
    }
    
	public void addProfileListener(ActionListener profileListener) {
		profileButton.addActionListener(profileListener);
	}
	
	public void addSaveButtonListener(ActionListener saveListener) {
		saveButton.addActionListener(saveListener);
	}
	
	public void addGame2Listener(ActionListener game2Listener) {
		player2Button.addActionListener(game2Listener);
	}
	
	public void addGame3Listener(ActionListener game3Listener) {
		player3Button.addActionListener(game3Listener);
	}
	
	public void addGame4Listener(ActionListener game4Listener) {
		player4Button.addActionListener(game4Listener);
	}
	
	// DA QUI IN POI METODI PER FAR PARLARE LA VIEW CON IL MODEL ==========================================================
	

	public JDialog getNewProfileDialog() {
		return newProfileDialog;
	}
	
	/** 
	 * A method that returns the username of the PlayerUser.
	 * @return the username of the PlayerUser
	 */
	public static String getUsername() {
	    return usernameTextField.getText();
	}   
	
	/**
	 * A method that return the avatar of the PlayerUser.
	 * @return the avatar of the PlayerUser
	 */
	public static String getAvatar() {
    	return imageIcon.getDescription();
	}
	
}
