import static spark.Spark.*;
import org.json.JSONArray;
import org.json.JSONObject;
import java.sql.*;
import java.io.*;

public class Main {
    private static Connection conn;

    public static void main(String[] args) {
        port(4567);
        staticFiles.externalLocation("C:/Users/Gayathri K/OneDrive/Desktop/Learn Curve/public");

        // 🔌 Initialize DB Connection
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:C:/Users/Gayathri K/OneDrive/Desktop/Learn Curve/database/learncurve.db");
            System.out.println("✅ Connected to learncurve.db");
        } catch (Exception e) {
            System.out.println("❌ DB connection failed: " + e.getMessage());
            e.printStackTrace();
            stop();
            return;
        }

        // 🔐 Login
        post("/login", (req, res) -> {
            String username = req.queryParams("username");
            String password = req.queryParams("password");

            if (username == null || password == null) {
                res.status(400);
                return "Missing username or password";
            }

            boolean success = UserAuth.validateLogin(conn, username, password);
            if (success) {
                res.redirect("/dashboard.html");
                return null;
            } else {
                res.status(401);
                return "Invalid credentials";
            }
        });

        // 📝 Registration
        post("/register", (req, res) -> {
            String username = req.queryParams("username");
            String password = req.queryParams("password");

            if (username == null || password == null) {
                res.status(400);
                return "Missing registration fields";
            }

            boolean success = UserAuth.registerUser(conn, username, password);
            return success ? "Registration successful" : "User already exists";
        });

        // 📊 Submit Score
        post("/submitScore", (req, res) -> {
            String username = req.queryParams("username");
            String subject = req.queryParams("subject");
            String date = req.queryParams("date");
            String scoreStr = req.queryParams("score");

            if (username == null || subject == null || date == null || scoreStr == null) {
                res.status(400);
                return "Missing score fields";
            }

            try {
                int score = Integer.parseInt(scoreStr);
                ScoreManager.submitScore(conn, username, subject, date, score);
                return "Score submitted";
            } catch (NumberFormatException e) {
                res.status(400);
                return "Invalid score format";
            }
        });

        // 🎯 Set Goal
        post("/setGoal", (req, res) -> {
            String username = req.queryParams("username");
            String subject = req.queryParams("subject");
            String targetStr = req.queryParams("target");

            if (username == null || subject == null || targetStr == null) {
                res.status(400);
                return "Missing goal fields";
            }

            try {
                int target = Integer.parseInt(targetStr);
                GoalManager.setGoal(conn, username, subject, target);
                return "Goal set";
            } catch (NumberFormatException e) {
                res.status(400);
                return "Invalid target format";
            }
        });

        // 💬 Submit Reflection
        post("/submitReflection", (req, res) -> {
            String username = req.queryParams("username");
            String reflection = req.queryParams("reflection");

            if (username == null || reflection == null) {
                res.status(400);
                return "Missing reflection fields";
            }

            ReflectionManager.saveReflection(conn, username, reflection);
            return "Reflection saved";
        });

        // 📥 Get Goals
        get("/getGoals", (req, res) -> {
            String username = req.queryParams("username");
            JSONArray goalsArray = new JSONArray();

            try {
                String sql = "SELECT subject, target FROM goals WHERE username = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, username);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    JSONObject goal = new JSONObject();
                    goal.put("subject", rs.getString("subject"));
                    goal.put("target", rs.getInt("target"));
                    goalsArray.put(goal);
                }
            } catch (Exception e) {
                e.printStackTrace();
                res.status(500);
                return "Error fetching goals";
            }

            res.type("application/json");
            return goalsArray.toString();
        });

        // 📥 Get Reflections
        get("/getReflections", (req, res) -> {
            String username = req.queryParams("username");
            JSONArray reflectionsArray = ReflectionFetcher.getReflections(conn, username);
            res.type("application/json");
            return reflectionsArray.toString();
        });

        // 📈 Serve Chart Image
        get("/chart.png", (req, res) -> {
            res.type("image/png");
            try {
                return new FileInputStream("C:/Users/Gayathri K/OneDrive/Desktop/Learn Curve/public/chart.png");
            } catch (FileNotFoundException e) {
                res.status(404);
                return "Chart image not found";
            }
        });
    }
}