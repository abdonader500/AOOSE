package entities;

import enums.Shifts;

import java.util.ArrayList;

public class Cashier extends User {
    private long pcNumber;
    private Shifts shiftSchedule;
    protected int salary;
    private ArrayList<String> promotions; // Using String for simplicity; replace with Promotion class later

    // Constructor
    public Cashier(int id, String fullName, String email, String password, long phoneNumber, long pcNumber, Shifts shiftSchedule, int salary) {
        super(id, fullName, email, password, phoneNumber);
        this.pcNumber = pcNumber;
        this.shiftSchedule = shiftSchedule;
        this.salary = salary;
        this.promotions = new ArrayList<>();
    }

    // Getters and Setters
    public void setPcNumber(long pcNumber) {
        this.pcNumber = pcNumber;
    }

    public void setShiftSchedule(Shifts shiftSchedule) {
        this.shiftSchedule = shiftSchedule;
    }

    public void setPromotions(ArrayList<String> promotions) {
        this.promotions = promotions;
    }

    protected void setSalary(int salary) {
        this.salary = salary;
    }

    public long getPcNumber() {
        return pcNumber;
    }

    public Shifts getShiftSchedule() {
        return shiftSchedule;
    }

    public int getSalary() {
        return salary;
    }

    public ArrayList<String> getPromotions() {
        return promotions;
    }


    // Methods
    public void addStock() {
        System.out.println("Stock added to inventory.");
        // Logic for adding stock to inventory
    }

    public void updateStock() {
        System.out.println("Stock updated in inventory.");
        // Logic for updating stock in inventory
    }

    public void removeStock() {
        System.out.println("Stock removed from inventory.");
        // Logic for removing stock from inventory
    }

    public void markAsDrug() {
        System.out.println("Item marked as a drug.");
        // Logic for marking an item as a drug
    }

    public void processPayment() {
        System.out.println("Processing payment...");
        // Logic for processing a payment
    }

    public void applyLoyaltyDiscount() {
        System.out.println("Loyalty discount applied.");
        // Logic for applying loyalty discounts
    }

    public void addPromotion(String promotion) {
        promotions.add(promotion);
        System.out.println("Promotion added: " + promotion);
    }

    public void removePromotion(String promotion) {
        if (promotions.remove(promotion)) {
            System.out.println("Promotion removed: " + promotion);
        } else {
            System.out.println("Promotion not found.");
        }
    }

    public String generateBill() {
        System.out.println("Bill generated.");
        // Logic for generating a bill
        return "Sample Bill";
    }

    public void viewList() {
        System.out.println("Viewing list of items...");
        // Logic for viewing items or other lists
    }
}
