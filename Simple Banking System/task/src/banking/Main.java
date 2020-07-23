package banking;

import banking.obj.BankManager;

public class Main {
    public static void main(String[] args) {
        BankManager bank = new BankManager();
        bank.initDataBase(args);
        bank.run();
    }
}
