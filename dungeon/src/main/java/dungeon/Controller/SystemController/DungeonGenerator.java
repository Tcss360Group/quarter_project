package dungeon.Controller.SystemController;

import java.util.ArrayList;
import java.util.Random;

import dungeon.Atom;
import dungeon.Controller.GameController;
import dungeon.Controller.GameState;
import dungeon.Dir;
import dungeon.Door;
import dungeon.DungeonCharacter;
import dungeon.DungeonGenerationOptions;
import dungeon.HeroStartPoint;
import dungeon.Ogre;
import dungeon.Pillar;
import dungeon.Room;
import dungeon.Skeleton;
import dungeon.Stairs;

/**
 * handles generating the world
 */
public final class DungeonGenerator extends SystemController {

    private static final SystemControllerName NAME = SystemControllerName.DungeonGenerator;
    private static final SystemControllerInitOrder ORDER = SystemControllerInitOrder.DungeonGenerator;
    private static final GameState INIT_STATE = GameState.LOADING;
    private static final boolean CAN_FIRE = false;
    
    public DungeonGenerator(final GameController theController) {
        super(theController, NAME, ORDER, INIT_STATE, CAN_FIRE);
    }

    public class DungeonGenerationFloor {
        ArrayList<Room> myRooms;
        Room[][] myMap;
        Room myBossRoom;
        Room myEntrance;
        BossRoomChallenge myChallenge;
        Pillar myPillar;
        ArrayList<DungeonCharacter> myCharacters;
        ArrayList<Atom> myAtoms;
        HeroStartPoint myStartPoint;

        DungeonGenerationFloor(final int theWidth, final int theHeight) {
            myRooms = new ArrayList<>();
            myMap = new Room[theHeight][theWidth];
            myBossRoom = null;
            myEntrance = null;
            myChallenge = null;
            myPillar = null;
            myCharacters = new ArrayList<>();
            myAtoms = new ArrayList<>();
            myStartPoint = null;
        }

    }

    public enum BossRoomChallenge {
        NO_CHALLENGE,
        BOSS,
    }

    @Override
    public void initialize() {
        GameController controller = getController();
        AtomLoader loader = (AtomLoader) controller.getSystemController(SystemControllerName.AtomLoader);
        CreateMapRet mapAndAtoms = createMap(controller.getOptions());
        controller.setMap(mapAndAtoms.map);
        controller.setMobs(mapAndAtoms.characters);
        loader.stageAtoms(mapAndAtoms.atoms, true);
    }

    public class CreateMapRet {
        public Room[][][] map;
        public ArrayList<Atom> atoms;
        public ArrayList<DungeonCharacter> characters;
        public HeroStartPoint startPoint;

        public CreateMapRet() {
            map = null;
            atoms = new ArrayList<>();
            characters = new ArrayList<>();
            startPoint = null;
        }
    }

    public CreateMapRet createMap(final DungeonGenerationOptions theOptions) {
        CreateMapRet ret = new CreateMapRet();
        ArrayList<DungeonGenerationFloor> floors = generate(theOptions);

        int depth = theOptions.getNumFloors();
        int height = theOptions.getHeight();
        int width = theOptions.getWidth();

        ret.map = new Room[depth][height][width];
        for (int i = 0; i < depth; i++) {
            DungeonGenerationFloor floor = floors.get(depth - 1 - i); //floors are generated top down, we're placing them bottom up
            ret.characters.addAll(floor.myCharacters);
            ret.atoms.addAll(floor.myAtoms);
            for (int j = 0; j < height; j++) {
                for (int k = 0; k < width; k++) {
                    ret.map[i][j][k] = floor.myMap[j][k];
                }
            }

        }

        return ret;
    }


