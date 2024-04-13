import java.sql.*;
import java.util.Scanner;

public class Equipment {
    private Connection conn;
    private Scanner scanner;

    public Equipment(Connection conn) {
        this.conn = conn;
        this.scanner = new Scanner(System.in);
    }

    public void addEquipment() {
        try {
            System.out.print("Enter equipment name: ");
            String name = scanner.nextLine();
            System.out.print("Enter initial status: ");
            String status = scanner.nextLine();
            System.out.print("Enter maintenance schedule (YYYY-MM-DD): ");
            String maintenanceDate = scanner.nextLine();

            String sql = "INSERT INTO equipment (name, status, maintenance_schedule) VALUES (?, ?, ?::date)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, status);
            pstmt.setString(3, maintenanceDate);
            pstmt.executeUpdate();
            System.out.println("Equipment added successfully.");
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Error adding equipment: " + e.getMessage());
        }
    }

    public void updateEquipmentStatus(int equipmentId) {
        try {
            System.out.print("Enter new status for equipment: ");
            String newStatus = scanner.nextLine();

            String sql = "UPDATE equipment SET status = ? WHERE equipment_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newStatus);
            pstmt.setInt(2, equipmentId);
            pstmt.executeUpdate();
            System.out.println("Equipment status updated successfully.");
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Error updating equipment status: " + e.getMessage());
        }
    }

    public void updateMaintenanceSchedule(int equipmentId) {
        try {
            System.out.print("Enter new maintenance schedule for equipment (YYYY-MM-DD): ");
            String newMaintenanceDate = scanner.nextLine();

            String sql = "UPDATE equipment SET maintenance_schedule = ?::date WHERE equipment_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newMaintenanceDate);
            pstmt.setInt(2, equipmentId);
            pstmt.executeUpdate();
            System.out.println("Maintenance schedule updated successfully.");
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Error updating maintenance schedule: " + e.getMessage());
        }
    }

    public void displayEquipment() {
        try {
            String sql = "SELECT equipment_id, name, status, maintenance_schedule FROM equipment";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            System.out.println("List of Equipment:");
            while (rs.next()) {
                System.out.printf("ID: %d, Name: %s, Status: %s, Maintenance Schedule: %s\n",
                        rs.getInt("equipment_id"), rs.getString("name"), rs.getString("status"),
                        rs.getDate("maintenance_schedule").toString());
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Error displaying equipment: " + e.getMessage());
        }
    }
}
