package dungeon;

import java.awt.Graphics2D;
import java.awt.List;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Arrays;

import dungeon.Controller.GameController;

public abstract class Atom {

    private static final String DEFAULT_NAME = "thing";

    public static final int X = 0;
    public static final int Y = 1;
    public static final int Z = 2;

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
        mySprite = new GameSprite("door-N.png", 0.0f, 0.0f, 1.0f);
    }

    public Atom(final int[] theCoords) {
        this(theCoords, DEFAULT_NAME);
    }

    public Atom(final String theName) {
        this(new int[] { 0, 0, 0 }, theName);
    }
 
    /**
     * returns what stage this class inits in, with NORMAL being the earliest and LATER being the latest
     * classes that init at later stages are able to react to the state of the world initialized in earlier stages
     * @return
     */
    public InitOrder initOrder() {
        return InitOrder.NORMAL;
    }

    /**
     * allows this object to actually react to the existing world after being placed on the map.
     */
    public void initialize() throws Exception {

    }

    public int[] getCoords() {
        return myCoords.clone();
    }
    public void setCoords(final int[] theCoords) {
        myCoords = theCoords.clone();
    }
    @SuppressWarnings("unchecked")
    public ArrayList<Atom> getContents() {
        return (ArrayList<Atom>)myContents.clone();
    }
    public String getName() {
        return myName;
    }

    public double getLayer() {
        return mySprite.getLayer();
    }
    public void setSprite(final GameSprite theSprite) {
        mySprite = theSprite;
    }
    public GameSprite getSprite() {
        return (GameSprite) mySprite.clone();
    }
    public void setName(final String theName) {
        myName = theName;
    }


    /** 
     * returns the manhatten distance between two atoms
     * -1 means no distance - one or both the atoms are not on the map
    */
    public static int distance(final Atom a, final Atom b) {
        if(a == null || b == null) {
            return -1;
        }
        final int[] aCoords = a.getCoords();
        final int[] bCoords = b.getCoords();

        return Math.abs(aCoords[X] - bCoords[X]) + Math.abs(aCoords[Y] - bCoords[Y]) + Math.abs(aCoords[Z] - bCoords[Z]);
    }

    ///get the outermost atom containing us and anything containing us and anything containing that...
    public Atom getOuterLoc() {
        return this;
    }

    /// gets one level loc out unless we are an Atom, which just returns ourselves
    public Atom stepOut() {
        return this;
    }

    public Atom getLoc() {
        return null;
    }
    
    public ArrayList<Atom> getRecursiveLocs() {
        return new ArrayList<>();
    }


    /** 
     * called when we are clicked on by (presumably) the player
     */
    public void onClicked(final Atom clickedBy) {
        System.out.println(myName + " was clicked on by " + clickedBy.toString() + " at: " + Arrays.toString(getOuterLoc().getCoords()));
    }

    /**
     * called when we (presumably the player) click an atom
     * @param clicked
     */
    public void clickOn(final Atom clicked) {
        clicked.onClicked(this);
    }

    public boolean physicalAccess(final Atom toAccess, final int maxDistance, final boolean onlySameZ) { 
        Atom ourOuter = getOuterLoc();
        Atom theirOuter = toAccess.getOuterLoc();

        int[] ourCoords = ourOuter.getCoords();
        int[] theirCoords = theirOuter.getCoords();

        if(Atom.distance(theirOuter, ourOuter) > maxDistance || (onlySameZ && ourCoords[Atom.Z] != theirCoords[Atom.Z])) {
            return false;
        }
        return true;
    }
    public boolean physicalAccess(final Atom toAccess, final int maxDistance) {
        return physicalAccess(toAccess, maxDistance, true);
    }

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

    ///get all contents and contents of contents and contents of contents of contents ... of this
    public ArrayList<Atom> getRecursiveContents() {
        ArrayList<Atom> retList = new ArrayList<>();
        ArrayList<Atom> queue = new ArrayList<>();
        queue.add(this);
        while(queue.size() > 0) {
            @SuppressWarnings("unchecked")
            ArrayList<Atom> copy = (ArrayList<Atom>)queue.clone();
            for(Atom node : copy) {
                queue.remove(node);
                queue.addAll(node.getContents());
                retList.add(node);
            }
        }
        return retList;
    }

    public ArrayList<Atom> getAtomsInView(final int theXRadius, final int theYRadius) {
        int[] coords = getCoords();
        GameController controller = Main.getController();
        int[] dims = controller.getDims();
        Room[][][] map = controller.getMap();

        int sx = Math.clamp(coords[Atom.X] - theXRadius, 0, dims[GameController.WIDTH] - 1);
        int sy = Math.clamp(coords[Atom.Y] - theYRadius, 0, dims[GameController.HEIGHT] - 1);
        int ex = Math.clamp(coords[Atom.X] + theXRadius, 0, dims[GameController.WIDTH] - 1);
        int ey = Math.clamp(coords[Atom.Y] + theYRadius, 0, dims[GameController.HEIGHT] - 1);

        int z = coords[Atom.Z];

        ArrayList<Atom> ret = new ArrayList<>();

        for(int y = sy; y < ey; y++) {
            for(int x = sx; x < ex; x++) {
                Room[][] test = map[z];
                Room roomAt = map[z][y][x];
                if(roomAt == null) {
                    continue;
                }
                ret.add(roomAt);
                ret.addAll(roomAt.getRecursiveContents());
            }
        }
        return ret;
    }

    public ArrayList<Atom> getAtomsInView(final int[] coords, final int theXRadius, final int theYRadius) {
        GameController controller = Main.getController();
        int[] dims = controller.getDims();
        Room[][][] map = controller.getMap();

        int sx = Math.clamp(coords[Atom.X] - theXRadius, 0, dims[GameController.WIDTH] - 1);
        int sy = Math.clamp(coords[Atom.Y] - theYRadius, 0, dims[GameController.HEIGHT] - 1);
        int ex = Math.clamp(coords[Atom.X] + theXRadius, 0, dims[GameController.WIDTH] - 1);
        int ey = Math.clamp(coords[Atom.Y] + theYRadius, 0, dims[GameController.HEIGHT] - 1);

        int z = coords[Atom.Z];

        ArrayList<Atom> ret = new ArrayList<>();

        for(int y = sy; y < ey; y++) {
            for(int x = sx; x < ex; x++) {
                Room roomAt = map[z][y][x];
                if(roomAt == null) {
                    continue;
                }
                ret.add(roomAt);
                ret.addAll(roomAt.getRecursiveContents());
            }
        }
        return ret;
    }

    //eventually these will fire off events so things can register to them
    /**
     * whether movable theMov can move into us from theOldLoc
     * @param theMov
     * @param theOldLoc
     * @return
     */
    protected boolean canEnter(final Movable theMov, final Atom theOldLoc) {
        final ArrayList<Atom> contents = getContents();
        for(Atom content : contents) {
            if(content != theMov && !content.cross(theMov, theOldLoc)) {
                //System.out.println(myName + " canEnter returned false on: " + content.toString());
                return false;
            }
        }
        //System.out.println(myName + " canEnter returned true for " + theMov.getName());
        return true;
    }

    /**
     * whether movable theMov can move out of us into theDest
     * @param theMov
     * @param theDest
     * @return
     */
    protected boolean canExit(final Movable theMov, final Atom theDest) {
        final ArrayList<Atom> contents = getContents();
        //System.out.println(myName + " canExit: " + contents.toString());
        for(Atom content : contents) {
            if(content != theMov && !content.uncross(theMov, theDest)) {
                //System.out.println(myName + " canExit returned false on: " + content.toString());
                return false;
            }
        }
        //System.out.println(myName + " canExit returned true for " + theMov.getName());
        return true;
    }

    /**
     * for when theMove has completed a move into us from theOldLoc
     * @param theMov
     * @param theOldLoc
     */
    protected void hasEntered(final Movable theMov, final Atom theOldLoc) {
        final ArrayList<Atom> contents = getContents();
        for(Atom content : contents) {
            content.onCrossed(theMov, theOldLoc);
        }
    }

    /**
     * for when theMov has completed a move out of us into theDest
     * @param theMov
     * @param theDest
     */
    protected void hasExited(final Movable theMov, final Atom theDest) {
        final ArrayList<Atom> contents = getContents();
        for(Atom content : contents) {
            content.onUncrossed(theMov, theDest);
        }
    }


    /**
     * called when something attempts to leave our location but before they do so
     * return false to stop it, return true to allow it
     */
    protected boolean uncross(final Movable theMov, final Atom theDest) {
        return true;
    }

    /**
     * called when something attempts to enter our location but before they do so
     * return false to stop it, return true to allow it
     */
    protected boolean cross(final Movable theMov, final Atom theOldLoc) {
        return true;
    }

    /**
     * called when something will exit our location but their loc hasnt updated
     */
    protected void onUncrossed(final Movable theMov, final Atom theDest) {
        return;
    }

    /**
     * called when something has entered our location
     */
    protected void onCrossed(final Movable theMov, final Atom theOldLoc) {
        return;
    }

}
