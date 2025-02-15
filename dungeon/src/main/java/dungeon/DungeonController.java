package dungeon;

public class DungeonController {

    public static void main( String[] args ) {
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
