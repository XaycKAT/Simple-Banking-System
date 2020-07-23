package banking.obj;

import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import static banking.other.Utils.LuhnCheck;

public class Account {
    private String cardNumber;
    private String pin;
    private Long balance;

    public Account() {
        this.cardNumber = generateNumber();
        this.pin = generatePin();
        this.balance = 0L;
    }

    public Account(String cardNumber, String pin, Long balance) {
        this.cardNumber = cardNumber;
        this.pin = pin;
        this.balance = balance;
    }

    private String generatePin() {
        return String.valueOf(ThreadLocalRandom.current().nextInt(1000, 9000));
    }

    private String generateNumber() {
        String BIN = "400000";
        String num = BIN + ThreadLocalRandom.current().nextLong(100_000_000L,
                900_000_000L);
        return num += LuhnCheck(num);
    }

    public void printCardInfo() {
        System.out.println("Your card has been created\n" + "Your card number:\n" + this.getCardNumber());
        System.out.println("Your card PIN:\n" + this.getPin() + "\n");
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
        int command = scanner.nextInt();

        switch (command) {
            case 1:
                System.out.println(this.getBalance());
                this.runAccountMenu();
                break;
            case 2:
                System.out.println("You have successfully logged out!");
                break;
            case 0:
                System.out.println("Bye!");
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
