import java.util.*;

// Reservation class
class Reservation {

    private String reservationId;
    private String guestName;
    private String roomType;
    private double price;

    public Reservation(String reservationId, String guestName, String roomType, double price) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.price = price;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Reservation ID: " + reservationId +
                ", Guest: " + guestName +
                ", Room Type: " + roomType +
                ", Price: ₹" + price;
    }
}

// BookingHistory class to store confirmed bookings
class BookingHistory {

    private List<Reservation> bookingList;

    public BookingHistory() {
        bookingList = new ArrayList<>();
    }

    // Add confirmed reservation
    public void addReservation(Reservation reservation) {
        bookingList.add(reservation);
        System.out.println("Reservation " + reservation.getReservationId() + " added to booking history.");
    }

    // Retrieve booking history
    public List<Reservation> getBookingHistory() {
        return bookingList;
    }
}

// Reporting Service
class BookingReportService {

    // Display all reservations
    public void displayAllBookings(List<Reservation> reservations) {

        System.out.println("\n--- Booking History ---");

        for (Reservation r : reservations) {
            System.out.println(r);
        }
    }

    // Generate summary report
    public void generateSummaryReport(List<Reservation> reservations) {

        int totalBookings = reservations.size();
        double totalRevenue = 0;

        for (Reservation r : reservations) {
            totalRevenue += r.getPrice();
        }

        System.out.println("\n--- Booking Summary Report ---");
        System.out.println("Total Bookings: " + totalBookings);
        System.out.println("Total Revenue: ₹" + totalRevenue);
    }
}

// Main class
public class BookMyStayApp {

    public static void main(String[] args) {

        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        // Simulating confirmed bookings
        Reservation r1 = new Reservation("RES101", "Arun", "Deluxe", 4500);
        Reservation r2 = new Reservation("RES102", "Meena", "Suite", 7000);
        Reservation r3 = new Reservation("RES103", "Rahul", "Standard", 3000);

        // Add bookings to history
        history.addReservation(r1);
        history.addReservation(r2);
        history.addReservation(r3);

        // Admin retrieves booking history
        List<Reservation> bookings = history.getBookingHistory();

        // Display booking history
        reportService.displayAllBookings(bookings);

        // Generate summary report
        reportService.generateSummaryReport(bookings);
    }
}