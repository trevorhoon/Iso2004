import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles transaction operations such as CRUD, searching, file I/O, and summaries.
 */
public class TransactionManager {
    private final List<Transaction> transactions;
    private final File dataFile;

    public TransactionManager(String fileName) {
        this.transactions = new ArrayList<>();
        this.dataFile = new File(fileName);
        loadFromFile();
    }

    public boolean addTransaction(Transaction transaction) {
        if (findById(transaction.getTransactionId()) != null) {
            return false;
        }
        transactions.add(transaction);
        saveToFile();
        return true;
    }

    public boolean updateTransaction(String originalId, Transaction updatedTransaction) {
        Transaction existing = findById(originalId);
        if (existing == null) {
            return false;
        }

        if (!originalId.equals(updatedTransaction.getTransactionId())
                && findById(updatedTransaction.getTransactionId()) != null) {
            return false;
        }

        existing.setTransactionId(updatedTransaction.getTransactionId());
        existing.setType(updatedTransaction.getType());
        existing.setCategory(updatedTransaction.getCategory());
        existing.setAmount(updatedTransaction.getAmount());
        existing.setDescription(updatedTransaction.getDescription());
        existing.setDate(updatedTransaction.getDate());

        saveToFile();
        return true;
    }

    public boolean deleteTransaction(String transactionId) {
        Transaction transaction = findById(transactionId);
        if (transaction == null) {
            return false;
        }
        transactions.remove(transaction);
        saveToFile();
        return true;
    }

    public List<Transaction> getAllTransactions() {
        return new ArrayList<>(transactions);
    }

    public List<Transaction> searchTransactions(String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        List<Transaction> result = new ArrayList<>();
        for (Transaction transaction : transactions) {
            if (transaction.getCategory().toLowerCase().contains(lowerKeyword)
                    || transaction.getDate().toLowerCase().contains(lowerKeyword)
                    || transaction.getType().toLowerCase().contains(lowerKeyword)) {
                result.add(transaction);
            }
        }
        return result;
    }

    public List<Transaction> filterByMonth(String yearMonth) {
        List<Transaction> result = new ArrayList<>();
        for (Transaction transaction : transactions) {
            if (transaction.getDate().startsWith(yearMonth)) {
                result.add(transaction);
            }
        }
        return result;
    }

    public double calculateTotalIncome() {
        double total = 0;
        for (Transaction transaction : transactions) {
            if ("Income".equalsIgnoreCase(transaction.getType())) {
                total += transaction.getAmount();
            }
        }
        return total;
    }

    public double calculateTotalExpense() {
        double total = 0;
        for (Transaction transaction : transactions) {
            if ("Expense".equalsIgnoreCase(transaction.getType())) {
                total += transaction.getAmount();
            }
        }
        return total;
    }

    public double calculateBalance() {
        return calculateTotalIncome() - calculateTotalExpense();
    }

    public String getMonthlySummary(String yearMonth) {
        double monthlyIncome = 0;
        double monthlyExpense = 0;

        for (Transaction transaction : filterByMonth(yearMonth)) {
            if ("Income".equalsIgnoreCase(transaction.getType())) {
                monthlyIncome += transaction.getAmount();
            } else if ("Expense".equalsIgnoreCase(transaction.getType())) {
                monthlyExpense += transaction.getAmount();
            }
        }

        return String.format(
                "Monthly Summary for %s\nTotal Income: %.2f\nTotal Expense: %.2f\nBalance: %.2f",
                yearMonth,
                monthlyIncome,
                monthlyExpense,
                monthlyIncome - monthlyExpense
        );
    }

    private Transaction findById(String transactionId) {
        for (Transaction transaction : transactions) {
            if (transaction.getTransactionId().equalsIgnoreCase(transactionId)) {
                return transaction;
            }
        }
        return null;
    }

    public void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dataFile))) {
            for (Transaction transaction : transactions) {
                writer.write(transaction.toFileString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    public void loadFromFile() {
        transactions.clear();

        if (!dataFile.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(dataFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|", -1);
                if (parts.length == 6) {
                    String id = parts[0];
                    String type = parts[1];
                    String category = parts[2];
                    double amount = Double.parseDouble(parts[3]);
                    String description = parts[4];
                    String date = parts[5];
                    transactions.add(new Transaction(id, type, category, amount, description, date));
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }
}
