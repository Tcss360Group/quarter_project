package dungeon;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;

public class Gremlin extends Monster {
    private double range;
    private int attackSpeed;
    private double hitChance;
    private double healChance;
    private int minHeal;
    private int maxHeal;
    private int minDamage;
    private int maxDamage;

    public Gremlin(final Atom theLoc) {
        super(theLoc, GREMLIN_NAME, GREMLIN_HEALTH, GREMLIN_DAMAGE, GREMLIN_RANGE, ATTACK_SPEED, HIT_CHANCE, CHANCE_TO_HEAL, MIN_HEAL, MAX_HEAL);
    }

    @Override
    public double attack() {
        Random random = new Random();
        if (random.nextDouble() < hitChance) {
            int damageDealt = random.nextInt(maxDamage - minDamage + 1) + minDamage; // Random damage between min and max
            return damageDealt;
        } else {
            return 0;
        }
    }
}