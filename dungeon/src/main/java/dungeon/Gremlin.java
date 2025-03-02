package dungeon;

import java.util.Random;

public class Gremlin extends Monster {
    private static final double GREMLIN_HEALTH = 70.0;
    private static final double GREMLIN_DAMAGE = 22.5; 
    private static final String GREMLIN_NAME = "Gremlin";
    private static final double GREMLIN_RANGE = 1.2; 
    private static final int ATTACK_SPEED = 5;
    private static final double HIT_CHANCE = 0.8;
    private static final double CHANCE_TO_HEAL = 0.4;
    private static final int MIN_HEAL = 20;
    private static final int MAX_HEAL = 40;
    private static final int MIN_DAMAGE = 20;       
    private static final int MAX_DAMAGE = 40;   

    public Gremlin(final Atom theLoc) {
        super(theLoc, GREMLIN_NAME, GREMLIN_HEALTH, GREMLIN_DAMAGE, GREMLIN_RANGE, ATTACK_SPEED, HIT_CHANCE, CHANCE_TO_HEAL, MIN_HEAL, MAX_HEAL);
    }

    @Override
    public double attack() {
        Random random = new Random();
        if (random.nextDouble() < HIT_CHANCE) {
            int damageDealt = random.nextInt(MAX_DAMAGE - MIN_DAMAGE + 1) + MIN_DAMAGE;  // Random value between 20 and 40
            System.out.println(getName() + " swiftly strikes and deals " + damageDealt + " damage!");
            return damageDealt;
        } else {
            System.out.println(getName() + " misses the attack!");
            return 0;
        }
    }
}
