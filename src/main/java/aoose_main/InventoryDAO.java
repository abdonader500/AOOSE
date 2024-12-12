package aoose_main;

import aoose_main.remotePattern.InventoryDTO;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.*;

public class InventoryDAO {
    private final MongoCollection<Document> collection;

    // Constructor to accept a MongoDatabase
    public InventoryDAO(MongoDatabase database) {
        if (database == null) {
            throw new IllegalArgumentException("MongoDatabase cannot be null.");
        }
        this.collection = database.getCollection("inventory");
    }

    // Get an item by its name
    public InventoryDTO getItemByName(String name) {
        Document doc = collection.find(eq("name", name)).first();
        if (doc != null) {
            return mapDocumentToDTO(doc);
        } else {
            System.out.println("Item with name " + name + " not found.");
            return null;
        }
    }


    // Get an item by ID
    public InventoryDTO getItemById(long id) {
        Document doc = collection.find(eq("item_id", id)).first();
        if (doc != null) {
            return mapDocumentToDTO(doc);
        }
        return null;
    }

    // Update an item in the inventory
    public void updateItem(InventoryDTO item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null.");
        }
        Document updateDoc = new Document("name", item.getItemName())
                .append("price", item.getPrice())
                .append("quantity", item.getQuantity());
        collection.updateOne(eq("name", item.getItemName()), new Document("$set", updateDoc));
    }

    // Create a new item in the inventory
    public void createItem(InventoryDTO item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null.");
        }
        Document doc = new Document("item_id", item.getItemID())
                .append("name", item.getItemName())
                .append("price", item.getPrice())
                .append("quantity", item.getQuantity());
        collection.insertOne(doc);
    }


    // Delete an item by name
    public void deleteItemByName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Item name cannot be null or empty.");
        }
        collection.deleteOne(eq("name", name));
    }

    // Delete an item by ID
    public void deleteItemById(long id) {
        collection.deleteOne(eq("item_id", id));
    }

    // Get all items in the inventory
    public List<InventoryDTO> getAllItems() {
        List<InventoryDTO> items = new ArrayList<>();
        try (MongoCursor<Document> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                items.add(mapDocumentToDTO(doc));
            }
        }
        return items;
    }

    // Search for items by partial name match
    public List<InventoryDTO> searchItemsByName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Search term cannot be null or empty.");
        }
        List<InventoryDTO> items = new ArrayList<>();
        try (MongoCursor<Document> cursor = collection.find(regex("name", ".*" + name + ".*", "i")).iterator()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                items.add(mapDocumentToDTO(doc));
            }
        }
        return items;
    }

    // Filter items by category
    public List<InventoryDTO> filterItemsByCategory(String category) {
        if (category == null || category.isEmpty()) {
            throw new IllegalArgumentException("Category cannot be null or empty.");
        }
        List<InventoryDTO> items = new ArrayList<>();
        try (MongoCursor<Document> cursor = collection.find(eq("category", category)).iterator()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                items.add(mapDocumentToDTO(doc));
            }
        }
        return items;
    }

    // Utility method to map a MongoDB document to an InventoryDTO object
    private InventoryDTO mapDocumentToDTO(Document doc) {
        if (doc == null) {
            throw new IllegalArgumentException("Document cannot be null.");
        }

        // Safely retrieve item ID
        int itemID = 0;
        if (doc.containsKey("item_id")) {
            Object itemIDObj = doc.get("item_id");
            if (itemIDObj instanceof Number) {
                itemID = ((Number) itemIDObj).intValue();
            } else {
                throw new IllegalArgumentException("Invalid item_id type in document.");
            }
        }

        // Safely retrieve name
        String name = doc.getString("name");
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Item name in document cannot be null or empty.");
        }

        // Safely retrieve price
        double price = 0.0;
        if (doc.containsKey("price")) {
            Object priceObj = doc.get("price");
            if (priceObj instanceof Number) {
                price = ((Number) priceObj).doubleValue();
            } else {
                throw new IllegalArgumentException("Invalid price type in document.");
            }
        }

        // Safely retrieve quantity
        int quantity = 0;
        if (doc.containsKey("quantity")) {
            Object quantityObj = doc.get("quantity");
            if (quantityObj instanceof Number) {
                quantity = ((Number) quantityObj).intValue();
            } else {
                throw new IllegalArgumentException("Invalid quantity type in document.");
            }
        }

        // Return the InventoryDTO object
        return new InventoryDTO(itemID, name, price, quantity);
    }



}
