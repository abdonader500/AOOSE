package aoose_main.entities.others;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class Feedback {
    private int feedbackId;
    private int supplierID;
    private String text;
    private LocalDateTime date;

    // Constructor
    public Feedback(int feedbackId, int supplierID, String text, LocalDateTime date) {
        this.feedbackId = feedbackId;
        this.supplierID = supplierID;
        this.text = text;
        this.date = date;
    }

    // Getters and Setters
    public int getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(int feedbackId) {
        this.feedbackId = feedbackId;
    }

    public int getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(int supplierID) {
        this.supplierID = supplierID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void saveToDatabase(MongoDatabase database) {
        MongoCollection<Document> collection = database.getCollection("feedback");

        Document doc = new Document("feedbackId", this.feedbackId)
                .append("supplierID", this.supplierID)
                .append("text", this.text)
                .append("date", this.date.toString());

        collection.insertOne(doc);
        System.out.println("Feedback saved to database with ID: " + this.feedbackId);
    }

    // Load feedback from database
    public static List<Feedback> loadFromDatabase(MongoDatabase database) {
        MongoCollection<Document> collection = database.getCollection("feedback");
        List<Feedback> feedbackList = new ArrayList<>();

        for (Document doc : collection.find()) {
            Feedback feedback = new Feedback(
                    doc.getInteger("feedbackId"),
                    doc.getInteger("supplierID"),
                    doc.getString("text"),
                    LocalDateTime.parse(doc.getString("date"))
            );
            feedbackList.add(feedback);
        }

        return feedbackList;
    }

    public void displayFeedback() {
        System.out.println("Feedback ID: " + feedbackId);
        System.out.println("User ID: " + supplierID);
        System.out.println("Feedback Date: " + date.toString());
        System.out.println("Message: " + text);
        System.out.println("Supplier ID: " + supplierID);
        System.out.println("Date: " + date);
        System.out.println("Text: " + text);
    }
}
