package dungeon;


/**
 * Represents a general object that can move within the dungeon.
 */
public abstract class DungeonObject extends Movable   {


    private String myType;

    /**
     * Constructor for DungeonObject.
     *
     * @param theLoc The starting location.
     * @param theName The name of the object.
     * @param theType The type of the object.
     */
    public DungeonObject(final Atom theLoc, final String theName, final String theType) {
        super(theLoc, theName);
        this.myType = theType;
    }

    /**
     * Gets the type of the object.
     * 
     * @return The object's type.
     */
    public String getType() {
        return myType;
    }

    /**
     * Sets the type of the object.
     * 
     * @param theType The new type.
     */
    public void setType(final String theType) {
        this.myType = theType;
    }

}
