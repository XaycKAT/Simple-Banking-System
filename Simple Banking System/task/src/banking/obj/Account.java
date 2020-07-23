package banking.obj;

import java.util.concurrent.ThreadLocalRandom;

import static banking.other.Utils.generateLastDigit;

public class Account {
    private final String cardNumber;
    private final String pin;
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

    public String getCardNumber() {
        return cardNumber;
    }

    public String getPin() {
        return pin;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    private String generatePin() {
        return String.valueOf(ThreadLocalRandom.current().nextInt(1000, 9000));
    }

    private String generateNumber() {
        String BIN = "400000";
        String num = BIN + ThreadLocalRandom.current().nextLong(100_000_000L,
                900_000_000L);
        return num + generateLastDigit(num);
    }

    public void printAccountInfo() {
        System.out.println("Your card has been created\n" + "Your card number:\n" + this.getCardNumber());
        System.out.println("Your card PIN:\n" + this.getPin() + "\n");
    }
}
