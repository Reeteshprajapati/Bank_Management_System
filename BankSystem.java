import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Console-based client interface for the Bank Management System with persistent storage.
 */
public class BankSystem {
    private static final Map<String, BankAccount> accountsMap = new LinkedHashMap<>();
    private static BankAccount activeAccount = null;
    private static final Scanner scanner = new Scanner(System.in);
    private static final String FILE_NAME = "accounts.txt";

    public static void main(String[] args) {
        loadAccountsFromFile();

        int choice;
        do {
            printMenu();
            choice = getChoice();

            switch (choice) {
                case 1:
                    createAccount();
                    break;
                case 2:
                    selectActiveAccount();
                    break;
                case 3:
                    depositMoney();
                    break;
                case 4:
                    withdrawMoney();
                    break;
                case 5:
                    checkBalance();
                    break;
                case 6:
                    displayAccountDetails();
                    break;
                case 7:
                    displayAllAccounts();
                    break;
                case 8:
                    System.out.println("\nThank you for using Bank Management System. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice! Please select a valid option (1-8).");
            }
        } while (choice != 8);

        scanner.close();
    }

    /**
     * Prints the primary user menu options.
     */
    private static void printMenu() {
        System.out.println("\n===== BANK MANAGEMENT SYSTEM =====");
        if (activeAccount != null) {
            System.out.println("[Active Account: " + activeAccount.getAccountNumber() + " (" + activeAccount.getAccountHolderName() + ")]");
        } else {
            System.out.println("[Active Account: None selected]");
        }
        System.out.println("----------------------------------");
        System.out.println("1. Create Account");
        System.out.println("2. Select Active Account");
        System.out.println("3. Deposit Money");
        System.out.println("4. Withdraw Money");
        System.out.println("5. Check Balance");
        System.out.println("6. Display Active Account Details");
        System.out.println("7. Display All Stored Accounts");
        System.out.println("8. Exit");
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
            return -1;
        }
    }

    /**
     * Loads previous accounts from file into memory.
     */
    private static void loadAccountsFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                BankAccount account = BankAccount.fromFileString(line);
                if (account != null) {
                    accountsMap.put(account.getAccountNumber(), account);
                }
            }
            if (!accountsMap.isEmpty()) {
                System.out.println("Loaded " + accountsMap.size() + " previous account(s) from " + FILE_NAME);
                // Set the first loaded account as active by default if none selected
                activeAccount = accountsMap.values().iterator().next();
            }
        } catch (IOException e) {
            System.out.println("Warning: Could not read persistent account details from file: " + e.getMessage());
        }
    }

    /**
     * Saves all stored accounts from memory to file.
     */
    private static void saveAccountsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (BankAccount account : accountsMap.values()) {
                writer.write(account.toFileString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error: Could not save account details to file: " + e.getMessage());
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

        if (accountsMap.containsKey(accNum)) {
            System.out.println("Error: An account with Account Number '" + accNum + "' already exists.");
            return;
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

        BankAccount newAccount = new BankAccount(name, accNum, initialAmount);
        accountsMap.put(accNum, newAccount);
        activeAccount = newAccount;
        saveAccountsToFile();
        System.out.println("\nAccount Created Successfully and stored permanently!");
    }

    /**
     * Allows user to select an existing account to work with.
     */
    private static void selectActiveAccount() {
        if (accountsMap.isEmpty()) {
            System.out.println("\nNo accounts stored in the system yet. Please create an account first.");
            return;
        }

        System.out.println("\nAvailable Account Numbers:");
        for (String accNum : accountsMap.keySet()) {
            BankAccount acc = accountsMap.get(accNum);
            System.out.println("- " + accNum + " (" + acc.getAccountHolderName() + ")");
        }

        System.out.print("\nEnter Account Number to select: ");
        String accNum = scanner.nextLine().trim();
        if (accountsMap.containsKey(accNum)) {
            activeAccount = accountsMap.get(accNum);
            System.out.println("Successfully switched active account to: " + activeAccount.getAccountNumber());
        } else {
            System.out.println("Account Number not found.");
        }
    }

    /**
     * Checks if the active account exists, prompts for deposit amount, and updates account.
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
            saveAccountsToFile();
            System.out.println("\nAmount Deposited Successfully and updated in permanent storage!");
        }
    }

    /**
     * Checks if the active account exists, prompts for withdrawal amount, and updates account.
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
            saveAccountsToFile();
            System.out.println("\nAmount Withdrawn Successfully and updated in permanent storage!");
        }
    }

    /**
     * Displays the current active account balance.
     */
    private static void checkBalance() {
        if (checkNoAccount()) return;
        System.out.println("\nCurrent Balance for Account " + activeAccount.getAccountNumber() + ": " + activeAccount.getBalance());
    }

    /**
     * Displays details of the active account.
     */
    private static void displayAccountDetails() {
        if (checkNoAccount()) return;
        activeAccount.displayDetails();
    }

    /**
     * Displays details for all stored accounts.
     */
    private static void displayAllAccounts() {
        if (accountsMap.isEmpty()) {
            System.out.println("\nNo accounts stored in the system.");
            return;
        }
        System.out.println("\n================ ALL STORED ACCOUNTS ================");
        for (BankAccount acc : accountsMap.values()) {
            acc.displayDetails();
        }
    }

    /**
     * Helper to verify if an active account is selected.
     */
    private static boolean checkNoAccount() {
        if (activeAccount == null) {
            System.out.println("\nWarning: No active account selected. Please select or create an account first.");
            return true;
        }
        return false;
    }
}
