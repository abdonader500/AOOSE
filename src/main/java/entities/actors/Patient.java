package entities.actors;

import enums.Gender;
import enums.PaymentStatus;

public class Patient extends User {
    private String address;
    private Gender gender;
    private int age;
    private String emergencyContact;
    private String issue;
    private String loyaltyDiscount;

    // Constructor
    public Patient(int id, String fullName, String email, String password, long phoneNumber,
                   String address, Gender gender, int age, String emergencyContact,
                   String issue, String loyaltyDiscount) {
        super(id, fullName, email, password, phoneNumber); // Call the parent constructor
        this.address = address;
        this.gender = gender;
        this.age = age;
        this.emergencyContact = emergencyContact;
        this.issue = issue;
        this.loyaltyDiscount = loyaltyDiscount;
    }

    // setters and getters
     public void setAddress(String address) {
            this.address = address;
        }

    public void setGender(Gender gender) {
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

    public String getAddress() {
        return address;
    }

    public Gender getGender() {
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


    // Methods
    public void addPrescription() {
        System.out.println("Prescription added.");
    }

    public void removePrescription() {
        System.out.println("Prescription removed.");
    }

    public void sendInsuranceForm() {
        System.out.println("Insurance form sent.");
    }

    public void writeInquiry() {
        System.out.println("Inquiry written.");
    }

    public PaymentStatus payBill() {
        System.out.println("Bill paid successfully.");
        return PaymentStatus.Success;
    }
}
