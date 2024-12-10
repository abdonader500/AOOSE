package entities.actors;

import java.util.ArrayList;
import java.util.List;
import entities.others.Order;
import entities.abstraction.Item;  // Assuming Item is a well-defined class in the entities package

public class InventoryClerk extends User {
    private int salary;
    private String department;

    // Constructor
    public InventoryClerk(int id, String fullName, String email, String password, long phoneNumber, int salary, String department) {
        super(id, fullName, email, password, phoneNumber);
        this.salary = salary;
        this.department = department;
    }

    // Setters and Getters
    protected void setSalary(int salary) {
        this.salary = salary;  // Admin can modify salary via this setter
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getSalary() {
        return salary;
    }

    public String getDepartment() {
        return department;
    }

    // Methods to manage orders
    public Order requestOrder(int orderId) {
        Order newOrder = new Order(orderId);  // Create a new order
        return newOrder;
    }

    // Method to view details of an item
    public void viewItemDetails(Item item) {
        System.out.println("Item Details: ID - " + item.getItemID() + ", Name - " + item.getName() + ", Price - $" + item.getPrice());
    }

    // Stock management methods
    public void addStock(Item item, int quantity) {
        System.out.println("Adding " + quantity + " units of " + item.getName() + " to inventory.");
        item.setQuantity(item.getQuantity() + quantity);  // Assuming Item has a quantity field
    }

    public void removeStock(Item item, int quantity) {
        System.out.println("Removing " + quantity + " units of " + item.getName() + " from inventory.");
        item.setQuantity(item.getQuantity() - quantity);
    }

    public void updateStock(Item item, int quantity) {
        System.out.println("Updating stock for " + item.getName() + " to " + quantity + " units.");
        item.setQuantity(quantity);
    }

    // Viewing the current stock list
    public List<Item> viewStockList() {
        System.out.println("Viewing stock list...");
        List<Item> stockList = new ArrayList<>();  // Placeholder for actual stock fetching logic
        // Example added items
        stockList.add(new Item(1, "Paracetamol", 0.50, 50));
        stockList.add(new Item(2, "Ibuprofen", 1.50, 30));
        return stockList;
    }

    public void notifySupplier(Item item, int quantity) {
        System.out.println("Notifying supplier to restock " + quantity + " units of " + item.getName() + ".");
        // Placeholder for actual notification logic
    }
}
