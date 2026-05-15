package librarymanagementsystem;

public class Book {
    private String bookId;
    private String title;
    private String author;
    private String category;
    private int publishedYear;
    private int quantity;
    private int availableQuantity;

    public Book(String bookId, String title, String author, String category,
                int publishedYear, int quantity, int availableQuantity) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.category = category;
        this.publishedYear = publishedYear;
        this.quantity = quantity;
        this.availableQuantity = availableQuantity;
    }

    public String getBookId() { return bookId; }
    public void setBookId(String bookId) { this.bookId = bookId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public int getPublishedYear() { return publishedYear; }
    public void setPublishedYear(int publishedYear) { this.publishedYear = publishedYear; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public int getAvailableQuantity() { return availableQuantity; }
    public void setAvailableQuantity(int availableQuantity) { this.availableQuantity = availableQuantity; }

    public String toFileString() {
        return bookId + "|" + title + "|" + author + "|" + category + "|"
                + publishedYear + "|" + quantity + "|" + availableQuantity;
    }
}
