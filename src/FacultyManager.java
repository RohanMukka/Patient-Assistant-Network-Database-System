package jsp_azure_test;

import java.sql.*;
import java.util.Scanner;
import java.io.*;

public class FacultyManager {
    // Database credentials
    static final String DB_URL = "jdbc:sqlserver://mukk0001-sql-server.database.windows.net:1433;database=cs-dsa-4513-sql-db";
    static final String USER = System.getenv("DB_USER");
    static final String PASS = System.getenv("DB_PASS");

    // JDBC connection
    private static Connection conn;

    public static void main(String[] args) {
        try {
            // Connect to the Azure SQL Database
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected to Azure SQL Database successfully.");

            Scanner scanner = new Scanner(System.in);
            int choice;

            do {
                displayMenu();
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline left-over

                switch (choice) {
                    case 1:
                        enterNewTeam();
                        break;
                    case 2:
                        enterNewClientAndAssociateWithTeams();
                        break;
                    case 3:
                        enterNewVolunteerAndAssociateWithTeams();
                        break;
                    case 4:
                        enterVolunteerHours();
                        break;
                    case 5:
                        enterNewEmployeeAndAssociateWithTeams();
                        break;
                    case 6:
                        enterExpense();
                        break;
                    case 7:
                        newDonorWithDonations();
                        break;
                    case 8:
                        getDoctorInfoForClient();
                        break;
                    case 9:
                        getEmployeeExpensesByPeriod();
                        break;
                    case 10:
                        getVolunteersForClient();
                        break;
                    case 11:
                        getTeamsFoundedAfter();
                        break;
                    case 12:
                        getAllPersonDetails();
                        break;
                    case 13:
                        getEmployeeDonors();
                        break;
                    case 14:
                        increaseSalaryForMultipleTeamsReport();
                        break;
                    case 15:
                        deleteLowPriorityClients();
                        break;
                    case 16:
                        importTeamsFromFile();
                        break;
                    case 17:
                        viewVolunteerHoursForTeam();
                        break;
                    case 18:
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } while (choice != 18);

            scanner.close();
            conn.close();
            System.out.println("Connection closed.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void displayMenu() {
        System.out.println("WELCOME TO THE PATIENT ASSISTANT NETWORK DATABASE SYSTEM");
        System.out.println("1. Enter a new team into the database");
        System.out.println("2. Enter a new client into the database and associate him or her with one or more teams");
        System.out.println("3. Enter a new volunteer into the database and associate him or her with one or more teams");
        System.out.println("4. Enter the number of hours a volunteer worked this month for a particular team");
        System.out.println("5. Enter a new employee into the database and associate him or her with one or more teams");
        System.out.println("6. Enter an expense charged by an employee");
        System.out.println("7. Enter a new donor and associate him or her with several donations");
        System.out.println("8. Retrieve the name and phone number of the doctor of a particular client");
        System.out.println("9. Retrieve total expenses for each employee");
        System.out.println("10. Retrieve the list of volunteers that are members of teams that support a particular client");
        System.out.println("11. Retrieve the names of all teams that were founded after a particular date");
        System.out.println("12. Retrieve the names, social security numbers, contact information, and emergency contact information of all people in the database");
        System.out.println("13. Retrieve the name and total amount donated by donors that are also employees. The list should be sorted by the total amount of the donations, and indicate if each donor wishes to remain anonymous");
        System.out.println("14. Increase the salary by 10% of all employees to whom more than one team must report");
        System.out.println("15. Delete all clients who do not have health insurance and whose value of importance for transportation is less than 5");
        System.out.println("16. Import: enter new teams from a data file");
        System.out.println("17. View Volunteer Hours for Team");
        System.out.println("18. Quit");
        System.out.print("Enter your choice: ");
    }

    // 1
    public static void enterNewTeam() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Team Name: ");
        String teamName = scanner.nextLine();
        System.out.print("Enter Founded Date (YYYY-MM-DD): ");
        String foundedDate = scanner.nextLine();
        System.out.print("Enter the Type: ");
        String team_type = scanner.nextLine();

        String sql = "INSERT INTO Team (team_name, date_when_formed, type) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, teamName);
            pstmt.setDate(2, Date.valueOf(foundedDate));
            pstmt.setString(3, team_type);
            pstmt.executeUpdate();
            System.out.println("Team added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 2
    public static void enterNewClientAndAssociateWithTeams() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Client SSN: ");
        String clientSsn = scanner.nextLine();
        System.out.print("Enter Client Name: ");
        String clientName = scanner.nextLine();
        System.out.print("Enter Gender: ");
        String clintGender = scanner.nextLine();
        System.out.print("Enter clients profession: ");
        String clientProfession = scanner.nextLine();
        System.out.print("Enter mailing success: "); // pdf typo: mailing adress
        String clientMail = scanner.nextLine();
        System.out.print("Enter email address: ");
        String clientEmail = scanner.nextLine();
        System.out.print("Enter Phone Number: ");
        String clientPhone = scanner.nextLine();
        System.out.print("Is on mailing list yes=1/No=0?: ");
        String clientIsonmail = scanner.nextLine();
        System.out.print("Enter Doctor Name: ");
        String clientDoctorname = scanner.nextLine();
        System.out.print("Enter Doctor Phone Number: ");
        String clientDoctorPhone = scanner.nextLine();
        System.out.print("Enter Date When First Assigned (YYYY-MM-DD): ");
        String Clientdatefirstassigned = scanner.nextLine();
        System.out.print("Enter team name to associate: ");
        String teamname = scanner.nextLine();
        System.out.print("Enter status: ");
        String status = scanner.nextLine();

        String sqlPerson = "INSERT INTO Person (SSN, Name, Gender, Profession, Mailing_address, Email_address, Phone_number, OnMailingList) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String sqlClient = "INSERT INTO Clients (SSN, Name, Gender, Profession, Mailing_address, Email_address, Phone_number, OnMailingList, Doctor_name, Doctor_phone_number, Date_first_assigned) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String sqlAssoc = "INSERT INTO cares (SSN, team_name, Status) VALUES (?, ?, ?)";

        try (PreparedStatement pstmtPerson = conn.prepareStatement(sqlPerson);
             PreparedStatement pstmtClient = conn.prepareStatement(sqlClient);
             PreparedStatement pstmtAssoc = conn.prepareStatement(sqlAssoc)) {

            pstmtPerson.setString(1, clientSsn);
            pstmtPerson.setString(2, clientName);
            pstmtPerson.setString(3, clintGender);
            pstmtPerson.setString(4, clientProfession);
            pstmtPerson.setString(5, clientMail);
            pstmtPerson.setString(6, clientEmail);
            pstmtPerson.setString(7, clientPhone);
            pstmtPerson.setString(8, clientIsonmail);
            pstmtPerson.executeUpdate();

            // Note: In Client table insert, parameters 2-8 are redundant as data is in Person, but schema has it duplicated?
            // Task 2 schema: Clients(SSN, Name, gender, Profession, Mailing_address, Email_address, Phone_number, OnMailingList, Doctor_name, Doctor_phone_number, Date_first_assigned)
            // This is NOT normalized (Client shouldn't repeat Person attributes), but following the code/schema exactly.
            pstmtClient.setString(1, clientSsn);
            pstmtClient.setString(2, clientName);
            pstmtClient.setString(3, clintGender);
            pstmtClient.setString(4, clientProfession);
            pstmtClient.setString(5, clientMail);
            pstmtClient.setString(6, clientEmail);
            pstmtClient.setString(7, clientPhone);
            pstmtClient.setString(8, clientIsonmail);
            pstmtClient.setString(9, clientDoctorname);
            pstmtClient.setString(10, clientDoctorPhone);
            pstmtClient.setDate(11, Date.valueOf(Clientdatefirstassigned));
            pstmtClient.executeUpdate();

            pstmtAssoc.setString(1, clientSsn);
            pstmtAssoc.setString(2, teamname);
            pstmtAssoc.setString(3, status);
            pstmtAssoc.executeUpdate();

            System.out.println("Client and association with team added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 3
    public static void enterNewVolunteerAndAssociateWithTeams() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Volunteer SSN: ");
        String clientSsn = scanner.nextLine();
        System.out.print("Enter volunteer Name: ");
        String clientName = scanner.nextLine();
        System.out.print("Enter Gender: ");
        String clintGender = scanner.nextLine();
        System.out.print("Enter volunteer profession: ");
        String clientProfession = scanner.nextLine();
        System.out.print("Enter mailing address: ");
        String clientMail = scanner.nextLine();
        System.out.print("Enter email address: ");
        String clientEmail = scanner.nextLine();
        System.out.print("Enter Phone Number: ");
        String clientPhone = scanner.nextLine();
        System.out.print("Is on mailing list yes=1/No=0?: ");
        String clientIsonmail = scanner.nextLine();
        System.out.print("Enter date of recent Training yyyy-mm-dd: ");
        String clienttrainingdate = scanner.nextLine();
        System.out.print("Enter Joining date yyyy-mm-dd: ");
        String clientjoindate = scanner.nextLine();
        System.out.print("Enter Location: ");
        String clientlocation = scanner.nextLine();
        System.out.print("Enter the Team Name: ");
        String clientteam = scanner.nextLine();
        System.out.print("Enter Team Lead SSN: ");
        String clientTeamLeadssn = scanner.nextLine();

        String sqlPerson = "INSERT INTO Person (SSN, Name, Gender, Profession, Mailing_address, Email_address, Phone_number, OnMailingList) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String sqlVolunteer = "INSERT INTO Volunteer (SSN, Name, Gender, Profession, Mailing_address, Email_address, Phone_number, OnMailingList, Date_of_recent_training, First_joined_date, Location) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String sqlserves = "INSERT INTO serves (SSN, team_name, team_lead) VALUES (?, ?, ?)";

        try (PreparedStatement pstmtperson = conn.prepareStatement(sqlPerson);
             PreparedStatement pstmtVolunteer = conn.prepareStatement(sqlVolunteer);
             PreparedStatement pstmtAssoc = conn.prepareStatement(sqlserves)) {

            pstmtperson.setString(1, clientSsn);
            pstmtperson.setString(2, clientName);
            pstmtperson.setString(3, clintGender);
            pstmtperson.setString(4, clientProfession);
            pstmtperson.setString(5, clientMail);
            pstmtperson.setString(6, clientEmail);
            pstmtperson.setString(7, clientPhone);
            pstmtperson.setString(8, clientIsonmail);
            pstmtperson.executeUpdate();

            pstmtVolunteer.setString(1, clientSsn);
            pstmtVolunteer.setString(2, clientName);
            pstmtVolunteer.setString(3, clintGender);
            pstmtVolunteer.setString(4, clientProfession);
            pstmtVolunteer.setString(5, clientMail);
            pstmtVolunteer.setString(6, clientEmail);
            pstmtVolunteer.setString(7, clientPhone);
            pstmtVolunteer.setString(8, clientIsonmail);
            pstmtVolunteer.setDate(9, Date.valueOf(clienttrainingdate));
            pstmtVolunteer.setDate(10, Date.valueOf(clientjoindate));
            pstmtVolunteer.setString(11, clientlocation);
            pstmtVolunteer.executeUpdate();

            pstmtAssoc.setString(1, clientSsn);
            pstmtAssoc.setString(2, clientteam);
            pstmtAssoc.setString(3, clientTeamLeadssn);
            pstmtAssoc.executeUpdate();

            System.out.println("Volunteer and association with team added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 4
    public static void enterVolunteerHours() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Volunteer SSN: ");
        String volunteerId = scanner.nextLine();
        System.out.print("Enter Team Name: ");
        String teamId = scanner.nextLine();
        System.out.print("Enter Hours: ");
        String hours = scanner.nextLine();

        String sql = "UPDATE serves SET number_of_hours=? WHERE SSN=? AND team_name=?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, hours);
            pstmt.setString(2, volunteerId);
            pstmt.setString(3, teamId);
            pstmt.executeUpdate();
            System.out.println("Volunteer hours updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 5
    public static void enterNewEmployeeAndAssociateWithTeams() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Client SSN: "); // prompt says Client SSN but context is Employee
        String clientSsn = scanner.nextLine();
        System.out.print("Enter Client Name: ");
        String clientName = scanner.nextLine();
        System.out.print("Enter Gender: ");
        String clintGender = scanner.nextLine();
        System.out.print("Enter clients profession: ");
        String clientProfession = scanner.nextLine();
        System.out.print("Enter mailing adress: ");
        String clientMail = scanner.nextLine();
        System.out.print("Enter email adress: ");
        String clientEmail = scanner.nextLine();
        System.out.print("Enter Phone Number: ");
        String clientPhone = scanner.nextLine();
        System.out.print("Is on mailing list yes=1/No=0?: ");
        String clientIsonmail = scanner.nextLine();
        System.out.print("Enter Salary: ");
        String salary = scanner.nextLine();
        System.out.print("Enter Marital Status: ");
        String maritalstatus = scanner.nextLine();
        System.out.print("Enter hire date: ");
        String hiredate = scanner.nextLine();

        System.out.print("Enter Team name to Associate: ");
        String teamId = scanner.nextLine();
        System.out.print("Enter description: ");
        String description = scanner.nextLine();

        String sqlPerson = "INSERT INTO Person (SSN, Name, Gender, Profession, Mailing_address, Email_address, Phone_number, OnMailingList) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String sqlEmployee = "INSERT INTO Employees (SSN, Name, Gender, Profession, Mailing_address, Email_address, Phone_number, OnMailingList, Salary, Marital_status, Hire_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String sqlAssoc = "INSERT INTO reports (team_name, SSN, Date, Description) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmtPerson = conn.prepareStatement(sqlPerson);
             PreparedStatement pstmt1 = conn.prepareStatement(sqlEmployee);
             PreparedStatement pstmt2 = conn.prepareStatement(sqlAssoc)) {

            pstmtPerson.setString(1, clientSsn);
            pstmtPerson.setString(2, clientName);
            pstmtPerson.setString(3, clintGender);
            pstmtPerson.setString(4, clientProfession);
            pstmtPerson.setString(5, clientMail);
            pstmtPerson.setString(6, clientEmail);
            pstmtPerson.setString(7, clientPhone);
            pstmtPerson.setString(8, clientIsonmail);
            pstmtPerson.executeUpdate();

            pstmt1.setString(1, clientSsn);
            pstmt1.setString(2, clientName);
            pstmt1.setString(3, clintGender);
            pstmt1.setString(4, clientProfession);
            pstmt1.setString(5, clientMail);
            pstmt1.setString(6, clientEmail);
            pstmt1.setString(7, clientPhone);
            pstmt1.setString(8, clientIsonmail);
            pstmt1.setString(9, salary);
            pstmt1.setString(10, maritalstatus);
            pstmt1.setDate(11, Date.valueOf(hiredate));
            pstmt1.executeUpdate();

            pstmt2.setString(1, teamId);
            pstmt2.setString(2, clientSsn);
            pstmt2.setDate(3, Date.valueOf(hiredate)); // Using hiredate as association date
            pstmt2.setString(4, description);
            pstmt2.executeUpdate();

            System.out.println("Employee and association with team added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 6
    public static void enterExpense() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Employee SSN: ");
        String empId = scanner.nextLine();
        System.out.print("Enter date: ");
        String date = scanner.nextLine();
        System.out.print("Enter Amount: ");
        String amount = scanner.nextLine();
        System.out.print("Enter description: ");
        String description = scanner.nextLine();

        String sql = "INSERT INTO Expenses (SSN, Date, Amount, Description) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, empId);
            pstmt.setDate(2, Date.valueOf(date));
            pstmt.setString(3, amount);
            pstmt.setString(4, description);
            pstmt.executeUpdate();
            System.out.println("Expense added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 7
    public static void newDonorWithDonations() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Donor SSN: ");
        String DonorSsn = scanner.nextLine();
        System.out.print("Enter donor Name: ");
        String DonorName = scanner.nextLine();
        System.out.print("Enter Gender: ");
        String DonorGender = scanner.nextLine();
        System.out.print("Enter Donor profession: ");
        String DonorProfession = scanner.nextLine();
        System.out.print("Enter mailing adress: ");
        String DonorMail = scanner.nextLine();
        System.out.print("Enter email adress: ");
        String DonorEmail = scanner.nextLine();
        System.out.print("Enter Phone Number: ");
        String DonorPhone = scanner.nextLine();
        System.out.print("Is on mailing list yes=1/No=0?: ");
        String DonorOnMail = scanner.nextLine();
        System.out.println("is anonymous yes=1/no=0: ");
        String DonorAnonymous = scanner.nextLine();
        System.out.println("Enter Date: ");
        String date = scanner.nextLine();
        System.out.println("Enter amount: ");
        String amount = scanner.nextLine();
        System.out.println("type of the donation: ");
        String type = scanner.nextLine();
        System.out.println("Enter fund raiser name: ");
        String name = scanner.nextLine();

        String sqlPerson = "INSERT INTO Person (SSN, Name, Gender, Profession, Mailing_address, Email_address, Phone_number, OnMailingList) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String sqlDonor = "INSERT INTO Donors (SSN, Name, Gender, Profession, Mailing_address, Email_address, Phone_number, OnMailingList, isAnonymous) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String sqlAssoc = "INSERT INTO Donations (SSN, Date, Amount, Type_of_donation, Fund_raiser_name) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pstmtPerson = conn.prepareStatement(sqlPerson);
             PreparedStatement pstmtDonor = conn.prepareStatement(sqlDonor);
             PreparedStatement pstmtAssoc = conn.prepareStatement(sqlAssoc)) {

            pstmtPerson.setString(1, DonorSsn);
            pstmtPerson.setString(2, DonorName);
            pstmtPerson.setString(3, DonorGender);
            pstmtPerson.setString(4, DonorProfession);
            pstmtPerson.setString(5, DonorMail);
            pstmtPerson.setString(6, DonorEmail);
            pstmtPerson.setString(7, DonorPhone);
            pstmtPerson.setString(8, DonorOnMail);
            pstmtPerson.executeUpdate();

            pstmtDonor.setString(1, DonorSsn);
            pstmtDonor.setString(2, DonorName);
            pstmtDonor.setString(3, DonorGender);
            pstmtDonor.setString(4, DonorProfession);
            pstmtDonor.setString(5, DonorMail);
            pstmtDonor.setString(6, DonorEmail);
            pstmtDonor.setString(7, DonorPhone);
            pstmtDonor.setString(8, DonorOnMail);
            pstmtDonor.setString(9, DonorAnonymous);
            pstmtDonor.executeUpdate();

            pstmtAssoc.setString(1, DonorSsn);
            pstmtAssoc.setString(2, date);
            pstmtAssoc.setString(3, amount);
            pstmtAssoc.setString(4, type);
            pstmtAssoc.setString(5, name);
            pstmtAssoc.executeUpdate();

            System.out.println("Successfully entered new donor with several donations");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 8
    public static void getDoctorInfoForClient() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Client SSN: ");
        String clientSSN = scanner.nextLine();
        String query = "SELECT Doctor_name, Doctor_phone_number FROM Clients WHERE SSN=?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, clientSSN);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                System.out.println("Doctor Name: " + rs.getString("Doctor_name"));
                System.out.println("Doctor Phone: " + rs.getString("Doctor_phone_number"));
            } else {
                System.out.println("Client not found.");
            }
        }
    }

    // 9
    public static void getEmployeeExpensesByPeriod() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter start date (YYYY-MM-DD): ");
        Date startDate = Date.valueOf(scanner.nextLine());
        System.out.print("Enter end date (YYYY-MM-DD): ");
        Date endDate = Date.valueOf(scanner.nextLine());

        String query = "SELECT e.Name, SUM(Amount) AS Total_Expenses " +
                "FROM Employees e JOIN Expenses ex ON e.SSN = ex.SSN " +
                "WHERE ex.Date BETWEEN ? AND ? " +
                "GROUP BY e.Name " +
                "ORDER BY Total_Expenses DESC";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDate(1, startDate);
            stmt.setDate(2, endDate);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.println("Employee: " + rs.getString("Name") + ", Total Expenses: " + rs.getBigDecimal("Total_Expenses"));
            }
        }
    }

    // 10
    public static void getVolunteersForClient() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Client SSN: ");
        String clientSSN = scanner.nextLine();
        String query = "SELECT DISTINCT v.Name " +
                "FROM Volunteer v " +
                "JOIN serves s ON v.SSN = s.SSN " +
                "JOIN cares c ON s.team_name = c.team_name " +
                "WHERE c.SSN = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, clientSSN);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.println("Volunteer: " + rs.getString("Name"));
            }
        }
    }

    // 11
    public static void getTeamsFoundedAfter() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter date (YYYY-MM-DD): ");
        Date date = Date.valueOf(scanner.nextLine());
        String query = "SELECT team_name FROM Team WHERE date_when_formed > ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDate(1, date);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.println("Team Name: " + rs.getString("team_name"));
            }
        }
    }

