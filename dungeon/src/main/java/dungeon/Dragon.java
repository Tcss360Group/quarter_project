package dungeon;

import java.util.Random;

public class Dragon extends Monster {
    private static final double HEALTH = 2000.0;
    private static final double DAMAGE = 45.0;  
    private static final double RANGE = 1.5;   
    private static final String NAME = "Dragon"; 
    private static final int ATTACK_SPEED = 2;
    private static final double HIT_CHANCE = 0.8;
    private static final double CHANCE_TO_HEAL = 0.2;
    private static final int MIN_HEAL = 50;
    private static final int MAX_HEAL = 100;
    private static final int MIN_DAMAGE = 60;       
    private static final int MAX_DAMAGE = 100;     

    public Dragon(final Atom theLoc) {
        super(theLoc, NAME, HEALTH, DAMAGE, RANGE, ATTACK_SPEED, HIT_CHANCE, CHANCE_TO_HEAL, MIN_HEAL, MAX_HEAL);
        setSprite(new GameSprite("dragon.png", 0,0, 0,0.8,0.8,10));
    }

    @Override
    public double attack() {
        Random random = new Random();
        if (random.nextDouble() < HIT_CHANCE) {
            int damageDealt = random.nextInt(MAX_DAMAGE - MIN_DAMAGE + 1) + MIN_DAMAGE;  // Random value between 30 and 60

            return damageDealt;
        } else {

            return 0;
        }
    }

}
