package dungeon.Controller.SystemController;

import dungeon.Controller.GameController;
import dungeon.Controller.GameState;
import dungeon.Messages.MTV.ChangeState;
import dungeon.Messages.VTM.ClassSelected;
import dungeon.Messages.VTM.ViewToModelMessage;
import dungeon.View.ViewRunner;
import dungeon.View.ViewState;

/**
 * meant for handling user interaction with the main menu. currently a stub.
 */
public class MainMenu extends SystemController {
    private static final SystemControllerName NAME = SystemControllerName.MainMenu;
    private static final GameState INIT_STATE = GameState.MAIN_MENU;
    private static final boolean CAN_FIRE = true;

    private boolean myHasStarted = false;
    private boolean myIsDone = false;
    /// what hero the player selected
    private String myHeroSelection = null;

    public MainMenu(final GameController theController) {
        super(theController, NAME, INIT_STATE, CAN_FIRE);
    }

    public String getHeroSelection() {
        return myHeroSelection;
    }
    public void setHeroSelection(final String theHeroSelection) {
        myHeroSelection = theHeroSelection;
    }

    @Override
    public void initialize() {
        //GameController controller = getController();
        //controller.setState(GameState.LOADING);
    }

    @Override
    public void fire(final boolean resumed) {
        GameController controller = getController();
        if(myHasStarted == false) {
            try {
                SwingUtilities.invokeAndWait(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        ViewRunner myView = new ViewRunner(controller);
                        getController().setView(myView);
                    }
                });
                myHasStarted = true;

            } catch(Exception e) {
                System.out.println("unable to start the View! " + e.getStackTrace());
                System.exit(1);
            }
        } else {
            BlockingQueue<ViewToModelMessage> queue = controller.myVTMQueue;
            ClassSelected classMessage = null;
            while(!queue.isEmpty()) {
                ViewToModelMessage message = queue.poll();
                if(message instanceof ClassSelected msg) {
                    classMessage = msg;
                    break;
                } else {
                    System.out.println("unhandled input msg " + message.toString());
                }
            }
            if(classMessage == null) {
                return;
            }
            String heroSelection = classMessage.myClass;
            if(heroSelection.equals("")) {
                return;
            }
            switch (heroSelection) {
                case "Warrior":
                    break;
                case "Thief":
                    break;
                case "Priestess":
                    break;
                default:
                    return; // invalid response
            }
            setHeroSelection(heroSelection);
            controller.myMTVQueue.add(new ChangeState(ViewState.GAME)); //get the view to start displaying the game
            myHasStarted = false;
            myIsDone = true;
            setCanFire(false);
            
            //Hero hero = null;
            //switch (heroString) {
            //    case "Warrior":
            //        hero = new Warrior(null); 
            //        break;
            //    case "Thief":
            //        hero = new Thief(null);  
            //        break;
            //    case "Priestess":
            //        hero = new Priestess(null); 
            //        break;
            //    default:
            //        hero = new Warrior(null); // Default to Warrior if invalid
            //}
            //controller.setPlayer(hero);
            //controller.setView(null);
            controller.setState(GameState.LOADING);
        }
    }
}
