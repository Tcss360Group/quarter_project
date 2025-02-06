package dungeon;

/**
 * things that can move
 */
public abstract class Movable extends Atom {

    private Atom myLoc;

    public Movable(final Atom theLoc, String theName) {
        super(theLoc.getCoords(), theName);
        setLoc(theLoc);
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
        }
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
    public void hasMoved(final Atom theOldLoc) {

    }
}
