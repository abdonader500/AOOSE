package entities.abstraction;


public class Item {
    private long itemID;
    private String name;
    private String category;
    private double price;
    private String description;
    private int quantity;

    // Constructor
    public Item(long itemID, String name, double price, int quantity) {
        this.itemID = itemID;
        this.name = name;
        this.category = category;
        this.price = price;
        this.description = description;
        this.quantity = quantity;
    }


    // setters and getters

    public void setItemID(long itemID) {
        this.itemID = itemID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getItemID() {
        return itemID;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() { return description; }

    public int getQuantity() { return quantity;}


    public void setQuantity(int quantity) { this.quantity = quantity;}

    // Display item details
    public void displayDetails() {
        System.out.println("Item ID: " + itemID);
        System.out.println("Name: " + name);
        System.out.println("Category: " + category);
        System.out.println("Price: $" + price);
        System.out.println("Description: " + description);
    }
}
