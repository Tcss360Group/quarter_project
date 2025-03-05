package dungeon.Controller.SystemController;

import dungeon.Controller.GameController;
import dungeon.Controller.GameState;

/**
 * basic processors and handlers of global state in the world.
 * 
 */
public abstract class SystemController {

    private static final SystemControllerInitOrder DEFAULT_INIT_ORDER = SystemControllerInitOrder.DEFAULT;
    //no default name, always name SystemControllers
    private static final double DEFAULT_PRIORITY = 10.0;
    private static final int DEFAULT_WAIT = 1;
    private static final boolean DEFAULT_CAN_FIRE = false;

    private GameController myController;
    private SystemControllerName myNameEnum; //should probably delete this
    private String myName;

    /**
     * what gamestate that this SystemController initializes in
     */
    private GameState myInitState;

    private SystemControllerInitOrder myInitOrder;
    /// later meant to determine how much of the tick the SystemController is allowed to use
    private double myPriority;
    /// how many ticks go between this SystemController firing. the longer this is the less it will process
    private int myWait;

    private boolean myCanFire;

    public SystemController(
            final GameController theController, 
            final SystemControllerName theName,
            final SystemControllerInitOrder theOrder,
            final GameState theInitState,
            final double thePriority,
            final int theWait,
            final boolean theCanFire) {
        myController = theController;
        myName = theName.getName();
        myInitOrder = theOrder;
        myInitState = theInitState;
        myPriority = thePriority;
        myWait = theWait;
        myCanFire = theCanFire;
    }

    public SystemController(final GameController theController, final SystemControllerName theName, final GameState theInitState) {
        this(theController, theName, DEFAULT_INIT_ORDER, theInitState, DEFAULT_PRIORITY, DEFAULT_WAIT, DEFAULT_CAN_FIRE);
    }
    
    public SystemController(final GameController theController, final SystemControllerName theName, final SystemControllerInitOrder theOrder, final GameState theInitState) {
        this(theController, theName, theOrder, theInitState, DEFAULT_PRIORITY, DEFAULT_WAIT, DEFAULT_CAN_FIRE);
    }

    public SystemController(final GameController theController, final SystemControllerName theName, final SystemControllerInitOrder theOrder, final GameState theInitState, final boolean theCanFire) {
        this(theController, theName, theOrder, theInitState, DEFAULT_PRIORITY, DEFAULT_WAIT, theCanFire);
    }

    public SystemController(final GameController theController, final SystemControllerName theName, final SystemControllerInitOrder theOrder, final GameState theInitState, final double thePriority) {
        this(theController, theName, theOrder, theInitState, thePriority, DEFAULT_WAIT, DEFAULT_CAN_FIRE);
    }

    public SystemController(final GameController theController, final SystemControllerName theName, final GameState theInitState, final boolean theCanFire) {
        this(theController, theName, DEFAULT_INIT_ORDER, theInitState, DEFAULT_PRIORITY, DEFAULT_WAIT, theCanFire);
    }

    public SystemController(final GameController theController, final SystemControllerName theName, final GameState theInitState, final double thePriority) {
        this(theController, theName, DEFAULT_INIT_ORDER, theInitState, thePriority, DEFAULT_WAIT, DEFAULT_CAN_FIRE);
    }
    
    public void initialize() {

    }

    /// dont extend this at all, this is what GameController calls on the SystemController when its ready to fire and is meant to later coordinate state
    public void ignite(final boolean resumed) {
        fire(resumed);
    }

    /**
     * process whatever is necessary for this SystemController, if anything.
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

    public SystemControllerInitOrder getInitOrder() {
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

    public GameState getInitState() {
        return myInitState;
    }

}
