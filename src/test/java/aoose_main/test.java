package aoose_main;

import aoose_main.entities.abstraction.Item;
import aoose_main.entities.actors.InventoryClerk;
import aoose_main.entities.actors.Supplier;
import aoose_main.entities.others.Order;
import aoose_main.enums.OrderStatus;
import aoose_main.remotePattern.InventoryDTO;
import aoose_main.connection.MongoDBConnection;
import com.mongodb.client.MongoDatabase;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class test {

    private static MongoDatabase database;
    private static InventoryDAO inventoryDAO;

    @BeforeAll
    public static void setupDatabase() {
        // Connect to MongoDB and initialize the orders and inventory collections
        database = MongoDBConnection.connect("pharmacy");
        Order.initializeDatabase(database);
        inventoryDAO = new InventoryDAO(database);
    }

    @Test
    public void testSupplierInitiatesOrderAndInventoryClerkProcessesIt() {
        Supplier supplier = new Supplier("Health Supplies Co.", 1234567890L, "123 Supplier Lane");
        Item item1 = new Item(1, "Paracetamol", "Pain reliever", 2.5, "Tablet", 100);
        Item item2 = new Item(2, "Ibuprofen", "Anti-inflammatory", 3.0, "Capsule", 50);

        // Supplier creates an order
        Order order = supplier.registerOrder(5, List.of(item1, item2));

        // Inventory clerk approves the order
        InventoryClerk inventoryClerk = new InventoryClerk(1, "John Doe", "johndoe@example.com", "password123", 1234567890L, 5000, "Inventory");
        List<InventoryDTO> inventory = new ArrayList<>();
        inventoryClerk.approveOrder(5, inventory, inventoryDAO);

        // Verify inventory
        System.out.println("Verifying inventory...");
        InventoryDTO dbItem = inventoryDAO.getItemByName("Paracetamol");
        assertNotNull(dbItem, "Paracetamol should be in the inventory.");
        assertEquals(100, dbItem.getQuantity(), "Paracetamol quantity should be 100.");
    }
}
