import java.sql.*;

public class ScoreManager {
    public static void submitScore(Connection conn, String username, String subject, String date, int score) {
        System.out.println("✅ Score submitted:");
        System.out.println("User: " + username);
        System.out.println("Subject: " + subject);
        System.out.println("Date: " + date);
        System.out.println("Score: " + score);

        try {
            String sql = "INSERT INTO scores (username, subject, date, score) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, subject);
            stmt.setString(3, date);
            stmt.setInt(4, score);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("❌ Failed to save score: " + e.getMessage());
            e.printStackTrace();
        }
    }
}