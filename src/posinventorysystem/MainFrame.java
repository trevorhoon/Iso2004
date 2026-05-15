package posinventorysystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Main Java Swing GUI.
 */
public class MainFrame extends JFrame {
    private final ProductManager productManager;
    private final SalesManager salesManager;

    private JTextField txtProductId;
    private JTextField txtProductName;
    private JTextField txtCategory;
    private JTextField txtQuantity;
    private JTextField txtPricePerUnit;

    private JTextField txtSellProductId;
    private JTextField txtSellQuantity;
    private JTextField txtMoneyPaid;

    private JTable tblProducts;
    private JTable tblSalesHistory;

    public MainFrame() {
        this.productManager = new ProductManager("data/products.txt");
        this.salesManager = new SalesManager(productManager, "data/sales.txt");

        setTitle("POS Inventory System");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Dashboard", createDashboardPanel());
        tabbedPane.addTab("Product Management", createProductManagementPanel());
        tabbedPane.addTab("Sales / POS", createSalesPanel());
        tabbedPane.addTab("Transaction History", createHistoryPanel());

        add(tabbedPane);
        refreshProductTable(productManager.getAllProducts());
        refreshSalesTable();
    }

    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("SansSerif", Font.PLAIN, 16));
        textArea.setText("Welcome to POS Inventory System\n\n"
                + "Use Product Management to add/update/delete products.\n"
                + "Use Sales / POS to sell products and calculate VAT (7%).\n"
                + "Use Transaction History to view sales records.");
        panel.add(textArea, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createProductManagementPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Product Details"));

        txtProductId = new JTextField();
        txtProductName = new JTextField();
        txtCategory = new JTextField();
        txtQuantity = new JTextField();
        txtPricePerUnit = new JTextField();

        formPanel.add(new JLabel("Product ID:"));
        formPanel.add(txtProductId);
        formPanel.add(new JLabel("Product Name:"));
        formPanel.add(txtProductName);
        formPanel.add(new JLabel("Category:"));
        formPanel.add(txtCategory);
        formPanel.add(new JLabel("Quantity:"));
        formPanel.add(txtQuantity);
        formPanel.add(new JLabel("Price per Unit:"));
        formPanel.add(txtPricePerUnit);

        JButton btnAddProduct = new JButton("Add Product");
        JButton btnUpdateProduct = new JButton("Update Product");
        JButton btnDeleteProduct = new JButton("Delete Product");
        JButton btnSearchProduct = new JButton("Search Product");
        JButton btnClear = new JButton("Clear");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(btnAddProduct);
        buttonPanel.add(btnUpdateProduct);
        buttonPanel.add(btnDeleteProduct);
        buttonPanel.add(btnSearchProduct);
        buttonPanel.add(btnClear);

        tblProducts = new JTable(new DefaultTableModel(
                new Object[]{"Product ID", "Product Name", "Category", "Quantity", "Price"}, 0));
        JScrollPane productScrollPane = new JScrollPane(tblProducts);

        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.CENTER);
        panel.add(productScrollPane, BorderLayout.SOUTH);

        btnAddProduct.addActionListener(e -> addProduct());
        btnUpdateProduct.addActionListener(e -> updateProduct());
        btnDeleteProduct.addActionListener(e -> deleteProduct());
        btnSearchProduct.addActionListener(e -> searchProduct());
        btnClear.addActionListener(e -> clearProductFields());

        return panel;
    }

    private JPanel createSalesPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Sales / POS"));

        txtSellProductId = new JTextField();
        txtSellQuantity = new JTextField();
        txtMoneyPaid = new JTextField();

        JButton btnSellProduct = new JButton("Sell Product");
        JButton btnClear = new JButton("Clear");

        panel.add(new JLabel("Product ID:"));
        panel.add(txtSellProductId);
        panel.add(new JLabel("Quantity to Sell:"));
        panel.add(txtSellQuantity);
        panel.add(new JLabel("Money Paid:"));
        panel.add(txtMoneyPaid);
        panel.add(btnSellProduct);
        panel.add(btnClear);

        btnSellProduct.addActionListener(e -> sellProduct());
        btnClear.addActionListener(e -> clearSalesFields());

        return panel;
    }

    private JPanel createHistoryPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        tblSalesHistory = new JTable(new DefaultTableModel(
                new Object[]{"Sale ID", "Product ID", "Product Name", "Qty Sold", "Total", "VAT", "Net", "Paid", "Change", "Date Time"}, 0));

        JButton btnViewHistory = new JButton("View History");
        btnViewHistory.addActionListener(e -> refreshSalesTable());

        panel.add(new JScrollPane(tblSalesHistory), BorderLayout.CENTER);
        panel.add(btnViewHistory, BorderLayout.SOUTH);
        return panel;
    }

    private void addProduct() {
        try {
            String id = txtProductId.getText().trim();
            String name = txtProductName.getText().trim();
            String category = txtCategory.getText().trim();
            int quantity = Integer.parseInt(txtQuantity.getText().trim());
            double price = Double.parseDouble(txtPricePerUnit.getText().trim());

            if (id.isEmpty() || name.isEmpty()) {
                showError("Product ID and Product Name are required.");
                return;
            }
            if (quantity <= 0 || price <= 0) {
                showError("Quantity and Price must be positive numbers.");
                return;
            }

            boolean added = productManager.addProduct(new Product(id, name, category, quantity, price));
            if (added) {
                showSuccess("Product added successfully.");
                refreshProductTable(productManager.getAllProducts());
                clearProductFields();
            } else {
                showError("Product ID already exists.");
            }
        } catch (NumberFormatException ex) {
            showError("Quantity and Price must be valid numbers.");
        }
    }

    private void updateProduct() {
        try {
            String id = txtProductId.getText().trim();
            String name = txtProductName.getText().trim();
            String category = txtCategory.getText().trim();
            int quantity = Integer.parseInt(txtQuantity.getText().trim());
            double price = Double.parseDouble(txtPricePerUnit.getText().trim());

            if (id.isEmpty() || name.isEmpty()) {
                showError("Product ID and Product Name are required.");
                return;
            }
            if (quantity <= 0 || price <= 0) {
                showError("Quantity and Price must be positive numbers.");
                return;
            }

            boolean updated = productManager.updateProduct(new Product(id, name, category, quantity, price));
            if (updated) {
                showSuccess("Product updated successfully.");
                refreshProductTable(productManager.getAllProducts());
                clearProductFields();
            } else {
                showError("Product not found.");
            }
        } catch (NumberFormatException ex) {
            showError("Quantity and Price must be valid numbers.");
        }
    }

    private void deleteProduct() {
        String id = txtProductId.getText().trim();
        if (id.isEmpty()) {
            showError("Product ID is required.");
            return;
        }

        boolean deleted = productManager.deleteProduct(id);
        if (deleted) {
            showSuccess("Product deleted successfully.");
            refreshProductTable(productManager.getAllProducts());
            clearProductFields();
        } else {
            showError("Product not found.");
        }
    }

    private void searchProduct() {
        String keyword = txtProductId.getText().trim();
        String nameKeyword = txtProductName.getText().trim();

        if (!keyword.isEmpty()) {
            Product product = productManager.searchById(keyword);
            if (product != null) {
                refreshProductTable(List.of(product));
            } else {
                showError("No product found by Product ID.");
            }
        } else if (!nameKeyword.isEmpty()) {
            List<Product> found = productManager.searchByName(nameKeyword);
            if (!found.isEmpty()) {
                refreshProductTable(found);
            } else {
                showError("No product found by Product Name.");
            }
        } else {
            showError("Enter Product ID or Product Name for searching.");
        }
    }

    private void sellProduct() {
        try {
            String productId = txtSellProductId.getText().trim();
            int qty = Integer.parseInt(txtSellQuantity.getText().trim());
            double paid = Double.parseDouble(txtMoneyPaid.getText().trim());

            if (productId.isEmpty()) {
                showError("Product ID is required.");
                return;
            }

            Product product = productManager.searchById(productId);
            if (product == null) {
                showError("Product not found.");
                return;
            }
            if (qty <= 0) {
                showError("Quantity sold must be a valid positive number.");
                return;
            }
            if (qty > product.getQuantity()) {
                showError("Quantity sold must not be greater than available stock.");
                return;
            }

            double totalPrice = salesManager.calculateTotalPrice(qty, product.getPricePerUnit());
            double vat = salesManager.calculateVat(totalPrice);
            double netPrice = salesManager.calculateNetPrice(totalPrice, vat);

            if (paid < netPrice) {
                showError("Money paid must not be less than net price.");
                return;
            }

            Sale sale = salesManager.sellProduct(productId, qty, paid);
            if (sale != null) {
                showSuccess("Sale successful.\nTotal: " + String.format("%.2f", totalPrice)
                        + "\nVAT (7%): " + String.format("%.2f", vat)
                        + "\nNet Price: " + String.format("%.2f", netPrice)
                        + "\nChange: " + String.format("%.2f", sale.getChangeAmount()));
                refreshProductTable(productManager.getAllProducts());
                refreshSalesTable();
                clearSalesFields();
            } else {
                showError("Unable to complete sale.");
            }
        } catch (NumberFormatException ex) {
            showError("Quantity and Money Paid must be valid numbers.");
        }
    }

    private void refreshProductTable(List<Product> productList) {
        DefaultTableModel model = (DefaultTableModel) tblProducts.getModel();
        model.setRowCount(0);
        for (Product product : productList) {
            model.addRow(new Object[]{product.getProductId(), product.getProductName(), product.getCategory(),
                    product.getQuantity(), String.format("%.2f", product.getPricePerUnit())});
        }
    }

    private void refreshSalesTable() {
        DefaultTableModel model = (DefaultTableModel) tblSalesHistory.getModel();
        model.setRowCount(0);
        for (Sale sale : salesManager.getSalesHistory()) {
            model.addRow(new Object[]{sale.getSaleId(), sale.getProductId(), sale.getProductName(),
                    sale.getQuantitySold(), String.format("%.2f", sale.getTotalPrice()),
                    String.format("%.2f", sale.getVat()), String.format("%.2f", sale.getNetPrice()),
                    String.format("%.2f", sale.getMoneyPaid()), String.format("%.2f", sale.getChangeAmount()),
                    sale.getDateTime()});
        }
    }

    private void clearProductFields() {
        txtProductId.setText("");
        txtProductName.setText("");
        txtCategory.setText("");
        txtQuantity.setText("");
        txtPricePerUnit.setText("");
        refreshProductTable(productManager.getAllProducts());
    }

    private void clearSalesFields() {
        txtSellProductId.setText("");
        txtSellQuantity.setText("");
        txtMoneyPaid.setText("");
    }

    private void showSuccess(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
