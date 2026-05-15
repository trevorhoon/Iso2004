package librarymanagementsystem;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BorrowManager {
    private final List<BorrowRecord> records = new ArrayList<>();
    private final File file;

    public BorrowManager(String filePath) {
        this.file = new File(filePath);
        loadRecords();
    }

    public List<BorrowRecord> getAllRecords() { return records; }

    public boolean borrowBook(Member member, Book book) {
        if (book.getAvailableQuantity() <= 0) return false;

        String transactionId = "T" + String.format("%03d", records.size() + 1);
        BorrowRecord record = new BorrowRecord(transactionId, member.getMemberId(), member.getFullName(),
                book.getBookId(), book.getTitle(), LocalDate.now().toString(), "-", "Borrowed");
        records.add(record);
        book.setAvailableQuantity(book.getAvailableQuantity() - 1);
        saveRecords();
        return true;
    }

    public boolean returnBook(String memberId, String bookId, BookManager bookManager) {
        for (int i = records.size() - 1; i >= 0; i--) {
            BorrowRecord record = records.get(i);
            if (record.getMemberId().equalsIgnoreCase(memberId)
                    && record.getBookId().equalsIgnoreCase(bookId)
                    && record.getStatus().equalsIgnoreCase("Borrowed")) {
                record.setStatus("Returned");
                record.setReturnDate(LocalDate.now().toString());
                Book book = bookManager.findBookById(bookId);
                if (book != null) {
                    book.setAvailableQuantity(book.getAvailableQuantity() + 1);
                }
                saveRecords();
                return true;
            }
        }
        return false;
    }

    public void saveRecords() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            for (BorrowRecord record : records) {
                writer.println(record.toFileString());
            }
        } catch (IOException e) {
            System.out.println("Failed to save records: " + e.getMessage());
        }
    }

    public final void loadRecords() {
        records.clear();
        if (!file.exists()) return;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 8) {
                    records.add(new BorrowRecord(parts[0], parts[1], parts[2], parts[3],
                            parts[4], parts[5], parts[6], parts[7]));
                }
            }
        } catch (IOException e) {
            System.out.println("Failed to load records: " + e.getMessage());
        }
    }
}
