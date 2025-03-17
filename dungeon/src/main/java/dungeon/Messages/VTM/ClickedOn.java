package dungeon.Messages.VTM;

import dungeon.Messages.RTV.RenderToViewMessage;
import dungeon.View.AtomView;

public class ClickedOn implements ViewToModelMessage, RenderToViewMessage {
    public AtomView clicked;

    public ClickedOn(final AtomView theClicked) {
        clicked = theClicked;
    }
}
