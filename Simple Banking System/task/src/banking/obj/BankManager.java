package banking.obj;

import banking.database.AccountDB;

import java.util.*;

import static banking.other.Utils.checkLuhnAlgorithm;

public class BankManager {

    private final Scanner scanner = new Scanner(System.in);
    private AccountDB accountDB;


    public BankManager() {
    }

    public void run() {
        runStartMenu();
    }

    public void initDataBase(String[] args) {
        try {
            AccountDB db = new AccountDB(args[1]);
            db.createNewDatabase();
            db.createNewTable();
            this.accountDB = db;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Wrong arguments\n" + e.getMessage());
        }
    }

    private void runStartMenu() {
        printMainMenu();
        int command = scanner.nextInt();
        switch (command) {
            case 1:
                createAccount();
                this.runStartMenu();
                break;
            case 2:
                loginAccount();
                this.runStartMenu();
                break;
            default:
                System.out.println("Bye!");
                System.exit(0);
                break;
        }
    }

    private void createAccount() {
        Account account = new Account();
        accountDB.insert(account);
        account.printAccountInfo();
    }

    private void loginAccount() {
        System.out.println("Enter your card number:");
        String number = scanner.next();
        System.out.println("Enter your PIN:");
        String pin = scanner.next();
        Account account = accountDB.get(number);

        if (account != null && pin.equals(account.getPin())) {
            System.out.println("You have successfully logged in!\n");
            runAccountMenu(account);
        } else {
            System.out.println("Wrong card number or PIN!\n");
        }
    }

    private static void printMainMenu() {
        System.out.println("1. Create an account\n" +
                "2. Log into account\n" +
                "0. Exit");
    }

    public void runAccountMenu(Account account) {
        printAccountMenu();
        int command = scanner.nextInt();

        switch (command) {
            case 1:
                System.out.println(account.getBalance());
                runAccountMenu(account);
                break;
            case 2:
                addIncome(account);
                break;
            case 3:
                doTransfer(account);
                break;
            case 4:
                accountDB.delete(account.getCardNumber());
                System.out.println("The account has been closed!\n");
                runStartMenu();
                break;
            case 5:
                System.out.println("You have successfully logged out!\n");
                break;
            case 0:
                System.out.println("Bye!");
                System.exit(0);
            default:
        }
    }

    private void addIncome(Account account) {
        account.setBalance(account.getBalance() + scanner.nextLong());
        accountDB.update(account);
        System.out.println("Income was added!\n");
        runAccountMenu(account);
    }

    private void doTransfer(Account accountFrom) {
        System.out.println("Enter card number:\n");
        String number = scanner.next();
        Account accountTo = accountDB.get(number);
        if (accountTo == null) {
            System.out.println("Such a card does not exist.");
            runAccountMenu(accountFrom);
        } else if (accountTo.getCardNumber().equals(accountFrom.getCardNumber())) {
            System.out.println("You can't transfer money to the same account!\n");
            runAccountMenu(accountFrom);
        } else if (!checkLuhnAlgorithm(number)){
            System.out.println("Probably you made mistake in the card number. Please try again!\n");
            runAccountMenu(accountFrom);
        } else {
            System.out.println("Enter how much money you want to transfer:\n");
            Long moneyToTransfer = scanner.nextLong();
            if (accountFrom.getBalance() < moneyToTransfer){
                System.out.println("Not enough money!\n");
                runAccountMenu(accountFrom);
            }
            accountTo.setBalance(accountTo.getBalance() + moneyToTransfer);
            accountFrom.setBalance(accountFrom.getBalance() - moneyToTransfer);
            accountDB.update(accountFrom);
            accountDB.update(accountTo);
            System.out.println("Success!\n");
            runAccountMenu(accountFrom);
        }

    }


    private void printAccountMenu() {
        System.out.println("1. Balance\n" +
                "2. Add income\n" +
                "3. Do transfer\n" +
                "4. Close account\n" +
                "5. Log out\n" +
                "0. Exit");
    }
}
