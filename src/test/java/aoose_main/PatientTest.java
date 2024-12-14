package aoose_main;

import aoose_main.entities.actors.Patient;
import aoose_main.entities.others.Insurance;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class PatientTest {

    private MongoDatabase database;
    private MongoCollection<Document> patientCollection;

    @Before
    public void setUp() {
        // Initialize the database connection
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017"); // Adjust URI if necessary
        database = mongoClient.getDatabase("aoose_pharmacy_system_test");
        patientCollection = database.getCollection("patients");

        // Ensure the collection is empty before starting the test
        patientCollection.deleteMany(new Document());
    }

    @After
    public void tearDown() {
        // Clean up the collection after tests
        patientCollection.deleteMany(new Document());
    }

    @Test
    public void testAddThreePatients() {
        // Create three patients
        Patient patient1 = new Patient(
                1,
                "Alice Johnson",
                "alice.johnson@example.com",
                "password123",
                1234567890L,
                "123 Main St",
                "Female",
                25,
                "9876543210",
                "Headache",
                5,
                null // No insurance for now
        );

        Patient patient2 = new Patient(
                2,
                "Bob Smith",
                "bob.smith@example.com",
                "password456",
                2234567890L,
                "456 Elm St",
                "Male",
                30,
                "8765432109",
                "Flu",
                10,
                null // No insurance for now
        );

        Patient patient3 = new Patient(
                3,
                "Charlie Brown",
                "charlie.brown@example.com",
                "password789",
                3234567890L,
                "789 Oak St",
                "Male",
                35,
                "7654321098",
                "Back Pain",
                15,
                null // No insurance for now
        );

        // Save the patients to the database
        savePatientToDatabase(patient1);
        savePatientToDatabase(patient2);
        savePatientToDatabase(patient3);

        // Verify that the patients are in the database
        assertNotNull(patientCollection.find(new Document("id", 1)).first());
        assertNotNull(patientCollection.find(new Document("id", 2)).first());
        assertNotNull(patientCollection.find(new Document("id", 3)).first());

        System.out.println("Three patients successfully added to the database.");
    }

    private void savePatientToDatabase(Patient patient) {
        Document doc = new Document("id", patient.getId())
                .append("fullName", patient.getFullName())
                .append("email", patient.getEmail())
                .append("password", patient.getPassword()) // Consider hashing passwords in real applications
                .append("phoneNumber", patient.getPhoneNumber())
                .append("address", patient.getAddress())
                .append("gender", patient.getGender())
                .append("age", patient.getAge())
                .append("emergencyContact", patient.getEmergencyContact())
                .append("issue", patient.getIssue())
                .append("loyaltyDiscount", patient.getLoyaltyDiscount())
                .append("insuranceID", patient.getInsuranceID());

        patientCollection.insertOne(doc);
    }
}
