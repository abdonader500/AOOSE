package aoose_main;

import aoose_main.entities.abstraction.Item;
import aoose_main.entities.abstraction.Promotion;
import aoose_main.entities.abstraction.PromotionInstance;
import aoose_main.entities.actors.Admin;
import aoose_main.entities.actors.Cashier;
import aoose_main.entities.actors.InsuranceProvider;
import aoose_main.entities.actors.Patient;
import aoose_main.entities.others.Bill;
import aoose_main.entities.others.MakePayment;
import aoose_main.entities.others.Payment;
import aoose_main.entities.others.Insurance;
import aoose_main.entities.actors.InsuranceProvider;
import aoose_main.enums.AccessLevels;
import aoose_main.enums.Shifts;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static org.junit.jupiter.api.Assertions.*;

public class testpaymentCycle {

    @Test
    public void test() {
        // Connect to MongoDB
        MongoDatabase database = MongoClients.create("mongodb://localhost:27017").getDatabase("pharmacy");

        // Initialize collections
        InsuranceProvider.initializeDatabase(database);
        Patient.initializeDatabase(database);

        // Step 1: Admin creates insurance provider
        System.out.println("--- Step 1: Admin creates insurance provider ---");
        Admin admin = new Admin(1, "Admin Alpha", "admin.alpha@example.com", "adminPass", 1234567890L, 20000, AccessLevels.Full);
        InsuranceProvider insuranceProvider = new InsuranceProvider(
                1,
                "HealthFirst Provider",
                "contact@healthfirst.com",
                "providerPass",
                1234567891L,
                "Standard T&Cs",
                "HealthFirst",
                "Comprehensive coverage for individuals and families"
        );
        admin.addInsuranceProvider(insuranceProvider, database);

        // Verify insurance provider is added to the database
        Document providerDoc = database.getCollection("insurance_providers").find(eq("id", insuranceProvider.getId())).first();
        assertNotNull(providerDoc, "Insurance provider should be added to the database.");

        // Step 2: Insurance provider creates insurance
        System.out.println("--- Step 2: Insurance provider creates insurance ---");
        List<Integer> tierIDs = Arrays.asList(101, 102); // Example tiers
        insuranceProvider.addInsurancePolicy(1, insuranceProvider.getId(), 20);

        // Verify insurance policy is added to the database
        Document insuranceDoc = database.getCollection("insurances").find(eq("insuranceID", 1)).first();
        assertNotNull(insuranceDoc, "Insurance should be added to the database.");

        // Step 3: Admin creates promotion
        System.out.println("--- Step 3: Admin creates promotion ---");
        String promotionName = "Health Discount " + System.currentTimeMillis();
        Promotion promotion = new Promotion(promotionName, "15% discount on health items", 15);
        admin.addPromotion(promotion, database);

        // Step 4: Admin creates promotion instance
        System.out.println("--- Step 4: Admin creates promotion instance ---");
        long promotionInstanceId = System.currentTimeMillis();
        PromotionInstance promotionInstance = new PromotionInstance(
                promotionInstanceId,
                promotion,
                new Date(),
                new Date(System.currentTimeMillis() + 86400000L)
        );
        admin.addPromotionInstance(promotionInstance, database);

        // Step 5: Admin creates patient with insurance
        System.out.println("--- Step 5: Admin creates patient with insurance ---");
        Patient patient = new Patient(
                2,
                "Jane Doe",
                "jane.doe@example.com",
                "password123",
                9876543210L,
                "456 Maple Street",
                "Female",
                30,
                "9876543212",
                "Routine checkup",
                400,
                Insurance.fromDocument(insuranceDoc) // Construct insurance object from database document
        );
        admin.addPatient(patient, database);

        // Step 6: Admin creates cashier
        System.out.println("--- Step 6: Admin creates cashier ---");
        Cashier cashier = new Cashier(
                3,
                "Cashier Charlie",
                "cashier.charlie@example.com",
                "cashierPass",
                1231231234L,
                202,
                Shifts.Night,
                12000
        );
        admin.addCashier(cashier, database);

        // Step 7: Cashier creates a bill and adds the promotion instance
        System.out.println("--- Step 7: Cashier creates a bill and adds the promotion instance ---");
        List<Item> items = new ArrayList<>();
        items.add(new Item(1, "Blood Pressure Monitor", "Device", 80.0, "For home use", 1));
        items.add(new Item(2, "Multivitamins", "Supplement", 50.0, "Daily vitamins", 2));
        Bill bill = cashier.generateBill(items, patient, database);
        cashier.addInsuranceDiscount(bill,patient,database);
        cashier.applyPromotion(bill, promotionInstance);
        cashier.applyLoyaltyDiscount(bill, patient, database);

        // Verify bill in database
        Document billDoc = database.getCollection("bills").find(eq("id", bill.getId())).first();
        assertNotNull(billDoc, "Bill should be added to the database.");

        // Step 8: Cashier processes payment
        System.out.println("--- Step 8: Cashier processes payment ---");
        Payment payment = cashier.createPayment(patient, bill, "Credit Card", "2024-12-31");

// Set payment strategy explicitly
        cashier.setPaymentStrategy(() -> {
            System.out.println("Processing payment via " + payment.getPaymentType());
        });
        cashier.executePayment(payment, database);

        // Final verification
        System.out.println("--- Final Verification ---");
        patient.displayPatientDetails();
        bill.getDetails();

        // Assertions
        assertNotNull(database.getCollection("payments").find(eq("paymentId", payment.getPaymentId())).first(), "Payment should be added to the database.");
        Document updatedBill = database.getCollection("bills").find(eq("id", bill.getId())).first();
        assertNotNull(updatedBill, "Bill should be updated in the database.");
        assertEquals(payment.getPaymentId(), updatedBill.getString("paymentId"), "Payment ID should be correctly set in the bill.");
        assertEquals(bill.getTotalAfterDiscounts(), updatedBill.getDouble("amountPaid"), "Amount paid should match the total after discounts.");
    }
}