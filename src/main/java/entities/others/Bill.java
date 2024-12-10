package entities.others;

import entities.abstraction.Item;
import java.util.ArrayList;
import entities.actors.Cashier;
import enums.Shifts;

public class Bill extends Cashier {
    private int id;
    private ArrayList<Item> items;
    private long paymentId;
    private int patientId;

    // Constructor
    public Bill(int id, String fullName, String email, String password, long phoneNumber,
                long pcNumber, Shifts shiftSchedule, int salary,
                ArrayList<Item> items, long paymentId, int patientId) {
        super(id, fullName, email, password, phoneNumber, pcNumber, shiftSchedule, salary);
        this.id = id;
        this.items = new ArrayList<>(items);
        this.paymentId = paymentId;
        this.patientId = patientId;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = new ArrayList<>(items);
    }

    public void setPaymentId(long paymentId) {
        this.paymentId = paymentId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    // Getters
    public int getId() {
        return id;
    }

    public ArrayList<Item> getItems() {
        return new ArrayList<>(items);
    }

    public long getPaymentId() {
        return paymentId;
    }

    public int getPatientId() {
        return patientId;
    }

    // Additional methods from Cashier that might be useful for Bill handling
    public double calculateTotal() {
        double total = 0;
        for (Item item : items) {
            total += item.getPrice() * item.getQuantity();
        }
        return total;
    }

    // Implementing a method to display bill details for the decorator to use
    public void getDetails() {
        super.executePayment();  // Assuming we might want to call some methods from Cashier related to payment
        System.out.println("Bill ID: " + id);
        System.out.println("Patient ID: " + patientId);
        System.out.println("Payment ID: " + paymentId);
        System.out.println("Items:");
        for (Item item : items) {
            System.out.println(" - " + item.getName() + ", Quantity: " + item.getQuantity() + ", Price: $" + item.getPrice());
        }
        System.out.println("Total: $" + calculateTotal());
    }
}
