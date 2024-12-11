package aoose_main.entities.actors;

public class Supplier {
    private String companyName;
    private long supplierContact;
    private String companyAddress;

    // Constructor
    public Supplier(String companyName, long supplierContact, String companyAddress) {
        this.companyName = companyName;
        this.supplierContact = supplierContact;
        this.companyAddress = companyAddress;
    }

    // setters and getters
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setSupplierContact(long supplierContact) {
        this.supplierContact = supplierContact;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanyName() {
        return companyName;
    }

    public long getSupplierContact() {
        return supplierContact;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }


    // Methods
    public void deliverItems() {
        System.out.println("Delivering items to the inventory...");
        // Logic for delivering items
    }

    public void addItems() {
        System.out.println("Adding items to the inventory...");
        // Logic for adding items
    }

    public void updateItems() {
        System.out.println("Updating items in the inventory...");
        // Logic for updating items
    }

    public void removeItems() {
        System.out.println("Removing items from the inventory...");
        // Logic for removing items
    }
}
