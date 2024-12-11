package aoose_main;

import aoose_main.entities.abstraction.Item;
import aoose_main.entities.actors.InventoryClerk;
import aoose_main.entities.actors.Supplier;
import aoose_main.entities.others.Order;
import aoose_main.enums.OrderStatus;
import aoose_main.connection.MongoDBConnection;
import com.mongodb.client.MongoDatabase;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
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
        // Supplier registers an order
        Supplier supplier = new Supplier("Health Supplies Co.", 1234567890L, "123 Supplier Lane");
        Item item1 = new Item(1, "Paracetamol", "Pain reliever", 2.5, "Tablet", 100);
        Item item2 = new Item(2, "Ibuprofen", "Anti-inflammatory", 3.0, "Capsule", 50);
        List<Item> items = Arrays.asList(item1, item2);

        Order order = supplier.registerOrder(1, items);
        Order order1 = supplier.registerOrder(3, items);

        // Display order details created by the supplier
        System.out.println("Order created by supplier:");
        System.out.println("Order ID: " + order.getOrderID());
        for (Item item : order.getItems()) {
            System.out.println("Item: " + item.getName() + ", Quantity: " + item.getQuantity() + ", Price: $" + item.getPrice());
        }
        System.out.println("Status: " + order.getStatus());

        // InventoryClerk processes the order
        InventoryClerk inventoryClerk = new InventoryClerk(1, "John Doe", "johndoe@example.com", "password123", 1234567890L, 5000, "Inventory");
        Order loadedOrder = inventoryClerk.requestOrder(1);

        assertNotNull(loadedOrder, "Order should be loaded from the database.");
        assertEquals(OrderStatus.Pending, loadedOrder.getStatus(), "Order status should initially be Pending.");

        inventoryClerk.approveOrder(1);

        // Reload the order to verify the status change
        Order approvedOrder = Order.loadFromDatabase(1);
        assertNotNull(approvedOrder, "Approved order should be loaded from the database.");
        assertEquals(OrderStatus.Received, approvedOrder.getStatus(), "Order status should be updated to Received.");

        // Display updated order details after processing
        System.out.println("Order after Inventory Clerk confirmation:");
        System.out.println("Order ID: " + approvedOrder.getOrderID());
        for (Item item : approvedOrder.getItems()) {
            System.out.println("Item: " + item.getName() + ", Quantity: " + item.getQuantity() + ", Price: $" + item.getPrice());
        }
        System.out.println("Status: " + approvedOrder.getStatus());
    }
}
