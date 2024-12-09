package entities.classes;

public class PromotionInstance {
    private long instanceID; // Unique identifier for this promotion instance
    private Promotion promotionRef; // Reference to the abstract promotion
    private String startDate; // Specific start date for this instance
    private String endDate; // Specific end date for this instance

    // Constructor
    public PromotionInstance(long instanceID, Promotion promotionRef, String startDate, String endDate) {
        this.instanceID = instanceID;
        this.promotionRef = promotionRef;
        this.startDate = startDate;
        this.endDate = endDate;
    }


    // setters and getters

    public void setInstanceID(long instanceID) {
        this.instanceID = instanceID;
    }

    public void setPromotionRef(Promotion promotionRef) {
        this.promotionRef = promotionRef;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public long getInstanceID() {
        return instanceID;
    }

    public Promotion getPromotionRef() {
        return promotionRef;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    // Method to check if this instance is within its specific validity period
    public boolean isInstanceActive(String currentDate) {
        // Compare currentDate with startDate and endDate
        return currentDate.compareTo(startDate) >= 0 && currentDate.compareTo(endDate) <= 0;
    }
}
