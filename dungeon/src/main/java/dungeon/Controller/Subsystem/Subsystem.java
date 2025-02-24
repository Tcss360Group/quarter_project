package dungeon.Controller.Subsystem;

import dungeon.Controller.GameController;

/**
 * basic processors and handlers of global state in the world.
 * 
 */
public class Subsystem {

    private GameController myController;
    private String myName;

    private SubsystemInitOrder myInitOrder;
    /// later meant to determine how much of the tick the subsystem is allowed to use
    private double myFirePriority;
    /// how many ticks go between this subsystem firing. the longer this is the less it will process
    private int myWait;

    private boolean myCanFire;

    public Subsystem(
            final GameController theController, 
            final String theName,
            final SubsystemInitOrder theOrder,
            final double theFirePriority,
            final int theWait,
            final boolean theCanFire) {
        myController = theController;
        myName = theName;
        myInitOrder = theOrder;
        myFirePriority = theFirePriority;
        myWait = theWait;
        myCanFire = theCanFire;
    }

    public void initialize() {

    }

    /// dont extend this at all, this is what GameController calls on the subsystem when its ready to fire and is meant to later coordinate state
    public void ignite(final boolean resumed) {
        fire(resumed);
    }

    /**
     * process whatever is necessary for this subsystem, if anything.
     * by default just sets CanFire to false
     * @param resumed
     */
    public void fire(final boolean resumed) {
        myCanFire = false;
    }

    public GameController getController() {
        return myController;
    }

    public void setController(GameController myController) {
        this.myController = myController;
    }

    public String getName() {
        return myName;
    }

    public void setName(String myName) {
        this.myName = myName;
    }

    public SubsystemInitOrder getInitOrder() {
        return myInitOrder;
    }


    public double getFirePriority() {
        return myFirePriority;
    }

    public void setFirePriority(final double myFirePriority) {
        this.myFirePriority = myFirePriority;
    }

    public int getWait() {
        return myWait;
    }

    public void setWait(final int myWait) {
        this.myWait = myWait;
    }

    public boolean getCanFire() {
        return myCanFire;
    }

    public void setCanFire(final boolean myCanFire) {
        this.myCanFire = myCanFire;
    }

}
