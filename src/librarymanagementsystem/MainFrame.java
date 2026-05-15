package librarymanagementsystem;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class MainFrame extends JFrame {
    private final BookManager bookManager;
    private final MemberManager memberManager;
    private final BorrowManager borrowManager;

    private JTextField txtBookId, txtTitle, txtAuthor, txtCategory, txtYear, txtQuantity, txtAvailable, txtSearchBook;
    private JTable tblBooks;

    private JTextField txtMemberId, txtMemberName, txtPhone, txtEmail, txtSearchMember;
    private JTable tblMembers;

    private JTextField txtBorrowMemberId, txtBorrowBookId;
    private JTextField txtReturnMemberId, txtReturnBookId;
    private JTable tblBorrowRecords;

    public MainFrame() {
        setTitle("Library Management System");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        bookManager = new BookManager("books.txt");
        memberManager = new MemberManager("members.txt");
        borrowManager = new BorrowManager("borrow_records.txt");

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Dashboard", createDashboardPanel());
        tabs.addTab("Book Management", createBookPanel());
        tabs.addTab("Member Management", createMemberPanel());
        tabs.addTab("Borrow Book", createBorrowPanel());
        tabs.addTab("Return Book", createReturnPanel());
        tabs.addTab("Transaction History", createHistoryPanel());

        add(tabs);
        refreshAllTables();
    }

    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setFont(new Font("SansSerif", Font.PLAIN, 16));
        area.setText("Welcome to Library Management System\n\n" +
                "Use tabs above to manage books, members, borrowing and returning.");
        panel.add(area, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createBookPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        JPanel form = new JPanel(new GridLayout(9, 2, 5, 5));

        txtBookId = new JTextField(); txtTitle = new JTextField(); txtAuthor = new JTextField();
        txtCategory = new JTextField(); txtYear = new JTextField(); txtQuantity = new JTextField();
        txtAvailable = new JTextField(); txtSearchBook = new JTextField();

        JButton btnAddBook = new JButton("Add Book");
        JButton btnUpdateBook = new JButton("Update Book");
        JButton btnDeleteBook = new JButton("Delete Book");
        JButton btnSearchBook = new JButton("Search Book");
        JButton btnClear = new JButton("Clear");

        form.add(new JLabel("Book ID")); form.add(txtBookId);
        form.add(new JLabel("Title")); form.add(txtTitle);
        form.add(new JLabel("Author")); form.add(txtAuthor);
        form.add(new JLabel("Category")); form.add(txtCategory);
        form.add(new JLabel("Published Year")); form.add(txtYear);
        form.add(new JLabel("Quantity")); form.add(txtQuantity);
        form.add(new JLabel("Available Quantity")); form.add(txtAvailable);
        form.add(new JLabel("Search (ID/Title/Author)")); form.add(txtSearchBook);
        form.add(btnAddBook); form.add(btnUpdateBook);

        JPanel actions = new JPanel();
        actions.add(btnDeleteBook); actions.add(btnSearchBook); actions.add(btnClear);

        tblBooks = new JTable();
        panel.add(form, BorderLayout.NORTH);
        panel.add(new JScrollPane(tblBooks), BorderLayout.CENTER);
        panel.add(actions, BorderLayout.SOUTH);

        btnAddBook.addActionListener(e -> addBook());
        btnUpdateBook.addActionListener(e -> updateBook());
        btnDeleteBook.addActionListener(e -> deleteBook());
        btnSearchBook.addActionListener(e -> searchBook());
        btnClear.addActionListener(e -> clearBookFields());
        return panel;
    }

    private JPanel createMemberPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        JPanel form = new JPanel(new GridLayout(6, 2, 5, 5));

        txtMemberId = new JTextField(); txtMemberName = new JTextField(); txtPhone = new JTextField();
        txtEmail = new JTextField(); txtSearchMember = new JTextField();

        JButton btnAddMember = new JButton("Add Member");
        JButton btnUpdateMember = new JButton("Update Member");
        JButton btnDeleteMember = new JButton("Delete Member");
        JButton btnSearchMember = new JButton("Search Member");
        JButton btnClear = new JButton("Clear");

        form.add(new JLabel("Member ID")); form.add(txtMemberId);
        form.add(new JLabel("Full Name")); form.add(txtMemberName);
        form.add(new JLabel("Phone")); form.add(txtPhone);
        form.add(new JLabel("Email")); form.add(txtEmail);
        form.add(new JLabel("Search (ID/Name)")); form.add(txtSearchMember);
        form.add(btnAddMember); form.add(btnUpdateMember);

        JPanel actions = new JPanel();
        actions.add(btnDeleteMember); actions.add(btnSearchMember); actions.add(btnClear);

        tblMembers = new JTable();
        panel.add(form, BorderLayout.NORTH);
        panel.add(new JScrollPane(tblMembers), BorderLayout.CENTER);
        panel.add(actions, BorderLayout.SOUTH);

        btnAddMember.addActionListener(e -> addMember());
        btnUpdateMember.addActionListener(e -> updateMember());
        btnDeleteMember.addActionListener(e -> deleteMember());
        btnSearchMember.addActionListener(e -> searchMember());
        btnClear.addActionListener(e -> clearMemberFields());
        return panel;
    }

    private JPanel createBorrowPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        txtBorrowMemberId = new JTextField();
        txtBorrowBookId = new JTextField();
        JButton btnBorrowBook = new JButton("Borrow Book");
        JButton btnClear = new JButton("Clear");
        panel.add(new JLabel("Member ID")); panel.add(txtBorrowMemberId);
        panel.add(new JLabel("Book ID")); panel.add(txtBorrowBookId);
        panel.add(btnBorrowBook); panel.add(btnClear);
        btnBorrowBook.addActionListener(e -> borrowBook());
        btnClear.addActionListener(e -> { txtBorrowMemberId.setText(""); txtBorrowBookId.setText(""); });
        return panel;
    }

    private JPanel createReturnPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        txtReturnMemberId = new JTextField();
        txtReturnBookId = new JTextField();
        JButton btnReturnBook = new JButton("Return Book");
        JButton btnClear = new JButton("Clear");
        panel.add(new JLabel("Member ID")); panel.add(txtReturnMemberId);
        panel.add(new JLabel("Book ID")); panel.add(txtReturnBookId);
        panel.add(btnReturnBook); panel.add(btnClear);
        btnReturnBook.addActionListener(e -> returnBook());
        btnClear.addActionListener(e -> { txtReturnMemberId.setText(""); txtReturnBookId.setText(""); });
        return panel;
    }

    private JPanel createHistoryPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        tblBorrowRecords = new JTable();
        panel.add(new JScrollPane(tblBorrowRecords), BorderLayout.CENTER);
        return panel;
    }

    private void refreshAllTables() { refreshBookTable(bookManager.getAllBooks()); refreshMemberTable(memberManager.getAllMembers()); refreshBorrowTable(); }
    private void refreshBookTable(List<Book> books) {
        DefaultTableModel model = new DefaultTableModel(new String[]{"Book ID", "Title", "Author", "Category", "Year", "Qty", "Available"}, 0);
        for (Book b : books) model.addRow(new Object[]{b.getBookId(), b.getTitle(), b.getAuthor(), b.getCategory(), b.getPublishedYear(), b.getQuantity(), b.getAvailableQuantity()});
        tblBooks.setModel(model);
    }
    private void refreshMemberTable(List<Member> members) {
        DefaultTableModel model = new DefaultTableModel(new String[]{"Member ID", "Full Name", "Phone", "Email"}, 0);
        for (Member m : members) model.addRow(new Object[]{m.getMemberId(), m.getFullName(), m.getPhoneNumber(), m.getEmail()});
        tblMembers.setModel(model);
    }
    private void refreshBorrowTable() {
        DefaultTableModel model = new DefaultTableModel(new String[]{"Transaction ID", "Member ID", "Member Name", "Book ID", "Book Title", "Borrow Date", "Return Date", "Status"}, 0);
        for (BorrowRecord r : borrowManager.getAllRecords()) model.addRow(new Object[]{r.getTransactionId(), r.getMemberId(), r.getMemberName(), r.getBookId(), r.getBookTitle(), r.getBorrowDate(), r.getReturnDate(), r.getStatus()});
        tblBorrowRecords.setModel(model);
    }

    private void addBook() {
        try {
            Book book = validateAndBuildBook();
            if (bookManager.addBook(book)) { JOptionPane.showMessageDialog(this, "Book added successfully."); clearBookFields(); refreshBookTable(bookManager.getAllBooks()); }
            else JOptionPane.showMessageDialog(this, "Book ID already exists.");
        } catch (IllegalArgumentException ex) { JOptionPane.showMessageDialog(this, ex.getMessage()); }
    }
    private void updateBook() { try { Book book = validateAndBuildBook(); if (bookManager.updateBook(book)) { JOptionPane.showMessageDialog(this, "Book updated successfully."); clearBookFields(); refreshBookTable(bookManager.getAllBooks()); } else JOptionPane.showMessageDialog(this, "Book not found."); } catch (IllegalArgumentException ex) { JOptionPane.showMessageDialog(this, ex.getMessage()); } }
    private void deleteBook() { String id = txtBookId.getText().trim(); if (id.isEmpty()) { JOptionPane.showMessageDialog(this, "Book ID must not be empty."); return; } if (bookManager.deleteBook(id)) { JOptionPane.showMessageDialog(this, "Book deleted successfully."); clearBookFields(); refreshBookTable(bookManager.getAllBooks()); } else JOptionPane.showMessageDialog(this, "Book not found."); }
    private void searchBook() { String key = txtSearchBook.getText().trim(); refreshBookTable(key.isEmpty() ? bookManager.getAllBooks() : bookManager.searchBooks(key)); }

    private Book validateAndBuildBook() {
        String id = txtBookId.getText().trim(), title = txtTitle.getText().trim(), author = txtAuthor.getText().trim(), category = txtCategory.getText().trim();
        if (id.isEmpty()) throw new IllegalArgumentException("Book ID must not be empty.");
        if (title.isEmpty()) throw new IllegalArgumentException("Book title must not be empty.");
        if (author.isEmpty()) throw new IllegalArgumentException("Author must not be empty.");
        int year = Integer.parseInt(txtYear.getText().trim()), quantity = Integer.parseInt(txtQuantity.getText().trim()), available = Integer.parseInt(txtAvailable.getText().trim());
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be a valid positive number.");
        if (available < 0 || available > quantity) throw new IllegalArgumentException("Available quantity must be between 0 and total quantity.");
        return new Book(id, title, author, category, year, quantity, available);
    }
    private void clearBookFields() { txtBookId.setText(""); txtTitle.setText(""); txtAuthor.setText(""); txtCategory.setText(""); txtYear.setText(""); txtQuantity.setText(""); txtAvailable.setText(""); txtSearchBook.setText(""); }

    private void addMember() { try { Member m = validateAndBuildMember(); if (memberManager.addMember(m)) { JOptionPane.showMessageDialog(this, "Member added successfully."); clearMemberFields(); refreshMemberTable(memberManager.getAllMembers()); } else JOptionPane.showMessageDialog(this, "Member ID already exists."); } catch (IllegalArgumentException ex) { JOptionPane.showMessageDialog(this, ex.getMessage()); } }
    private void updateMember() { try { Member m = validateAndBuildMember(); if (memberManager.updateMember(m)) { JOptionPane.showMessageDialog(this, "Member updated successfully."); clearMemberFields(); refreshMemberTable(memberManager.getAllMembers()); } else JOptionPane.showMessageDialog(this, "Member not found."); } catch (IllegalArgumentException ex) { JOptionPane.showMessageDialog(this, ex.getMessage()); } }
    private void deleteMember() { String id = txtMemberId.getText().trim(); if (id.isEmpty()) { JOptionPane.showMessageDialog(this, "Member ID must not be empty."); return; } if (memberManager.deleteMember(id)) { JOptionPane.showMessageDialog(this, "Member deleted successfully."); clearMemberFields(); refreshMemberTable(memberManager.getAllMembers()); } else JOptionPane.showMessageDialog(this, "Member not found."); }
    private void searchMember() { String key = txtSearchMember.getText().trim(); refreshMemberTable(key.isEmpty() ? memberManager.getAllMembers() : memberManager.searchMembers(key)); }
    private Member validateAndBuildMember() {
        String id = txtMemberId.getText().trim(), name = txtMemberName.getText().trim(), phone = txtPhone.getText().trim(), email = txtEmail.getText().trim();
        if (id.isEmpty()) throw new IllegalArgumentException("Member ID must not be empty.");
        if (name.isEmpty()) throw new IllegalArgumentException("Member name must not be empty.");
        if (!email.contains("@") || !email.contains(".")) throw new IllegalArgumentException("Please enter a valid email format.");
        return new Member(id, name, phone, email);
    }
    private void clearMemberFields() { txtMemberId.setText(""); txtMemberName.setText(""); txtPhone.setText(""); txtEmail.setText(""); txtSearchMember.setText(""); }

    private void borrowBook() {
        String memberId = txtBorrowMemberId.getText().trim(), bookId = txtBorrowBookId.getText().trim();
        Member member = memberManager.findMemberById(memberId);
        Book book = bookManager.findBookById(bookId);
        if (member == null) { JOptionPane.showMessageDialog(this, "Member not found."); return; }
        if (book == null) { JOptionPane.showMessageDialog(this, "Book not found."); return; }
        if (book.getAvailableQuantity() <= 0) { JOptionPane.showMessageDialog(this, "This book is not available for borrowing."); return; }
        if (borrowManager.borrowBook(member, book)) {
            bookManager.saveBooks();
            JOptionPane.showMessageDialog(this, "Book borrowed successfully.");
            txtBorrowMemberId.setText(""); txtBorrowBookId.setText("");
            refreshAllTables();
        }
    }

    private void returnBook() {
        String memberId = txtReturnMemberId.getText().trim(), bookId = txtReturnBookId.getText().trim();
        if (borrowManager.returnBook(memberId, bookId, bookManager)) {
            bookManager.saveBooks();
            JOptionPane.showMessageDialog(this, "Book returned successfully.");
            txtReturnMemberId.setText(""); txtReturnBookId.setText("");
            refreshAllTables();
        } else {
            JOptionPane.showMessageDialog(this, "Cannot return. This book was not borrowed by the member.");
        }
    }
}
