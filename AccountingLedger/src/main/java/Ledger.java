import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Ledger {

    /*
    Ledger allows users to keep record of financial transactions by
    Allowing them to record deposits and payments and view a ledger of reports,
    including all, monthly, yearly and a search by vendor option
     */


    // Create a scanner object for user input
    static Scanner scanner = new Scanner(System.in);

    // Create static variables for current date, month and year
    static LocalDate currentDate = LocalDate.now();
    static int currentMonth = currentDate.getMonthValue();
    static int currentYear = currentDate.getYear();

    // Create a static ArrayList for sorting
    static ArrayList<String> entries = new ArrayList<>();


    public static void main(String[] args) {
        System.out.println("Welcome to Ledger");
        homeScreen();
    }

    public static void homeScreen() {
        // Display the home screen
        System.out.println("1) Add Deposit");
        System.out.println("2) Make Payment");
        System.out.println("3) Ledger");
        System.out.println("4) Exit");

        try {
            int choice = scanner.nextInt(); // Get user input
            scanner.nextLine(); // Consume the newline

            if (choice == 1) {
                writeToCSV(addDeposit());
                homeScreen();
            } else if (choice == 2) {
                writeToCSV(addPayment());
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

    public static void ledgerScreen() {
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
                allEntries();
                ledgerScreen();
            } else if (choice == 2) {
                System.out.println("All deposits");
                viewDeposits();
                ledgerScreen();
            } else if (choice == 3) {
                System.out.println("All payments");
                viewPayments();
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

    public static void viewReports() {
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
                monthToDate();
                viewReports();
            } else if (choice==2) {
                System.out.println("All entries from last month");
                previousMonth();
                viewReports();
            } else if (choice==3) {
                System.out.println("All entries from this year");
                yearToDate();
                viewReports();
            } else if (choice==4) {
                System.out.println("All entries from last year");
                previousYear();
                viewReports();
            } else if (choice==5) {
                searchByVendor();
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

    // Prompts for and creates a payment string
    public static String addPayment() {
        // Prompt for payment information
        System.out.println("Please enter the payment information");
        System.out.print("Describe the purchased item : ");
        String item = scanner.nextLine();
        System.out.print("Name of vendor : ");
        String vendor = scanner.nextLine();
        System.out.print("Enter the cost of the item as a negative number : ");
        double cost = scanner.nextDouble();
        if (cost > 0) {
            System.out.println("Invalid input\nAmount must be negative");
            homeScreen();
        }
        scanner.nextLine(); // Consume to newline
        // Return string
        return (item+"|"+vendor+"|"+cost);
    }

    // Prompts for and creates a deposit string
    public static String addDeposit() {
        // Prompt for deposit information
        System.out.println("Please enter the deposit information");
        System.out.print("Describe the deposit : ");
        String description = scanner.nextLine();
        System.out.print("Who is the vendor : ");
        String vendor = scanner.nextLine();
        System.out.print("Deposit amount : ");
        double amount = scanner.nextDouble();
        if (amount < 0) {
            System.out.println("Invalid input\nAmount must be greater than zero");
            homeScreen();
        }
        scanner.nextLine();
        // Return the string
        return (description+"|"+vendor+"|"+amount);
    }

    // Writes to the ledger.csv file
    public static void writeToCSV(String action) {
            // Get the local date and time
            LocalDate date = LocalDate.now();
            LocalTime time = LocalTime.now();

            String entry = (date+"|"+time.format(DateTimeFormatter.ofPattern("HH:mm:ss"))+"|"+action+"\n");

        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("ledger.csv", true));
            // Write the date, time and action to the ledger.csv file
            bufferedWriter.write(entry);
            System.out.println("Entry recorded");

            bufferedWriter.close();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    // Displays all entries recorded in the ledger
    public static void allEntries() {
        // Prints all entries to the terminal
        String input;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("ledger.csv"));
            while ((input = bufferedReader.readLine()) != null) {
                // Add entry to entries ArrayList
                entries.add(input);
            }
            sortArray(entries);
            // Close bufferedReader
            bufferedReader.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    // Displays all deposits recorded in the ledger
    public static void viewDeposits() {
        // Shows all deposits recorded in the ledger
        String input;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("ledger.csv"));
            while ((input = bufferedReader.readLine()) != null) {
                String[] tokens = input.split("\\|");
                if (Double.parseDouble(tokens[4]) > 0) {
                    // Add entry to the arraylist
                    entries.add(input);
                }
            }
            // Sort the array list
            sortArray(entries);

            bufferedReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Displays all payments recorded in the ledger
    public static void viewPayments() {

        String input;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("ledger.csv"));
            while ((input = bufferedReader.readLine()) != null) {
                String[] tokens = input.split("\\|");
                if (Double.parseDouble(tokens[4]) < 0){
                    // Add to entries
                    entries.add(input);
                }
            }
            // Sort and print the entries
            sortArray(entries);
            // Close the reader
            bufferedReader.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Displays all entries from the current month
    public static void monthToDate() {
        String input;

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("ledger.csv"));
            while ((input = bufferedReader.readLine()) != null) {
                String[] tokens = input.split("\\|");
                String[] date = tokens[0].split("-");
                if (Double.parseDouble(date[1]) == currentMonth && Double.parseDouble(date[0]) == currentYear) {
                    entries.add(input);
                }
            }
            sortArray(entries);
            bufferedReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

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

    // Display all entries from the current year
    public static void yearToDate() {
        String input;

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("ledger.csv"));
            while ((input = bufferedReader.readLine()) != null) {
                String[] tokens = input.split("\\|");
                String[] date = tokens[0].split("-");
                if (Integer.parseInt(date[0]) == currentYear) {
                    entries.add(input);
                }
            }
            sortArray(entries);
            bufferedReader.close();
        } catch (IOException e){
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
