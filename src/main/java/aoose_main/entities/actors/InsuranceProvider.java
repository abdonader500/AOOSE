package aoose_main.entities.actors;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class InsuranceProvider extends User {
    // Additional Attributes
    private String termsAndConditions;
    private String companyName;
    private String coverageDetails;

    private static MongoCollection<Document> providerCollection; // Shared MongoDB collection for InsuranceProvider objects
    private static MongoCollection<Document> insuranceCollection; // Shared MongoDB collection for insurance policies

    // Constructor
    public InsuranceProvider(int id, String fullName, String email, String password, long phoneNumber,
                             String termsAndConditions, String companyName, String coverageDetails) {
        super(id, fullName, email, password, phoneNumber);
        setTermsAndConditions(termsAndConditions);
        setCompanyName(companyName);
        setCoverageDetails(coverageDetails);
    }

    // Static method to initialize the MongoDB collections
    public static void initializeDatabase(MongoDatabase database) {
        providerCollection = database.getCollection("insurance_providers");
        insuranceCollection = database.getCollection("insurances");
    }

    // Setters and Getters
    public String getTermsAndConditions() {
        return termsAndConditions;
    }

    public void setTermsAndConditions(String termsAndConditions) {
        if (termsAndConditions == null || termsAndConditions.isEmpty()) {
            throw new IllegalArgumentException("Terms and conditions cannot be empty.");
        }
        this.termsAndConditions = termsAndConditions;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        if (companyName == null || companyName.isEmpty()) {
            throw new IllegalArgumentException("Company name cannot be empty.");
        }
        this.companyName = companyName;
    }

    public String getCoverageDetails() {
        return coverageDetails;
    }

    public void setCoverageDetails(String coverageDetails) {
        if (coverageDetails == null || coverageDetails.isEmpty()) {
            throw new IllegalArgumentException("Coverage details cannot be empty.");
        }
        this.coverageDetails = coverageDetails;
    }

    // Methods to Manage Insurance Policies

    public void addInsurancePolicy(int insuranceID, int insuranceProviderID, int insurancePercentage) {
        Document doc = new Document("insuranceID", insuranceID)
                .append("insuranceProviderID", insuranceProviderID)
                .append("insurancePercentage", insurancePercentage);

        if (insuranceCollection.find(eq("insuranceID", insuranceID)).first() == null) {
            insuranceCollection.insertOne(doc);
            System.out.println("Insurance policy added: " + doc.toJson());
        } else {
            insuranceCollection.updateOne(eq("insuranceID", insuranceID), new Document("$set", doc));
            System.out.println("Insurance policy updated: " + doc.toJson());
        }

        // Verify insurance is saved
        Document savedInsurance = insuranceCollection.find(eq("insuranceID", insuranceID)).first();
        System.out.println("Saved insurance in database: " + (savedInsurance != null ? savedInsurance.toJson() : "null"));
    }


    public List<Document> viewAllInsurancePolicies() {
        List<Document> policies = new ArrayList<>();
        for (Document doc : insuranceCollection.find()) {
            policies.add(doc);
            System.out.println(doc.toJson());
        }
        return policies;
    }

    public void removeInsurancePolicy(int insuranceID) {
        if (insuranceCollection.find(eq("insuranceID", insuranceID)).first() != null) {
            insuranceCollection.deleteOne(eq("insuranceID", insuranceID));
            System.out.println("Insurance policy removed with ID: " + insuranceID);
        } else {
            System.out.println("No insurance policy found with ID: " + insuranceID);
        }
    }

    // Save InsuranceProvider to the database
    @Override
    public void saveToDatabase() {
        Document doc = new Document("id", getId())
                .append("fullName", getFullName())
                .append("email", getEmail())
                .append("password", getPassword()) // Store hashed passwords in real applications
                .append("phoneNumber", getPhoneNumber())
                .append("termsAndConditions", termsAndConditions)
                .append("companyName", companyName)
                .append("coverageDetails", coverageDetails);

        if (providerCollection.find(eq("id", getId())).first() == null) {
            providerCollection.insertOne(doc);
            System.out.println("InsuranceProvider saved to database: " + doc.toJson());
        } else {
            providerCollection.updateOne(eq("id", getId()), new Document("$set", doc));
            System.out.println("InsuranceProvider updated in database: " + doc.toJson());
        }
    }

    // Load an InsuranceProvider from the database
    public static InsuranceProvider loadFromDatabase(int id) {
        Document doc = providerCollection.find(eq("id", id)).first();
        if (doc != null) {
            return new InsuranceProvider(
                    doc.getInteger("id"),
                    doc.getString("fullName"),
                    doc.getString("email"),
                    doc.getString("password"),
                    doc.getLong("phoneNumber"),
                    doc.getString("termsAndConditions"),
                    doc.getString("companyName"),
                    doc.getString("coverageDetails")
            );
        }
        System.out.println("InsuranceProvider with ID " + id + " not found in the database.");
        return null;
    }

    // Delete an InsuranceProvider from the database
    @Override
    public void deleteFromDatabase() {
        providerCollection.deleteOne(eq("id", getId()));
        System.out.println("InsuranceProvider with ID " + getId() + " deleted from the database.");
    }

    @Override
    public String toString() {
        return "InsuranceProvider{" +
                "id=" + getId() +
                ", fullName='" + getFullName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", phoneNumber=" + getPhoneNumber() +
                ", termsAndConditions='" + termsAndConditions + '\'' +
                ", companyName='" + companyName + '\'' +
                ", coverageDetails='" + coverageDetails + '\'' +
                '}';
    }
}
