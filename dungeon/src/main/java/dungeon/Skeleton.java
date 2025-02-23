package dungeon;

import java.util.Random;

public class Skeleton extends Monster {
    private static final double SKELETON_HEALTH = 100.0;
    private static final double SKELETON_DAMAGE = 40.0;  
    private static final double SKELETON_RANGE = 1.0;   
    private static final String SKELETON_NAME = "Skeleton"; 
    private static final int ATTACK_SPEED = 3;
    private static final double HIT_CHANCE = 0.8;
    private static final double CHANCE_TO_HEAL = 0.3;
    private static final int MIN_HEAL = 30;
    private static final int MAX_HEAL = 50;
    private static final int MIN_DAMAGE = 30;       
    private static final int MAX_DAMAGE = 50;     

    public Skeleton(final Atom theLoc) {
        super(theLoc, SKELETON_NAME, SKELETON_HEALTH, SKELETON_DAMAGE, SKELETON_RANGE, ATTACK_SPEED, HIT_CHANCE, CHANCE_TO_HEAL, MIN_HEAL, MAX_HEAL);
    }

    @Override
    public double attack() {
        Random random = new Random();
        if (random.nextDouble() < HIT_CHANCE) {
            int damageDealt = random.nextInt(MAX_DAMAGE - MIN_DAMAGE + 1) + MIN_DAMAGE;  // Random value between 30 and 50
            System.out.println(getName() + " slashes with its sword and deals " + damageDealt + " damage!");
            return damageDealt;
        } else {
            System.out.println(getName() + " swings and misses!");
            return 0;
        }
    }
}
