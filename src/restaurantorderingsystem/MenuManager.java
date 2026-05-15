package restaurantorderingsystem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MenuManager {
    private final List<MenuItem> menuItems;
    private final File menuFile;
    private final DecimalFormat df;

    public MenuManager(String menuFilePath) {
        this.menuItems = new ArrayList<MenuItem>();
        this.menuFile = new File(menuFilePath);
        this.df = new DecimalFormat("0.00");
    }

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public boolean addMenuItem(MenuItem menuItem) {
        if (findById(menuItem.getItemId()) != null) {
            return false;
        }
        menuItems.add(menuItem);
        return true;
    }

    public boolean updateMenuItem(MenuItem updatedItem) {
        MenuItem existing = findById(updatedItem.getItemId());
        if (existing == null) {
            return false;
        }
        existing.setItemName(updatedItem.getItemName());
        existing.setCategory(updatedItem.getCategory());
        existing.setPrice(updatedItem.getPrice());
        existing.setAvailabilityStatus(updatedItem.getAvailabilityStatus());
        return true;
    }

    public boolean deleteMenuItem(String itemId) {
        MenuItem existing = findById(itemId);
        if (existing == null) {
            return false;
        }
        menuItems.remove(existing);
        return true;
    }

    public MenuItem findById(String itemId) {
        for (MenuItem item : menuItems) {
            if (item.getItemId().equalsIgnoreCase(itemId)) {
                return item;
            }
        }
        return null;
    }

    public List<MenuItem> searchByIdOrName(String keyword) {
        List<MenuItem> results = new ArrayList<MenuItem>();
        String key = keyword.toLowerCase();
        for (MenuItem item : menuItems) {
            if (item.getItemId().toLowerCase().contains(key) || item.getItemName().toLowerCase().contains(key)) {
                results.add(item);
            }
        }
        return results;
    }

    public void loadMenuItems() throws IOException {
        menuItems.clear();
        if (!menuFile.exists()) {
            return;
        }

        BufferedReader reader = new BufferedReader(new FileReader(menuFile));
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.trim().isEmpty() || line.startsWith("ItemID|")) {
                continue;
            }
            String[] parts = line.split("\\|");
            if (parts.length == 5) {
                MenuItem item = new MenuItem(parts[0], parts[1], parts[2], Double.parseDouble(parts[3]), parts[4]);
                menuItems.add(item);
            }
        }
        reader.close();
    }

    public void saveMenuItems() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(menuFile));
        writer.write("ItemID|ItemName|Category|Price|AvailabilityStatus");
        writer.newLine();
        for (MenuItem item : menuItems) {
            writer.write(item.getItemId() + "|" + item.getItemName() + "|" + item.getCategory() + "|"
                    + df.format(item.getPrice()) + "|" + item.getAvailabilityStatus());
            writer.newLine();
        }
        writer.close();
    }
}
