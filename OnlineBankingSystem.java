import java.util.*;

class Account {
    private String accountNumber;
    private String accountHolder;
    private double balance;
    private List<String> transactionHistory;

    public Account(String accountNumber, String accountHolder, double balance) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = balance;
        this.transactionHistory = new LinkedList<>();
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            transactionHistory.add("Deposited: " + amount);
            if (transactionHistory.size() > 10) {
                transactionHistory.remove(0);
            }
            System.out.println("Deposit successful. New balance: " + balance);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            transactionHistory.add("Withdrawn: " + amount);
            if (transactionHistory.size() > 10) {
                transactionHistory.remove(0);
            }
            System.out.println("Withdrawal successful. New balance: " + balance);
            return true;
        } else {
            System.out.println("Invalid withdrawal amount or insufficient balance.");
            return false;
        }
    }

    public void addTransaction(String transaction) {
        transactionHistory.add(transaction);
        if (transactionHistory.size() > 10) {
            transactionHistory.remove(0);
        }
    }

    public List<String> getTransactionHistory() {
        return transactionHistory;
    }
}

class Bank {
    private Map<String, Account> accounts = new HashMap<>();
    private Set<String> generatedAccountNumbers = new HashSet<>();
    private Random random = new Random();

    private String generateAccountNumber() {
        String accountNumber;
        do {
            accountNumber = String.format("%011d", random.nextLong() % 100000000000L);
        } while (generatedAccountNumbers.contains(accountNumber));
        generatedAccountNumbers.add(accountNumber);
        return accountNumber;
    }

    public void createAccount(String accountHolder, double initialDeposit) {
        String accountNumber = generateAccountNumber();
        Account newAccount = new Account(accountNumber, accountHolder, initialDeposit);
        accounts.put(accountNumber, newAccount);
        System.out.println("Account created successfully.");
        System.out.println("Account Number: " + newAccount.getAccountNumber());
        System.out.println("Account Holder: " + newAccount.getAccountHolder());
        System.out.println("Balance: " + newAccount.getBalance());
    }

    public void deposit(String accountNumber, double amount) {
        Account account = accounts.get(accountNumber);
        if (account != null) {
            account.deposit(amount);
        } else {
            System.out.println("Account not found.");
        }
    }

    public void withdraw(String accountNumber, double amount) {
        Account account = accounts.get(accountNumber);
        if (account != null) {
            account.withdraw(amount);
        } else {
            System.out.println("Account not found.");
        }
    }

    public void checkBalance(String accountNumber) {
        Account account = accounts.get(accountNumber);
        if (account != null) {
            System.out.println("Account Holder: " + account.getAccountHolder() + " | Balance: " + account.getBalance());
            System.out.println("Last 10 Transactions:");
            for (String transaction : account.getTransactionHistory()) {
                System.out.println(transaction);
            }
        } else {
            System.out.println("Account not found.");
        }
    }

    public void transfer(String fromAccount, String toAccount, double amount) {
        Account sender = accounts.get(fromAccount);
        Account receiver = accounts.get(toAccount);
        if (sender != null && receiver != null) {
            if (sender.withdraw(amount)) {
                receiver.deposit(amount);
                sender.addTransaction("Sent " + amount + " to " + receiver.getAccountNumber());
                receiver.addTransaction("Received " + amount + " from " + sender.getAccountNumber());
                System.out.println("Transfer successful. " + amount + " sent to " + receiver.getAccountHolder());
            }
        } else {
            System.out.println("One or both accounts not found.");
        }
    }
}

public class OnlineBankingSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Bank bank = new Bank();

        while (true) {
            System.out.println("\nOnline Banking System");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Check Balance & Transactions");
            System.out.println("5. Transfer Money");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter Account Holder Name: ");
                    String accHolder = scanner.next();
                    System.out.print("Enter Initial Deposit: ");
                    double initialDeposit = scanner.nextDouble();
                    bank.createAccount(accHolder, initialDeposit);
                    break;
                case 2:
                    System.out.print("Enter Account Number: ");
                    String accNumDep = scanner.next();
                    System.out.print("Enter Deposit Amount: ");
                    double depAmount = scanner.nextDouble();
                    bank.deposit(accNumDep, depAmount);
                    break;
                case 3:
                    System.out.print("Enter Account Number: ");
                    String accNumWith = scanner.next();
                    System.out.print("Enter Withdrawal Amount: ");
                    double withAmount = scanner.nextDouble();
                    bank.withdraw(accNumWith, withAmount);
                    break;
                case 4:
                    System.out.print("Enter Account Number: ");
                    String accNumCheck = scanner.next();
                    bank.checkBalance(accNumCheck);
                    break;
                case 5:
                    System.out.print("Enter Sender Account Number: ");
                    String senderAcc = scanner.next();
                    System.out.print("Enter Receiver Account Number: ");
                    String receiverAcc = scanner.next();
                    System.out.print("Enter Amount to Transfer: ");
                    double transferAmount = scanner.nextDouble();
                    bank.transfer(senderAcc, receiverAcc, transferAmount);
                    break;
                case 6:
                    System.out.println("Exiting... Thank you!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }
}