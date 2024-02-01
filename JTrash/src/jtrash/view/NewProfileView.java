package jtrash.view;
// NewProfileView.java
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class NewProfileView extends JFrame {
    private JTextField usernameTextField;
    private JLabel profilePicLabel;
    private JButton chooseImageButton;
    private JButton saveButton;

    public NewProfileView() {
        setTitle("New Profile");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        usernameTextField = new JTextField(20);
        profilePicLabel = new JLabel();
        chooseImageButton = new JButton("Choose Image");
        saveButton = new JButton("Save");

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(usernameTextField)
                        .addComponent(profilePicLabel)
                        .addComponent(chooseImageButton)
                        .addComponent(saveButton)
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(usernameTextField)
                        .addComponent(profilePicLabel)
                        .addComponent(chooseImageButton)
                        .addComponent(saveButton)
        );

        chooseImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Images", "jpg", "jpeg", "png", "gif");
                fileChooser.setFileFilter(filter);
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    ImageIcon imageIcon = new ImageIcon(selectedFile.getAbsolutePath());
                    Image image = imageIcon.getImage();
                    Image scaledImage = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                    ImageIcon scaledImageIcon = new ImageIcon(scaledImage);
                    profilePicLabel.setIcon(scaledImageIcon);
                }
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameTextField.getText();
                ImageIcon profilePicIcon = (ImageIcon) profilePicLabel.getIcon();
                if (username.isEmpty() || profilePicIcon == null) {
                    JOptionPane.showMessageDialog(null, "Please enter a username and choose a profile picture.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    saveData(username, profilePicIcon);
                }
            }
        });

        setVisible(true);
    }

    private void saveData(String username, ImageIcon profilePicIcon) {
        try {
            String filename = "profile.txt";
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            writer.write("Username: " + username + "\n");
            writer.write("Profile Picture: " + profilePicIcon.getDescription() + "\n");
            writer.close();
            JOptionPane.showMessageDialog(null, "Profile saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error saving profile.", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }


}
