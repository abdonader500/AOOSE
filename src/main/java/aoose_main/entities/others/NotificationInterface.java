package aoose_main.entities.others;

public interface NotificationInterface {
    void displayNotificationDetails(); // Display notification details

    void send(); // Send the notification

    long getNotificationID(); // Add method to get Notification ID
    String getMessage(); // Add method to get the message
}
