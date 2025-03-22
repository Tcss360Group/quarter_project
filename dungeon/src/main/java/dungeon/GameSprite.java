package dungeon;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.imageio.ImageIO;

import dungeon.View.ImageDisplayApp;

/**
 * the physical appearance of atoms.
 */
public class GameSprite implements Cloneable, Serializable { 
    private double myRoomX;
    private double myRoomY;
    private double myLayer;
    private transient BufferedImage myImage;

    /**
     * the width of our sprite on the screen as a ratio of a "standard tile" which takes up 1 / InOut.SCREEN_WIDTH_TILES of the screen horizontally
     */
    private double myWidth;
    /**
     * the height of our sprite on the screen as a ratio of a "standard tile" which takes up 1 / InOut.SCREEN_HEIGHT_TILES of the screen vertically
     */
    private double myHeight;

    private double myRotation;


    public GameSprite(final String theImagePath, final double x, final double y, final double theRotation, final double theWidth, final double theHeight, final double theLayer) {
        this.myRoomX = x;
        this.myRoomY = y;
        this.myLayer = theLayer;
        this.myImage = null;
        this.myWidth = theWidth;
        this.myHeight = theHeight;
        this.myRotation = theRotation;
        if(theImagePath != null && !theImagePath.equals("")) {
            setImage(theImagePath);
        }
    }

    /**
     * Constructor to initialize a GameSprite with position, layer, and icon.
     * 
     * @param theImagePath The path to the icon image.
     * @param x The x-coordinate in the room.
     * @param y The y-coordinate in the room.
     * @param theLayer The layer value.
    */
    public GameSprite(final String theImagePath, final double x, final double y, final double theRotation, final double theLayer) {
        this(theImagePath,x,y, theRotation,0.5,0.5, theLayer);
    }

    /**
     * Constructor to initialize a GameSprite with position, layer, and icon.
     * 
     * @param theImagePath The path to the icon image.
     * @param x The x-coordinate in the room.
     * @param y The y-coordinate in the room.
     * @param theLayer The layer value.
    */
    public GameSprite(String theImagePath, double x, double y, double theLayer) {
        this(theImagePath,x,y,0.0,0.5,0.5,theLayer);
    }
    

    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
        if(myImage != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(myImage, "png", baos);
            byte[] imageBytes = baos.toByteArray();
            oos.writeObject(imageBytes);
        } else {
            oos.writeObject(null);
        }
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject(); 
        byte[] imageBytes = (byte[]) ois.readObject(); 
        if (imageBytes != null) {
            ByteArrayInputStream bais = new ByteArrayInputStream(imageBytes);
            myImage = ImageIO.read(bais);
        } else {
            myImage = null;
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
    public double getWidth() {
        return myWidth;
    }
    public double getHeight() {
        return myHeight;
    }
    public double getRotation() {
        return myRotation;
    }
    public BufferedImage getImage() {
        BufferedImage ret = new BufferedImage(myImage.getWidth(), myImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = ret.createGraphics();
        try {
            g.drawImage(myImage, 0, 0, null);
        } finally {
            g.dispose();
        }
        return ret;
    }


    public void setX(double x) {
        this.myRoomX = x;
    }
    
    public void setY(double y) {
        this.myRoomY = y;
    }
    
    public void setTranslation(double x, double y) {
        myRoomX = x;
        myRoomY = y;
    }

    public void translate(double dx, double dy) {
        setTranslation(myRoomX + dx, myRoomY + dy);
    }

    public void setRotation(double theta) {
        myRotation = theta;
    }
    public void rotate(double theta) {
        myRotation += theta;
    }

    public void setScale(double theScaleX, double theScaleY) {
        myWidth = theScaleX;
        myHeight = theScaleY;
    }
    public void scale(double theScaleX, double theScaleY) {
        myWidth *= theScaleX;
        myHeight *= theScaleY;
    }
    public void scaleWidth(final double theScale) {
        myWidth *= theScale;
    }
    public void scaleHeight(final double theScale) {
        myHeight *= theScale;
    }

    public void setLayer(double theLayer) {
        this.myLayer = theLayer;
    }
    
    public void setImage(final BufferedImage icon) {
        this.myImage = icon;
    }

    public void setImage(final String theImagePath) {

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

    public void setWidth(final double theWidth) {
        myWidth = theWidth;
    }
    public void setHeight(final double theHeight) {
        myHeight = theHeight;
    }

    public GameSprite clone() {
        GameSprite ret = new GameSprite("", myRoomX, myRoomY, myRotation, myWidth, myHeight, myLayer);
        if(myImage != null) {
            ret.setImage(getImage());
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
