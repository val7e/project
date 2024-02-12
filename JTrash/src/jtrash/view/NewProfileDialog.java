package jtrash.view;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;

public class NewProfileDialog extends JDialog implements Serializable {
	private JPanel profilePanel;
	private JLabel usernameLabel, avatarLabel;
    private JTextField usernameTextField;
    private JButton chooseAvatarButton, saveButton;
    private ImageIcon imageIcon;
    private ActionListener saveActionListener;
    private String data0, data1;

    public NewProfileDialog() {
    	displayNewProfileDialog();
    }

    private void displayNewProfileDialog() {
    	 setTitle("Create a new profile");
         setSize(600, 400);
         setLocationRelativeTo(null);
         setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
         profilePanel = new JPanel(new GridBagLayout());
         profilePanel.setBackground(new Color(63, 115, 85));
 
         usernameLabel = new JLabel("Enter your username:");
         usernameLabel.setFont(new Font("Arial", Font.PLAIN, 18));
         usernameLabel.setForeground(Color.WHITE);
         usernameTextField = new JTextField(15);
         usernameTextField.setBounds(50,150,150,20);
         imageIcon = new ImageIcon("graphics/placeholder.png");
         
         avatarLabel = new JLabel(imageIcon);
         chooseAvatarButton = new JButton("Choose avatar");
         chooseAvatarButton.setFont(new Font("Arial", Font.PLAIN, 18));
         chooseAvatarButton.setBackground(Color.BLACK);
         chooseAvatarButton.setForeground(Color.WHITE);
         saveButton = new JButton("SAVE");
         saveButton.setFont(new Font("Arial", Font.PLAIN, 18));
         saveButton.setBackground(Color.BLACK);
         saveButton.setForeground(Color.WHITE);
         
         GridBagConstraints constraints = new GridBagConstraints();
         
         // Set constraints for usernameLabel
         constraints.gridx = 0; // Column 0
         constraints.gridy = 1; // Row 1
         constraints.anchor = GridBagConstraints.EAST; 
         constraints.insets = new Insets(0, 0, 10, 10);
         profilePanel.add(usernameLabel, constraints);
         
         // Set constraints for usernameTextField
         constraints.gridx = 2; // Column 2
         constraints.anchor = GridBagConstraints.WEST;
         constraints.insets = new Insets(0, 10, 10, 0);
         
         profilePanel.add(usernameTextField, constraints);

         // Set constraints for imageLabel
         constraints.gridx = 0; // Column 0
         constraints.gridy = 2; // Row 2
         constraints.anchor = GridBagConstraints.EAST;
         constraints.insets = new Insets(0, 0, 10, 45);
         profilePanel.add(avatarLabel, constraints);
         
         // Set constraints for chooseImageButton
         constraints.gridx = 2; // Column 2
         constraints.anchor = GridBagConstraints.WEST; 
         constraints.insets = new Insets(0, 10, 20, 50);
         profilePanel.add(chooseAvatarButton, constraints);
         
         // Set constraints for saveButton
         constraints.gridx = 1; // Column 1
         constraints.gridy = 4; // Row 3
         constraints.anchor = GridBagConstraints.CENTER; // Center horizontally
         constraints.weightx = 0.0; // Do not expand horizontally
         constraints.insets = new Insets(30, 0, 0, 0); // Add space at the top
         profilePanel.add(saveButton, constraints);
      
         getContentPane().add(profilePanel);
         chooseAvatarButton.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 JFileChooser fileChooser = new JFileChooser("C:\\Users\\val7e\\Pictures\\avatar");
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

         saveButton.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 String username = usernameTextField.getText();
                 if (username.isEmpty()) {
                     JOptionPane.showMessageDialog(null, "Please enter a username and choose a profile picture.", "Error", JOptionPane.ERROR_MESSAGE);
                 } else {
                     saveData(username, imageIcon);
                     data0  = getUsername();
                     data1 = getAvatar();
                     setVisible(false);
                     
                 }
             }
         });
         
         setVisible(true);
    }

    private void saveData(String username, ImageIcon avatarIcon) {
        try {
            String filename = "profile.txt";
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            writer.write("Username: " + username + "\n");
            writer.write("Profile Picture: " + avatarIcon.getDescription() + "\n");
            writer.close();
            JOptionPane.showMessageDialog(null, "Profile saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error saving profile.", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    
    public void addSaveButtonListener(ActionListener listener) {
        saveButton.addActionListener(listener);
    }
    public void setSaveActionListener(ActionListener listener) {
    	saveActionListener = listener;
    	saveButton.addActionListener(saveActionListener);
    }
    
    public String getUsername() {
        return usernameTextField.getText();
    }
    
    
    public String getAvatar() {
    	return imageIcon.getDescription();
    }
    


}
