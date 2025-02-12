package dungeon;

/**
 * Placed in rooms to let things move to and fro other rooms.
 */
public class Door extends Movable {
    
    // Default sprite for the door
    private static final GameSprite DOOR_DEFAULT_SPRITE = new GameSprite("door_default.png", 0.0f, 0.0f, 0.0f);
    
    // The room this door leads to
    private Room myDest;

    private boolean myIsOpen;
    
    /**
     * Constructor to initialize a door leading to a specific room.
     * 
     * @param to The room this door connects to.
     */
    public Door(final Atom theLocation, final String theName, final Room theDest) {
        super(theLocation, theName);
        this.myDest = theDest;
        this.myIsOpen = false;
    }

    public void unlock() {
        setOpen(true);
    }
    public void lock() {
        setOpen(false);
    }

    private void setOpen(final boolean theOpen) {
        myIsOpen = theOpen;
    }

    /**
     * Checks if the door is open.
     * 
     * @return true if the door is open, false otherwise.
     */
    public boolean getOpen() {
        // Placeholder 
        return myIsOpen;
    }

    protected boolean uncross(final Movable theMov, final Atom theDest) {
        if(theDest != myDest) {
            return true;
        }
        return myIsOpen;
    }

    protected boolean cross(final Movable theMov, final Atom theOldLoc) {
        if(theOldLoc != myDest) {
            return true;
        }
        return myIsOpen;
    }
}
