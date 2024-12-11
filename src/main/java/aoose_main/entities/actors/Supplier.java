package aoose_main.entities.actors;

import aoose_main.entities.abstraction.Item;
import aoose_main.entities.others.Order;
import aoose_main.enums.OrderStatus;
import aoose_main.remotePattern.InventoryDTO;

import java.util.ArrayList;
import java.util.List;

public class Supplier {
    private String companyName;
    private long supplierContact;
    private String companyAddress;

    // Constructor
    public Supplier(String companyName, long supplierContact, String companyAddress) {
        this.companyName = companyName;
        this.supplierContact = supplierContact;
        this.companyAddress = companyAddress;
    }

    // Setters and Getters
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public long getSupplierContact() {
        return supplierContact;
    }

    public void setSupplierContact(long supplierContact) {
        this.supplierContact = supplierContact;
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
