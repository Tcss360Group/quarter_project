package dungeon;

public class Subsystem {

    private int myInitOrder;
    private int myFirePriority;

    private String myName;
    private Stage myStage;


    public enum Stage {
        IDLE,
        QUEUED,
        RUNNING,
        PAUSED,
        SLEEPING,
        PAUSING;
    }
    

    public void initialize() {

    }

}
