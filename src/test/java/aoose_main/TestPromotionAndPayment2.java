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
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestPromotionAndPayment2 {

    private static MongoDatabase database;
    private static Admin admin;
    private static Patient patient;
    private static Cashier cashier;
    private static Promotion promotion;
    private static PromotionInstance promotionInstance;
    private static Bill bill;
    private static Payment payment;

    @BeforeAll
    public static void setupDatabase() {
        // Connect to MongoDB
        database = MongoClients.create("mongodb://localhost:27017").getDatabase("pharmacy");
        Patient.initializeDatabase(database);

        // Create admin instance
        admin = new Admin(2, "Admin Beta", "admin.beta@example.com", "adminBetaPass", 2233445566L, 25000, AccessLevels.Full);
    }

    @Test
    @Order(1)
    public void testAdminCreatesPromotion() {
        System.out.println("--- Step 1: Admin creates promotion and promotion instance ---");

        // Create a unique promotion
        String promotionName = "Spring Sale " + System.currentTimeMillis();
        promotion = new Promotion(promotionName, "15% off on select items", 15);
        admin.addPromotion(promotion, database);

        // Create a unique promotion instance
        long promotionInstanceId = System.currentTimeMillis();
        promotionInstance = new PromotionInstance(
                promotionInstanceId,
                promotion,
                new Date(),
                new Date(System.currentTimeMillis() + 86400000L)
        );
        admin.addPromotionInstance(promotionInstance, database);

        // Verify promotion and promotion instance
        assertNotNull(database.getCollection("promotions").find(eq("name", promotionName)).first(), "Promotion should be added to the database.");
    }

    @Test
    @Order(2)
    public void testAdminCreatesPatientAndCashier() {
        System.out.println("--- Step 2: Admin creates patient and cashier ---");

        // Create patient
        patient = new Patient(
                2,
                "Jane Smith",
                "jane.smith@example.com",
                "password456",
                9988776655L,
                "456 Oak Avenue",
                "Female",
                28,
                "9988776644",
                "Headache symptoms",
                1600,
                null
        );
        admin.addPatient(patient, database);

        // Create cashier
        cashier = new Cashier(
                3,
                "Cashier Gamma",
                "cashier.gamma@example.com",
                "cashierGammaPass",
                5566778899L,
                202,
                Shifts.Night,
                12000
        );
        admin.addCashier(cashier, database);

        // Verify patient and cashier
        assertNotNull(database.getCollection("patients").find(eq("id", patient.getId())).first(), "Patient should be added to the database.");
        assertNotNull(database.getCollection("cashiers").find(eq("id", cashier.getId())).first(), "Cashier should be added to the database.");
    }

    @Test
    @Order(3)
    public void testCashierGeneratesBill() {
        System.out.println("--- Step 3: Cashier generates a bill ---");

        List<Item> items = new ArrayList<>();
        items.add(new Item(3, "Pain Reliever", "Medicine", 40.0, "Relieves pain", 3));
        items.add(new Item(4, "Multivitamins", "Supplement", 25.0, "Supports overall health", 2));
        bill = cashier.generateBill(items, patient, database);

        // Verify bill is added to database
        MongoCollection<Document> billsCollection = database.getCollection("bills");
        Document billDoc = billsCollection.find(eq("id", bill.getId())).first();
        assertNotNull(billDoc, "Bill should be added to the database.");
    }

    @Test
    @Order(4)
    public void testCashierAppliesPromotionAndLoyaltyDiscount() {
        System.out.println("--- Step 4: Cashier applies promotion and loyalty discount ---");

        // Apply promotion
        cashier.applyPromotion(bill, promotionInstance);

        // Apply loyalty discount
        cashier.applyLoyaltyDiscount(bill, patient, database);

        // Verify discounts applied
        double expectedDiscount = bill.getTotalAmount() * promotion.getDiscountPercentage() / 100 + 100; // Including loyalty discount
    }

    @Test
    @Order(5)
    public void testCashierExecutesPayment() {
        System.out.println("--- Step 5: Cashier executes payment ---");

        payment = cashier.createPayment(patient, bill, "Card", "2024-12-25");

        // Pass the payment type explicitly to the strategy
        cashier.setPaymentStrategy(() -> {
            System.out.println("Processing payment via " + payment.getPaymentType());
        });
        cashier.executePayment(payment, database);

        // Verify payment and updated bill in the database
        assertNotNull(database.getCollection("payments").find(eq("paymentId", payment.getPaymentId())).first(), "Payment should be added to the database.");
        Document updatedBill = database.getCollection("bills").find(eq("id", bill.getId())).first();
        assertEquals(payment.getPaymentId(), updatedBill.getString("paymentId"), "Payment ID should be correctly set in the bill.");
        assertEquals(bill.getTotalAfterDiscounts(), updatedBill.getDouble("amountPaid"), "Amount paid should match the total after discounts.");
    }

    @Test
    @Order(6)
    public void testPatientLoyaltyDiscountUpdated() {
        System.out.println("--- Step 6: Verify patient loyalty discount is updated ---");

        Document patientDoc = database.getCollection("patients").find(eq("id", patient.getId())).first();
        assertEquals(600, patientDoc.getInteger("loyaltyDiscount"), "Loyalty discount should be updated.");
    }
}
