# Java Banking System (NetBeans + Swing)

A desktop **Banking System** developed in **Java Swing** using **NetBeans IDE**. This project demonstrates core object-oriented programming concepts and persistent data management via **file handling** (no database dependency).

It includes both user and admin workflows such as account registration/login, money operations, profile management, and customer overview.

---

## Project Description

This application simulates common banking operations in a desktop environment. Instead of using a database, it stores and retrieves data from local files, making it suitable for learning Java fundamentals such as:

- GUI design with Swing
- Event-driven programming
- OOP design (classes, encapsulation, modularity)
- Data persistence with file I/O
- Basic role-based flow (user vs. admin)

---

## Features

- User registration
- Secure login flow
- Deposit funds
- Withdraw funds
- Transfer funds between accounts
- View account balance
- Transaction history
- Change password
- Profile editing
- Admin customer view
- File-based data persistence

---

## Technologies Used

- **Language:** Java
- **IDE:** NetBeans
- **GUI Framework:** Java Swing
- **Data Storage:** File Handling (text/binary files)
- **Version Control:** Git + GitHub

---

## How to Run (NetBeans)

1. **Clone the repository**
   ```bash
   git clone https://github.com/<your-username>/<your-repo-name>.git
   ```
2. Open **NetBeans IDE**.
3. Go to **File > Open Project**.
4. Select this project folder and click **Open Project**.
5. Wait for NetBeans to resolve project configuration.
6. Right-click the project in the sidebar and choose **Run**.
7. Use the UI to register/login and test banking operations.

> Note: Since this project uses file handling, required data files/folders should remain in their expected relative paths.

---

## Project Structure

```text
<project-root>/
├── src/                    # Java source files (UI, logic, models)
├── nbproject/              # NetBeans project configuration
├── build/                  # Build output (generated)
├── dist/                   # Packaged output (generated)
├── README.md
└── .gitignore
```

---

## Screenshots

> Replace these placeholders with actual screenshots from your project.

- Login Screen: `![Login Screen](docs/screenshots/login-placeholder.png)`
- Registration Screen: `![Registration Screen](docs/screenshots/register-placeholder.png)`
- Dashboard / Main Menu: `![Dashboard](docs/screenshots/dashboard-placeholder.png)`
- Transaction History: `![Transaction History](docs/screenshots/history-placeholder.png)`
- Admin Customer View: `![Admin View](docs/screenshots/admin-placeholder.png)`

---

## What I Learned

Through this project, I practiced and improved:

- Designing desktop applications with Java Swing
- Building modular Java classes using OOP principles
- Managing persistent data using file read/write operations
- Implementing multi-step user workflows (register, login, transact)
- Validating user input and handling edge cases
- Structuring a real coursework-style software project in NetBeans

---

## Future Improvements

- Migrate from file handling to a **MySQL** relational database
- Rebuild backend services with **Spring Boot**
- Add proper password hashing and stronger authentication flow
- Add form validation and improved error handling across all screens
- Introduce unit/integration tests
- Improve UI/UX design and responsiveness
- Add exportable transaction statements

---

## Privacy & Sample Data Note

This repository should not include real personal data. Use only dummy/test values for:

- Names
- Emails
- Phone numbers
- Addresses
- Passwords

