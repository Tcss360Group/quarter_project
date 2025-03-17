package dungeon.Messages.VTM;

public class ClassSelected implements ViewToModelMessage {
    public String myClass;
    public ClassSelected(final String theClass) {
        myClass = theClass;
    }
}
