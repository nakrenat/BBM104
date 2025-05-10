import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

/**
 * Main class to handle the reading of account and transaction data,
 * process transactions, and print account summaries.
 */
public class Main {

    /**
     * The entry point of the program. Reads account and transaction data from files,
     * processes transactions, and prints the account details and transaction history.
     *
     * @param args Command-line arguments containing the paths to the accounts and transactions files.
     */
    public static void main(String[] args) {
        // Account and transaction data
        if (args.length < 2) {
            System.out.println("Usage: java Main <accountsFile> <transactionsFile>");
            return; // Exit if insufficient arguments
        }

        // Map to store accounts by their account ID
        Map<String, BankAccount> accounts = new HashMap<>();

        // Read accounts from accounts.txt
        try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String accountID = parts[0];
                String accountType = parts[1];
                double balance = Double.parseDouble(parts[2]);

                // Process different account types
                switch (accountType.toLowerCase()) {
                    case "current":
                        double overdraftLimit = Double.parseDouble(parts[3]);
                        accounts.put(accountID, new CurrentAccount(accountID, balance, overdraftLimit));
                        break;

                    case "saving":
                        double interestRate = Double.parseDouble(parts[3]);
                        double minBalance = Double.parseDouble(parts[4]);
                        accounts.put(accountID, new SavingsAccount(accountID, balance, interestRate, minBalance));
                        break;

                    case "deposit":
                        interestRate = Double.parseDouble(parts[3]);
                        int termInMonths = Integer.parseInt(parts[4]);
                        double penalty = Double.parseDouble(parts[5]);
                        LocalDate startDate = LocalDate.parse(parts[6]);
                        accounts.put(accountID, new FixedDepositAccount(accountID, balance, interestRate, termInMonths, penalty, startDate));
                        break;

                    default:
                        System.out.println("Unknown account type: " + accountType);
                        break;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading accounts.txt: " + e.getMessage());
        }

        // Read transactions from transactions.txt
        try (BufferedReader br = new BufferedReader(new FileReader(args[1]))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String senderID = parts[0];
                double amount = Double.parseDouble(parts[1]);
                String receiverID = parts[2];

                // Process the transaction
                try {
                    BankAccount sender = accounts.get(senderID);
                    BankAccount receiver = accounts.get(receiverID);

                    if (sender != null && receiver != null) {
                        // Withdraw and deposit amounts, record the transactions
                        sender.withdraw(amount); // Withdraw total amount including penalty
                        sender.recordTransaction(senderID, receiverID, -amount);
                        receiver.recordTransaction(senderID, receiverID, amount);
                        receiver.deposit(amount); // Add to receiver's balance dynamically
                    } else {
                        System.out.println("Invalid transaction: Accounts not found.");
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading transactions.txt: " + e.getMessage());
        }

        // Print summaries of all accounts
        for (BankAccount account : accounts.values()) {
            if (account instanceof CurrentAccount) {
                ((CurrentAccount) account).printAccountDetails();
            } else if (account instanceof SavingsAccount) {
                ((SavingsAccount) account).printAccountDetails();
            } else if (account instanceof FixedDepositAccount) {
                ((FixedDepositAccount) account).printAccountDetails();
            }
        }
    }
}
