package aoose_main.entities.others;

public class PayCash implements MakePayment {
    private String name;
    private String email;

    public PayCash(String name, String email) {
        this.name = name;
        this.email = email;
    }

    @Override
    public void processPayment() {
        System.out.println("Processing payment using cash:");
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
    }
}
