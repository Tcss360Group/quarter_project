package dungeon.Controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import dungeon.Atom;
import dungeon.Controller.SystemController.AtomLoader;
import dungeon.Controller.SystemController.DungeonGenerator;
import dungeon.Controller.SystemController.InitializerTest;
import dungeon.Controller.SystemController.MainMenu;
import dungeon.Controller.SystemController.SystemController;
import dungeon.Controller.SystemController.SystemControllerName;
import dungeon.DungeonCharacter;
import dungeon.DungeonGenerationOptions;
import dungeon.Hero;
import dungeon.HeroStartPoint;
import dungeon.Pillar;
import dungeon.Room;
import dungeon.View;


/**
 * handles initializing and running all SystemControllers that themselves create and run the world.
 */
public final class GameController {
    //"global" info that i havent figured out where else to put yet TODO: dedicated singletons

    private ArrayList<DungeonCharacter> myMobs;
    private Hero myPlayer;
    private HeroStartPoint myHeroStartPoint;
    /// game ends when all of these are collected by the player
    private ArrayList<Pillar> myPillars;
    private DungeonGenerationOptions myOptions;
    /// the game map
    private Room[][][] myMap;

    private View myView;

    ///actual state that the game controller uses to run the game on a given tick
    private GameState myState;

    /// number of ticks the GameController has ran for in this game
    private int myTicks = 0;
    

    private ArrayList<SystemController> mySystemControllers;
    private ArrayList<SystemController> mySystemControllersToInit;
    private HashMap<String, SystemController> mySystemControllersByName;
    private HashMap<GameState, ArrayList<SystemController>> mySystemControllersByState;

    public GameController(final DungeonGenerationOptions theOptions) {
        myMobs = new ArrayList<>();
        myPlayer = null;
        myPillars = new ArrayList<>();
        myState = GameState.INITIALIZING;
        myMap = null;
        myOptions = theOptions;
        mySystemControllers = new ArrayList<>();
        mySystemControllersToInit = new ArrayList<>( //TODO: sort this later, for now make sure to order them correctly
                Arrays.asList(
                    new InitializerTest(this),
                    new MainMenu(this),
                    new AtomLoader(this),
                    new DungeonGenerator(this)
                )
            );
        mySystemControllersByState = new HashMap<>();
        mySystemControllersByName = new HashMap<>();

        for(SystemController system : mySystemControllersToInit) {
            GameState state = system.getInitState();
            mySystemControllersByState.putIfAbsent(state, new ArrayList<>());
            mySystemControllersByState.get(state).add(system);
        }
    }

    public GameState getState() {
        return myState;
    }

    public void setState(final GameState theNewState) {
        myState = theNewState;
    }

    /// actually updates the subsystems we should be processing / initing
    private void updateForState(final GameState theOldState, final GameState theNewState) {
        if (myState != GameState.INITIALIZING && theOldState == theNewState) {
            return;
        }
        ArrayList<SystemController> toRemove = new ArrayList<>();
        for (SystemController systemController : mySystemControllersToInit) {
            if (systemController.getInitState() != theNewState) {
                if(mySystemControllers.indexOf(systemController) != -1) {
                    mySystemControllers.remove(systemController); //probably a bad idea, add a deinit function?
                }
                continue;
            }
            systemController.initialize();
            String name = systemController.getName();
            mySystemControllers.add(systemController);
            mySystemControllersByName.put(name, systemController);

            toRemove.add(systemController);
        }
        mySystemControllersToInit.removeAll(toRemove);
        if (myState != theNewState) { //in case the system calls setState itself (this is a way to do this)
            updateForState(theNewState, myState);
        }
        //myState = theNewState;
    }

    public void init() {


        setState(GameState.INITIALIZING);
        updateForState(GameState.INITIALIZING, GameState.INITIALIZING);
        //int size = (int)Math.sqrt(theNumRooms);
        //DungeonGenerationOptions options = new DungeonGenerationOptions(
        //        theNumRooms,
        //        theNumFloors,
        //        4,
        //        10,
        //        new String[] { "a", "b", "c", "d" },
        //        size,
        //        size);
        //DungeonGenerator generator = new DungeonGenerator(this);
        //myMap = generator.createMap(myOptions);


    }

    public void start() throws Exception {
        //updateForState(GameState.INITIALIZING, GameState.INITIALIZING);
        while (true) {
            try {
                loop();
            } catch (Exception e) {
                System.out.println("UNCAUGHT EXCEPTION FROM LOOP: " + e.toString());
                throw e; //just so i catch it, might be temporary
                //continue;
            }
            break;
        }
    }

    private void loop() throws Exception { //TODO: loop in player input through here to their mob?
        GameState oldState;
        ArrayList<SystemController> controllersToRun = mySystemControllersByState.get(getState());
        while (!gameIsDone()) {
            oldState = getState();
            controllersToRun = mySystemControllersByState.get(getState());
            if(controllersToRun == null) {
                throw new Exception("state doesnt have controllers in it: " + getState());
            }
            //System.out.println("tick: " + tick);
            for (int i = 0; i < myMobs.size(); i++) {
                DungeonCharacter character = myMobs.get(i);
                character.pollAction();
            }
            for(SystemController system : controllersToRun) {
                if(system.getCanFire()) {
                    system.fire(false);
                }
            }
            if(getState() != oldState) {
                updateForState(oldState, getState());
            }
            myTicks++;
        }
        oldState = getState();
        setState(GameState.DONE);
        updateForState(oldState, getState());
        System.out.println("game is done!");
    }

    private boolean gameIsDone() {
        if(getState() != GameState.HAPPENING) {
            System.out.println("game is in state: " + getState());
            return false;
        }
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

    public SystemController getSystemController(final SystemControllerName name) {
        return mySystemControllersByName.getOrDefault(name.getName(), null);
    }

    public Room[][][] getMap() {
        return myMap;
    }

    public void setMap(final Room[][][] theMap) {
        myMap = theMap;
    }

    public DungeonGenerationOptions getOptions() {
        return myOptions;
    }

    public ArrayList<DungeonCharacter> getMobs() {
        return myMobs;
    }

    public void setMobs(ArrayList<DungeonCharacter> myMobs) {
        this.myMobs = myMobs;
    }

    public void addPillar(final Pillar thePillar) {
        myPillars.add(thePillar);
    }

    public void setView(final View theView) {
        myView = theView;
    }
    public View getView() {
        return myView;
    }

    public void setPlayer(final Hero thePlayer) {
        myPlayer = thePlayer;
    }
    public Hero getPlayer() {
        return myPlayer;
    }

    public void setHeroStartPoint(final HeroStartPoint theStartPoint) {
        myHeroStartPoint = theStartPoint;
    }
    public HeroStartPoint getHeroStartPoint() {
        return myHeroStartPoint;
    }
}
