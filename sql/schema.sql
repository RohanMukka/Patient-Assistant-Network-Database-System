-- Schema for Patient Assistant Network Database System

CREATE TABLE Person (
    SSN VARCHAR(20) PRIMARY KEY,
    Name VARCHAR(100) NOT NULL,
    Gender VARCHAR(10),
    Profession VARCHAR(50),
    Mailing_address VARCHAR(255),
    Email_address VARCHAR(100),
    Phone_number VARCHAR(20),
    OnMailingList INT -- 0 for No, 1 for Yes
);

CREATE TABLE Clients (
    SSN VARCHAR(20) PRIMARY KEY,
    Doctor_name VARCHAR(100),
    Doctor_phone_number VARCHAR(20),
    Date_first_assigned DATE,
    FOREIGN KEY (SSN) REFERENCES Person(SSN)
);

CREATE TABLE Volunteer (
    SSN VARCHAR(20) PRIMARY KEY,
    Date_of_recent_training DATE,
    First_joined_date DATE,
    Location VARCHAR(100),
    FOREIGN KEY (SSN) REFERENCES Person(SSN)
);

CREATE TABLE Employees (
    SSN VARCHAR(20) PRIMARY KEY,
    Salary DECIMAL(10, 2),
    Marital_status VARCHAR(20),
    Hire_date DATE,
    FOREIGN KEY (SSN) REFERENCES Person(SSN)
);

CREATE TABLE Donors (
    SSN VARCHAR(20) PRIMARY KEY,
    isAnonymous INT, -- 0 for No, 1 for Yes
    FOREIGN KEY (SSN) REFERENCES Person(SSN)
);

CREATE TABLE Need (
    SSN VARCHAR(20),
    Need_name VARCHAR(100),
    Importance_value INT,
    PRIMARY KEY (SSN, Need_name),
    FOREIGN KEY (SSN) REFERENCES Clients(SSN)
);

CREATE TABLE Insurance (
    Policy_id VARCHAR(50) PRIMARY KEY,
    Provider_name VARCHAR(100),
    Provider_address VARCHAR(255),
    Type VARCHAR(50)
);

CREATE TABLE has_policy (
    Policy_id VARCHAR(50),
    SSN VARCHAR(20),
    PRIMARY KEY (Policy_id, SSN),
    FOREIGN KEY (Policy_id) REFERENCES Insurance(Policy_id),
    FOREIGN KEY (SSN) REFERENCES Clients(SSN)
);

CREATE TABLE Team (
    team_name VARCHAR(100) PRIMARY KEY,
    type VARCHAR(50),
    date_when_formed DATE,
    team_id INT IDENTITY(1,1) UNIQUE -- Added to support Task 17 which uses integer team_id
);

CREATE TABLE serves (
    SSN VARCHAR(20),
    team_name VARCHAR(100),
    team_lead VARCHAR(20), -- SSN of team lead
    number_of_hours INT DEFAULT 0,
    Active_status VARCHAR(20),
    PRIMARY KEY (SSN, team_name),
    FOREIGN KEY (SSN) REFERENCES Volunteer(SSN),
    FOREIGN KEY (team_name) REFERENCES Team(team_name)
);

CREATE TABLE cares (
    SSN VARCHAR(20),
    team_name VARCHAR(100),
    Status VARCHAR(50),
    PRIMARY KEY (SSN, team_name),
    FOREIGN KEY (SSN) REFERENCES Clients(SSN),
    FOREIGN KEY (team_name) REFERENCES Team(team_name)
);

CREATE TABLE Expenses (
    Expense_ID INT IDENTITY(1,1) PRIMARY KEY, -- Adding surrogate key as (SSN, Date, Amount) might not be unique
    SSN VARCHAR(20),
    Date DATE,
    Amount DECIMAL(10, 2),
    Description VARCHAR(255),
    FOREIGN KEY (SSN) REFERENCES Employees(SSN)
);

CREATE TABLE reports (
    team_name VARCHAR(100),
    SSN VARCHAR(20),
    Date DATE,
    Description VARCHAR(255),
    PRIMARY KEY (team_name, SSN),
    FOREIGN KEY (team_name) REFERENCES Team(team_name),
    FOREIGN KEY (SSN) REFERENCES Employees(SSN)
);

CREATE TABLE Donations (
    Donation_ID INT IDENTITY(1,1) PRIMARY KEY,
    SSN VARCHAR(20),
    Date DATE,
    Amount DECIMAL(10, 2),
    Type_of_donation VARCHAR(50),
    Fund_raiser_name VARCHAR(100),
    FOREIGN KEY (SSN) REFERENCES Donors(SSN)
);

-- Derived from Task 2 Schema
CREATE TABLE Credit_Card (
    SSN VARCHAR(20),
    Date DATE,
    Amount DECIMAL(10, 2),
    Type_of_donation VARCHAR(50),
    Fund_raiser_name VARCHAR(100),
    card_number VARCHAR(50),
    card_type VARCHAR(20),
    expiry_date DATE,
    FOREIGN KEY (SSN) REFERENCES Donors(SSN)
);

CREATE TABLE Check_Table ( -- 'Check' is a keyword
    SSN VARCHAR(20),
    Date DATE,
    Amount DECIMAL(10, 2),
    Type_of_donation VARCHAR(50),
    Fund_raiser_name VARCHAR(100),
    Check_number VARCHAR(50),
    FOREIGN KEY (SSN) REFERENCES Donors(SSN)
);

CREATE TABLE Emergency_contact (
    SSN VARCHAR(20),
    Name_of_emergency_contact VARCHAR(100),
    phone_number_of_emergency_contact VARCHAR(20),
    relationship VARCHAR(50),
    PRIMARY KEY (SSN, Name_of_emergency_contact),
    FOREIGN KEY (SSN) REFERENCES Person(SSN)
);

-- Task 17 Support
-- Task 17 in Java uses a separate table 'Volunteer_Hours' and expects 'volunteer_id' column in Volunteer
-- and 'team_id' (int) in Team.
ALTER TABLE Volunteer ADD volunteer_id AS SSN; -- Computed column if creating from scratch, or just rely on 'SSN' if we fix the query.
-- However, T-SQL 'AS SSN' works for computed columns.
-- But to be safe, we will create Volunteer_Hours table as referenced.

CREATE TABLE Volunteer_Hours (
    volunteer_id VARCHAR(20),
    team_id INT,
    hours INT,
    FOREIGN KEY (volunteer_id) REFERENCES Volunteer(SSN),
    FOREIGN KEY (team_id) REFERENCES Team(team_id)
);

