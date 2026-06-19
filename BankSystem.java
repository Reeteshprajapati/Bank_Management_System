import java.util.Scanner;

/**
 * Console-based client interface for the Bank Management System.
 */
public class BankSystem {
    private static BankAccount activeAccount = null;
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;
        do {
            printMenu();
            choice = getChoice();

            switch (choice) {
                case 1:
                    createAccount();
                    break;
                case 2:
                    depositMoney();
                    break;
                case 3:
                    withdrawMoney();
                    break;
                case 4:
                    checkBalance();
                    break;
                case 5:
                    displayAccountDetails();
                    break;
                case 6:
                    System.out.println("\nThank you for using Bank Management System. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice! Please select a valid option (1-6).");
            }
        } while (choice != 6);

        scanner.close();
    }

    /**
     * Prints the primary user menu options.
     */
    private static void printMenu() {
        System.out.println("\n===== BANK MANAGEMENT SYSTEM =====");
        System.out.println("1. Create Account");
        System.out.println("2. Deposit Money");
        System.out.println("3. Withdraw Money");
        System.out.println("4. Check Balance");
        System.out.println("5. Display Account Details");
        System.out.println("6. Exit");
        System.out.print("\nEnter Your Choice: ");
    }

    /**
     * Safely reads an integer option from the user.
     */
    private static int getChoice() {
        try {
            String input = scanner.nextLine().trim();
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1; // Return an invalid option code to trigger default switch case
        }
    }

    /**
     * Guides the user to create a new Bank Account.
     */
    private static void createAccount() {
        System.out.print("\nEnter Name: ");
        String name = scanner.nextLine().trim();
        while (name.isEmpty()) {
            System.out.print("Name cannot be empty. Please enter Name: ");
            name = scanner.nextLine().trim();
        }

        System.out.print("Enter Account Number: ");
        String accNum = scanner.nextLine().trim();
        while (accNum.isEmpty()) {
            System.out.print("Account Number cannot be empty. Please enter Account Number: ");
            accNum = scanner.nextLine().trim();
        }

        double initialAmount = -1;
        while (initialAmount < 0) {
            System.out.print("Enter Initial Amount: ");
            try {
                String input = scanner.nextLine().trim();
                initialAmount = Double.parseDouble(input);
                if (initialAmount < 0) {
                    System.out.println("Initial amount cannot be negative.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid decimal number.");
            }
        }

        activeAccount = new BankAccount(name, accNum, initialAmount);
        System.out.println("\nAccount Created Successfully!");
    }

    /**
     * Checks if the account exists, prompts for deposit amount, and updates account.
     */
    private static void depositMoney() {
        if (checkNoAccount()) return;

        double amount = -1;
        while (amount <= 0) {
            System.out.print("Enter Deposit Amount: ");
            try {
                String input = scanner.nextLine().trim();
                amount = Double.parseDouble(input);
                if (amount <= 0) {
                    System.out.println("Deposit amount must be greater than zero.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid decimal number.");
            }
        }

        if (activeAccount.deposit(amount)) {
            System.out.println("\nAmount Deposited Successfully!");
        }
    }

    /**
     * Checks if the account exists, prompts for withdrawal amount, and updates account.
     */
    private static void withdrawMoney() {
        if (checkNoAccount()) return;

        double amount = -1;
        while (amount <= 0) {
            System.out.print("Enter Withdrawal Amount: ");
            try {
                String input = scanner.nextLine().trim();
                amount = Double.parseDouble(input);
                if (amount <= 0) {
                    System.out.println("Withdrawal amount must be greater than zero.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid decimal number.");
            }
        }

        if (activeAccount.withdraw(amount)) {
            System.out.println("\nAmount Withdrawn Successfully!");
        }
    }

    /**
     * Displays the current account balance.
     */
    private static void checkBalance() {
        if (checkNoAccount()) return;
        System.out.println("\nCurrent Balance: " + activeAccount.getBalance());
    }

    /**
     * Displays details of the current account.
     */
    private static void displayAccountDetails() {
        if (checkNoAccount()) return;
        activeAccount.displayDetails();
    }

    /**
     * Helper to verify if an account is created.
     * Prints a warning and returns true if no account exists.
     */
    private static boolean checkNoAccount() {
        if (activeAccount == null) {
            System.out.println("\nWarning: No active account found. Please create an account first (Option 1).");
            return true;
        }
        return false;
    }
}
