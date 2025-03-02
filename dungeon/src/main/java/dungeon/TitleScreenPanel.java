package dungeon;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;


public class TitleScreenPanel extends JPanel {
    private final JButton[] buttons;

    public TitleScreenPanel(ActionListener controller) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.BLACK);
        setAlignmentX(Component.CENTER_ALIGNMENT);

        // Title Label (Centered)
        JLabel titleLabel = new JLabel("Dungeon-Adventure");
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);

        // Panel to hold buttons vertically (Centered)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);

        // Buttons with Times New Roman Font and Fixed Size
        Font buttonFont = new Font("Times New Roman", Font.BOLD, 14);
        Dimension buttonSize = new Dimension(200, 60);

        JButton startButton = createStyledButton("New Game", buttonFont, buttonSize);
        JButton loadButton = createStyledButton("Load Game", buttonFont, buttonSize);
        JButton exitButton = createStyledButton("Exit", buttonFont, buttonSize);

        buttons = new JButton[]{startButton, loadButton, exitButton};

        // Add buttons with spacing
        buttonPanel.add(Box.createVerticalStrut(10)); // Space at the top
        buttonPanel.add(startButton);
        buttonPanel.add(Box.createVerticalStrut(10)); // Space between buttons
        buttonPanel.add(loadButton);
        buttonPanel.add(Box.createVerticalStrut(10)); // Space between buttons
        buttonPanel.add(exitButton);

        // Add action listeners to each button (except start button for now)
        for (JButton button : buttons) {
            if (button != startButton) {
                button.addActionListener(controller);  // controller for Load Game & Exit
            }
        }

        // Add action listener for startButton to show the confirmation dialog
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int response = JOptionPane.showConfirmDialog(null, "Are you sure?", 
                    "Start New Game", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                // Only start the game if the user clicks "Yes"
                if (response == JOptionPane.YES_OPTION) {
                    // Show hero selection dialog after confirming to start
                    showHeroSelectionDialog();
                }
                // If "No" is selected, nothing happens, no game starts
            }
        });

        // Center the title and buttons vertically and add them to the main panel
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);  // Add this line to ensure the title is centered horizontally.
        add(Box.createVerticalStrut(150));  // Space before the title
        add(titleLabel);
        add(Box.createVerticalStrut(40));  // Space after the title
        add(buttonPanel);
    }

    private JButton createStyledButton(String text, Font font, Dimension size) {
        JButton button = new JButton(text);
        button.setFont(font);
        button.setPreferredSize(size);
        button.setMaximumSize(size); // Prevents stretching
        button.setAlignmentX(Component.CENTER_ALIGNMENT); // Aligns center in BoxLayout
        return button;
    }

    private void showHeroSelectionDialog() {
        // Create radio buttons for hero selection
        JRadioButton warriorButton = new JRadioButton("Warrior");
        JRadioButton thiefButton = new JRadioButton("Thief");
        JRadioButton priestessButton = new JRadioButton("Priestess");
    
        // Group the radio buttons so only one can be selected
        ButtonGroup heroGroup = new ButtonGroup();
        heroGroup.add(warriorButton);
        heroGroup.add(thiefButton);
        heroGroup.add(priestessButton);
    
        // Show the dialog with the radio buttons
        JPanel heroPanel = new JPanel();
        heroPanel.add(warriorButton);
        heroPanel.add(thiefButton);
        heroPanel.add(priestessButton);
    
        int option = JOptionPane.showConfirmDialog(
                null, heroPanel, "Which Hero should Start?", 
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    
        // After the user confirms, handle their choice
        if (option == JOptionPane.OK_OPTION) {
            // Check if a hero was selected
            if (!warriorButton.isSelected() && !thiefButton.isSelected() && !priestessButton.isSelected()) {
                JOptionPane.showMessageDialog(null, "[Please Select a Hero First]", 
                                              "Error", JOptionPane.ERROR_MESSAGE);
                // Show the dialog again if no hero was selected
                showHeroSelectionDialog();
            } else {
                if (warriorButton.isSelected()) {
                    System.out.println("Warrior selected!");
                    // Initialize the game with Warrior (call startNewGame)
                    startNewGame();
                } else if (thiefButton.isSelected()) {
                    System.out.println("Thief selected!");
                    // Initialize the game with Thief (call startNewGame)
                    startNewGame();
                } else if (priestessButton.isSelected()) {
                    System.out.println("Priestess selected!");
                    // Initialize the game with Priestess (call startNewGame)
                    startNewGame();
                }
            }
        } else {
            System.out.println("Hero selection canceled.");
            // Handle case if the user cancels the hero selection
        }
    }

    private void startNewGame() {
        // Your game start logic here
        System.out.println("Starting a new game...");
        // For example, transition to the game screen, initialize the game, etc.
    }

    public JButton getStartButton() { return buttons[0]; }
    public JButton getLoadButton() { return buttons[1]; }
    public JButton getExitButton() { return buttons[2]; }
}
