package dungeon;

public class Priestess extends Hero {
    private static final double DEFAULT_HEALTH = 75.0;
    private static final int ATTACK_SPEED = 5;
    private static final double HIT_CHANCE = 0.7;
    private static final int MIN_DAMAGE = 25;
    private static final int MAX_DAMAGE = 45;
    private static final double BLOCK_CHANCE = 0.3;
    private static final double HEAL_MIN = 20.0;
    private static final double HEAL_MAX = 40.0;

    public Priestess() {
        super(BLOCK_CHANCE, "Healing Touch");
    }

    
    public double attack() {
        if (Math.random() < HIT_CHANCE) {
            return MIN_DAMAGE + Math.random() * (MAX_DAMAGE - MIN_DAMAGE);
        }
        return 0;
    }

    // healing
    public double heal() {
        double healAmount = HEAL_MIN + Math.random() * (HEAL_MAX - HEAL_MIN);
        System.out.println("Priestess uses Healing Touch!");
        System.out.println("Healing amount: " + healAmount);
        return healAmount;
    }

    // important skill
    @Override
    public void useSpecialSkill() {
        double chance = Math.random();
        if (chance < 0.5) {
            System.out.println("Priestess uses her special healing skill!");
            heal();
        } else {
            System.out.println("Priestess performs a normal attack.");
            System.out.println("Damage dealt: " + attack());
        }
    }
}
