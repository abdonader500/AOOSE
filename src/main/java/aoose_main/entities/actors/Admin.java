package aoose_main.entities.actors;

<<<<<<< HEAD
=======

import aoose_main.entities.abstraction.Promotion;
import aoose_main.entities.abstraction.PromotionInstance;
import aoose_main.entities.others.Inquiry;
>>>>>>> parent of 0f22ea2 (my commit)
import aoose_main.entities.others.Insurance;
import aoose_main.enums.AccessLevels;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class Admin extends User {
    private int salary;
    private AccessLevels accessLevel;

    // Constructor
    public Admin(int id, String fullName, String email, String password, long phoneNumber, int salary, AccessLevels accessLevel) {
        super(id, fullName, email, password, phoneNumber);
        this.salary = salary;
        this.accessLevel = accessLevel;
    }

    // Setters and Getters
    public void setSalary(int salary) {
        this.salary = salary;
    }

    public void setAccessLevel(AccessLevels accessLevel) {
        this.accessLevel = accessLevel;
    }

    public int getSalary() {
        return salary;
    }

    public AccessLevels getAccessLevel() {
        return accessLevel;
    }

    // Methods to Manage Users

    public void addPatient(Patient patient, MongoDatabase database) {
        MongoCollection<Document> collection = database.getCollection("patients");
        Document doc = new Document("id", patient.getId())
                .append("fullName", patient.getFullName())
                .append("email", patient.getEmail())
                .append("password", patient.getPassword())
                .append("phoneNumber", patient.getPhoneNumber())
                .append("address", patient.getAddress())
                .append("loyaltyDiscount", patient.getLoyaltyDiscount())
                .append("age", patient.getAge())
                .append("gender", patient.getGender())
                .append("emergencyContact", patient.getEmergencyContact())
                .append("issue", patient.getIssue());

        // Add Insurance to the patient document
        Insurance insurance = patient.getInsurance();
        if (insurance != null) {
            doc.append("insurance", insurance.toDocument());
        }

        // Save or update the patient in the database
        if (collection.find(eq("id", patient.getId())).first() == null) {
            collection.insertOne(doc);
            System.out.println("Patient added: " + patient.getFullName());
        } else {
            collection.updateOne(eq("id", patient.getId()), new Document("$set", doc));
            System.out.println("Patient updated: " + patient.getFullName());
        }
    }




    public void addPharmacist(Pharmacist pharmacist, MongoDatabase database) {
        MongoCollection<Document> collection = database.getCollection("pharmacists");
        Document doc = new Document("id", pharmacist.getId())
                .append("fullName", pharmacist.getFullName())
                .append("email", pharmacist.getEmail())
                .append("password", pharmacist.getPassword())
                .append("phoneNumber", pharmacist.getPhoneNumber())
                .append("salary", pharmacist.getSalary())
                .append("licenseNumber", pharmacist.getLicenseNumber())
                .append("specialization", pharmacist.getSpecialization());

        saveOrUpdateUser(collection, doc);
    }
    public void addInsuranceProvider(InsuranceProvider insuranceProvider, MongoDatabase database) {
        MongoCollection<Document> collection = database.getCollection("insurance_providers");
        Document doc = new Document("id", insuranceProvider.getId())
                .append("fullName", insuranceProvider.getFullName())
                .append("email", insuranceProvider.getEmail())
                .append("password", insuranceProvider.getPassword())
                .append("phoneNumber", insuranceProvider.getPhoneNumber())
                .append("termsAndConditions", insuranceProvider.getTermsAndConditions())
                .append("companyName", insuranceProvider.getCompanyName())
                .append("coverageRate", insuranceProvider.getCoverageDetails()); // Update with coverageRate field

        saveOrUpdateUser(collection, doc);
    }


    public void addInventoryClerk(InventoryClerk clerk, MongoDatabase database) {
        MongoCollection<Document> collection = database.getCollection("inventory_clerks");
        Document doc = new Document("id", clerk.getId())
                .append("fullName", clerk.getFullName())
                .append("email", clerk.getEmail())
                .append("password", clerk.getPassword())
                .append("phoneNumber", clerk.getPhoneNumber())
                .append("salary", clerk.getSalary())
                .append("department", clerk.getDepartment());

        saveOrUpdateUser(collection, doc);
    }

    public void addSupplier(Supplier supplier, MongoDatabase database) {
        MongoCollection<Document> collection = database.getCollection("suppliers");
        Document doc = new Document("id", supplier.getId())
                .append("fullName", supplier.getFullName())
                .append("email", supplier.getEmail())
                .append("password", supplier.getPassword())
                .append("phoneNumber", supplier.getPhoneNumber())
                .append("companyName", supplier.getCompanyName())
                .append("companyAddress", supplier.getCompanyAddress());

        saveOrUpdateUser(collection, doc);
    }

    public void addAdmin(Admin admin, MongoDatabase database) {
        MongoCollection<Document> collection = database.getCollection("admins");
        Document doc = new Document("id", admin.getId())
                .append("fullName", admin.getFullName())
                .append("email", admin.getEmail())
                .append("password", admin.getPassword())
                .append("phoneNumber", admin.getPhoneNumber())
                .append("salary", admin.getSalary())
                .append("accessLevel", admin.getAccessLevel().toString());

        saveOrUpdateUser(collection, doc);
    }

    public void addCashier(Cashier cashier, MongoDatabase database) {
        MongoCollection<Document> collection = database.getCollection("cashiers");
        Document doc = new Document("id", cashier.getId())
                .append("fullName", cashier.getFullName())
                .append("email", cashier.getEmail())
                .append("password", cashier.getPassword())
                .append("phoneNumber", cashier.getPhoneNumber())
                .append("salary", cashier.getSalary());

        saveOrUpdateUser(collection, doc);
    }

    private void saveOrUpdateUser(MongoCollection<Document> collection, Document doc) {
        if (collection.find(eq("id", doc.getInteger("id"))).first() == null) {
            collection.insertOne(doc);
            System.out.println("User added: " + doc.getString("fullName"));
        } else {
            collection.updateOne(eq("id", doc.getInteger("id")), new Document("$set", doc));
            System.out.println("User updated: " + doc.getString("fullName"));
        }
    }

    public void removeUser(int userId, MongoDatabase database, String userType) {
        MongoCollection<Document> collection = database.getCollection(userType);
        if (collection.find(eq("id", userId)).first() != null) {
            collection.deleteOne(eq("id", userId));
            System.out.println("User with ID " + userId + " removed from " + userType + " collection.");
        } else {
            System.out.println("User with ID " + userId + " not found in " + userType + " collection.");
        }
    }

    public void viewAllUsers(MongoDatabase database, String userType) {
        MongoCollection<Document> collection = database.getCollection(userType);
        for (Document doc : collection.find()) {
            System.out.println("ID: " + doc.getInteger("id"));
            System.out.println("Name: " + doc.getString("fullName"));
            System.out.println("Email: " + doc.getString("email"));
            System.out.println("Phone Number: " + doc.getLong("phoneNumber"));
            System.out.println("------------------------");
        }
    }
<<<<<<< HEAD
=======

    public void addPromotion(Promotion promotion, MongoDatabase database) {
        MongoCollection<Document> collection = database.getCollection("promotions");

        // Check for duplicate promotion name
        if (collection.find(eq("name", promotion.getName())).first() != null) {
            System.out.println("Error: A promotion with the same name already exists.");
            return;
        }

        Document doc = new Document("name", promotion.getName())
                .append("description", promotion.getDescription())
                .append("discountPercentage", promotion.getDiscountPercentage());

        collection.insertOne(doc);
        System.out.println("Promotion added: " + promotion.getName());
    }

    public void addPromotionInstance(PromotionInstance promotionInstance, MongoDatabase database) {
        MongoCollection<Document> collection = database.getCollection("promotion_instances");

        // Check for duplicate promotion instance ID
        if (collection.find(eq("instanceID", promotionInstance.getInstanceID())).first() != null) {
            System.out.println("Error: A promotion instance with the same ID already exists.");
            return;
        }

        // Ensure the referenced promotion exists in the database
        MongoCollection<Document> promotionCollection = database.getCollection("promotions");
        Document promotionDoc = promotionCollection.find(eq("name", promotionInstance.getPromotionRef().getName())).first();
        if (promotionDoc == null) {
            System.out.println("Error: The referenced promotion does not exist.");
            return;
        }

        Document doc = new Document("instanceID", promotionInstance.getInstanceID())
                .append("promotionName", promotionInstance.getPromotionRef().getName())
                .append("startDate", promotionInstance.getStartDate())
                .append("endDate", promotionInstance.getEndDate());

        collection.insertOne(doc);
        System.out.println("Promotion instance added with ID: " + promotionInstance.getInstanceID());
    }
    public void viewAllInquiries(MongoDatabase database) {
        List<Inquiry> inquiries = (List<Inquiry>) Inquiry.loadFromDatabase(database);
        if (inquiries.isEmpty()) {
            System.out.println("No inquiries found in the database.");
            return;
        }

        for (Inquiry inquiry : inquiries) {
            inquiry.displayInquiryDetails();
            System.out.println("-------------------------");
        }
    }
>>>>>>> parent of 0f22ea2 (my commit)
}
