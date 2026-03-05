/**
 * Book My Stay - Hotel Booking Management System
 *
 * Use Case 4: Room Search & Availability Check
 *
 * Demonstrates read-only search functionality where guests
 * can view available room types without modifying inventory.
 *
 * @author ShristyShree
 * @version 4.1
 */

import java.util.HashMap;
import java.util.Map;

// Abstract Room class
abstract class Room {

    protected int beds;
    protected int size;
    protected double price;

    public Room(int beds, int size, double price) {
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    public abstract String getRoomType();

    public void displayRoomDetails() {
        System.out.println("Beds: " + beds);
        System.out.println("Size: " + size + " sq.ft");
        System.out.println("Price per night: $" + price);
    }
}

// Single Room
class SingleRoom extends Room {

    public SingleRoom() {
        super(1, 200, 100);
    }

    public String getRoomType() {
        return "Single Room";
    }
}

// Double Room
class DoubleRoom extends Room {

    public DoubleRoom() {
        super(2, 350, 180);
    }

    public String getRoomType() {
        return "Double Room";
    }
}

// Suite Room
class SuiteRoom extends Room {

    public SuiteRoom() {
        super(3, 500, 300);
    }

    public String getRoomType() {
        return "Suite Room";
    }
}


// Inventory class (read-only usage here)
class RoomInventory {

    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();

        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 0); // Example unavailable room
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public Map<String, Integer> getAllAvailability() {
        return inventory;
    }
}


// Search Service
class RoomSearchService {

    private RoomInventory inventory;

    public RoomSearchService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void searchAvailableRooms() {

        System.out.println("Available Room Options:\n");

        Map<String, Integer> availability = inventory.getAllAvailability();

        for (String roomType : availability.keySet()) {

            int count = availability.get(roomType);

            // Defensive check to show only available rooms
            if (count > 0) {

                Room room;

                if (roomType.equals("Single Room")) {
                    room = new SingleRoom();
                } else if (roomType.equals("Double Room")) {
                    room = new DoubleRoom();
                } else {
                    room = new SuiteRoom();
                }

                System.out.println("Room Type: " + room.getRoomType());
                room.displayRoomDetails();
                System.out.println("Available Rooms: " + count);
                System.out.println("------------------------------");
            }
        }
    }
}


public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("     Book My Stay Application    ");
        System.out.println(" Hotel Booking Management System ");
        System.out.println(" Version: 4.1");
        System.out.println("=================================\n");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Initialize search service
        RoomSearchService searchService = new RoomSearchService(inventory);

        // Guest searches for rooms
        searchService.searchAvailableRooms();

        System.out.println("\nSearch completed successfully.");
    }
}