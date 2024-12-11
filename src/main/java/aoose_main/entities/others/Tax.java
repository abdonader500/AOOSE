package aoose_main.entities.others;

public class Tax implements BillDecorator {
    private Bill bill;
    private double taxRate;

    public Tax(Bill bill, double taxRate) {
        this.bill = bill;
        this.taxRate = taxRate;
    }

    @Override
    public void getDetails() {
        bill.getDetails();
        double total = bill.calculateTotal();
        double taxAmount = total * taxRate / 100;
        System.out.println("Tax: $" + taxAmount);
        System.out.println("Total with Tax: $" + (total + taxAmount));
    }
}
