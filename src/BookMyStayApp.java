import java.util.*;

// Custom Exception for invalid booking scenarios
class InvalidBookingException extends Exception {

    public InvalidBookingException(String message) {
        super(message);
    }
}

// Room Inventory Manager
class RoomInventory {

    private Map<String, Integer> roomInventory;

    public RoomInventory() {
        roomInventory = new HashMap<>();

        roomInventory.put("Standard", 2);
        roomInventory.put("Deluxe", 2);
        roomInventory.put("Suite", 1);
    }

    public void bookRoom(String roomType) throws InvalidBookingException {

        // Validate room type
        if (!roomInventory.containsKey(roomType)) {
            throw new InvalidBookingException("Invalid Room Type: " + roomType);
        }

        int available = roomInventory.get(roomType);

        // Prevent negative inventory
        if (available <= 0) {
            throw new InvalidBookingException("No rooms available for type: " + roomType);
        }

        roomInventory.put(roomType, available - 1);

        System.out.println("Room booked successfully: " + roomType);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Room Inventory:");

        for (Map.Entry<String, Integer> entry : roomInventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

// Main class
public class BookMyStayApp {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();

        Scanner scanner = new Scanner(System.in);

        inventory.displayInventory();

        System.out.print("\nEnter room type to book: ");
        String roomType = scanner.nextLine();

        try {

            inventory.bookRoom(roomType);

        } catch (InvalidBookingException e) {

            // Graceful error handling
            System.out.println("Booking Failed: " + e.getMessage());
        }

        // System continues running safely
        inventory.displayInventory();

        scanner.close();
    }
}