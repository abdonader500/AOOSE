package aoose_main;

import aoose_main.connection.MongoDBConnection;
import aoose_main.entities.others.Insurance;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.jupiter.api.*;

import static com.mongodb.client.model.Filters.eq;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PatientTest {

    private MongoDatabase database;
    private MongoCollection<Document> collection;
    private Patient patient;

    @BeforeAll
    public void setupDatabase() {
        // Initialize MongoDB connection and collection
        database = MongoDBConnection.connect("pharmacy");
        collection = database.getCollection("patients");
    }

    @BeforeEach
    public void setupTestData() {
        // Create a test Patient instance with associated Insurance
        Insurance insurance = new Insurance(1001, 2001, null, 80, 1);
        patient = new Patient(1, "John Doe", "john.doe@example.com", "password123", 1234567890L,
                "1234 Elm Street", "Male", 30, "1234567890", "Headache",
                "10%", insurance);

        // Insert initial test data into the database
        Document insuranceDoc = new Document("insuranceProviderID", 1001)
                .append("insuranceID", 2001)
                .append("insurancePercentage", 80)
                .append("patientID", 1);
        Document patientDoc = new Document("id", 1)
                .append("fullName", "John Doe")
                .append("email", "john.doe@example.com")
                .append("phoneNumber", 1234567890L)
                .append("address", "1234 Elm Street")
                .append("gender", "Male")
                .append("age", 30)
                .append("emergencyContact", "1234567890")
                .append("issue", "Headache")
                .append("loyaltyDiscount", "10%")
                .append("insurance", insuranceDoc);
        collection.insertOne(patientDoc);
    }

    @AfterEach
    public void cleanupTestData() {
        // Clear test data from the collection
        collection.deleteMany(new Document());
    }

    @Test
    public void testSaveToDatabase() {
        // Modify and save the patient's data
        patient.setIssue("Updated Issue");
        patient.saveToDatabase(database);

        // Verify that the patient data is updated in the database
        Document updatedPatient = collection.find(eq("id", 1)).first();
        assertNotNull(updatedPatient);
        assertEquals("Updated Issue", updatedPatient.getString("issue"));
    }

    @Test
    public void testLoadFromDatabase() {
        Patient loadedPatient = Patient.loadFromDatabase(1, database);

        // Verify that the loaded patient data is correct
        assertNotNull(loadedPatient);
        assertEquals("John Doe", loadedPatient.getFullName());
        assertEquals("Headache", loadedPatient.getIssue());
    }

    @Test
    public void testDeleteFromDatabase() {
        patient.deleteFromDatabase(database);

        // Verify that the patient data is removed from the database
        long count = collection.countDocuments(eq("id", 1));
        assertEquals(0, count);
    }

    @AfterAll
    public void teardownDatabase() {
        // Cleanup resources
        database = null;
        collection = null;
    }
}
