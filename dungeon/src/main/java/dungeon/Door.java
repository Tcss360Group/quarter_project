package dungeon;

import java.util.Optional;

/**
 * Placed in rooms to let things move to and fro other rooms.
 */
public class Door extends Movable {

    
    private static final String DEFAULT_NAME = "door";
    // Default sprite for the door
    private static final GameSprite DOOR_NORTH = new GameSprite("door-N.png", 0.0f, 0.0f, 0.0, 1.0, 1.0, 1.0f);
    private static final GameSprite DOOR_EAST = new GameSprite("door-E.png", 0.0f, 0.0f, 0.0, 1.0, 1.0,1.0f);
    private static final GameSprite DOOR_WEST = new GameSprite("door-W.png", 0.0f, 0.0f, 0.0, 1.0, 1.0,1.0f);
    private static final GameSprite DOOR_SOUTH = new GameSprite("door-S.png", 0.0f, 0.0f, 0.0, 1.0, 1.0,1.0f);
    
    // The room this door leads to
    private Atom myDest;

    private boolean myIsOpen;
    
    /**
     * Constructor to initialize a door leading to a specific room.
     * 
     * @param to The room this door connects to.
     */
    public Door(final Atom theLocation, final String theName, final Atom theDest) {
        super(theLocation, theName);
        this.myDest = theDest;
        this.myIsOpen = false;
    }

    public Door(final Atom theLocation, final Atom theDest) {
        this(theLocation, DEFAULT_NAME, theDest);
    }

    @Override
    public void initialize() throws Exception {
        super.initialize();
        if(myDest == null) {
            return;
        }

        Optional<Dir> dirToDest = Dir.getDir(this, myDest.getOuterLoc());
        Dir realDir = dirToDest.orElseThrow();
        switch(realDir) {
            case Dir.N -> setSprite(DOOR_NORTH.clone());
            case Dir.E -> setSprite(DOOR_EAST.clone());
            case Dir.S -> setSprite(DOOR_SOUTH.clone());
            case Dir.W -> setSprite(DOOR_WEST.clone());
        }
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

    public Atom getDest() {
        return myDest;
    }
    public void setDest(final Atom theDest) {
        myDest = theDest;
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
