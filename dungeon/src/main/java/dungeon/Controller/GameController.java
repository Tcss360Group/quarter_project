package dungeon;

import java.util.ArrayList;


/**
 * handles initializing and running all systems that themselves create and run the world.
 * eventually at least. for now it can do everything itself
 */
public class GameController {
    private ArrayList<DungeonCharacter> myMobs;
    private Hero myPlayer;
    /// game ends when all of these are collected by the player
    private ArrayList<Pillar> myPillars;
    /// the game map
    private Room[][][] myMap;

    private GameState myState;


    public enum GameState {
        SETTING_UP,
        HAPPENING,
        DONE
    }

    public GameController() {
        myMobs = new ArrayList<>();
        myPlayer = null;
        myPillars = new ArrayList<>();
        myState = GameState.SETTING_UP;
        myMap = null;
    }

    public void init(final int theNumRooms, final int theNumFloors) {
        int size = (int)Math.sqrt(theNumRooms);
        DungeonGenerationOptions options = new DungeonGenerationOptions(
                theNumRooms,
                theNumFloors,
                4,
                10,
                new String[] { "a", "b", "c", "d" },
                size,
                size);
        DungeonGenerator generator = new DungeonGenerator();
        myMap = generator.createMap(options, this);

    }

    public void start() {
        myState = GameState.HAPPENING;
        while (true) {
            try {
                loop();
            } catch (Exception e) {
                System.out.println("UNCAUGHT EXCEPTION FROM LOOP: " + e.toString());
                continue;
            }
            break;
        }
    }

    private void loop() { //TODO: loop in player input through here to their mob?
        int tick = 0;
        while (!gameIsDone() || tick >= 10) {
            System.out.println("tick: " + tick);
            for (int i = 0; i < myMobs.size(); i++) {
                DungeonCharacter character = myMobs.get(i);
                character.pollAction();
            }
        }
        myState = GameState.DONE;
        System.out.println("game is done!");
    }

    private boolean gameIsDone() {
        int numCollectedPillars = 0;
        for (Pillar pillar : myPillars) {
            ArrayList<Atom> recursiveContainers = pillar.getRecursiveLocs();
            for (Atom container : recursiveContainers) {
                if (container instanceof Hero) {
                    numCollectedPillars++;
                }
            }
        }
        System.out.println("num pillars: " + myPillars.size() + " num collected pillars: " + numCollectedPillars);
        return numCollectedPillars == myPillars.size();
    }
}
