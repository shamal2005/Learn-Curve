import java.sql.*;
import org.json.JSONArray;
import org.json.JSONObject;

public class ReflectionFetcher {

    public static JSONArray getReflections(Connection conn, String username) {
        JSONArray reflectionsArray = new JSONArray();

        try {
            String sql = "SELECT reflection, timestamp FROM reflections WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                JSONObject ref = new JSONObject();
                ref.put("text", rs.getString("reflection"));
                ref.put("timestamp", rs.getString("timestamp"));
                reflectionsArray.put(ref);
            }
        } catch (SQLException e) {
            System.out.println("❌ Failed to fetch reflections: " + e.getMessage());
            e.printStackTrace();
        }

        return reflectionsArray;
    }
}