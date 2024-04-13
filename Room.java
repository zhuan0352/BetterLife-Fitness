import java.sql.*;
import java.util.Scanner;

public class Room {
    private Connection conn;
    private Scanner scanner;

    public Room(Connection conn) {
        this.conn = conn;
        this.scanner = new Scanner(System.in);
    }

    public void addBooking(int roomId, String dateTime) {
        if (!isRoomAvailable(roomId, dateTime)) {
            System.out.println("Room is not available at this time.");
            return;
        }

        try {
            String sql = "UPDATE rooms SET booking_schedule = array_append(booking_schedule, ?::timestamptz) WHERE room_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, dateTime);
            pstmt.setInt(2, roomId);
            pstmt.executeUpdate();
            System.out.println("Booking added successfully.");
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Error adding booking: " + e.getMessage());
        }
    }

    public void removeBooking(int roomId, String dateTime) {
        try {
            String sql = "UPDATE rooms SET booking_schedule = array_remove(booking_schedule, ?::timestamptz) WHERE room_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, dateTime);
            pstmt.setInt(2, roomId);
            pstmt.executeUpdate();
            System.out.println("Booking removed successfully.");
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Error removing booking: " + e.getMessage());
        }
    }

    public void displayBookings(int roomId) {
        try {
            String sql = "SELECT booking_schedule FROM rooms WHERE room_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, roomId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Timestamp[] bookings = (Timestamp[]) rs.getArray(1).getArray();
                System.out.println("Bookings for Room " + roomId + ":");
                for (Timestamp booking : bookings) {
                    System.out.println(booking.toString());
                }
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Error displaying bookings: " + e.getMessage());
        }
    }

    private boolean isRoomAvailable(int roomId, String dateTime) {
        try {
            String sql = "SELECT booking_schedule FROM rooms WHERE room_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, roomId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Timestamp[] bookings = (Timestamp[]) rs.getArray(1).getArray();
                Timestamp requestedTime = Timestamp.valueOf(dateTime);
                for (Timestamp booking : bookings) {
                    if (booking.equals(requestedTime)) {
                        return false;
                    }
                }
            }
            rs.close();
            pstmt.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error checking room availability: " + e.getMessage());
            return false;
        }
    }
}
