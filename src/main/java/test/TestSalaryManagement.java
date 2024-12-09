package test;

import entities.Admin;
import entities.Cashier;
import entities.Pharmacist;
import enums.AccessLevels;
import enums.Shifts;

public class TestSalaryManagement {
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

        // Create a Pharmacist
        Pharmacist pharmacist = new Pharmacist(
                3,
                "Dr. Sarah Connor",
                "sarah.connor@example.com",
                "securepassword",
                1234567890L,
                "PH123456",
                Shifts.Morning,
                4000,
                "Oncology"
        );

        // Display initial salaries
        System.out.println("Initial salary for Cashier: $" + cashier.getSalary());
        System.out.println("Initial salary for Pharmacist: $" + pharmacist.getSalary());

        // Admin updates the salaries
        admin.setEmployeeSalary(cashier, 3000);
        admin.setEmployeeSalary(pharmacist, 4500);

        // Display updated salaries
        System.out.println("Updated salary for Cashier: $" + cashier.getSalary());
        System.out.println("Updated salary for Pharmacist: $" + pharmacist.getSalary());
    }
}
