package dungeon;

public class Main {

    public static void main(String[] args) {
        testBasicWorldGen(4, 4);
    }

    public static void testBasicWorldGen(final int theRoomsPerFloor, final int theNumFloors) {
        GameController controller = new GameController();
        controller.init(theRoomsPerFloor, theNumFloors);
        controller.start();
    }

    public static void testBasicMovement() {
        Room roomA = new Room(new int[]{0,0,0}, "room A");
        TestObj phys = new TestObj(roomA, "the physical object");
        System.out.println( phys.getName() + " is in " + (phys.getLoc() != null ? phys.getLoc().getName() : "nullspace"));
        phys.move(null);
        System.out.println( phys.getName() + " is in " + (phys.getLoc() != null ? phys.getLoc().getName() : "nullspace"));
        Room roomB = new Room(new int[]{0,0,1}, "room B");
        phys.move(roomB);
        System.out.println( phys.getName() + " is in " + (phys.getLoc() != null ? phys.getLoc().getName() : "nullspace"));

        Door roomBtoA = new Door(roomB, "to A", roomA);
        Door roomAtoB = new Door(roomA, "to B", roomB);

        phys.move(roomA);
        System.out.println("AFTER ADDING DOORS: " + phys.getName() + " is in " + (phys.getLoc() != null ? phys.getLoc().getName() : "nullspace"));
        roomBtoA.unlock();
        phys.move(roomA);
        System.out.println("AFTER UNLOCKING B->A: " + phys.getName() + " is in " + (phys.getLoc() != null ? phys.getLoc().getName() : "nullspace"));
        roomAtoB.unlock();
        phys.move(roomA);
        System.out.println("AFTER UNLOCKING A->B: " + phys.getName() + " is in " + (phys.getLoc() != null ? phys.getLoc().getName() : "nullspace"));
    }
}
