package jtrash.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GameView extends JFrame {
//    private JLabel welcomeLabel;
//    private JLabel usernameLabel;
//    private JTextField usernameField;
//    private JButton player2Button;
//    private JButton player3Button;
//    private JButton player4Button;
    private JButton startButton;
    private JButton newProfileButton;
    private JButton pickExistingButton; 
    private JPanel profilePanel;

    public GameView() {
    	 // Set up the frame
        setTitle("JTrash Card Game");
        setSize(800, 600);  // Adjust size as needed
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame

        // Set green background color
        getContentPane().setBackground(new Color(63, 115, 85));

        // Create components for the initial window
        JLabel welcomeLabel = new JLabel("Welcome to JTrash!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setForeground(Color.WHITE);
        
        startButton = new JButton("START");
        startButton.setFont(new Font("Arial", Font.PLAIN, 18));
        startButton.setBackground(Color.BLACK);
        startButton.setForeground(Color.WHITE);
        startButton.setFocusPainted(false); // Remove the button border
        
        newProfileButton = new JButton("New Profile");
        profilePanel = new JPanel();

        profilePanel.add(newProfileButton);
        profilePanel.setVisible(false);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(startButton)
                        .addComponent(profilePanel)
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(startButton)
                        .addComponent(profilePanel)
        );

        setVisible(true);
    }

//        // Create components for the homepage
//        welcomeLabel = new JLabel("Welcome to JTrash!");
//        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
//        usernameLabel = new JLabel("Enter your username:");
//        usernameField = new JTextField(20);
//        player2Button = new JButton("2 Player");
//        player3Button = new JButton("3 Players");
//        player4Button = new JButton("4 Players");
//
//        // Add components to a panel
//        JPanel homePanel = new JPanel(new GridBagLayout());
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.insets = new Insets(10, 10, 10, 10); // Padding
//
//        gbc.gridx = 0;
//        gbc.gridy = 0;
//        gbc.gridwidth = 2;
//        homePanel.add(welcomeLabel, gbc);
//
//        gbc.gridy++;
//        gbc.gridwidth = 1;
//        homePanel.add(usernameLabel, gbc);
//
//        gbc.gridx++;
//        homePanel.add(usernameField, gbc);
//
//        gbc.gridx = 0;
//        gbc.gridy++;
//        homePanel.add(player2Button, gbc);
//
//        gbc.gridx++;
//        homePanel.add(player3Button, gbc);
//
//        gbc.gridx++;
//        homePanel.add(player4Button, gbc);
//
//        // Add the panel to the frame
//        add(homePanel);

    
 // Getter method for the start button
    public JButton getStartButton() {
        return startButton;
    }
    
    public void addStartButtonListener(ActionListener listener) {
        startButton.addActionListener(listener);
    }

    public void addNewProfileButtonListener(ActionListener listener) {
        newProfileButton.addActionListener(listener);
    }

    public void showNewProfileButton() {
        newProfileButton.setVisible(true);
    }

    public void hideNewProfileButton() {
        newProfileButton.setVisible(false);
    }

    public void showProfilePanel() {
        profilePanel.setVisible(true);
    }

    public void hideProfilePanel() {
        profilePanel.setVisible(false);
    }
//    // Getter method for the welcome label
//    public JLabel getWelcomeLabel() {
//        return welcomeLabel;
//    }
//    
//    // Getter methods for accessing components
//    public JTextField getUsernameField() {
//        return usernameField;
//    }
//
//    public JButton getPlayer2Button() {
//        return player2Button;
//    }
//
//    public JButton getPlayer3Button() {
//        return player3Button;
//    }
//
//    public JButton getPlayer4Button() {
//        return player4Button;
//    }
}