    // 12
    public static void getAllPersonDetails() throws SQLException {
        String query = "SELECT p.Name, p.SSN, p.Email_address, p.Phone_number, " +
                "ec.Name_of_emergency_contact, ec.Phone_number_of_emergency_contact " +
                "FROM Person p LEFT JOIN Emergency_contact ec ON p.SSN = ec.SSN";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.println("Name: " + rs.getString("Name") + ", SSN: " + rs.getString("SSN"));
                System.out.println("Contact Email: " + rs.getString("Email_address") + ", Phone: " + rs.getString("Phone_number"));
                System.out.println("Emergency Contact: " + rs.getString("Name_of_emergency_contact") + ", Phone: " + rs.getString("Phone_number_of_emergency_contact"));
            }
        }
    }

    // 13
    public static void getEmployeeDonors() throws SQLException {
        String query = "SELECT d.Name, SUM(dn.Amount) AS Total_Donation, d.isAnonymous " +
                "FROM Donors d " +
                "JOIN Employees e ON d.SSN = e.SSN " +
                "JOIN Donations dn ON d.SSN = dn.SSN " +
                "GROUP BY d.Name, d.isAnonymous " +
                "ORDER BY Total_Donation DESC";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String anonymityStatus = rs.getBoolean("isAnonymous") ? " (Anonymous)" : "";
                System.out.println("Donor: " + rs.getString("Name") + anonymityStatus + ", Total Donation: " + rs.getBigDecimal("Total_Donation"));
            }
        }
    }

    // 14
    public static void increaseSalaryForMultipleTeamsReport() throws SQLException {
        String query = "UPDATE Employees SET Salary = Salary * 1.10 " +
                "WHERE SSN IN (SELECT SSN FROM reports GROUP BY SSN HAVING COUNT(DISTINCT team_name) > 1)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Salary updated for " + rowsAffected + " employees.");
        }
    }

    // 15
    public static void deleteLowPriorityClients() throws SQLException {
        String query = "DELETE FROM Clients WHERE SSN NOT IN " +
                "(SELECT SSN FROM has_policy) " +
                "AND SSN IN (SELECT SSN FROM Need WHERE Need_name = 'transportation' AND Importance_value < 5)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            int rowsDeleted = stmt.executeUpdate();
            System.out.println("Deleted " + rowsDeleted + " low-priority clients.");
        }
    }

    // 16
    public static void importTeamsFromFile() {
        System.out.print("Enter the input filename for importing teams: ");
        Scanner scanner = new Scanner(System.in);
        String fileName = scanner.nextLine();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            String query = "INSERT INTO Team (team_name, type, date_when_formed) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(","); // Assumes file format: team_name,type,date_when_formed
                    if (data.length == 3) {
                        stmt.setString(1, data[0].trim());
                        stmt.setString(2, data[1].trim());
                        stmt.setDate(3, Date.valueOf(data[2].trim())); // Date format: YYYY-MM-DD
                        stmt.executeUpdate();
                        System.out.println("Inserted team: " + data[0]);
                    } else {
                        System.out.println("Invalid data format in line: " + line);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }

    // 17
    public static void viewVolunteerHoursForTeam() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Team ID: ");
        int teamId = scanner.nextInt();

        String sql = "SELECT Volunteer.name AS volunteer_name, Volunteer_Hours.hours " +
                "FROM Volunteer_Hours " +
                "JOIN Volunteer ON Volunteer_Hours.volunteer_id = Volunteer.volunteer_id " +
                "WHERE Volunteer_Hours.team_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, teamId);
            ResultSet rs = pstmt.executeQuery();

            System.out.println("Volunteer Hours for Team ID " + teamId + ":");
            while (rs.next()) {
                System.out.println("Volunteer: " + rs.getString("volunteer_name") + ", Hours: " + rs.getInt("hours"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
