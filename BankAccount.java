/**
 * Represents a customer's bank account in the system.
 */
public class BankAccount {
    private String accountHolderName;
    private String accountNumber;
    private double balance;

    /**
     * Constructor to initialize the bank account with details.
     * 
     * @param accountHolderName The name of the account holder
     * @param accountNumber      The unique account number
     * @param initialBalance     The initial deposit amount
     */
    public BankAccount(String accountHolderName, String accountNumber, double initialBalance) {
        this.accountHolderName = accountHolderName;
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    /**
     * Deposits a positive amount into the account.
     * 
     * @param amount The amount to deposit
     * @return true if successful, false otherwise
     */
    public boolean deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Error: Deposit amount must be greater than zero.");
            return false;
        }
        balance += amount;
        return true;
    }

    /**
     * Withdraws a positive amount from the account if funds are sufficient.
     * 
     * @param amount The amount to withdraw
     * @return true if successful, false otherwise
     */
    public boolean withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Error: Withdrawal amount must be greater than zero.");
            return false;
        }
        if (amount > balance) {
            System.out.println("Error: Insufficient balance. Current balance is: " + balance);
            return false;
        }
        balance -= amount;
        return true;
    }

    /**
     * Displays details of the bank account.
     */
    public void displayDetails() {
        System.out.println("\n--- Account Details ---");
        System.out.println("Account Holder: " + accountHolderName);
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Current Balance: " + balance);
        System.out.println("-----------------------\n");
    }
}
