package banking.obj;

import banking.database.DataBase;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Bank {

    private Scanner scanner;
    private String BIN = "400000";
    private DataBase dataBase;


    public Bank() {
        scanner = new Scanner(System.in);
    }

    public void run() {
        runStartMenu();
    }

    public void initDataBase(String[] args) {
        try {
            DataBase db = new DataBase(args[1]);
            db.createNewDatabase();
            db.createNewTable();
            this.dataBase = db;
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

    private void printCardCreate(String cardNumber, Integer pin) {
        System.out.println("Your card has been created\n" + "Your card number:\n" + cardNumber);
        System.out.println("Your card PIN:\n" + pin + "\n");
    }

    private void createAccount() {
        String number = BIN + ThreadLocalRandom.current().nextLong(100_000_000L,
                900_000_000L);
        Integer pin = ThreadLocalRandom.current().nextInt(1000, 9000);
        number += LuhnCheck(number);
        dataBase.insert(number, pin.toString());
        printCardCreate(number, pin);
    }

    private void loginAccount() {
        System.out.println("Enter your card number:");
        String number = scanner.next();
        System.out.println("Enter your PIN:");
        Integer pin = scanner.nextInt();
        Account account = dataBase.selectAccount(number);

        if (account != null && pin.toString().equals(account.getPin())) {
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

    private Integer LuhnCheck(String cardNumber) {
        List<Integer> list = Arrays.stream(cardNumber.split("")).map(Integer::parseInt)
                .collect(Collectors.toList());
        for (int i = 0; i < list.size(); i += 2) {
            Integer num = list.get(i) * 2;
            if (num > 9)
                num = num / 10 + num % 10;
            list.set(i, num);
        }
        return (10 - list.stream().mapToInt(Integer::intValue).sum() % 10) % 10;
    }
}
