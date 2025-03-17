package dungeon;

import java.util.ArrayList;

/**
 * things that can move
 */
public abstract class Movable extends Atom {

    private Atom myLoc;

    public Movable(final Atom theLoc, String theName) {
        super(theLoc != null ? theLoc.getCoords() : new int[]{0,0,0}, theName);
        setLoc(theLoc);
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
        }
    }

<<<<<<< Updated upstream
=======

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
    
>>>>>>> Stashed changes
    ///get all atoms that contain us and what contains us and what contains that and so on
    @Override
    public ArrayList<Atom> getRecursiveLocs() {
        ArrayList<Atom> retList = new ArrayList<>();
        Atom loc = getLoc();
        while(loc != null) {
            retList.add(loc);
            if(loc instanceof Movable) {
                Movable MLoc = (Movable) loc;
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
