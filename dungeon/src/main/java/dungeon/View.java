package dungeon;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class View {
    private final JFrame frame;
    private final JPanel mainPanel;
    private final CardLayout cardLayout;
    private final TitleScreenPanel titleScreen;
    private final JPanel dungeonPanel;
    private static int currentRow = 0;
    private static int currentCol = 0;
    private static JButton[][] rooms = new JButton[5][5];
    private String selectedHero = "";  // To store the selected hero

    public View() {
        frame = new JFrame("Dungeon Adventure Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - frame.getWidth()) / 2;
        int y = (screenSize.height - frame.getHeight()) / 2;
        frame.setLocation(x, y);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Title screen setup
        titleScreen = new TitleScreenPanel(new TitleScreenController());
        mainPanel.add(titleScreen, "TitleScreen");

        // Dungeon grid setup (initially empty, will be initialized later)
        dungeonPanel = new JPanel(new GridLayout(5, 5));
        mainPanel.add(dungeonPanel, "Dungeon");

        frame.add(mainPanel);
        cardLayout.show(mainPanel, "TitleScreen"); // Show title screen first
        frame.setVisible(true);
    }

    // Initialize dungeon grid
    private void initializeDungeon() {
        dungeonPanel.removeAll(); // Clear panel before adding rooms
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                rooms[i][j] = new JButton("Room " + (i * 5 + j + 1));
                rooms[i][j].addActionListener(new RoomActionListener(i, j));
                dungeonPanel.add(rooms[i][j]);
            }
        }
        dungeonPanel.revalidate();
        dungeonPanel.repaint();
    }

    // Title screen controller to handle button clicks
    private class TitleScreenController implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            if (command.equals("New Game")) {
                // Show hero selection dialog
                showHeroSelectionDialog();
            } else if (command.equals("Load Game")) {
                // Implement load game functionality
                JOptionPane.showMessageDialog(frame, "Implement");
            } else if (command.equals("Exit")) {
                System.exit(0);
            }
        }
    }

    private void showHeroSelectionDialog() {
        // Create radio buttons for hero selection
        String[] heroes = {"Warrior", "Thief", "Priestess"};
        String selectedHero = (String) JOptionPane.showInputDialog(
                frame,
                "Which Hero should start in the Dungeon?",
                "Hero Selection",
                JOptionPane.QUESTION_MESSAGE,
                null,
                heroes,
                heroes[0]
        );
    
        if (selectedHero != null) {
            this.selectedHero = selectedHero; // Store the selected hero
            System.out.println(selectedHero + " selected!");
            
            // Initialize the dungeon grid only after hero selection
            initializeDungeon(); 
            
            // Switch to dungeon grid
            cardLayout.show(mainPanel, "Dungeon"); 
        } else {
            System.out.println("Hero selection canceled.");
        }
    }

    // ActionListener for dungeon rooms
    private static class RoomActionListener implements ActionListener {
        private int row, col;
        public RoomActionListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Clicked room at (" + row + ", " + col + ")");
        }
    }

    // Main method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(View::new);
    }
}
