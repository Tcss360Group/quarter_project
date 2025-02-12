package dungeon;

import java.util.Random;

public abstract class Monster extends DungeonCharacter {
    private double chanceToHeal;
    private int minHealPoints;
    private int maxHealPoints;

    public Monster(double health, double damage, String name, double range, int attackSpeed, double hitChance, 
                   double chanceToHeal, int minHealPoints, int maxHealPoints) {
    
        this.chanceToHeal = chanceToHeal;
        this.minHealPoints = minHealPoints;
        this.maxHealPoints = maxHealPoints;
    }

    public double getChanceToHeal() {
        return chanceToHeal;
    }

    public void setChanceToHeal(double chanceToHeal) {
        this.chanceToHeal = chanceToHeal;
    }

    public int getMinHealPoints() {
        return minHealPoints;
    }

    public void setMinHealPoints(int minHealPoints) {
        this.minHealPoints = minHealPoints;
    }

    public int getMaxHealPoints() {
        return maxHealPoints;
    }

    public void setMaxHealPoints(int maxHealPoints) {
        this.maxHealPoints = maxHealPoints;
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
}
