import java.time.temporal.ChronoUnit;
import java.time.LocalDate;
import java.util.*;
import java.time.format.DateTimeFormatter;

/**
 * Interface for operations on bank accounts.
 */
interface AccountOperations {
    /**
     * Withdraws a specified amount from the account.
     *
     * @param amount the amount to withdraw
     * @throws Exception if withdrawal exceeds balance or account-specific limits
     */
    void withdraw(double amount) throws Exception;

    /**
     * Deposits a specified amount into the account.
     *
     * @param amount the amount to deposit
     */
    void deposit(double amount);

    /**
     * Evaluates the risk associated with the account based on its balance and type.
     *
     * @return a risk evaluation string
     */
    String evaluateRisk();

    /**
     * Records a transaction involving this account.
     *
     * @param senderID the ID of the sender account
     * @param receiverID the ID of the receiver account
     * @param amount the transaction amount
     */
    void recordTransaction(String senderID, String receiverID, double amount);
}

/**
 * Abstract base class for bank accounts.
 */
abstract class BankAccount implements AccountOperations {
    protected String accountID;
    protected double balance;
    protected List<Transaction> transactionHistory;

    /**
     * Constructs a bank account with an ID and balance.
     *
     * @param accountID the unique identifier for the account
     * @param balance the initial balance of the account
     */
    public BankAccount(String accountID, double balance) {
        this.accountID = accountID;
        this.balance = balance;
        this.transactionHistory = new ArrayList<>();
    }

    /**
     * Gets the account ID.
     *
     * @return the account ID
     */
    public String getAccountID() {
        return accountID;
    }

    /**
     * Gets the account balance.
     *
     * @return the current balance
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Prints the transaction history for the account.
     */
    public void printTransactionHistory() {
        for (Transaction t : transactionHistory) {
            System.out.println(t);
        }
    }

    /**
     * Abstract method for printing account details, to be implemented by subclasses.
     */
    public abstract void printAccountDetails();
}

/**
 * Represents a current account, allowing overdrafts.
 */
class CurrentAccount extends BankAccount {
    private double overdraftLimit;

    /**
     * Constructs a CurrentAccount with specified account ID, balance, and overdraft limit.
     *
     * @param accountID the unique identifier for the account
     * @param balance the initial balance
     * @param overdraftLimit the overdraft limit for the account
     */
    public CurrentAccount(String accountID, double balance, double overdraftLimit) {
        super(accountID, balance);
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public void withdraw(double amount) throws Exception {
        if (balance - amount <= -overdraftLimit) {
            throw new Exception("Current Account: Amount exceeds overdraft limit. Amount: " + amount + " Balance: " + balance + " Limit: " + overdraftLimit);
        }
        balance -= amount;
    }

    @Override
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Invalid deposit amount.");
        }
        balance += amount;
    }

    @Override
    public String evaluateRisk() {
        return balance < 0 ? "Current Account-Medium Risk: Account is in overdraft." : "Current Account-Low Risk: Account is in stable.";
    }

    @Override
    public void recordTransaction(String senderID, String receiverID, double amount) {
        transactionHistory.add(new Transaction(senderID, receiverID, amount, LocalDate.now()));
    }

    @Override
    public void printAccountDetails() {
        System.out.println("****************** Summary for Account " + accountID + " ******************");
        printTransactionHistory();
        System.out.println("Account Info");
        System.out.println("Current Account - Account Number: " + accountID);
        System.out.println("Balance: $" + balance);
        System.out.println("Overdraft Limit: $" + overdraftLimit);
        System.out.println("Account Risk Evaluation");
        System.out.println(evaluateRisk());
        System.out.println("************************************************************************************************************");
    }
}

/**
 * Represents a savings account with an interest rate and a minimum balance.
 */
class SavingsAccount extends BankAccount {
    private double interestRate;
    private double minBalance;

    /**
     * Constructs a SavingsAccount with specified account ID, balance, interest rate, and minimum balance.
     *
     * @param accountID the unique identifier for the account
     * @param balance the initial balance
     * @param interestRate the interest rate applied to the balance
     * @param minBalance the minimum balance required to avoid penalties
     */
    public SavingsAccount(String accountID, double balance, double interestRate, double minBalance) {
        super(accountID, balance);
        this.interestRate = interestRate;
        this.minBalance = minBalance;
    }

    /**
     * Calculates the interest for the savings account based on the balance and interest rate.
     *
     * @return the calculated interest
     */
    public double calculateInterest() {
        return balance * interestRate;  // Interest = Balance × InterestRate
    }

    @Override
    public void withdraw(double amount) throws Exception {
        if (amount > (minBalance + balance)) {
            throw new Exception("Saving Account: Amount exceeds overdraft limit. Amount: " + amount + " Balance: " + minBalance);
        }
        balance -= amount;
        if (balance - amount < minBalance) {
            double shortfall = minBalance - (balance - amount); // Calculate shortfall
            double penalty = shortfall * 0.05; // 5% penalty
            balance -= (amount + penalty); // Deduct withdrawal and penalty
        } else {
            balance -= amount; // Regular withdrawal
        }
    }

