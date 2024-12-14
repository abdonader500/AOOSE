package aoose_main.entities.others;

public class Tax implements BillDecorator {
    private Bill bill; // The bill being decorated
    private double taxRate; // The tax rate applied to the bill

    // Constructor
    public Tax(Bill bill, double taxRate) {
        if (bill == null) {
            throw new IllegalArgumentException("Bill cannot be null.");
        }
        if (taxRate < 0) {
            throw new IllegalArgumentException("Tax rate cannot be negative.");
        }
        this.bill = bill;
        this.taxRate = taxRate;
    }

    // Get the details of the bill including tax
    @Override
    public void getDetails() {
        bill.getDetails(); // Display the original bill details
        double total = bill.calculateTotal();
        double taxAmount = total * taxRate / 100; // Calculate the tax amount
        System.out.println("Tax Rate: " + taxRate + "%");
        System.out.println("Tax Amount: $" + taxAmount);
        System.out.println("Total with Tax: $" + (total + taxAmount));
    }

    // Getters and setters for flexibility
    public double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(double taxRate) {
        if (taxRate < 0) {
            throw new IllegalArgumentException("Tax rate cannot be negative.");
        }
        this.taxRate = taxRate;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        if (bill == null) {
            throw new IllegalArgumentException("Bill cannot be null.");
        }
        this.bill = bill;
    }
}
