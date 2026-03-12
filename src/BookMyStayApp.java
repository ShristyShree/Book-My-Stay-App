import java.util.*;

// Represents an individual Add-On Service
class Service {
    private String serviceName;
    private double cost;

    public Service(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return serviceName + " (₹" + cost + ")";
    }
}

// Manages add-on services linked to reservations
class AddOnServiceManager {

    // Map<ReservationID, List<Service>>
    private Map<String, List<Service>> reservationServices;

    public AddOnServiceManager() {
        reservationServices = new HashMap<>();
    }

    // Add service to reservation
    public void addService(String reservationId, Service service) {

        reservationServices.putIfAbsent(reservationId, new ArrayList<>());

        reservationServices.get(reservationId).add(service);

        System.out.println(service.getServiceName() +
                " added to reservation " + reservationId);
    }

    // Display services for reservation
    public void showServices(String reservationId) {

        List<Service> services = reservationServices.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No services selected for reservation " + reservationId);
            return;
        }

        System.out.println("Services for Reservation " + reservationId + ":");

        for (Service s : services) {
            System.out.println("- " + s);
        }
    }

    // Calculate total additional cost
    public double calculateServiceCost(String reservationId) {

        List<Service> services = reservationServices.get(reservationId);

        if (services == null)
            return 0;

        double total = 0;

        for (Service s : services) {
            total += s.getCost();
        }

        return total;
    }
}

// Main class
public class BookMyStayApp {

    public static void main(String[] args) {

        AddOnServiceManager manager = new AddOnServiceManager();

        // Sample Reservation ID
        String reservationId = "RES101";

        // Create services
        Service breakfast = new Service("Breakfast", 500);
        Service airportPickup = new Service("Airport Pickup", 1200);
        Service spa = new Service("Spa Access", 1500);

        // Guest selects services
        manager.addService(reservationId, breakfast);
        manager.addService(reservationId, airportPickup);
        manager.addService(reservationId, spa);

        System.out.println();

        // Display selected services
        manager.showServices(reservationId);

        System.out.println();

        // Calculate additional cost
        double extraCost = manager.calculateServiceCost(reservationId);

        System.out.println("Total Add-On Cost for Reservation " + reservationId + ": ₹" + extraCost);
    }
}