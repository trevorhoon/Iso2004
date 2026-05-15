# POS Inventory System

## Short Description
A beginner-friendly Java Swing desktop application for product inventory and point-of-sale (POS) management using file handling (`.txt`) instead of a database.

## Features
- Add new product
- View all products
- Search product by Product ID or Product Name
- Update product information
- Delete product
- Sell product with stock check
- Auto-reduce stock after successful sale
- Prevent sale when stock is not enough
- Calculate total price, VAT (7%), net price, and customer change
- Save and load product data from `products.txt`
- Save sales history to `sales.txt`
- Show success/error messages using `JOptionPane`

## Technologies Used
- Java (OOP)
- Java Swing (`JFrame`, `JPanel`, `JLabel`, `JTextField`, `JButton`, `JTable`, `JScrollPane`, `JOptionPane`)
- File handling (`BufferedReader`, `BufferedWriter`, `FileReader`, `FileWriter`)

## How to Run in NetBeans
1. Open NetBeans.
2. Click **File > Open Project** and select this project folder.
3. Ensure the source package is `posinventorysystem` under `src`.
4. Run `Main.java`.
5. The app will load product data from `data/products.txt` automatically.

## Project Structure
```
POS Inventory System/
├── src/
│   └── posinventorysystem/
│       ├── Main.java
│       ├── MainFrame.java
│       ├── Product.java
│       ├── ProductManager.java
│       ├── Sale.java
│       └── SalesManager.java
├── data/
│   ├── products.txt
│   └── sales.txt
├── .gitignore
└── README.md
```

## Sample Data Format
### `products.txt`
Format:
`ProductID|ProductName|Category|Quantity|Price`

Example:
`P001|Coca Cola|Drink|20|25.00`

### `sales.txt`
Format:
`SaleID|ProductID|ProductName|QuantitySold|TotalPrice|VAT|NetPrice|MoneyPaid|ChangeAmount|DateTime`

Example:
`S001|P001|Coca Cola|2|50.00|3.50|53.50|100.00|46.50|2026-05-16 14:30`

## Screenshots
- Dashboard screen
- Product Management screen
- Sales / POS screen
- Transaction History screen

## What I Learned
- Building a desktop app with Java Swing components
- Applying OOP with separate manager and model classes
- Implementing input validation for POS operations
- Using file handling for persistent storage
- Designing a beginner-friendly project structure for GitHub

## Future Improvements
- Add MySQL database
- Add login system
- Add admin and cashier roles
- Add receipt printing
- Add daily sales report
- Add barcode support
- Improve UI design
