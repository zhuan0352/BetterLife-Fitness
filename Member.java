import java.sql.*;
import java.util.Scanner;

public class Member {
    private Connection conn;
    private Scanner scanner;
    private int memberId;

    public Member(Connection conn) {
        this.conn = conn;
        this.scanner = new Scanner(System.in);

    }
    public Member(Connection conn, int memberId) {
        this.conn = conn;
        this.scanner = new Scanner(System.in);
        this.memberId = memberId;
    }

    public void register() {
        try {
            System.out.println("Create your member ID:");
            int memberId = Integer.parseInt(scanner.nextLine());
            System.out.println("Enter name:");
            String name = scanner.nextLine();
            System.out.println("Enter email:");
            String email = scanner.nextLine();
            System.out.println("Enter password:");
            String password = scanner.nextLine();

            String sql = "INSERT INTO members (member_id, name, email, password) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, memberId);
            pstmt.setString(2, name);
            pstmt.setString(3, email);
            pstmt.setString(4, password);
            pstmt.executeUpdate();
            System.out.println("Registration Successful!");
        } catch (SQLException e) {
            System.out.println("Error in registration: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid member ID format.");
        }
    }


    public void updateProfile() {
        try {
            System.out.println("Update Profile:");
            System.out.print("Enter new name: ");
            String name = scanner.nextLine();
            System.out.print("Enter new email: ");
            String email = scanner.nextLine();
            System.out.print("Enter new password: ");
            String password = scanner.nextLine();
            String sql = "UPDATE members SET name = ?, email = ?, password = ? WHERE member_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, password);
            pstmt.setInt(4, memberId);
            pstmt.executeUpdate();
            System.out.println("Profile Updated Successfully!");
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Error updating profile: " + e.getMessage());
        }
    }

    public void setFitnessGoals() {
        try {
            System.out.println("Set Fitness Goals:");
            System.out.print("Do you want to set goals for muscle gain or weight loss? Enter 'muscle' or 'weight': ");
            String goalType = scanner.nextLine().trim().toLowerCase();
            String jsonGoal = "";

            if ("muscle".equals(goalType) || "weight".equals(goalType)) {
                System.out.print("Enter target (e.g., 5 kg): ");
                String target = scanner.nextLine();
                System.out.print("Enter duration (e.g., 6 months): ");
                String duration = scanner.nextLine();

                jsonGoal = String.format("{\"%s_gain\": {\"target\": \"%s\", \"duration\": \"%s\"}}", goalType, target, duration);
            } else {
                System.out.println("Invalid input. Please enter 'muscle' or 'weight'.");
                return;
            }

            String sql = "UPDATE members SET fitness_goals = ?::jsonb WHERE member_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, jsonGoal);
            pstmt.setInt(2, memberId);
            pstmt.executeUpdate();
            System.out.println("Fitness Goals Updated Successfully!");
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Error setting fitness goals: " + e.getMessage());
        }
    }



    public void inputHealthMetrics() {
        try {
            System.out.println("Enter health metrics:");
            System.out.print("Heart Rate (bpm): ");
            int heartRate = scanner.nextInt();
            System.out.print("Weight (kg): ");
            double weight = scanner.nextDouble();
            scanner.nextLine();
            System.out.print("Blood Pressure (e.g., 120/80): ");
            String bloodPressure = scanner.nextLine();

            String jsonHealthMetrics = String.format(
                    "{\"heart_rate\": %d, \"weight\": %.1f, \"blood_pressure\": \"%s\"}",
                    heartRate, weight, bloodPressure
            );

            String sql = "UPDATE members SET health_metrics = ?::jsonb WHERE member_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, jsonHealthMetrics);
            pstmt.setInt(2, memberId);
            pstmt.executeUpdate();
            System.out.println("Health Metrics Updated Successfully!");
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Error updating health metrics: " + e.getMessage());
        }
    }


    public void displayHealthMetrics() {
        try {
            String sql = "SELECT health_metrics FROM members WHERE member_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, memberId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String healthMetrics = rs.getString(1);
                System.out.println("Current Health Metrics: " + healthMetrics);
            } else {
                System.out.println("No health metrics found.");
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Error displaying health metrics: " + e.getMessage());
        }
    }


}
