/**
 * Book My Stay - Hotel Booking Management System
 *
 * Use Case 6: Reservation Confirmation & Room Allocation
 *
 * Demonstrates safe room allocation using Queue, HashMap,
 * and Set to prevent double booking.
 *
 * @author ShristyShree
 * @version 6.1
 */

import java.util.*;

// Reservation class representing booking request
class Reservation {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}


// Room Inventory Service
class InventoryService {

    private Map<String, Integer> inventory = new HashMap<>();

    public InventoryService() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 2);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void decrementRoom(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (String room : inventory.keySet()) {
            System.out.println(room + " -> Available: " + inventory.get(room));
        }
    }
}


// Booking Service for allocation
class BookingService {

    private Queue<Reservation> requestQueue;
    private InventoryService inventoryService;

    private Set<String> allocatedRoomIds = new HashSet<>();
    private Map<String, Set<String>> roomAllocationMap = new HashMap<>();

    private int roomCounter = 1;

    public BookingService(Queue<Reservation> requestQueue, InventoryService inventoryService) {
        this.requestQueue = requestQueue;
        this.inventoryService = inventoryService;
    }

    public void processBookings() {

        while (!requestQueue.isEmpty()) {

            Reservation request = requestQueue.poll();

            String roomType = request.getRoomType();
            String guest = request.getGuestName();

            System.out.println("\nProcessing reservation for " + guest);

            if (inventoryService.getAvailability(roomType) > 0) {

                String roomId = generateRoomId(roomType);

                allocatedRoomIds.add(roomId);

                roomAllocationMap
                        .computeIfAbsent(roomType, k -> new HashSet<>())
                        .add(roomId);

                inventoryService.decrementRoom(roomType);

                System.out.println("Reservation Confirmed!");
                System.out.println("Guest: " + guest);
                System.out.println("Room Type: " + roomType);
                System.out.println("Assigned Room ID: " + roomId);

            } else {

                System.out.println("Reservation Failed: No available rooms for " + roomType);
            }
        }
    }

    private String generateRoomId(String roomType) {

        String roomId;

        do {
            roomId = roomType.substring(0,2).toUpperCase() + "-" + roomCounter++;
        } while (allocatedRoomIds.contains(roomId));

        return roomId;
    }
}


public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("     Book My Stay Application    ");
        System.out.println(" Hotel Booking Management System ");
        System.out.println(" Version: 6.1");
        System.out.println("=================================");

        // Initialize inventory
        InventoryService inventory = new InventoryService();

        // Booking request queue
        Queue<Reservation> requestQueue = new LinkedList<>();

        requestQueue.offer(new Reservation("Alice", "Single Room"));
        requestQueue.offer(new Reservation("Bob", "Double Room"));
        requestQueue.offer(new Reservation("Charlie", "Suite Room"));
        requestQueue.offer(new Reservation("David", "Single Room"));

        // Process booking requests
        BookingService bookingService = new BookingService(requestQueue, inventory);

        bookingService.processBookings();

        inventory.displayInventory();
    }
}