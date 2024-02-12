package jtrash.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MenuFrame extends JFrame {
    /**
     * Immagine di Background del pannello
     */
	private JPanel menuPanel;
	private JLabel menuTitle, gameMode;
	private JButton profileDialog, player2Button, player3Button, player4Button;
	private NewProfileDialog profile;
	private NewProfileListener newProfileListener;
    
    public MenuFrame() {
    	displayMenu();       
    }
    
    private void displayMenu() {
    	setTitle("JTrash Menu");
    	setSize(800, 600);
    	setLocationRelativeTo(null);
    	setResizable(false);
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	
    	menuPanel = new JPanel(new GridBagLayout());
    	menuPanel.setBackground(new Color(63, 115, 85));
    	
        menuTitle = new JLabel("JTrash Menu");
        menuTitle.setFont(new Font("Arial", Font.BOLD, 30));
        menuTitle.setForeground(Color.WHITE);

        gameMode = new JLabel("and then choose game mode:");
        gameMode.setFont(new Font("Arial", Font.BOLD, 20));
        gameMode.setForeground(Color.WHITE);
        
        GridBagConstraints titleConstraints = new GridBagConstraints();
        titleConstraints.gridx = 1;
        titleConstraints.gridy = 0;
        titleConstraints.insets = new Insets(10, 10, 10, 10);
        menuPanel.add(menuTitle, titleConstraints);
        
        Dimension buttonDimension = new Dimension(200, 50);
        
        profileDialog = new JButton("Create a new profile");
        profileDialog.setFont(new Font("Arial", Font.PLAIN, 18));
        profileDialog.setBackground(Color.BLACK);
        profileDialog.setForeground(Color.WHITE);
        profileDialog.setFocusPainted(false); // Remove the button border
        profileDialog.setPreferredSize(buttonDimension);
        
        player2Button = new JButton("2 Player");
        player2Button.setFont(new Font("Arial", Font.PLAIN, 18));
        player2Button.setBackground(Color.BLACK);
        player2Button.setForeground(Color.WHITE);
        player2Button.setFocusPainted(false); // Remove the button border
        player2Button.setPreferredSize(buttonDimension);
        
        player3Button = new JButton("3 Players");
        player3Button.setFont(new Font("Arial", Font.PLAIN, 18));
        player3Button.setBackground(Color.BLACK);
        player3Button.setForeground(Color.WHITE);
        player3Button.setFocusPainted(false); // Remove the button border
        player3Button.setPreferredSize(buttonDimension);
        
        player4Button = new JButton("4 Players");
        player4Button.setFont(new Font("Arial", Font.PLAIN, 18));
        player4Button.setBackground(Color.BLACK);
        player4Button.setForeground(Color.WHITE);
        player4Button.setFocusPainted(false); // Remove the button border
        player4Button.setPreferredSize(buttonDimension);

        // Add components to a panel

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding

        gbc.gridx = 1;
        gbc.gridy = 1;
        
        gbc.gridwidth = 1;
        menuPanel.add(profileDialog, gbc);

        gbc.gridy = 2;
        menuPanel.add(gameMode, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        menuPanel.add(player2Button, gbc);

        gbc.gridx++;
        menuPanel.add(player3Button, gbc);

        gbc.gridx++;
        menuPanel.add(player4Button, gbc);

        // Add the panel to the frame
        add(menuPanel);

        setVisible(true);   
        
        
        profileDialog.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent e) {
            	System.out.println("dialog profilo aperto");
            	new NewProfileDialog();
            	
            		String[] data = openNewProfileDialog();
            		System.out.println(data);
            		if (data != null && data.length == 2) {
            			newProfileListener.newProfileDataEntered(data[0], data[1]);
            		}
            	
        	}
        });
    }
    
    
    
    private String[] openNewProfileDialog() {
    	NewProfileDialog dialog = new NewProfileDialog();
    	dialog.setVisible(true);
    	String username = dialog.getUsername();
    	String avatar = dialog.getAvatar();
    	return new String[] {username, avatar};
    }
    
    public void setNewProfileListener(NewProfileListener listener) {
        this.newProfileListener = listener;
    }
    
    public NewProfileDialog profile() {
    	return profile;
    }

}