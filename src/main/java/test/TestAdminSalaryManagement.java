package test;

import entities.Admin;
import entities.Cashier;
import enums.AccessLevels;
import enums.Shifts;
import enums.Shifts;

public class TestAdminSalaryManagement {
    public static void main(String[] args) {
        // Create an Admin
        Admin admin = new Admin(
                1,
                "Alice Smith",
                "admin@example.com",
                "securepassword",
                1234567890L,
                8000,
                AccessLevels.Full
        );

        // Create a Cashier
        Cashier cashier = new Cashier(
                2,
                "Bob Johnson",
                "bob.johnson@example.com",
                "cashierpassword",
                9876543210L,
                1002L,
                Shifts.Morning,
                2500
        );

        // Display initial salary
        System.out.println("Initial salary for " + cashier.getFullName() + ": $" + cashier.getSalary());

        // Admin updates the cashier's salary
        admin.setEmployeeSalary(cashier, 3000);

        // Display updated salary
        System.out.println("Updated salary for " + cashier.getFullName() + ": $" + cashier.getSalary());
    }
}
