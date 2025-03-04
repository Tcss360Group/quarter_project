package dungeon;

public abstract class DungeonCharacter extends Movable {
    private double health;
    private final double maxHealth;  // Store the initial max health
    private double damage;
    private double range;
    private int attackSpeed;
    private double hitChance;

    public DungeonCharacter(final Atom theLoc, final String theName, final double health, final double damage,
            final double range, final int attackSpeed, final double hitChance) {
        super(theLoc, theName);
        this.maxHealth = health;  // Set maxHealth during construction
        this.health = health;  // Current health
        this.damage = damage;
        this.range = range;
        this.attackSpeed = attackSpeed;
        this.hitChance = hitChance;
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

    public abstract double attack();
}
