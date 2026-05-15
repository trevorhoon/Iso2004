# Movie Ticket Booking System

## 1) Project Title
**Movie Ticket Booking System**

## 2) Short Description
A beginner-friendly Java Swing desktop project for managing movies, showtimes, and ticket bookings using **file handling** (`.txt`) only.

## 3) Features
- Dashboard with total movies, showtimes, booked tickets, and revenue
- Movie Management: add, view, search, update, delete
- Showtime Management: add, view, update, delete
- Ticket Booking: select showtime + seat and book ticket
- Booking History: view all records and cancel by booking ID
- Seat conflict prevention for same movie/date/time/hall
- Data persistence using `movies.txt`, `showtimes.txt`, and `bookings.txt`

## 4) Technologies Used
- Java (OOP)
- Java Swing (GUI)
- File Handling (`BufferedReader`, `BufferedWriter`)
- NetBeans-compatible project structure

## 5) How to Run in NetBeans
1. Open NetBeans.
2. Create/Open a Java project and place this repository content.
3. Set `src/movieticketbookingsystem/Main.java` as Main Class.
4. Build and Run.

## 6) Project Structure
```
Movie Ticket Booking System/
├── src/
│   └── movieticketbookingsystem/
│       ├── Main.java
│       ├── MainFrame.java
│       ├── Movie.java
│       ├── MovieManager.java
│       ├── Showtime.java
│       ├── ShowtimeManager.java
│       ├── Booking.java
│       └── BookingManager.java
├── movies.txt
├── showtimes.txt
├── bookings.txt
├── .gitignore
└── README.md
```

## 7) Sample Data Format
### movies.txt
`MovieID|Title|Genre|Duration|Language|Rating`

### showtimes.txt
`ShowtimeID|MovieID|MovieTitle|ShowDate|ShowTime|HallNumber|TicketPrice`

### bookings.txt
`BookingID|CustomerName|PhoneNumber|MovieTitle|ShowDate|ShowTime|HallNumber|SeatNumber|TicketPrice|BookingStatus`

## 8) Screenshots
- Dashboard screen: *(add screenshot here)*
- Movie Management screen: *(add screenshot here)*
- Showtime Management screen: *(add screenshot here)*
- Ticket Booking screen: *(add screenshot here)*
- Booking History screen: *(add screenshot here)*

## 9) What I Learned
- Practical OOP design in Java
- Swing form design and event handling
- File-based CRUD operations
- Validation and error handling with `JOptionPane`
- Managing relationships between movie, showtime, and booking

## 10) Future Improvements
- Add MySQL database
- Add login system
- Add admin and cashier roles
- Add seat map UI with clickable buttons
- Add receipt generation
- Add ticket printing
- Add daily sales report
- Add discount system
- Add online booking simulation
- Improve UI design
