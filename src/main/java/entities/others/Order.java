package entities.others;

import java.util.ArrayList;
import java.util.List;
import entities.abstraction.Item;  // Ensure this import path matches the location of your Item class

public class Order {
    private int orderID;
    private List<Item> items;  // List to hold items in the order
    private double totalAmount;  // To hold the calculated total of the order

    // Constructor
    public Order(int orderID) {
        this.orderID = orderID;
        this.items = new ArrayList<>();
        this.totalAmount = 0.0;  // Initialize total amount
    }

    // Method to add an item to the order
    public void addItem(Item item) {
        this.items.add(item);
        this.totalAmount += item.getPrice() * item.getQuantity();  // Update total amount
    }

    // Method to remove an item from the order
    public void removeItem(Item item) {
        if (this.items.contains(item)) {
            this.items.remove(item);
            this.totalAmount -= item.getPrice() * item.getQuantity();  // Update total amount
        }
    }

    // Getters and Setters
    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public List<Item> getItems() {
        return new ArrayList<>(items);  // Return a copy of the items list
    }

    public void setItems(List<Item> items) {
        this.items = new ArrayList<>(items);
        recalculateTotal();  // Recalculate total whenever items list is set
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    private void recalculateTotal() {
        totalAmount = 0.0;  // Reset total amount
        for (Item item : items) {
            totalAmount += item.getPrice() * item.getQuantity();
        }
    }

    // Method to display order details
    public void displayOrderDetails() {
        System.out.println("Order ID: " + orderID);
        System.out.println("Items in the Order:");
        for (Item item : items) {
            System.out.println(item.getName() + ", Quantity: " + item.getQuantity() + ", Price: $" + item.getPrice());
        }
        System.out.println("Total Order Amount: $" + totalAmount);
    }
}
