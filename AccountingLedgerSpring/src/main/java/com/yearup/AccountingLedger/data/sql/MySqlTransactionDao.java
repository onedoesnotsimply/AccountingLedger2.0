package com.yearup.AccountingLedger.data.sql;

import com.yearup.AccountingLedger.data.TransactionDao;
import com.yearup.AccountingLedger.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class MySqlTransactionDao implements TransactionDao {
    private DataSource dataSource;

    @Autowired
    public MySqlTransactionDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void addTransaction(Transaction transaction) {
        String query = "INSERT INTO transactions (description, vendor, amount) VALUES (?, ?, ?)";

        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)){

            preparedStatement.setString(1, transaction.getDescription());
            preparedStatement.setString(2, transaction.getVendor());
            preparedStatement.setDouble(3, transaction.getAmount());

            int rows = preparedStatement.executeUpdate();

            if (rows>0){
                System.out.println("Transaction added successfully");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Transaction> getAllEntries() {
        List<Transaction> transactions = new ArrayList<>();
        String query = "SELECT * FROM transactions";

        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery()){

            while(resultSet.next()){
                // Parse the rows into transactions
                Transaction transaction = mapRow(resultSet);
                transactions.add(transaction);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return transactions;
    }

    @Override
    public List<Transaction> getAllDeposits() {
        List<Transaction> deposits = new ArrayList<>();
        String query = "SELECT * FROM transactions WHERE amount > 0";

        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()){
                Transaction transaction = mapRow(resultSet);
                deposits.add(transaction);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return deposits;
    }

    @Override
    public List<Transaction> getAllPayments() {
        List<Transaction> payments = new ArrayList<>();

        String query = "SELECT * FROM transactions WHERE amount < 0";

        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()){
                Transaction transaction = mapRow(resultSet);
                payments.add(transaction);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return payments;
    }

    @Override
    public List<Transaction> getMonthToDate() {
        List<Transaction> transactions = new ArrayList<>();
        String query = "SELECT * FROM transactions WHERE " +
                "MONTH(date_time) = MONTH(CURRENT_DATE) " +
                "AND " +
                "YEAR(date_time) = YEAR(CURRENT_DATE)";

        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()){
                Transaction transaction = mapRow(resultSet);
                transactions.add(transaction);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return transactions;
    }

    @Override
    public List<Transaction> getLastMonth() {
        return List.of();
    }

    @Override
    public List<Transaction> getYearToDate() {
        List<Transaction> transactions = new ArrayList<>();
        String query = "SELECT * FROM transactions WHERE YEAR(date_time) = YEAR(CURRENT_DATE)";

        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()){
                Transaction transaction = mapRow(resultSet);
                transactions.add(transaction);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return transactions;
    }

    @Override
    public List<Transaction> getLastYear() {
        return List.of();
    }

    @Override
    public List<Transaction> searchByVendor(String vendorName) {
        return List.of();
    }

    private Transaction mapRow(ResultSet row) throws SQLException {
        int id = row.getInt(1);
        String timestamp = String.valueOf(row.getTimestamp(2));
        String description = row.getString(3);
        String vendor = row.getString(4);
        double amount = row.getDouble(5);
        return new Transaction(id,timestamp,description,vendor,amount);
    }
}
