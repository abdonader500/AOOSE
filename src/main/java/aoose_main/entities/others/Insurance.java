package aoose_main.entities.others;

import java.util.List;

public class Insurance {
    private long insuranceProviderID; // ID of the associated insurance provider
    private long insuranceID; // Unique ID for the insurance policy
    private List<Insurance> insuranceTier; // List representing the insurance tier structure
    private int insurancePercentage; // Percentage of coverage by the insurance
    private long patientID; // ID of the patient who has this insurance

    // Constructor
    public Insurance(long insuranceProviderID, long insuranceID, List<Insurance> insuranceTier,
                     int insurancePercentage, long patientID) {
        this.insuranceProviderID = insuranceProviderID;
        this.insuranceID = insuranceID;
        this.insuranceTier = insuranceTier;
        this.insurancePercentage = insurancePercentage;
        this.patientID = patientID;
    }

    // setters and getters
    public long getInsuranceProviderID() {
        return insuranceProviderID;
    }

    public void setInsuranceProviderID(long insuranceProviderID) {
        this.insuranceProviderID = insuranceProviderID;
    }

    public long getInsuranceID() {
        return insuranceID;
    }

    public void setInsuranceID(long insuranceID) {
        this.insuranceID = insuranceID;
    }

    public List<Insurance> getInsuranceTier() {
        return insuranceTier;
    }

    public void setInsuranceTier(List<Insurance> insuranceTier) {
        this.insuranceTier = insuranceTier;
    }

    public int getInsurancePercentage() {
        return insurancePercentage;
    }

    public void setInsurancePercentage(int insurancePercentage) {
        this.insurancePercentage = insurancePercentage;
    }

    public long getPatientID() {
        return patientID;
    }

    public void setPatientID(long patientID) {
        this.patientID = patientID;
    }

    // Method to calculate coverage based on insurance percentage
    public double calculateCoverage(double totalBill) {
        return totalBill * (insurancePercentage / 100.0);
    }

    // Method to display insurance details
    public void displayInsuranceDetails() {
        System.out.println("Insurance ID: " + insuranceID);
        System.out.println("Provider ID: " + insuranceProviderID);
        System.out.println("Patient ID: " + patientID);
        System.out.println("Insurance Coverage Percentage: " + insurancePercentage + "%");
        if (insuranceTier != null && !insuranceTier.isEmpty()) {
            System.out.println("Insurance Tier Details:");
            for (Insurance tier : insuranceTier) {
                System.out.println("- Tier Insurance ID: " + tier.getInsuranceID());
            }
        }
    }
}
