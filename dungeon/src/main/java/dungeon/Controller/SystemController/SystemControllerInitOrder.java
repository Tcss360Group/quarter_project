package dungeon.Controller.SystemController;

/// represents the orders in which SystemControllers initialize.
/// entries match the exact name of the SystemController they represent. use PascalCase if an entry is a SystemController and SCREAMING_SNAKE_CASE
/// if the entry is not (for example, DEFAULT vs DungeonGenerator).
/// ORDER THE ENTRIES BY THE ORDER THEY INIT!!!!!
public enum SystemControllerInitOrder {

    DungeonGenerator(3.0),
    DEFAULT(10.0),
    AtomLoader(15.0);

    private final double order;

    SystemControllerInitOrder(final double theOrder) {
        order = theOrder;
    }

    public double order() {
        return order;
    }
}
