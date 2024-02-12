package jtrash.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GameView extends JFrame {
	
	private JWindow win;
	private JPanel contentPanel;
	private JLabel welcomeLabel, startGameGif;
    private JButton startButton;
    protected MenuFrame menu;
    protected NewProfileDialog profile;
    protected GameFrame game;
    
    public GameView() {
    	displayStartFrame();
    }

    
    private void displayStartFrame() {
    	 // Set up the frame
    	setTitle("JTrash Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        
        contentPanel = new JPanel(new GridBagLayout());
        Dimension buttonDimension = new Dimension(200, 50);

        // Set green background color
        contentPanel.setBackground(new Color(63, 115, 85));

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
        
        GridBagConstraints constraints = new GridBagConstraints();

        // Create components for the initial window
        welcomeLabel = new JLabel("Welcome to JTrash!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 30));
        welcomeLabel.setForeground(Color.WHITE);

        ImageIcon img = new ImageIcon("graphics/game.gif");
        startGameGif = new JLabel();
        startGameGif.setIcon(img);
        
        startButton = new JButton("START");
        startButton.setFont(new Font("Arial", Font.PLAIN, 18));
        startButton.setBackground(Color.BLACK);
        startButton.setForeground(Color.WHITE);
        startButton.setFocusPainted(false); // Remove the button border
        startButton.setPreferredSize(buttonDimension);
        

        // Set constraints for titleLabel
        constraints.gridx = 0; // Column 0
        constraints.gridy = 0; // Row 0
        constraints.anchor = GridBagConstraints.CENTER; // Center horizontally
        constraints.weightx = 1.0; // Expand horizontally
        constraints.insets = new Insets(0, 0, 10, 0); // Add space at the bottom
        
        contentPanel.add(welcomeLabel, constraints);

        // Set constraints for imageLabel
        constraints.gridx = 0; // Column 0
        constraints.gridy = 1; // Row 1
        constraints.anchor = GridBagConstraints.CENTER; // Center horizontally
        constraints.insets = new Insets(0, 0, 10, 0); // Add space at the bottom
        contentPanel.add(startGameGif, constraints);
        
        // Set constraints for button
        constraints.gridx = 0; // Column 0
        constraints.gridy = 2; // Row 2
        constraints.anchor = GridBagConstraints.CENTER; // Center horizontally
        constraints.weightx = 0.0; // Do not expand horizontally
        constraints.insets = new Insets(10, 0, 0, 0); // Add space at the top
        
        contentPanel.add(startButton, constraints);
        getContentPane().add(contentPanel);

    }

    
 // Getter method for the start button
    public MenuFrame getMenuFrame() {
        return menu;
    }
    
    public GameFrame getGameFrame() {
    	return game;
    }
    
    
    
    public void addStartButtonListener(ActionListener listener) {
        startButton.addActionListener(listener);
    }
    
    public void showMenuFrame() {
    	menu = new MenuFrame();
    	
    	
    	
    }
    
    
}
