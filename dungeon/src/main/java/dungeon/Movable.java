package dungeon;

import java.util.ArrayList;

/**
 * things that can move
 */
public abstract class Movable extends Atom {

    private static final double VISION_POWER = VisionPower.NONE.power();
    private static final double INVISION_POWER = VisionPower.NONE.power();

    private Atom myLoc;
    ///states whether this movable is visible to the player
    private double myVisionPower;
    /// if the invisibility of an object is greater than the vision power of a mob then that mob cant see that object
    private double myInvisiblePower;

    public Movable(final Atom theLoc, String theName) {
        super(theLoc != null ? theLoc.getCoords() : new int[]{0,0,0}, theName);
        setLoc(theLoc);
        myVisionPower = VISION_POWER;
        myInvisiblePower = INVISION_POWER;
    }
    public Movable(final Atom theLoc, String theName, final double theVisionPower) {
        super(theLoc != null ? theLoc.getCoords() : new int[]{0,0,0}, theName);
        setLoc(theLoc);
        myVisionPower = theVisionPower;
        myInvisiblePower = INVISION_POWER;
    }
    public Movable(final Atom theLoc, String theName, final double theVisionPower, final double theInvisiblePower) {
        super(theLoc != null ? theLoc.getCoords() : new int[]{0,0,0}, theName);
        setLoc(theLoc);
        myVisionPower = theVisionPower;
        myInvisiblePower = theInvisiblePower;
    }

    public Atom getLoc() {
        return myLoc;
    }
    private void setLoc(final Atom theDest) {
        if(myLoc != null) {
            myLoc.removeContents(this);
        }
        myLoc = theDest;
        if(theDest != null) {
            theDest.addContents(this);
            setCoords(theDest.getCoords());
        } else {
            setCoords(new int[]{-1,-1,-1});
        }
    }

    public double getVisionPower() {
        return myVisionPower;
    }

    public void setVisible(final double theVisionPower) {
        myVisionPower = theVisionPower;
    }

    public double getInivisiblePower() {
        return myInvisiblePower;
    }
    
    public void setInvisiblePower(final double theInvisiblePower) {
        myInvisiblePower = theInvisiblePower;
    }

    ///get all atoms that contain us and what contains us and what contains that and so on
    public ArrayList<Atom> getRecursiveLocs() {
        ArrayList<Atom> retList = new ArrayList<>();
        Atom loc = getLoc();
        while(loc != null) {
            retList.add(loc);
            if(loc instanceof Movable MLoc) {
                loc = MLoc.getLoc();
            } else {
                break;
            }
        }

        return retList;
    }

    ///move from our loc to theDest if possible
    public void move(final Atom theDest) {
        if(myLoc != null && !myLoc.canExit(this, theDest)) {
            return; //rules against early returns are dumb, if i >have< to change it later i will
        }
        if(theDest != null && !theDest.canEnter(this, myLoc)) {
            return;
        }

        if(myLoc != null) {
            myLoc.hasExited(this, theDest);
        }
        Atom oldLoc = myLoc;
        setLoc(theDest);
        
        if(myLoc != null) {
            myLoc.hasEntered(this, oldLoc);
        }

        hasMoved(oldLoc);
    }

    /// we have completed a move, now we may put whatever reacts to that in here
    protected void hasMoved(final Atom theOldLoc) {

    }

    /**
     * called when we try to move into theDest but theObstacle blocked us from doing so
     * allows us to react to solid objects.
     */
    protected void bump(final Atom theDest, final Atom theObstacle) {

    }

}
