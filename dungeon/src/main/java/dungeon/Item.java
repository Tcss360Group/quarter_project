package dungeon;

/**
 * Represents an item that can exist in the dungeon.
 */
public abstract class Item extends DungeonObject implements Pickupable {

    private String myType;

    /**
     * Constructor for an Item.
     *
     * @param theLoc The starting location.
     * @param theName The name of the item.
     * @param theType The type of the item.
     */
    public Item(final Atom theLoc, final String theName, final String theType) {
        super(theLoc, theName, theType);
        this.myType = theType;
    }

    /**
     * Gets the type of the item.
     * 
     * @return The item's type.
     */
    public String getType() {
        return myType;
    }

    /**
     * Sets the type of the item.
     * 
     * @param theType The new type.
     */
    public void setType(final String theType) {
        this.myType = theType;
    }

    /**
     * Abstract method to define item usage.
     * Must be implemented by concrete subclasses.
     *
     * @param theUser The entity using the item.
     */
    public abstract void use(final DungeonCharacter theUser);
}
