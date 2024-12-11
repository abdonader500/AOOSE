package aoose_main.entities.actors;

import java.util.List;

import aoose_main.entities.abstraction.Item;
import aoose_main.entities.others.Order;
import aoose_main.enums.OrderStatus;

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

    // Approve an order
    public void approveOrder(int orderId) {
        Order order = requestOrder(orderId);
        if (order != null) {
            order.setStatus(OrderStatus.Received);
            order.saveToDatabase();
            System.out.println("Order with ID " + orderId + " approved and marked as Received.");
        }
    }

    // Reject an order
    public void rejectOrder(int orderId) {
        Order order = requestOrder(orderId);
        if (order != null) {
            order.setStatus(OrderStatus.Rejected);
            order.saveToDatabase();
            System.out.println("Order with ID " + orderId + " rejected.");
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
