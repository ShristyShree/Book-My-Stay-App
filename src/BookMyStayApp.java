import java.util.*;

// Reservation class
class Reservation {

    private String reservationId;
    private String roomType;
    private String roomId;
    private boolean active;

    public Reservation(String reservationId, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.roomType = roomType;
        this.roomId = roomId;
        this.active = true;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getRoomId() {
        return roomId;
    }

    public boolean isActive() {
        return active;
    }

    public void cancel() {
        active = false;
    }

    @Override
    public String toString() {
        return "ReservationID: " + reservationId +
                ", RoomType: " + roomType +
                ", RoomID: " + roomId +
                ", Status: " + (active ? "Confirmed" : "Cancelled");
    }
}


// Inventory Manager
class RoomInventory {

    private Map<String, Integer> inventory;

    public RoomInventory() {

        inventory = new HashMap<>();

        inventory.put("Standard", 2);
        inventory.put("Deluxe", 2);
        inventory.put("Suite", 1);
    }

    public boolean allocateRoom(String roomType) {

        int available = inventory.getOrDefault(roomType, 0);

        if (available <= 0) {
            return false;
        }

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


// Cancellation Service
class CancellationService {

    private Map<String, Reservation> reservations;
    private Stack<String> rollbackStack;
    private RoomInventory inventory;

    public CancellationService(RoomInventory inventory) {

        this.inventory = inventory;

        reservations = new HashMap<>();
        rollbackStack = new Stack<>();
    }

    public void confirmBooking(String reservationId, String roomType, String roomId) {

        if (inventory.allocateRoom(roomType)) {

            Reservation r = new Reservation(reservationId, roomType, roomId);

            reservations.put(reservationId, r);

            System.out.println("Booking Confirmed: " + r);

        } else {

            System.out.println("Booking Failed: No rooms available for " + roomType);
        }
    }

    public void cancelBooking(String reservationId) {

        Reservation r = reservations.get(reservationId);

        // Validation
        if (r == null) {
            System.out.println("Cancellation Failed: Reservation does not exist.");
            return;
        }

        if (!r.isActive()) {
            System.out.println("Cancellation Failed: Reservation already cancelled.");
            return;
        }

        // Rollback process
        rollbackStack.push(r.getRoomId());

        inventory.restoreRoom(r.getRoomType());

        r.cancel();

        System.out.println("Booking Cancelled Successfully for Reservation: " + reservationId);
    }

    public void showRollbackStack() {

        System.out.println("\nRollback Stack (Released Room IDs):");

        for (String roomId : rollbackStack) {
            System.out.println(roomId);
        }
    }
}


// Main class
public class BookMyStayApp {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();

        CancellationService service = new CancellationService(inventory);

        // Confirm bookings
        service.confirmBooking("RES101", "Standard", "S101");
        service.confirmBooking("RES102", "Deluxe", "D201");

        inventory.displayInventory();

        // Cancel a booking
        service.cancelBooking("RES101");

        inventory.displayInventory();

        // Attempt invalid cancellation
        service.cancelBooking("RES999");

        // Attempt duplicate cancellation
        service.cancelBooking("RES101");

        service.showRollbackStack();
    }
}