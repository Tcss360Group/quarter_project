package dungeon.Controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import dungeon.Atom;
import dungeon.Controller.SystemController.AtomLoader;
import dungeon.Controller.SystemController.DungeonGenerator;
import dungeon.Controller.SystemController.InOut;
import dungeon.Controller.SystemController.InitializerTest;
import dungeon.Controller.SystemController.MainMenu;
import dungeon.Controller.SystemController.SystemController;
import dungeon.Controller.SystemController.SystemControllerName;
import dungeon.DungeonCharacter;
import dungeon.DungeonGenerationOptions;
import dungeon.Hero;
<<<<<<< Updated upstream
import dungeon.Pillar;
import dungeon.Room;
=======
import dungeon.HeroStartPoint;
import dungeon.Messages.MTV.ModelToViewMessage;
import dungeon.Messages.VTM.ViewToModelMessage;
import dungeon.Pillar;
import dungeon.Room;
import dungeon.View.ViewRunner;
>>>>>>> Stashed changes


/**
 * handles initializing and running all SystemControllers that themselves create and run the world.
 */
public class GameController {
    //"global" info that i havent figured out where else to put yet TODO: dedicated singletons

    //indices of respective dimensions of the world in myDims
    public static final int WIDTH = Atom.X;
    public static final int HEIGHT = Atom.Y;
    public static final int DEPTH = Atom.Z;

    private ArrayList<DungeonCharacter> myMobs;
    private Hero myPlayer;
    /// game ends when all of these are collected by the player
    private ArrayList<Pillar> myPillars;
    private DungeonGenerationOptions myOptions;
    /// the game map
    private Room[][][] myMap;
    private int[] myDims;

    private ViewRunner myView;
    ///messages given to us by the ViewRunner that we havent resolved yet
    public BlockingQueue<ViewToModelMessage> myVTMQueue;
    ///messages we're sending to the ViewRunner (they grab a reference to this)
    public BlockingQueue<ModelToViewMessage> myMTVQueue;


    //actual state that the game controller uses to run the game on a given tick
    private GameState myState;

<<<<<<< Updated upstream
=======
    /// number of ticks the GameController has ran for in this game
    private int myTicks = 0;

    /// if true we will return from loop()
    private boolean myShouldClose = false;
    

>>>>>>> Stashed changes
    private ArrayList<SystemController> mySystemControllers;
    private ArrayList<SystemController> mySystemControllersToInit;
    private HashMap<String, SystemController> mySystemControllersByName;

    public GameController(final DungeonGenerationOptions theOptions) {
        myMobs = new ArrayList<>();
        myPlayer = null;
        myPillars = new ArrayList<>();
        myState = GameState.INITIALIZING;
        myMap = null;
        myDims = null;
        myOptions = theOptions;
        mySystemControllers = new ArrayList<>();
        mySystemControllersToInit = new ArrayList<>( //TODO: sort this later, for now make sure to order them correctly
                Arrays.asList(
                    new InitializerTest(this),
                    new MainMenu(this),
                    new AtomLoader(this),
                    new DungeonGenerator(this),
                    new InOut(this)
                )
            );

        mySystemControllersByName = new HashMap<>();
        myMTVQueue = new LinkedBlockingQueue<>(){
            @Override
            public boolean add(ModelToViewMessage toAdd) {
                System.out.println("MTVQueue added " + toAdd.toString());
                return super.add(toAdd);
            }
            @Override
            public ModelToViewMessage poll() {
                ModelToViewMessage ret = super.poll();
                if(ret != null) {

                    System.out.println("MTVQueue poll() got " + ret.toString());
                }
                return ret;
            }
        };
        myVTMQueue = new LinkedBlockingQueue<>() {
            @Override
            public boolean add(ViewToModelMessage toAdd) {
                System.out.println("VTMQueue added " + toAdd.toString());
                return super.add(toAdd);
            }
            @Override
            public ViewToModelMessage poll() {
                ViewToModelMessage ret = super.poll();
                if(ret != null) {

                    System.out.println("VTMQueue poll() got " + ret.toString());
                }
                return ret;
            }
        };

    }

    public void setState(final GameState theNewState) {
        final GameState theOldState = myState;
        if (theNewState != GameState.INITIALIZING && theOldState == theNewState) {
            return;
        }
        ArrayList<SystemController> toRemove = new ArrayList<>();
        for (SystemController systemController : mySystemControllersToInit) {
            if (systemController.getInitState() != theNewState) {
                continue;
            }
            systemController.initialize();
            if (myState != theOldState) { //in case the system calls setState itself
                return;
            }
            String name = systemController.getName();
            mySystemControllers.add(systemController);
            mySystemControllersByName.put(name, systemController);

            toRemove.add(systemController);
        }
        mySystemControllersToInit.removeAll(toRemove);
        myState = theNewState;
    }

    public void init() {
        setState(GameState.INITIALIZING);
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

    public void start() {
        setState(GameState.HAPPENING);
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
        while (!gameIsDone() && tick < 10) {
            //System.out.println("tick: " + tick);
            for (int i = 0; i < myMobs.size(); i++) {
                DungeonCharacter character = myMobs.get(i);
                character.pollAction();
            }
            tick++;
        }
        setState(GameState.DONE);
        System.out.println("game is done!");
    }

    private boolean gameIsDone() {
<<<<<<< Updated upstream
=======
        if(myShouldClose) {
            return true;
        }
        if(getState() != GameState.HAPPENING) {
            System.out.println("game is in state: " + getState());
            return false;
        }
>>>>>>> Stashed changes
        int numCollectedPillars = 0;
        for (Pillar pillar : myPillars) {
            ArrayList<Atom> recursiveContainers = pillar.getRecursiveLocs();
            for (Atom container : recursiveContainers) {
                if (container instanceof Hero) {
                    numCollectedPillars++;
                }
            }
        }
        //System.out.println("num pillars: " + myPillars.size() + " num collected pillars: " + numCollectedPillars);
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
        myDims = new int[3];
        //depth height width
        myDims[DEPTH] = myMap.length;
        myDims[HEIGHT] = myMap[0].length;
        myDims[WIDTH] = myMap[0][0].length;
    }

    public int[] getDims() {
        return myDims.clone();
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
<<<<<<< Updated upstream
=======

    public void setView(final ViewRunner theView) {
        myView = theView;
    }
    public ViewRunner getView() {
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

    public boolean getShouldClose() {
        return myShouldClose;
    }

    public void setShouldClose(final boolean myShouldClose) {
        this.myShouldClose = myShouldClose;
    }

>>>>>>> Stashed changes
}
