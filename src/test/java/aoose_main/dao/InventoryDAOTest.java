package aoose_main.dao;

import aoose_main.dao.InventoryDAO;
import aoose_main.remotePattern.InventoryDTO;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.ImmutableMongodConfig;
import de.flapdoodle.embed.mongo.config.MongoCmdOptions;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MongoDBContainer;

import java.io.IOException;

import static com.mongodb.client.model.Filters.eq;
import static org.junit.jupiter.api.Assertions.*;

public class InventoryDAOTest {
    private static MongoDBContainer mongoDBContainer;
    private static InventoryDAO inventoryDAO;

    @BeforeAll
    static void setUp() {
        String username = "abdonader500";
        String password = "AbdoNader%40183099"; // URL-encoded password
        String connectionString = String.format("mongodb://%s:%s@localhost:27017/?authSource=admin", username, password);

        MongoDatabase database = MongoClients.create(connectionString).getDatabase("testInventoryDB");
        inventoryDAO = new InventoryDAO(database);
    }





    @AfterAll
    static void tearDown() {
        if (mongoDBContainer != null) {
            mongoDBContainer.stop();
        }
    }

    @Test
    void testCreateAndFetchItem() {
        InventoryDTO newItem = new InventoryDTO(1, "TestItem", 100.0, 10);
        inventoryDAO.createItem(newItem);

        InventoryDTO fetchedItem = inventoryDAO.getItemById(1);
        assertNotNull(fetchedItem);
        assertEquals("TestItem", fetchedItem.getItemName());
        assertEquals(100.0, fetchedItem.getPrice());
        assertEquals(10, fetchedItem.getQuantity());
    }

    @Test
    void testUpdateAndDeleteItem() {
        InventoryDTO updatedItem = new InventoryDTO(1, "UpdatedItem", 150.0, 5);
        inventoryDAO.updateItem(updatedItem);

        InventoryDTO fetchedUpdatedItem = inventoryDAO.getItemById(1);
        assertEquals("UpdatedItem", fetchedUpdatedItem.getItemName());

        inventoryDAO.deleteItem(1);
        assertNull(inventoryDAO.getItemById(1));
    }
}
