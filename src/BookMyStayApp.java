/**
 * Book My Stay - Hotel Booking Management System
 *
 * Demonstrates multiple use cases of the hotel booking system
 * using Core Java concepts.
 *
 * @author ShristyShree
 * @version 1.0
 */

public class BookMyStayApp {

    public static void main(String[] args) {

        // Use Case 1
        showWelcomeMessage();

        // Later you will call other use cases
        // useCase2();
        // useCase3();
    }

    /**
     * Use Case 1: Application Entry & Welcome Message
     */
    public static void showWelcomeMessage() {

        System.out.println("==================================");
        System.out.println("     Welcome to Book My Stay      ");
        System.out.println(" Hotel Booking Management System  ");
        System.out.println(" Version: 1.0");
        System.out.println("==================================");

        System.out.println("Application started successfully.");
    }

}