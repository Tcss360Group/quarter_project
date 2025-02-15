package dungeon;

import java.util.Random;

public abstract class Monster extends DungeonCharacter {
    private final double chanceToHeal;
    private final int minHealPoints;
    private final int maxHealPoints;

    public Monster(double health, double damage, String name, double range, int attackSpeed, double hitChance, 
                   double chanceToHeal, int minHealPoints, int maxHealPoints) {
       
        super(health, damage, name, range, attackSpeed, hitChance);
        this.chanceToHeal = chanceToHeal;
        this.minHealPoints = minHealPoints;
        this.maxHealPoints = maxHealPoints;
    }

    public double getChanceToHeal() {
        return chanceToHeal;
    }

    public int getMinHealPoints() {
        return minHealPoints;
    }

    public int getMaxHealPoints() {
        return maxHealPoints;
    }

    public void heal() {
        if (new Random().nextDouble() < chanceToHeal) {
            int healAmount = new Random().nextInt(maxHealPoints - minHealPoints + 1) + minHealPoints;
            setHealth(getHealth() + healAmount);
            System.out.println(getName() + " heals for " + healAmount + " points!");
        }
    }

    @Override
    public void setHealth(double health) {
        super.setHealth(health);
        if (health > 0) {
            heal(); 
        }
    }

    public abstract void specialMonsterAbility();

    @Override
    public double attack() {
        return getDamage();
    }
}
