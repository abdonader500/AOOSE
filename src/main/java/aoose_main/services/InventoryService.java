package aoose_main.services;

import aoose_main.dao.InventoryDAO;
import aoose_main.remotePattern.InventoryDTO;

public class InventoryService {
    private InventoryDAO inventoryDAO;

    public InventoryService(InventoryDAO inventoryDAO) {
        this.inventoryDAO = inventoryDAO;
    }

    public InventoryDTO getItemById(int id) {
        return inventoryDAO.getItemById(id);
    }

    public void updateInventoryItem(InventoryDTO item) {
        inventoryDAO.updateItem(item);
    }
    // Additional methods as necessary
}
