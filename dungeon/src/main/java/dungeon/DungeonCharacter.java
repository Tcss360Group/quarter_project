package dungeon;

public abstract class DungeonCharacter extends Movable {
    private double health;
    private double damage;
    private double range;
    private int attackSpeed;
    private double hitChance;

    
    public DungeonCharacter(final Atom theLoc, final String theName, final double health, final double damage,
            final double range, final int attackSpeed, final double hitChance) {
        super(theLoc, theName);
        this.health = health;
        this.damage = damage;
        this.range = range;
        this.attackSpeed = attackSpeed;
        this.hitChance = hitChance;
    }

    ///called every tick to make this DC perform an action
    public void pollAction() {

    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = Math.max(0, health); 
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
