package dungeon.View;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageDisplayApp {

    private static ImagePanel imagePanel;
    private static String myString;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Create the frame
            JFrame frame = new JFrame("Image Display App");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);

            // Initialize and add the image panel
            imagePanel = new ImagePanel();
            frame.add(imagePanel, BorderLayout.CENTER);
            imagePanel.setIgnoreRepaint(true);

            // Show the frame
            frame.setVisible(true);
            myString = "";
        });
    }

    // Static method to update the image from anywhere in the Model
    public static void updateDisplayedImage(BufferedImage image, String theString) {
        if (imagePanel != null) {
            imagePanel.setImage(image, theString);

        } else {
            System.err.println("ImagePanel is not initialized!");
        }
    }
}

// Custom JPanel to display the image
class ImagePanel extends JPanel {
    private BufferedImage image;
    private String myString;

    public void setImage(BufferedImage image, String theString) {
        myString = theString;
        this.image = image;
        repaint(); // Trigger repaint to display the new image
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Paint the panel background

        // Draw the image if it's set
        if (image != null) {
            int x = (getWidth() - image.getWidth()) / 2; // Center the image horizontally
            int y = (getHeight() - image.getHeight()) / 2; // Center the image vertically
            g.drawImage(image, x, y, null);
            g.setColor(Color.RED);
            g.drawString(myString, getWidth() / 2 - 50, getHeight() / 2);
        } else {
            // Draw a placeholder message if no image is set
            g.setColor(Color.RED);
            g.drawString("No image loaded", getWidth() / 2 - 50, getHeight() / 2);
        }
    }
}
