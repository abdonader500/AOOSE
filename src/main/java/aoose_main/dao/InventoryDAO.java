package aoose_main.dao;

import aoose_main.remotePattern.InventoryDTO;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.*;

public class InventoryDAO {
    private MongoCollection<Document> collection;

    // Constructor to accept a MongoDatabase
    public InventoryDAO(MongoDatabase database) {
        this.collection = database.getCollection("inventory");
    }

    // Get item by ID
    public InventoryDTO getItemById(int id) {
        Document doc = collection.find(eq("item_id", id)).first();
        if (doc != null) {
            return new InventoryDTO(
                    doc.getInteger("item_id"),
                    doc.getString("name"),
                    doc.getDouble("price"),
                    doc.getInteger("quantity")
            );
        }
        return null;
    }

    // Update an item
    public void updateItem(InventoryDTO item) {
        Document updateDoc = new Document("name", item.getItemName())
                .append("price", item.getPrice())
                .append("quantity", item.getQuantity());
        collection.updateOne(eq("item_id", item.getItemId()), new Document("$set", updateDoc));
    }

    // Create a new item
    public void createItem(InventoryDTO item) {
        Document doc = new Document("item_id", item.getItemId())
                .append("name", item.getItemName())
                .append("price", item.getPrice())
                .append("quantity", item.getQuantity());
        collection.insertOne(doc);
    }

    // Delete an item by ID
    public void deleteItem(int id) {
        collection.deleteOne(eq("item_id", id));
    }

    // Get all items
    public List<InventoryDTO> getAllItems() {
        List<InventoryDTO> items = new ArrayList<>();
        try (MongoCursor<Document> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                InventoryDTO item = new InventoryDTO(
                        doc.getInteger("item_id"),
                        doc.getString("name"),
                        doc.getDouble("price"),
                        doc.getInteger("quantity")
                );
                items.add(item);
            }
        }
        return items;
    }

    // Search items by name
    public List<InventoryDTO> searchItemsByName(String name) {
        List<InventoryDTO> items = new ArrayList<>();
        try (MongoCursor<Document> cursor = collection.find(regex("name", ".*" + name + ".*", "i")).iterator()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                InventoryDTO item = new InventoryDTO(
                        doc.getInteger("item_id"),
                        doc.getString("name"),
                        doc.getDouble("price"),
                        doc.getInteger("quantity")
                );
                items.add(item);
            }
        }
        return items;
    }

    // Filter items by category
    public List<InventoryDTO> filterItemsByCategory(String category) {
        List<InventoryDTO> items = new ArrayList<>();
        try (MongoCursor<Document> cursor = collection.find(eq("category", category)).iterator()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                InventoryDTO item = new InventoryDTO(
                        doc.getInteger("item_id"),
                        doc.getString("name"),
                        doc.getDouble("price"),
                        doc.getInteger("quantity")
                );
                items.add(item);
            }
        }
        return items;
    }
}
