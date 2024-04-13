import java.sql.*;
import java.util.Scanner;

public class Billing {
    private Connection conn;
    private Scanner scanner;

    public Billing(Connection conn) {
        this.conn = conn;
        this.scanner = new Scanner(System.in);
    }

    public void generateBill() {
        try {
            System.out.print("Enter member ID for the bill: ");
            int memberId = scanner.nextInt();
            System.out.print("Enter amount to be billed: ");
            double amount = scanner.nextDouble();
            scanner.nextLine();

            String sql = "INSERT INTO billing (member_id, amount, payment_status) VALUES (?, ?, false)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, memberId);
            pstmt.setDouble(2, amount);
            pstmt.executeUpdate();
            System.out.println("Bill generated successfully.");
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Error generating bill: " + e.getMessage());
        }
    }

    public void makePayment(int billId) {
        try {
            // First check if the bill exists and is unpaid
            if (!checkBillStatus(billId)) {
                System.out.println("Bill is either already paid or does not exist.");
                return;
            }

            // Proceed to make payment
            String sql = "UPDATE billing SET payment_status = true WHERE bill_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, billId);
            pstmt.executeUpdate();
            System.out.println("Payment made successfully. Transaction pending confirmation.");
            pstmt.close();
            confirmTransaction(billId);
        } catch (SQLException e) {
            System.out.println("Error making payment: " + e.getMessage());
        }
    }

    public void confirmTransaction(int billId) {
        System.out.println("Transaction for bill ID " + billId + " confirmed.");
    }

    private boolean checkBillStatus(int billId) throws SQLException {
        String sql = "SELECT payment_status FROM billing WHERE bill_id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, billId);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            boolean isPaid = rs.getBoolean("payment_status");
            rs.close();
            pstmt.close();
            return !isPaid; // Return true if not paid
        }
        rs.close();
        pstmt.close();
        return false;
    }
}
