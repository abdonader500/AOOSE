package aoose_main.entities.actors;

import aoose_main.entities.others.MakePayment;
import aoose_main.entities.abstraction.PromotionInstance;
import aoose_main.enums.Shifts;

import java.util.ArrayList;
import java.util.List;

public class Cashier extends User {
    private long pcNumber;
    private Shifts shiftSchedule;
    protected int salary;
    private List<PromotionInstance> promotionInstances; // Using String for simplicity; replace with Promotion class later
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

    public void setPromotionInstances(List<PromotionInstance> promotionInstances) { this.promotionInstances = promotionInstances;}

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


    // Methods

    public void markAsDrug() {
        System.out.println("Item marked as a drug.");
        // Logic for marking an item as a drug
    }

    public void processPayment() {
        System.out.println("Processing payment...");
        // Logic for processing a payment
    }

    public void applyLoyaltyDiscount() {
        System.out.println("Loyalty discount applied.");
        // Logic for applying loyalty discounts
    }

    public void addPromotionInstance(PromotionInstance instance) {
        promotionInstances.add(instance);
        System.out.println("PromotionInstance added: " + instance.getInstanceID());
    }

    public void removePromotionInstance(long instanceID) {
        promotionInstances.removeIf(instance -> instance.getInstanceID() == instanceID);
        System.out.println("PromotionInstance removed with ID: " + instanceID);
    }

    public void viewPromotionInstances() {
        System.out.println("List of PromotionInstances managed by Cashier:");
        for (PromotionInstance instance : promotionInstances) {
            System.out.println("- ID: " + instance.getInstanceID() +
                    ", Promotion: " + instance.getPromotionRef().getName() +
                    ", Start Date: " + instance.getStartDate() +
                    ", End Date: " + instance.getEndDate());
        }
    }
    public MakePayment getPaymentStrategy() {
        return paymentStrategy;
    }

    public void setPaymentStrategy(MakePayment paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public void executePayment() {
        if (paymentStrategy != null) {
            paymentStrategy.processPayment();
        } else {
            System.out.println("No payment method set for this cashier.");
        }
    }

    public String generateBill() {
        System.out.println("Bill generated.");
        // Logic for generating a bill
        return "Sample Bill";
    }

    public void viewList() {
        System.out.println("Viewing list of items...");
        // Logic for viewing items or other lists
    }
}
