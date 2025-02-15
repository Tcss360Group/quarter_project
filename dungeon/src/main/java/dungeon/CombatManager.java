package dungeon;

import java.util.Random;

public class CombatManager {

    public static void battle(Hero hero, Monster monster) {
        System.out.println(hero.getName() + " enters combat with " + monster.getName());

        while (hero.getHealth() > 0 && monster.getHealth() > 0) {
            if (attackHits(hero.getHitChance())) {
                monster.setHealth(monster.getHealth() - hero.attack());
                System.out.println(hero.getName() + " attacks " + monster.getName() + " for " + hero.attack() + " damage.");
            } else {
                System.out.println(hero.getName() + " misses!");
            }

            if (monster.getHealth() > 0 && attackHits(monster.getHitChance())) {
                double damageDealt = monster.attack();
                
                if (new Random().nextDouble() < hero.getBlockChance()) {
                    System.out.println(hero.getName() + " blocks the attack!");
                    damageDealt *= 0.5; 
                }

                hero.setHealth(hero.getHealth() - damageDealt);
                System.out.println(monster.getName() + " attacks " + hero.getName() + " for " + damageDealt + " damage.");
            } else {
                System.out.println(monster.getName() + " misses!");
            }
        }

        if (hero.getHealth() <= 0) {
            System.out.println(hero.getName() + " has been defeated.");
        } else {
            System.out.println(monster.getName() + " has been defeated.");
        }
    }

    private static boolean attackHits(double hitChance) {
        return new Random().nextDouble() < hitChance;
    }
}
