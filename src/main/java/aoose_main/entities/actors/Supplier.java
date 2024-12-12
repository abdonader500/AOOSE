package aoose_main.entities.actors;

import aoose_main.entities.abstraction.Item;
import aoose_main.entities.others.Order;
import aoose_main.enums.OrderStatus;
import java.util.List;

public class Supplier extends User {
    private String companyName;
    private String companyAddress;

    // Constructor
    public Supplier(int id, String fullName, String email, String password, long phoneNumber,
                    String companyName, String companyAddress) {
        super(id, fullName, email, password, phoneNumber); // Call the parent constructor
        this.companyName = companyName;
        this.companyAddress = companyAddress;
    }

    // Setters and Getters
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    // Method to register a new order
    public Order registerOrder(int orderID, List<Item> items) {
        Order newOrder = new Order(orderID, OrderStatus.Pending); // Default status is Pending

        // Associate items with the order directly
        newOrder.setItems(items);
        newOrder.saveToDatabase();
        System.out.println("Order registered by supplier: " + companyName);
        return newOrder;
    }

    // Method to update stock information
    public void updateAvailableStock(Order order, OrderStatus status) {
        order.setStatus(status); // Update the order status (e.g., Received, Rejected)
        order.saveToDatabase();
        System.out.println("Order status updated to: " + status.name());
    }
}
