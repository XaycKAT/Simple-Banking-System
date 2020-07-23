package banking.database;

import banking.obj.Account;

import java.sql.*;

public class DataBase {

    private String fileName;

    public DataBase(String fileName) {
        this.fileName = fileName;
    }

    public void createNewDatabase() {
        try (Connection conn = this.connect()) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
//                System.out.println("The driver name is " + meta.getDriverName());
//                System.out.println("A new database has been created.");
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

    public void insert(String number, String pin) {
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

    public Account selectAccount(String number) {
        String sql = "SELECT pin, balance FROM card WHERE number = ?";

        try (Connection conn = this.connect();
             PreparedStatement statement  = conn.prepareStatement(sql)){

            statement.setString(1,number);
            ResultSet rs  = statement.executeQuery();

            return new Account(number, rs.getString("pin"), rs.getLong("balance"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