    private ArrayList<DungeonGenerationFloor> generate(final DungeonGenerationOptions theOptions) {
        int numFloors = theOptions.getNumFloors();
        int numPillars = theOptions.getNumPillars();
        //starting out at 4 rooms per floor
        int numRooms = 4;//myOptions.getNumRooms();

        ArrayList<DungeonGenerationFloor> ret = new ArrayList<>();
        int pillarsDelta = numFloors / numPillars;
        String[] pillarNames = { "a", "b", "c", "d" };
        int pillarI = 0;

        Random rng = new Random();

        for (int i = 0; i < numFloors; i++) {
            boolean placePillar = i % pillarsDelta == 0;

            DungeonGenerationFloor floor = generateFloor(
                    theOptions.getWidth(),
                    theOptions.getHeight(),
                    numRooms,
                    placePillar,
                    pillarNames[pillarI],
                    ret,
                    rng,
                    theOptions);

            if (i > 0) {
                DungeonGenerationFloor theFloorAbove = ret.getLast();
                floor.myAtoms.add(new Stairs(floor.myEntrance, theFloorAbove.myBossRoom));
                Stairs stairs = new Stairs(theFloorAbove.myBossRoom, floor.myEntrance);
                stairs.lock();
                theFloorAbove.myAtoms.add(stairs);
            }

            if (i == 0) {
                HeroStartPoint startPoint = new HeroStartPoint(floor.myEntrance);
                floor.myAtoms.add(startPoint);
                floor.myStartPoint = startPoint;
            }

            ret.add(floor);

            if (placePillar) {
                pillarI++;
            }
        }

        return ret;
    }
    
    private Atom addAtom(final DungeonGenerationFloor theFloor, final Atom theAtom) {
        theFloor.myAtoms.add(theAtom);
        return theAtom;
    }

    private Room addRoom(final DungeonGenerationFloor theFloor, final Room theRoom) {
        addAtom(theFloor, theRoom);
        theFloor.myRooms.add(theRoom);
        int[] coords = theRoom.getCoords();
        theFloor.myMap[coords[Atom.Y]][coords[Atom.X]] = theRoom;
        return theRoom;
    }

    private DungeonCharacter addMob(final DungeonGenerationFloor theFloor, final DungeonCharacter theCharacter) {
        addAtom(theFloor, theCharacter);
        theFloor.myCharacters.add(theCharacter);
        return theCharacter;
    }

    private Pillar addPillar(final DungeonGenerationFloor theFloor, final Pillar thePillar) {
        addAtom(theFloor, thePillar);
        theFloor.myPillar = thePillar;
        return thePillar;
    }

