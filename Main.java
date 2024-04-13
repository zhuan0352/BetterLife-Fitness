import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost/BetterLifeFitness";
        String user = "postgres";
        String password = "5033";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            Scanner scanner = new Scanner(System.in);
            boolean continueRunning = true;

            while (continueRunning) {
                System.out.println("Are you a Member, Trainer, or Administrative Staff? (Enter M, T, A, or Q to Quit)");
                String userType = scanner.nextLine().toUpperCase();

                switch (userType) {
                    case "M" -> handleMember(conn, scanner);
                    case "T" -> handleTrainer(conn, scanner);
                    case "A" -> handleAdministrativeStaff(conn, scanner);
                    case "Q" -> {
                        continueRunning = false;
                        System.out.println("Exiting the system. Goodbye!");
                    }
                    default -> System.out.println("Invalid input. Please enter M, T, A, or Q.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Database connection failure: " + e.getMessage());
        }
    }

    private static void handleTrainer(Connection conn, Scanner scanner) {
        System.out.println("Enter Trainer ID:");
        int trainerId = scanner.nextInt();
        Trainers trainer = new Trainers(conn, trainerId);
        System.out.println("Select an option:");
        System.out.println("1: Manage Schedule");
        System.out.println("2: View Member Profile");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1 -> trainer.manageSchedule();
            case 2 -> trainer.viewMemberProfile();
            default -> System.out.println("Invalid choice.");
        }
    }

