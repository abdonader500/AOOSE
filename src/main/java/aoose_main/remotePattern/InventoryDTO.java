package aoose_main.remotePattern;

import java.io.Serializable;

public class InventoryDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private final int itemID;  // Add itemID
    private final String itemName;
    private final double price;
    private final int quantity;

    // Constructor
    public InventoryDTO(int itemID, String itemName, double price, int quantity) {
        if (itemName == null || itemName.isEmpty()) {
            throw new IllegalArgumentException("Item name cannot be null or empty.");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative.");
        }

        this.itemID = itemID;
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }

    // Getters
    public int getItemID() {
        return itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "InventoryDTO{" +
                "itemID=" + itemID +
                ", itemName='" + itemName + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
