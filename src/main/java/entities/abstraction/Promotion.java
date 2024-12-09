package entities.abstraction;

import java.util.Date;

public class Promotion {
    // Attributes for the abstract concept of a promotion
    private String name;
    private String description;
    private int discountPercentage;
    private Date startDate;
    private Date endDate;

    // Constructor
    public Promotion(String name, String description, int discountPercentage, Date startDate, Date endDate) {
        this.name = name;
        this.description = description;
        this.discountPercentage = discountPercentage;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // setters and getters

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDiscountPercentage(int discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }


    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getDiscountPercentage() {
        return discountPercentage;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    // Method to check if the promotion is active
    public boolean isActive() {
        Date now = new Date();
        return now.after(startDate) && now.before(endDate);
    }
}
