package entities.abstraction;

public class ItemInstance {
    // Attributes
    private long instanceID; // Unique identifier for the instance
    private Item itemRef; // Reference to the abstract item
    private int quantity; // Quantity in stock
    private String expirationDate; // Expiration date (if applicable)

    // Constructor
    public ItemInstance(long instanceID, Item itemRef, int quantity, String expirationDate) {
        this.instanceID = instanceID;
        this.itemRef = itemRef;
        this.quantity = quantity;
        this.expirationDate = expirationDate;
    }


    // setters and getters
    public void setInstanceID(long instanceID) {
        this.instanceID = instanceID;
    }

    public void setItemRef(Item itemRef) {
        this.itemRef = itemRef;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public long getInstanceID() {
        return instanceID;
    }

    public Item getItemRef() {
        return itemRef;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    // Method to check if the item instance is still valid
    public boolean isExpired(String currentDate) {
        return expirationDate.compareTo(currentDate) < 0;
    }

    // Display instance details
    public void displayInstanceDetails() {
        System.out.println("Instance ID: " + instanceID);
        System.out.println("Quantity: " + quantity);
        System.out.println("Expiration Date: " + expirationDate);
        System.out.println("Item Details:");
        itemRef.displayDetails();
    }
}
