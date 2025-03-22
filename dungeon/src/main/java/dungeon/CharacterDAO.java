package dungeon;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;



public class CharacterDAO implements Serializable {
    private static final long serialVersionUID = 1L;

    public static void insertCharacter(String name, double health, double damage, double range, int attackSpeed, double hitChance, double healChance, int minHeal, int maxHeal, int minDamage, int maxDamage) {
        String sql = "INSERT INTO Characters(name, health, damage, range, attack_speed, hit_chance, heal_chance, min_heal, max_heal, min_damage, max_damage) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setDouble(2, health);
            pstmt.setDouble(3, damage);
            pstmt.setDouble(4, range);
            pstmt.setInt(5, attackSpeed);
            pstmt.setDouble(6, hitChance);
            pstmt.setDouble(7, healChance);
            pstmt.setInt(8, minHeal);
            pstmt.setInt(9, maxHeal);
            pstmt.setInt(10, minDamage);
            pstmt.setInt(11, maxDamage);
            pstmt.executeUpdate();
            System.out.println("Character added to the database: " + name);
        } catch (Exception e) {
            System.out.println("Error inserting character: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public static void getCharacters() {
        String sql = "SELECT * FROM Characters";
        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("Characters in the database:");
            while (rs.next()) {
                System.out.println("Name: " + rs.getString("name") + 
                                   " | Health: " + rs.getInt("health") + 
                                   " | Damage: " + rs.getInt("damage"));
            }
        } catch (Exception e) {
            System.out.println("Error retrieving characters: " + e.getMessage());
            e.printStackTrace();
        }
    }
}


