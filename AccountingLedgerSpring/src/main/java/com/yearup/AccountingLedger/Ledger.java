package com.yearup.AccountingLedger;

import com.yearup.AccountingLedger.data.TransactionDao;
import com.yearup.AccountingLedger.display.UserInterface;
import com.yearup.AccountingLedger.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
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

    // Create static variables for current date, month and year
    static LocalDate currentDate = LocalDate.now();
    static int currentMonth = currentDate.getMonthValue();
    static int currentYear = currentDate.getYear();

    // Create a static ArrayList for sorting
    static ArrayList<String> entries = new ArrayList<>();


    @Override
    public void run(String... args) throws Exception {
        System.out.println("Welcome to Ledger");
        homeScreen();
        //System.out.println(transactionDao.getAllEntries());
    }

    /*
    public static void main(String[] args) {
        System.out.println("Welcome to Ledger");
        homeScreen();
    }
    */

    public void homeScreen() {
        // Display the home screen
        System.out.println("1) Add Transaction");
        System.out.println("2) Ledger");
        System.out.println("3) Exit");

        try {
            int choice = scanner.nextInt(); // Get user input
            scanner.nextLine(); // Consume the newline

            if (choice == 1) {
                //writeToCSV(addDeposit());
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
                System.out.println("All entries");
                getAllEntries();
                ledgerScreen();
            } else if (choice == 2) {
                System.out.println("All deposits");
                getAllDeposits();
                ledgerScreen();
            } else if (choice == 3) {
                System.out.println("All payments");
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
        System.out.println("1) Month To Date");
        System.out.println("2) Previous Month");
        System.out.println("3) Year To Date");
        System.out.println("4) Previous Year");
        System.out.println("5) Search by Vendor");
        System.out.println("6) Back");

        try {
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice==1) {
                System.out.println("All entries from this month");
                getLastMonth();
                viewReports();
            } else if (choice==2) {
                System.out.println("All entries from last month");
                //previousMonth();
                viewReports();
            } else if (choice==3) {
                System.out.println("All entries from this year");
                getLastYear();
                viewReports();
            } else if (choice==4) {
                System.out.println("All entries from last year");
                //previousYear();
                viewReports();
            } else if (choice==5) {
                //searchByVendor();
                viewReports();
            } else if (choice==6) {
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

    public void getLastMonth() {
        List<Transaction> transactions = transactionDao.getLastMonth();
        transactions.forEach(System.out::println);
    }

    public void getLastYear() {
        List<Transaction> transactions = transactionDao.getLastYear();
        transactions.forEach(System.out::println);
    }


    // TODO
    // Sorts, prints and clears the ArrayList
    public static void sortArray(ArrayList<String> items){
        Collections.reverse(items);
        for (String entry : items){
            System.out.println(entry);
        }
        System.out.println(" ");
        items.clear();
    }

    // Displays all entries from the past month
    public static void previousMonth() {
        String input;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("ledger.csv"));
            while ((input = bufferedReader.readLine()) != null) {
                String[] tokens = input.split("\\|");
                String[] date = tokens[0].split("-");
                if (Double.parseDouble(date[1]) == currentMonth-1 && Integer.parseInt(date[0]) == currentYear) {
                    entries.add(input);
                }
            }
            sortArray(entries);
            bufferedReader.close();
        }catch (IOException e){
            throw new RuntimeException(e);
        }

    }

    // Display all entries from the previous year
    public static void previousYear() {
        String input;

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("ledger.csv"));
            while ((input = bufferedReader.readLine()) != null) {
                String[] tokens = input.split("\\|");
                String[] date = tokens[0].split("-");
                if (Integer.parseInt(date[0]) == currentYear-1) {
                    entries.add(input);
                }
            }
            sortArray(entries);
            bufferedReader.close();
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    // Displays all entries from a specific vendor
    public static void searchByVendor() {
        // Prompt user for the name of the vendor
        System.out.print("Please enter the name of the vendor : ");
        String vendor = scanner.nextLine();

        String input;
        // Read and query the csv file for entries from that vendor
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("ledger.csv"));
            while ((input = bufferedReader.readLine()) != null) {
                String[] tokens = input.split("\\|");
                if (tokens[3].equalsIgnoreCase(vendor)) {
                    entries.add(input);
                }
            }
            System.out.println("All entries from "+vendor);
            sortArray(entries);
            bufferedReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
