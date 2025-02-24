package dungeon;

import java.util.ArrayList;

/**
 * handles initializing atoms
 */
public class AtomLoader extends Subsystem {
    private static final int NORMAL_INIT = 0;
    private static final int LATE_INIT = 1;
    private static final int LATER_INIT = 2;
    private static final int NUM_STAGES = 3;

    private ArrayList<ArrayList<Atom>> myInitStages;

    public AtomLoader() {
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
