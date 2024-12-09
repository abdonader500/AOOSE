package entities.others;

import enums.ReportType;

public class Report {
    private long id;
    private ReportType reportType;

    // Constructor
    public Report(long id, ReportType reportType) {
        this.id = id;
        this.reportType = reportType;
    }

    // Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ReportType getReportType() {
        return reportType;
    }

    public void setReportType(ReportType reportType) {
        this.reportType = reportType;
    }

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", reportType=" + reportType +
                '}';
    }
}
