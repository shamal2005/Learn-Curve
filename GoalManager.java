import java.sql.*;

public class GoalManager {
    public static void setGoal(Connection conn, String username, String subject, int target) {
        System.out.println("✅ Goal set:");
        System.out.println("User: " + username);
        System.out.println("Subject: " + subject);
        System.out.println("Target: " + target);

        try {
            String sql = "INSERT INTO goals (username, subject, target) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, subject);
            stmt.setInt(3, target);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("❌ Failed to save goal: " + e.getMessage());
            e.printStackTrace();
        }
    }
}