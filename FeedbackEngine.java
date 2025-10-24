import java.sql.*;

public class FeedbackEngine {
    public static String generateFeedback() {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT subject, AVG(score) as avg_score FROM scores GROUP BY subject";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            StringBuilder feedback = new StringBuilder("📊 Your Progress:\n");
            while (rs.next()) {
                String subject = rs.getString("subject");
                int avg = rs.getInt("avg_score");
                feedback.append(subject).append(": ").append(avg).append("%\n");
            }
            return feedback.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error generating feedback";
        }
    }
}