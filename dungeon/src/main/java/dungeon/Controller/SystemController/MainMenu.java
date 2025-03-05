package dungeon.Controller.SystemController;

import dungeon.Controller.GameController;
import dungeon.Controller.GameState;

/**
 * meant for handling user interaction with the main menu. currently a stub.
 */
public class MainMenu extends SystemController {
    private static final SystemControllerName NAME = SystemControllerName.MainMenu;
    private static final GameState INIT_STATE = GameState.MAIN_MENU;
    private static final boolean CAN_FIRE = false;

    public MainMenu(final GameController theController) {
        super(theController, NAME, INIT_STATE, CAN_FIRE);
    }

    @Override
    public void initialize() {
        GameController controller = getController();
        controller.setState(GameState.LOADING);
    }
}