    @Override
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Invalid deposit amount.");
        }
        balance += amount;
    }

    @Override
    public String evaluateRisk() {
        return balance < minBalance ? "Saving Account-Medium Risk: Balance is below minimum." : "Saving Account-Low Risk: Account is in stable.";
    }

    @Override
    public void recordTransaction(String senderID, String receiverID, double amount) {
        transactionHistory.add(new Transaction(senderID, receiverID, amount, LocalDate.now()));
    }

    @Override
    public void printAccountDetails() {
        System.out.println("****************** Summary for Account " + accountID + " ******************");
        printTransactionHistory();
        System.out.println("Account Info");
        System.out.println("Savings Account - Account Number: " + accountID);
        System.out.println("Balance: $" + balance);
        System.out.println("Interest Rate: " + (interestRate * 100) + "%");
        System.out.println("Account Risk Evaluation");
        System.out.println(evaluateRisk());
        System.out.println("************************************************************************************************************");
    }
}

/**
 * Represents a fixed deposit account with a specific term and penalty for early withdrawal.
 */
class FixedDepositAccount extends BankAccount {
    private double interestRate;
    private int termInMonths;
    private double penalty;
    private LocalDate startDate;

    /**
     * Constructs a FixedDepositAccount with specified account ID, balance, interest rate, term, penalty, and start date.
     *
     * @param accountID the unique identifier for the account
     * @param balance the initial balance
     * @param interestRate the interest rate applied to the balance
     * @param termInMonths the duration of the fixed deposit in months
     * @param penalty the penalty for early withdrawal
     * @param startDate the start date of the fixed deposit
     */
    public FixedDepositAccount(String accountID, double balance, double interestRate, int termInMonths, double penalty, LocalDate startDate) {
        super(accountID, balance);
        this.interestRate = interestRate;
        this.termInMonths = termInMonths;
        this.penalty = penalty;
        this.startDate = startDate;
    }

    /**
     * Calculates the interest for the fixed deposit account based on balance and days to maturity.
     *
     * @return the calculated interest
     */
    public double calculateInterest() {
        LocalDate maturityDate = startDate.plusMonths(termInMonths);
        long daysToMaturity = ChronoUnit.DAYS.between(LocalDate.now(), maturityDate);
        if (daysToMaturity > 0) {
            return balance * interestRate * daysToMaturity / 365;
        }
        return 0; // No interest if maturity has passed
    }

    @Override
    public void withdraw(double amount) throws Exception {
        LocalDate maturityDate = startDate.plusMonths(termInMonths);
        if (LocalDate.now().isBefore(maturityDate)) {
            double penaltyAmount = amount * penalty;
            double totalAmount = amount + penaltyAmount;
            if (totalAmount > balance) {
                throw new Exception("Fixed Deposit Account: Insufficient funds including penalty charges.");
            }
            balance -= totalAmount;
        } else {
            if (amount > balance) {
                throw new Exception("Fixed Deposit Account: Insufficient funds.");
            }
            balance -= amount;
        }
    }

    @Override
    public void deposit(double amount) {
        throw new UnsupportedOperationException("Deposits are not allowed in Fixed Deposit Accounts.");
    }

    @Override
    public String evaluateRisk() {
        if (LocalDate.now().isBefore(startDate.plusMonths(termInMonths))) {
            return "Fixed Deposit Account-Low Risk: Account is active.";
        } else {
            return "Fixed Deposit Account-High Risk: Account has matured.";
        }
    }

    @Override
    public void recordTransaction(String senderID, String receiverID, double amount) {
        transactionHistory.add(new Transaction(senderID, receiverID, amount, LocalDate.now()));
    }

    @Override
    public void printAccountDetails() {
        System.out.println("****************** Summary for Account " + accountID + " ******************");
        printTransactionHistory();
        System.out.println("Account Info");
        System.out.println("Fixed Deposit Account - Account Number: " + accountID);
        System.out.println("Balance: $" + balance);
        System.out.println("Interest Rate: " + (interestRate * 100) + "%");
        LocalDate maturityDate = startDate.plusMonths(termInMonths);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        System.out.println("Maturity Date: " + maturityDate.format(formatter));
        System.out.println("Status: " + (LocalDate.now().isBefore(startDate.plusMonths(termInMonths)) ? "Active" : "Matured"));
        System.out.println("Account Risk Evaluation");
        System.out.println(evaluateRisk());
        System.out.println("************************************************************************************************************");
    }
}

/**
 * Represents a financial transaction between two accounts.
 */
class Transaction {
    private final String senderID;
    private final String receiverID;
    private final double amount;
    private final LocalDate date;

    /**
     * Constructs a transaction with the specified sender, receiver, amount, and date.
     *
     * @param senderID the ID of the sender account
     * @param receiverID the ID of the receiver account
     * @param amount the transaction amount
     * @param date the date of the transaction
     */
    public Transaction(String senderID, String receiverID, double amount, LocalDate date) {
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.amount = amount;
        this.date = date;
    }

    @Override
    public String toString() {
        return "------------------------------------\n" +
                "Transaction UD: " + UUID.randomUUID().toString() + "\n" +
                "Sender: " + senderID + "\n" +
                "Receiver: " + receiverID + "\n" +
                "Amount: " + amount + "\n" +
                "------------------------------------\n";
    }
}
