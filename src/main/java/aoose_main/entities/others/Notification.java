package aoose_main.entities.others;

import java.time.LocalDateTime;

public class Notification implements NotificationInterface {
    private final long notificationID;
    private final String message;
    private final  LocalDateTime timestamp; // Timestamp when the notification is created
    private String status; // Status of the notification (e.g., "sent", "read")
    private final long userID; // The ID of the user receiving the notification
    private final String notificationType; // Type of notification (e.g., "appointment", "inventory")
    private final  String priority; // Priority level (e.g., "high", "medium", "low")

    public Notification(long notificationID, String message, long userID, String notificationType, String priority) {
        this.notificationID = notificationID;
        this.message = message;
        this.timestamp = LocalDateTime.now(); // Automatically set to the current time
        this.status = "pending"; // Default status
        this.userID = userID;
        this.notificationType = notificationType;
        this.priority = priority;
    }

    @Override
    public void displayNotificationDetails() {
        System.out.println("Notification ID: " + notificationID);
        System.out.println("Message: " + message);
        System.out.println("Timestamp: " + timestamp);
        System.out.println("Status: " + status);
        System.out.println("User ID: " + userID);
        System.out.println("Notification Type: " + notificationType);
        System.out.println("Priority: " + priority);
    }

    @Override
    public void send() {
        this.status = "sent"; // Change status to 'sent' when the notification is sent
        System.out.println("Sending notification: " + message);
    }

    @Override
    public long getNotificationID() {
        return notificationID;
    }

    @Override
    public String getMessage() {
        return message;
    }

    // Getters and setters for new fields
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getStatus() {
        return status;
    }

    public long getUserID() {
        return userID;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public String getPriority() {
        return priority;
    }
}
