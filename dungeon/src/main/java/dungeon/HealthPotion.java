package dungeon;

/**
 * A health potion that restores health when used.
 */
public class HealthPotion extends Item {

    // Default sprite for the health potion
    private static final GameSprite HEALTH_POTION_DEFAULT_SPRITE = 
        new GameSprite("health_potion.png", 0.0f, 0.0f, 0.0f);

    private final int healAmount;

    /**
     * Constructor for a HealthPotion.
     *
     * @param theLoc The location of the potion.
     * @param theName The name of the potion.
     * @param theType The type of potion.
     * @param theHealAmount The amount of health restored.
     */
    public HealthPotion(final Atom theLoc, final String theName, final String theType, final int theHealAmount) {
        super(theLoc, theName, theType);
        this.healAmount = theHealAmount;
    }

    /**
     * Uses the health potion, restoring health to the user.
     *
     * @param theUser The entity using the potion.
     */
    @Override
    public void use(DungeonObject theUser) {
        System.out.println(theUser.getName() + " used " + getName() + " and restored " + healAmount + " health!");
    }
}
