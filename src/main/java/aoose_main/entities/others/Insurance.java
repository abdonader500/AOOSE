package aoose_main.entities.others;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
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
        this.insuranceTier = (insuranceTier != null) ? new ArrayList<>(insuranceTier) : new ArrayList<>();
        this.insurancePercentage = insurancePercentage;
        this.patientID = patientID;
    }

    // Overloaded Constructor (for simpler creation)
    public Insurance(long insuranceProviderID, long insuranceID, int insurancePercentage, long patientID) {
        this(insuranceProviderID, insuranceID, null, insurancePercentage, patientID);
    }

    // Method to create an Insurance object from a MongoDB Document
    public static Insurance fromDocument(Document insuranceDoc) {
        if (insuranceDoc == null) {
            return null;
        }

        long insuranceProviderID = 0;
        if (insuranceDoc.get("insuranceProviderID") != null) {
            insuranceProviderID = insuranceDoc.get("insuranceProviderID") instanceof Integer
                    ? insuranceDoc.getInteger("insuranceProviderID").longValue()
                    : insuranceDoc.getLong("insuranceProviderID");
        }

        long insuranceID = 0;
        if (insuranceDoc.get("insuranceID") != null) {
            insuranceID = insuranceDoc.get("insuranceID") instanceof Integer
                    ? insuranceDoc.getInteger("insuranceID").longValue()
                    : insuranceDoc.getLong("insuranceID");
        }

        int insurancePercentage = insuranceDoc.getInteger("insurancePercentage", 0);
        long patientID = 0;
        if (insuranceDoc.get("patientID") != null) {
            patientID = insuranceDoc.get("patientID") instanceof Integer
                    ? insuranceDoc.getInteger("patientID").longValue()
                    : insuranceDoc.getLong("patientID");
        }

        // Recreate insurance tiers if they exist
        List<Document> tierDocs = insuranceDoc.getList("insuranceTier", Document.class);
        List<Insurance> insuranceTier = new ArrayList<>();
        if (tierDocs != null) {
            for (Document tierDoc : tierDocs) {
                insuranceTier.add(Insurance.fromDocument(tierDoc));
            }
        }

        return new Insurance(insuranceProviderID, insuranceID, insuranceTier, insurancePercentage, patientID);
    }





    // Method to convert an Insurance object to a MongoDB Document
    public Document toDocument() {
        Document doc = new Document()
                .append("insuranceProviderID", insuranceProviderID)
                .append("insuranceID", insuranceID)
                .append("insurancePercentage", insurancePercentage)
                .append("patientID", patientID);

        // Convert insurance tiers to documents if they exist
        if (insuranceTier != null && !insuranceTier.isEmpty()) {
            List<Document> tierDocs = new ArrayList<>();
            for (Insurance tier : insuranceTier) {
                tierDocs.add(tier.toDocument());
            }
            doc.append("insuranceTier", tierDocs);
        }

        return doc;
    }

    // Send form method to interact with the database
    public void sendForm(MongoDatabase database) {
        if (database == null) {
            throw new IllegalArgumentException("Database connection cannot be null.");
        }

        MongoCollection<Document> collection = database.getCollection("insuranceForms");
        collection.insertOne(this.toDocument());
        System.out.println("Insurance form submitted for insurance ID: " + insuranceID);
    }

    // Getters and Setters
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
        return (insuranceTier != null) ? new ArrayList<>(insuranceTier) : new ArrayList<>();
    }

    public void setInsuranceTier(List<Insurance> insuranceTier) {
        this.insuranceTier = (insuranceTier != null) ? new ArrayList<>(insuranceTier) : new ArrayList<>();
    }

    public int getInsurancePercentage() {
        return insurancePercentage;
    }

    public void setInsurancePercentage(int insurancePercentage) {
        if (insurancePercentage < 0 || insurancePercentage > 100) {
            throw new IllegalArgumentException("Insurance percentage must be between 0 and 100.");
        }
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
        if (totalBill < 0) {
            throw new IllegalArgumentException("Total bill cannot be negative.");
        }
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
        } else {
            System.out.println("No insurance tiers available.");
        }
    }
}
