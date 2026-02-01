-- 1. Enter a new team
INSERT INTO Team (team_name, date_when_formed, type) VALUES (?, ?, ?);

-- 2. Enter a new client and associate with teams
INSERT INTO Person (SSN, Name, Gender, Profession, Mailing_address, Email_address, Phone_number, OnMailingList) VALUES (?, ?, ?, ?, ?, ?, ?, ?);
INSERT INTO Clients (SSN, Name, Gender, Profession, Mailing_address, Email_address, Phone_number, OnMailingList, Doctor_name, Doctor_phone_number, Date_first_assigned) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
INSERT INTO cares (SSN, team_name, Status) VALUES (?, ?, ?);

-- 3. Enter a new volunteer and associate with teams
INSERT INTO Person (SSN, Name, Gender, Profession, Mailing_address, Email_address, Phone_number, OnMailingList) VALUES (?, ?, ?, ?, ?, ?, ?, ?);
INSERT INTO Volunteer (SSN, Name, Gender, Profession, Mailing_address, Email_address, Phone_number, OnMailingList, Date_of_recent_training, First_joined_date, Location) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
INSERT INTO serves (SSN, team_name, team_lead) VALUES (?, ?, ?);

-- 4. Enter volunteer hours
UPDATE serves SET number_of_hours=? WHERE SSN=? AND team_name=?;

-- 5. Enter a new employee and associate with teams
INSERT INTO Person (SSN, Name, Gender, Profession, Mailing_address, Email_address, Phone_number, OnMailingList) VALUES (?, ?, ?, ?, ?, ?, ?, ?);
INSERT INTO Employees (SSN, Name, Gender, Profession, Mailing_address, Email_address, Phone_number, OnMailingList, Salary, Marital_status, Hire_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
INSERT INTO reports (team_name, SSN, Date, Description) VALUES (?, ?, ?, ?);

-- 6. Enter an expense charged by an employee
INSERT INTO Expenses (SSN, Date, Amount, Description) VALUES (?, ?, ?, ?);

-- 7. Enter a new donor and associate with donations
INSERT INTO Person (SSN, Name, Gender, Profession, Mailing_address, Email_address, Phone_number, OnMailingList) VALUES (?, ?, ?, ?, ?, ?, ?, ?);
INSERT INTO Donors (SSN, Name, Gender, Profession, Mailing_address, Email_address, Phone_number, OnMailingList, isAnonymous) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);
INSERT INTO Donations (SSN, Date, Amount, Type_of_donation, Fund_raiser_name) VALUES (?, ?, ?, ?, ?);

-- 8. Retrieve doctor info for a client
SELECT Doctor_name, Doctor_phone_number FROM Clients WHERE SSN=?;

-- 9. Retrieve total expenses for each employee
SELECT e.Name, SUM(Amount) AS Total_Expenses 
FROM Employees e 
JOIN Expenses ex ON e.SSN = ex.SSN 
WHERE ex.Date BETWEEN ? AND ? 
GROUP BY e.Name 
ORDER BY Total_Expenses DESC;

-- 10. Retrieve volunteers for a client (via teams)
SELECT DISTINCT v.Name 
FROM Volunteer v 
JOIN serves s ON v.SSN = s.SSN 
JOIN cares c ON s.team_name = c.team_name 
WHERE c.SSN = ?;

-- 11. Retrieve teams founded after a date
SELECT team_name FROM Team WHERE date_when_formed > ?;

-- 12. Retrieve all person details
SELECT p.Name, p.SSN, p.Email_address, p.Phone_number, ec.Name_of_emergency_contact, ec.Phone_number_of_emergency_contact 
FROM Person p 
LEFT JOIN Emergency_contact ec ON p.SSN = ec.SSN;

-- 13. Retrieve employee donors
SELECT d.Name, SUM(dn.Amount) AS Total_Donation, d.isAnonymous 
FROM Donors d 
JOIN Employees e ON d.SSN = e.SSN 
JOIN Donations dn ON d.SSN = dn.SSN 
GROUP BY d.Name, d.isAnonymous 
ORDER BY Total_Donation DESC;

-- 14. Increase salary for employees reporting to multiple teams
UPDATE Employees SET Salary = Salary * 1.10 
WHERE SSN IN (SELECT SSN FROM reports GROUP BY SSN HAVING COUNT(DISTINCT team_name) > 1);

-- 15. Delete low priority clients
DELETE FROM Clients WHERE SSN NOT IN (SELECT SSN FROM has_policy) 
AND SSN IN (SELECT SSN FROM Need WHERE Need_name = 'transportation' AND Importance_value < 5);

-- 16. Import teams from file
INSERT INTO Team (team_name, type, date_when_formed) VALUES (?, ?, ?);

-- 17. View Volunteer Hours for Team
SELECT Volunteer.name AS volunteer_name, Volunteer_Hours.hours 
FROM Volunteer_Hours 
JOIN Volunteer ON Volunteer_Hours.volunteer_id = Volunteer.volunteer_id 
WHERE Volunteer_Hours.team_id = ?;
