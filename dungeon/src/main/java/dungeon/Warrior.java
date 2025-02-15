package dungeon;

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

    public Warrior() {
        super(DEFAULT_HEALTH, DEFAULT_DAMAGE, NAME, DEFAULT_RANGE, ATTACK_SPEED, HIT_CHANCE, BLOCK_CHANCE, "Crushing Blow");
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
        if (Math.random() < 0.4) {
            System.out.println("Warrior uses Crushing Blow!");
            System.out.println("Damage dealt: " + (75 + Math.random() * 100));
        } else {
            System.out.println("Crushing Blow missed!");
        }
    }
}
