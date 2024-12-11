package aoose_main;

import aoose_main.connection.MongoDBConnection;
import aoose_main.entities.actors.InsuranceProvider;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.jupiter.api.*;

import static com.mongodb.client.model.Filters.eq;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class InsuranceProviderTest {

    private MongoDatabase database;
    private MongoCollection<Document> collection;
    private InsuranceProvider insuranceProvider;

    @BeforeAll
    public void setupDatabase() {
        // Initialize MongoDB connection and collection
        database = MongoDBConnection.connect("pharmacy");
        collection = database.getCollection("insuranceForms");
        InsuranceProvider.initializeDatabase(database);
    }

    @BeforeEach
    public void setupTestData() {
        // Create a test InsuranceProvider instance
        insuranceProvider = new InsuranceProvider(1, "John Doe", "john.doe@example.com", "password123", 1234567890L, "Terms and Conditions", "Company ABC", "Full Coverage");

        // Add test insurance forms to the database
        Document form1 = new Document("insuranceId", "INS123")
                .append("formDetails", "Form for customer A")
                .append("status", "pending");
        Document form2 = new Document("insuranceId", "INS124")
                .append("formDetails", "Form for customer B")
                .append("status", "pending");
        collection.insertOne(form1);
        collection.insertOne(form2);
    }

    @AfterEach
    public void cleanupTestData() {
        // Clear test data from the collection
        collection.deleteMany(new Document());
    }

    @Test
    public void testCheckInsuranceForm() {
        insuranceProvider.checkInsuranceForm("Form for customer A");

        // Verify that the form exists in the database
        Document form = collection.find(eq("formDetails", "Form for customer A")).first();
        assertNotNull(form);
        assertEquals("Form for customer A", form.getString("formDetails"));
    }

    @Test
    public void testApproveInsurance() {
        insuranceProvider.approveInsurance("INS123");

        // Verify that the status is updated to "approved"
        Document form = collection.find(eq("insuranceId", "INS123")).first();
        assertNotNull(form);
        assertEquals("approved", form.getString("status"));
    }

    @Test
    public void testRejectInsurance() {
        insuranceProvider.rejectInsurance("INS124");

        // Verify that the status is updated to "rejected"
        Document form = collection.find(eq("insuranceId", "INS124")).first();
        assertNotNull(form);
        assertEquals("rejected", form.getString("status"));
    }

    @AfterAll
    public void teardownDatabase() {
        // Cleanup resources
        database = null;
        collection = null;
    }
}
