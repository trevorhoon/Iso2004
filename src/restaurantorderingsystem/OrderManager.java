package restaurantorderingsystem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderManager {
    private static final double VAT_RATE = 0.07;

    private final File ordersFile;
    private final DecimalFormat df;

    public OrderManager(String ordersFilePath) {
        this.ordersFile = new File(ordersFilePath);
        this.df = new DecimalFormat("0.00");
    }

    public double calculateSubtotal(List<OrderItem> orderItems) {
        double subtotal = 0.0;
        for (OrderItem orderItem : orderItems) {
            subtotal += orderItem.getLineTotal();
        }
        return subtotal;
    }

    public double calculateVat(double subtotal) {
        return subtotal * VAT_RATE;
    }

    public double calculateNetTotal(double subtotal, double vat) {
        return subtotal + vat;
    }

    public double calculateChange(double moneyPaid, double netTotal) {
        return moneyPaid - netTotal;
    }

    public String formatMoney(double amount) {
        return df.format(amount);
    }

    public String generateOrderId() {
        return "O" + System.currentTimeMillis();
    }

    public String getCurrentDateTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
    }

    public void saveOrder(Order order, double subtotal, double vat, double netTotal, double moneyPaid, double change) throws IOException {
        boolean fileExists = ordersFile.exists();
        BufferedWriter writer = new BufferedWriter(new FileWriter(ordersFile, true));

        if (!fileExists || ordersFile.length() == 0) {
            writer.write("OrderID|ItemID|ItemName|Quantity|Price|Subtotal|VAT|NetTotal|MoneyPaid|ChangeAmount|DateTime");
            writer.newLine();
        }

        String dateTime = getCurrentDateTime();
        for (OrderItem orderItem : order.getOrderItems()) {
            writer.write(order.getOrderId() + "|" + orderItem.getItemId() + "|" + orderItem.getItemName() + "|"
                    + orderItem.getQuantity() + "|" + formatMoney(orderItem.getPrice()) + "|" + formatMoney(subtotal)
                    + "|" + formatMoney(vat) + "|" + formatMoney(netTotal) + "|" + formatMoney(moneyPaid)
                    + "|" + formatMoney(change) + "|" + dateTime);
            writer.newLine();
        }

        writer.close();
    }

    public List<String[]> loadOrderHistory() throws IOException {
        List<String[]> rows = new ArrayList<String[]>();
        if (!ordersFile.exists()) {
            return rows;
        }

        BufferedReader reader = new BufferedReader(new FileReader(ordersFile));
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.trim().isEmpty() || line.startsWith("OrderID|")) {
                continue;
            }
            String[] parts = line.split("\\|");
            if (parts.length == 11) {
                rows.add(parts);
            }
        }
        reader.close();
        return rows;
    }
}
