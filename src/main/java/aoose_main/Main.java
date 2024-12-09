package aoose_main;

import com.mongodb.client.MongoDatabase;
import connection.MongoDBConnection;

public class Main {
    public static void main(String[] args) {
        try {
            MongoDatabase database = MongoDBConnection.connect("pharmacy");
            System.out.println("Connected to MongoDB database: " + database.getName());
        } catch (Exception e) {
            System.err.println("An error occurred while connecting to MongoDB:");
            e.printStackTrace();
        }
    }
}
