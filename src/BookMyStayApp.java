/**
 * Book My Stay - Hotel Booking Management System
 *
 * Use Case 2: Basic Room Types & Static Availability
 *
 * Demonstrates object modeling using abstraction, inheritance,
 * polymorphism, and simple availability variables.
 *
 * @author ShristyShree
 * @version 2.1
 */

// Abstract class representing a generic room
abstract class Room {

    protected int beds;
    protected int size;
    protected double price;

    public Room(int beds, int size, double price) {
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    // Method to display room details
    public void displayRoomDetails() {
        System.out.println("Beds: " + beds);
        System.out.println("Size: " + size + " sq.ft");
        System.out.println("Price per night: $" + price);
    }

    // Abstract method for room type
    public abstract String getRoomType();
}


// Single Room class
class SingleRoom extends Room {

    public SingleRoom() {
        super(1, 200, 100.0);
    }

    @Override
    public String getRoomType() {
        return "Single Room";
    }
}


// Double Room class
class DoubleRoom extends Room {

    public DoubleRoom() {
        super(2, 350, 180.0);
    }

    @Override
    public String getRoomType() {
        return "Double Room";
    }
}


// Suite Room class
class SuiteRoom extends Room {

    public SuiteRoom() {
        super(3, 500, 300.0);
    }

    @Override
    public String getRoomType() {
        return "Suite Room";
    }
}


public class BookMyStayApp{

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("     Book My Stay Application    ");
        System.out.println(" Hotel Booking Management System ");
        System.out.println(" Version: 2.1");
        System.out.println("=================================\n");

        // Create room objects (Polymorphism)
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Static availability variables
        int singleRoomAvailability = 5;
        int doubleRoomAvailability = 3;
        int suiteRoomAvailability = 2;

        // Display room information
        System.out.println("Room Type: " + single.getRoomType());
        single.displayRoomDetails();
        System.out.println("Available Rooms: " + singleRoomAvailability);
        System.out.println("---------------------------------\n");

        System.out.println("Room Type: " + doubleRoom.getRoomType());
        doubleRoom.displayRoomDetails();
        System.out.println("Available Rooms: " + doubleRoomAvailability);
        System.out.println("---------------------------------\n");

        System.out.println("Room Type: " + suite.getRoomType());
        suite.displayRoomDetails();
        System.out.println("Available Rooms: " + suiteRoomAvailability);
        System.out.println("---------------------------------\n");

        System.out.println("Application finished execution.");
    }
}