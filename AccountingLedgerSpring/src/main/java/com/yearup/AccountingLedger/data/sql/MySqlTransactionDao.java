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

    }

    @Override
    public List<Transaction> getAllEntries() {
        List<Transaction> transactions = new ArrayList<>();
        Transaction transaction;
        String query = "SELECT * FROM transactions";

        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery()){

            while(resultSet.next()){
                // Parse the rows into transactions
                int id = resultSet.getInt(1);
                String timestamp = String.valueOf(resultSet.getTimestamp(2));
                String description = resultSet.getString(3);
                String vendor = resultSet.getString(4);
                double amount = resultSet.getDouble(5);
                transaction = new Transaction(id,timestamp,description,vendor,amount);
                transactions.add(transaction);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return transactions;
    }

    @Override
    public List<Transaction> getAllDeposits() {
        return List.of();
    }

    @Override
    public List<Transaction> getAllPayments() {
        return List.of();
    }

    @Override
    public List<Transaction> getMonthToDate() {
        return List.of();
    }

    @Override
    public List<Transaction> getLastMonth() {
        return List.of();
    }

    @Override
    public List<Transaction> getYearToDate() {
        return List.of();
    }

    @Override
    public List<Transaction> getLastYear() {
        return List.of();
    }

    @Override
    public List<Transaction> searchByVendor(String vendorName) {
        return List.of();
    }
}
