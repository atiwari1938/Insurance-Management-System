package mainmod;

import dao.InsuranceServiceImpl;
import entity.Policy;
import myexceptions.PolicyNotFoundException;
import util.DBConnUtil;
import util.DBPropertyUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Scanner;

public class MainModule {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try (Connection connection = DBPropertyUtil()) {
            InsuranceServiceImpl insuranceService = new InsuranceServiceImpl(connection);

            // Display menu
            while (true) {
                displayPolicyMenu();

                // Get user choice
                int choice = getUserChoice();

                try {
                    // Perform action based on user choice
                    switch (choice) {
                        case 1:
                            createPolicy(insuranceService);
                            break;
                        case 2:
                            getPolicy(insuranceService);
                            break;
                        case 3:
                            getAllPolicies(insuranceService);
                            break;
                        case 4:
                            updatePolicy(insuranceService);
                            break;
                        case 5:
                            deletePolicy(insuranceService);
                            break;
                        case 6:
                            System.out.println("Thankyou for using IMS.");
                            return;
                        default:
                            System.out.println("Invalid option. Please try again.");
                    }
                }
                 catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (SQLException e) {
            System.err.println("Failed to establish a database connection: " + e.getMessage());
        }
    }

    private static Connection DBPropertyUtil() {
		// TODO Auto-generated method stub
		return null;
	}

	private static void displayPolicyMenu() {
        System.out.println("\n*** Policy Menu ***");
        System.out.println("1. Create Policy");
        System.out.println("2. Get Policy");
        System.out.println("3. Get All Policies");
        System.out.println("4. Update Policy");
        System.out.println("5. Delete Policy");
        System.out.println("6. Exit");
        System.out.print("Enter your choice: ");
    }

    private static int getUserChoice() {
        return scanner.nextInt();
    }

    private static void createPolicy(InsuranceServiceImpl insuranceService) {
        // Creating a new policy
        System.out.println("Enter Policy ID:");
        int policyId = scanner.nextInt();
        System.out.println("Enter Policy Name:");
        scanner.nextLine(); 
        String policyName = scanner.nextLine();
        System.out.println("Enter Policy Type:");
        String policyType = scanner.nextLine();
        System.out.println("Enter Coverage Amount");
        double CoverageAmount = scanner.nextDouble();

        Policy newPolicy = new Policy(policyId, policyName, policyType, CoverageAmount);

        if (insuranceService.createPolicy(newPolicy)) {
            System.out.println("Policy created successfully!");
        } else {
            System.out.println("Policy creation failed!");
        }
    }

    private static void getPolicy(InsuranceServiceImpl insuranceService) throws PolicyNotFoundException {
        // Retrieving a policy
        System.out.println("Enter Policy ID to retrieve:");
        int policyIdToRetrieve = scanner.nextInt();
        Policy retrievedPolicy = insuranceService.getPolicy(policyIdToRetrieve);

        if (retrievedPolicy != null) {
            System.out.println("Retrieved Policy: " + retrievedPolicy);
        } else {
            System.out.println("Policy with ID " + policyIdToRetrieve + " not found.");
        }
    }

    private static void getAllPolicies(InsuranceServiceImpl insuranceService) throws PolicyNotFoundException {
        // Retrieving all policies
        Collection<Policy> allPolicies = insuranceService.getAllPolicies();

        if (allPolicies != null && !allPolicies.isEmpty()) {
            System.out.println("All Policies:");
            for (Policy policy : allPolicies) {
                System.out.println(policy);
            }
        } else {
            System.out.println("No policies found.");
        }
    }

    private static void updatePolicy(InsuranceServiceImpl insuranceService) throws PolicyNotFoundException {
        // Updating a policy
        System.out.println("Enter Policy ID to update:");
        int policyIdToUpdate = scanner.nextInt();
        Policy policyToUpdate = insuranceService.getPolicy(policyIdToUpdate);

        if (policyToUpdate != null) {
            System.out.println("Enter updated Policy Name:");
            scanner.nextLine(); // Consume the newline character
            String updatedPolicyName = scanner.nextLine();
            policyToUpdate.setPolicyName(updatedPolicyName);
            System.out.println("Enter Updated policy type");
            String updatedPolicyType= scanner.nextLine();
            policyToUpdate.setPolicyType(updatedPolicyType);
            System.out.println("Enter updated Coverage Amount:");
            double updatedCoverageAmount = scanner.nextDouble();
            policyToUpdate.setCoverageAmount(updatedCoverageAmount);

            if (insuranceService.updatePolicy(policyToUpdate)) {
                System.out.println("Policy updated successfully!");
            } else {
                System.out.println("Policy update failed!");
            }
        } else {
            throw new PolicyNotFoundException("Policy with ID " + policyIdToUpdate + " not found.");
        }
    }

    private static void deletePolicy(InsuranceServiceImpl insuranceService) throws PolicyNotFoundException {
        // Deleting a policy
        System.out.println("Enter Policy ID to delete:");
        int policyIdToDelete = scanner.nextInt();

        if (insuranceService.deletePolicy(policyIdToDelete)) {
            System.out.println("Policy deleted successfully!");
        } else {
            System.out.println("Policy with ID " + policyIdToDelete + " not found.");
        }
    }
}
