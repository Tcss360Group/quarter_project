package dungeon.Controller.Subsystem;

import java.util.ArrayList;

import dungeon.Atom;
import dungeon.Controller.GameController;
import dungeon.InitOrder;

/**
 * handles initializing atoms
 */
public class AtomLoader extends Subsystem {
    private static final String NAME = "Atom Loader";
    private static final SubsystemInitOrder INIT_ORDER = SubsystemInitOrder.AtomLoader;
    private static final double PRIORITY = 10.0;
    private static final int WAIT = 1;
    private static final boolean CAN_FIRE = true;

    private static final int NORMAL_INIT = 0;
    private static final int LATE_INIT = 1;
    private static final int LATER_INIT = 2;
    private static final int NUM_STAGES = 3;

    private ArrayList<ArrayList<Atom>> myInitStages;

    public AtomLoader(final GameController theGameController) {
        super(theGameController, NAME, INIT_ORDER, PRIORITY, WAIT, CAN_FIRE);
        myInitStages = new ArrayList<>(NUM_STAGES);
        myInitStages.set(NORMAL_INIT, new ArrayList<>());
        myInitStages.set(LATE_INIT, new ArrayList<>());
        myInitStages.set(LATER_INIT, new ArrayList<>());

    }

    public void stageAtom(final Atom theAtom) {
        final InitOrder order = theAtom.initOrder();
        switch (order) {
            case IMMEDIATE: {
                try {
                    theAtom.initialize();
                } catch (Exception e) {
                    System.out.println("exception in stageAtom! " + e.toString());
                }
                return;
            }
            default: {
                myInitStages.get(order.index()).add(theAtom);
            }
        }
    }
    


}
