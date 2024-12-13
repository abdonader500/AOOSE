package aoose_main.entities.others;

import aoose_main.entities.abstraction.Item;
import aoose_main.entities.abstraction.PromotionInstance;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class Bill implements BillDecorator {
    private int id; // Unique identifier for the bill
    private List<Item> items; // List of items in the bill
    private int patientId; // ID of the patient for whom the bill is generated
    private double totalAmount; // Total amount for the bill
    private double discount; // Total discount applied to the bill
    private PromotionInstance appliedPromotion; // Applied promotion
    private String paymentId; // Payment ID associated with the bill
    private double amountPaid; // Amount paid after discounts

    // Constructor
    public Bill(List<Item> items, int patientId) {
        this.id = (int) (Math.random() * 100000); // Generate random ID
        this.items = new ArrayList<>(items);
        this.patientId = patientId;
        this.totalAmount = calculateTotal();
        this.discount = 0;
        this.amountPaid = 0; // Default to 0
        this.paymentId = null; // Default to null
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public List<Item> getItems() {
        return new ArrayList<>(items);
    }

    public int getPatientId() {
        return patientId;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    private int generateUniqueId() {
        return (int) (System.currentTimeMillis() & 0xfffffff); // Example logic
    }

    public double calculateTotal() {
        double total = 0;
        for (Item item : items) {
            total += item.getPrice() * item.getQuantity();
        }
        return total;
    }

    // Apply promotion
    public void applyPromotion(PromotionInstance promotion) {
        double promotionDiscount = totalAmount * promotion.getPromotionRef().getDiscountPercentage() / 100;
        discount += promotionDiscount;
        appliedPromotion = promotion;
    }

    // Apply loyalty discount
    public void applyLoyaltyDiscount(double amount) {
        discount += amount;
    }

    public double getTotalAfterDiscounts() {
        return totalAmount - discount;
    }

    public void saveToDatabase(MongoDatabase database) {
        MongoCollection<Document> collection = database.getCollection("bills");

        // Create document from the Bill
        Document doc = new Document("id", this.id)
                .append("patientId", this.patientId)
                .append("paymentId", this.paymentId)
                .append("totalAmount", this.totalAmount)
                .append("discount", this.discount)
                .append("amountPaid", this.amountPaid)
                .append("items", this.items.stream().map(item -> {
                    return new Document("itemId", item.getItemID())
                            .append("name", item.getName())
                            .append("price", item.getPrice())
                            .append("quantity", item.getQuantity());
                }).toList());

        // Insert into the collection
        collection.insertOne(doc);
        System.out.println("Bill saved to database with ID: " + this.id);
    }

    public void updateInDatabase(MongoDatabase database) {
        MongoCollection<Document> collection = database.getCollection("bills");
        Document updateDoc = new Document("$set", new Document()
                .append("paymentId", this.paymentId)
                .append("amountPaid", this.amountPaid));
        collection.updateOne(new Document("id", this.id), updateDoc);
        System.out.println("Bill updated in database with ID: " + this.id);
    }

    // Display bill details
    @Override
    public void getDetails() {
        System.out.println("Bill ID: " + id);
        System.out.println("Patient ID: " + patientId);
        System.out.println("Items:");
        for (Item item : items) {
            System.out.println(" - " + item.getName() + ": Quantity = " + item.getQuantity() + ", Price = $" + item.getPrice());
        }
        System.out.println("Total Before Discounts: $" + totalAmount);
        System.out.println("Discount Applied: $" + discount);
        System.out.println("Final Total: $" + getTotalAfterDiscounts());
        System.out.println("Amount Paid: $" + amountPaid);
        if (appliedPromotion != null) {
            System.out.println("Applied Promotion: " + appliedPromotion.getPromotionRef().getName());
        }
        System.out.println("Payment ID: " + (paymentId != null ? paymentId : "N/A"));
    }
}
