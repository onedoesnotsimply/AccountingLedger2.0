package com.yearup.AccountingLedger.display;

import com.yearup.AccountingLedger.data.TransactionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class UserInterface {
    private TransactionDao transactionDao;

    @Autowired
    public UserInterface(TransactionDao transactionDao) {
        this.transactionDao = transactionDao;
    }

    // Create a scanner object for user input
    static Scanner scanner = new Scanner(System.in);

    public void homeScreen() {
        // Display the home screen
        System.out.println("1) Add Deposit");
        System.out.println("2) Make Payment");
        System.out.println("3) Ledger");
        System.out.println("4) Exit");

        try {
            int choice = scanner.nextInt(); // Get user input
            scanner.nextLine(); // Consume the newline

            if (choice == 1) {
                //writeToCSV(addDeposit());
                homeScreen();
            } else if (choice == 2) {
                //writeToCSV(addPayment());
                homeScreen();
            } else if (choice==3) {
                System.out.println("Which Ledger would you like to see?");
                ledgerScreen();
            } else if (choice==4) {
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
                transactionDao.getAllEntries();
                ledgerScreen();
            } else if (choice == 2) {
                System.out.println("All deposits");
                //viewDeposits();
                ledgerScreen();
            } else if (choice == 3) {
                System.out.println("All payments");
                //viewPayments();
                ledgerScreen();
            } else if (choice == 4) {
                System.out.println("Which report would you like to view?");
                //viewReports();
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
                //monthToDate();
                viewReports();
            } else if (choice==2) {
                System.out.println("All entries from last month");
                //previousMonth();
                viewReports();
            } else if (choice==3) {
                System.out.println("All entries from this year");
                //yearToDate();
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
}
