import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ReflectionManager {
    public static void saveReflection(Connection conn, String username, String reflection) {
        System.out.println("✅ Reflection saved:");
        System.out.println("User: " + username);
        System.out.println("Reflection: " + reflection);

        try {
            String sql = "INSERT INTO reflections (username, reflection) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, reflection);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("❌ Failed to save reflection: " + e.getMessage());
            e.printStackTrace();
        }
    }
}