package entities.others;

import entities.abstraction.Item;
import java.util.ArrayList;

public class Bill {
    private long id;
    private ArrayList<Item> items; // Use your Item class
    private long paymentId;
    private int patientId;

    // Constructor
    public Bill(long id, ArrayList<Item> items, long paymentId, int patientId) {
        this.id = id;
        this.items = items;
        this.paymentId = paymentId;
        this.patientId = patientId;
    }

    // Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(long paymentId) {
        this.paymentId = paymentId;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    // Calculate total bill amount
    public double calculateTotal() {
        double total = 0;
        for (Item item : items) {
            total += item.getPrice(); // Sum the price of each item
        }
        return total;
    }

    // Display bill details
    public void displayBill() {
        System.out.println("Bill ID: " + id);
        System.out.println("Patient ID: " + patientId);
        System.out.println("Payment ID: " + paymentId);
        System.out.println("Items in the Bill:");
        for (Item item : items) {
            item.displayDetails(); // Call the displayDetails method from the Item class
            System.out.println("--------------------");
        }
        System.out.println("Total Amount: $" + calculateTotal());
    }

    @Override
    public String toString() {
        return "Bill{" +
                "id=" + id +
                ", items=" + items +
                ", paymentId=" + paymentId +
                ", patientId=" + patientId +
                '}';
    }
}
