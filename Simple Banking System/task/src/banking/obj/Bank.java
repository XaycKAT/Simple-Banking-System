package banking.obj;

import banking.database.AccountDB;

import java.util.*;

public class Bank {

    private Scanner scanner;
    private AccountDB accountDB;


    public Bank() {
        scanner = new Scanner(System.in);
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
            System.out.println(e.getMessage());
        }
    }

    private void runStartMenu() {
        printMainMenu();
        Integer command = scanner.nextInt();
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
                break;
        }
    }

    private void createAccount() {
        Account account = new Account();
        accountDB.insert(account);
        account.printCardInfo();
    }

    private void loginAccount() {
        System.out.println("Enter your card number:");
        String number = scanner.next();
        System.out.println("Enter your PIN:");
        String pin = scanner.next();
        Account account = accountDB.selectAccount(number);

        if (account != null && pin.equals(account.getPin())) {
            System.out.println("You have successfully logged in!\n");
            account.runAccountMenu();
        } else {
            System.out.println("Wrong card number or PIN!\n");
        }
    }

    private static void printMainMenu() {
        System.out.println("1. Create an account\n" +
                "2. Log into account\n" +
                "0. Exit");
    }
}
