package restaurantorderingsystem;

public class OrderItem {
    private final String itemId;
    private final String itemName;
    private final int quantity;
    private final double price;

    public OrderItem(String itemId, String itemName, int quantity, double price) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.quantity = quantity;
        this.price = price;
    }

    public String getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public double getLineTotal() {
        return price * quantity;
    }
}
