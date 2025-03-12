package dungeon;

public abstract class DungeonCharacter extends Movable {

    private static final double VISION_POWER = VisionPower.NONE.power();

    private double health;
    private final double maxHealth;  // Store the initial max health
    private double damage;
    private double range;
    private int attackSpeed;
    private double hitChance;
    /// any other movable in the room with a vision power lower than this can be seen by 
    private double myVisionPower;

    public DungeonCharacter(final Atom theLoc, final String theName, final double health, final double damage,
            final double range, final int attackSpeed, final double hitChance) {
        super(theLoc, theName);
        this.maxHealth = health;  // Set maxHealth during construction
        this.health = health;  // Current health
        this.damage = damage;
        this.range = range;
        this.attackSpeed = attackSpeed;
        this.hitChance = hitChance;
        myVisionPower = VISION_POWER;
    }

    // Called every tick to make this DC perform an action
    public void pollAction() {
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = Math.max(0, health);  // Ensure health does not go below 0
    }

    public double getMaxHealth() {
        return maxHealth;  // Return the max health (unchanged value)
    }

    public double getDamage() {
        return damage;
    }

    public double getRange() {
        return range;
    }

    public int getAttackSpeed() {
        return attackSpeed;
    }

    public double getHitChance() {
        return hitChance;
    }

    public double getVisionPower() {
        return myVisionPower;
    }

    public void setVisible(final double theVisionPower) {
        myVisionPower = theVisionPower;
    }

    public abstract double attack();
}
