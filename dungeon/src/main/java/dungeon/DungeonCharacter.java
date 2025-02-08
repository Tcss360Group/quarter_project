package dungeon;

public abstract class DungeonCharacter extends Movable {
    private double health;
    private double damage;
    private String name;
    private double range;
    private int attackSpeed;
    private double hitChance;

    public DungeonCharacter() {
        // Default constructor implementation (if needed)
    }

    // getters and setters
    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public String getName() {
        return name;
    }

    public double getRange() {
        return range;
    }

    public int getAttackSpeed() {
        return attackSpeed;
    }

    // Methods
    public abstract double attack();

    public abstract double attackBehavior();
}
