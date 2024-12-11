package aoose_main.dao;

import aoose_main.connection.MongoDBConnection;
import aoose_main.remotePattern.InventoryDTO;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

public class InventoryDAO {
    private MongoCollection<Document> collection;

    public InventoryDAO() {
        MongoDatabase database = MongoDBConnection.connect("yourDatabaseName");
        collection = database.getCollection("inventory");
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
}
