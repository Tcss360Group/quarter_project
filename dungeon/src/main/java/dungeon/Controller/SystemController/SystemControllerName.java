package dungeon.Controller.SystemController;

public enum SystemControllerName {
    MainMenu("Main Menu"),
    InitializerTest("Initializer Test"),
    AtomLoader("Atom Loader"),
    DungeonGenerator("Dungeon Generator");

    private final String myName;

    private SystemControllerName(final String theName) {
        myName = theName;
    }

    public String getName() {
        return myName;
    }
}
