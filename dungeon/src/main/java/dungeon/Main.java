package dungeon;

import dungeon.Controller.GameController;

public class Main {

    private static GameController controller = null;

    private static void setController(final GameController theController) {
        controller = theController;
    }

    public static GameController getController() {
        return controller;
    }

<<<<<<< Updated upstream
    public static void main(String[] args) {
        testBasicWorldGen(4, 4);
=======
    public static void main(String[] args) throws Exception {
        try {
            testBasicWorldGen(12, 4); 
        } catch (Exception e) {
            e.printStackTrace();
        }
>>>>>>> Stashed changes
    }

    public static void testBasicWorldGen(final int theRoomsPerFloor, final int theNumFloors) {

        int size = theRoomsPerFloor;
        DungeonGenerationOptions options = new DungeonGenerationOptions(
                theRoomsPerFloor,
                theNumFloors,
                4,
                10,
                new String[] { "a", "b", "c", "d" },
                size,
                size);
        GameController controller = new GameController(options);
        setController(controller);
        controller.init();
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
