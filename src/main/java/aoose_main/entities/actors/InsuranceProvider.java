package aoose_main.entities.actors;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;

public class InsuranceProvider extends User {
    // Additional Attributes
    private String termsAndConditions;
    private String companyName;
    private String coverageDetails;

    private static MongoCollection<Document> collection; // Shared MongoDB collection for InsuranceProvider objects

    // Constructor
    public InsuranceProvider(int id, String fullName, String email, String password, long phoneNumber,
                             String termsAndConditions, String companyName, String coverageDetails) {
        super(id, fullName, email, password, phoneNumber);
        setTermsAndConditions(termsAndConditions);
        setCompanyName(companyName);
        setCoverageDetails(coverageDetails);
    }

    // Static method to initialize the MongoDB collection
    public static void initializeDatabase(MongoDatabase database) {
        collection = database.getCollection("insurance_providers");
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

    // Methods
    public void checkInsuranceForm(String formDetails) {
        // Simulate a database search for the insurance form
        Document form = collection.find(eq("formDetails", formDetails)).first();
        if (form != null) {
            System.out.println("Insurance form found and validated: " + form.toJson());
        } else {
            System.out.println("Insurance form not found for details: " + formDetails);
        }
    }

    public void approveInsurance(String insuranceId) {
        // Update the insurance status to 'approved' in the database
        Document updateDoc = new Document("$set", new Document("status", "approved"));
        collection.updateOne(eq("insuranceId", insuranceId), updateDoc);
        System.out.println("Insurance approved for ID: " + insuranceId);
    }

    public void rejectInsurance(String insuranceId) {
        // Update the insurance status to 'rejected' in the database
        Document updateDoc = new Document("$set", new Document("status", "rejected"));
        collection.updateOne(eq("insuranceId", insuranceId), updateDoc);
        System.out.println("Insurance rejected for ID: " + insuranceId);
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

        if (collection.find(eq("id", getId())).first() == null) {
            collection.insertOne(doc);
            System.out.println("InsuranceProvider saved to database: " + doc.toJson());
        } else {
            collection.updateOne(eq("id", getId()), new Document("$set", doc));
            System.out.println("InsuranceProvider updated in database: " + doc.toJson());
        }
    }

    // Load an InsuranceProvider from the database
    public static InsuranceProvider loadFromDatabase(int id) {
        Document doc = collection.find(eq("id", id)).first();
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
        collection.deleteOne(eq("id", getId()));
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
