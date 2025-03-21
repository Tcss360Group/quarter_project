package dungeon;

public class DungeonGenerationOptions {

    public static final int DEFAULT_WIDTH = 30;
    public static final int DEFAULT_HEIGHT = 30;
    public static final int DEFAULT_NUM_ROOMS = 40;
    public static final int DEFAULT_NUM_FLOORS = 4;
    public static final int DEFAULT_NUM_PILLARS = 4;
    public static final int DEFAULT_NON_BOSSROOM_MONSTER_CHANCE = 25;
    public static final int DEFAULT_ROOM_GENERATION_CHANCE = 33;
    public static final int DEFAULT_HEALTH_POTION_GENERATION_CHANCE = 10;
    public static final int DEFAULT_SWORD_GENERATION_CHACNE = 5;
    public static final String[] DEFAULT_PILLAR_NAMES = new String[]{"Abstraction", "Encapsulation", "Inheritance", "Polymorphism"};

    private int myNumRooms;
    private int myNumFloors;
    private int myNumPillars;
    private int myNonBossRoomMonsterChance;
    private int myRoomGenerationChance;
    private int myHealthPotionGenerationChance;
    private int mySwordGenerationChance;
    private String[] myPillarNames;
    private int myWidth;
    private int myHeight;

    public DungeonGenerationOptions() {
        myNumRooms = DEFAULT_NUM_ROOMS;
        myNumFloors = DEFAULT_NUM_FLOORS;
        myNumPillars = DEFAULT_NUM_PILLARS;
        myNonBossRoomMonsterChance = DEFAULT_NON_BOSSROOM_MONSTER_CHANCE;
        myRoomGenerationChance = DEFAULT_ROOM_GENERATION_CHANCE;
        myHealthPotionGenerationChance = DEFAULT_HEALTH_POTION_GENERATION_CHANCE;
        mySwordGenerationChance = DEFAULT_SWORD_GENERATION_CHACNE;
        myPillarNames = DEFAULT_PILLAR_NAMES.clone();
        myWidth = DEFAULT_WIDTH;
        myHeight = DEFAULT_HEIGHT;
    }

    public DungeonGenerationOptions(
            final int theNumRooms,
            final int theNumFloors,
            final int theNumPillars,
            final int theNonBossRoomMonsterChance,
            final int theRoomGenerationChance,
            final int theHealthPotionGenerationChance,
            final int theSwordGenerationChance,
            final String[] thePillarNames,
            final int theWidth,
            final int theHeight
        ) {
        myNumRooms = theNumRooms;
        myNumFloors = theNumFloors;
        myNumPillars = theNumPillars;
        myNonBossRoomMonsterChance = theNonBossRoomMonsterChance;
        myRoomGenerationChance = theRoomGenerationChance;
        myHealthPotionGenerationChance = theHealthPotionGenerationChance;
        mySwordGenerationChance = theSwordGenerationChance;
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
    public int getRoomGenerationChance() {
        return myRoomGenerationChance;
    }
    public int getHealthPotionGenerationChance() {
        return myHealthPotionGenerationChance;
    }
    public int getSwordGenerationChance() {
        return mySwordGenerationChance;
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
    
    public void setSwordGenerationChance(final int theChance) {
        mySwordGenerationChance = theChance;
    }
    public void setRoomGenerationChance(final int theChance) {
        myRoomGenerationChance = theChance;
    }
    public void setHealthPotionGenerationChance(final int theChance) {
        myHealthPotionGenerationChance = theChance;
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
