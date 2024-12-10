package test;

import entities.others.Insurance;
import entities.actors.InsuranceProvider;
import entities.actors.Patient;

import java.util.ArrayList;
import java.util.List;

public class TestInsuranceCoverage {
    public static void main(String[] args) {
        // Step 1: Create an InsuranceProvider
        InsuranceProvider provider = new InsuranceProvider(
                "Terms and Conditions: Covers up to 80% of expenses.",
                "HealthCare Inc.",
                "Medical and Surgical Coverage"
        );

        // Step 2: Create Insurance policies and add to the provider
        List<Insurance> insuranceTiers = new ArrayList<>();
        Insurance insurance1 = new Insurance(
                101, // Provider ID
                202, // Insurance ID
                insuranceTiers, // Tier list (empty here)
                80, // Coverage percentage
                1001 // Patient ID
        );

        Insurance insurance2 = new Insurance(
                101, // Provider ID
                203, // Insurance ID
                insuranceTiers, // Tier list (empty here)
                60, // Coverage percentage
                1002 // Patient ID
        );

        // Add insurances to the provider (Composition)
        provider.addInsurance(insurance1);
        provider.addInsurance(insurance2);

        // Step 3: Create Patients and assign insurances (Aggregation)
        Patient patient1 = new Patient(
                1001,
                "Alice Smith",
                "alice.smith@example.com",
                "password123",
                9876543210L,
                "123 Maple Street",
                "Female",
                28,
                "1234567890",
                "Fever",
                "Gold"
        );
        patient1.setInsurance(insurance1);

        Patient patient2 = new Patient(
                1002,
                "Bob Johnson",
                "bob.johnson@example.com",
                "password123",
                9876543211L,
                "456 Oak Avenue",
                "Male",
                35,
                "0987654321",
                "Back Pain",
                "Silver"
        );
        patient2.setInsurance(insurance2);

        // Step 4: Test coverage calculation for both patients
        double billAmount = 1000.0;

        System.out.println("Testing Insurance Coverage:");
        System.out.println("----------------------------");

        // Patient 1
        System.out.println("Patient 1 Details:");
        patient1.displayPatientDetails();
        double coverage1 = patient1.getInsurance().calculateCoverage(billAmount);
        System.out.println("Total Bill: $" + billAmount);
        System.out.println("Coverage Amount: $" + coverage1);
        System.out.println("Patient Pays: $" + (billAmount - coverage1));
        System.out.println();

        // Patient 2
        System.out.println("Patient 2 Details:");
        patient2.displayPatientDetails();
        double coverage2 = patient2.getInsurance().calculateCoverage(billAmount);
        System.out.println("Total Bill: $" + billAmount);
        System.out.println("Coverage Amount: $" + coverage2);
        System.out.println("Patient Pays: $" + (billAmount - coverage2));
        System.out.println();

        // Step 5: Display all insurances under the provider
        System.out.println("All Insurances under the Provider:");
        provider.displayInsurances();
    }
}
