-- Drop the database if it exists, create it if it doesn't

DROP DATABASE IF EXISTS accounting_ledger;

CREATE DATABASE IF NOT EXISTS accounting_ledger;

USE accounting_ledger;

-- Create tables

CREATE TABLE `transactions` (
`transaction_id` INT NOT NULL auto_increment,
`date_time` datetime DEFAULT CURRENT_TIMESTAMP,
`description` varchar(250),
`vendor` varchar(50),
`amount` DOUBLE,
PRIMARY KEY(`transaction_id`)
);

-- Truncate all data

TRUNCATE TABLE transactions;

-- Add data to transactions

INSERT INTO transactions (description, vendor, amount) VALUES ("Invoice","Bank of America", 2200.0);
INSERT INTO transactions (description, vendor, amount) VALUES ("Bill", "Mortgages of America", -450.0);