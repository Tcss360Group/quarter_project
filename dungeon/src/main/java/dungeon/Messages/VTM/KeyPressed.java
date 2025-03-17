package dungeon.Messages.VTM;

import java.util.ArrayList;

public class KeyPressed implements ViewToModelMessage {
    public ArrayList<Character> pressed;

    public KeyPressed(final ArrayList<Character> thePressed) {
        pressed = (ArrayList<Character>)thePressed.clone();
    }
}
