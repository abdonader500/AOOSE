package aoose_main.entities.actors;

import aoose_main.entities.others.Insurance;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;

public class Patient extends User {
    private String address;
    private String gender;
    private int age;
    private String emergencyContact;
    private String issue;
    private int loyaltyDiscount;
    private int insuranceID; // Reference to the insurance

    private static MongoCollection<Document> insuranceCollection;

    // Constructor
    public Patient(int id, String fullName, String email, String password, long phoneNumber,
                   String address, String gender, int age, String emergencyContact, String issue,
                   int loyaltyDiscount, Insurance insurance) {
        super(id, fullName, email, password, phoneNumber);
        this.address = address;
        this.gender = gender;
        this.age = age;
        this.emergencyContact = emergencyContact;
        this.issue = issue;
        this.loyaltyDiscount = loyaltyDiscount;

        // Set the insuranceID from the Insurance object
        this.insuranceID = (insurance != null) ? (int) insurance.getInsuranceID() : 0;
    }



    // Static method to initialize the MongoDB insurance collection
    public static void initializeDatabase(MongoDatabase database) {
        insuranceCollection = database.getCollection("insurances");
    }

    // Methods to interact with the database for insurance
    public Insurance getInsurance() {
        if (insuranceCollection == null) {
            throw new IllegalStateException("Database collection is not initialized.");
        }
        Document doc = insuranceCollection.find(eq("insuranceID", insuranceID)).first();
        return doc != null ? Insurance.fromDocument(doc) : null;
    }

    public void setInsuranceID(int insuranceID) {
        this.insuranceID = insuranceID;
    }

    public int getInsuranceID() {
        return insuranceID;
    }

    // Write an inquiry related to the patient
    public void writeInquiry(MongoDatabase database, String inquiryMessage) {
        MongoCollection<Document> collection = database.getCollection("inquiries");
        Document inquiry = new Document("patientId", getId())
                .append("message", inquiryMessage)
                .append("status", "pending");
        collection.insertOne(inquiry);
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

    public void setLoyaltyDiscount(int loyaltyDiscount) {
        this.loyaltyDiscount = loyaltyDiscount;
    }

    public int getLoyaltyDiscount() {
        return loyaltyDiscount;
    }

    public static Patient loadFromDatabase(int id, MongoDatabase database) {
        // Get the MongoDB collection for patients
        MongoCollection<Document> collection = database.getCollection("patients");

        // Find the patient by ID
        Document doc = collection.find(eq("id", id)).first();
        if (doc != null) {
            // Extract the insurance document if it exists
            Insurance insurance = null;
            if (doc.containsKey("insurance")) {
                Document insuranceDoc = (Document) doc.get("insurance");
                insurance = Insurance.fromDocument(insuranceDoc);
            }

            // Return the patient object constructed from the database document
            return new Patient(
                    doc.getInteger("id"),
                    doc.getString("fullName"),
                    doc.getString("email"),
                    doc.getString("password"),
                    doc.getLong("phoneNumber"),
                    doc.getString("address"),
                    doc.getString("gender"),
                    doc.getInteger("age"),
                    doc.getString("emergencyContact"),
                    doc.getString("issue"),
                    doc.getInteger("loyaltyDiscount"),
                    insurance
            );
        }

        // Log if the patient was not found
        System.out.println("Patient with ID " + id + " not found in the database.");
        return null;
    }

    public void updateLoyaltyDiscountInDatabase(MongoDatabase database) {
        MongoCollection<Document> collection = database.getCollection("patients");
        collection.updateOne(eq("id", getId()), new Document("$set", new Document("loyaltyDiscount", loyaltyDiscount)));
        System.out.println("Loyalty discount updated in database for patient ID: " + getId());
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
        System.out.println("Insurance ID: " + insuranceID);

        Insurance insurance = getInsurance();
        if (insurance != null) {
            insurance.displayInsuranceDetails();
        } else {
            System.out.println("No insurance assigned or insurance not found.");
        }
    }
}
