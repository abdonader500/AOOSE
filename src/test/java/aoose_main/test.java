package aoose_main;

import aoose_main.entities.abstraction.Item;
import aoose_main.entities.actors.InventoryClerk;
import aoose_main.entities.actors.Supplier;
import aoose_main.entities.others.Order;
import aoose_main.enums.OrderStatus;
import aoose_main.connection.MongoDBConnection;
import aoose_main.remotePattern.InventoryDTO;
import com.mongodb.client.MongoDatabase;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class test {

    private static MongoDatabase database;

    @BeforeAll
    public static void setupDatabase() {
        // Connect to MongoDB and initialize the orders collection
        database = MongoDBConnection.connect("pharmacy");
        Order.initializeDatabase(database);
    }

    @Test
    public void testSupplierCreatesOrderAndInventoryClerkProcessesIt() {
        Supplier supplier = new Supplier("Health Supplies Co.", 1234567890L, "123 Supplier Lane");
        Item item1 = new Item(1, "Paracetamol", "Pain reliever", 2.5, "Tablet", 100);
        Item item2 = new Item(2, "Ibuprofen", "Anti-inflammatory", 3.0, "Capsule", 50);

        // Supplier registers an order
        Order order = supplier.registerOrder(1, List.of(item1, item2));

        // Set up InventoryClerk and inventory list
        InventoryClerk inventoryClerk = new InventoryClerk(1, "John Doe", "johndoe@example.com", "password123", 1234567890L, 5000, "Inventory");
        List<InventoryDTO> inventory = new ArrayList<>();
        InventoryDAO inventoryDAO = new InventoryDAO(database);

        // Approve order and add items to inventory
        inventoryClerk.approveOrder(1, inventory,inventoryDAO);

        // Verify inventory contains the approved items
        for (InventoryDTO inventoryItem : inventory) {
            String itemName = inventoryItem.getItemName();
            double price = inventoryItem.getPrice();
            int quantity = inventoryItem.getQuantity(); // Already cast to int in DTO

            System.out.println("Inventory Item: " + itemName + ", Quantity: " + quantity + ", Price: " + price);
        }

        // Assert inventory size and content
        assertEquals(2, inventory.size(), "Inventory should have 2 items.");

        // Verify Paracetamol
        InventoryDTO dbItem = inventoryDAO.getItemByName("Paracetamol");
        assertNotNull(dbItem, "Paracetamol should be in the inventory.");
        assertEquals("Paracetamol", dbItem.getItemName(), "Paracetamol name should match.");
        assertEquals(100, dbItem.getQuantity(), "Paracetamol quantity should be 100.");
        assertEquals(2.5, dbItem.getPrice(), "Paracetamol price should be 2.5.");

        // Verify Ibuprofen
        InventoryDTO dbItem2 = inventoryDAO.getItemByName("Ibuprofen");
        assertNotNull(dbItem2, "Ibuprofen should be in the inventory.");
        assertEquals("Ibuprofen", dbItem2.getItemName(), "Ibuprofen name should match.");
        assertEquals(50, dbItem2.getQuantity(), "Ibuprofen quantity should be 50.");
        assertEquals(3.0, dbItem2.getPrice(), "Ibuprofen price should be 3.0.");
    }
}
