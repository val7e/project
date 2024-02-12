package jtrash.view;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

public class ProfileSelectionView extends JFrame {

    private JLabel labelTitle;
    private JLabel labelDescription;
    private JLabel labelImage;
    private JRadioButton radioButtonNewProfile;
    private JRadioButton radioButtonExistingProfile;
    private JButton buttonContinue;
    

    public ProfileSelectionView() {
        super("Trash Profile Selection");

        labelTitle = new JLabel("Select a profile");
        labelTitle.setFont(new Font("Arial", Font.BOLD, 24));

        labelDescription = new JLabel("Do you want to create a new profile or pick an existing one?");

        radioButtonNewProfile = new JRadioButton("Create a new profile");
        radioButtonExistingProfile = new JRadioButton("Pick an existing profile");

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(radioButtonNewProfile);
        buttonGroup.add(radioButtonExistingProfile);

        buttonContinue = new JButton("Continue");
//        labelImage = new JLabel(new ImageIcon(image));
//        getContentPane().add(labelImage, BorderLayout.CENTER);


        // Aggiungi i componenti al frame
        getContentPane().add(labelTitle, BorderLayout.NORTH);
        getContentPane().add(labelDescription, BorderLayout.CENTER);
        getContentPane().add(radioButtonNewProfile, BorderLayout.WEST);
        getContentPane().add(radioButtonExistingProfile, BorderLayout.EAST);
        getContentPane().add(buttonContinue, BorderLayout.SOUTH);

        // Imposta le dimensioni del frame
        setSize(300, 200);
        setVisible(true);
    }
}
