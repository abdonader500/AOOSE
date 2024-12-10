package entities.actors;

import entities.others.Insurance;

public class Patient extends User {
    private String address;
    private String gender;
    private int age;
    private String emergencyContact;
    private String issue;
    private String loyaltyDiscount;
    private Insurance insurance; // Aggregation: Patient can have an Insurance

    // Constructor
    public Patient(int id, String fullName, String email, String password, long phoneNumber,
                   String address) {
        super(id, fullName, email, password, phoneNumber); // Call parent constructor
        this.address = address;
        this.gender = gender;
        this.age = age;
        this.emergencyContact = emergencyContact;
        this.issue = issue;
        this.loyaltyDiscount = loyaltyDiscount;
    }


    // setters and getters
    public void setInsurance(Insurance insurance) {
        this.insurance = insurance;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public void setLoyaltyDiscount(String loyaltyDiscount) {
        this.loyaltyDiscount = loyaltyDiscount;
    }

    public Insurance getInsurance() {
        return insurance;
    }

    public String getAddress() {
        return address;
    }

    public String getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public String getIssue() {
        return issue;
    }

    public String getLoyaltyDiscount() {
        return loyaltyDiscount;
    }

    // Method to display patient details and associated insurance
    public void displayPatientDetails() {
        System.out.println("Patient Name: " + getFullName());
        System.out.println("Address: " + address);
        System.out.println("Gender: " + gender);
        System.out.println("Age: " + age);
        if (insurance != null) {
            System.out.println("Insurance Details:");
            insurance.displayInsuranceDetails();
        } else {
            System.out.println("No insurance assigned.");
        }
    }
}
