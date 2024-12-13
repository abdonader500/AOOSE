package aoose_main;

import aoose_main.entities.abstraction.Promotion;
import aoose_main.entities.abstraction.PromotionInstance;
import aoose_main.entities.actors.Admin;
import aoose_main.enums.AccessLevels;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class testAdminPromotion {

    @Test
    public void testCreatePromotion() {
        // Connect to MongoDB database
        MongoDatabase database = MongoClients.create("mongodb://localhost:27017").getDatabase("pharmacy");

        // Initialize Admin
        Admin admin = new Admin(
                1,
                "Admin Alpha",
                "admin.alpha@pharmacy.com",
                "securePass",
                1122334455L,
                20000,
                AccessLevels.Full
        );

        // Promotion details
        String promotionName = "Winter Sale";
        String promotionDescription = "20% off on all products!";
        int discountPercentage = 20;

        // Create and add Promotion
        Promotion promotion = new Promotion(
                promotionName,
                promotionDescription,
                discountPercentage
        );
        admin.addPromotion(promotion, database);

        // Verify Promotion creation
        Document promotionDoc = database.getCollection("promotions").find(new Document("name", promotionName)).first();
        assertNotNull(promotionDoc, "Promotion should be stored in the database.");
        assertEquals(promotionName, promotionDoc.getString("name"), "Promotion name should match.");
        assertEquals(discountPercentage, promotionDoc.getInteger("discountPercentage"), "Discount percentage should match.");
    }

    @Test
    public void testCreatePromotionInstance() {
        // Connect to MongoDB database
        MongoDatabase database = MongoClients.create("mongodb://localhost:27017").getDatabase("pharmacy");

        // Initialize Admin
        Admin admin = new Admin(
                1,
                "Admin Alpha",
                "admin.alpha@pharmacy.com",
                "securePass",
                1122334455L,
                20000,
                AccessLevels.Full
        );

        // Promotion details
        String promotionName = "Holiday Sale";
        String promotionDescription = "30% off on select items!";
        int discountPercentage = 30;
        Date startDate = new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000); // Start date: tomorrow
        Date endDate = new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000); // End date: one week from now

        // Create Promotion
        Promotion promotion = new Promotion(
                promotionName,
                promotionDescription,
                discountPercentage
        );
        admin.addPromotion(promotion, database);

        // Create PromotionInstance
        PromotionInstance promotionInstance = new PromotionInstance(
                1001,
                promotion,
                startDate,
                endDate
        );
        admin.addPromotionInstance(promotionInstance, database);

        // Verify PromotionInstance creation
        Document promotionInstanceDoc = database.getCollection("promotion_instances").find(new Document("instanceID", 1001)).first();
        assertNotNull(promotionInstanceDoc, "Promotion instance should be stored in the database.");
        assertEquals(1001, promotionInstanceDoc.getLong("instanceID"), "Instance ID should match.");
        assertEquals(promotionName, promotionInstanceDoc.getString("promotionName"), "Promotion name should match.");
    }
}
