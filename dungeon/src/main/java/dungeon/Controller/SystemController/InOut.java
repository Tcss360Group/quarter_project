package dungeon.Controller.SystemController;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;

import dungeon.Atom;
import dungeon.Controller.GameController;
import dungeon.Controller.GameState;
import dungeon.Hero;
import dungeon.Messages.MTV.ChangeState;
import dungeon.Messages.MTV.ModelToViewMessage;
import dungeon.Messages.MTV.Update;
import dungeon.Messages.VTM.ClickedOn;
import dungeon.Messages.VTM.Close;
import dungeon.Messages.VTM.KeyPressed;
import dungeon.Messages.VTM.ViewToModelMessage;
import dungeon.View.AtomView;
import dungeon.View.ViewState;

/**
 * handles receiving input from and giving output to the ViewRunner
 */
public class InOut extends SystemController {

    public static final int SCREEN_WIDTH_TILES = 10;
    public static final int SCREEN_HEIGHT_TILES = 10;

    private static final SystemControllerName NAME = SystemControllerName.InOut;
    private static final boolean CAN_FIRE = true;
    private static final GameState INIT_STATE = GameState.HAPPENING;

    private int[] myPlayerCoords;
    private HashMap<AtomView, Atom> myAtomsByView;
    private ArrayList<AtomView> myCurrentAtomViews;
    private Rectangle myCurrentScreenMap;
    private boolean hasFiredBefore = false;

    public InOut(final GameController controller) {
        super(controller, NAME, INIT_STATE);
        setCanFire(CAN_FIRE);
        myPlayerCoords = null;
        myAtomsByView = new HashMap<>();
        myCurrentAtomViews = new ArrayList<>();
        myCurrentScreenMap = new Rectangle(0,0,1,1);
    }

    @Override
    public void initialize() {
        GameController controller = getController();
        
        LinkedBlockingQueue<ModelToViewMessage> toQueue = (LinkedBlockingQueue<ModelToViewMessage>) controller.myMTVQueue;
        toQueue.add(new ChangeState(ViewState.GAME));  
        
    }

    @Override
    public void fire(boolean resumed) {
        GameController controller = getController();
        LinkedBlockingQueue<ViewToModelMessage> in = (LinkedBlockingQueue<ViewToModelMessage>) controller.myVTMQueue;
        LinkedBlockingQueue<ModelToViewMessage> out = (LinkedBlockingQueue<ModelToViewMessage>) controller.myMTVQueue;
        Atom clickedAtom = null;

        //System.out.println("InOut.fire() ");

        while(!in.isEmpty()) {
            
            ViewToModelMessage input = in.poll();
            if(input instanceof Close) {
                controller.setShouldClose(true);
                return;
            }
            else if(input instanceof KeyPressed kp) {
                System.out.println("InOut received " + kp.pressed.toString() + " chars! not implemented on model side though");
                continue;
            } 
            else if(input instanceof ClickedOn clck) {
                clickedAtom = myAtomsByView.getOrDefault(clck.clicked, clickedAtom);
                continue;
            }
            else {
                System.out.println("unsupported message in InOut.fire()! " + input.toString());
            }
        }
        
        boolean anyUpdate = !hasFiredBefore;
        Hero player = controller.getPlayer();

        if(clickedAtom != null) {
            anyUpdate = true; 
            player.clickOn(clickedAtom);
        }


        if(!anyUpdate) {
            hasFiredBefore = true;
            return;
        }

        Atom playerLoc = player.getOuterLoc();
        int[] playerCoords = playerLoc.getCoords();

        myCurrentScreenMap.setRect(
            createScreenMapForPlayer(playerCoords, SCREEN_WIDTH_TILES, SCREEN_HEIGHT_TILES)
        );

        System.out.println("screen map currently at: " + myCurrentScreenMap.toString() + ", player is at: [" + playerCoords[Atom.X] + ", " + playerCoords[Atom.Y] + ", " + playerCoords[Atom.Z] + "]");
        ArrayList<Atom> atomsInView = playerLoc.getAtomsInView((int)myCurrentScreenMap.getWidth() / 2, (int)myCurrentScreenMap.getHeight() / 2);

        int id = 0;
        myAtomsByView.clear();
        myCurrentAtomViews.clear();
        for(Atom atom : atomsInView) {
            AtomView view = new AtomView(id, atom, myCurrentScreenMap);
            id++;

            myAtomsByView.put(view, atom);
            myCurrentAtomViews.add(view);
        }

        try {
            out.put(new Update(myCurrentAtomViews));
        } catch (Exception e) {
            e.printStackTrace();
        }
        hasFiredBefore = true;
    }

    private Rectangle createScreenMapForPlayer(final int[] theCoords, final int theWidth, final int theHeight) {

        return new Rectangle(theCoords[Atom.X] - theWidth / 2, theCoords[Atom.Y] - theHeight / 2, theWidth, theHeight);
    }
}
