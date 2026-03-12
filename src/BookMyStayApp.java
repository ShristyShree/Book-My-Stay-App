import java.util.*;
import java.util.concurrent.*;

// Reservation class
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() { return reservationId; }
    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }

    @Override
    public String toString() {
        return "ReservationID: " + reservationId + ", Guest: " + guestName + ", RoomType: " + roomType;
    }
}

// Thread-safe Room Inventory
class RoomInventory {
    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Standard", 2);
        inventory.put("Deluxe", 2);
        inventory.put("Suite", 1);
    }

    // Synchronized allocation to ensure thread safety
    public synchronized boolean allocateRoom(String roomType) {
        int available = inventory.getOrDefault(roomType, 0);
        if (available <= 0) return false;
        inventory.put(roomType, available - 1);
        return true;
    }

    public synchronized void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

// Booking request task (Runnable)
class GuestBookingTask implements Runnable {
    private String guestName;
    private String roomType;
    private BookMyStayApp app;

    public GuestBookingTask(String guestName, String roomType, BookMyStayApp app) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.app = app;
    }

    @Override
    public void run() {
        app.processBooking(guestName, roomType);
    }
}

// Main Application Class
public class BookMyStayApp {

    private RoomInventory inventory;
    private List<Reservation> confirmedBookings;
    private int reservationCounter;

    public BookMyStayApp() {
        inventory = new RoomInventory();
        confirmedBookings = Collections.synchronizedList(new ArrayList<>());
        reservationCounter = 100; // For generating reservation IDs
    }

    // Thread-safe booking processing
    public void processBooking(String guestName, String roomType) {
        boolean success = inventory.allocateRoom(roomType);
        if (success) {
            String reservationId;
            synchronized (this) {
                reservationId = "RES" + reservationCounter++;
            }
            Reservation reservation = new Reservation(reservationId, guestName, roomType);
            confirmedBookings.add(reservation);
            System.out.println("Booking Confirmed: " + reservation);
        } else {
            System.out.println("Booking Failed for " + guestName + " - RoomType: " + roomType + " (No availability)");
        }
    }

    // Simulate multiple concurrent bookings
    public void simulateConcurrentBookings() {
        ExecutorService executor = Executors.newFixedThreadPool(5);

        // Sample guest booking requests
        String[][] requests = {
                {"Alice", "Standard"},
                {"Bob", "Deluxe"},
                {"Charlie", "Standard"},
                {"Diana", "Suite"},
                {"Ethan", "Deluxe"},
                {"Fiona", "Suite"},  // Should fail due to limited inventory
                {"George", "Standard"} // Should fail if all Standard rooms taken
        };

        for (String[] req : requests) {
            executor.submit(new GuestBookingTask(req[0], req[1], this));
        }

        executor.shutdown();
        try {
            executor.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Show final inventory and bookings
        inventory.displayInventory();
        System.out.println("\nConfirmed Bookings:");
        synchronized (confirmedBookings) {
            for (Reservation r : confirmedBookings) {
                System.out.println(r);
            }
        }
    }

    // Main method
    public static void main(String[] args) {
        BookMyStayApp app = new BookMyStayApp();
        app.simulateConcurrentBookings();
    }
}