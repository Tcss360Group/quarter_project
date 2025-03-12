package dungeon.Controller.SystemController;

import dungeon.Hero;
import dungeon.Priestess;
import dungeon.Thief;
import dungeon.View;
import dungeon.Warrior;
import dungeon.Controller.GameController;
import dungeon.Controller.GameState;

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
            View myView = new View();
            controller.setView(myView);
            myHasStarted = true;
        } else {
            View view = controller.getView();
            if(view == null) { //error?
                return;
            }
            if(view.getHero().equals("")) {
                return;
            }
            switch (view.getHero()) {
                case "Warrior":
                    break;
                case "Thief":
                    break;
                case "Priestess":
                    break;
                default:
                    return; // invalid response
            }
            setHeroSelection(view.getHero());
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
