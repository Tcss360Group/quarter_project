package dungeon.Controller.SystemController;

import java.util.ArrayList;

import dungeon.Atom;
import dungeon.Controller.GameController;
import dungeon.Controller.GameState;
import dungeon.InitOrder;

/**
 * handles initializing atoms (calling initialize() on them)
 * this is necessary so that atoms can always react to others in the map when theyre created regardless of what
 * order theyre placed on the map. so long as you give this SystemController a list of all atoms youve created they 
 * will have initialize() called on them in the correct order.
 * 
 * Atoms are created according to a two step process:
 * 1. actual object creation from calling constructors. this sets the absolute minimum number of arguments as possible
 * 2. calling initialize() on them for any additional logic that requires knowing what else is in their location, such as 
 *      pillars linking to the stairs object in their room.
 *      if we instead relied on new(), then youd have to create the pillar after the stairs object or else you have a bug.
 *      now you dont have to care.
 */
public class AtomLoader extends SystemController {
    private static final SystemControllerName NAME = SystemControllerName.AtomLoader;
    private static final SystemControllerInitOrder INIT_ORDER = SystemControllerInitOrder.AtomLoader;
    private static final GameState INIT_STATE = GameState.LOADING;
    private static final double PRIORITY = 10.0;
    private static final int WAIT = 1;
    private static final boolean CAN_FIRE = true;

    private static final int NORMAL_INIT = 0;
    private static final int LATE_INIT = 1;
    private static final int LATER_INIT = 2;
    private static final int NUM_STAGES = 3;

    private ArrayList<ArrayList<Atom>> myInitStages;

    public AtomLoader(final GameController theGameController) {
        super(theGameController, NAME, INIT_ORDER, INIT_STATE, PRIORITY, WAIT, CAN_FIRE);
        myInitStages = createInitList();

    }

    private ArrayList<ArrayList<Atom>> createInitList() {
        ArrayList<ArrayList<Atom>> ret = new ArrayList<>(NUM_STAGES);
        ret.add(NORMAL_INIT, new ArrayList<>());
        ret.add(LATE_INIT, new ArrayList<>());
        ret.add(LATER_INIT, new ArrayList<>());
        return ret;
    }

    private void stageAtom(final Atom theAtom, final ArrayList<ArrayList<Atom>> theStageList) {
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
                theStageList.get(order.index()).add(theAtom);
            }
        }

    }

    public void stageAtom(final Atom theAtom) {
        stageAtom(theAtom, myInitStages);
    }

    /**
     * whenever you create a bunch of atoms, put them in a list and call this on that list so theyre all initialized
     * correctly.
     * @param theAtoms the list of atoms to initialize
     * @param immediate if true, immediately initializes theAtoms before returning. otherwise inits them on the next fire().
     */
    public void stageAtoms(final ArrayList<Atom> theAtoms, final boolean immediate) {
        if (immediate) {
            ArrayList<ArrayList<Atom>> immediateInitList = createInitList();
            for (Atom atom : theAtoms) {
                stageAtom(atom, immediateInitList);
            }
            initAtoms(immediateInitList);
        } else {
            for (Atom atom : theAtoms) {
                stageAtom(atom);
            }
        }
    }
    
    @Override
    public void fire(boolean resumed) {
        initAtoms(myInitStages);
    }

    public void initAtoms(final ArrayList<ArrayList<Atom>> theAtomsToInit) {
        for (Atom initer : theAtomsToInit.get(NORMAL_INIT)) {
            try {
                initer.initialize();
            } catch (Exception e) {
            }
        }
        theAtomsToInit.get(NORMAL_INIT).clear();
        for (Atom initer : theAtomsToInit.get(LATE_INIT)) {
            try {
                initer.initialize(); 
            } catch (Exception e) {
            }
        }
        theAtomsToInit.get(LATE_INIT).clear();
        for (Atom initer : theAtomsToInit.get(LATER_INIT)) {
            try {
                initer.initialize();
            } catch (Exception e) {
            }
        } 
        theAtomsToInit.get(LATER_INIT).clear();
    }

}
