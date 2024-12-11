package aoose_main.entities.others;

public class PayVisa implements MakePayment {
    private String name;
    private String cardNumber;
    private String expireDate;
    private int cvv;

    public PayVisa(String name, String cardNumber, String expireDate, int cvv) {
        this.name = name;
        this.cardNumber = cardNumber;
        this.expireDate = expireDate;
        this.cvv = cvv;
    }

    @Override
    public void processPayment() {
        System.out.println("Processing payment using Visa:");
        System.out.println("Name: " + name);
        System.out.println("Card Number: " + cardNumber);
        System.out.println("Expiry Date: " + expireDate);
        System.out.println("CVV: " + cvv);
    }
}
