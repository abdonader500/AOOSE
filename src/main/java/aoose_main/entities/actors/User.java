package aoose_main.entities.actors;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;

import java.util.Objects;

public class User {
    // Attributes
    private int id;
    private String fullName;
    private String email;
    private String password; // In a real application, consider storing a hashed password
    private long phoneNumber;

    private static MongoCollection<Document> collection; // Shared MongoDB collection for all User objects

    // Constructor
    public User(int id, String fullName, String email, String password, long phoneNumber) {
        this.id = id;
        this.fullName = fullName;
        setEmail(email); // Validation
        setPassword(password); // Validation
        this.phoneNumber = phoneNumber;
    }

    // Static method to initialize the MongoDB collection
    public static void initializeDatabase(MongoDatabase database) {
        collection = database.getCollection("users");
    }

    // Setters with validation
    public void setId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID must be positive.");
        }
        this.id = id;
    }

    public void setFullName(String fullName) {
        if (fullName == null || fullName.isEmpty()) {
            throw new IllegalArgumentException("Full name cannot be empty.");
        }
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        if (email == null || !email.matches("^[\\w.-]+@[\\w.-]+\\.\\w+$")) {
            throw new IllegalArgumentException("Invalid email format.");
        }
        this.email = email;
    }

    public void setPassword(String password) {
        if (password == null || password.length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters long.");
        }
        this.password = password; // Ideally, hash the password
    }

    public void setPhoneNumber(long phoneNumber) {
        if (String.valueOf(phoneNumber).length() < 7) {
            throw new IllegalArgumentException("Phone number is invalid.");
        }
        this.phoneNumber = phoneNumber;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    // Database Methods

    public void saveToDatabase() {
        Document doc = new Document("id", id)
                .append("fullName", fullName)
                .append("email", email)
                .append("password", password) // Store hashed passwords in real applications
                .append("phoneNumber", phoneNumber);

        if (collection.find(eq("id", id)).first() == null) {
            collection.insertOne(doc);
            System.out.println("User saved to database: " + doc.toJson());
        } else {
            collection.updateOne(eq("id", id), new Document("$set", doc));
            System.out.println("User updated in database: " + doc.toJson());
        }
    }

    public static User loadFromDatabase(int id) {
        Document doc = collection.find(eq("id", id)).first();
        if (doc != null) {
            return new User(
                    doc.getInteger("id"),
                    doc.getString("fullName"),
                    doc.getString("email"),
                    doc.getString("password"),
                    doc.getLong("phoneNumber")
            );
        }
        System.out.println("User with ID " + id + " not found in the database.");
        return null;
    }

    public void deleteFromDatabase() {
        collection.deleteOne(eq("id", id));
        System.out.println("User with ID " + id + " deleted from the database.");
    }

    // Methods
    public void changePassword(String newPassword) {
        setPassword(newPassword);
        saveToDatabase();
        System.out.println("Password updated successfully!");
    }

    public void viewAccountDetails() {
        if (collection == null) {
            throw new IllegalStateException("Database collection is not initialized.");
        }

        FindIterable<Document> allUsers = collection.find();

        System.out.println("All User Accounts:");
        for (Document doc : allUsers) {
            System.out.println("User ID: " + doc.getInteger("id"));
            System.out.println("Full Name: " + doc.getString("fullName"));
            System.out.println("Email: " + doc.getString("email"));
            System.out.println("Phone Number: " + doc.getLong("phoneNumber"));
            System.out.println("------------------------");
        }
    }

    public void updateUser(User updatedUser) {
        setId(updatedUser.id);
        setFullName(updatedUser.fullName);
        setEmail(updatedUser.email);
        setPassword(updatedUser.password);
        setPhoneNumber(updatedUser.phoneNumber);
        saveToDatabase();
        System.out.println("User updated successfully!");
    }

    // Override equals() and hashCode()
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return id == user.id &&
                phoneNumber == user.phoneNumber &&
                fullName.equals(user.fullName) &&
                email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullName, email, phoneNumber);
    }

    // Override toString()
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber=" + phoneNumber +
                '}';
    }
}
