package posinventorysystem;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles add, update, delete, search, save, and load product data.
 */
public class ProductManager {
    private final List<Product> products;
    private final File productFile;

    public ProductManager(String filePath) {
        this.products = new ArrayList<>();
        this.productFile = new File(filePath);
        loadProductsFromFile();
    }

    public List<Product> getAllProducts() {
        return products;
    }

    public Product searchById(String productId) {
        for (Product product : products) {
            if (product.getProductId().equalsIgnoreCase(productId.trim())) {
                return product;
            }
        }
        return null;
    }

    public List<Product> searchByName(String productName) {
        List<Product> result = new ArrayList<>();
        for (Product product : products) {
            if (product.getProductName().toLowerCase().contains(productName.toLowerCase().trim())) {
                result.add(product);
            }
        }
        return result;
    }

    public boolean addProduct(Product newProduct) {
        if (searchById(newProduct.getProductId()) != null) {
            return false;
        }
        products.add(newProduct);
        saveProductsToFile();
        return true;
    }

    public boolean updateProduct(Product updatedProduct) {
        Product existing = searchById(updatedProduct.getProductId());
        if (existing == null) {
            return false;
        }
        existing.setProductName(updatedProduct.getProductName());
        existing.setCategory(updatedProduct.getCategory());
        existing.setQuantity(updatedProduct.getQuantity());
        existing.setPricePerUnit(updatedProduct.getPricePerUnit());
        saveProductsToFile();
        return true;
    }

    public boolean deleteProduct(String productId) {
        Product product = searchById(productId);
        if (product == null) {
            return false;
        }
        products.remove(product);
        saveProductsToFile();
        return true;
    }

    public void saveProductsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(productFile))) {
            for (Product product : products) {
                writer.write(product.toFileString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public final void loadProductsFromFile() {
        products.clear();
        if (!productFile.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(productFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 5) {
                    String productId = parts[0];
                    String productName = parts[1];
                    String category = parts[2];
                    int quantity = Integer.parseInt(parts[3]);
                    double price = Double.parseDouble(parts[4]);
                    products.add(new Product(productId, productName, category, quantity, price));
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
