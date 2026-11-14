-- =============================================================================
-- CSA09 - PROGRAMMING IN JAVA - ASSIGNMENT 5 (CO5)
-- DATA PERSISTENCE ENGINEERING: SCHEMA DEFINITION SCRIPT
-- =============================================================================

CREATE DATABASE IF NOT EXISTS bus_pass_db;
USE bus_pass_db;

-- -----------------------------------------------------------------------------
-- 1. Users Table (Tasks A5.2 & B2: SQL Injection & Authentication Security)
-- -----------------------------------------------------------------------------
DROP TABLE IF EXISTS users;
CREATE TABLE users (
    id VARCHAR(50) NOT NULL,
    pwd VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL DEFAULT 'USER',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Seed authentic user accounts for demonstration
INSERT INTO users (id, pwd, role) VALUES 
('admin', 'Admin@2026_SecureKey', 'ADMINISTRATOR'),
('officer1', 'PassDesk#987', 'OFFICER'),
('student1', 'College#Pass21', 'STUDENT');

-- -----------------------------------------------------------------------------
-- 2. Bus Pass Table (Tasks B3 & B4: High-Throughput Batch Processing & Migration)
-- -----------------------------------------------------------------------------
DROP TABLE IF EXISTS bus_pass;
CREATE TABLE bus_pass (
    pass_id VARCHAR(20) NOT NULL,
    reg_no VARCHAR(20) NOT NULL,
    name VARCHAR(100) NOT NULL,
    route_no INT NOT NULL,
    amount DOUBLE NOT NULL,
    issued_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (pass_id),
    INDEX idx_route (route_no),
    INDEX idx_regno (reg_no)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- -----------------------------------------------------------------------------
-- 3. Student Table (Task A3: Data Access Object [DAO] Pattern)
-- -----------------------------------------------------------------------------
DROP TABLE IF EXISTS student;
CREATE TABLE student (
    reg_no VARCHAR(20) NOT NULL,
    name VARCHAR(100) NOT NULL,
    dept VARCHAR(50) NOT NULL,
    year INT NOT NULL,
    cgpa DOUBLE NOT NULL,
    PRIMARY KEY (reg_no)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Seed sample student entities
INSERT INTO student (reg_no, name, dept, year, cgpa) VALUES
('192311001', 'Arun Kumar', 'CSE', 3, 8.85),
('192311002', 'Divya Rajan', 'ECE', 2, 9.12),
('192311003', 'Rahul Sharma', 'IT', 4, 8.40);

-- -----------------------------------------------------------------------------
-- 4. Wallets Table (Task A5.1: ACID Transactions & Wallet Transfer)
-- -----------------------------------------------------------------------------
DROP TABLE IF EXISTS wallet;
CREATE TABLE wallet (
    wallet_id VARCHAR(30) NOT NULL,
    owner_name VARCHAR(100) NOT NULL,
    balance DOUBLE NOT NULL,
    PRIMARY KEY (wallet_id),
    CONSTRAINT chk_positive_balance CHECK (balance >= 0.0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO wallet (wallet_id, owner_name, balance) VALUES
('W_HOSTEL_101', 'Hostel Wallet - Student 101', 2500.00),
('W_CANTEEN_01', 'Central Campus Canteen Wallet', 10000.00);
