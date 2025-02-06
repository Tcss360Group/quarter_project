package dungeon;

import java.util.ArrayList;

/**
 * an entity that can be traced to either be in nullspace (null myLoc) or the contents of another atom
 */
public abstract class Atom {

    public static int X = 2;
    public static int Y = 1;
    public static int Z = 0;

    private int[] myCoords;
    ///atoms that are directly inside of us
    private ArrayList<Movable> myContents;
    private String myName;
    /// the representation of our physical appearance in the world
    private GameSprite mySprite;

    public Atom(final int[] theCoords, final String theName) {
        myCoords = theCoords.clone();
        myName = theName;
        myContents = new ArrayList<>();
        mySprite = new GameSprite();
    }

    public int[] getCoords() {
        return myCoords;
    }
    protected void setCoords(final int[] theCoords) {
        myCoords = theCoords.clone();
    }
    @SuppressWarnings("unchecked")
    public ArrayList<Atom> getContents() {
        return (ArrayList<Atom>)myContents.clone();
    }
    public String getName() {
        return myName;
    }
    public GameSprite getSprite() {
        return (GameSprite) mySprite.clone();
    }
    public void setName(final String theName) {
        myName = theName;
    }

    //NO setter for loc at the atom level. non movable atoms DO NOT MOVE!!
    public void addContents(final Movable theNewContent) {
        myContents.add(theNewContent);
    }
    public void addContents(final ArrayList<Movable> theNewContents) {
        myContents.addAll(theNewContents);
    }
    public void removeContents(final Movable theOldContent) {
        myContents.remove(theOldContent);
    }
    public void removeContents(final ArrayList<Movable> theOldContents) {
        myContents.removeAll(theOldContents);
    }


    //eventually these will fire off events so things can register to them
    /**
     * whether movable theMov can move into us from theOldLoc
     * @param theMov
     * @param theOldLoc
     * @return
     */
    protected boolean canEnter(final Movable theMov, final Atom theOldLoc) {
        return true;
    }
    /**
     * whether movable theMov can move out of us into theDest
     * @param theMov
     * @param theDest
     * @return
     */
    protected boolean canExit(final Movable theMov, final Atom theDest) {
        return true;
    }

    /**
     * for when theMove has completed a move into us from theOldLoc
     * @param theMov
     * @param theOldLoc
     */
    protected void hasEntered(final Movable theMov, final Atom theOldLoc) {

    }

    /**
     * for when theMov has completed a move out of us into theDest
     * @param theMov
     * @param theDest
     */
    protected void hasExited(final Movable theMov, final Atom theDest) {

    }

}
