package librarymanagementsystem;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BookManager {
    private final List<Book> books = new ArrayList<>();
    private final File file;

    public BookManager(String filePath) {
        this.file = new File(filePath);
        loadBooks();
    }

    public List<Book> getAllBooks() { return books; }

    public boolean addBook(Book book) {
        if (findBookById(book.getBookId()) != null) {
            return false;
        }
        books.add(book);
        saveBooks();
        return true;
    }

    public Book findBookById(String bookId) {
        for (Book book : books) {
            if (book.getBookId().equalsIgnoreCase(bookId)) return book;
        }
        return null;
    }

    public List<Book> searchBooks(String keyword) {
        List<Book> results = new ArrayList<>();
        String search = keyword.toLowerCase();
        for (Book book : books) {
            if (book.getBookId().toLowerCase().contains(search)
                    || book.getTitle().toLowerCase().contains(search)
                    || book.getAuthor().toLowerCase().contains(search)) {
                results.add(book);
            }
        }
        return results;
    }

    public boolean updateBook(Book updatedBook) {
        Book existing = findBookById(updatedBook.getBookId());
        if (existing == null) return false;
        existing.setTitle(updatedBook.getTitle());
        existing.setAuthor(updatedBook.getAuthor());
        existing.setCategory(updatedBook.getCategory());
        existing.setPublishedYear(updatedBook.getPublishedYear());
        existing.setQuantity(updatedBook.getQuantity());
        existing.setAvailableQuantity(updatedBook.getAvailableQuantity());
        saveBooks();
        return true;
    }

    public boolean deleteBook(String bookId) {
        Book existing = findBookById(bookId);
        if (existing == null) return false;
        books.remove(existing);
        saveBooks();
        return true;
    }

    public void saveBooks() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            for (Book book : books) {
                writer.println(book.toFileString());
            }
        } catch (IOException e) {
            System.out.println("Failed to save books: " + e.getMessage());
        }
    }

    public final void loadBooks() {
        books.clear();
        if (!file.exists()) return;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 7) {
                    books.add(new Book(parts[0], parts[1], parts[2], parts[3],
                            Integer.parseInt(parts[4]), Integer.parseInt(parts[5]), Integer.parseInt(parts[6])));
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Failed to load books: " + e.getMessage());
        }
    }
}
