package dungeon;

/**
 * movables that arent alive
 */
public abstract class Physical extends Movable {
    public Physical(final Atom theLoc, final String theName) {
        super(theLoc, theName);
    }
}
