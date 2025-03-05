package dungeon;

/**
 * landmark that can be used to place the hero when the game starts.
 */
public class HeroStartPoint extends Movable {
    private static final String NAME = "hero landmark";
    
    public HeroStartPoint(final Atom theLoc) {
        super(theLoc, NAME);
    }
}
