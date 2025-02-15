package dungeon;

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

    public Thief() {
        super(DEFAULT_HEALTH, DEFAULT_DAMAGE, NAME, DEFAULT_RANGE, ATTACK_SPEED, HIT_CHANCE, BLOCK_CHANCE, "Surprise Attack");
    }

    @Override
    public double attack() {
        if (Math.random() < HIT_CHANCE) {
            return MIN_DAMAGE + Math.random() * (MAX_DAMAGE - MIN_DAMAGE);
        }
        return 0;
    }

    @Override
    public void useSpecialSkill() {
        double chance = Math.random();
        if (chance < 0.4) {
            System.out.println("Thief executes a Surprise Attack and gets an extra turn!");
            System.out.println("Damage dealt: " + attack());
            System.out.println("Extra attack damage: " + attack());
        } else if (chance < 0.6) {
            System.out.println("Thief got caught! No attack this turn.");
        } else {
            System.out.println("Thief performs a normal attack.");
            System.out.println("Damage dealt: " + attack());
        }
    }
}
