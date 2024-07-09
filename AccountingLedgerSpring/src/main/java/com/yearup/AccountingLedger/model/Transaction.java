package com.yearup.AccountingLedger.model;

public class Transaction {
    private int id;
    private String timestamp;
    private String description;
    private String vendor;
    private double amount;

    // Constructor
    public Transaction(String description, String vendor, double amount){
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    };

    // All arguments constructor
    public Transaction(int id, String timestamp, String description, String vendor, double amount){
        this.id = id;
        this.timestamp = timestamp;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return  """
                \tID           |\t\t%d
                \tDate         |\t\t%s
                \tDescription  |\t\t%s
                \tVendor       |\t\t%s
                \tAmount       |\t\t%.2f
                *==========================================================*""".formatted(id,timestamp,description,vendor,amount);
    }

    // Getters and setters

    public int getId() {
        return id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getDescription() {
        return description;
    }

    public String getVendor() {
        return vendor;
    }

    public double getAmount() {
        return amount;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
