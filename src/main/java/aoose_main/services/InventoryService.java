package aoose_main.services;

import aoose_main.dao.InventoryDAO;
import aoose_main.remotePattern.InventoryDTO;

import java.util.List;

public class InventoryService {
    private InventoryDAO inventoryDAO;

    public InventoryService(InventoryDAO inventoryDAO) {
        this.inventoryDAO = inventoryDAO;
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
    }

    // Update an existing inventory item
    public void updateInventoryItem(InventoryDTO item) {
        if (item == null || item.getItemId() <= 0) {
            throw new IllegalArgumentException("Invalid item data for update");
        }
        inventoryDAO.updateItem(item);
    }

    // Delete an item by ID
    public void deleteInventoryItem(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID must be positive");
        }
        inventoryDAO.deleteItem(id);
    }

    // List all items in the inventory
    public List<InventoryDTO> getAllInventoryItems() {
        return inventoryDAO.getAllItems();
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
