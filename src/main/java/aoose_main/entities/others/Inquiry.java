package aoose_main.entities.others;

public class Inquiry {
    private int inquiryID;
    private int patientID;
    private String message;
    private String status;
    private String response;

    // Constructor
    public Inquiry(int inquiryID, int patientID, String message, String status) {
        this.inquiryID = inquiryID;
        this.patientID = patientID;
        this.message = message;
        this.status = status;
        this.response = "";
    }

    // Getters and Setters
    public int getInquiryID() {return inquiryID;}

    public void setInquiryID(int inquiryID) {this.inquiryID = inquiryID;}

    public int getPatientID() {return patientID;}

    public void setPatientID(int patientID) {this.patientID = patientID;}

    public String getMessage() {return message;}

    public void setMessage(String message) {this.message = message;}

    public String getStatus() {return status;}

    public void setStatus(String status) {this.status = status;}

    public String getResponse() {return response;}

    public void setResponse(String response) {this.response = response;}

    // Method to display inquiry details
    public void displayInquiryDetails() {
        System.out.println("Inquiry ID: " + inquiryID);
        System.out.println("Patient ID: " + patientID);
        System.out.println("Message: " + message);
        System.out.println("Status: " + status);
        System.out.println("Response: " + response);
    }
}
