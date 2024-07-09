package com.yearup.AccountingLedger;

import com.yearup.AccountingLedger.data.TransactionDao;
import com.yearup.AccountingLedger.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class Ledger implements CommandLineRunner {
    private TransactionDao transactionDao;

    @Autowired
    public Ledger(TransactionDao transactionDao) {
        this.transactionDao = transactionDao;
    }

    // Create a scanner object for user input
    static Scanner scanner = new Scanner(System.in);

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Welcome to Ledger");
        homeScreen();
    }

    public void homeScreen() {
        // Display the home screen
        System.out.println("1) Add Transaction");
        System.out.println("2) Ledger");
        System.out.println("3) Exit");

        try {
            int choice = scanner.nextInt(); // Get user input
            scanner.nextLine(); // Consume the newline

            if (choice == 1) {
                addTransaction();
                homeScreen();
            } else if (choice==2) {
                System.out.println("Which Ledger would you like to see?");
                ledgerScreen();
            } else if (choice==3) {
                scanner.close();
                System.exit(0);
            } else {
                System.out.println("Invalid Input");
                homeScreen();
            }
        } catch (Exception e){
            System.out.println("Invalid Input");
            scanner.nextLine();
            homeScreen();
        }
    }

    public void ledgerScreen() {
        System.out.println("1) All Entries");
        System.out.println("2) Deposits");
        System.out.println("3) Payments");
        System.out.println("4) Reports");
        System.out.println("5) Home");

        try {
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                System.out.println("All entries\n------------------------------------------------------------");
                getAllEntries();
                ledgerScreen();
            } else if (choice == 2) {
                System.out.println("All deposits\n------------------------------------------------------------");
                getAllDeposits();
                ledgerScreen();
            } else if (choice == 3) {
                System.out.println("All payments\n------------------------------------------------------------");
                getAllPayments();
                ledgerScreen();
            } else if (choice == 4) {
                System.out.println("Which report would you like to view?");
                viewReports();
            } else if (choice == 5) {
                homeScreen();
            } else {
                System.out.println("Invalid Input");
                ledgerScreen();
            }
        } catch (Exception e){
            System.out.println("Invalid Input");
            scanner.nextLine();
            ledgerScreen();
        }
    }

    public void viewReports() {
        System.out.println("""
            1) Month To Date
            2) Previous Month
            3) Year To Date
            4) Previous Year
            5) Search by Vendor
            6) Custom Search
            7) Back""");

        try {
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice==1) {
                System.out.println("All entries from this month\n------------------------------------------------------------");
                getMonthToDate();
                viewReports();
            } else if (choice==2) {
                System.out.println("All entries from last month\n------------------------------------------------------------");
                getLastMonth();
                viewReports();
            } else if (choice==3) {
                System.out.println("All entries from this year\n------------------------------------------------------------");
                getYearToDate();
                viewReports();
            } else if (choice==4) {
                System.out.println("All entries from last year\n------------------------------------------------------------");
                getLastYear();
                viewReports();
            } else if (choice==5) {
                getByVendor();
                viewReports();
            } else if (choice==6) {
                customSearch();
                viewReports();
            } else if (choice==7) {
                ledgerScreen();
            } else {
                System.out.println("Invalid Input");
                viewReports();
            }
        } catch (Exception e){
            System.out.println("Invalid Input");
            scanner.nextLine();
            viewReports();
        }
    }

    public void addTransaction() {
        // Initialize the transaction object
        Transaction transaction;

        // Prompt for data
        System.out.println("Enter a description of the transaction");
        String description = scanner.nextLine();
        System.out.println("Enter the name of the vendor");
        String vendor = scanner.nextLine();
        System.out.println("Enter the amount");
        double amount = scanner.nextDouble();

        // Create the transaction
        transaction = new Transaction(description, vendor, amount);
        /* Write the transaction to the database */
        transactionDao.addTransaction(transaction);
    }

    public void getAllEntries() {
        List<Transaction> transactions = transactionDao.getAllEntries();
        transactions.forEach(System.out::println);
    }

    public void getAllDeposits() {
        List<Transaction> transactions = transactionDao.getAllDeposits();
        transactions.forEach(System.out::println);
    }

    public void getAllPayments() {
        List<Transaction> transactions = transactionDao.getAllPayments();
        transactions.forEach(System.out::println);
    }

    public void getMonthToDate() {
        List<Transaction> transactions = transactionDao.getMonthToDate();
        transactions.forEach(System.out::println);
    }

    public void getYearToDate() {
        List<Transaction> transactions = transactionDao.getYearToDate();
        transactions.forEach(System.out::println);
    }

    public void getLastMonth() {
        List<Transaction> transactions = transactionDao.getLastMonth();
        transactions.forEach(System.out::println);
    }

    public void getLastYear() {
        List<Transaction> transactions = transactionDao.getLastYear();
        transactions.forEach(System.out::println);
    }

    public void getByVendor() {
        System.out.println("Enter the name of the vendor");
        String vendorName = scanner.nextLine();
        List<Transaction> transactions = transactionDao.searchByVendor(vendorName);
        transactions.forEach(System.out::println);
    }

    public void customSearch() {
        System.out.println("Search parameters are optional - leave empty if not applicable");
        System.out.print("Enter start and end date (yyyy-mm-DD/yyyy-mm-DD) : ");
        String date = scanner.nextLine();
        System.out.print("Enter transaction description : ");
        String description = scanner.nextLine();
        System.out.print("Enter the name of the vendor : ");
        String vendor = scanner.nextLine();
        System.out.print("Enter the amount : ");
        String amount = scanner.nextLine();

        String startDate="", endDate="";

        if (!date.isEmpty()){
            startDate = date.split("/")[0];
            endDate = date.split("/")[1];
        }

        List<Transaction> transactions = transactionDao.customSearch(startDate,endDate,description,vendor,amount);
        transactions.forEach(System.out::println);
    }
}
