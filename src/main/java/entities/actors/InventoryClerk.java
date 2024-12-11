package entities.actors;

import java.util.ArrayList;
import java.util.List;
import entities.others.Order;
import entities.abstraction.Item;  // Assuming Item is a well-defined class in the entities package

public class InventoryClerk extends User {
    private int salary;
    private String department;
    private List<Item> inventory;

    // Constructor
    public InventoryClerk(int id, String fullName, String email, String password, long phoneNumber, int salary, String department) {
        super(id, fullName, email, password, phoneNumber);
        this.salary = salary;
        this.department = department;
        this.inventory = new ArrayList<>(); // Initialize the inventory list
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

    public void addStock(Item item, int quantity) {
        boolean found = false;
        for (Item invItem : inventory) {
            if (invItem.getItemID() == item.getItemID()) {
                invItem.setQuantity(invItem.getQuantity() + quantity);
                found = true;
                System.out.println("Updated " + item.getName() + ": + " + quantity + " units");
                break;
            }
        }
        if (!found) {
            item.setQuantity(quantity);
            inventory.add(item);
            System.out.println("Added new item to inventory: " + item.getName() + ", Quantity: " + quantity);
        }
    }

    public void removeStock(Item item, int quantity) {
        for (Item invItem : inventory) {
            if (invItem.getItemID() == item.getItemID()) {
                if (invItem.getQuantity() >= quantity) {
                    invItem.setQuantity(invItem.getQuantity() - quantity);
                    System.out.println("Removed " + quantity + " units of " + item.getName());
                    break;
                } else {
                    System.out.println("Not enough stock to remove " + quantity + " units of " + item.getName());
                }
            }
        }
    }

    public void updateStock(Item item, int quantity) {
        for (Item invItem : inventory) {
            if (invItem.getItemID() == item.getItemID()) {
                invItem.setQuantity(quantity);
                System.out.println("Updated stock for " + item.getName() + " to " + quantity + " units.");
                break;
            }
        }
    }

    // Viewing the current stock list
    public List<Item> viewStockList() {
        System.out.println("Current Inventory:");
        for (Item item : inventory) {
            System.out.println("Item ID: " + item.getItemID() + ", Name: " + item.getName() + ", Quantity: " + item.getQuantity());
        }
        return new ArrayList<>(inventory); // Returns a copy of the inventory list
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


    public void notifySupplier(Item item, int quantity) {
        System.out.println("Notifying supplier to restock " + quantity + " units of " + item.getName() + ".");
        // Placeholder for actual notification logic
    }
}
