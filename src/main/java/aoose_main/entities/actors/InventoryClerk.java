package aoose_main.entities.actors;

import java.util.ArrayList;
import java.util.List;

import aoose_main.InventoryDAO;
import aoose_main.entities.abstraction.Item;
import aoose_main.entities.others.Order;
import aoose_main.enums.OrderStatus;
import aoose_main.remotePattern.InventoryDTO;

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
        this.salary = salary;
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

    // Load an order by ID
    public Order requestOrder(int orderId) {
        Order order = Order.loadFromDatabase(orderId);
        if (order == null) {
            System.out.println("Order with ID " + orderId + " not found.");
        }
        return order;
    }

    // Approve an order and add items to inventory
    // Approve an order and add items to inventory
    public void approveOrder(int orderId, List<InventoryDTO> inventory, InventoryDAO inventoryDAO) {
        Order order = requestOrder(orderId);
        if (order != null && order.getStatus() == OrderStatus.Pending) {
            // Update the order status to Received
            order.setStatus(OrderStatus.Received);
            order.saveToDatabase();
            System.out.println("Order with ID " + orderId + " approved and marked as Received.");

            // Add or update items in the inventory
            for (Item item : order.getItems()) {
                if (item == null || item.getItemID() <= 0 || item.getName() == null || item.getQuantity() <= 0) {
                    System.out.println("Skipping invalid item in order: " + order.getOrderID());
                    continue;
                }

                // Check for existing item by item ID
                InventoryDTO existingItem = inventoryDAO.getItemById((int) item.getItemID());

                if (existingItem != null) {
                    // Update the quantity of the existing item
                    int updatedQuantity = existingItem.getQuantity() + item.getQuantity();
                    InventoryDTO updatedItem = new InventoryDTO(
                            existingItem.getItemID(),
                            existingItem.getItemName(),
                            existingItem.getPrice(),
                            updatedQuantity
                    );
                    inventoryDAO.updateItem(updatedItem); // Persist updated item

                    // Update the in-memory list
                    inventory.removeIf(invItem -> invItem.getItemID() == updatedItem.getItemID());
                    inventory.add(updatedItem);
                    System.out.println("Updated inventory item: " + updatedItem.getItemName() + " with new quantity: " + updatedQuantity);
                } else {
                    // Create a new inventory item
                    InventoryDTO newItem = new InventoryDTO(
                            (int) item.getItemID(),
                            item.getName(),
                            item.getPrice(),
                            item.getQuantity()
                    );
                    inventoryDAO.createItem(newItem); // Persist new item
                    inventory.add(newItem); // Add to in-memory list
                    System.out.println("Added new inventory item: " + newItem.getItemName());
                }
            }
        } else {
            System.out.println("Order with ID " + orderId + " cannot be approved.");
        }
    }





    // Reject an order
    public void rejectOrder(int orderId) {
        Order order = requestOrder(orderId);
        if (order != null && order.getStatus() == OrderStatus.Pending) {
            order.setStatus(OrderStatus.Rejected);
            order.saveToDatabase();
            System.out.println("Order with ID " + orderId + " rejected.");
        } else {
            System.out.println("Order with ID " + orderId + " cannot be rejected (not pending).");
        }
    }

    // View details of an order
    public void viewOrderDetails(int orderId) {
        Order order = requestOrder(orderId);
        if (order != null) {
            System.out.println("Order Details:");
            System.out.println("Order ID: " + order.getOrderID());
            System.out.println("Order Status: " + order.getStatus());
            System.out.println("Items:");
            for (Item item : order.getItems()) {
                System.out.println(" - " + item.getName() + ", Quantity: " + item.getQuantity() + ", Price: $" + item.getPrice());
            }
        }
    }
}
