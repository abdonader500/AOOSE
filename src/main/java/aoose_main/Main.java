package aoose_main;

import aoose_main.gui.login;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import javax.swing.JFrame;


public class Main {
    public static void main(String[] args) {
        // Connect to MongoDB
        MongoDatabase database = MongoClients.create("mongodb://localhost:27017").getDatabase("pharmacy");

        // Create the main JFrame
        JFrame frame = new JFrame("Login Page");
        login loginPanel = new login(database, frame);

        // Set up the JFrame
        frame.setContentPane(loginPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setVisible(true);
    }
}

