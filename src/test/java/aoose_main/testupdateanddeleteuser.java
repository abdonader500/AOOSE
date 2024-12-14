package aoose_main;

import aoose_main.entities.actors.Admin;
import aoose_main.enums.AccessLevels;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.jupiter.api.Test;

import static com.mongodb.client.model.Filters.eq;
import static org.junit.jupiter.api.Assertions.*;

public class testupdateanddeleteuser {

    @Test
    public void test() {
        // Connect to MongoDB
        MongoDatabase database = MongoClients.create("mongodb://localhost:27017").getDatabase("pharmacy");

        // Initialize Admin
        Admin admin = new Admin(1, "Admin Alpha", "admin.alpha@example.com", "adminPass", 1234567890L, 20000, AccessLevels.Full);

        // Step 1: Remove the cashier
        int cashierId = 3;
        admin.removeUser(cashierId, database, "cashiers");

        // Verify the cashier was removed
        Document cashierDoc = database.getCollection("cashiers").find(eq("id", cashierId)).first();
        assertNull(cashierDoc, "Cashier with ID " + cashierId + " should be removed.");

        // Step 2: Update the patient's full name and loyalty discount
        int patientId = 2;
        Document updatedFields = new Document("fullName", "Jane Smith")
                .append("loyaltyDiscount", 700);
        admin.updateUser(patientId, database, "patients", updatedFields);

        // Verify the patient's details were updated
        Document updatedPatient = database.getCollection("patients").find(eq("id", patientId)).first();
        assertNotNull(updatedPatient, "Patient with ID " + patientId + " should exist in the database.");
        assertEquals("Jane Smith", updatedPatient.getString("fullName"), "Patient's full name should be updated to 'Jane Smith'.");
        assertEquals(700, updatedPatient.getInteger("loyaltyDiscount"), "Patient's loyalty discount should be updated to 700.");
    }
}