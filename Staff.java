import java.sql.*;

public class Staff {
    private Connection conn;

    public Staff(Connection conn) {
        this.conn = conn;
    }

    public void removeStaff(int staffId) {
        try {
            String sql = "DELETE FROM administrative_staff WHERE staff_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, staffId);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Staff member removed successfully.");
            } else {
                System.out.println("No staff member found with ID: " + staffId);
            }
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Error removing staff member: " + e.getMessage());
        }
    }

    public void listStaff() {
        try {
            String sql = "SELECT staff_id FROM administrative_staff";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            System.out.println("List of Staff IDs:");
            while (rs.next()) {
                int staffId = rs.getInt("staff_id");
                System.out.println("Staff ID: " + staffId);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Error listing staff members: " + e.getMessage());
        }
    }
}
