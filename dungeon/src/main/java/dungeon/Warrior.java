package dungeon;

public class Warrior extends Hero {
    private static final double DEFAULT_HEALTH = 125.0;
    private static final int ATTACK_SPEED = 4;
    private static final double HIT_CHANCE = 0.8;
    private static final int MIN_DAMAGE = 35;
    private static final int MAX_DAMAGE = 60;
    private static final double BLOCK_CHANCE = 0.2;

    public Warrior() {
        super(BLOCK_CHANCE, "Crushing Blow");
    }

    public double attack() {
        if (Math.random() < HIT_CHANCE) {
            return MIN_DAMAGE + Math.random() * (MAX_DAMAGE - MIN_DAMAGE);
        }
        return 0;
    }

    // important skill
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
