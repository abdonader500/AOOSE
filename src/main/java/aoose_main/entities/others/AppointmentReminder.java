package aoose_main.entities.others;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays; // Correct import for Arrays.asList()

public class AppointmentReminder implements NotificationDecorator {
    private final NotificationInterface notification;
    private final LocalDateTime reminderTime; // When the reminder will be sent

    public AppointmentReminder(NotificationInterface notification) {
        this.notification = notification;
        this.reminderTime = calculateReminderTime(); // Calculate the reminder time dynamically
    }

    @Override
    public void displayNotificationDetails() {
        notification.displayNotificationDetails();
        addAppointmentReminder();
    }

    @Override
    public void send() {
        notification.send();
        addAppointmentReminder();
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

    private void addAppointmentReminder() {
        System.out.println("Adding appointment reminder...");
        System.out.println("Reminder will be sent at: " + reminderTime);
    }

    private LocalDateTime calculateReminderTime() {
        // Calculate reminder time (for example, 1 hour before the appointment)
        return LocalDateTime.now().plusHours(1);
    }

    private void saveToDatabase() {
        try (var mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            MongoDatabase database = mongoClient.getDatabase("pharmacy");

            // Format the reminderTime to a string
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedReminderTime = reminderTime.format(formatter);

            // Create the document for the decorated notification
            Document doc = new Document("notificationID", getNotificationID())
                    .append("message", getMessage())
                    .append("decorators", Arrays.asList("AppointmentReminder" , "InventoryAlert"))
                    .append("additionalDetails", new Document("appointmentReminderDetails", "Appointment reminder added.")
                            .append("reminderTime", formattedReminderTime)); // Use formatted string for reminderTime

            database.getCollection("decorated_notifications").insertOne(doc);
        }
    }
}





