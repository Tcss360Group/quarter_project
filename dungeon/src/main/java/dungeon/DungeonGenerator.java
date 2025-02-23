package dungeon;

import java.util.ArrayList;
import java.util.Random;

/**
 * handles generating the world
 */
public final class DungeonGenerator {

    public class DungeonGenerationFloor {
        ArrayList<Room> myRooms;
        Room[][] myMap;
        Room myBossRoom;
        Room myEntrance;
        BossRoomChallenge myChallenge;
        Pillar myPillar;
        ArrayList<DungeonCharacter> myCharacters;

        DungeonGenerationFloor(final int theWidth, final int theHeight) {
            myRooms = new ArrayList<>();
            myMap = new Room[theHeight][theWidth];
            myBossRoom = null;
            myEntrance = null;
            myChallenge = null;
            myPillar = null;
            myCharacters = new ArrayList<>();
        }

    }

    public enum BossRoomChallenge {
        NO_CHALLENGE,
        BOSS,
    }

    public Room[][][] createMap(final DungeonGenerationOptions theOptions, final GameController theGameController) {
        ArrayList<DungeonGenerationFloor> floors = generate(theOptions, theGameController);

        int depth = theOptions.getNumFloors();
        int height = theOptions.getHeight();
        int width = theOptions.getWidth();

        Room[][][] ret = new Room[depth][height][width];
        for (int i = 0; i < depth; i++) {
            DungeonGenerationFloor floor = floors.get(depth - 1 - i); //floors are generated top down, we're placing them bottom up
            for (int j = 0; j < height; j++) {
                for (int k = 0; k < width; k++) {
                    ret[i][j][k] = floor.myMap[j][k];
                }
            }
        }

        return ret;
    }

    private ArrayList<DungeonGenerationFloor> generate(final DungeonGenerationOptions theOptions, final GameController theGameController) {
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
                    pillarNames[pillarI],
                    ret,
                    rng,
                    theOptions,
                    theGameController);

            if (i > 0) {
                DungeonGenerationFloor theFloorAbove = ret.getLast();
                Stairs usToThem = new Stairs(floor.myEntrance, theFloorAbove.myBossRoom);
                Stairs themToUs = new Stairs(theFloorAbove.myBossRoom, floor.myEntrance);
            }

            ret.add(floor);

            if (placePillar) {
                pillarI++;
            }
        }

        return ret;
    }
    

    private DungeonGenerationFloor generateFloor(
            final int theWidth,
            final int theHeight,
            final int theNumRooms,
            final String thePillarName,
            final ArrayList<DungeonGenerationFloor> theFloorsAbove,
            final Random theRNG,
            final DungeonGenerationOptions theOptions,
            final GameController theGameController
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
        
        Room currentRoom = new Room(new int[]{x, y, z});
        floor.myRooms.add(currentRoom);
        floor.myEntrance = currentRoom;
        floor.myMap[y][x] = currentRoom;

        Dir dirToMove = Dir.N;

        if (y == theHeight - 1) {
            dirToMove = dirToMove.clockwise();
        }
        if (x == theWidth - 1) {
            dirToMove = dirToMove.clockwise();
        }

        System.out.println("dir: " + dirToMove.toString() + " x: " + x + " y: " + y + " width: " + theWidth + " height: " + theHeight);

        for (int i = 1; i < theNumRooms; i++) {
            switch (dirToMove) {
                case N -> y++;
                case E -> x++;
                case S -> y--;
                case W -> x--;
            }
            dirToMove = dirToMove.clockwise();
            System.out.println("dir: " + dirToMove.toString() + " x: " + x + " y: " + y + " width: " + theWidth + " height: " + theHeight);

            currentRoom = new Room(new int[]{x, y, 0});

            floor.myMap[y][x] = currentRoom;

            Room comp;
            if (y < theHeight - 1) {
                comp = floor.myMap[y + 1][x];
                Door usToThem = new Door(currentRoom, comp);
                Door themToUs = new Door(comp, currentRoom);
                usToThem.unlock();
                themToUs.unlock();
            }
            if (x < theWidth - 1) {
                comp = floor.myMap[y][x+1];
                Door usToThem = new Door(currentRoom, comp);
                Door themToUs = new Door(comp, currentRoom);
                usToThem.unlock();
                themToUs.unlock();
            }
            if (y > 0) {
                comp = floor.myMap[y - 1][x];
                Door usToThem = new Door(currentRoom, comp);
                Door themToUs = new Door(comp, currentRoom);
                usToThem.unlock();
                themToUs.unlock();
            }
            if (x > 0) { 
                comp = floor.myMap[y][x - 1];
                Door usToThem = new Door(currentRoom, comp);
                Door themToUs = new Door(comp, currentRoom);
                usToThem.unlock();
                themToUs.unlock();
            }
            
            if (i == theNumRooms - 1) {
                floor.myBossRoom = currentRoom;
                if (thePillarName != null) {
                    Pillar pillar = new Pillar(currentRoom, thePillarName);
                    floor.myPillar = pillar;
                    //TODO: figure out how to track what kinds of bosses have already been used
                    Ogre boss = new Ogre(currentRoom);
                    floor.myCharacters.add(boss);
                }
            }
            
            //no monsters in the first room and no additional monsters in the boss room
            if (floor.myBossRoom != currentRoom && i > 0) {
                int monsterRoll = theRNG.nextInt(100);
                if (monsterRoll < theOptions.getNonBossRoomMonsterChance()) {
                    Skeleton skelly = new Skeleton(currentRoom);
                    floor.myCharacters.add(skelly);
                }
            }
        }


        return floor;
    }
}
