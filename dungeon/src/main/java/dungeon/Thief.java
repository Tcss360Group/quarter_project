package dungeon;

import java.util.ArrayList;

public class Thief extends Hero {
    private static final double DEFAULT_HEALTH = 75.0;
    private static final double DEFAULT_DAMAGE = 0.0;
    private static final String NAME = "Thief";
    private static final double DEFAULT_RANGE = 1.0;
    private static final int ATTACK_SPEED = 6;
    private static final double HIT_CHANCE = 0.8;
    private static final int MIN_DAMAGE = 20;
    private static final int MAX_DAMAGE = 40;
    private static final double BLOCK_CHANCE = 0.4;

    public Thief(final Atom theLoc) {
        super(theLoc, NAME, DEFAULT_HEALTH, DEFAULT_DAMAGE, DEFAULT_RANGE, ATTACK_SPEED, HIT_CHANCE, BLOCK_CHANCE, "Surprise Attack");
    }

    @Override
    public double attack() {
        if (Math.random() < HIT_CHANCE) {
            return Math.round(MIN_DAMAGE + Math.random() * (getSumWeaponMod() + MAX_DAMAGE - MIN_DAMAGE));
        }
        return 0;
    }

    @Override
    public ArrayList<String> useSpecialSkill(final DungeonCharacter theTarget) {
        ArrayList<String> ret = new ArrayList<>();
        double chance = Math.random();
        if (chance < 0.4) {
            ret.add("Thief executes a Surprise Attack and gets an extra turn!");
            double d1 = attack();
            theTarget.addHealth(-d1);
            ret.add("Damage dealt: " + d1);
            double d2 = attack();
            theTarget.addHealth(-d2);
            ret.add("Extra attack damage: " + d2);
        } else if (chance < 0.6) {
            ret.add("Thief got caught! No attack this turn.");
        } else {
            ret.add("Thief performs a normal attack.");
            double d = attack();
            theTarget.addHealth(-d);
            ret.add("Damage dealt: " + d);
        }
        return ret;
    }
}
