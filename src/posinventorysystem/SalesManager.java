package posinventorysystem;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles selling products, VAT calculation, change calculation, stock reduction,
 * and transaction history.
 */
public class SalesManager {
    private static final double VAT_RATE = 0.07;
    private final ProductManager productManager;
    private final List<Sale> salesHistory;
    private final File salesFile;

    public SalesManager(ProductManager productManager, String salesFilePath) {
        this.productManager = productManager;
        this.salesHistory = new ArrayList<>();
        this.salesFile = new File(salesFilePath);
        loadSalesFromFile();
    }

    public List<Sale> getSalesHistory() {
        return salesHistory;
    }

    public double calculateTotalPrice(int quantity, double pricePerUnit) {
        return quantity * pricePerUnit;
    }

    public double calculateVat(double totalPrice) {
        return totalPrice * VAT_RATE;
    }

    public double calculateNetPrice(double totalPrice, double vat) {
        return totalPrice + vat;
    }

    public double calculateChange(double moneyPaid, double netPrice) {
        return moneyPaid - netPrice;
    }

    public String getNextSaleId() {
        int nextNumber = salesHistory.size() + 1;
        return String.format("S%03d", nextNumber);
    }

    public Sale sellProduct(String productId, int quantityToSell, double moneyPaid) {
        Product product = productManager.searchById(productId);
        if (product == null || quantityToSell <= 0 || product.getQuantity() < quantityToSell) {
            return null;
        }

        double totalPrice = calculateTotalPrice(quantityToSell, product.getPricePerUnit());
        double vat = calculateVat(totalPrice);
        double netPrice = calculateNetPrice(totalPrice, vat);

        if (moneyPaid < netPrice) {
            return null;
        }

        double change = calculateChange(moneyPaid, netPrice);
        product.setQuantity(product.getQuantity() - quantityToSell);
        productManager.saveProductsToFile();

        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        Sale sale = new Sale(getNextSaleId(), product.getProductId(), product.getProductName(), quantityToSell,
                totalPrice, vat, netPrice, moneyPaid, change, dateTime);

        salesHistory.add(sale);
        saveSalesToFile();
        return sale;
    }

    public void saveSalesToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(salesFile))) {
            for (Sale sale : salesHistory) {
                writer.write(sale.toFileString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public final void loadSalesFromFile() {
        salesHistory.clear();
        if (!salesFile.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(salesFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 10) {
                    Sale sale = new Sale(parts[0], parts[1], parts[2], Integer.parseInt(parts[3]),
                            Double.parseDouble(parts[4]), Double.parseDouble(parts[5]),
                            Double.parseDouble(parts[6]), Double.parseDouble(parts[7]),
                            Double.parseDouble(parts[8]), parts[9]);
                    salesHistory.add(sale);
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
