package dungeon;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.ModuleLayer.Controller;

import dungeon.Controller.GameController;

public class Main implements Serializable {

    private static final long serialVersionUID = 1L;

    private static GameController controller = null;
    private static boolean debugConcurrency = false;

    public static void SerializedtheWorld (String FilePath){
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FilePath))) {
            oos.writeObject(controller);
            System.out.println("All objects serialized!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void deSerializetheWorld(String FilePath){
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FilePath))) {
            GameController deserializedObject = (GameController) ois.readObject();

            // Replace the original reference with the deserialized object
            controller = deserializedObject;
        } catch(Exception e){
            e.printStackTrace();

        }
    }
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
        DatabaseSetup.createTable();
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
