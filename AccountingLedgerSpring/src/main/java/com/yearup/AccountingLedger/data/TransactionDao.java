package com.yearup.AccountingLedger.data;

import com.yearup.AccountingLedger.model.Transaction;

import java.util.List;

public interface TransactionDao {
    public void addTransaction(Transaction transaction);
    public List<Transaction> getAllEntries();
    public List<Transaction> getAllDeposits();
    public List<Transaction> getAllPayments();
    public List<Transaction> getMonthToDate();
    public List<Transaction> getLastMonth();
    public List<Transaction> getYearToDate();
    public List<Transaction> getLastYear();
    public List<Transaction> searchByVendor(String vendorName);
    public List<Transaction> customSearch(String startDate, String endDate, String description, String vendor, String amount);
}
