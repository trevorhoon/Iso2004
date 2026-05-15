package librarymanagementsystem;

public class BorrowRecord {
    private String transactionId;
    private String memberId;
    private String memberName;
    private String bookId;
    private String bookTitle;
    private String borrowDate;
    private String returnDate;
    private String status;

    public BorrowRecord(String transactionId, String memberId, String memberName, String bookId,
                        String bookTitle, String borrowDate, String returnDate, String status) {
        this.transactionId = transactionId;
        this.memberId = memberId;
        this.memberName = memberName;
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.status = status;
    }

    public String getTransactionId() { return transactionId; }
    public String getMemberId() { return memberId; }
    public String getMemberName() { return memberName; }
    public String getBookId() { return bookId; }
    public String getBookTitle() { return bookTitle; }
    public String getBorrowDate() { return borrowDate; }
    public String getReturnDate() { return returnDate; }
    public String getStatus() { return status; }

    public void setReturnDate(String returnDate) { this.returnDate = returnDate; }
    public void setStatus(String status) { this.status = status; }

    public String toFileString() {
        return transactionId + "|" + memberId + "|" + memberName + "|" + bookId + "|"
                + bookTitle + "|" + borrowDate + "|" + returnDate + "|" + status;
    }
}