private static void handleMember(Connection conn, Scanner scanner) {
    System.out.println("Are you a current member or a new member? (Enter C for Current, N for New)");
    String memberType = scanner.nextLine();

    if (memberType.equalsIgnoreCase("N")) {
        Member member = new Member(conn);
        member.register();
    }

    System.out.println("Please enter your Member ID:");
    int memberId = Integer.parseInt(scanner.nextLine());

    Member member = new Member(conn, memberId);
    Dashboard dashboard = new Dashboard(conn, memberId);

    System.out.println("Select an option:");
    System.out.println("1: Profile Management");
    System.out.println("2: Dashboard Display");
    System.out.println("3: Schedule Management");

    int choice = Integer.parseInt(scanner.nextLine());

    switch (choice) {
        case 1 -> handleProfileManagement(conn, member, scanner);
        case 2 -> handleDashboardDisplay(dashboard, scanner);
        case 3 -> handleScheduleManagement(conn, scanner);
        default -> System.out.println("Invalid choice.");
    }
}

    private static void handleProfileManagement(Connection conn,Member member, Scanner scanner) {
        Billing billing = new Billing(conn);
        System.out.println("Select an option:");
        System.out.println("1: Update Profile");
        System.out.println("2: Set Fitness Goals");
        System.out.println("3: Input Health Metrics");
        System.out.println("4: Display Health Metrics");
        System.out.println("5: Billing and Payment");


        int choice = Integer.parseInt(scanner.nextLine());
        switch (choice) {
            case 1 -> member.updateProfile();
            case 2 -> member.setFitnessGoals();
            case 3 -> member.inputHealthMetrics();
            case 4 -> member.displayHealthMetrics();
            case 5 -> {
                System.out.print("Enter bill ID to make payment: ");
                int billId = scanner.nextInt();
                scanner.nextLine();
                billing.makePayment(billId);
            }
            default -> System.out.println("Invalid choice.");
        }
    }

    private static void handleDashboardDisplay(Dashboard dashboard, Scanner scanner) {
        System.out.println("Select an option:");

        System.out.println("1: Display Routine");
        System.out.println("2: Add Routine");
        System.out.println("3: Display Achievements");

        int choice = Integer.parseInt(scanner.nextLine());
        switch (choice) {

            case 1 -> dashboard.displayRoutine();
            case 2 -> {
                System.out.println("Enter routine details:");
                String routine = scanner.nextLine();
                dashboard.addRoutine(routine);
            }
            case 3 -> dashboard.displayAchievements();
            default -> System.out.println("Invalid choice.");
        }
    }

    private static void handleScheduleManagement(Connection conn, Scanner scanner) {
        TrainingSessions trainingSessions = new TrainingSessions(conn);
        FitnessClasses fitnessClasses = new FitnessClasses(conn);

        System.out.println("Select an option:");
        System.out.println("1: Schedule a Personal Training Session");
        System.out.println("2: Reschedule a Personal Training Session");
        System.out.println("3: Cancel a Personal Training Session");
        System.out.println("4: Register for a Fitness Class");

        int choice = Integer.parseInt(scanner.nextLine());
        switch (choice) {
            case 1 -> trainingSessions.scheduleSession();
            case 2 -> trainingSessions.rescheduleSession();
            case 3 -> trainingSessions.cancelSession();
            case 4 -> fitnessClasses.addClass();
            default -> System.out.println("Invalid choice.");
        }
    }


    private static void handleAdministrativeStaff(Connection conn, Scanner scanner) {
        System.out.println("Select an option:");
        System.out.println("1: Manage Room Bookings");
        System.out.println("2: Equipment Maintenance Monitoring");
        System.out.println("3: Class Schedule Updating");
        System.out.println("4: Billing and Payment Processing");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1 -> handleRoomManagement(conn, scanner);
            case 2 -> handleEquipmentManagement(conn, scanner);
            case 3 -> handleClassScheduleManagement(conn, scanner);
            case 4 -> handleBillingManagement(conn, scanner);
            default -> System.out.println("Invalid choice.");
        }
    }


    private static void handleRoomManagement(Connection conn, Scanner scanner) {
        Room room = new Room(conn);

        System.out.println("Select an option:");
        System.out.println("1: Add Booking");
        System.out.println("2: Remove Booking");
        System.out.println("3: Display Bookings");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                System.out.print("Enter room ID: ");
                int roomIdToAdd = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Enter date and time (YYYY-MM-DD HH:MM): ");
                String dateTimeToAdd = scanner.nextLine();
                room.addBooking(roomIdToAdd, dateTimeToAdd);
                break;
            case 2:
                System.out.print("Enter room ID: ");
                int roomIdToRemove = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Enter date and time (YYYY-MM-DD HH:MM): ");
                String dateTimeToRemove = scanner.nextLine();
                room.removeBooking(roomIdToRemove, dateTimeToRemove);
                break;
            case 3:
                System.out.print("Enter room ID: ");
                int roomIdToDisplay = scanner.nextInt();
                scanner.nextLine();
                room.displayBookings(roomIdToDisplay);
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private static void handleEquipmentManagement(Connection conn, Scanner scanner) {
        Equipment equipment = new Equipment(conn);

        System.out.println("Select an option:");
        System.out.println("1: Add Equipment");
        System.out.println("2: Update Equipment Status");
        System.out.println("3: Update Maintenance Schedule");
        System.out.println("4: Display Equipment");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1 -> equipment.addEquipment();
            case 2 -> {
                System.out.print("Enter equipment ID: ");
                int equipmentIdToUpdate = scanner.nextInt();
                scanner.nextLine();
                equipment.updateEquipmentStatus(equipmentIdToUpdate);
            }
            case 3 -> {
                System.out.print("Enter equipment ID: ");
                int equipmentIdToUpdateSchedule = scanner.nextInt();
                scanner.nextLine();
                equipment.updateMaintenanceSchedule(equipmentIdToUpdateSchedule);
            }
            case 4 -> equipment.displayEquipment();
            default -> System.out.println("Invalid choice.");
        }
    }

    private static void handleClassScheduleManagement(Connection conn, Scanner scanner) {
        FitnessClasses fitnessClasses = new FitnessClasses(conn);

        System.out.println("Select an option:");
        System.out.println("1: Add Class");
        System.out.println("2: Update Class");
        System.out.println("3: Remove Class");
        System.out.println("4: List Classes");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1 -> fitnessClasses.addClass();
            case 2 -> {
                System.out.print("Enter class ID: ");
                int classIdToUpdate = scanner.nextInt();
                scanner.nextLine();
                fitnessClasses.updateClass(classIdToUpdate);
            }
            case 3 -> {
                System.out.print("Enter class ID to remove: ");
                int classIdToRemove = scanner.nextInt();
                scanner.nextLine();
                fitnessClasses.removeClass(classIdToRemove);
            }
            case 4 -> fitnessClasses.listClasses();
            default -> System.out.println("Invalid choice.");
        }
    }

    private static void handleBillingManagement(Connection conn, Scanner scanner) {
        Billing billing = new Billing(conn);

        System.out.println("Select an option:");
        System.out.println("1: Generate Bill");
        System.out.println("2: Confirm Transaction");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1 -> billing.generateBill();

            case 2 -> {
                System.out.print("Enter bill ID to confirm transaction: ");
                int billIdToConfirm = scanner.nextInt();
                scanner.nextLine();
                billing.confirmTransaction(billIdToConfirm);
            }
            default -> System.out.println("Invalid choice.");
        }
    }
}

