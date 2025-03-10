package dungeon;

public abstract class Hero extends DungeonCharacter {
    private final double blockChance;
    private final String specialSkill;

    public Hero(final Atom theLoc, final String theName, final double health, final double damage, final double range, final int attackSpeed, final double hitChance, 
                final double blockChance, final String specialSkill) {
        
        super(theLoc, theName, health, damage,  range, attackSpeed, hitChance);
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
