package dungeon.Controller.SystemController;

import dungeon.Controller.GameController;
import dungeon.Controller.GameState;

/**
 * immediately sets the game controllers state to MAIN_MENU
 */
public class InitializerTest extends SystemController {
    private static final SystemControllerName NAME = SystemControllerName.InitializerTest;
    private static final SystemControllerInitOrder INIT_ORDER = SystemControllerInitOrder.DEFAULT;
    private static final GameState INIT_STATE = GameState.INITIALIZING;
    private static final boolean CAN_FIRE = false;

    public InitializerTest(final GameController theController) {
        super(theController, NAME, INIT_STATE, CAN_FIRE);
    }

    @Override
    public void initialize() {
        GameController controller = getController();
        controller.setState(GameState.MAIN_MENU);
    }
}
