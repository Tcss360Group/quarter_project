package dungeon;

import java.util.Random;

public class Room extends Atom {
    private String roomName;

    public Room(int[] coordinates, String roomName) {
        super(coordinates, roomName);  // Call the Atom constructor with coordinates and roomName
        this.roomName = roomName;
    }

    public String getRoomName() {
        return roomName;
    }

    // Method to check if a battle occurs in the room
    public boolean checkForBattle() {
        Random random = new Random();
        return random.nextDouble() < 0.5;  // 50% chance for a battle to occur
    }
}
