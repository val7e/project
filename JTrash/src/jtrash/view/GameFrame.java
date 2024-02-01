package jtrash.view;

import java.awt.Color;

import javax.swing.*;

public class GameFrame {
    private JFrame frame;
    private JLabel label1;

    public GameFrame() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(500, 500, 600, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(Color.CYAN);
        label1 = new JLabel("Miao!");
        label1.setForeground(Color.RED);
        frame.add(label1);
        
        frame.getContentPane().setBackground(Color.GREEN);
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }

    public JFrame getFrame() {
        return frame;
    }
}