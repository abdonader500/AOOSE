package aoose_main;

import aoose_main.entities.actors.*;
import aoose_main.entities.others.Insurance;
import aoose_main.enums.AccessLevels;
import aoose_main.enums.Shifts;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class UserCreationTest {

    @Test
    public void testCreateUsers() {
        // Connect to the MongoDB database
        MongoDatabase database = MongoClients.create("mongodb://localhost:27017").getDatabase("pharmacy");

        // Initialize Admin to manage user creation
        Admin admin = new Admin(1, "Super Admin", "admin@pharmacy.com", "adminPass", 1234567890L, 10000, AccessLevels.Full);

        // Create two Patients with Insurance
        List<Insurance> insuranceTier1 = new ArrayList<>(); // Example for adding insurance tiers
        Insurance insurance1 = new Insurance(101, 201, insuranceTier1, 80, 2); // Adjust parameters: insuranceProviderID, insuranceID, tier, percentage, patientID
        Patient patient1 = new Patient(2, "John Doe", "john.doe@example.com", "password123", 1111111111L,
                "123 Elm Street", "Male", 30, "1122334455", "Regular check-up", 5, insurance1);

        List<Insurance> insuranceTier2 = new ArrayList<>(); // Example for adding insurance tiers
        Insurance insurance2 = new Insurance(102, 202, insuranceTier2, 90, 3); // Adjust parameters: insuranceProviderID, insuranceID, tier, percentage, patientID
        Patient patient2 = new Patient(3, "Jane Smith", "jane.smith@example.com", "password456", 2222222222L,
                "456 Oak Avenue", "Female", 28, "2233445566", "Allergy symptoms", 10, insurance2);


        admin.addPatient(patient1, database);
        admin.addPatient(patient2, database);

        // Create two Pharmacists
        Pharmacist pharmacist1 = new Pharmacist(4, "Alice Brown", "alice.brown@example.com", "password789", 3333333333L,
                "PH12345", Shifts.Morning, 5000, "Oncology");

        Pharmacist pharmacist2 = new Pharmacist(5, "Bob White", "bob.white@example.com", "password987", 4444444444L,
                "PH67890", Shifts.Night, 6000, "Pediatrics");

        admin.addPharmacist(pharmacist1, database);
        admin.addPharmacist(pharmacist2, database);

        // Create two Inventory Clerks
        InventoryClerk clerk1 = new InventoryClerk(6, "Chris Green", "chris.green@example.com", "password654",
                5555555555L, 4000, "Warehouse A");

        InventoryClerk clerk2 = new InventoryClerk(7, "Diana Blue", "diana.blue@example.com", "password321",
                6666666666L, 4500, "Warehouse B");

        admin.addInventoryClerk(clerk1, database);
        admin.addInventoryClerk(clerk2, database);

        // Create two Suppliers
        Supplier supplier1 = new Supplier("Global Med Supplies", 7777777777L, "789 Birch Road");
        Supplier supplier2 = new Supplier("HealthTech", 8888888888L, "101 Maple Street");

        admin.addSupplier(supplier1, database);
        admin.addSupplier(supplier2, database);

        // Create two Admins
        Admin admin1 = new Admin(8, "Eve Admin", "eve.admin@example.com", "password112", 9999999999L,
                12000, AccessLevels.Limited);

        Admin admin2 = new Admin(9, "Frank Admin", "frank.admin@example.com", "password113", 1010101010L,
                13000, AccessLevels.ViewOnly);

        admin.addAdmin(admin1, database);
        admin.addAdmin(admin2, database);

        // Create two Cashiers
        Cashier cashier1 = new Cashier(10, "Grace Yellow", "grace.yellow@example.com", "password221", 1212121212L,
                9876543210L, Shifts.Morning, 3000);

        Cashier cashier2 = new Cashier(11, "Hank Orange", "hank.orange@example.com", "password332", 1313131313L,
                1234567890L, Shifts.Night, 3200);

        admin.addCashier(cashier1, database);
        admin.addCashier(cashier2, database);

        // Print confirmation
        System.out.println("All user types created and added to the database successfully.");
    }
}
