package aoose_main;

import aoose_main.entities.actors.Admin;
import aoose_main.entities.actors.Patient;
import aoose_main.entities.others.Inquiry;
import aoose_main.enums.AccessLevels;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.jupiter.api.*;

import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestInquiryWorkflow {

    private static MongoDatabase database;
    private static Patient patient;
    private static Admin admin;
    private static Inquiry inquiry;

    @BeforeAll
    public static void setupDatabase() {
        // Connect to MongoDB
        database = MongoClients.create("mongodb://localhost:27017").getDatabase("pharmacy");

        // Initialize patient and admin
        patient = new Patient(
                1,
                "John Doe",
                "john.doe@example.com",
                "password123",
                1234567890L,
                "123 Elm Street",
                "Male",
                35,
                "1234567891",
                "Flu Symptoms",
                100,
                null
        );

        admin = new Admin(
                2,
                "Admin Alpha",
                "admin.alpha@example.com",
                "adminPass",
                9876543210L,
                30000,
                AccessLevels.Full
        );
    }

    @Test
    @Order(1)
    public void testPatientCreatesInquiry() {
        System.out.println("--- Step 1: Patient creates an inquiry ---");

        // Patient writes an inquiry
        String inquiryMessage = "When will my prescription be ready?";
        patient.writeInquiry(database, inquiryMessage);

        // Verify the inquiry is saved in the database
        Document inquiryDoc = database.getCollection("inquiries").find(eq("patientId", patient.getId())).first();
        assertNotNull(inquiryDoc, "Inquiry should be saved in the database.");
        assertEquals(inquiryMessage, inquiryDoc.getString("message"), "Inquiry message should match.");

        // Load the inquiry for further tests
        inquiry = new Inquiry(
                inquiryDoc.getInteger("inquiryId"),
                inquiryDoc.getInteger("patientId"),
                inquiryDoc.getString("message")
        );
        inquiry.setTimestamp(inquiryDoc.getDate("timestamp"));
    }

    @Test
    @Order(2)
    public void testAdminViewsInquiries() {
        System.out.println("--- Step 2: Admin views inquiries ---");

        // Admin views all inquiries
        List<Inquiry> inquiries = Inquiry.loadFromDatabase(database);

        // Verify inquiries are loaded and displayed
        assertNotNull(inquiries, "Inquiries list should not be null.");
        assertEquals(1, inquiries.size(), "There should be one inquiry in the database.");

        Inquiry loadedInquiry = inquiries.get(0);
        assertEquals(patient.getId(), loadedInquiry.getPatientId(), "Patient ID in inquiry should match.");
        assertEquals(inquiry.getMessage(), loadedInquiry.getMessage(), "Inquiry message should match.");

        System.out.println("Admin is viewing inquiries:");
        for (Inquiry inquiry : inquiries) {
            inquiry.displayInquiryDetails();
        }
    }
}
