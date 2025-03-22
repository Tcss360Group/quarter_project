package dungeon;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;

public class Skeleton extends Monster {
    private double range;
    private int attackSpeed;
    private double hitChance;
    private double healChance;
    private int minHeal;
    private int maxHeal;
    private int minDamage;
    private int maxDamage;

    public Skeleton(final Atom theLoc) {
        super(theLoc, "Skeleton", 0, 0, 0, 0, 0, 0, 0, 0); // Temporary placeholder values
        fetchStatsFromDatabase(); // Load stats dynamically
    }

    private void fetchStatsFromDatabase() {
        String sql = "SELECT * FROM Characters WHERE name = 'Skeleton'";
        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                setName(rs.getString("name"));
                setHealth(rs.getDouble("health"));
                setDamage(rs.getDouble("damage"));
                this.range = rs.getDouble("range");
                this.attackSpeed = rs.getInt("attack_speed");
                this.hitChance = rs.getDouble("hit_chance");
                this.healChance = rs.getDouble("heal_chance");
                this.minHeal = rs.getInt("min_heal");
                this.maxHeal = rs.getInt("max_heal");
                this.minDamage = rs.getInt("min_damage");
                this.maxDamage = rs.getInt("max_damage");
            }
        } catch (Exception e) {
            System.out.println("Error fetching Skeleton stats from database: " + e.getMessage());
            e.printStackTrace();
        }
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