package dungeon;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.Statement;
public class DatabaseSetup implements Serializable{
    private static final long serialVersionUID = 1L;

    public static void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Characters (" +
                     "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                     "name TEXT NOT NULL, " +
                     "health INTEGER NOT NULL, " +
                     "damage INTEGER NOT NULL);";

        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table 'Characters' has been created or already exists.");
        } catch (Exception e) {
            System.out.println("Error creating table: " + e.getMessage());
        }
    }
}
