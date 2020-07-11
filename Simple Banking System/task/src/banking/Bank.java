package banking;

import banking.obj.Account;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Bank {

    private List<Account> accounts = new ArrayList<>();
    private Scanner scanner;
    private String CARD_MASK = "400000";

    public Bank() {
        scanner = new Scanner(System.in);
    }

    public void runStartMenu() {
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

    private void printCardCreate(String cardNumber, Integer pin) {
        System.out.println("Your card has been created\n" + "Your card number:\n" + cardNumber);
        System.out.println("Your card PIN:\n" + pin + "\n");
    }

    private void createAccount() {
        Long number = ThreadLocalRandom.current().nextLong(1000000000L, 9000000000L);
        Integer pin = ThreadLocalRandom.current().nextInt(1000, 9000);
        accounts.add(new Account(CARD_MASK + number, pin, 0L));
        printCardCreate(CARD_MASK + number,pin);
    }

    private void loginAccount() {
        System.out.println("Enter your card number:");
        String number = scanner.next();
        System.out.println("Enter your PIN:");
        Integer pin = scanner.nextInt();
        Account account = Account.findAccountByCardNumber(number, accounts);

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
