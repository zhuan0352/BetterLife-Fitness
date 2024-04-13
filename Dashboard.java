import java.sql.*;
public class Dashboard {
    private Connection conn;
    private int memberId;

    public Dashboard(Connection conn, int memberId) {
        this.conn = conn;
        this.memberId = memberId;
    }

    public void displayRoutine() {
        try {
            String sql = "SELECT exercise_routines FROM dashboard WHERE member_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, memberId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String[] routines = (String[]) rs.getArray(1).getArray();
                System.out.println("Exercise Routines:");
                for (String routine : routines) {
                    System.out.println(routine);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error displaying routines: " + e.getMessage());
        }
    }

    public void addRoutine(String routine) {
        try {
            String sql = "UPDATE dashboard SET exercise_routines = array_append(exercise_routines, ?) WHERE member_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, routine);
            pstmt.setInt(2, memberId);
            pstmt.executeUpdate();
            System.out.println("Routine added successfully.");
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Error adding routine: " + e.getMessage());
        }
    }

    public void displayAchievements() {
        try {
            String sql = "SELECT fitness_achievements FROM dashboard WHERE member_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, memberId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String[] achievements = (String[]) rs.getArray(1).getArray();
                System.out.println("Fitness Achievements:");
                for (String achievement : achievements) {
                    System.out.println(achievement);
                }
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Error displaying achievements: " + e.getMessage());
        }
    }
}
