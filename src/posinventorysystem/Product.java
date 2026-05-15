package posinventorysystem;

/**
 * Stores product information.
 */
public class Product {
    private String productId;
    private String productName;
    private String category;
    private int quantity;
    private double pricePerUnit;

    public Product(String productId, String productName, String category, int quantity, double pricePerUnit) {
        this.productId = productId;
        this.productName = productName;
        this.category = category;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public String toFileString() {
        return productId + "|" + productName + "|" + category + "|" + quantity + "|" + String.format("%.2f", pricePerUnit);
    }
}
