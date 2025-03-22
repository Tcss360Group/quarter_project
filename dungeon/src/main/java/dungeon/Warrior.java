package dungeon;

import java.util.ArrayList;

public class Warrior extends Hero {
    private static final double DEFAULT_HEALTH = 125.0;
    private static final double DEFAULT_DAMAGE = 0.0;
    private static final String NAME = "Warrior";
    private static final double DEFAULT_RANGE = 1.0;
    private static final int ATTACK_SPEED = 4;
    private static final double HIT_CHANCE = 0.8;
    private static final int MIN_DAMAGE = 35;
    private static final int MAX_DAMAGE = 60;
    private static final double BLOCK_CHANCE = 0.2;

    public Warrior(final Atom theLoc) {
        super(theLoc, NAME, DEFAULT_HEALTH, DEFAULT_DAMAGE, DEFAULT_RANGE, ATTACK_SPEED, HIT_CHANCE, BLOCK_CHANCE, "Crushing Blow");
    }

    @Override
    public double attack() {
        if (Math.random() < HIT_CHANCE) {
            double damage = MIN_DAMAGE + Math.random() * (getSumWeaponMod() + MAX_DAMAGE - MIN_DAMAGE);
            return damage;  
        }
        return 0;
    }

    @Override
    public ArrayList<String> useSpecialSkill(final DungeonCharacter theTarget) {
        ArrayList<String> ret = new ArrayList<>();
        if (Math.random() < 0.4) {
            double damage = 75 + Math.random() * (getSumWeaponMod() + 100);
            damage = Math.round(damage);  
            ret.add("Warrior uses Crushing Blow!");
            ret.add("Damage dealt: " + damage);
        } else {
            ret.add("Crushing Blow missed!");
        }
        return ret;
    }
}
