package dungeon.Controller.Subsystem;

import dungeon.Controller.GameController;

/**
 * basic processors and handlers of global state in the world.
 * 
 */
public class Subsystem {

    private static final SubsystemInitOrder DEFAULT_INIT_ORDER = SubsystemInitOrder.DEFAULT;
    //no default name, always name subsystems
    private static final double DEFAULT_PRIORITY = 10.0;
    private static final int DEFAULT_WAIT = 1;
    private static final boolean DEFAULT_CAN_FIRE = false;

    private GameController myController;
    private String myName;

    private SubsystemInitOrder myInitOrder;
    /// later meant to determine how much of the tick the subsystem is allowed to use
    private double myPriority;
    /// how many ticks go between this subsystem firing. the longer this is the less it will process
    private int myWait;

    private boolean myCanFire;

    public Subsystem(
            final GameController theController, 
            final String theName,
            final SubsystemInitOrder theOrder,
            final double thePriority,
            final int theWait,
            final boolean theCanFire) {
        myController = theController;
        myName = theName;
        myInitOrder = theOrder;
        myPriority = thePriority;
        myWait = theWait;
        myCanFire = theCanFire;
    }

    public Subsystem(final GameController theController, final String theName) {
        this(theController, theName, DEFAULT_INIT_ORDER, DEFAULT_PRIORITY, DEFAULT_WAIT, DEFAULT_CAN_FIRE);
    }
    
    public Subsystem(final GameController theController, final String theName, final SubsystemInitOrder theOrder) {
        this(theController, theName, theOrder, DEFAULT_PRIORITY, DEFAULT_WAIT, DEFAULT_CAN_FIRE);
    }

    public Subsystem(final GameController theController, final String theName, final SubsystemInitOrder theOrder, final boolean theCanFire) {
        this(theController, theName, theOrder, DEFAULT_PRIORITY, DEFAULT_WAIT, theCanFire);
    }

    public Subsystem(final GameController theController, final String theName, final SubsystemInitOrder theOrder, final double thePriority) {
        this(theController, theName, theOrder, thePriority, DEFAULT_WAIT, DEFAULT_CAN_FIRE);
    }

    public Subsystem(final GameController theController, final String theName, final double thePriority) {
        this(theController, theName, DEFAULT_INIT_ORDER, thePriority, DEFAULT_WAIT, DEFAULT_CAN_FIRE);
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


    public double getPriority() {
        return myPriority;
    }

    public void setPriority(final double myPriority) {
        this.myPriority = myPriority;
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
