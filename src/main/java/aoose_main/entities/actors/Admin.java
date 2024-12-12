package aoose_main.entities.actors;

import aoose_main.enums.AccessLevels;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

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

        saveOrUpdateUser(collection, doc);
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
        // Get the suppliers collection from the database
        MongoCollection<Document> collection = database.getCollection("suppliers");

        // Create a document representing the supplier
        Document doc = new Document("companyName", supplier.getCompanyName())
                .append("supplierContact", supplier.getSupplierContact())
                .append("companyAddress", supplier.getCompanyAddress());

        // Check if a supplier with the same company name already exists
        if (collection.find(eq("companyName", supplier.getCompanyName())).first() == null) {
            // Insert new supplier into the collection
            collection.insertOne(doc);
            System.out.println("Supplier added: " + supplier.getCompanyName());
        } else {
            // Update existing supplier with new data
            collection.updateOne(eq("companyName", supplier.getCompanyName()), new Document("$set", doc));
            System.out.println("Supplier updated: " + supplier.getCompanyName());
        }
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
}
