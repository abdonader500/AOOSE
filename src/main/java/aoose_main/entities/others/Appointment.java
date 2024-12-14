package aoose_main.entities.others;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class Appointment {
    private long appointmentID;
    private String appointmentInformation;
    private long patientID;
    private long pharmacistID; // The pharmacist who made the appointment

    // Constructor
    public Appointment(long appointmentID, String appointmentInformation, long patientID, long pharmacistID) {
        this.appointmentID = appointmentID;
        this.appointmentInformation = appointmentInformation;
        this.patientID = patientID;
        this.pharmacistID = pharmacistID;
    }

    // Setters and getters
    public void setAppointmentID(long appointmentID) {
        this.appointmentID = appointmentID;
    }

    public void setAppointmentInformation(String appointmentInformation) {
        this.appointmentInformation = appointmentInformation;
    }

    public void setPatientID(long patientID) {
        this.patientID = patientID;
    }

    public void setPharmacistID(long pharmacistID) {
        this.pharmacistID = pharmacistID;
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

    public long getPharmacistID() {
        return pharmacistID;
    }

    // Display appointment details
    public void displayAppointmentDetails() {
        System.out.println("Appointment ID: " + appointmentID);
        System.out.println("Appointment Information: " + appointmentInformation);
        System.out.println("Patient ID: " + patientID);
        System.out.println("Pharmacist ID: " + pharmacistID);
    }

    // Save the appointment to the database
    public void saveToDatabase(MongoDatabase database) {
        MongoCollection<Document> collection = database.getCollection("appointments");

        Document doc = new Document("appointmentID", this.appointmentID)
                .append("appointmentInformation", this.appointmentInformation)
                .append("patientID", this.patientID)
                .append("pharmacistID", this.pharmacistID);

        if (collection.find(eq("appointmentID", this.appointmentID)).first() == null) {
            collection.insertOne(doc);
            System.out.println("Appointment saved to database with ID: " + this.appointmentID);
        } else {
            collection.updateOne(eq("appointmentID", this.appointmentID), new Document("$set", doc));
            System.out.println("Appointment updated in the database with ID: " + this.appointmentID);
        }
    }

    // Load appointments from the database
    public static List<Appointment> loadFromDatabase(MongoDatabase database) {
        MongoCollection<Document> collection = database.getCollection("appointments");
        List<Appointment> appointments = new ArrayList<>();

        for (Document doc : collection.find()) {
            Appointment appointment = new Appointment(
                    doc.getLong("appointmentID"),
                    doc.getString("appointmentInformation"),
                    doc.getLong("patientID"),
                    doc.getLong("pharmacistID")
            );
            appointments.add(appointment);
        }

        return appointments;
    }

    // Load a single appointment by ID
    public static Appointment loadAppointmentByID(MongoDatabase database, long appointmentID) {
        MongoCollection<Document> collection = database.getCollection("appointments");

        Document doc = collection.find(eq("appointmentID", appointmentID)).first();
        if (doc != null) {
            return new Appointment(
                    doc.getLong("appointmentID"),
                    doc.getString("appointmentInformation"),
                    doc.getLong("patientID"),
                    doc.getLong("pharmacistID")
            );
        } else {
            System.out.println("No appointment found with ID: " + appointmentID);
            return null;
        }
    }
}
