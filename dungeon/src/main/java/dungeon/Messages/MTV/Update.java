package dungeon.Messages.MTV;

import java.util.ArrayList;

import dungeon.Messages.VTR.ViewToRenderMessage;
import dungeon.View.AtomView;

/**
 * tells the view to change everything its displaying
 */
public class Update implements ModelToViewMessage, ViewToRenderMessage {
    public final ArrayList<AtomView> views;

    public Update(final ArrayList<AtomView> theViews) {
        views = theViews;
    }
}
