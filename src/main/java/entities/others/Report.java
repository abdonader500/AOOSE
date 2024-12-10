package entities.others;
import entities.actors.Admin;
import enums.AccessLevels;
import enums.ReportType;

public class Report extends Admin {
    // Attributes specific to Report
    private long ID; // Unique identifier for the report
    private ReportType reportType; // Type of the report

    // Constructor
    public Report(int id, String fullName, String email, String password, long phoneNumber, int salary,
                  AccessLevels accessLevel, long reportID, ReportType reportType) {
        super(id, fullName, email, password, phoneNumber, salary, accessLevel); // Call Admin constructor
        this.ID = reportID;
        this.reportType = reportType;
    }


    // setters and getters
    public void setID(long ID) {
        this.ID = ID;
    }

    public void setReportType(ReportType reportType) {
        this.reportType = reportType;
    }

    public long getID() {
        return ID;
    }

    public ReportType getReportType() {
        return reportType;
    }

    // Method to generate the report
    public void generate() {
        System.out.println("Admin " + getFullName() + " is generating " + reportType + "...");
        // Logic for generating the report
    }
}
