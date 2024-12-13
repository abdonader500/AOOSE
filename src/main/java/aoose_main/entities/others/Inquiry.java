package aoose_main.entities.others;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Inquiry {
    private int inquiryId;
    private int patientId;
    private String message;
    private String status;
    private Date timestamp;

    // Constructor
    public Inquiry(int inquiryId, int patientId, String message) {
        this.inquiryId = inquiryId;
        this.patientId = patientId;
        this.message = message;
        this.timestamp = new Date(); // Current timestamp
    }

    // Getters and Setters
    public int getInquiryId() {
        return inquiryId;
    }

    public void setInquiryId(int inquiryId) {
        this.inquiryId = inquiryId;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    // Save the inquiry to the database
    public void saveToDatabase(MongoDatabase database) {
        MongoCollection<Document> collection = database.getCollection("inquiries");

        Document doc = new Document("inquiryId", this.inquiryId)
                .append("patientId", this.patientId)
                .append("message", this.message)
                .append("timestamp", this.timestamp);

        collection.insertOne(doc);
        System.out.println("Inquiry saved to database with ID: " + this.inquiryId);
    }

    // Load inquiries from the database
    public static List<Inquiry> loadFromDatabase(MongoDatabase database) {
        MongoCollection<Document> collection = database.getCollection("inquiries");
        List<Inquiry> inquiries = new ArrayList<>();

        for (Document doc : collection.find()) {
            Inquiry inquiry = new Inquiry(
                    doc.getInteger("inquiryId"),
                    doc.getInteger("patientId"),
                    doc.getString("message")
            );
            inquiry.setTimestamp(doc.getDate("timestamp"));
            inquiries.add(inquiry);
        }

        return inquiries; // Directly return the list
    }

    // Utility method to display inquiry details
    public void displayInquiryDetails() {
        System.out.println("Inquiry ID: " + inquiryId);
        System.out.println("Patient ID: " + patientId);
        System.out.println("Message: " + message);
        System.out.println("Timestamp: " + timestamp);
    }
}
