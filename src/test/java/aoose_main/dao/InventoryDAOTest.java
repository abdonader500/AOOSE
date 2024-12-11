package aoose_main.dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.jupiter.api.*;
import aoose_main.connection.MongoDBConnection;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class InventoryDAOTest {

    private MongoDatabase database;
    private MongoCollection<Document> collection;
    private final String collectionName = "medications";

    @BeforeAll
    public void setup() {
        // Connect to the MongoDB database
        database = MongoDBConnection.connect("pharmacy");
        collection = database.getCollection(collectionName);
        if (collection == null) {
            database.createCollection(collectionName);
            collection = database.getCollection(collectionName);
        }
    }

    @BeforeEach
    public void cleanCollection() {
        // Clear the collection before each test
        collection.deleteMany(new Document());
    }

    @Test
    public void testCreateItem() {
        // Arrange
        Document item = new Document("name", "Ibuprofen")
                .append("category", "Pain Reliever")
                .append("price", 10.99)
                .append("stock", 50);

        // Act
        collection.insertOne(item);

        // Assert
        Document retrievedItem = collection.find(new Document("name", "Ibuprofen")).first();
        assertNotNull(retrievedItem);
        assertEquals("Ibuprofen", retrievedItem.getString("name"));
        assertEquals("Pain Reliever", retrievedItem.getString("category"));
        assertEquals(10.99, retrievedItem.getDouble("price"));
        assertEquals(50, retrievedItem.getInteger("stock"));
    }

    @Test
    public void testReadItem() {
        // Arrange
        Document item = new Document("name", "Aspirin")
                .append("category", "Pain Reliever")
                .append("price", 5.99)
                .append("stock", 30);
        collection.insertOne(item);

        // Act
        Document retrievedItem = collection.find(new Document("name", "Aspirin")).first();

        // Assert
        assertNotNull(retrievedItem);
        assertEquals("Aspirin", retrievedItem.getString("name"));
    }

    @Test
    public void testUpdateItem() {
        // Arrange
        Document item = new Document("name", "Cough Syrup")
                .append("category", "Cold Medicine")
                .append("price", 15.99)
                .append("stock", 20);
        collection.insertOne(item);

        // Act
        collection.updateOne(new Document("name", "Cough Syrup"),
                new Document("$set", new Document("price", 17.99)));

        // Assert
        Document updatedItem = collection.find(new Document("name", "Cough Syrup")).first();
        assertNotNull(updatedItem);
        assertEquals(17.99, updatedItem.getDouble("price"));
    }

    @Test
    public void testDeleteItem() {
        // Arrange
        Document item = new Document("name", "Vitamin C")
                .append("category", "Supplements")
                .append("price", 8.99)
                .append("stock", 100);
        collection.insertOne(item);

        // Act
        collection.deleteOne(new Document("name", "Vitamin C"));

        // Assert
        Document deletedItem = collection.find(new Document("name", "Vitamin C")).first();
        assertNull(deletedItem);
    }
}
