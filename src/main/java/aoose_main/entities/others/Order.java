package aoose_main.entities.others;

import aoose_main.entities.abstraction.Item;
import aoose_main.enums.OrderStatus;
import aoose_main.remotePattern.InventoryDTO;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class Order {
    private int orderID;
    private List<Item> items; // List to hold items in the order
    private OrderStatus status; // Order status (Received, Pending, Rejected)

    private static MongoCollection<Document> ordersCollection; // MongoDB collection for orders

    // Static method to initialize the MongoDB collection
    public static void initializeDatabase(MongoDatabase database) {
        ordersCollection = database.getCollection("orders");
    }

    // Constructor
    public Order(int orderID, OrderStatus status) {
        this.orderID = orderID;
        this.items = new ArrayList<>();
        this.status = status;
    }

    // Load an order from the database
    public static Order loadFromDatabase(int orderID) {
        Document orderDoc = ordersCollection.find(eq("orderID", orderID)).first();
        if (orderDoc != null) {
            Order order = new Order(orderDoc.getInteger("orderID"), OrderStatus.valueOf(orderDoc.getString("status")));
            List<Document> itemsDocs = (List<Document>) orderDoc.get("items");

            if (itemsDocs != null) {
                for (Document itemDoc : itemsDocs) {
                    try {
                        Item item = new Item(
                                ((Number) itemDoc.get("itemID")).intValue(), // Safe type conversion to Integer
                                itemDoc.getString("name"),
                                itemDoc.getString("category"),
                                itemDoc.getDouble("price"),
                                itemDoc.getString("description"),
                                ((Number) itemDoc.get("quantity")).intValue() // Safe type conversion to Integer
                        );
                        order.items.add(item);
                    } catch (Exception e) {
                        System.out.println("Error loading item: " + e.getMessage());
                    }
                }
            }
            return order;
        } else {
            System.out.println("Order with ID " + orderID + " not found in the database.");
            return null;
        }
    }

    // Save the order to the database
    public void saveToDatabase() {
        List<Document> itemsDocs = new ArrayList<>();
        for (Item item : items) {
            itemsDocs.add(new Document("itemID", item.getItemID()) // Ensure item ID is saved
                    .append("name", item.getName())
                    .append("category", item.getCategory()) // Save category
                    .append("price", item.getPrice())
                    .append("description", item.getDescription()) // Save description
                    .append("quantity", item.getQuantity()));
        }

        Document orderDoc = new Document("orderID", orderID)
                .append("items", itemsDocs)
                .append("status", status.name()); // Save status as a string

        if (ordersCollection.find(eq("orderID", orderID)).first() == null) {
            ordersCollection.insertOne(orderDoc);
            System.out.println("Order saved to database.");
        } else {
            ordersCollection.updateOne(eq("orderID", orderID), new Document("$set", orderDoc));
            System.out.println("Order updated in database.");
        }
    }

    // Approve and add items to inventory
    public void approveAndAddToInventory(List<InventoryDTO> inventoryItems) {
        if (this.status != OrderStatus.Pending) {
            System.out.println("Order is not in a pending state. Cannot approve.");
            return;
        }

        // Convert items to InventoryDTO and add them to the inventory list
        for (Item item : items) {
            InventoryDTO inventoryDTO = new InventoryDTO(
                    (int) item.getItemID(),  // Ensure item ID is passed
                    item.getName(),
                    item.getPrice(),
                    item.getQuantity()
            );
            inventoryItems.add(inventoryDTO);
            System.out.println("Item added to inventory: " + inventoryDTO.getItemName());
        }

        // Update the order status and save to the database
        this.status = OrderStatus.Received;
        saveToDatabase();
        System.out.println("Order approved and items added to inventory.");
    }

    // Reject the order
    public void rejectOrder() {
        if (this.status == OrderStatus.Pending) {
            this.status = OrderStatus.Rejected;
            saveToDatabase();
            System.out.println("Order with ID " + orderID + " rejected.");
        } else {
            System.out.println("Order is not in a pending state. Cannot reject.");
        }
    }

    // Getters and Setters
    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public List<Item> getItems() {
        return new ArrayList<>(items); // Return a copy of the items list
    }

    public void setItems(List<Item> items) {
        this.items = new ArrayList<>(items);
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
