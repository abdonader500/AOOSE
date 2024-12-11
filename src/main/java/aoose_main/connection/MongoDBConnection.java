package aoose_main.connection;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoDBConnection {
    public static MongoDatabase connect(String databaseName) {
        String connectionString = "mongodb://localhost:27017";
        MongoClient mongoClient = MongoClients.create(connectionString);
        return mongoClient.getDatabase(databaseName);
    }
}
