# Library Management System

## Short Description
Library Management System is a beginner-friendly Java Swing desktop application for managing books, members, and borrowing/returning records using file handling (`.txt` files) instead of a database.

## Features
- Add, view, search, update, and delete books
- Register, view, search, update, and delete members
- Borrow and return books
- Prevent borrowing when available quantity is 0
- Prevent returning books that are not currently borrowed
- Store and load all data from text files
- Show clear success/error messages with `JOptionPane`

## Technologies Used
- Java (OOP)
- Java Swing GUI
- File Handling (`BufferedReader`, `FileWriter`, `PrintWriter`)
- NetBeans-compatible project structure

## How to Run in NetBeans
1. Open NetBeans.
2. Create/Open a Java project and point to this repository folder.
3. Ensure source files are under `src/librarymanagementsystem/`.
4. Run `Main.java`.
5. The app will load data from:
   - `books.txt`
   - `members.txt`
   - `borrow_records.txt`

## Project Structure
```
Library Management System/
├── src/
│   └── librarymanagementsystem/
│       ├── Main.java
│       ├── MainFrame.java
│       ├── Book.java
│       ├── BookManager.java
│       ├── Member.java
│       ├── MemberManager.java
│       ├── BorrowRecord.java
│       └── BorrowManager.java
├── books.txt
├── members.txt
├── borrow_records.txt
├── .gitignore
└── README.md
```

## Sample Data Format
### books.txt
`BookID|Title|Author|Category|PublishedYear|Quantity|AvailableQuantity`

Example:
`B001|Introduction to Java|John Smith|Programming|2022|5|5`

### members.txt
`MemberID|FullName|PhoneNumber|Email`

Example:
`M001|Hein Htet San|0912345678|demo@gmail.com`

### borrow_records.txt
`TransactionID|MemberID|MemberName|BookID|BookTitle|BorrowDate|ReturnDate|Status`

Example:
`T001|M001|Hein Htet San|B001|Introduction to Java|2026-05-16|-|Borrowed`

## What I Learned
- Designing a Java desktop app with Swing
- Applying OOP using separate model and manager classes
- Managing persistent data with text files
- Implementing input validation and user-friendly error handling

## Future Improvements
- Add MySQL database
- Add login system
- Add admin and librarian roles
- Add overdue fine calculation
- Add due date reminder
- Add book cover image
- Add report generation
- Improve UI design
