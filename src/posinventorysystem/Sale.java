package posinventorysystem;

/**
 * Stores sale transaction information.
 */
public class Sale {
    private String saleId;
    private String productId;
    private String productName;
    private int quantitySold;
    private double totalPrice;
    private double vat;
    private double netPrice;
    private double moneyPaid;
    private double changeAmount;
    private String dateTime;

    public Sale(String saleId, String productId, String productName, int quantitySold, double totalPrice,
                double vat, double netPrice, double moneyPaid, double changeAmount, String dateTime) {
        this.saleId = saleId;
        this.productId = productId;
        this.productName = productName;
        this.quantitySold = quantitySold;
        this.totalPrice = totalPrice;
        this.vat = vat;
        this.netPrice = netPrice;
        this.moneyPaid = moneyPaid;
        this.changeAmount = changeAmount;
        this.dateTime = dateTime;
    }

    public String getSaleId() {
        return saleId;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantitySold() {
        return quantitySold;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public double getVat() {
        return vat;
    }

    public double getNetPrice() {
        return netPrice;
    }

    public double getMoneyPaid() {
        return moneyPaid;
    }

    public double getChangeAmount() {
        return changeAmount;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String toFileString() {
        return saleId + "|" + productId + "|" + productName + "|" + quantitySold + "|"
                + String.format("%.2f", totalPrice) + "|" + String.format("%.2f", vat) + "|"
                + String.format("%.2f", netPrice) + "|" + String.format("%.2f", moneyPaid) + "|"
                + String.format("%.2f", changeAmount) + "|" + dateTime;
    }
}
