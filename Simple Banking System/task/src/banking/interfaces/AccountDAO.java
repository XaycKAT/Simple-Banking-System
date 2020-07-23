package banking.interfaces;

import banking.obj.Account;

public interface AccountDAO {

    Account get(String accountNumber);
    void insert(Account account);
    void update(Account account);
    void delete(String accountNumber);

}
