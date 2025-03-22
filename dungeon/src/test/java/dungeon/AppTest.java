package dungeon;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import dungeon.Controller.GameController;

/**
 * Unit test suite for the model
 */
public class AppTest 
{
    /// returns true iff theContents has the same coords as theLoc
    public static boolean coordsMatch(final Atom theLoc, final Atom theContents) {
        return theLoc.getCoords().equals(theContents.getCoords());
    }

    /// returns true iff theContents is in theLoc.contents
    public static boolean isInContents(final Atom theLoc, final Atom theContents) {
        return theLoc.getContents().indexOf(theContents) != -1;
    }
    
    /// returns true iff theContents.getLoc() == theLoc
    public static boolean isLocOf(final Atom theLoc, final Atom theContents) {
        return theContents.getLoc() == theLoc;
    }

    /// returns true iff (theContent's loc == theLoc) == (theContents is in theLoc.contents)
    /// either both are true or neither is true
    public static boolean verifyContentsAndLocMatch(final Atom theLoc, final Atom theContents) {
        return isLocOf(theLoc, theContents) == isInContents(theLoc, theContents);
    }

    /// returns true depending on whether theSecondShouldBeInFirst is true, if it is then checks if that is the case. if its not checks if its not
    /// the case.
    public static boolean verifyLocationLogic(final Atom theFirst, final Atom theSecond, final boolean theSecondShouldBeInFirst) throws IllegalStateException {
        if(!verifyContentsAndLocMatch(theFirst, theSecond)) { //always a failure
            throw new IllegalStateException("somehow " + theFirst.toString() + " and " + theSecond.toString() + " have mismatching locs (" + theFirst.getLoc().toString() + ", " + theSecond.getLoc().toString() + ") and contents!");
        }

        if(isLocOf(theFirst, theSecond) != theSecondShouldBeInFirst) {
            return false;
        }
        if(isInContents(theFirst, theSecond) != theSecondShouldBeInFirst) {
            return false;
        }

        return true;
    }

    @Test
    public void testBasicMovement() {
        Room roomA = new Room(new int[]{0,0,0}, "room A");
        TestObj phys = new TestObj(roomA, "the physical object");
        assertTrue(verifyLocationLogic(roomA, phys, true));

        phys.move(null);
        assertTrue(phys.getLoc() == null);

        Room roomB = new Room(new int[]{0,0,1}, "room B");
        phys.move(roomB);
        assertTrue(verifyLocationLogic(roomB, phys, true));
        assertTrue(verifyLocationLogic(roomA, phys, false));

        Door roomBtoA = new Door(roomB, "to A", roomA);
        Door roomAtoB = new Door(roomA, "to B", roomB);
        assertTrue(verifyLocationLogic(roomB, roomBtoA, true));
        assertTrue(roomBtoA.getDest() == roomA);
        assertTrue(verifyLocationLogic(roomA, roomAtoB, true));
        assertTrue(roomAtoB.getDest() == roomB);

        phys.move(roomA);
        assertTrue(verifyLocationLogic(roomB, phys, true));
        assertTrue(verifyLocationLogic(roomA, phys, false));
        roomBtoA.unlock();
        roomAtoB.unlock();

        phys.move(roomA);
        assertTrue(verifyLocationLogic(roomB, phys, false));
        assertTrue(verifyLocationLogic(roomA, phys, true));

        roomAtoB.unlock();
        roomBtoA.unlock();
        phys.move(roomB); 
        assertTrue(verifyLocationLogic(roomB, phys, true));
        assertTrue(verifyLocationLogic(roomA, phys, false));
    }

    @Test
    public void testWorldGenPillars() throws Exception {

        Main.generateWorld(40, 4, true, false); 
        GameController controller = Main.getController();
        ArrayList<Pillar> pillars = controller.getPillars();
        assertTrue(pillars.size() == 4);
    }

    @Test
    public void testWorldGenNumRoomsPerFloor() throws Exception {

        Main.generateWorld(40, 4, true, false); 
        GameController controller = Main.getController();
        Room[][][] map = controller.getMap();
        for(int z = 0; z < map.length; z++) {
            int numRooms = 0;
            for(int y = 0; y < map[z].length; y++) {
                for(int x = 0; x < map[z][y].length; x++) {
                    Room room = map[z][y][x];
                    if(room == null) {
                        continue;
                    }
                    numRooms++;
                }
            }
            assertTrue(numRooms <= 40);
        }
    }
}
