package dungeon;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;

public class Gremlin extends Monster {
    public Gremlin(final Atom theLoc) {
        super(theLoc, "Gremlin", 0, 0, 0, 0, 0, 0, 0, 0); // Temporary placeholder values
        setSprite(new GameSprite("gremlin.png", 0,0, 10));
        fetchStatsFromDatabase(); // Pull stats from database
    }

    private void fetchStatsFromDatabase() {
        String sql = "SELECT * FROM Characters WHERE name = 'Gremlin'";
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
            System.out.println("Error fetching Gremlin stats from database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public double attack() {
        Random random = new Random();
        if (random.nextDouble() < getHitChance()) {
            int damageDealt = random.nextInt(getMaxDamage() - getMinDamage() + 1) + getMinDamage(); // Random damage between min and max
            return damageDealt;
        } else {
            return 0;
        }
    }
}