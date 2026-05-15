# Attendance Management System

A beginner-friendly Java Swing desktop application for managing students and daily attendance using file handling (`.txt`) only. This project is designed for NetBeans and showcases Object-Oriented Programming concepts for a GitHub portfolio.

## Features

- Dashboard with:
  - Total students
  - Total present
  - Total absent
  - Total late
- Student Management:
  - Add student
  - View all students
  - Search student by ID or name
  - Update student information
  - Delete student
- Attendance Management:
  - Mark attendance (Present / Absent / Late)
  - Select attendance date
  - View attendance records
  - Search attendance by student ID, name, or date
- Attendance Summary:
  - Calculate total Present, Absent, and Late records
- File handling:
  - Save student data to `students.txt`
  - Save attendance data to `attendance_records.txt`
  - Load both files when the program starts
- Validation with clear `JOptionPane` messages

## Technologies Used

- Java (JDK 8+)
- Java Swing
- Object-Oriented Programming (OOP)
- File Handling (`BufferedReader`, `BufferedWriter`)
- NetBeans-compatible project structure

## How to Run in NetBeans

1. Open NetBeans.
2. Click **File > Open Project** and choose this project folder.
3. Ensure `src/attendancemanagementsystem/Main.java` is the main class.
4. Run the project.

You can also run from terminal:

```bash
javac -d out src/attendancemanagementsystem/*.java
java -cp out attendancemanagementsystem.Main
```

## Project Structure

```text
Attendance Management System/
├── src/
│   └── attendancemanagementsystem/
│       ├── Main.java
│       ├── MainFrame.java
│       ├── Student.java
│       ├── StudentManager.java
│       ├── AttendanceRecord.java
│       └── AttendanceManager.java
├── students.txt
├── attendance_records.txt
├── .gitignore
└── README.md
```

## Sample Data Format

### `students.txt`
Format:

```text
StudentID|FirstName|LastName|Gender|Major|PhoneNumber|Email
```

Example:

```text
S001|Hein|Htet San|Male|Computer Science|0912345678|demo@gmail.com
S002|John|Doe|Male|Information Technology|0987654321|john@example.com
```

### `attendance_records.txt`
Format:

```text
AttendanceID|StudentID|StudentName|Date|Status|Remark
```

Example:

```text
A001|S001|Hein Htet San|2026-05-16|Present|On time
A002|S002|John Doe|2026-05-16|Late|Arrived 10 minutes late
```

## Screenshots

> Add screenshots after running the app.

- Dashboard Screen (placeholder)
- Student Management Screen (placeholder)
- Mark Attendance Screen (placeholder)
- Attendance Records Screen (placeholder)
- Attendance Summary Screen (placeholder)

## What I Learned

- How to build a complete desktop application with Java Swing
- How to apply OOP using manager and model classes
- How to validate user input for better user experience
- How to store and load data using text files
- How to organize a project for a clean GitHub portfolio

## Future Improvements

- Add MySQL database
- Add login system
- Add admin and teacher roles
- Add monthly attendance report
- Add export to PDF
- Add attendance percentage calculation
- Add dashboard charts
- Improve UI design
