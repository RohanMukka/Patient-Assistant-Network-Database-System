# Patient Assistant Network Database System

This is a comprehensive database management system designed for the Patient Assistant Network. It manages information about clients, volunteers, employees, donors, and teams using a relational database backend.

## Overview
The system allows administrators to perform various operations such as registering new teams, clients, volunteers, and employees, recording donations and expenses, and generating reports. It uses a menu-driven console interface for interaction.

## Features
- **Team Management**: Create and manage teams (medical, transportation, fundraising, etc.).
- **Person Management**: Unified management of Clients, Volunteers, Employees, and Donors as specialized roles of a 'Person'.
- **Association**: Link Clients and Volunteers to Teams (`cares` and `serves` relationships).
- **Financial Tracking**: Record Employee expenses and Donor donations.
- **Reporting**:
    - Retrieve Doctor info for Clients.
    - Calculate total expenses per employee.
    - View volunteers for specific clients.
    - Generate salary increase reports.
    - Identify high-value donors.

## Tech Stack
- **Language**: Java 1.8+
- **Database**: Azure SQL Database (SQL Server)
- **Connectivity**: JDBC (Java Database Connectivity)
- **Driver**: MSSQL JDBC Driver

## Database Design Summary
The database follows a relational model centered around the `Person` superclass table, with specialized subclass tables:
- `Person`: Stores common attributes (SSN, Name, Contact).
- `Clients`: Stores patient-specific info (Doctor, diagnosis date).
- `Volunteer`: Stores service info (Training date, Join date).
- `Employees`: Stores employment info (Salary, Hire date).
- `Donors`: Stores donation preferences (Anonymity).

Key Relationships:
- `serves`: Volunteers serve Teams.
- `cares`: Teams care for Clients.
- `reports`: Employees report to Teams.
- `Donations`: Recorded for Donors.
- `Expenses`: Recorded for Employees.

## Setup & How to Run

### Prerequisites
- Java Development Kit (JDK) installed.
- SQL Server database accessible.
- JDBC Driver for SQL Server (e.g., `mssql-jdbc`).

### Database Setup
1. Execute the `sql/schema.sql` script in your SQL Server database to create the tables.
2. (Optional) Run `sql/queries.sql` to verify query syntax or populate initial data.

### Configuration
The application uses environment variables for database credentials to ensure security.
Set the following environment variables before running the application:

- `DB_USER`: Your database username.
- `DB_PASS`: Your database password.

*Note: The JDBC URL is currently configured for a specific Azure SQL instance. You may need to update `DB_URL` in `src/FacultyManager.java` if using a different server.*

### Running the Application
1. Compile the Java source code:
   ```bash
   javac src/FacultyManager.java
   ```
2. Run the application (ensure JDBC driver is in classpath):
   ```bash
   java -cp ".;path/to/mssql-jdbc.jar" jsp_azure_test.FacultyManager
   ```

## Security Note
**IMPORTANT**: database credentials should never be hardcoded in the source code. This version of the project retrieves `DB_USER` and `DB_PASS` from system environment variables. Ensure these are set in your deployment environment.
