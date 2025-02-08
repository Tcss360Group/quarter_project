package dungeon;

/**
 * Placed in rooms to let things move to and fro other rooms.
 */
public class Door extends Movable {
    
    // Default sprite for the door
    private static final GameSprite DOOR_DEFAULT_SPRITE = new GameSprite("door_default.png");
    
    // The room this door leads to
    private Room to;
    
    /**
     * Constructor to initialize a door leading to a specific room.
     * 
     * @param to The room this door connects to.
     */
    public Door(Room to) {
        this.to = to;
    }
    
    /**
     * Checks if the door is open.
     * 
     * @return true if the door is open, false otherwise.
     */
    public boolean isOpen() {
        // Placeholder 
        return false;
    }
}
