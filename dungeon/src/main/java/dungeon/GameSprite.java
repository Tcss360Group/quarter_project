package dungeon;

import java.awt.Image;

/**
 * the physical appearance of atoms.
 */
public class GameSprite {
    
    private float myRoomX;
    private float myRoomY;
    private float myLayer;
    private Image myIcon;
    
    /**
     * Constructor to initialize a GameSprite with position, layer, and icon.
     * 
     * @param theIconPath The path to the icon image.
     * @param x The x-coordinate in the room.
     * @param y The y-coordinate in the room.
     * @param theLayer The layer value.
     */
    public GameSprite(String theIconPath, float x, float y, float theLayer) {
        // Implementation for loading the image would go here
        this.myRoomX = x;
        this.myRoomY = y;
        this.myLayer = theLayer;
        // Placeholder for setting the image: this.myIcon = loadImage(theIconPath);
    }
    
    public void setX(float x) {
        this.myRoomX = x;
    }
    
    public void setY(float y) {
        this.myRoomY = y;
    }
    
    public void setLayer(float theLayer) {
        this.myLayer = theLayer;
    }
    
    public void setIcon(Image icon) {
        this.myIcon = icon;
    }
}
