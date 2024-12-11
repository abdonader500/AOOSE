package aoose_main.entities.actors;

import aoose_main.enums.Shifts;

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

    // setters for getters

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

    public List<String> generateItemsList() {
        System.out.println("Generating items list...");
        List<String> items = new ArrayList<>(); // Placeholder
        items.add("Paracetamol");
        items.add("Ibuprofen");
        return items;
    }

    public void notifyInventoryClerk() {
        System.out.println("Notifying inventory clerk...");
    }
}
