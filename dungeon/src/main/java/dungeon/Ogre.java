package dungeon;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;

public class Ogre extends Monster {
    private double range;
    private int attackSpeed;
    private double hitChance;
    private double healChance;
    private int minHeal;
    private int maxHeal;
    private int minDamage;
    private int maxDamage;

    public Ogre(final Atom theLoc) {
        super(theLoc, "Ogre", 0, 0, 0, 0, 0, 0, 0, 0); // Temporary placeholder values
        setSprite(new GameSprite("ogre.png", 0,0, 10));
        fetchStatsFromDatabase(); // Load stats dynamically from the database
    }

    private void fetchStatsFromDatabase() {
        String sql = "SELECT * FROM Characters WHERE name = 'Ogre'";
        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                setName(rs.getString("name"));
                setHealth(rs.getDouble("health"));
                setDamage(rs.getDouble("damage"));
                setRange(rs.getDouble("range"));
                setSpeed(rs.getInt("attack_speed"));
                sethitChance(rs.getDouble("hit_chance"));
                sethealChance(rs.getDouble("heal_chance"));
                setMinHeal(rs.getInt("min_heal"));
                setMaxHeal(rs.getInt("max_heal"));
                setMinDamage(rs.getInt("min_damage"));
                setMaxDamage(rs.getInt("max_damage"));
            }
        } catch (Exception e) {
            System.out.println("Error fetching Ogre stats from database: " + e.getMessage());
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