package dungeon;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import dungeon.View.ImageDisplayApp;

/**
 * the physical appearance of atoms.
 */
public class GameSprite implements Cloneable { 
    private double myRoomX;
    private double myRoomY;
    private double myLayer;
    private BufferedImage myImage;
    static int numWindows = 0;
    static int maxWindows = 10;
    
    /**
     * Constructor to initialize a GameSprite with position, layer, and icon.
     * 
     * @param theImagePath The path to the icon image.
     * @param x The x-coordinate in the room.
     * @param y The y-coordinate in the room.
     * @param theLayer The layer value.
    */
    public GameSprite(String theImagePath, double x, double y, double theLayer) {
        // Implementation for loading the image would go here
        this.myRoomX = x;
        this.myRoomY = y;
        this.myLayer = theLayer;
        this.myImage = null;
        String path = theImagePath != null ? theImagePath : "";
        String finalPath = System.getProperty("user.dir") + "\\game_icons\\" + path;
        try {
            if(path != null && !path.equals("")) {
                File file = new File(finalPath);
                BufferedImage image = ImageIO.read(file); 
                myImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
                Graphics2D g = myImage.createGraphics();
                g.drawImage(image, 0, 0, null);
                g.dispose();
            }
        } catch (Exception e) {
            System.out.println("error in GameSprite loading the image at path: " + finalPath + " file: " + new File(finalPath));
            System.out.println("cwd: " + System.getProperty("user.dir"));
            e.printStackTrace();
        }
        if(myImage == null) {
            myImage = new BufferedImage(1,1,BufferedImage.TYPE_INT_RGB);
        }
    }
    
    public double getX() {
        return myRoomX;
    }
    public double getY() {
        return myRoomY;
    }
    public double getLayer() {
        return myLayer;
    }
    public BufferedImage getImage() {
        BufferedImage ret = new BufferedImage(myImage.getWidth(), myImage.getHeight(), myImage.getType());
        Graphics2D g = ret.createGraphics();
        try {
            g.drawImage(myImage, 0, 0, null);
        } finally {
            g.dispose();
        }
        
        int width = ret.getWidth();
        int height = ret.getHeight();

        boolean anyNonZero = false;

        //for(int y = 0; y < height; y++) {
        //    for(int x = 0; x < width; x++) {
        //        int rgb = ret.getRGB(x,y);
        //        int red = (rgb >> 16) & 0xFF;
        //        int green = (rgb >> 8) & 0xFF;
        //        int blue = rgb & 0xFF;
        //
        //        if(red != 0 || green != 0 || blue != 0) {
        //            anyNonZero = true;
        //            System.out.printf("RET Pixel (%d, %d): R=%d, G=%d, B=%d\n", x, y, red, green, blue); 
        //        }
        //        
        //        // Print the RGB values
        //    }
        //}
        //
        //if(anyNonZero == false) {
        //    for(int y = 0; y < height; y++) {
        //        for(int x = 0; x < width; x++) {
        //            int rgb = myImage.getRGB(x,y);
        //            int red = (rgb >> 16) & 0xFF;
        //            int green = (rgb >> 8) & 0xFF;
        //            int blue = rgb & 0xFF;
        //
        //            if(red != 0 || green != 0 || blue != 0) {
        //                anyNonZero = true;
        //                System.out.printf("myImage Pixel (%d, %d): R=%d, G=%d, B=%d\n", x, y, red, green, blue); 
        //            }
        //            
        //            // Print the RGB values
        //        }
        //    }
        //}
        //
        //if(numWindows < maxWindows) {
        //    numWindows++;
        //    ImageDisplayApp.main(null);
        //    try {
        //        ImageDisplayApp.updateDisplayedImage(myImage, finalPath);
        //    } catch(Exception e) {
        //        e.printStackTrace();
        //    }
        //
        //}
        return ret;
    }

    public void setX(double x) {
        this.myRoomX = x;
    }
    
    public void setY(double y) {
        this.myRoomY = y;
    }
    
    public void setLayer(float theLayer) {
        this.myLayer = theLayer;
    }
    
    public void setImage(final BufferedImage icon) {
        this.myImage = icon;
    }

    public GameSprite clone() {
        GameSprite ret = new GameSprite("", myRoomX, myRoomY, myLayer);
        if(myImage != null) {
            ret.setImage(getImage());
            if(!areImagesEqual(myImage, ret.myImage)) {
                int asda = 1;
            }
        }
        //if(numWindows < maxWindows) {
        //    numWindows++;
        //    ImageDisplayApp.main(null);
        //    try {
        //        ImageDisplayApp.updateDisplayedImage(ret.myImage, "");
        //    } catch(Exception e) {
        //        e.printStackTrace();
        //    }
        // 
        //}
        return ret;
    }

    public static boolean areImagesEqual(BufferedImage img1, BufferedImage img2) {
        // Step 1: Check if dimensions are the same
        if (img1.getWidth() != img2.getWidth() || img1.getHeight() != img2.getHeight()) {
            return false; // Images are different
        }

        // Step 2: Compare pixel by pixel
        for (int y = 0; y < img1.getHeight(); y++) {
            for (int x = 0; x < img1.getWidth(); x++) {
                if (img1.getRGB(x, y) != img2.getRGB(x, y)) {
                    return false; // Found a mismatch
                }
            }
        }

        return true; // Images are identical
    }
}
