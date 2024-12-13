package aoose_main.entities.others;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.List;

public class InventoryAlert implements NotificationDecorator {
    private final NotificationInterface notification;
    private final long productID; // The ID of the product being alerted
    private final int stockLevel; // The current stock level of the product

    public InventoryAlert(NotificationInterface notification, long productID, int stockLevel) {
        this.notification = notification;
        this.productID = productID;
        this.stockLevel = stockLevel;
    }

    @Override
    public void displayNotificationDetails() {
        notification.displayNotificationDetails();
        addInventoryAlert();
    }

    @Override
    public void send() {
        notification.send();
        addInventoryAlert();
        saveToDatabase();
    }

    @Override
    public long getNotificationID() {
        return notification.getNotificationID();
    }

    @Override
    public String getMessage() {
        return notification.getMessage();
    }

    private void addInventoryAlert() {
        System.out.println("Adding inventory alert...");
        System.out.println("Product ID: " + productID);
        System.out.println("Stock level: " + stockLevel);
    }

    private void saveToDatabase() {
        try (var mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            MongoDatabase database = mongoClient.getDatabase("pharmacy");

            // Create the document for the decorated notification
            Document doc = new Document("notificationID", getNotificationID())
                    .append("message", getMessage())
                    .append("decorators", List.of("InventoryAlert"))
                    .append("additionalDetails", new Document("inventoryAlertDetails", "Inventory alert added.")
                            .append("productID", productID)
                            .append("stockLevel", stockLevel));

            database.getCollection("decorated_notifications").insertOne(doc);
        }
    }
}



