import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.text.SimpleDateFormat;

public class Trainers {
    private Connection conn;
    private int trainerId;
    private Scanner scanner;

    public Trainers(Connection conn, int trainerId) {
        this.conn = conn;
        this.trainerId = trainerId;
        this.scanner = new Scanner(System.in);
    }

    public void manageSchedule() {
        System.out.println("Choose an option:");
        System.out.println("1 - Add Available Time");
        System.out.println("2 - Remove Available Time");
        System.out.println("3 - Display Schedule");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1 -> addAvailableTime();
            case 2 -> removeAvailableTime();
            case 3 -> displaySchedule();
            default -> System.out.println("Invalid choice.");
        }
    }

    private void addAvailableTime() {
        try {
            System.out.print("Enter new available time (YYYY-MM-DD HH:MM:SS+TZ): ");
            String newTime = scanner.nextLine();
            String sql = "UPDATE trainers SET available_times = array_append(available_times, ?::timestamptz) WHERE trainer_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newTime);
            pstmt.setInt(2, trainerId);
            pstmt.executeUpdate();
            System.out.println("Available time added successfully.");
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Error adding available time: " + e.getMessage());
        }
    }

    private void removeAvailableTime() {
        try {
            displaySchedule();
            System.out.print("Enter the time to remove (exact match needed, YYYY-MM-DD HH:MM:SS+TZ): ");
            String timeToRemove = scanner.nextLine();
            String sql = "UPDATE trainers SET available_times = array_remove(available_times, ?::timestamptz) WHERE trainer_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, timeToRemove);
            pstmt.setInt(2, trainerId);
            pstmt.executeUpdate();
            System.out.println("Available time removed successfully.");
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Error removing available time: " + e.getMessage());
        }
    }

    private void displaySchedule() {
        try {
            String sql = "SELECT available_times FROM trainers WHERE trainer_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, trainerId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Timestamp[] times = (Timestamp[]) rs.getArray(1).getArray(); // Corrected casting here
                System.out.println("Available Times:");
                // Define a SimpleDateFormat for output
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                for (Timestamp time : times) {
                    String formattedTime = sdf.format(time); // Format Timestamp to String
                    System.out.println(formattedTime);
                }
            } else {
                System.out.println("No available times found.");
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Error displaying schedule: " + e.getMessage());
        }
    }

    public void viewMemberProfile() {
        try {
            System.out.print("Enter the member's name to search: ");
            String memberName = scanner.nextLine();
            String sql = "SELECT member_id, name, email FROM members WHERE name ILIKE ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + memberName + "%");  // Use ILIKE for case-insensitive matching and '%' for partial matches

            ResultSet rs = pstmt.executeQuery();
            boolean found = false;
            while (rs.next()) {
                if (!found) {
                    System.out.println("Search Results:");
                    found = true;
                }
                System.out.println("Member ID: " + rs.getInt("member_id"));
                System.out.println("Member Name: " + rs.getString("name"));
                System.out.println("Member Email: " + rs.getString("email"));
                System.out.println("-------------------------");
            }

            if (!found) {
                System.out.println("No members found with the name '" + memberName + "'.");
            }

            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Error viewing member profile: " + e.getMessage());
        }
    }

}

