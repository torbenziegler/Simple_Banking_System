package org.hyperskill;


import org.sqlite.SQLiteDataSource;

import java.sql.*;

public class JDBC {


    public static void connect(String url) {

        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);

        String createTable = "CREATE TABLE IF NOT EXISTS card (\n"
                + "	id INTEGER AUTO_INCREMENT,\n"
                + "	number TEXT NOT NULL,\n"
                + "	pin TEXT NOT NULL,\n"
                + "	balance INTEGER DEFAULT 0\n"
                + ");";


        try (Connection con = dataSource.getConnection()) {
            // Statement creation
            try (Statement statement = con.createStatement()) {
                // Statement execution
                statement.executeUpdate(createTable);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private Connection connect() {
        // SQLite connection string
        String url = Main.path;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public int getBalance(String cardNumber) {
        String sql = "SELECT balance "
                + "FROM card WHERE number = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the value
            pstmt.setString(1, cardNumber);
            //
            ResultSet rs = pstmt.executeQuery();

            return rs.getInt("balance");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return -5000;
    }

    public boolean loginVerify(String cardNumber, String pin) {
        boolean numberBoo = false;
        boolean pinBoo = false;

        String sql = "SELECT number, pin "
                + "FROM card WHERE number = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the value
            pstmt.setString(1, cardNumber);
            //
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                if (rs.getString("number").equals(cardNumber)) {
                    numberBoo = true;
                }
                if (rs.getString("pin").equals(pin)) {
                    pinBoo = true;
                }

            }
        } catch (SQLException ignored) {
        }

        return numberBoo && pinBoo;
    }


    public boolean checkCardNumber(String cardNumber) {
        String sql = "SELECT * "
                + "FROM card WHERE number = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the value
            pstmt.setString(1, cardNumber);
            //
            ResultSet rs = pstmt.executeQuery();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }

        return true;
    }


    public void addAccountToDatabase(String number, String pin) {
        String sql = "INSERT INTO card(number, pin) VALUES(?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, number);
            pstmt.setString(2, pin);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateBalance(int income, String cardNumber) {
        String sql = "UPDATE card SET balance = balance + ?"
                + "WHERE number = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setInt(1, income);
            pstmt.setString(2, cardNumber);
            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Income was added!");
    }

    public void deleteAccount(String number) {
        String sql = "DELETE FROM card WHERE number = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setString(1, number);
            // execute the delete statement
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void makeTransfer(String sourceCard, String targetCard, int amount) {
        updateBalance(-amount, sourceCard);
        updateBalance(amount, targetCard);
    }
}
