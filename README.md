# Personal Expense Tracker

A beginner-friendly Java Swing desktop application to manage income and expenses with text-file storage.

## Features
- Add income and expense records
- Update and delete existing transactions
- View all transactions in a JTable
- Search transactions by type, category, or date
- Filter transactions by month (`YYYY-MM`)
- View monthly summary (income, expense, balance)
- Dashboard totals:
  - Total income
  - Total expense
  - Current balance
- Automatic save to `transactions.txt`
- Automatic load from `transactions.txt` at startup
- Validation with clear success/error messages using `JOptionPane`

## Technologies Used
- Java (JDK 8+)
- Java Swing (GUI)
- File handling (`BufferedReader`, `BufferedWriter`)
- Object-Oriented Programming (OOP)

## How to Run in NetBeans
1. Open NetBeans.
2. Click **File > Open Project**.
3. Select this project folder (`Personal Expense Tracker` repository).
4. Make sure JDK is configured.
5. Run `Main.java`.

## Project Structure
- `Main.java` - Entry point, starts the Swing app.
- `MainFrame.java` - Main GUI, form actions, JTable, dashboard labels.
- `Transaction.java` - Transaction model class.
- `TransactionManager.java` - Business logic, file operations, calculations.
- `transactions.txt` - Data file for transactions.
- `.gitignore` - Java/NetBeans ignore rules.

## Sample Data Format
Data file name: `transactions.txt`

Format per line:
```
TransactionID|Type|Category|Amount|Description|Date
```

Example:
```
T001|Income|Allowance|5000.00|Monthly allowance|2026-05-16
T002|Expense|Food|120.00|Lunch at university|2026-05-16
T003|Expense|Transport|45.00|BTS fare|2026-05-16
```

## What I Learned
- How to build a complete desktop CRUD app using Java Swing
- How to separate responsibilities using OOP classes
- How to save/load records using text file handling
- How to validate user input and display clear GUI messages
- How to create a clean beginner-friendly project for GitHub

## Future Improvements
- Add MySQL database
- Add login system
- Add user accounts
- Add budget limit warning
- Add pie chart or bar chart
- Export report to PDF
- Add dark mode
- Improve UI design
