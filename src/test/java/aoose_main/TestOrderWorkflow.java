package aoose_main;

import aoose_main.entities.abstraction.Item;
import aoose_main.entities.actors.InventoryClerk;
import aoose_main.entities.actors.Supplier;
import aoose_main.entities.others.Order;
import aoose_main.enums.OrderStatus;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestOrderWorkflow {

    private MongoDatabase database;
    private Supplier supplier;
    private InventoryClerk inventoryClerk;
    private Order order;

    @BeforeEach
    public void setup() {
        // Connect to MongoDB
        database = MongoClients.create("mongodb://localhost:27017").getDatabase("pharmacy");

        // Initialize the orders collection
        Order.initializeDatabase(database);

        // Create instances of Supplier and InventoryClerk
        supplier = new Supplier(
                1,
                "Supplier Alpha",
                "supplier.alpha@example.com",
                "supplierPass",
                1234567890L,
                "Pharma Supplies Co.",
                "123 Supplier St."
        );

        inventoryClerk = new InventoryClerk(
                2,
                "Inventory Clerk Beta",
                "clerk.beta@example.com",
                "clerkPass",
                9876543210L,
                10000,
                "Pharmacy Inventory"
        );
    }

    @Test
    public void testSupplierCreatesOrder() {
        System.out.println("--- Step 1: Supplier creates an order ---");

        // Create a list of items
        List<Item> items = new ArrayList<>();
        items.add(new Item(1, "Paracetamol", "Medicine", 10.0, "For pain relief", 100));
        items.add(new Item(2, "Ibuprofen", "Medicine", 15.0, "Anti-inflammatory", 50));

        // Supplier registers the order
        order = supplier.registerOrder(1, items);

        // Verify order creation
        assertNotNull(order, "Order should not be null.");
        assertEquals(OrderStatus.Pending, order.getStatus(), "Order status should be Pending after creation.");

        // Verify order in the database
        Document orderDoc = database.getCollection("orders").find(eq("orderID", order.getOrderID())).first();
        assertNotNull(orderDoc, "Order should be saved in the database.");
        assertEquals(OrderStatus.Pending.name(), orderDoc.getString("status"), "Order status in the database should be Pending.");
    }

    @Test
    public void testInventoryClerkReceivesOrder() {
        System.out.println("--- Step 2: Inventory Clerk receives the order ---");

        // Create a list of items to simulate the same order as in testSupplierCreatesOrder
        List<Item> items = new ArrayList<>();
        items.add(new Item(1, "Paracetamol", "Medicine", 10.0, "For pain relief", 100));
        items.add(new Item(2, "Ibuprofen", "Medicine", 15.0, "Anti-inflammatory", 50));

        // Supplier registers the order again (mocking the state from the first test)
        order = supplier.registerOrder(1, items);

        // Inventory Clerk approves the order
        inventoryClerk.approveOrder(order.getOrderID(), new ArrayList<>(), new InventoryDAO(database));

        // Verify order status is updated to Received
        order = Order.loadFromDatabase(order.getOrderID());
        assertNotNull(order, "Order should not be null after being loaded from the database.");
        assertEquals(OrderStatus.Received, order.getStatus(), "Order status should be updated to Received.");

        // Verify order status in the database
        Document updatedOrderDoc = database.getCollection("orders").find(eq("orderID", order.getOrderID())).first();
        assertNotNull(updatedOrderDoc, "Order should exist in the database.");
        assertEquals(OrderStatus.Received.name(), updatedOrderDoc.getString("status"), "Order status in the database should be updated to Received.");
    }
}
