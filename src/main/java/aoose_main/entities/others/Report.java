package aoose_main.entities.others;
import aoose_main.enums.AccessLevels;
import aoose_main.entities.actors.Admin;
import aoose_main.enums.ReportType;

public class Report extends Admin {
    // Attributes specific to Report
    private long ID;
    private String text;
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

    public void setText(String text) {
        this.text = text;
    }

    public long getID() {
        return ID;
    }

    public ReportType getReportType() {
        return reportType;
    }

    public String getText() {return text;}

    // Method to generate the report
    public void generate() {
        System.out.println("Admin " + getFullName() + " is generating " + reportType + "...");
        // Logic for generating the report
    }
}
