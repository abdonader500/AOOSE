package entities.actors;

public class InsuranceProvider {
    private String companyName;
    private long companyContact;
    private String companyAddress;

    public InsuranceProvider(String companyName, long companyContact, String companyAddress) {
        this.companyName = companyName;
        this.companyContact = companyContact;
        this.companyAddress = companyAddress;
    }

    // setters and Setters
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setCompanyContact(long companyContact) {
        this.companyContact = companyContact;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanyName() {
        return companyName;
    }

    public long getCompanyContact() {
        return companyContact;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }


    // Methods
    public void processClaim(String claimId) {
        System.out.println("Processing claim with ID: " + claimId);
        // Logic to process an insurance claim
    }

    public boolean verifyPatientInsurance(String patientId) {
        System.out.println("Verifying insurance for patient with ID: " + patientId);
        // Logic to verify a patient's insurance
        return true; // Placeholder; replace with actual verification logic
    }
}
