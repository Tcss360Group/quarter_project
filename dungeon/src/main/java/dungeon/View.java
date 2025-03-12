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
    private JButton[][] rooms = new JButton[5][5];
    private String selectedHero = "";  // To store the selected hero
    private Hero hero;  // The player's hero

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

        // Dungeon grid setup
        dungeonPanel = new JPanel(new GridLayout(5, 5));
        mainPanel.add(dungeonPanel, "Dungeon");

        frame.add(mainPanel);
        cardLayout.show(mainPanel, "TitleScreen"); // Show title screen first
        frame.setVisible(true);
    }

    public String getHero() {
        return selectedHero;
    }

    // Initialize dungeon grid
    private void initializeDungeon() {
        dungeonPanel.removeAll(); // Clear panel before adding rooms
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                rooms[i][j] = new JButton("Room " + (i * 5 + j + 1));
                rooms[i][j].addActionListener(new RoomActionListener(i, j)); // Link rooms to combat
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

            switch (command) {
                case "New Game":
                    // Show hero selection dialog before starting the game
                    showHeroSelectionDialog();
                    break;
                case "Load Game":
                    JOptionPane.showMessageDialog(frame, "Implement");
                    break;
                case "Exit":
                    System.exit(0);
                    break;
                default:
                    System.out.println("Unknown command: " + command);
            }
        }
    }


    private void showHeroSelectionDialog() {
        // Create hero options
        String[] heroes = {"Warrior", "Thief", "Priestess"};
        String chosenHero = (String) JOptionPane.showInputDialog(
                frame,
                "Select your hero:",
                "Hero Selection",
                JOptionPane.QUESTION_MESSAGE,
                null,
                heroes,
                heroes[0]
        );

        if (chosenHero != null) {
            this.selectedHero = chosenHero;
            System.out.println(selectedHero + " selected!");

            //// Create the hero object based on selection
            //Room initialRoom = new Room(new int[]{0, 0}, "Starting Room"); // Example room for hero location
            //switch (chosenHero) {
            //    case "Warrior":
            //        this.hero = new Warrior(initialRoom); 
            //        break;
            //    case "Thief":
            //        this.hero = new Thief(initialRoom);  
            //        break;
            //    case "Priestess":
            //        this.hero = new Priestess(initialRoom); 
            //        break;
            //    default:
            //        this.hero = new Warrior(initialRoom); // Default to Warrior if invalid
            //}

            // Initialize dungeon grid and switch to it
            initializeDungeon();
            cardLayout.show(mainPanel, "Dungeon");
        } else {
            System.out.println("Hero selection canceled.");
        }
    }

    private class RoomActionListener implements ActionListener {
        private final int row, col;
    
        public RoomActionListener(int row, int col) {
            this.row = row;
            this.col = col;
        }
    
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Clicked room at (" + row + ", " + col + ")");
            // Create a random monster for combat
            Room currentRoom = new Room(new int[]{row, col}, "Room " + (row * 5 + col + 1));
            Monster monster = MonsterFactory.createRandomMonster(currentRoom); 
            // Start combat
            CombatManager.battle(frame, hero, monster);  
        }
    }
    

    // Main method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(View::new);
    }
}
