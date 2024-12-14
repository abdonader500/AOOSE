package aoose_main;

import aoose_main.entities.actors.*;
import aoose_main.entities.others.Insurance;
import aoose_main.enums.AccessLevels;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class testInsuranceCycle {

    @Test
    public void testInsuranceCycle() {
        // Connect to MongoDB database
        MongoDatabase database = MongoClients.create("mongodb://localhost:27017").getDatabase("pharmacy");

        // Initialize collections for InsuranceProvider and Patient
        InsuranceProvider.initializeDatabase(database);
        Patient.initializeDatabase(database);

        // Step 1: Admin creates an InsuranceProvider
        Admin admin = new Admin(
                200,
                "Admin Alpha",
                "alpha.admin@pharmacy.com",
                "secureAdminPass",
                1122334455L,
                15000,
                AccessLevels.Full
        );

        InsuranceProvider insuranceProvider = new InsuranceProvider(
                300,
                "Elite Health Coverage",
                "contact@elitehealth.com",
                "strongElitePassword",
                2233445566L,
                "Elite health coverage terms apply",
                "Elite Health Inc.",
                "Covers up to 90% of medical expenses"
        );

        // Add InsuranceProvider to the database
        admin.addInsuranceProvider(insuranceProvider, database);

        // Verify that the InsuranceProvider was added successfully
        InsuranceProvider loadedProvider = InsuranceProvider.loadFromDatabase(300);
        assertNotNull(loadedProvider, "InsuranceProvider should be successfully loaded from the database");
        assertEquals("Elite Health Coverage", loadedProvider.getFullName(), "InsuranceProvider name should match");

        // Step 2: InsuranceProvider creates an insurance policy
        int insuranceID = 501;
        int insuranceProviderID = insuranceProvider.getId();
        int insurancePercentage = 90; // Coverage percentage
        insuranceProvider.addInsurancePolicy(insuranceID, insuranceProviderID, insurancePercentage);

        // Verify that the insurance policy was added successfully
        List<Document> policies = insuranceProvider.viewAllInsurancePolicies();
        assertFalse(policies.isEmpty(), "Insurance policies should be present in the database");
        assertEquals(501, policies.get(0).getInteger("insuranceID"), "Insurance ID should match");

        // Step 3: Admin creates a Patient and associates the newly created insurance
        Insurance patientInsurance = new Insurance(insuranceProviderID, insuranceID, insurancePercentage, 601); // Patient ID: 601
        Patient patient = new Patient(
                601,
                "Alice Green",
                "alice.green@example.com",
                "securePatientPass",
                5566778899L,
                "789 Pine Avenue",
                "Female",
                29,
                "4455667788",
                "Routine check-up",
                20,
                patientInsurance
        );

        // Add Patient to the database
        admin.addPatient(patient, database);

        // Step 4: Verify that the Patient was added successfully
        Patient loadedPatient = (Patient) Patient.loadFromDatabase(601);
        assertNotNull(loadedPatient, "Patient should be successfully loaded from the database");
        assertEquals("Alice Green", loadedPatient.getFullName(), "Patient name should match");
        assertEquals(601, loadedPatient.getId(), "Patient ID should match");

        // Step 5: Verify the insurance association
        Insurance loadedInsurance = loadedPatient.getInsurance();
        assertNotNull(loadedInsurance, "Insurance should be associated with the patient");
        assertEquals(501, loadedInsurance.getInsuranceID(), "Insurance ID should match");
        assertEquals(90, loadedInsurance.getInsurancePercentage(), "Insurance percentage should match");
    }
}
