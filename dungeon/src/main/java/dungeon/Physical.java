package dungeon;

/**
 * Class that represents a physical object in the game world that is movable but not alive.
 * Includes items, traps, obstacles, etc.
 */
public abstract class Physical extends Movable {

    private static final String DESCRIPTION = "";

    private boolean isActive;  // Tracks whether the object is still active 
    private String description; // A brief description of the object

    public Physical(final Atom theLoc, final String theName) {
        super(theLoc, theName);
        this.description = DESCRIPTION;
        this.isActive = true; // Default state, can be deactivated (e.g., after use)
    }

    public Physical(final Atom theLoc, final String theName, String description) {
        super(theLoc, theName);
        this.description = description;
        this.isActive = true; // Default state, can be deactivated (e.g., after use)
    }

    /**
     * Abstract method to be implemented by specific physical objects (e.g., items, traps).
     * Defines how the object is interacted with.
     */
    public void interact() {

    }

    /**
     * A method to deactivate the object (e.g., a trap after it's triggered or an item after it is picked up).
     */
    public void deactivate() {
        isActive = false;
    }

    /**
     * Method to update the state of the object if necessary after interaction (optional).
     */
    public void updateState() {
    }

    /**
     * Returns the description of the physical object.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Checks if the physical object is still active.
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * Called when the physical object moves. Can be overridden to handle additional logic after movement.
     * @param theOldLoc The previous location of the object.
     */
    @Override
    protected void hasMoved(final Atom theOldLoc) {
    }

    /**
     * Called when the object bumps into an obstacle (for handling collision logic).
     * @param theDest The destination location the object is trying to move to.
     * @param theObstacle The obstacle the object is colliding with.
     */
    @Override
    protected void bump(final Atom theDest, final Atom theObstacle) {
        // Implement logic for when an object bumps into an obstacle (e.g., stop moving or trigger event).
        System.out.println("The " + getName() + " bumps into an obstacle: " + theObstacle);
    }

    /**
     * For debugging or displaying the object's info.
     */
    @Override
    public String toString() {
        return getName() + " (" + getLoc().getCoords()[0] + ", " + getLoc().getCoords()[1] + ") - " + getDescription();
    }
}
