package banking.obj;

import java.util.List;
import java.util.Scanner;

public class Account {
    private String cardNumber;
    private String pin;
    private Long balance;

    public Account(String cardNumber, String pin, Long balance) {
        this.cardNumber = cardNumber;
        this.pin = pin;
        this.balance = balance;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public void runAccountMenu() {
        Scanner scanner = new Scanner(System.in);
        printMenu();
        Integer command = scanner.nextInt();

        switch(command) {
            case 1: System.out.println(this.getBalance());
                this.runAccountMenu();
                break;
            case 2: System.out.println("You have successfully logged out!");
                break;
            case 0: System.out.println("Bye!");
                System.exit(0);
            default:
        }

    }

    private void printMenu() {
        System.out.println("1. Balance\n" +
                "2. Log out\n" +
                "0. Exit");
    }
}
