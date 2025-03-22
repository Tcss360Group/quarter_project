package dungeon;

import dungeon.Controller.GameController;

public class Main {

    private static GameController controller = null;
    private static boolean debugConcurrency = false;

    private static void setController(final GameController theController) {
        controller = theController;
    }

    public static GameController getController() {
        return controller;
    }

    public static boolean getDebugConcurrency() {
        return debugConcurrency;
    }

    public static void main(String[] args) throws Exception {
        try {
            generateWorld(40, 4, false, true); 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void generateWorld(final int theRoomsPerFloor, final int theNumFloors, final boolean theIsHeadLess, final boolean start) throws Exception {

        int size = theRoomsPerFloor;
        DungeonGenerationOptions options = new DungeonGenerationOptions();
        options.setNumRooms(theRoomsPerFloor);
        options.setNumFloors(theNumFloors);
        options.setNumPillars(4);
        options.setNonBossRoomMonsterChance(30);
        options.setWidth(size);
        options.setHeight(size);
        GameController controller = new GameController(options, theIsHeadLess);
        setController(controller);
        controller.init();
        if(start) {
            try {
                controller.start();
            } catch(Exception e) {
                throw e;
            }
        }
    }

}
