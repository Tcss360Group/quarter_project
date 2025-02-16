package dungeon;

/**
 * Represents a staircase that allows movement between dungeon floors.
 */
public class Stair extends Movable {

    // Default sprite for the stair
    private static final GameSprite STAIR_DEFAULT_SPRITE = new GameSprite("stair_default.png", 0.0f, 0.0f, 0.0f);

    /**
     * Constructor to initialize a staircase.
     * 
     * @param theLocation The location of the staircase.
     * @param theName The name of the staircase.
     */
    public Stair(final Atom theLocation, final String theName) {
        super(theLocation, theName);
    }
}
