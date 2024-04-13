import java.sql.*;
import java.util.Scanner;

public class FitnessClasses {
    private Connection conn;
    private Scanner scanner;

    public FitnessClasses(Connection conn) {
        this.conn = conn;
        this.scanner = new Scanner(System.in);
    }

    public void addClass() {
        try {
            System.out.print("Enter schedule (YYYY-MM-DD HH:MM:SS): ");
            String schedule = scanner.nextLine();
            System.out.print("Enter room ID: ");
            int roomID = scanner.nextInt();
            System.out.print("Enter member ID: ");
            int memberID = scanner.nextInt();
            System.out.print("Enter trainer ID: ");
            int trainerID = scanner.nextInt();
            scanner.nextLine();

            String sql = "INSERT INTO fitness_classes (schedule, room_id, member_id, trainer_id) VALUES (?::timestamptz, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, schedule);
            pstmt.setInt(2, roomID);
            pstmt.setInt(3, memberID);
            pstmt.setInt(4, trainerID);
            pstmt.executeUpdate();
            System.out.println("Fitness class added successfully.");
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Error adding fitness class: " + e.getMessage());
        }
    }

    public void updateClass(int classId) {
        try {
            System.out.print("Update schedule for class ID " + classId + " (YYYY-MM-DD HH:MM:SS): ");
            String newSchedule = scanner.nextLine();

            String sql = "UPDATE fitness_classes SET schedule = ?::timestamptz WHERE class_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newSchedule);
            pstmt.setInt(2, classId);
            pstmt.executeUpdate();
            System.out.println("Fitness class schedule updated successfully.");
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Error updating fitness class: " + e.getMessage());
        }
    }

    public void removeClass(int classId) {
        try {
            String sql = "DELETE FROM fitness_classes WHERE class_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, classId);
            pstmt.executeUpdate();
            System.out.println("Fitness class removed successfully.");
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Error removing fitness class: " + e.getMessage());
        }
    }

    public void listClasses() {
        try {
            String sql = "SELECT class_id, schedule, room_id, member_id, trainer_id FROM fitness_classes";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            System.out.println("Scheduled Fitness Classes:");
            while (rs.next()) {
                System.out.printf("Class ID: %d, Schedule: %s, Room ID: %d, Member ID: %d, Trainer ID: %d\n",
                        rs.getInt("class_id"), rs.getTimestamp("schedule").toString(),
                        rs.getInt("room_id"), rs.getInt("member_id"), rs.getInt("trainer_id"));
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Error listing fitness classes: " + e.getMessage());
        }
    }
}
