package entities.others;

public class Appointment {
    private long appointmentID;
    private String appointmentInformation;
    private long patientID;

    // Constructor
    public Appointment(long appointmentID, String appointmentInformation, long patientID) {
        this.appointmentID = appointmentID;
        this.appointmentInformation = appointmentInformation;
        this.patientID = patientID;
    }

    // setters and getters

    public void setAppointmentID(long appointmentID) {
        this.appointmentID = appointmentID;
    }

    public void setAppointmentInformation(String appointmentInformation) {
        this.appointmentInformation = appointmentInformation;
    }

    public void setPatientID(long patientID) {
        this.patientID = patientID;
    }

    public long getAppointmentID() {
        return appointmentID;
    }

    public String getAppointmentInformation() {
        return appointmentInformation;
    }

    public long getPatientID() {
        return patientID;
    }

    // Display appointment details
    public void displayAppointmentDetails() {
        System.out.println("Appointment ID: " + appointmentID);
        System.out.println("Appointment Information: " + appointmentInformation);
        System.out.println("Patient ID: " + patientID);
    }
}
