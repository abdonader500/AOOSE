package aoose_main.test;

import aoose_main.entities.others.MakePayment;
import aoose_main.entities.others.PayVisa;
import aoose_main.entities.actors.Cashier;
import aoose_main.entities.others.PayCash;
import aoose_main.enums.Shifts;

public class TestStrategyPattern {
    public static void main(String[] args) {
        Cashier cashier = new Cashier(
                1,
                "John Doe",
                "john.doe@example.com",
                "password123",
                9876543210L,
                1001L,
                Shifts.Morning,
                3000
        );

        // Test Visa Payment
        MakePayment visaPayment = new PayVisa("John Doe", "1234-5678-9012-3456", "12/25", 123);
        cashier.setPaymentStrategy(visaPayment);
        System.out.println("Testing Visa Payment:");
        cashier.executePayment();

        // Test Cash Payment
        MakePayment cashPayment = new PayCash("John Doe", "john.doe@example.com");
        cashier.setPaymentStrategy(cashPayment);
        System.out.println("\nTesting Cash Payment:");
        cashier.executePayment();
    }
}