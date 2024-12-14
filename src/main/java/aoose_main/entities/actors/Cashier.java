package aoose_main.entities.actors;

import aoose_main.entities.others.Insurance;
import aoose_main.entities.others.MakePayment;
import aoose_main.entities.others.Payment;
import aoose_main.entities.others.Bill;
import aoose_main.entities.abstraction.Item;
import aoose_main.entities.abstraction.PromotionInstance;
import aoose_main.enums.Shifts;
import com.mongodb.client.MongoDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Cashier extends User {
    private long pcNumber;
    private Shifts shiftSchedule;
    protected int salary;
    private List<PromotionInstance> promotionInstances;
    private MakePayment paymentStrategy;

    // Constructor
    public Cashier(int id, String fullName, String email, String password, long phoneNumber,
                   long pcNumber, Shifts shiftSchedule, int salary) {
        super(id, fullName, email, password, phoneNumber);
        this.pcNumber = pcNumber;
        this.shiftSchedule = shiftSchedule;
        this.salary = salary;
        this.promotionInstances = new ArrayList<>();
    }

    // Getters and Setters
    public void setPcNumber(long pcNumber) {
        this.pcNumber = pcNumber;
    }

    public void setShiftSchedule(Shifts shiftSchedule) {
        this.shiftSchedule = shiftSchedule;
    }

    public void setPromotionInstances(List<PromotionInstance> promotionInstances) {
        this.promotionInstances = promotionInstances;
    }

    protected void setSalary(int salary) {
        this.salary = salary;
    }

    public long getPcNumber() {
        return pcNumber;
    }

    public Shifts getShiftSchedule() {
        return shiftSchedule;
    }

    public int getSalary() {
        return salary;
    }

    public List<PromotionInstance> getPromotionInstances() {
        return promotionInstances;
    }

    public MakePayment getPaymentStrategy() {
        return paymentStrategy;
    }

    public void setPaymentStrategy(MakePayment paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    // Generate bill
    public Bill generateBill(List<Item> items, Patient patient, MongoDatabase database) {
        Bill bill = new Bill(items, patient.getId());
        bill.saveToDatabase(database); // Save the bill to the database
        System.out.println("Bill generated and saved for patient ID: " + patient.getId());
        return bill;
    }

    // Apply promotion
    public void applyPromotion(Bill bill, PromotionInstance promotion) {
        if (promotion != null && promotion.isActive(new Date())) {
            bill.applyPromotion(promotion);
            System.out.println("Promotion applied: " + promotion.getPromotionRef().getName());
        } else {
            System.out.println("Invalid or inactive promotion.");
        }
    }

    public void addInsuranceDiscount(Bill bill, Patient patient, MongoDatabase database) {
        Insurance patientInsurance = patient.getInsurance();
        if (patientInsurance != null) {
            double insuranceDiscount = bill.getTotalAmount() * patientInsurance.getInsurancePercentage() / 100.0;
            bill.applyInsuranceDiscount(insuranceDiscount); // Apply the insurance discount
            bill.updateInDatabase(database); // Update the bill in the database
            System.out.println("Insurance discount of $" + insuranceDiscount + " applied for patient ID: " + patient.getId());
        } else {
            System.out.println("No insurance available for patient ID: " + patient.getId());
        }
    }
    // Apply loyalty discount
    public void applyLoyaltyDiscount(Bill bill, Patient patient, MongoDatabase database) {
        if (patient.getLoyaltyDiscount() >= 1000) {
            bill.applyLoyaltyDiscount(100); // Subtract 100 pounds from the bill
            patient.setLoyaltyDiscount(patient.getLoyaltyDiscount() - 1000); // Deduct 1000 points
            patient.updateLoyaltyDiscountInDatabase(database);
            System.out.println("Loyalty discount applied and updated for patient ID: " + patient.getId());
        }
        else{
            double amountPaid = bill.getTotalAfterDiscounts();
            patient.setLoyaltyDiscount(patient.getLoyaltyDiscount() + (int) amountPaid);
            patient.updateLoyaltyDiscountInDatabase(database);
            System.out.println("Amount of " + (int) amountPaid + " added to the loyalty discount for patient ID: " + patient.getId());
        }
    }

    // Execute payment and save to the database
    public void executePayment(Payment payment, MongoDatabase database) {
        if (paymentStrategy != null) {
            payment.processPayment(paymentStrategy);
            payment.setStatus("Paid");

            // Save payment to database
            payment.saveToDatabase(database);

            // Link payment ID to the bill and update the bill's amount paid
            Bill bill = payment.getBill();
            bill.setPaymentId(payment.getPaymentId());
            bill.setAmountPaid(payment.getAmount());
            bill.updateInDatabase(database);

            System.out.println("Payment processed and saved successfully.");
        } else {
            System.out.println("No payment strategy set. Payment failed.");
        }
    }

    // Create payment
    public Payment createPayment(Patient patient, Bill bill, String paymentType, String date) {
        double amountAfterDiscounts = bill.getTotalAfterDiscounts();
        Payment payment = new Payment(patient, bill, amountAfterDiscounts, date, paymentType, "Unpaid");
        System.out.println("Payment created for Bill ID: " + bill.getId());
        return payment;
    }
}
