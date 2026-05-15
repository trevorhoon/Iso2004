import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 * Main GUI for the Personal Expense Tracker.
 */
public class MainFrame extends JFrame {
    private final TransactionManager transactionManager;

    private JTextField txtTransactionId;
    private JTextField txtAmount;
    private JTextField txtDescription;
    private JTextField txtDate;
    private JTextField txtSearch;
    private JTextField txtMonthFilter;

    private JComboBox<String> cmbType;
    private JComboBox<String> cmbCategory;

    private JButton btnAddTransaction;
    private JButton btnUpdateTransaction;
    private JButton btnDeleteTransaction;
    private JButton btnSearchTransaction;
    private JButton btnClear;
    private JButton btnViewSummary;
    private JButton btnFilterMonth;
    private JButton btnViewAll;

    private JTable tblTransactions;
    private DefaultTableModel tableModel;

    private JLabel lblTotalIncome;
    private JLabel lblTotalExpense;
    private JLabel lblBalance;

    public MainFrame() {
        transactionManager = new TransactionManager("transactions.txt");
        initializeFrame();
        initializeComponents();
        registerEvents();
        loadTransactionsToTable(transactionManager.getAllTransactions());
        updateDashboardLabels();
    }

    private void initializeFrame() {
        setTitle("Personal Expense Tracker");
        setSize(1000, 620);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
    }

