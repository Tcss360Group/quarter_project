package dungeon;

public abstract class Hero extends DungeonCharacter {
    private final double blockChance;
    private final String specialSkill;

    public Hero(double health, double damage, String name, double range, int attackSpeed, double hitChance, 
                double blockChance, String specialSkill) {
        
        super(health, damage, name, range, attackSpeed, hitChance);
        this.blockChance = blockChance;
        this.specialSkill = specialSkill;
    }

    public double getBlockChance() {
        return blockChance;
    }

    public String getSpecialSkill() {
        return specialSkill;
    }

    public abstract void useSpecialSkill();

    @Override
    public double attack() {
        return getDamage();
    }
}
