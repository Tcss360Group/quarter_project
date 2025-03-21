package dungeon;

import java.util.Random;

public class Room extends Atom {

    private static final String NAME = "room";

    public Room(int[] coordinates) {
        this(coordinates, NAME);  // Call the Atom constructor with coordinates and roomName
    }
    public Room(int[] coordinates, String name) {
        super(coordinates, name);  // Call the Atom constructor with coordinates and roomName
        GameSprite newSprite = new GameSprite("temp_room.png", 0, 0, 0);
        newSprite.setWidth(1.0);
        newSprite.setHeight(1.0);
        setSprite(newSprite);
        
    }


    // Method to check if a battle occurs in the room
    public boolean checkForBattle() {
        Random random = new Random();
        return random.nextDouble() < 0.5;  // 50% chance for a battle to occur
    }
}
