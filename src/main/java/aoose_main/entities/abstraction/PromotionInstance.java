package aoose_main.entities.abstraction;

import java.util.Date;

public class PromotionInstance {
    private long instanceID; // Unique identifier for this promotion instance
    private Promotion promotionRef; // Reference to the abstract promotion
    private Date startDate; // Specific start date for this instance
    private Date endDate; // Specific end date for this instance

    // Constructor
    public PromotionInstance(long instanceID, Promotion promotionRef, Date startDate, Date endDate) {
        this.instanceID = instanceID;
        this.promotionRef = promotionRef;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Getters and setters
    public long getInstanceID() {
        return instanceID;
    }

    public void setInstanceID(long instanceID) {
        this.instanceID = instanceID;
    }

    public Promotion getPromotionRef() {
        return promotionRef;
    }

    public void setPromotionRef(Promotion promotionRef) {
        this.promotionRef = promotionRef;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    // Method to check if this instance is active
    public boolean isActive(Date currentDate) {
        return currentDate.after(startDate) && currentDate.before(endDate);
    }
}
