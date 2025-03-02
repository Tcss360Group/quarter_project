package dungeon;

import java.util.Random;

public abstract class Monster extends DungeonCharacter {
    private final double chanceToHeal;
    private final int minHealPoints;
    private final int maxHealPoints;

    public Monster(final Atom theLoc, final String theName, final double health, final double damage, final double range, final int attackSpeed, final double hitChance, 
                   final double chanceToHeal, final int minHealPoints, final int maxHealPoints) {
        super(theLoc, theName, health, damage, range, attackSpeed, hitChance);
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
            setHealth(getHealth() + healAmount);  // Add healing points
            System.out.println(getName() + " heals for " + healAmount + " points!");
        }
    }

    @Override
    public void setHealth(double health) {
        // Ensure health doesn't go below 0
        health = Math.max(health, 0);

        // Optionally, cap the health at a maximum value if needed
        // Example: If you want max health to be 200, you could do:
        // health = Math.min(health, 200);

        super.setHealth(health);
        if (health > 0) {
            heal();  // If health is still positive, try healing
        }
    }

    public void specialMonsterAbility() {
        // Implement special ability for the monster
    }

    @Override
    public double attack() {
        return getDamage();
    }
}
