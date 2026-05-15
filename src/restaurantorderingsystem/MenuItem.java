package restaurantorderingsystem;

public class MenuItem {
    private String itemId;
    private String itemName;
    private String category;
    private double price;
    private String availabilityStatus;

    public MenuItem(String itemId, String itemName, String category, double price, String availabilityStatus) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.category = category;
        this.price = price;
        this.availabilityStatus = availabilityStatus;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getAvailabilityStatus() {
        return availabilityStatus;
    }

    public void setAvailabilityStatus(String availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }
}
