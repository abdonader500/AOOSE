package entities;

import enums.AccessLevels;

public class Admin extends User {
    private int salary;
    private AccessLevels accessLevel;

    // Constructor
    public Admin(int id, String fullName, String email, String password, long phoneNumber, int salary, AccessLevels accessLevel) {
        super(id, fullName, email, password, phoneNumber); // Call the parent constructor
        this.salary = salary;
        this.accessLevel = accessLevel;
    }

    // setters and getters

     public void setSalary(int salary) {
            this.salary = salary;
        }

    public void setAccessLevel(AccessLevels accessLevel) {
        this.accessLevel = accessLevel;
    }

    public int getSalary() {
        return salary;
    }

    public AccessLevels getAccessLevel() {
        return accessLevel;
    }



    // Methods
    public void viewUserDetails(User user) {
        System.out.println("Viewing User Details:");
        System.out.println("ID: " + user.getId());
        System.out.println("Name: " + user.getFullName());
        System.out.println("Email: " + user.getEmail());
        System.out.println("Phone: " + user.getPhoneNumber());
    }

    public void generateReport(String reportType) {
        System.out.println("Generating report of type: " + reportType);
        // Logic to create a Report object and generate it
    }

    public void addUser(User newUser) {
        System.out.println("User added successfully: " + newUser.getFullName());
        // Logic to interact with a UserMapper or database
    }

    public void removeUser(int userId) {
        System.out.println("User with ID " + userId + " removed successfully.");
        // Logic to interact with a UserMapper or database
    }

    public void viewInquiry() {
        System.out.println("Viewing customer inquiries...");
        // Logic to fetch inquiries from the database
    }

    public void createPromotion(String description, int percentage) {
        System.out.println("Creating promotion: " + description + " with " + percentage + "% discount.");
        // Logic to create and save a promotion
    }

    public void answerCustomerInquiry(String answer) {
        System.out.println("Answering customer inquiry: " + answer);
        // Logic to interact with an Inquiry object
    }

    public void setEmployeeSalary(Cashier cashier, int newSalary) {
        cashier.salary = newSalary; // Accessing private field directly because Admin is a trusted authority
        System.out.println("Salary updated for " + cashier.getFullName() + ": $" + newSalary);
    }
    public void setEmployeeSalary(Pharmacist pharmacist, int newSalary) {
        pharmacist.salary = newSalary; // Accessing private salary field
        System.out.println("Salary updated for " + pharmacist.getFullName() + ": $" + newSalary);
    }

}
