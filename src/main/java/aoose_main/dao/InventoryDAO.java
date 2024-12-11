package aoose_main.dao;

import aoose_main.remotePattern.InventoryDTO;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;

public class InventoryDAO {
    private MongoCollection<Document> collection;

    // Constructor to accept a MongoDatabase
    public InventoryDAO(MongoDatabase database) {
        this.collection = database.getCollection("inventory");
    }

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

    public void updateItem(InventoryDTO item) {
        Document updateDoc = new Document("name", item.getItemName())
                .append("price", item.getPrice())
                .append("quantity", item.getQuantity());
        collection.updateOne(eq("item_id", item.getItemId()), new Document("$set", updateDoc));
    }

    public void createItem(InventoryDTO item) {
        Document doc = new Document("item_id", item.getItemId())
                .append("name", item.getItemName())
                .append("price", item.getPrice())
                .append("quantity", item.getQuantity());
        collection.insertOne(doc);
    }

    public void deleteItem(int id) {
        collection.deleteOne(eq("item_id", id));
    }
}
