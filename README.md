# Restaurant Ordering System

## 1. Project Title
**Restaurant Ordering System**

## 2. Short Description
A beginner-friendly Java Swing desktop application for restaurant menu management and POS ordering. This project uses Object-Oriented Programming (OOP) and file handling (`.txt`) instead of a database.

## 3. Features
- Dashboard screen
- Menu management
  - Add menu item
  - View all menu items
  - Search menu item by ID or name
  - Update menu item
  - Delete menu item
- Order / POS screen
  - Create customer order
  - Add multiple items to one order
  - Remove order item
  - Calculate subtotal, VAT (7%), and net total
  - Accept money paid and calculate change
  - Prevent ordering unavailable items
- Order history screen
  - Save order history to file
  - Load and display saved order history
- Validation and user feedback
  - Clear validation messages with `JOptionPane`

## 4. Technologies Used
- Java (JDK 8+)
- Java Swing
- OOP (classes, objects, encapsulation)
- File Handling (`BufferedReader`, `BufferedWriter`)
- NetBeans-compatible project structure

## 5. How to Run in NetBeans
1. Open NetBeans.
2. Click **File > Open Project**.
3. Select this project folder.
4. Ensure JDK is configured.
5. Run `Main.java` from `src/restaurantorderingsystem/Main.java`.

## 6. Project Structure
```
Restaurant Ordering System/
├── src/
│   └── restaurantorderingsystem/
│       ├── Main.java
│       ├── MainFrame.java
│       ├── MenuItem.java
│       ├── MenuManager.java
│       ├── Order.java
│       ├── OrderItem.java
│       └── OrderManager.java
├── menu_items.txt
├── orders.txt
├── .gitignore
└── README.md
```

## 7. Sample Data Format
### `menu_items.txt`
`ItemID|ItemName|Category|Price|AvailabilityStatus`

Example:
- `F001|Chicken Fried Rice|Food|65.00|Available`
- `D001|Thai Milk Tea|Drink|45.00|Available`
- `DS001|Chocolate Cake|Dessert|80.00|Available`

### `orders.txt`
`OrderID|ItemID|ItemName|Quantity|Price|Subtotal|VAT|NetTotal|MoneyPaid|ChangeAmount|DateTime`

Example:
- `O001|F001|Chicken Fried Rice|2|65.00|130.00|9.10|139.10|200.00|60.90|2026-05-16 14:30`
- `O002|D001|Thai Milk Tea|1|45.00|45.00|3.15|48.15|100.00|51.85|2026-05-16 14:35`

## 9. What I Learned
- How to structure a Java Swing app using OOP.
- How to separate responsibilities between data classes, managers, and GUI classes.
- How to validate user input and show useful messages.
- How to store and load persistent data using text files.
- How to build a clean project for a GitHub portfolio.

## 10. Future Improvements
- Add MySQL database
- Add login system
- Add admin and cashier roles
- Add table number management
- Add receipt printing
- Add daily sales report
- Add best-selling item report
- Add discount system
- Improve UI design
