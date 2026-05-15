# Student Management System

A beginner-friendly Java Swing desktop application to manage student records using Object-Oriented Programming and file handling.

This project is designed for learning and for building a clean GitHub portfolio as a Computer Science student.

## Features

- Add student
- View all students
- Search student by ID or name
- Update student information
- Delete student
- Save student data to `students.txt`
- Load student data from `students.txt` when the program starts
- Show validation messages for empty fields
- Show success and error messages with `JOptionPane`

## Technologies Used

- Java (JDK 8+)
- Java Swing (GUI)
- Object-Oriented Programming (OOP)
- File Handling (`BufferedReader`, `BufferedWriter`)
- NetBeans IDE (compatible)

## How to Run in NetBeans

1. Open NetBeans.
2. Choose **File > Open Project**.
3. Select this project folder (`Student Management System` / this repository).
4. Make sure your JDK is configured.
5. Run `StudentManagementSystemFrame.java`.

You can also run from terminal:

```bash
javac -d out src/studentmanagementsystem/*.java
java -cp out studentmanagementsystem.StudentManagementSystemFrame
```

## Project Structure

```text
.
├── .gitignore
├── README.md
├── students.txt
└── src
    └── studentmanagementsystem
        ├── Student.java
        ├── StudentManager.java
        └── StudentManagementSystemFrame.java
```

## Screenshots

> Add your screenshots here after running the app.

- `docs/images/main-window.png`
- `docs/images/add-student-success.png`
- `docs/images/search-result.png`

## What I Learned

- How to design a Java desktop UI with Swing
- How to separate responsibilities using classes (model, manager, UI)
- How to perform CRUD operations without a database
- How to save and load data using text files
- How to validate user input and show feedback dialogs

## Future Improvements

- Add MySQL database
- Add login system
- Add admin dashboard
- Improve UI design
