package banking.database;

import banking.interfaces.DAO;
import banking.obj.Account;

import java.sql.*;

public class AccountDB implements DAO {

    private final String fileName;

    public AccountDB(String fileName) {
        this.fileName = fileName;
    }

    private Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:" + fileName;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void createNewDatabase() {
        try (Connection conn = this.connect()) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void createNewTable() {

        // SQL statement for creating a new table
        String sql = "CREATE TABLE card ("
                + "	id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "	number TEXT NOT NULL,\n"
                + " pin TEXT NOT NULL,\n"
                + "	balance INTEGER DEFAULT 0"
                + ");";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insert(Account account) {
        String number = account.getCardNumber();
        String pin = account.getPin();

        String sql = "INSERT INTO card(number , pin) VALUES(?,?)";

        try (Connection conn = this.connect();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, number);
            statement.setString(2, pin);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(Account account) {
        String sql = "UPDATE card SET balance = ? WHERE number = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setLong(1, account.getBalance());
            pstmt.setString(2, account.getCardNumber());
            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(String accountNumber) {
        String sql = "DELETE FROM card WHERE number = ?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setString(1, accountNumber);
            // execute the delete statement
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public Account get(String number) {

        String sql = "SELECT pin, balance FROM card WHERE number = ?";

        try (Connection conn = this.connect();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, number);
            ResultSet rs = statement.executeQuery();

            return new Account(number, rs.getString("pin"), rs.getLong("balance"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
