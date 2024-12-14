package aoose_main.entities.others;

import aoose_main.entities.actors.Patient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.UUID;

public class Payment {
    private String paymentId; // Unique identifier for the payment
    private Patient patient;
    private Bill bill;
    private double amount;
    private String date;
    private String paymentType;
    private String status; // e.g., "Paid", "Unpaid"

    // Constructor
    public Payment(Patient patient, Bill bill, double amount, String date, String paymentType, String status) {
        this.paymentId = UUID.randomUUID().toString(); // Generate a unique ID for each payment
        this.patient = patient;
        this.bill = bill;
        this.amount = amount;
        this.date = date;
        this.paymentType = paymentType;
        this.status = status;
    }

    // Getters and Setters
    public String getPaymentId() {
        return paymentId;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Process payment using the strategy
    public void processPayment(MakePayment paymentStrategy) {
        if (paymentStrategy != null) {
            paymentStrategy.processPayment();
            this.status = "Paid";
        } else {
            System.out.println("No payment strategy provided.");
        }
    }

    // Save the payment to the database
    public void saveToDatabase(MongoDatabase database) {
        MongoCollection<Document> collection = database.getCollection("payments");
        Document paymentDoc = new Document("paymentId", this.paymentId)
                .append("patientId", this.patient != null ? this.patient.getId() : null)
                .append("billId", this.bill != null ? this.bill.getId() : null)
                .append("amountPaid", this.amount)
                .append("paymentType", this.paymentType)
                .append("date", this.date)
                .append("status", this.status);
        collection.insertOne(paymentDoc);
        System.out.println("Payment added to database with ID: " + this.paymentId);
    }

    // Display payment details
    public void displayPaymentDetails() {
        System.out.println("Payment Details:");
        System.out.println("Payment ID: " + paymentId);
        System.out.println("Patient: " + (patient != null ? patient.getFullName() : "N/A"));
        System.out.println("Bill ID: " + (bill != null ? bill.getId() : "N/A"));
        System.out.println("Amount: $" + amount);
        System.out.println("Date: " + date);
        System.out.println("Payment Type: " + paymentType);
        System.out.println("Status: " + status);
    }
}
