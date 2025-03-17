package dungeon;

import java.util.ArrayList;

/**
 * things that can move
 */
public abstract class Movable extends Atom {

    private static final double INVISION_POWER = VisionPower.NONE.power();

    private Atom myLoc;
    /// if the invisibility of an object is greater than the vision power of a mob then that mob cant see that object
    private double myInvisiblePower;

    public Movable(final Atom theLoc, String theName) {
        super(theLoc != null ? theLoc.getCoords() : new int[]{0,0,0}, theName);
        setLoc(theLoc);
        myInvisiblePower = INVISION_POWER;
    }
    public Movable(final Atom theLoc, String theName, final double theInvisiblePower) {
        super(theLoc != null ? theLoc.getCoords() : new int[]{0,0,0}, theName);
        setLoc(theLoc);
        myInvisiblePower = theInvisiblePower;
    }

    @Override
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

    public double getInivisiblePower() {
        return myInvisiblePower;
    }
    
    public void setInvisiblePower(final double theInvisiblePower) {
        myInvisiblePower = theInvisiblePower;
    }

    ///get the outermost atom containing us and anything containing us and anything containing that...
    @Override
    public Atom getOuterLoc() {
        Atom ret = getLoc();
        while(ret instanceof Movable retM) {
            ret = retM.getLoc();
        }
        return ret;
    }

    @Override
    public Atom stepOut() {
        return getLoc();
    }
    
    ///get all atoms that contain us and what contains us and what contains that and so on
    @Override
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


    @Override
    public ArrayList<Atom> getAtomsInView(final int theXRadius, final int theYRadius) {
        Atom outermostLoc = getOuterLoc();
        return super.getAtomsInView(outermostLoc.getCoords(), theXRadius, theYRadius);
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
