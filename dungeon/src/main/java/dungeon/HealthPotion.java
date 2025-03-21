package dungeon;

import java.util.Random;

/**
 * A health potion that restores health when used.
 */
public class HealthPotion extends Item {

    private static final String NAME = "Health Potion";
    private static final int HEAL_AMOUNT = 50;
    // Default sprite for the health potion
    private static final GameSprite HEALTH_POTION_DEFAULT_SPRITE = 
        new GameSprite("health_potion.png", 0.0f, 0.0f, 0.0, 0.2, 0.2, 15.0f);

    private final int healAmount;

    /**
     * Constructor for a HealthPotion.
     *
     * @param theLoc The location of the potion.
     * @param theName The name of the potion.
     * @param theType The type of potion.
     * @param theHealAmount The amount of health restored.
     */
    public HealthPotion(final Atom theLoc, final String theName, final int theHealAmount) {
        super(theLoc, theName, "");
        this.healAmount = theHealAmount;
        setSprite(HEALTH_POTION_DEFAULT_SPRITE.clone());
    }

    public HealthPotion(final Atom theLoc, final Random theRNG, final int theLowerBound, final int theHigherBound) {
        this(theLoc, NAME, theRNG.nextInt(theLowerBound, theHigherBound));
    }

    public HealthPotion(final Atom theLoc) {
        this(theLoc, NAME, HEAL_AMOUNT);
    }
    
    /**
     * Uses the health potion, restoring health to the user.
     *
     * @param theUser The entity using the potion.
     */
    @Override
    public void use(final DungeonCharacter theUser) {
        theUser.addHealth(healAmount);
        System.out.println(theUser.getName() + " used " + getName() + " and restored " + healAmount + " health!");
        move(null); //move into nullspace and get GC'd
    }
}
