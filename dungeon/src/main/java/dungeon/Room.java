package dungeon;

/** where things are - the base level of atom in the world */
public class Room extends Atom {

    private static final String DEFAULT_NAME = "Room";

    public Room(final int[] theCoords, final String theName) {
        super(theCoords, theName);
    }

    public Room(final int[] theCoords) {
        super(theCoords, DEFAULT_NAME);
    }

    public Room() {
        super(DEFAULT_NAME);
    }

}
