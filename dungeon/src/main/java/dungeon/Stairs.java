package dungeon;

public class Stairs extends Door {
    private static String DEFAULT_NAME = "stairs";

    public Stairs(final Atom theLocation, final String theName, final Room theDest) {
        super(theLocation, theName, theDest);
    }

    public Stairs(final Atom theLocation, final Room theDest) {
        this(theLocation, DEFAULT_NAME, theDest);
    }

}
