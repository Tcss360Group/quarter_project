package dungeon.Messages.MTV;

import dungeon.View.ViewState;

/**
 * tells the view to change its overall state to keep with the model,
 * such as moving from main menu to displaying the game
 */
public class ChangeState implements ModelToViewMessage {

    public ViewState state;
    public ChangeState(final ViewState theState) {
        state = theState;
    }
}
