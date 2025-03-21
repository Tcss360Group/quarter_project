package dungeon;

import java.util.Random;

public class MonsterFactory {
    private static final Random RANDOM = new Random();

    public static Monster createMonster(final String type, final Atom theLoc) {
        switch (type.toLowerCase()) {
            case "ogre" -> {
                return new Ogre(theLoc);
            }
            case "skeleton" -> {
                return new Skeleton(theLoc);
            }
            case "gremlin" -> {
                return new Gremlin(theLoc);
            }
            default -> throw new IllegalArgumentException("Unknown monster type: " + type);
        }
    }

    // random monster gen progress
    public static Monster createRandomMonster(final Atom theLoc) {
        String[] monsterTypes = {"ogre", "skeleton", "gremlin"};
        return createRandomMonster(theLoc, monsterTypes);
    }
    public static Monster createRandomMonster(final Atom theLoc, final String[] theTypes) {
        String randomType = theTypes[RANDOM.nextInt(theTypes.length)];
        return createMonster(randomType, theLoc);
    }
}
