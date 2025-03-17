package dungeon;

import dungeon.Controller.GameController;
import dungeon.Controller.SystemController.MainMenu;
import dungeon.Controller.SystemController.SystemControllerName;

/**
 * landmark that can be used to place the hero when the game starts.
 */
public class HeroStartPoint extends Movable {
    private static final String NAME = "hero landmark";
    private static final double INVISIBILITY = VisionPower.ABSTRACT.power();
    
    public HeroStartPoint(final Atom theLoc) {
        super(theLoc, NAME, INVISIBILITY);
    }

}
