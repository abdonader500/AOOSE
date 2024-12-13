package aoose_main;

import aoose_main.entities.abstraction.Item;
import aoose_main.entities.abstraction.Promotion;
import aoose_main.entities.abstraction.PromotionInstance;
import aoose_main.entities.actors.Admin;
import aoose_main.entities.actors.Cashier;
import aoose_main.entities.actors.Patient;
import aoose_main.entities.others.Bill;
import aoose_main.entities.others.Payment;
import aoose_main.enums.AccessLevels;
import aoose_main.enums.Shifts;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static org.junit.jupiter.api.Assertions.*;

public class TestPromotionAndPayment {

    @Test
    public void testPromotionAndPaymentWorkflow() {
        // Connect to MongoDB
        MongoDatabase database = MongoClients.create("mongodb://localhost:27017").getDatabase("pharmacy");

        // Initialize database collections
        System.out.println("--- Initializing database collections ---");
        Patient.initializeDatabase(database);

        // Step 1: Admin creates promotion and promotion instance
        System.out.println("--- Step 1: Admin creates promotion and promotion instance ---");
        Admin admin = new Admin(1, "Admin Alpha", "admin.alpha@example.com", "adminPass", 1234567890L, 20000, AccessLevels.Full);

        // Create a unique promotion
        String promotionName = "Winter Sale " + System.currentTimeMillis();
        Promotion promotion = new Promotion(promotionName, "20% off on selected items", 20);
        admin.addPromotion(promotion, database);

        // Create a unique promotion instance
        long promotionInstanceId = System.currentTimeMillis();
        PromotionInstance promotionInstance = new PromotionInstance(
                promotionInstanceId,
                promotion,
                new Date(),
                new Date(System.currentTimeMillis() + 86400000L)
        );
        admin.addPromotionInstance(promotionInstance, database);

        // Step 2: Admin creates insurance and patient
        System.out.println("--- Step 2: Admin creates insurance and patient ---");
        Patient patient = new Patient(
                1,
                "John Doe",
                "john.doe@example.com",
                "password123",
                9876543210L,
                "123 Elm Street",
                "Male",
                30,
                "9876543211",
                "Cold symptoms",
                1500,
                null
        );
        admin.addPatient(patient, database);

        // Step 3: Create a cashier and add to the database
        System.out.println("--- Step 3: Create a cashier and add to the database ---");
        Cashier cashier = new Cashier(
                2,
                "Cashier Beta",
                "cashier.beta@example.com",
                "cashierPass",
                1122334455L,
                101,
                Shifts.Morning,
                10000
        );
        admin.addCashier(cashier, database);

        // Step 4: Cashier generates a bill
        System.out.println("--- Step 4: Cashier generates a bill ---");
        List<Item> items = new ArrayList<>();
        items.add(new Item(1, "Cough Syrup", "Medicine", 50.0, "For treating cough", 2));
        items.add(new Item(2, "Vitamin C", "Supplement", 30.0, "Boosts immunity", 1));
        Bill bill = cashier.generateBill(items, patient, database);

        // Verify bill is added to database
        MongoCollection<Document> billsCollection = database.getCollection("bills");
        Document billDoc = billsCollection.find(eq("id", bill.getId())).first();
        assertNotNull(billDoc, "Bill should be added to the database.");

        // Apply promotion
        System.out.println("--- Step 5: Cashier applies promotion to the bill ---");
        cashier.applyPromotion(bill, promotionInstance);

        // Apply loyalty discount
        System.out.println("--- Step 6: Cashier applies loyalty discount to the bill ---");
        cashier.applyLoyaltyDiscount(bill, patient, database);

        // Step 7: Execute payment
        System.out.println("--- Step 7: Cashier executes payment ---");
        Payment payment = cashier.createPayment(patient, bill, "Cash", "2024-12-20");

        // Pass the payment type explicitly to the strategy
        cashier.setPaymentStrategy(() -> {
            System.out.println("Processing payment via " + payment.getPaymentType());
        });
        cashier.executePayment(payment, database);

        // Final verification
        System.out.println("--- Final Verification ---");
        patient.displayPatientDetails();
        bill.getDetails();

        // Additional Assertions
        assertNotNull(database.getCollection("payments").find(eq("paymentId", payment.getPaymentId())).first(), "Payment should be added to the database.");
        Document updatedBill = database.getCollection("bills").find(eq("id", bill.getId())).first();
        assertNotNull(updatedBill, "Bill should be updated in the database.");
        assertEquals(payment.getPaymentId(), updatedBill.getString("paymentId"), "Payment ID should be correctly set in the bill.");
        assertEquals(bill.getTotalAfterDiscounts(), updatedBill.getDouble("amountPaid"), "Amount paid should match the total after discounts.");

        // Verify patient loyalty discount is updated
        Document patientDoc = database.getCollection("patients").find(eq("id", patient.getId())).first();
        assertEquals(500, patientDoc.getInteger("loyaltyDiscount"), "Loyalty discount should be updated.");
    }
}
