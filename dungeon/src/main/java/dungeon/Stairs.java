package dungeon;

public class Stairs extends Door {
    private static String DEFAULT_NAME = "stairs";
    private static GameSprite DEFAULT_SPRITE = new GameSprite("stairs.png", 0.2f, 0.0f, 12.0f);

    public Stairs(final Atom theLocation, final String theName, final Room theDest) {
        super(theLocation, theName, theDest);
        setSprite(DEFAULT_SPRITE.clone());
    }

    public Stairs(final Atom theLocation, final Room theDest) {
        this(theLocation, DEFAULT_NAME, theDest);
    }

}
