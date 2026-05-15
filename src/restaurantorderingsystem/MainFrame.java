package restaurantorderingsystem;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class MainFrame extends JFrame {
    private final MenuManager menuManager;
    private final OrderManager orderManager;
    private Order currentOrder;

    private JTextField txtItemId;
    private JTextField txtItemName;
    private JTextField txtPrice;
    private JComboBox<String> cmbCategory;
    private JComboBox<String> cmbAvailability;
    private JTextField txtSearch;
    private JTable tblMenuItems;
    private DefaultTableModel menuTableModel;

    private JTextField txtOrderId;
    private JComboBox<String> cmbOrderItem;
    private JTextField txtQuantity;
    private JTextField txtMoneyPaid;
    private JTable tblCurrentOrder;
    private DefaultTableModel orderTableModel;
    private JLabel lblSubtotal;
    private JLabel lblVAT;
    private JLabel lblNetTotal;
    private JLabel lblChange;

    private JTable tblOrderHistory;
    private DefaultTableModel historyTableModel;

    public MainFrame() {
        menuManager = new MenuManager("menu_items.txt");
        orderManager = new OrderManager("orders.txt");
        setupFrame();
        loadInitialData();
    }

    private void setupFrame() {
        setTitle("Restaurant Ordering System");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Dashboard", createDashboardPanel());
        tabbedPane.addTab("Menu Management", createMenuPanel());
        tabbedPane.addTab("Order / POS", createOrderPanel());
        tabbedPane.addTab("Order History", createHistoryPanel());

        add(tabbedPane);
    }

    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel title = new JLabel("Restaurant Ordering System", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(28f));

        JLabel subtitle = new JLabel("Use the tabs to manage menu items and create customer orders.", SwingConstants.CENTER);
        panel.add(title, BorderLayout.CENTER);
        panel.add(subtitle, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createMenuPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        JPanel inputPanel = new JPanel(new GridLayout(3, 4, 8, 8));
        txtItemId = new JTextField();
        txtItemName = new JTextField();
        txtPrice = new JTextField();
        cmbCategory = new JComboBox<String>(new String[]{"Food", "Drink", "Dessert", "Snack"});
        cmbAvailability = new JComboBox<String>(new String[]{"Available", "Unavailable"});

        inputPanel.add(new JLabel("Item ID"));
        inputPanel.add(txtItemId);
        inputPanel.add(new JLabel("Item Name"));
        inputPanel.add(txtItemName);
        inputPanel.add(new JLabel("Category"));
        inputPanel.add(cmbCategory);
        inputPanel.add(new JLabel("Price"));
        inputPanel.add(txtPrice);
        inputPanel.add(new JLabel("Availability"));
        inputPanel.add(cmbAvailability);

        JButton btnClear = new JButton("Clear");
        btnClear.addActionListener(e -> clearMenuInputs());
        inputPanel.add(btnClear);

        txtSearch = new JTextField();
        JButton btnSearchItem = new JButton("Search Item");
        btnSearchItem.addActionListener(e -> searchMenuItems());
        inputPanel.add(txtSearch);
        inputPanel.add(btnSearchItem);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnAddItem = new JButton("Add Item");
        JButton btnUpdateItem = new JButton("Update Item");
        JButton btnDeleteItem = new JButton("Delete Item");

        btnAddItem.addActionListener(e -> addMenuItem());
        btnUpdateItem.addActionListener(e -> updateMenuItem());
        btnDeleteItem.addActionListener(e -> deleteMenuItem());

        buttonPanel.add(btnAddItem);
        buttonPanel.add(btnUpdateItem);
        buttonPanel.add(btnDeleteItem);

        menuTableModel = new DefaultTableModel(new String[]{"Item ID", "Item Name", "Category", "Price", "Availability"}, 0);
        tblMenuItems = new JTable(menuTableModel);
        tblMenuItems.getSelectionModel().addListSelectionListener(e -> fillMenuInputsFromSelectedRow());

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(tblMenuItems), BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createOrderPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel(new GridLayout(3, 4, 8, 8));
        txtOrderId = new JTextField();
        txtOrderId.setEditable(false);
        cmbOrderItem = new JComboBox<String>();
        txtQuantity = new JTextField();
        txtMoneyPaid = new JTextField();

        topPanel.add(new JLabel("Order ID"));
        topPanel.add(txtOrderId);
        topPanel.add(new JLabel("Menu Item"));
        topPanel.add(cmbOrderItem);
        topPanel.add(new JLabel("Quantity"));
        topPanel.add(txtQuantity);
        topPanel.add(new JLabel("Money Paid"));
        topPanel.add(txtMoneyPaid);

        JButton btnAddToOrder = new JButton("Add To Order");
        JButton btnRemoveFromOrder = new JButton("Remove From Order");
        JButton btnCalculateTotal = new JButton("Calculate Total");
        JButton btnConfirmOrder = new JButton("Confirm Order");

        btnAddToOrder.addActionListener(e -> addToOrder());
        btnRemoveFromOrder.addActionListener(e -> removeFromOrder());
        btnCalculateTotal.addActionListener(e -> calculateTotals());
        btnConfirmOrder.addActionListener(e -> confirmOrder());

        topPanel.add(btnAddToOrder);
        topPanel.add(btnRemoveFromOrder);
        topPanel.add(btnCalculateTotal);
        topPanel.add(btnConfirmOrder);

        orderTableModel = new DefaultTableModel(new String[]{"Item ID", "Item Name", "Quantity", "Price", "Line Total"}, 0);
        tblCurrentOrder = new JTable(orderTableModel);

        JPanel totalsPanel = new JPanel(new GridLayout(2, 2, 8, 8));
        lblSubtotal = new JLabel("Subtotal: 0.00");
        lblVAT = new JLabel("VAT (7%): 0.00");
        lblNetTotal = new JLabel("Net Total: 0.00");
        lblChange = new JLabel("Change: 0.00");
        totalsPanel.add(lblSubtotal);
        totalsPanel.add(lblVAT);
        totalsPanel.add(lblNetTotal);
        totalsPanel.add(lblChange);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(tblCurrentOrder), BorderLayout.CENTER);
        panel.add(totalsPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createHistoryPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        historyTableModel = new DefaultTableModel(
                new String[]{"Order ID", "Item ID", "Item Name", "Quantity", "Price", "Subtotal", "VAT", "Net Total", "Money Paid", "Change", "Date Time"}, 0);
        tblOrderHistory = new JTable(historyTableModel);
        panel.add(new JScrollPane(tblOrderHistory), BorderLayout.CENTER);
        return panel;
    }

    private void loadInitialData() {
        try {
            menuManager.loadMenuItems();
            refreshMenuTable(menuManager.getMenuItems());
            refreshOrderItemCombo();
            loadOrderHistoryTable();
            resetOrder();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading files: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refreshMenuTable(List<MenuItem> items) {
        menuTableModel.setRowCount(0);
        for (MenuItem item : items) {
            menuTableModel.addRow(new Object[]{item.getItemId(), item.getItemName(), item.getCategory(), orderManager.formatMoney(item.getPrice()), item.getAvailabilityStatus()});
        }
    }

    private void refreshOrderItemCombo() {
        cmbOrderItem.removeAllItems();
        for (MenuItem item : menuManager.getMenuItems()) {
            cmbOrderItem.addItem(item.getItemId() + " - " + item.getItemName());
        }
    }

    private void addMenuItem() {
        String validation = validateMenuInput();
        if (validation != null) {
            showError(validation);
            return;
        }

        MenuItem menuItem = new MenuItem(
                txtItemId.getText().trim(),
                txtItemName.getText().trim(),
                cmbCategory.getSelectedItem().toString(),
                Double.parseDouble(txtPrice.getText().trim()),
                cmbAvailability.getSelectedItem().toString()
        );

        if (!menuManager.addMenuItem(menuItem)) {
            showError("Item ID already exists.");
            return;
        }

        saveMenuAndRefresh("Menu item added successfully.");
        clearMenuInputs();
    }

    private void updateMenuItem() {
        String validation = validateMenuInput();
        if (validation != null) {
            showError(validation);
            return;
        }

        MenuItem updated = new MenuItem(
                txtItemId.getText().trim(),
                txtItemName.getText().trim(),
                cmbCategory.getSelectedItem().toString(),
                Double.parseDouble(txtPrice.getText().trim()),
                cmbAvailability.getSelectedItem().toString()
        );

        if (!menuManager.updateMenuItem(updated)) {
            showError("Item not found for update.");
            return;
        }

        saveMenuAndRefresh("Menu item updated successfully.");
        clearMenuInputs();
    }

    private void deleteMenuItem() {
        String itemId = txtItemId.getText().trim();
        if (itemId.isEmpty()) {
            showError("Item ID must not be empty.");
            return;
        }

        if (!menuManager.deleteMenuItem(itemId)) {
            showError("Item not found for delete.");
            return;
        }

        saveMenuAndRefresh("Menu item deleted successfully.");
        clearMenuInputs();
    }

    private void searchMenuItems() {
        String keyword = txtSearch.getText().trim();
        if (keyword.isEmpty()) {
            refreshMenuTable(menuManager.getMenuItems());
            return;
        }
        List<MenuItem> results = menuManager.searchByIdOrName(keyword);
        refreshMenuTable(results);
    }

    private String validateMenuInput() {
        if (txtItemId.getText().trim().isEmpty()) return "Item ID must not be empty.";
        if (txtItemName.getText().trim().isEmpty()) return "Item name must not be empty.";
        if (cmbCategory.getSelectedItem() == null) return "Category must not be empty.";

        try {
            double price = Double.parseDouble(txtPrice.getText().trim());
            if (price <= 0) return "Price must be a valid positive number.";
        } catch (NumberFormatException e) {
            return "Price must be a valid positive number.";
        }

        String availability = cmbAvailability.getSelectedItem() == null ? "" : cmbAvailability.getSelectedItem().toString();
        if (!availability.equals("Available") && !availability.equals("Unavailable")) {
            return "Availability status must be Available or Unavailable.";
        }
        return null;
    }

    private void addToOrder() {
        if (cmbOrderItem.getSelectedItem() == null) {
            showError("Please add menu items first.");
            return;
        }

        int quantity;
        try {
            quantity = Integer.parseInt(txtQuantity.getText().trim());
            if (quantity <= 0) {
                showError("Quantity must be a valid positive number.");
                return;
            }
        } catch (NumberFormatException e) {
            showError("Quantity must be a valid positive number.");
            return;
        }

        String selected = cmbOrderItem.getSelectedItem().toString();
        String itemId = selected.split(" - ")[0];
        MenuItem item = menuManager.findById(itemId);
        if (item == null) {
            showError("Selected item was not found.");
            return;
        }
        if (!"Available".equals(item.getAvailabilityStatus())) {
            showError("Cannot order unavailable items.");
            return;
        }

        OrderItem orderItem = new OrderItem(item.getItemId(), item.getItemName(), quantity, item.getPrice());
        currentOrder.addOrderItem(orderItem);
        orderTableModel.addRow(new Object[]{item.getItemId(), item.getItemName(), quantity, orderManager.formatMoney(item.getPrice()), orderManager.formatMoney(orderItem.getLineTotal())});
        txtQuantity.setText("");
    }

    private void removeFromOrder() {
        int selectedRow = tblCurrentOrder.getSelectedRow();
        if (selectedRow == -1) {
            showError("Please select a row to remove.");
            return;
        }
        currentOrder.removeOrderItem(selectedRow);
        orderTableModel.removeRow(selectedRow);
    }

    private void calculateTotals() {
        double subtotal = orderManager.calculateSubtotal(currentOrder.getOrderItems());
        double vat = orderManager.calculateVat(subtotal);
        double netTotal = orderManager.calculateNetTotal(subtotal, vat);

        lblSubtotal.setText("Subtotal: " + orderManager.formatMoney(subtotal));
        lblVAT.setText("VAT (7%): " + orderManager.formatMoney(vat));
        lblNetTotal.setText("Net Total: " + orderManager.formatMoney(netTotal));
    }

    private void confirmOrder() {
        if (currentOrder.getOrderItems().isEmpty()) {
            showError("Please add at least one item to the order.");
            return;
        }

        double subtotal = orderManager.calculateSubtotal(currentOrder.getOrderItems());
        double vat = orderManager.calculateVat(subtotal);
        double netTotal = orderManager.calculateNetTotal(subtotal, vat);

        double moneyPaid;
        try {
            moneyPaid = Double.parseDouble(txtMoneyPaid.getText().trim());
        } catch (NumberFormatException e) {
            showError("Money paid must be a valid number.");
            return;
        }

        if (moneyPaid < netTotal) {
            showError("Money paid must not be less than net total.");
            return;
        }

        double change = orderManager.calculateChange(moneyPaid, netTotal);
        lblChange.setText("Change: " + orderManager.formatMoney(change));

        try {
            orderManager.saveOrder(currentOrder, subtotal, vat, netTotal, moneyPaid, change);
            JOptionPane.showMessageDialog(this, "Order saved successfully.");
            loadOrderHistoryTable();
            resetOrder();
        } catch (IOException e) {
            showError("Error saving order: " + e.getMessage());
        }
    }

    private void loadOrderHistoryTable() {
        historyTableModel.setRowCount(0);
        try {
            List<String[]> rows = orderManager.loadOrderHistory();
            for (String[] row : rows) {
                historyTableModel.addRow(row);
            }
        } catch (IOException e) {
            showError("Error loading order history: " + e.getMessage());
        }
    }

    private void saveMenuAndRefresh(String successMessage) {
        try {
            menuManager.saveMenuItems();
            refreshMenuTable(menuManager.getMenuItems());
            refreshOrderItemCombo();
            JOptionPane.showMessageDialog(this, successMessage);
        } catch (IOException e) {
            showError("Error saving menu file: " + e.getMessage());
        }
    }

    private void fillMenuInputsFromSelectedRow() {
        int row = tblMenuItems.getSelectedRow();
        if (row == -1) {
            return;
        }

        txtItemId.setText(menuTableModel.getValueAt(row, 0).toString());
        txtItemName.setText(menuTableModel.getValueAt(row, 1).toString());
        cmbCategory.setSelectedItem(menuTableModel.getValueAt(row, 2).toString());
        txtPrice.setText(menuTableModel.getValueAt(row, 3).toString());
        cmbAvailability.setSelectedItem(menuTableModel.getValueAt(row, 4).toString());
    }

    private void clearMenuInputs() {
        txtItemId.setText("");
        txtItemName.setText("");
        txtPrice.setText("");
        txtSearch.setText("");
        cmbCategory.setSelectedIndex(0);
        cmbAvailability.setSelectedIndex(0);
    }

    private void resetOrder() {
        currentOrder = new Order(orderManager.generateOrderId());
        txtOrderId.setText(currentOrder.getOrderId());
        txtQuantity.setText("");
        txtMoneyPaid.setText("");
        orderTableModel.setRowCount(0);
        lblSubtotal.setText("Subtotal: 0.00");
        lblVAT.setText("VAT (7%): 0.00");
        lblNetTotal.setText("Net Total: 0.00");
        lblChange.setText("Change: 0.00");
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Validation Error", JOptionPane.ERROR_MESSAGE);
    }
}
