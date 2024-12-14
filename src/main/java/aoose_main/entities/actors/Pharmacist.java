package aoose_main.entities.actors;

import aoose_main.entities.abstraction.Item;
import aoose_main.entities.others.Appointment;
import aoose_main.enums.Shifts;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.List;

public class Pharmacist extends User {
    // Attributes specific to Pharmacist
    private String licenseNumber;
    private Shifts ShiftsSchedule;
    protected int salary; // No public setter
    private String specialization;

    // Constructor
    public Pharmacist(int id, String fullName, String email, String password, long phoneNumber,
                      String licenseNumber, Shifts ShiftsSchedule, int salary, String specialization) {
        super(id, fullName, email, password, phoneNumber); // Call parent constructor
        this.licenseNumber = licenseNumber;
        this.ShiftsSchedule = ShiftsSchedule;
        this.salary = salary;
        this.specialization = specialization;
    }

    // Setters and getters
    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public void setShiftsSchedule(Shifts ShiftsSchedule) {
        this.ShiftsSchedule = ShiftsSchedule;
    }

    protected void setSalary(int salary) {
        this.salary = salary;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public int getSalary() {
        return salary;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public Shifts getShiftsSchedule() {
        return ShiftsSchedule;
    }

    public String getSpecialization() {
        return specialization;
    }

    // Methods
    public void viewVerifiedPatient() {
        System.out.println("Viewing verified patient details...");
    }

    public void scheduleAppointment() {
        System.out.println("Scheduling an appointment...");
    }

    public void notifyInventoryClerk() {
        System.out.println("Notifying inventory clerk...");
    }


    public void addAppointmentForPatient(long patientID, String appointmentInformation, MongoDatabase database) {
        try {
            // Generate a unique appointment ID
            long appointmentID = System.currentTimeMillis(); // Unique based on timestamp

            // Create the appointment
            Appointment appointment = new Appointment(appointmentID, appointmentInformation, patientID, this.getId());

            // Save the appointment to the database
            appointment.saveToDatabase(database);

            System.out.println("Appointment successfully created for Patient ID: " + patientID);
        } catch (Exception e) {
            System.err.println("Error creating appointment: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public Item searchItemById(long itemID, MongoDatabase database) {
        try {
            // Get the "items" collection from the database
            MongoCollection<Document> collection = database.getCollection("items");

            // Query the database for an item with the specified ID
            Document itemDoc = collection.find(eq("itemID", itemID)).first();

            if (itemDoc != null) {
                // Convert the document to an Item object
                Item item = new Item(
                        itemDoc.getLong("itemID"),
                        itemDoc.getString("name"),
                        itemDoc.getString("category"),
                        itemDoc.getDouble("price"),
                        itemDoc.getString("description"),
                        itemDoc.getInteger("quantity")
                );
                System.out.println("Item found: " + item.getName());
                return item;
            } else {
                System.out.println("No item found with ID: " + itemID);
                return null;
            }
        } catch (Exception e) {
            System.err.println("Error searching for item: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
