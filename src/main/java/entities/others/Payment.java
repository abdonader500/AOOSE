package entities.others;

import entities.actors.Patient;
import entities.others.Bill;  // Assuming Bill is in the entities package

public class Payment {
    private Patient patient;  // Composition: Payment contains Patient details
    private Bill bill;        // Composition: Payment is linked to a Bill
    private double amount;
    private String date;
    private String paymentType;
    private String status;

    // Constructor
    public Payment(Patient patient, Bill bill, double amount, String date, String paymentType, String status) {
        this.patient = patient;  // Set the patient associated with this payment
        this.bill = bill;        // Set the bill associated with this payment
        this.amount = amount;
        this.date = date;
        this.paymentType = paymentType;
        this.status = status;
    }

    // Getters and Setters for the Patient
    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }

    // Getters and Setters for the Bill
    public Bill getBill() { return bill; }
    public void setBill(Bill bill) { this.bill = bill; }

    // Getters and Setters for other attributes
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getPaymentType() { return paymentType; }
    public void setPaymentType(String paymentType) { this.paymentType = paymentType; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
