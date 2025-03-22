package dungeon;

import java.io.Serializable;
import java.util.Random;

public class MonsterFactory implements Serializable{
    private static final long serialVersionUID = 1L;

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
            case "dragon" -> {
                return new Dragon(theLoc);
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
