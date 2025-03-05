package dungeon;

import java.util.ArrayList;
import dungeon.Controller.GameController;

public class Pillar extends Physical {
    private static String DESCRIPTION = "A mysterious ancient pillar";
    /// the stairs that lead from our spawning room to the floor below us, if any exists
    private Stairs myHereToBelow;
    /// the stairs that lead from the floor below us to our spawning room, if any exists
    private Stairs myBelowToHere;

    public Pillar(final Atom theLoc, final String theName) {
        super(theLoc, theName, DESCRIPTION);
        myHereToBelow = null;
        myBelowToHere = null;
    }

    @Override
    public void initialize() {
        final GameController controller = Main.getController();
        controller.addPillar(this);

        Atom loc = getLoc();
        int[] coords = loc.getCoords();

        for (Atom contents : loc.getContents()) {
            if (contents instanceof Stairs sContents) {
                myHereToBelow = sContents;
            }
        }
        
        coords[Z] -= 1;
        if (Z < 0) {
            return;
        }
        Room[][][] map = controller.getMap();
        Room below = map[coords[Z]][coords[Y]][coords[X]];

        for (Atom contents : below.getContents()) {
            if (contents instanceof Stairs sContents) {
                myBelowToHere = sContents;
            }
        }

    }

    public void onPickedUp() {
        Hero loc = (Hero) getLoc();
        System.out.println("you have picked up the pillar of " + getName() + "!");
        myHereToBelow.unlock();
        myBelowToHere.unlock();
    }
    
    public void onDropped() {
        System.out.println("you have dropped the pillar of " + getName() + "!");
        myHereToBelow.lock();
        myBelowToHere.lock();
    }

    @Override
    public void hasMoved(final Atom theOldLoc) {
        if (getLoc() instanceof Hero && !(theOldLoc instanceof Hero)) {
            onPickedUp();
        } else if (!(getLoc() instanceof Hero) && theOldLoc instanceof Hero) {
            onDropped();
        }

    }

}
