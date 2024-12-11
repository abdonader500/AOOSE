package aoose_main;

import aoose_main.connection.MongoDBConnection;
import aoose_main.entities.actors.User;
import com.mongodb.client.MongoDatabase;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserTest {

    private MongoDatabase database;

    @BeforeAll
    public void setupDatabase() {
        // Initialize MongoDB connection
        database = MongoDBConnection.connect("pharmacy");
        User.initializeDatabase(database);
    }

    @BeforeEach
    public void setupTestData() {
        // Add initial test data to the database
        User testUser = new User(1, "Alice Brown", "alice.brown@example.com", "password123", 1234567890L);
        testUser.saveToDatabase();
    }



    @Test
    public void testSaveToDatabase() {
        User user = new User(2, "John Smith", "john.smith@example.com", "securePass", 9876543210L);
        user.saveToDatabase();

        User retrievedUser = User.loadFromDatabase(2);
        assertNotNull(retrievedUser);
        assertEquals("John Smith", retrievedUser.getFullName());
        assertEquals("john.smith@example.com", retrievedUser.getEmail());
        assertEquals(9876543210L, retrievedUser.getPhoneNumber());
    }

    @Test
    public void testLoadFromDatabase() {
        User user = User.loadFromDatabase(1);
        assertNotNull(user);
        assertEquals("Alice Brown", user.getFullName());
        assertEquals("alice.brown@example.com", user.getEmail());
        assertEquals(1234567890L, user.getPhoneNumber());
    }

    @Test
    public void testUpdateUser() {
        User user = User.loadFromDatabase(1);
        assertNotNull(user);

        User updatedUser = new User(1, "Alice Johnson", "alice.johnson@example.com", "password123", 1112223333L);
        user.updateUser(updatedUser);

        User result = User.loadFromDatabase(1);
        assertNotNull(result);
        assertEquals("Alice Johnson", result.getFullName());
        assertEquals("alice.johnson@example.com", result.getEmail());
        assertEquals(1112223333L, result.getPhoneNumber());
    }

    @Test
    public void testChangePassword() {
        User user = User.loadFromDatabase(1);
        assertNotNull(user);

        user.changePassword("newPassword456");

        User updatedUser = User.loadFromDatabase(1);
        assertNotNull(updatedUser);
        assertEquals("newPassword456", updatedUser.getPassword());
    }

    @Test
    public void testViewAccountDetails() {
        User user = User.loadFromDatabase(1);
        assertNotNull(user);

        System.out.println("Testing viewAccountDetails:");
        user.viewAccountDetails(); // This should print the user's details to the console
    }

    @AfterAll
    public void teardownDatabase() {
        // Cleanup resources
        database = null;
    }
}
