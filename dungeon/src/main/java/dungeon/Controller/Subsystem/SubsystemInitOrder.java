package dungeon.Controller.Subsystem;

/// represents the orders in which subsystems initialize.
/// entries match the exact name of the subsystem they represent. use PascalCase if an entry is a subsystem and SCREAMING_SNAKE_CASE
/// if the entry is not (for example, DEFAULT vs DungeonGenerator).
/// ORDER THE ENTRIES BY THE ORDER THEY INIT!!!!!
public enum SubsystemInitOrder {

    DungeonGenerator(3.0),
    DEFAULT(10.0),
    AtomLoader(15.0);

    private final double order;

    SubsystemInitOrder(final double theOrder) {
        order = theOrder;
    }

    public double order() {
        return order;
    }
}
