import java.sql.*;
import java.util.Scanner;

public class TrainingSessions {
    private Connection conn;
    private Scanner scanner;

    public TrainingSessions(Connection conn) {
        this.conn = conn;
        this.scanner = new Scanner(System.in);
    }

    public void scheduleSession() {
        try {
            System.out.print("Enter member ID: ");
            int memberId = scanner.nextInt();
            System.out.print("Enter trainer ID: ");
            int trainerId = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            System.out.print("Enter date and time for the session (YYYY-MM-DD HH:MM:SS): ");
            String dateTime = scanner.nextLine();

            // Check if the trainer is available at the given time
            if (!isTrainerAvailable(trainerId, dateTime)) {
                System.out.println("Trainer is not available at this time.");
                return;
            }

            String sql = "INSERT INTO training_sessions (member_id, trainer_id, date_time) VALUES (?, ?, ?::timestamptz)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, memberId);
            pstmt.setInt(2, trainerId);
            pstmt.setString(3, dateTime);
            pstmt.executeUpdate();
            System.out.println("Session scheduled successfully.");
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Error scheduling session: " + e.getMessage());
        }
    }

    public void rescheduleSession() {
        try {
            System.out.print("Enter session ID to reschedule: ");
            int sessionId = scanner.nextInt();
            System.out.print("Enter trainer ID: ");
            int trainerId = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Enter new date and time for the session (YYYY-MM-DD HH:MM:SS): ");
            String newDateTime = scanner.nextLine();

            if (!isTrainerAvailable(trainerId, newDateTime)) {
                System.out.println("Trainer is not available at this new time.");
                return;
            }

            String sql = "UPDATE training_sessions SET date_time = ?::timestamptz WHERE session_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newDateTime);
            pstmt.setInt(2, sessionId);
            pstmt.executeUpdate();
            System.out.println("Session rescheduled successfully.");
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Error rescheduling session: " + e.getMessage());
        }
    }

    private boolean isTrainerAvailable(int trainerId, String dateTime) {
        try {
            String sql = "SELECT available_times FROM trainers WHERE trainer_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, trainerId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Timestamp[] availableTimes = (Timestamp[]) rs.getArray(1).getArray();
                Timestamp requestedTime = Timestamp.valueOf(dateTime);
                for (Timestamp availableTime : availableTimes) {
                    if (availableTime.equals(requestedTime)) {
                        return true;
                    }
                }
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Error checking trainer availability: " + e.getMessage());
        }
        return false;
    }

    public void cancelSession() {
        try {
            System.out.print("Enter session ID to cancel: ");
            int sessionId = scanner.nextInt();
            scanner.nextLine();

            String sql = "DELETE FROM training_sessions WHERE session_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, sessionId);
            pstmt.executeUpdate();
            System.out.println("Session cancelled successfully.");
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Error cancelling session: " + e.getMessage());
        }
    }

//    public void listSessions(int memberId) {
//        try {
//            String sql = "SELECT session_id, trainer_id, date_time FROM training_sessions WHERE member_id = ?";
//            PreparedStatement pstmt = conn.prepareStatement(sql);
//            pstmt.setInt(1, memberId);
//            ResultSet rs = pstmt.executeQuery();
//            System.out.println("Scheduled Sessions:");
//            while (rs.next()) {
//                System.out.println("Session ID: " + rs.getInt("session_id") + ", Trainer ID: " + rs.getInt("trainer_id") + ", Date & Time: " + rs.getTimestamp("date_time"));
//            }
//            rs.close();
//            pstmt.close();
//        } catch (SQLException e) {
//            System.out.println("Error listing sessions: " + e.getMessage());
//        }
//    }
}
