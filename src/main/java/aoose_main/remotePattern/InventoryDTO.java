package aoose_main.remotePattern;

import java.io.Serializable;

public class InventoryDTO implements Serializable {
    private static final long serialVersionUID = 1L;  // Control the version

    private final int itemId;
    private final String itemName;
    private final double price;
    private final int quantity;

    // Constructor
    public InventoryDTO(int itemId, String itemName, double price, int quantity) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }

    // Getters only for immutability
    public int getItemId() {
        return itemId;
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
}
