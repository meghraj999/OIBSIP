import java.util.*;

// Class to store transaction details
class Transaction {
    String type;
    double amount;

    Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    public String toString() {
        return type + " : ₹" + amount;
    }
}

// Account class
class Account {
    private String userId;
    private String pin;
    private double balance;
    private List<Transaction> transactions;

    public Account(String userId, String pin) {
        this.userId = userId;
        this.pin = pin;
        this.balance = 0.0;
        this.transactions = new ArrayList<>();
    }

    public boolean login(String userId, String pin) {
        return this.userId.equals(userId) && this.pin.equals(pin);
    }

    public void deposit(double amount) {
        balance += amount;
        transactions.add(new Transaction("Deposit", amount));
        System.out.println("₹" + amount + " deposited successfully.");
    }

    public void withdraw(double amount) {
        if (amount > balance) {
            System.out.println("Insufficient balance!");
        } else {
            balance -= amount;
            transactions.add(new Transaction("Withdraw", amount));
            System.out.println("₹" + amount + " withdrawn successfully.");
        }
    }

    public void transfer(Account receiver, double amount) {
        if (amount > balance) {
            System.out.println("Insufficient balance!");
        } else {
            balance -= amount;
            receiver.balance += amount;
            transactions.add(new Transaction("Transfer to " + receiver.userId, amount));
            System.out.println("₹" + amount + " transferred successfully.");
        }
    }

    public void showTransactionHistory() {
        if (transactions.isEmpty()) {
            System.out.println("No transactions yet.");
        } else {
            System.out.println("\nTransaction History:");
            for (Transaction t : transactions) {
                System.out.println(t);
            }
        }
    }

    // Balance Check Method
    public void checkBalance() {
        System.out.println("Current Balance: ₹" + balance);
    }
}

// ATM class
class ATM {
    private Scanner sc;
    private Account account;
    private Account receiver;

    public ATM() {
        sc = new Scanner(System.in);
        account = new Account("user123", "1234");   // default user
        receiver = new Account("user456", "4567"); // for transfer
    }

    public void start() {
        System.out.println("===== Welcome to ATM =====");

        System.out.print("Enter User ID: ");
        String userId = sc.nextLine();

        System.out.print("Enter PIN: ");
        String pin = sc.nextLine();

        if (account.login(userId, pin)) {
            System.out.println("Login Successful!\n");
            menu();
        } else {
            System.out.println("Invalid Credentials!");
        }
    }

    private void menu() {
        int choice;

        do {
            System.out.println("\n===== ATM Menu =====");
            System.out.println("1. Transaction History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Check Balance");
            System.out.println("6. Quit");
            System.out.print("Enter choice: ");

            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    account.showTransactionHistory();
                    break;

                case 2:
                    System.out.print("Enter amount to withdraw: ");
                    double withdrawAmt = sc.nextDouble();
                    account.withdraw(withdrawAmt);
                    break;

                case 3:
                    System.out.print("Enter amount to deposit: ");
                    double depositAmt = sc.nextDouble();
                    account.deposit(depositAmt);
                    break;

                case 4:
                    System.out.print("Enter amount to transfer: ");
                    double transferAmt = sc.nextDouble();
                    account.transfer(receiver, transferAmt);
                    break;

                case 5:
                    account.checkBalance();
                    break;

                case 6:
                    System.out.println("Thank you for using ATM!");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }
        } while (choice != 6);
    }
}

// Main class
public class ATMSystem {
    public static void main(String[] args) {
        ATM atm = new ATM();
        atm.start();
    }
}