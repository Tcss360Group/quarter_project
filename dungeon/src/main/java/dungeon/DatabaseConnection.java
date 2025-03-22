package dungeon;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection implements Serializable {
    private static final long serialVersionUID = 1L;

    public static Connection connect() {
        String url = "jdbc:sqlite:dungeon_game.db"; // SQLite database file
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
}
