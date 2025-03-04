package dungeon;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TitleScreenPanel extends JPanel {
    private final Image backgroundImage;

    public TitleScreenPanel(ActionListener controller) {
        // Load the background image from the resources folder
        backgroundImage = new ImageIcon("quarter_project/game_icons/TitleScreenBackGround.jpg").getImage();


        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.BLACK); // Default background in case the image doesn't load
        setAlignmentX(Component.CENTER_ALIGNMENT);

        // Title Label
        JLabel titleLabel = new JLabel("Dungeon Adventure");
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);

        // Panel to hold buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);

        // Buttons
        Font buttonFont = new Font("Times New Roman", Font.BOLD, 14);
        Dimension buttonSize = new Dimension(200, 60);

        JButton startButton = createStyledButton("New Game", buttonFont, buttonSize, controller);
        JButton loadButton = createStyledButton("Load Game", buttonFont, buttonSize, controller);
        JButton exitButton = createStyledButton("Exit", buttonFont, buttonSize, controller);

        // Add buttons to panel
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(startButton);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(loadButton);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(exitButton);

        // Add elements to main panel
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(Box.createVerticalStrut(150));
        add(titleLabel);
        add(Box.createVerticalStrut(40));
        add(buttonPanel);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this); // Draw the image to fill the panel
        }
    }

    private JButton createStyledButton(String text, Font font, Dimension size, ActionListener listener) {
        JButton button = new JButton(text);
        button.setFont(font);
        button.setPreferredSize(size);
        button.setMaximumSize(size);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(listener);
        return button;
    }
}
