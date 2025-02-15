package dungeon;

public abstract class DungeonCharacter {
    private double health;
    private double damage;
    private String name;
    private double range;
    private int attackSpeed;
    private double hitChance;

    
    public DungeonCharacter(double health, double damage, String name, double range, int attackSpeed, double hitChance) {
        this.health = health;
        this.damage = damage;
        this.name = name;
        this.range = range;
        this.attackSpeed = attackSpeed;
        this.hitChance = hitChance;
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

    public String getName() {
        return name;
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
