import java.sql.*;
import java.util.*;

class DBConnection {
    public static Connection getConnection() {
        try {
            String url = "jdbc:mysql://localhost:3306/reservation_db";
            String user = "root";
            String password = "Nagraj@2317"; 

            return DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            System.out.println("Database Connection Failed!");
            return null;
        }
    }
}

public class OnlineReservationSystem {

    static Scanner sc = new Scanner(System.in);
    static int pnrCounter = 1000;

    static String validUser = "admin";
    static String validPass = "1234";

    public static void main(String[] args) {

        if (!login()) {
            System.out.println("Invalid Login! Exiting...");
            return;
        }

        int choice;

        do {
            System.out.println("\n===== ONLINE RESERVATION SYSTEM =====");
            System.out.println("1. Reservation");
            System.out.println("2. Cancellation");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    makeReservation();
                    break;
                case 2:
                    cancelReservation();
                    break;
                case 3:
                    System.out.println("Thank you!");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 3);
    }

    // LOGIN
    static boolean login() {
        System.out.println("===== LOGIN =====");
        System.out.print("Username: ");
        String user = sc.next();
        System.out.print("Password: ");
        String pass = sc.next();

        return user.equals(validUser) && pass.equals(validPass);
    }

    // RESERVATION
    static void makeReservation() {
        sc.nextLine();

        try {
            Connection con = DBConnection.getConnection();

            System.out.println("\n===== RESERVATION FORM =====");

            System.out.print("Name: ");
            String name = sc.nextLine();

            System.out.print("Train Number: ");
            int trainNo = sc.nextInt();
            sc.nextLine();

            String trainName = getTrainName(trainNo);

            System.out.print("Class Type: ");
            String classType = sc.nextLine();

            System.out.print("Journey Date: ");
            String date = sc.nextLine();

            System.out.print("From: ");
            String from = sc.nextLine();

            System.out.print("To: ");
            String to = sc.nextLine();

            int pnr = ++pnrCounter;

            String query = "INSERT INTO reservations VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(query);

            pst.setInt(1, pnr);
            pst.setString(2, name);
            pst.setInt(3, trainNo);
            pst.setString(4, trainName);
            pst.setString(5, classType);
            pst.setString(6, date);
            pst.setString(7, from);
            pst.setString(8, to);

            pst.executeUpdate();

            System.out.println("\nReservation Successful!");
            System.out.println("PNR Number: " + pnr);

            con.close();

        } catch (Exception e) {
            System.out.println("Error in reservation!");
            e.printStackTrace();
        }
    }

    // CANCELLATION
    static void cancelReservation() {
        try {
            Connection con = DBConnection.getConnection();

            System.out.print("\nEnter PNR: ");
            int pnr = sc.nextInt();

            String query = "SELECT * FROM reservations WHERE pnr = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, pnr);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                System.out.println("\nReservation Found:");
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Train: " + rs.getString("train_name"));
                System.out.println("From: " + rs.getString("source"));
                System.out.println("To: " + rs.getString("destination"));
                System.out.println("Date: " + rs.getString("journey_date"));

                System.out.print("Confirm Cancellation (yes/no): ");
                String confirm = sc.next();

                if (confirm.equalsIgnoreCase("yes")) {
                    String deleteQuery = "DELETE FROM reservations WHERE pnr = ?";
                    PreparedStatement del = con.prepareStatement(deleteQuery);
                    del.setInt(1, pnr);
                    del.executeUpdate();

                    System.out.println("Ticket Cancelled Successfully!");
                } else {
                    System.out.println("Cancelled Aborted.");
                }

            } else {
                System.out.println("PNR Not Found!");
            }

            con.close();

        } catch (Exception e) {
            System.out.println("Error in cancellation!");
            e.printStackTrace();
        }
    }

    // TRAIN NAME AUTO FILL
    static String getTrainName(int trainNo) {
        switch (trainNo) {
            case 101: return "Express";
            case 102: return "Superfast";
            case 103: return "Rajdhani";
            case 104: return "Shatabdi";
            default: return "Unknown";
        }
    }
}