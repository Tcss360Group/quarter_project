package dungeon;

import java.util.ArrayList;

public class Priestess extends Hero {
    private static final double DEFAULT_HEALTH = 75.0;
    private static final double DEFAULT_DAMAGE = 0.0; 
    private static final String NAME = "Priestess";
    private static final double DEFAULT_RANGE = 1.0;
    private static final int ATTACK_SPEED = 5;
    private static final double HIT_CHANCE = 0.7;
    private static final int MIN_DAMAGE = 25;
    private static final int MAX_DAMAGE = 45;
    private static final double BLOCK_CHANCE = 0.3;
    private static final double HEAL_MIN = 20.0;
    private static final double HEAL_MAX = 40.0;

    
    public Priestess(final Atom theLoc) {
        super(theLoc, NAME, DEFAULT_HEALTH, DEFAULT_DAMAGE, DEFAULT_RANGE, ATTACK_SPEED, HIT_CHANCE, BLOCK_CHANCE, "Healing Touch");
    }

    @Override
    public double attack() {
        if (Math.random() < HIT_CHANCE) {
            return Math.round(MIN_DAMAGE + Math.random() * (getSumWeaponMod() + MAX_DAMAGE - MIN_DAMAGE));
        }
        return 0;
    }

    public double heal() {
        double healAmount = Math.round(HEAL_MIN + Math.random() * (HEAL_MAX - HEAL_MIN));
        System.out.println("Priestess uses Healing Touch!");
        System.out.println("Healing amount: " + healAmount);
        return healAmount;
    }

    @Override
    public ArrayList<String> useSpecialSkill(final DungeonCharacter theTarget) {
        ArrayList<String> ret = new ArrayList<>();
        double chance = Math.random();
        if (chance < 0.5) {
            ret.add("Priestess uses her special healing skill!");
            addHealth(heal());
        } else {
            ret.add("Priestess performs a normal attack.");
            double damage = attack();
            theTarget.addHealth(-damage);
            ret.add("Damage dealt: " + damage);
        }
        return ret;
    }
}
