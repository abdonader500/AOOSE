package aoose_main;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import aoose_main.connection.MongoDBConnection;

public class Main {
    public static void main(String[] args) {
        try {
            // Connect to the MongoDB database
            MongoDatabase database = MongoDBConnection.connect("pharmacy");
            System.out.println("Connected to MongoDB database: " + database.getName());

            // Create or get a collection
            String collectionName = "medications";
            MongoCollection<Document> collection = database.getCollection(collectionName);
            if (collection == null) {
                database.createCollection(collectionName);
                System.out.println("Collection created: " + collectionName);
                collection = database.getCollection(collectionName);
            } else {
                System.out.println("Collection exists: " + collectionName);
            }

            // Insert a sample document into the collection
            Document sampleData = new Document("name", "Paracetamol")
                    .append("category", "Pain Reliever")
                    .append("price", 5.99)
                    .append("stock", 100);
            collection.insertOne(sampleData);
            System.out.println("Inserted document: " + sampleData.toJson());

            // Verify data insertion
            Document retrievedData = collection.find(Filters.eq("name", "Paracetamol")).first();
            if (retrievedData != null) {
                System.out.println("Document retrieved from database: " + retrievedData.toJson());
            } else {
                System.out.println("No document found in the collection.");
            }
        } catch (Exception e) {
            System.err.println("An error occurred while working with MongoDB:");
            e.printStackTrace();
        }
    }
}
