package dungeon;

/**
 * A class that represents a generic game event.
 */
public abstract class GameEvent {
    
    private String description;

    public GameEvent(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Abstract method to be implemented by specific events.
     * This method defines how the event is triggered and handled.
     */
    public abstract void trigger();
}
