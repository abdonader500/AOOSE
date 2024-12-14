package aoose_main;

import aoose_main.entities.actors.Admin;
import aoose_main.entities.actors.Patient;
import aoose_main.entities.others.Inquiry;
import aoose_main.enums.AccessLevels;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class inquiryTest {

    @Test
    public void testAddAndViewInquiries() {
        // Connect to MongoDB
        MongoDatabase database = MongoClients.create("mongodb://localhost:27017").getDatabase("pharmacy");

        // Create sample patients
        Patient patient1 = new Patient(1, "John Doe", "john.doe@example.com", "password123", 9876543210L,
                "123 Elm Street", "Male", 30, "9876543211", "Issue 1", 1500, null);

        Patient patient2 = new Patient(2, "Jane Smith", "jane.smith@example.com", "password456", 9876543211L,
                "456 Oak Avenue", "Female", 28, "9876543212", "Issue 2", 1600, null);

        Patient patient3 = new Patient(3, "Robert Johnson", "robert.johnson@example.com", "password789", 9876543212L,
                "789 Pine Road", "Male", 35, "9876543213", "Issue 3", 1700, null);

        // Add inquiries by patients
        Inquiry inquiry1 = new Inquiry(1, patient1.getId(), "How can I use my insurance for this bill?");
        inquiry1.saveToDatabase(database);

        Inquiry inquiry2 = new Inquiry(2, patient2.getId(), "What are the details of the current promotions?");
        inquiry2.saveToDatabase(database);

        Inquiry inquiry3 = new Inquiry(3, patient3.getId(), "How can I apply for a loyalty discount?");
        inquiry3.saveToDatabase(database);

        // Create an admin
        Admin admin = new Admin(1, "Admin Alpha", "admin.alpha@example.com", "adminPass", 1234567890L, 20000, AccessLevels.Full);

        // Admin views all inquiries
        System.out.println("--- Viewing All Inquiries ---");
        admin.viewAllInquiries(database);

        // Verify inquiries were saved to the database
        assertEquals(3, Inquiry.loadFromDatabase(database).size(), "There should be 3 inquiries in the database.");
    }
}
