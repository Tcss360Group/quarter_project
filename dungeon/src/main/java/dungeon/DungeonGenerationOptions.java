package dungeon;

public class DungeonGenerationOptions {
    private int myNumRooms;
    private int myNumFloors;
    private int myNumPillars;
    private int myNonBossRoomMonsterChance;
    private String[] myPillarNames;
    private int myWidth;
    private int myHeight;

    public DungeonGenerationOptions(
            final int theNumRooms,
            final int theNumFloors,
            final int theNumPillars,
            final int theNonBossRoomMonsterChance,
            final String[] thePillarNames,
            final int theWidth,
            final int theHeight
        ) {
        myNumRooms = theNumRooms;
        myNumFloors = theNumFloors;
        myNumPillars = theNumPillars;
        myNonBossRoomMonsterChance = theNonBossRoomMonsterChance;
        myPillarNames = thePillarNames.clone();
        myWidth = theWidth;
        myHeight = theHeight;
    }

    public int getNumRooms() {
        return myNumRooms;
    }
    public int getNumFloors() {
        return myNumFloors;
    }
    public int getNumPillars() {
        return myNumPillars;
    }

    public int getNonBossRoomMonsterChance() {
        return myNonBossRoomMonsterChance;
    }

    public String[] getPillarNames() {
        return myPillarNames.clone();
    }
    
    public int getWidth() {
        return myWidth;
    }
    public int getHeight() {
        return myHeight;
    }
    
    public void setNumPillars(int myNumPillars) {
        this.myNumPillars = myNumPillars;
    }
    public void setNumFloors(int myNumFloors) {
        this.myNumFloors = myNumFloors;
    }
    public void setNumRooms(int myNumRooms) {
        this.myNumRooms = myNumRooms;
    }

    public void setNonBossRoomMonsterChance(final int myNonBossRoomMonsterChance) {
        this.myNonBossRoomMonsterChance = myNonBossRoomMonsterChance;
    }

    public void setPillarNames(final String[] thePillarNames) {
        myPillarNames = thePillarNames.clone();
    }
    public void setWidth(int theWidth) {
        this.myWidth = theWidth;
    }
    public void setHeight(int theHeight) {
        this.myHeight = theHeight;
    }
}