    private DungeonGenerationFloor generateFloor(
            final int theWidth,
            final int theHeight,
            final int theNumRooms,
            final boolean thePlacePillar,
            final String thePillarName,
            final ArrayList<DungeonGenerationFloor> theFloorsAbove,
            final Random theRNG,
            final DungeonGenerationOptions theOptions
        ) {
        int x = Math.round(theWidth / 2);
        int y = Math.round(theHeight / 2);
        //we're descending into the dungeon, so later floors are lower.
        int z = theOptions.getNumFloors() - theFloorsAbove.size();

        DungeonGenerationFloor theFloorAbove = null;
        if (!theFloorsAbove.isEmpty()) {
            theFloorAbove = theFloorsAbove.getLast();
        }

        DungeonGenerationFloor floor = new DungeonGenerationFloor(theWidth, theHeight);
        theFloorsAbove.add(floor);

        if (theFloorAbove != null) {
            int[] coords = theFloorAbove.myBossRoom.getCoords();
            x = coords[Atom.X];
            y = coords[Atom.Y];
        }
        
        
        System.out.println("x " + x + " y " + y);


        Room currentRoom = addRoom(floor, new Room(new int[] { x, y, z }));
        floor.myEntrance = currentRoom;

        ArrayList<Room> border = new ArrayList<>();
        border.add(currentRoom);

        int roomsCreated = 1;
        while (roomsCreated < theNumRooms) {
            ArrayList<Room> newBorder = new ArrayList<>();
            int i = -1;
            for (Room borderRoom : border) {
                i++;
                if (roomsCreated >= theNumRooms) {
                    break;
                }
                int[] borderCoords = borderRoom.getCoords();
                Room selectRoom = null;
                int[] selectCoords = borderCoords.clone();
                boolean readded = false;

                if (borderCoords[Atom.X] + 1 < theWidth && roomsCreated < theNumRooms) {
                    selectCoords = new int[] { borderCoords[Atom.X] + 1, borderCoords[Atom.Y], borderCoords[Atom.Z] };
                    selectRoom = floor.myMap[selectCoords[Atom.Y]][selectCoords[Atom.X]];
                    if (selectRoom == null) {
                        if (theRNG.nextInt(1, 100) > 75) {
                            selectRoom = addRoom(floor, new Room(selectCoords));
                            newBorder.add(selectRoom);

                            Door usToThem = new Door(selectRoom, borderRoom);
                            floor.myAtoms.add(usToThem);
                            Door themToUs = new Door(borderRoom, selectRoom);
                            floor.myAtoms.add(themToUs);
                            usToThem.unlock();
                            themToUs.unlock();

                            roomsCreated++;
                        } else {
                            readded = true;
                        }
                    }
                }
                if (borderCoords[Atom.Y] + 1 < theHeight && roomsCreated < theNumRooms) {
                    selectCoords = new int[] { borderCoords[Atom.X], borderCoords[Atom.Y] + 1, borderCoords[Atom.Z] };
                    selectRoom = floor.myMap[selectCoords[Atom.Y]][selectCoords[Atom.X]];
                    if (selectRoom == null) {
                        if (theRNG.nextInt(1, 100) > 75) {
                            selectRoom = addRoom(floor, new Room(selectCoords));
                            newBorder.add(selectRoom);

                            Door usToThem = new Door(selectRoom, borderRoom);
                            floor.myAtoms.add(usToThem);
                            Door themToUs = new Door(borderRoom, selectRoom);
                            floor.myAtoms.add(themToUs);
                            usToThem.unlock();
                            themToUs.unlock();

                            roomsCreated++;
                        } else {
                            readded = true;
                        }
                    }
                }
                if (borderCoords[Atom.X] - 1 >= 0 && roomsCreated < theNumRooms) {
                    selectCoords = new int[] { borderCoords[Atom.X] - 1, borderCoords[Atom.Y], borderCoords[Atom.Z] };
                    selectRoom = floor.myMap[selectCoords[Atom.Y]][selectCoords[Atom.X]];
                    if (selectRoom == null) {
                        if (theRNG.nextInt(1, 100) > 75) {
                            selectRoom = addRoom(floor, new Room(selectCoords));
                            newBorder.add(selectRoom);

                            Door usToThem = new Door(selectRoom, borderRoom);
                            floor.myAtoms.add(usToThem);
                            Door themToUs = new Door(borderRoom, selectRoom);
                            floor.myAtoms.add(themToUs);
                            usToThem.unlock();
                            themToUs.unlock();

                            roomsCreated++;
                        } else {
                            readded = true;
                        }
                    }
                }
                if (borderCoords[Atom.Y] - 1 >= 0 && roomsCreated < theNumRooms) {
                    selectCoords = new int[] { borderCoords[Atom.X], borderCoords[Atom.Y] - 1, borderCoords[Atom.Z] };
                    selectRoom = floor.myMap[selectCoords[Atom.Y]][selectCoords[Atom.X]];
                    if (selectRoom == null) {
                        if (theRNG.nextInt(1, 100) > 75) {
                            selectRoom = addRoom(floor, new Room(selectCoords));
                            newBorder.add(selectRoom);

                            Door usToThem = new Door(selectRoom, borderRoom);
                            floor.myAtoms.add(usToThem);
                            Door themToUs = new Door(borderRoom, selectRoom);
                            floor.myAtoms.add(themToUs);
                            usToThem.unlock();
                            themToUs.unlock();

                            roomsCreated++;
                        } else {
                            readded = true;
                        }
                    }
                }
                if (readded) {
                    newBorder.add(borderRoom);
                }


                if (roomsCreated >= theNumRooms) {
                    floor.myBossRoom = borderRoom;
                    if (thePlacePillar) {
                        addPillar(floor, new Pillar(borderRoom, thePillarName));
                        //TODO: figure out how to track what kinds of bosses have already been used
                        addMob(floor, new Ogre(borderRoom));
                    }
                }
                
                //no monsters in the first room and no additional monsters in the boss room
                if (floor.myBossRoom != borderRoom && roomsCreated > 0) {
                    int monsterRoll = theRNG.nextInt(100);
                    if (monsterRoll < theOptions.getNonBossRoomMonsterChance()) {
                        addMob(floor, new Skeleton(borderRoom));
                    }
                }

            }
            border = newBorder;
        }
        return floor;

    }
}
