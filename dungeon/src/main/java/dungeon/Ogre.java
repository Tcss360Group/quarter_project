package dungeon;

import java.util.Random;

public class Ogre extends Monster {
    private static final double OGRE_HEALTH = 200.0;
    private static final double OGRE_DAMAGE = 45.0;  
    private static final double OGRE_RANGE = 1.5;   
    private static final String OGRE_NAME = "Orge"; 
    private static final int ATTACK_SPEED = 2;
    private static final double HIT_CHANCE = 0.6;
    private static final double CHANCE_TO_HEAL = 0.1;
    private static final int MIN_HEAL = 30;
    private static final int MAX_HEAL = 60;
    private static final int MIN_DAMAGE = 30;       
    private static final int MAX_DAMAGE = 60;     

    public Ogre(final Atom theLoc) {
        super(theLoc, OGRE_NAME, OGRE_HEALTH, OGRE_DAMAGE, OGRE_RANGE, ATTACK_SPEED, HIT_CHANCE, CHANCE_TO_HEAL, MIN_HEAL, MAX_HEAL);
        setSprite(new GameSprite("ogre.png", 0,0, 10));
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