    private void initializeComponents() {
        JPanel panelTop = new JPanel(new GridLayout(3, 1, 6, 6));

        JPanel panelDashboard = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5));
        lblTotalIncome = new JLabel("Total Income: 0.00");
        lblTotalExpense = new JLabel("Total Expense: 0.00");
        lblBalance = new JLabel("Balance: 0.00");
        panelDashboard.add(lblTotalIncome);
        panelDashboard.add(lblTotalExpense);
        panelDashboard.add(lblBalance);

        JPanel panelForm = new JPanel(new GridLayout(2, 6, 6, 6));
        txtTransactionId = new JTextField();
        cmbType = new JComboBox<>(new String[]{"Income", "Expense"});
        cmbCategory = new JComboBox<>(new String[]{
            "Salary", "Allowance", "Gift", "Other Income",
            "Food", "Transport", "Rent", "Shopping", "Education", "Entertainment", "Other Expense"
        });
        txtAmount = new JTextField();
        txtDescription = new JTextField();
        txtDate = new JTextField();

        panelForm.add(new JLabel("Transaction ID"));
        panelForm.add(new JLabel("Type"));
        panelForm.add(new JLabel("Category"));
        panelForm.add(new JLabel("Amount"));
        panelForm.add(new JLabel("Description"));
        panelForm.add(new JLabel("Date (YYYY-MM-DD)"));

        panelForm.add(txtTransactionId);
        panelForm.add(cmbType);
        panelForm.add(cmbCategory);
        panelForm.add(txtAmount);
        panelForm.add(txtDescription);
        panelForm.add(txtDate);

        JPanel panelActions = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 5));
        btnAddTransaction = new JButton("Add Transaction");
        btnUpdateTransaction = new JButton("Update Transaction");
        btnDeleteTransaction = new JButton("Delete Transaction");
        btnSearchTransaction = new JButton("Search");
        btnClear = new JButton("Clear");
        btnViewSummary = new JButton("View Summary");
        btnFilterMonth = new JButton("Filter Month");
        btnViewAll = new JButton("View All");

        txtSearch = new JTextField(12);
        txtMonthFilter = new JTextField(7);
        txtMonthFilter.setToolTipText("YYYY-MM");

        panelActions.add(btnAddTransaction);
        panelActions.add(btnUpdateTransaction);
        panelActions.add(btnDeleteTransaction);
        panelActions.add(new JLabel("Search"));
        panelActions.add(txtSearch);
        panelActions.add(btnSearchTransaction);
        panelActions.add(new JLabel("Month"));
        panelActions.add(txtMonthFilter);
        panelActions.add(btnFilterMonth);
        panelActions.add(btnViewAll);
        panelActions.add(btnViewSummary);
        panelActions.add(btnClear);

        panelTop.add(panelDashboard);
        panelTop.add(panelForm);
        panelTop.add(panelActions);

        String[] columns = {"Transaction ID", "Type", "Category", "Amount", "Description", "Date"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblTransactions = new JTable(tableModel);
        tblTransactions.setPreferredScrollableViewportSize(new Dimension(950, 360));
        JScrollPane scrollPane = new JScrollPane(tblTransactions);

        add(panelTop, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void registerEvents() {
        btnAddTransaction.addActionListener(e -> addTransaction());
        btnUpdateTransaction.addActionListener(e -> updateTransaction());
        btnDeleteTransaction.addActionListener(e -> deleteTransaction());
        btnSearchTransaction.addActionListener(e -> searchTransactions());
        btnFilterMonth.addActionListener(e -> filterByMonth());
        btnViewAll.addActionListener(e -> loadTransactionsToTable(transactionManager.getAllTransactions()));
        btnViewSummary.addActionListener(e -> viewMonthlySummary());
        btnClear.addActionListener(e -> clearInputFields());

        tblTransactions.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tblTransactions.getSelectedRow() >= 0) {
                int row = tblTransactions.getSelectedRow();
                txtTransactionId.setText(tableModel.getValueAt(row, 0).toString());
                cmbType.setSelectedItem(tableModel.getValueAt(row, 1).toString());
                cmbCategory.setSelectedItem(tableModel.getValueAt(row, 2).toString());
                txtAmount.setText(tableModel.getValueAt(row, 3).toString());
                txtDescription.setText(tableModel.getValueAt(row, 4).toString());
                txtDate.setText(tableModel.getValueAt(row, 5).toString());
            }
        });
    }

    private void addTransaction() {
        Transaction transaction = validateAndBuildTransaction();
        if (transaction == null) {
            return;
        }

        boolean success = transactionManager.addTransaction(transaction);
        if (success) {
            JOptionPane.showMessageDialog(this, "Transaction added successfully.");
            refreshTableAndDashboard();
            clearInputFields();
        } else {
            JOptionPane.showMessageDialog(this, "Transaction ID already exists.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateTransaction() {
        int selectedRow = tblTransactions.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a transaction to update.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String originalId = tableModel.getValueAt(selectedRow, 0).toString();
        Transaction updatedTransaction = validateAndBuildTransaction();
        if (updatedTransaction == null) {
            return;
        }

        boolean success = transactionManager.updateTransaction(originalId, updatedTransaction);
        if (success) {
            JOptionPane.showMessageDialog(this, "Transaction updated successfully.");
            refreshTableAndDashboard();
            clearInputFields();
        } else {
            JOptionPane.showMessageDialog(this, "Unable to update transaction. Check transaction ID.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteTransaction() {
        String transactionId = txtTransactionId.getText().trim();
        if (transactionId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Transaction ID must not be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean success = transactionManager.deleteTransaction(transactionId);
        if (success) {
            JOptionPane.showMessageDialog(this, "Transaction deleted successfully.");
            refreshTableAndDashboard();
            clearInputFields();
        } else {
            JOptionPane.showMessageDialog(this, "Transaction not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchTransactions() {
        String keyword = txtSearch.getText().trim();
        if (keyword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter category, date, or type to search.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<Transaction> result = transactionManager.searchTransactions(keyword);
        loadTransactionsToTable(result);
        JOptionPane.showMessageDialog(this, result.size() + " transaction(s) found.");
    }

    private void filterByMonth() {
        String yearMonth = txtMonthFilter.getText().trim();
        if (yearMonth.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter month in YYYY-MM format.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<Transaction> result = transactionManager.filterByMonth(yearMonth);
        loadTransactionsToTable(result);
        JOptionPane.showMessageDialog(this, result.size() + " transaction(s) found for " + yearMonth + ".");
    }

    private void viewMonthlySummary() {
        String yearMonth = txtMonthFilter.getText().trim();
        if (yearMonth.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter month in YYYY-MM format for summary.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String summary = transactionManager.getMonthlySummary(yearMonth);
        JOptionPane.showMessageDialog(this, summary);
    }

    private Transaction validateAndBuildTransaction() {
        String transactionId = txtTransactionId.getText().trim();
        String type = cmbType.getSelectedItem().toString().trim();
        String category = cmbCategory.getSelectedItem().toString().trim();
        String amountText = txtAmount.getText().trim();
        String description = txtDescription.getText().trim();
        String date = txtDate.getText().trim();

        if (transactionId.isEmpty()) {
            showValidationError("Transaction ID must not be empty.");
            return null;
        }

        if (!"Income".equals(type) && !"Expense".equals(type)) {
            showValidationError("Type must be Income or Expense.");
            return null;
        }

        if (category.isEmpty()) {
            showValidationError("Category must not be empty.");
            return null;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountText);
            if (amount <= 0) {
                showValidationError("Amount must be a valid positive number.");
                return null;
            }
        } catch (NumberFormatException e) {
            showValidationError("Amount must be a valid positive number.");
            return null;
        }

        if (description.isEmpty()) {
            showValidationError("Description must not be empty.");
            return null;
        }

        if (date.isEmpty()) {
            showValidationError("Date must not be empty.");
            return null;
        }

        return new Transaction(transactionId, type, category, amount, description, date);
    }

    private void showValidationError(String message) {
        JOptionPane.showMessageDialog(this, message, "Validation Error", JOptionPane.ERROR_MESSAGE);
    }

    private void clearInputFields() {
        txtTransactionId.setText("");
        cmbType.setSelectedIndex(0);
        cmbCategory.setSelectedIndex(0);
        txtAmount.setText("");
        txtDescription.setText("");
        txtDate.setText("");
        tblTransactions.clearSelection();
    }

    private void loadTransactionsToTable(List<Transaction> transactions) {
        tableModel.setRowCount(0);
        for (Transaction transaction : transactions) {
            tableModel.addRow(new Object[]{
                transaction.getTransactionId(),
                transaction.getType(),
                transaction.getCategory(),
                String.format("%.2f", transaction.getAmount()),
                transaction.getDescription(),
                transaction.getDate()
            });
        }
    }

    private void updateDashboardLabels() {
        lblTotalIncome.setText(String.format("Total Income: %.2f", transactionManager.calculateTotalIncome()));
        lblTotalExpense.setText(String.format("Total Expense: %.2f", transactionManager.calculateTotalExpense()));
        lblBalance.setText(String.format("Balance: %.2f", transactionManager.calculateBalance()));
    }

    private void refreshTableAndDashboard() {
        loadTransactionsToTable(transactionManager.getAllTransactions());
        updateDashboardLabels();
    }
}
