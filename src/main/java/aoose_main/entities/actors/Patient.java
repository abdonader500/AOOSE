package aoose_main.entities.actors;

import aoose_main.entities.others.Insurance;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

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
                   String address, String gender, int age, String emergencyContact, String issue,
                   String loyaltyDiscount, Insurance insurance) {
        super(id, fullName, email, password, phoneNumber);
        this.address = address;
        this.gender = gender;
        this.age = age;
        this.emergencyContact = emergencyContact;
        this.issue = issue;
        this.loyaltyDiscount = loyaltyDiscount;
        this.insurance = insurance;
    }

    // Methods to interact with the database for insurance
    public void sendInsuranceForm(MongoDatabase database) {
        if (this.insurance != null) {
            this.insurance.sendForm(database);
        }
    }

    // Write an inquiry related to the patient
    public void writeInquiry(MongoDatabase database, String inquiryMessage) {
        MongoCollection<Document> collection = database.getCollection("inquiries");
        Document inquiry = new Document("patientId", getId())
                .append("message", inquiryMessage)
                .append("status", "pending");
        collection.insertOne(inquiry);
    }

    // Update insurance details
    public void updateInsurance(Insurance newInsurance) {
        this.insurance = newInsurance;
    }

    // Setters & Getters
    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getIssue() {
        return issue;
    }

    public void setLoyaltyDiscount(String loyaltyDiscount) {
        this.loyaltyDiscount = loyaltyDiscount;
    }

    public String getLoyaltyDiscount() {
        return loyaltyDiscount;
    }

    public Insurance getInsurance() {
        return insurance;
    }

    // Display patient details
    public void displayPatientDetails() {
        System.out.println("Patient Name: " + getFullName());
        System.out.println("Address: " + getAddress());
        System.out.println("Gender: " + getGender());
        System.out.println("Age: " + getAge());
        System.out.println("Emergency Contact: " + getEmergencyContact());
        System.out.println("Issue: " + getIssue());
        System.out.println("Loyalty Discount: " + getLoyaltyDiscount());
        if (insurance != null) {
            insurance.displayInsuranceDetails();
        }
    }
}
