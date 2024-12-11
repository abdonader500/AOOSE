package aoose_main.entities.others;

public class Notification {
    private long notificationID;
    private String message;

    // Constructor
    public Notification(long notificationID, String message) {
        this.notificationID = notificationID;
        this.message = message;
    }


    // setters and getters
    public void setNotificationID(long notificationID) {
        this.notificationID = notificationID;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getNotificationID() {
        return notificationID;
    }

    public String getMessage() {
        return message;
    }

    // Display notification details
    public void displayNotificationDetails() {
        System.out.println("Notification ID: " + notificationID);
        System.out.println("Message: " + message);
    }
}
