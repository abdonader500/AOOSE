package aoose_main.services;

import aoose_main.InventoryDAO;
import aoose_main.remotePattern.InventoryDTO;

import java.util.List;

public class InventoryService {
    private final InventoryDAO inventoryDAO;

    // Constructor
    public InventoryService(InventoryDAO inventoryDAO) {
        if (inventoryDAO == null) {
            throw new IllegalArgumentException("InventoryDAO cannot be null");
        }
        this.inventoryDAO = inventoryDAO;
    }

    // Retrieve an item by its name
    public InventoryDTO getItemByName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Item name cannot be null or blank");
        }
        List<InventoryDTO> items = inventoryDAO.searchItemsByName(name);
        if (items.isEmpty()) {
            throw new RuntimeException("No items found with name: " + name);
        }
        return items.get(0); // Assuming the first match is sufficient
    }

    // Retrieve an item by its ID
    public InventoryDTO getItemById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID must be positive");
        }
        InventoryDTO item = inventoryDAO.getItemById(id);
        if (item == null) {
            throw new RuntimeException("Item with ID " + id + " not found");
        }
        return item;
    }

    // Add a new item to the inventory
    public void addInventoryItem(InventoryDTO item) {
        if (item == null || item.getItemName() == null || item.getItemName().isEmpty()) {
            throw new IllegalArgumentException("Invalid item data");
        }
        inventoryDAO.createItem(item);
        System.out.println("Item added to inventory: " + item.getItemName());
    }

    // Update an existing inventory item
    public void updateInventoryItem(InventoryDTO item) {
        if (item == null || item.getItemName() == null || item.getItemName().isEmpty()) {
            throw new IllegalArgumentException("Invalid item data for update");
        }
        inventoryDAO.updateItem(item);
        System.out.println("Item updated in inventory: " + item.getItemName());
    }

    // Delete an item by ID
    public void deleteInventoryItem(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID must be positive");
        }
        inventoryDAO.deleteItemById(id);
        System.out.println("Item with ID " + id + " deleted from inventory");
    }

    // List all items in the inventory
    public List<InventoryDTO> getAllInventoryItems() {
        List<InventoryDTO> items = inventoryDAO.getAllItems();
        if (items.isEmpty()) {
            System.out.println("No items found in the inventory.");
        }
        return items;
    }

    // Search for items by name
    public List<InventoryDTO> searchItemsByName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Search name cannot be null or empty");
        }
        return inventoryDAO.searchItemsByName(name);
    }

    // Filter items by category
    public List<InventoryDTO> filterItemsByCategory(String category) {
        if (category == null || category.isEmpty()) {
            throw new IllegalArgumentException("Category cannot be null or empty");
        }
        return inventoryDAO.filterItemsByCategory(category);
    }
}
