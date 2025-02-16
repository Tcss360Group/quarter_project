package View;

import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;

public class View {

    public static void main(String[] args) {

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        // Create the JFrame
        JFrame frame = new JFrame("Dungeon Adventure Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,500);

        // Set Frame to open in the center of the screen
        int x = (screenSize.width - frame.getWidth()) / 2;
        int y = (screenSize.height - frame.getHeight()) / 2;
        frame.setLocation(x, y);
        
        // Create the menu bar
        JMenuBar menuBar = new JMenuBar();
        
        // Create the help menu
        JMenu helpMenu = new JMenu("Help");
        JButton howToButton = new JButton("How to");
        JButton testButton = new JButton("Test");

        helpMenu.add(howToButton);
        helpMenu.add(testButton);
        menuBar.add(helpMenu);
        
        // Create the options menu
        JMenu optionsMenu = new JMenu("Options");
        menuBar.add(optionsMenu);
        
        // Add save, load and exit menu items to options menu
        JMenuItem saveMenuItem = new JMenuItem("Save");
        JMenuItem loadMenuItem = new JMenuItem("Load");
        optionsMenu.add(saveMenuItem);
        optionsMenu.add(loadMenuItem);
        
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        optionsMenu.add(exitMenuItem);
        
        // Set the menu bar
        frame.setJMenuBar(menuBar);
        
        frame.setVisible(true);
    
        frame.setLayout(new GridLayout(5, 5));
        
        // Create a 2D array to represent the rooms
        JButton[][] rooms = new JButton[5][5];
        
        // Initialize the rooms and add them to the frame
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                rooms[i][j] = new JButton("Room " + (i * 5 + j + 1));
                rooms[i][j].addActionListener(new RoomActionListener(i, j));
                frame.add(rooms[i][j]);
            }
        }
        
        frame.setVisible(true);
    }
    
    static class RoomActionListener implements ActionListener {
        private int row;
        private int col;
        
        public RoomActionListener(int row, int col) {
            this.row = row;
            this.col = col;
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            // Handle room button click
            System.out.println("Clicked room at (" + row + ", " + col + ")");
        }
    }
}
