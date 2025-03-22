package dungeon;

import java.io.Serializable;

/**
 * A class that represents a generic game event.
 */
public abstract class GameEvent implements Serializable {
    private static final long serialVersionUID = 1L;

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
