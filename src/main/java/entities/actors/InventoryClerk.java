package entities.actors;

import java.util.ArrayList;
import java.util.List;

public class InventoryClerk extends User {
    private int salary;
    private String department;

    // Constructor
    public InventoryClerk(int id, String fullName, String email, String password, long phoneNumber, int salary, String department) {
        super(id, fullName, email, password, phoneNumber);
        this.salary = salary;
        this.department = department;
    }


    // setters and getters

    protected void setSalary(int salary) {
        this.salary = salary; // Admin can modify salary via this setter
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


    // Methods
    public void viewItemDetails() {
        System.out.println("Viewing item details...");
        // Logic to fetch and display item details
    }

    public void addStock() {
        System.out.println("Adding stock to inventory...");
        // Logic to add stock to inventory
    }

    public void removeStock() {
        System.out.println("Removing stock from inventory...");
        // Logic to remove stock from inventory
    }

    public void updateStock() {
        System.out.println("Updating stock in inventory...");
        // Logic to update stock in inventory
    }

    public List<String> viewStockList() {
        System.out.println("Viewing stock list...");
        List<String> stockList = new ArrayList<>(); // Placeholder; replace with Item objects later
        stockList.add("Paracetamol - 50 units");
        stockList.add("Ibuprofen - 30 units");
        return stockList;
    }

    public void notifySupplier() {
        System.out.println("Notifying supplier about stock requirements...");
        // Logic to notify supplier
    }
}
