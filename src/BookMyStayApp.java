import java.io.*;
import java.util.*;

// Reservation class (Serializable)
class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;

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

// Room Inventory class (Serializable)
class RoomInventory implements Serializable {
    private static final long serialVersionUID = 1L;

    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Standard", 2);
        inventory.put("Deluxe", 2);
        inventory.put("Suite", 1);
    }

    public boolean allocateRoom(String roomType) {
        int available = inventory.getOrDefault(roomType, 0);
        if (available <= 0) return false;
        inventory.put(roomType, available - 1);
        return true;
    }

    public void restoreRoom(String roomType) {
        int available = inventory.getOrDefault(roomType, 0);
        inventory.put(roomType, available + 1);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

// Persistence Service
class PersistenceService {

    private static final String INVENTORY_FILE = "inventory.dat";
    private static final String BOOKINGS_FILE = "bookings.dat";

    public static void saveInventory(RoomInventory inventory) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(INVENTORY_FILE))) {
            oos.writeObject(inventory);
            System.out.println("Inventory saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving inventory: " + e.getMessage());
        }
    }

    public static RoomInventory loadInventory() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(INVENTORY_FILE))) {
            RoomInventory inventory = (RoomInventory) ois.readObject();
            System.out.println("Inventory loaded successfully.");
            return inventory;
        } catch (FileNotFoundException e) {
            System.out.println("No saved inventory found. Starting fresh.");
            return new RoomInventory();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading inventory: " + e.getMessage());
            return new RoomInventory();
        }
    }

    public static void saveBookings(List<Reservation> bookings) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(BOOKINGS_FILE))) {
            oos.writeObject(bookings);
            System.out.println("Bookings saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving bookings: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Reservation> loadBookings() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(BOOKINGS_FILE))) {
            List<Reservation> bookings = (List<Reservation>) ois.readObject();
            System.out.println("Bookings loaded successfully.");
            return bookings;
        } catch (FileNotFoundException e) {
            System.out.println("No saved bookings found. Starting fresh.");
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading bookings: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}

// Main App
public class BookMyStayApp{

    private RoomInventory inventory;
    private List<Reservation> bookings;
    private int reservationCounter;

    public BookMyStayApp() {
        inventory = PersistenceService.loadInventory();
        bookings = PersistenceService.loadBookings();
        reservationCounter = 100 + bookings.size();
    }

    public void bookRoom(String guestName, String roomType) {
        if (inventory.allocateRoom(roomType)) {
            String reservationId = "RES" + reservationCounter++;
            Reservation r = new Reservation(reservationId, guestName, roomType);
            bookings.add(r);
            System.out.println("Booking Confirmed: " + r);
        } else {
            System.out.println("Booking Failed: No rooms available for " + roomType);
        }
    }

    public void shutdown() {
        PersistenceService.saveInventory(inventory);
        PersistenceService.saveBookings(bookings);
        System.out.println("System shutdown complete. State persisted.");
    }

    public void displayState() {
        inventory.displayInventory();
        System.out.println("\nConfirmed Bookings:");
        for (Reservation r : bookings) {
            System.out.println(r);
        }
    }

    public static void main(String[] args) {

       BookMyStayApp app= new BookMyStayApp();

        System.out.println("\n--- Current System State on Startup ---");
        app.displayState();

        // Simulate new bookings
        app.bookRoom("Alice", "Standard");
        app.bookRoom("Bob", "Suite");
        app.bookRoom("Charlie", "Deluxe");

        System.out.println("\n--- System State Before Shutdown ---");
        app.displayState();

        // Persist data
        app.shutdown();

        // Restart simulation
        System.out.println("\n--- Simulating System Restart ---");
        BookMyStayApp recoveredApp = new BookMyStayApp();
        recoveredApp.displayState();
    }
}