package aoose_main.entities.others;

import java.time.LocalDateTime;

public class Feedback {
    private int feedbackId;
    private int supplierID;
    private String text;
    private LocalDateTime date;

    // Constructor
    public Feedback(int feedbackId, int userId, String text, LocalDateTime date) {
        this.feedbackId = feedbackId;
        this.supplierID = userId;
        this.text = text;
        this.date = date;
    }

    // Getters and Setters
    public int getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(int feedbackId) {
        this.feedbackId = feedbackId;
    }

    public int getUserId() {
        return supplierID;
    }

    public void setUserId(int userId) {
        this.supplierID = userId;
    }

    public String getMessage() {
        return text;
    }

    public void setMessage(String message) {
        this.text = message;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }


    public void displayFeedback() {
        System.out.println("Feedback ID: " + feedbackId);
        System.out.println("User ID: " + supplierID);
        System.out.println("Feedback Date: " + date.toString());
        System.out.println("Message: " + text);
    }
}
