package entities.actors;

import entities.others.Insurance;

import java.util.ArrayList;
import java.util.List;

public class InsuranceProvider {
    private String termsAndConditions;
    private String companyName;
    private String coverageDetails;
    private List<Insurance> insurances; // Composition: InsuranceProvider owns Insurance

    // Constructor
    public InsuranceProvider(String termsAndConditions, String companyName, String coverageDetails) {
        this.termsAndConditions = termsAndConditions;
        this.companyName = companyName;
        this.coverageDetails = coverageDetails;
        this.insurances = new ArrayList<>();
    }


    // setters and getters
    public void setTermsAndConditions(String termsAndConditions) {
        this.termsAndConditions = termsAndConditions;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setCoverageDetails(String coverageDetails) {
        this.coverageDetails = coverageDetails;
    }

    public String getTermsAndConditions() {
        return termsAndConditions;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCoverageDetails() {
        return coverageDetails;
    }

    public List<Insurance> getInsurances() {
        return insurances;
    }

    // Methods to manage Insurance
    public void addInsurance(Insurance insurance) {
        insurances.add(insurance);
        System.out.println("Insurance added: " + insurance.getInsuranceID());
    }

    public void removeInsurance(long insuranceID) {
        insurances.removeIf(insurance -> insurance.getInsuranceID() == insuranceID);
        System.out.println("Insurance removed: " + insuranceID);
    }

    public void displayInsurances() {
        System.out.println("Insurance list for provider: " + companyName);
        for (Insurance insurance : insurances) {
            insurance.displayInsuranceDetails();
        }
    }
}
